package eapli.base.board.domain;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("UNDO")
public class UndoPostIt extends BoardHistory{

    @ManyToOne
    @Column(nullable = false)
    private PostIt postIt;

    protected UndoPostIt() {
    }
    public UndoPostIt(PostIt postIt){
        this.postIt = postIt;
    }


    @Override
    public String getType() {
        return "UNDO";
    }


    public PostIt getPostIt() {
        return postIt;
    }


    public String getPostItData() {
        return postIt.getData();
    }




}
