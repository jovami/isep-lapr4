package eapli.base.board.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.repositories.BoardParticipantRepository;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTO;
import eapli.base.clientusermanagement.dto.SystemUserNameEmailDTOMapper;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShareBoardService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final TransactionalContext txCtx = PersistenceContext.repositories()
            .newTransactionalContext();

    private ArrayList<SystemUser> users;
    private final BoardRepository boardRepository;

    private final BoardParticipantRepository boardParticipantRepository;

    private final UserRepository userRepository;

    public ShareBoardService(){
        boardRepository = PersistenceContext.repositories().boards();
        boardParticipantRepository = PersistenceContext.repositories().boardParticipants();
        userRepository = PersistenceContext.repositories().users();
        users = (ArrayList<SystemUser>) userRepository.findAll();



    }

    public Iterable<Board> listBoardsUserOwns() {

        return this.boardRepository.listBoardsUserOwns(userRepository.ofIdentity(authz.session()
                .orElseThrow()
                .authenticatedUser()
                .identity()).orElseThrow());
    }

    public boolean shareBoard(Board board, List<SystemUserNameEmailDTO> users)
    {
        txCtx.beginTransaction();

        for (SystemUserNameEmailDTO user : users) {
            SystemUser us = fromDTO(user);
            BoardParticipant boardParticipant = new BoardParticipant(board, us);
            boardParticipantRepository.save(boardParticipant);
        }

        txCtx.commit();

        return true;

    }

    public Iterable<BoardParticipant> boardParticipants(Board board)
    {
        return boardParticipantRepository.listBoardParticipants(board);
    }

    private SystemUser fromDTO(SystemUserNameEmailDTO dto) throws ConcurrencyException {
        return this.userRepository.ofIdentity(dto.username())
                .orElseThrow(() -> new ConcurrencyException("User no longer exists"));
    }

    public List<SystemUserNameEmailDTO> Users() {
        return new SystemUserNameEmailDTOMapper().toDTO(users, Comparator.comparing(SystemUser::identity));
    }

}
