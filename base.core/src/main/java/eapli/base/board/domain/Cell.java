package eapli.base.board.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;

@Entity
public class Cell implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cellId;

    @Embedded
    private BoardRow row;

    @Embedded
    private BoardColumn column;
    @OneToOne
    private PostIt postIt;
    @Transient
    private final Deque<BoardHistory> history = new ConcurrentLinkedDeque<>();

    protected Cell() {
    }

    public Cell(BoardRow row, BoardColumn column) {
        this.row = row;
        this.column = column;
        this.postIt = null;
    }

    public synchronized boolean addPostIt(Board board, PostIt postIt) {
        if (hasPostIt())
            return false;

        this.postIt = postIt;

        formatString(board, null, postIt.getData(), Type.CREATE);
        return true;
    }

    public synchronized boolean updatePostIt(Board board, String data, SystemUser user) {
        if (!this.hasPostIt() || !this.postIt.getOwner().sameAs(user))
            return false;

        this.postIt.alterPostItData(data);

        this.formatString(board, this.history.getFirst(), data, Type.UPDATE);
        return true;
    }

    public synchronized Optional<String> movePostIt(Board board, Cell cellTo, SystemUser user) {
        if (!this.hasPostIt() || !this.postIt.getOwner().sameAs(user))
            return Optional.empty();

        if (cellTo.hasPostIt() || !this.postIt.getOwner().sameAs(user))
            return Optional.empty();

        if (cellTo.addPostIt(board, this.postIt) && this.removePostIt(board))
            return Optional.of(cellTo.postIt.getData());

        return Optional.empty();
    }

    public synchronized boolean removePostIt(Board board) {
        if (!hasPostIt())
            return false;

        var tmp = this.postIt;
        this.postIt = null;

        formatString(board, this.history.getFirst(), tmp.getData(), Type.REMOVE);
        return true;
    }

    public synchronized Optional<String> undoPostItChange(Board board, SystemUser user) {
        if (!this.hasPostIt() || !this.postIt.getOwner().sameAs(user))
            return Optional.empty();

        for (final var entry : this.history) {
            if (Type.valueOf(entry.getType()) == Type.UPDATE) {

                this.postIt.alterPostItData(entry.getPrevContent());

                this.formatString(board, entry, null, Type.UNDO);
                return Optional.of(entry.getPrevContent());
            }
        }
        return Optional.empty();
    }


    public synchronized String getPostItData() {
        return this.postIt.getData();
    }

    public BoardRow getRow() {
        return row;
    }

    public BoardColumn getColumn() {
        return column;
    }

    public synchronized PostIt getPostIt() {
        return this.postIt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cell cell = (Cell) o;
        return Objects.equals(row, cell.row) && Objects.equals(column, cell.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public synchronized boolean hasPostIt() {
        return this.postIt != null;
    }

    private void formatString(Board board, BoardHistory entry, String data, Type type) {
        var sb = new StringBuilder();

        sb.append(type);
        sb.append('\t');
        sb.append(board.getBoardTitle().title());
        sb.append('\t');
        sb.append(row.getRowId());
        sb.append(',');
        sb.append(column.getColumnId());
        sb.append('\t');
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm:ss")));

        sb.append('\t');

        switch (type) {
            case UNDO:
                sb.append(entry.getPosContent());
                sb.append('\t');
                sb.append(entry.getPrevContent());
                this.history.push(new UndoPostIt(sb.toString()));
                break;
            case CREATE:
                sb.append(data);
                this.history.push(new CreatePostIt(sb.toString()));
                break;
            case REMOVE:
                sb.append(data);
                this.history.push(new RemovePostIt(sb.toString()));
                break;
            case UPDATE:
                sb.append(entry.getPosContent());
                sb.append('\t');
                sb.append(data);
                this.history.push(new ChangePostIt(sb.toString()));
                break;
        }
    }

    public synchronized Deque<BoardHistory> getHistory() {
        return new LinkedList<>(history);
    }
}
