package eapli.base.app.common.console.presentation.authz;

import eapli.base.board.application.CreateBoardController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

public class CreateBoardUI extends AbstractUI {
    private final CreateBoardController controller;


    public CreateBoardUI() {
        controller = new CreateBoardController();
    }

    @Override
    protected boolean doShow() {

        final String boardTitle = Console.readLine("Title of the Board:");
        final int rows = Console.readInteger("Number of Rows:");
        final int columns = Console.readInteger("Number of Columns:");

        try{
            if (!controller.createBoard(boardTitle, rows, columns)) {
                System.out.println("Board Title Already Exists");
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Board Dimensions");
            return false;
        }

        System.out.println("Board Created Successfully!!");
        return true;

    }

    @Override
    public String headline() {
        return "Board";
    }
}
