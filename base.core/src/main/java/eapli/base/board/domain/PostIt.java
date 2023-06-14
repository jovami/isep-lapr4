package eapli.base.board.domain;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class PostIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postItId;
    @Column(nullable = false, unique = true)
    private int cellId;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DATA")
    private String postItData;

    @ElementCollection(targetClass = LocalDate.class)
    private List<LocalDate> changesInPostIt ;



    @Lob
    private String text;

    @Lob
    private File image;


    protected PostIt() {
    }

    public PostIt(int cellId) {
        this.cellId = cellId;
        this.changesInPostIt = new ArrayList<>();
        changesInPostIt.add(LocalDate.now());
    }

    @Deprecated
    public void alterCell(int cellId) {
        this.cellId = cellId;
    }

    @Deprecated
    public int getCellId() {
        return cellId;
    }

    public int getPostItId() {
        return postItId;
    }

    public void alterPostItData(String newData){
        this.postItData = newData;
    }

    //TODO: implent a service
    @Deprecated
    public void swapPostIts(PostIt postIt1, PostIt postIt2){
        String temp = postIt1.postItData;
        postIt1.postItData = postIt2.postItData;
        postIt2.postItData = temp;
    }
    /*public List<LocalDate> changesInPostIt(){return this.changesInPostIt;}

    public boolean changePostItText(String text)
    {
        this.text = text;
        return changesInPostIt.add(LocalDate.now());
    }

    public boolean changePostItImage(File image)
    {
        this.image = image;
        return changesInPostIt.add(LocalDate.now());
    }*/

    /*public boolean undoLastChangeInPostIt()
    {
        if (!changesInPostIt.isEmpty()) {
            changesInPostIt.remove(changesInPostIt.size() - 1);
            return true;
        }
        return false;
    }*/


    public String getData() {
        return postItData;
    }

    //TODO: IMPLEMENT
    /*public String addContent(String data){
        return this.postItData;
    }*/

    public boolean hasData(){
        return this.postItData!=null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postItId, cellId, postItData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PostIt postIt = (PostIt) o;
        return postItId == postIt.postItId && cellId == postIt.cellId && postItData.equals(postIt.postItData);
    }

}
