package eapli.base.infrastructure.bootstrapers.demo;


import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.repositories.BoardRepository;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.repositories.StudentRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

import java.util.Optional;

public class BoardBootstrapper implements Action {

    StudentRepository studentRepository = PersistenceContext.repositories().students();
    BoardRepository boardRepository = PersistenceContext.repositories().boards();


    @Override
    public boolean execute() {
        final int MAX_ROWS = 20;
        final int MAX_COLUMNS = 10;

        Board.setMax(MAX_ROWS, MAX_COLUMNS);
        SystemUser owner = user();

        if (owner == null)
            return false;

        saveBoard(BoardTitle.valueOf("board1"), 10, 10, owner);
        saveBoard(BoardTitle.valueOf("board2"), 15, 5, owner);

        return true;
    }

    private void saveBoard(BoardTitle boardTitle, int rows, int columns, SystemUser owner) {
        boardRepository.save(new Board(boardTitle, rows, columns, owner));
    }

    private SystemUser user() {
        Optional<Student> user = studentRepository.findByUsername(Username.valueOf("johnny"));

        Optional<SystemUser> SysUser = user.map(Student::user);
        return SysUser.orElse(null);
    }


}
