package eapli.base.board.domain.application;

import eapli.base.board.domain.domain.Board;
import eapli.base.board.domain.domain.BoardTitle;
import eapli.base.board.domain.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class CreateBoardController {
    private Board board;

    private final BoardRepository repository;

    public CreateBoardController(){
        repository = PersistenceContext.repositories().boards();
    }
    public boolean createBoard(String boardTitle, int rows, int columns){
        BoardTitle title = new BoardTitle(boardTitle);
        if (repository.ofIdentity(title).isPresent())
            return false;
        board = new Board(boardTitle, rows,columns);
        return true;
    }


    public void persistBoard(){
        repository.save(board);
    }
    public long countAll(){
        return repository.size();
    }

    public void listBoards(){
        for (Board board: repository.findAll()) {
            System.out.println(board);
        }
    }

}
