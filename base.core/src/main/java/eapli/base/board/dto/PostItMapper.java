package eapli.base.board.dto;


import eapli.base.board.domain.PostIt;
import jovami.util.dto.Mapper;

public class PostItMapper implements Mapper<PostIt,PostItDTO> {

    @Override
    public PostItDTO toDTO(PostIt postIt) {
        return new PostItDTO(postIt);
    }
}
