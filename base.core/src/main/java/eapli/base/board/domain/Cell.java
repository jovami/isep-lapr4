package eapli.base.board.domain;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;


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
    @Getter
    private final Deque<BoardHistory> history = new ConcurrentLinkedDeque<>();


    protected Cell() {
    }

    public Cell(BoardRow row, BoardColumn column) {
        this.row = row;
        this.column = column;
        this.postIt = null;
    }

    public boolean addPostIt(Board board, PostIt postIt) {
        synchronized (this) {
            if (hasPostIt()) {
                return false;
            }
            this.postIt = postIt;

            return formatString(board, null, postIt.getData(), Type.CREATE);
        }
    }


    public boolean changePostItData(Board board, String newData) {
        synchronized (this) {
            if (!hasPostIt())
                return false;

            if (!formatString(board, this.history.getFirst(), newData, Type.UPDATE))
                return false;
        }

        this.postIt.alterPostItData(newData);

        return true;
    }


    public boolean removePostIt(Board board) {
        synchronized (this) {
            if (!hasPostIt())
                return false;
            var tmp = this.postIt;
            this.postIt = null;

            return formatString(board, null, tmp.getData(), Type.REMOVE);
        }
    }

    public boolean movePostIt(Board board, Cell cellTo) {
        synchronized (this) {
            if (!hasPostIt())
                return false;

            if (cellTo.hasPostIt())
                return false;

            if (!formatString(board, this.history.getFirst(), null, Type.UPDATE))
                return false;
        }

        return cellTo.addPostIt(board, this.postIt) && this.removePostIt(board);
    }

    public String getPostItData() {
        return this.postIt.getData();
    }

    public BoardRow getRow() {
        return row;
    }

    public BoardColumn getColumn() {
        return column;
    }

    public PostIt getPostIt() {
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

    public boolean hasPostIt() {
        return this.postIt != null;
    }


    public boolean addUndoToHistory(Board board, PostIt postit) {
        synchronized (this.history) {
            for (final var entry : this.history) {
                if (Type.valueOf(entry.getType()) == Type.UNDO) {
                    postit.alterPostItData(entry.getPrevContent());

                    return formatString(board, entry, null, Type.UNDO);
                }
            }
        }
        return false;
    }


    private boolean formatString(Board board, BoardHistory entry, String newData, Type type) {
        var sb = new StringBuilder();

        sb.append(type);
        sb.append('\t');
        sb.append(board);
        sb.append('\t');
        sb.append(row.getRowId());
        sb.append(',');
        sb.append(column.getColumnId());
        sb.append('\t');
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm")));

        sb.append('\t');

        switch (type) {
            case UNDO:
                sb.append(entry.getPosContent());
                sb.append('\t');
                sb.append(entry.getPrevContent());
                this.history.push(new UndoPostIt(sb.toString()));
                break;
            case CREATE:
                sb.append(newData);
                this.history.push(new CreatePostIt(sb.toString()));
                break;
            case REMOVE:
                sb.append(newData);
                this.history.push(new RemovePostIt(sb.toString()));
                break;
            case UPDATE:
                sb.append(entry.getPosContent());
                sb.append('\t');
                sb.append(newData);
                this.history.push(new ChangePostIt(sb.toString()));
                break;
        }


        return true;
    }


}
