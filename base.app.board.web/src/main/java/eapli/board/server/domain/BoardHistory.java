package eapli.board.server.domain;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.board.server.dto.BoardHistoryDTO;
import eapli.board.server.dto.BoardHistoryMapper;
import lombok.Getter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

@Entity
@Table(name = "BOARD_HISTORY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
public abstract class BoardHistory {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private Type type;
    @Getter
    private BoardTitle boardTitle;
    @Getter
    private String row1;
    @Getter
    private String column1;
    @Getter
    @Column(nullable = false, updatable = false)
    private LocalDateTime time;
    @Getter
    private String prevContent;
    @Getter
    private String posContent;



    protected BoardHistory() {
    }
    public BoardHistory(String str) {
        BoardHistoryMapper mapper= new BoardHistoryMapper();
        BoardHistoryDTO dto = mapper.toDTO(str);
        this.type = Type.valueOf(dto.getType());
        this.boardTitle = BoardTitle.valueOf(dto.getBoard());
        this.row1 = dto.getPosition()[0];
        this.column1 = dto.getPosition()[1];
        this.time = LocalDateTime.parse(dto.getTime());
        if (this.type.equals(Type.CREATE)){
            this.prevContent = null;
            this.posContent = dto.getPosText();
        } else {
            this.prevContent = dto.getPrevText();
            this.posContent = dto.getPosText();
        }
    }


    public abstract String getType();






}
