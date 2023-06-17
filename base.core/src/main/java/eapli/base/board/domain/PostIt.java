package eapli.base.board.domain;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PostIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postItId;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DATA")
    private String postItData;


    @Transient
    private SystemUser postItOwner;

    protected PostIt() {
    }

    public PostIt(SystemUser postItOwner) {
        this.postItOwner = postItOwner;
    }

    public PostIt(SystemUser postItOwner, String data) {
        this.postItOwner = postItOwner;
        this.postItData = data;
    }

    public SystemUser getOwner() {
        return postItOwner;
    }

    /*public int getPostItId() {
        return postItId;
    }*/

    public synchronized void alterPostItData(String newData) {
        this.postItData = newData;
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

    public boolean hasData() {
        return this.postItData != null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postItId, postItData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PostIt postIt = (PostIt) o;
        return postItId == postIt.postItId && postItData.equals(postIt.postItData);
    }

}
