package eapli.base.board.application;

import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.dto.BoardDTO;
import eapli.base.board.dto.BoardParticipantDTO;
import eapli.base.board.dto.PostItDTO;

import java.io.File;
import java.util.List;

public class CreatePostItOnBoardController {

    private final PostItService svc;

    public CreatePostItOnBoardController()
    {
        this.svc = new PostItService();
    }

    public List<BoardParticipantDTO> listBoardUserLoggedParticipates() {
        return this.svc.listBoardUserLoggedParticipates();
    }

    public PostItDTO createPostIt(BoardDTO boardDTO, int cellId)
    {
        return svc.createPostIt(boardDTO.board(),cellId);
    }

    public boolean addTextToPostIT(BoardDTO boardDTO, PostItDTO postItDTO, int cellId, String info)
    {
        return svc.addTextToPostIT(boardDTO.board(),cellId,postItDTO.postIt(),info);
    }

    public boolean addImageToPostIT(BoardDTO boardDTO, PostItDTO postItDTO, int cellId, File image)
    {
        return svc.addImageToPostIT(boardDTO.board(),cellId,postItDTO.postIt(),image);
    }

}
