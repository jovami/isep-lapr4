package eapli.board.server.application;


import eapli.base.board.domain.Board;
import eapli.base.board.domain.PostIt;
import eapli.base.board.dto.BoardParticipantDTO;
import eapli.base.board.dto.BoardParticipantMapper;
import eapli.base.board.dto.PostItDTO;
import eapli.base.board.dto.PostItMapper;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.io.File;
import java.util.List;

public class PostItService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final BoardParticipantRepository boardParticipantRepository;

    private final UserRepository userRepository;

    public PostItService(){
        boardParticipantRepository = PersistenceContext.repositories().boardParticipants();
        userRepository = PersistenceContext.repositories().users();
    }


    public void createPostIt(Board board, int cellPosition, String text, SystemUser postItOwner)
    {
        board.getCell(cellPosition).createPostIt(text,postItOwner);
    }
    //TODO: MOVE VERIFICATIONS TO HERE

    public void swapPostIts(PostIt postIt1, PostIt postIt2){
        String temp = postIt1.getData();
        postIt1.alterPostItData(postIt2.getData()) ;
        postIt2.alterPostItData(temp);
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
