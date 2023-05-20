package eapli.base.app.common.console.presentation.authz;

import eapli.base.board.application.CreateBoardController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

public class CreateBoardUI extends AbstractUI {
    private final CreateBoardController controller;

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
                    "\nPlease insert values bigger than %d and smaller than %d and %d for rows and columns respectively\n",
                    0, MAX_ROWS, MAX_COLUMNS);
            return false;
        } else {
            if (!controller.createBoard(boardTitle, rows, columns)) {
                System.out.println("Board Title Already Exists");
                return false;
            }

            System.out.println("Board Created Successfully!!");
            return true;
        }
    }

    @Override
    public String headline() {
        return "Board";
    }
}
