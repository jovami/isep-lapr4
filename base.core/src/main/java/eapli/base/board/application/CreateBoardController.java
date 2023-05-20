package eapli.base.board.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

@UseCaseController
public class CreateBoardController {
    private Board board;
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final BoardRepository repository;

    private final UserRepository userRepository;

    public CreateBoardController() {
        repository = PersistenceContext.repositories().boards();
        userRepository = PersistenceContext.repositories().users();

    }

    public boolean createBoard(String boardTitle, int rows, int columns) {
        BoardTitle title = new BoardTitle(boardTitle);
        if (repository.ofIdentity(title).isPresent())
            return false;

        board = new Board(boardTitle, rows, columns,
                userRepository.ofIdentity(authz.session().get().authenticatedUser().identity()).get());
        return true;
    }

    public void persistBoard() {
        repository.save(board);
    }

    public long countAll() {
        return repository.size();
    }

}
