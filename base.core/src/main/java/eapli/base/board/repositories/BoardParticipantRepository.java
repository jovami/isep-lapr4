package eapli.base.board.repositories;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardParticipant;
import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public interface BoardParticipantRepository extends DomainRepository<Integer,BoardParticipant> {

    Iterable<BoardParticipant> listBoardParticipants(Board board);

    Iterable<Board> listBoardsByParticipant(SystemUser user);

    // TODO: ensure archived boards do not show up
    Iterable<Board> withPermission(SystemUser user, BoardParticipantPermissions perm);
    Iterable<BoardParticipant> byUser(SystemUser user);

    default Iterable<Board> boardsUserCanWrite(SystemUser user){
        return withPermission(user,BoardParticipantPermissions.WRITE);
    }

    default Iterable<Board> boardsUserCanRead(SystemUser user){
        return withPermission(user,BoardParticipantPermissions.READ);
    }
    Iterable<Board> listBoardsUserParticipatesNotArchived(SystemUser user);

}
