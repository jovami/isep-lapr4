package eapli.base.board.dto;

import eapli.base.board.domain.PostIt;

import java.util.Objects;

public class PostItDTO {

    private final PostIt postIt;

    public PostItDTO(PostIt postIt)
    {
        this.postIt = postIt;
    }

    public PostIt postIt(){return this.postIt;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj == null || obj.getClass() != this.getClass())
            return false;
        var o = (PostIt) obj;
        return this.postIt.equals(o) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.postIt);
    }

    @Override
    public String toString() {
        return " "  + postIt;
    }
}
