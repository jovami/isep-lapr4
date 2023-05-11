package eapli.base.board.domain;

import javax.persistence.*;

@Entity
public class PostIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postItId;

    @Column(nullable = false, unique = true)
    private int cellId;


    protected PostIt(){
    }
    public PostIt(int cellId) {
        this.cellId=cellId;
    }


    public void alterCell(int cellId) {
        this.cellId = cellId;
    }

    public int getCellId() {
        return cellId;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DATA")
    private byte[] postItData;



}
