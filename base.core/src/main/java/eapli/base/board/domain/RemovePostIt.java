package eapli.base.board.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("REMOVE")
public class RemovePostIt extends BoardHistory {
    private BoardTitle board;
    private int row;
    private int column;
    private LocalDateTime time;
    private String content;

    protected RemovePostIt() {
        // for ORM
    }

    public RemovePostIt(String str) {
        super(str);
        parseStr(str);
    }

    public void parseStr(String string){
        String[] split = string.split("\t");
        this.board = BoardTitle.valueOf(split[1]);
        this.row = Integer.parseInt(split[2].split(",")[0]);
        this.column = Integer.parseInt(split[2].split(",")[1]);
        this.time = LocalDateTime.parse(split[3], DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm:ss"));
        this.content = split[4];
    }

    @Override
    public String getType() {
        return "REMOVE";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm:ss");
        return getType() + "\t"
                + board.title() + "\t"
                + row + "," + column + "\t"
                + formatter.format(time) + "\t"
                + content;
    }
}
