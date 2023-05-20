package eapli.base.board.domain;

import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class PostIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postItId;

    @Column(nullable = false, unique = true)
    private int cellId;

    protected PostIt() {
    }

    public PostIt(int cellId) {
        this.cellId = cellId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PostIt postIt = (PostIt) o;
        return postItId == postIt.postItId && cellId == postIt.cellId && Arrays.equals(postItData, postIt.postItData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(postItId, cellId);
        result = 31 * result + Arrays.hashCode(postItData);
        return result;
    }
}
