package eapli.base.board.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("CREATE")
public class CreatePostIt extends BoardHistory{

    @ManyToOne
    @Column(nullable = false)
    private Cell cell1;

    protected CreatePostIt() {
    }
    public CreatePostIt(Cell cell1) {
        this.cell1 = cell1;
    }


    @Override
    public String getType() {
        return "CREATE";
    }


    public Cell getCell1() {
        return cell1;
    }






}
