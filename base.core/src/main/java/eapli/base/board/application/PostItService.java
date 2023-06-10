package eapli.base.board.application;


import eapli.base.board.domain.Board;
import eapli.base.board.domain.Cell;
import eapli.base.board.domain.PostIt;
import eapli.base.board.dto.*;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PostItService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final BoardParticipantRepository boardParticipantRepository;

    private final UserRepository userRepository;

    public PostItService(){
        boardParticipantRepository = PersistenceContext.repositories().boardParticipants();
        userRepository = PersistenceContext.repositories().users();
    }

    //TODO: corrigir para so receber a lista de board onde a board esta no estado shared
    public List<BoardParticipantDTO> listBoardUserLoggedParticipates() {

        return new BoardParticipantMapper().toDTO(this.boardParticipantRepository.listBoardUserLoggedParticipates(userRepository.ofIdentity(authz.session()
                .orElseThrow()
                .authenticatedUser()
                .identity()).orElseThrow()));
    }

    public PostItDTO createPostIt(Board board, int cellId)
    {
        return new PostItMapper().toDTO(board.createPostIt(cellId));
    }

    public boolean addTextToPostIT(Board board, int cellId, PostIt postIt, String info)
    {
        postIt.changePostItText(info);
        return board.registerChangeInPostIt(cellId,postIt);
    }

    public boolean addImageToPostIT(Board board, int cellId, PostIt postIt, File image)
    {
        postIt.changePostItImage(image);
        return board.registerChangeInPostIt(cellId,postIt);
    }

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
    }

}
