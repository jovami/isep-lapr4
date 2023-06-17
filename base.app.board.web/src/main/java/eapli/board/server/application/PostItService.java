package eapli.board.server.application;


import eapli.base.board.domain.Board;
import eapli.base.board.domain.Cell;
import eapli.base.board.domain.PostIt;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

public class PostItService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final BoardParticipantRepository boardParticipantRepository;

    private final UserRepository userRepository;

    public PostItService() {
        boardParticipantRepository = PersistenceContext.repositories().boardParticipants();
        userRepository = PersistenceContext.repositories().users();
    }


    public boolean createPostIt(Board board, int row, int col, String text, SystemUser postItOwner) {
        Cell c = board.getCell(row, col);
        if (c.hasPostIt())
            System.out.println("Post:" + c.getPostIt().getData());

        return board.addPostIt(row, col, new PostIt(postItOwner, text));
    }

    public boolean updatePostIt(Board board, int row, int col, String text, SystemUser postItOwner) {
        var c = board.getCell(row, col);
        if (!c.hasPostIt() || !c.getPostIt().getOwner().equals(postItOwner))
            return false;

        return c.changePostItData(board, text);
    }

    //TODO: MOVE VERIFICATIONS TO HERE
    public boolean movePostIt(Board board, int rowFrom, int colFrom, int rowTo, int colTo, SystemUser postItOwner) {
        var cellFrom = board.getCell(rowFrom, colFrom);
        if (!cellFrom.hasPostIt() || !cellFrom.getPostIt().getOwner().equals(postItOwner))
            return false;

        var cellTo = board.getCell(rowTo, colTo);
        if (cellTo.hasPostIt())
            return false;

        return board.movePostIt(rowFrom, colFrom, rowTo, colTo);
    }

   /*

    public boolean addImageToPostIT(Board board, int cellId, PostIt postIt, File image)
    {
        postIt.changePostItImage(image);
        return board.registerChangeInPostIt(cellId,postIt);
    }*/
/*
    public boolean undoLastChangeInPostIt(Board board, PostIt postIt, int cellId)
    {
        return board.getCells().get(cellId).undoLastChangeInPostIt(postIt);
    }

    public List<LocalDate> viewHistoryOfUpdatesOnBoard(Board board)
    {
        List<LocalDate> changesInBoard = new ArrayList<>();
        List<Cell> cells = board.getCells();

        for(Cell cl : cells)
        {
            List<PostIt> postIts = cl.getPostIts();

            for(PostIt pos : postIts)
            {
                List<LocalDate> changes=pos.changesInPostIt();
                for(LocalDate ld: changes)
                {
                    changesInBoard.add(ld);
                }
            }
        }

        return changesInBoard;
    }*/

}
