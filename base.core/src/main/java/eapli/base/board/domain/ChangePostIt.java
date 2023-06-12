package eapli.base.board.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("CHANGE")
public class ChangePostIt extends BoardHistory{

    @ManyToOne
    @Column(nullable = false)
    private PostIt postIt;

    protected ChangePostIt() {
    }
    public ChangePostIt(PostIt postIt) {
        this.postIt = postIt;
    }


    @Override
    public String getType() {
        return "CHANGE";
    }


    public PostIt getPostIt() {
        return postIt;
    }

    public byte[] getPostItData() {
        return postIt.getData();
    }


}
