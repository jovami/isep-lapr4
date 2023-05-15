package eapli.base.app.common.console.presentation.authz;

import eapli.base.board.application.CreateBoardController;
import eapli.base.clientusermanagement.domain.users.Manager;
import eapli.base.clientusermanagement.domain.users.Student;
import eapli.base.clientusermanagement.domain.users.Teacher;
import eapli.base.clientusermanagement.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CreateBoardUI extends AbstractUI {
    @Autowired
    CreateBoardController controller;

    private final int MAX_ROWS = 20;
    private final int MAX_COLUMNS = 10;


    public CreateBoardUI() {
        controller = new CreateBoardController();
    }


    @Override
    protected boolean doShow() {

        final String boardTitle = Console.readLine("Title of the Board:");
        final int rows = Console.readInteger("Number of Rows:");
        final int columns = Console.readInteger("Number of Columns:");


        if (rows > MAX_ROWS || rows < 1 || columns > MAX_COLUMNS || columns < 1) {
            System.out.printf("\nSorry, unable to crate a board with that size. " +
                    "\nPlease insert values bigger than %d and smaller than %d and %d for rows and columns respectively\n", 0, MAX_ROWS, MAX_COLUMNS);
            return false;
        } else {
            if (!controller.createBoard(boardTitle, rows, columns)) {
                System.out.println("Board Title Already Exists");
                return false;
            }


            controller.persistBoard();

            System.out.println("Board Created Successfully!!");
            return true;
        }
    }

    @Override
    public String headline() {
        return "Board";
    }
}
