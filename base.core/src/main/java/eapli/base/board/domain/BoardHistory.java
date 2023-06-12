package eapli.base.board.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOARD_HISTORY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
public abstract class BoardHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Board board;
    @Column(nullable = false, updatable = false)
    LocalDateTime date;


    protected BoardHistory() {
    }
    public BoardHistory(Board board) {
        this.board = board;
        this.date = LocalDateTime.now();
    }


    public abstract String getType();


    public Board getBoard(){
        return board;
    }

    public Long getId() {
        return id;
    }


    public String toStringHeader() {
        return "BoardHistory:\n" +
                String.format("%-8s | %-5s | %-5s | %-16s\n",
                        "Type", "Cell1", "Cell2", "Date") +
                "----------------------------------------------";
    }

    /*@Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = dateFormat.format(date);
        return String.format("%-8s | [%d,%d] | [%d,%d] | %s\n",
                getType(), getCell1().getRow().getRowId(), getCell1().getColumn().getColumnId(),
                getCell2().getRow().getRowId(),getCell2().getColumn().getColumnId(),formattedDate);
    }*/


}
