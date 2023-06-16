package eapli.base.board.domain;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardHistory;
import eapli.base.board.domain.BoardTitle;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("CREATE")
public class CreatePostIt extends BoardHistory {


    //private Type type;
    private BoardTitle board;
    private int row;
    private int column;
    private LocalDateTime time;
    private String content;




    protected CreatePostIt() {
    }
    public CreatePostIt(Board board, String str) {
        super(str);
        parseStr(str);
    }

    public void parseStr(String string){
        String[] split = string.split("\t");
        //this.type = Type.valueOf(split[0]);
        this.board = BoardTitle.valueOf(split[1]);
        this.row = Integer.parseInt(split[2].split(",")[0]);
        this.column = Integer.parseInt(split[2].split(",")[1]);
        this.time = LocalDateTime.parse(split[3], DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm"));
        this.content = split[4];

    }


    @Override
    public String getType() {
        return "CREATE";
    }



    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm");
        return getType()+"\t"+board.title()+"\t"+row+","+column+"\t"+formatter.format(time)+"\t"+content;
    }




}
