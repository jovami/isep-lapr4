package eapli.server.application.newChangeEvent;

import eapli.board.SBProtocol;
import eapli.framework.domain.events.DomainEventBase;
public class NewChangeEvent extends DomainEventBase {
    private String board;
    private SBProtocol message;
    public NewChangeEvent(String board, SBProtocol message){
        this.board = board;
        this.message = message;
    }

    public String boardChanged(){
        return this.board;
    }
    public SBProtocol message(){
        return this.message;
    }


}
