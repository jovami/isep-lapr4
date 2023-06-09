package eapli.base.board.dto;

import eapli.base.board.domain.Board;
import jovami.util.dto.Mapper;

public class BoardMapper implements Mapper<Board,BoardDTO> {

    @Override
    public BoardDTO toDTO(Board board) {
        return new BoardDTO(board);
    }
}
