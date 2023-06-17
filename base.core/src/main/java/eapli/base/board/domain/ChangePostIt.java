package eapli.base.board.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CHANGE")
public class ChangePostIt extends BoardHistory {

    //private Type type;
    private BoardTitle board;
    private int row;
    private int column;
    private LocalDateTime time;
    private String prevContent;
    private String posContent;




    protected ChangePostIt() {
    }
    public ChangePostIt(String str) {
        super(str);
        parseStr(str);
    }

    public void parseStr(String string){
        String[] split = string.split("\t");
        this.board = BoardTitle.valueOf(split[1]);
        this.row = Integer.parseInt(split[2].split(",")[0]);
        this.column = Integer.parseInt(split[2].split(",")[1]);
        this.time = LocalDateTime.parse(split[3]);
        this.prevContent = split[4];
        this.posContent = split[5];

    }


    @Override
    public String getType() {
        return "CHANGE";
    }



    @Override
    public String toString() {
        return getType()+"\t"+board.title()+"\t"+row+"\t"+column+"\t"+time+"\t"+prevContent+"\t"+posContent;
    }


}
