package eapli.board.server.application.newChangeEvent;

import eapli.base.board.domain.Board;
import eapli.board.SBProtocol;
import eapli.framework.domain.events.DomainEventBase;

public class NewChangeEvent extends DomainEventBase {
    private Board board;
    private SBProtocol message;
    public NewChangeEvent(Board board, SBProtocol message){
        this.board = board;
        this.message = message;
    }

    public Board boardChanged(){
        return this.board;
    }
    public SBProtocol message(){
        return this.message;
    }


}
