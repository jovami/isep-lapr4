package eapli.base.board.dto;


import jovami.util.dto.Mapper;

public class BoardHistoryMapper implements Mapper<String, BoardHistoryDTO> {

    @Override
    public BoardHistoryDTO toDTO(String str) {
        return new BoardHistoryDTO(str);
    }
}
