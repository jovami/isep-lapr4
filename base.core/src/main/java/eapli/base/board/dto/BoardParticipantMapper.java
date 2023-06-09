package eapli.base.board.dto;


import eapli.base.board.domain.BoardParticipant;
import jovami.util.dto.Mapper;

public class BoardParticipantMapper implements Mapper<BoardParticipant,BoardParticipantDTO> {

    @Override
    public BoardParticipantDTO toDTO(BoardParticipant boardParticipant) {
        return new BoardParticipantDTO(boardParticipant);
    }
}
