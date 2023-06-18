package eapli.base.board.domain;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PostIt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public synchronized SystemUser getOwner() {
        return postItOwner;
    }
    

    public synchronized void alterPostItData(String newData) {
        this.postItData = newData;
    }

    public synchronized String getData() {
        return postItData;
    }

    public synchronized boolean hasData() {
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
