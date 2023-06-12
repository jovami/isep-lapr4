package eapli.board.client.presentation;

import eapli.board.client.application.CreatePostItController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class CreatePostItUI extends AbstractUI {


    private final int serverPort;
    private final InetAddress serverIP;


    public CreatePostItUI(InetAddress address, int serverPort) {
        this.serverIP = address;
        this.serverPort = serverPort;
    }

    @Override
    protected boolean doShow() {
        CreatePostItController controller = new CreatePostItController(serverIP, serverPort);

        try {

            //TODO: Check if user has that board
            var widget = new SelectWidget<>("Choose a board to create a postIt on:", controller.getBoardsList());
            widget.show();
            String boardChosen = widget.selectedElement();
            int index = widget.selectedOption();
            if (boardChosen == null) {
                System.out.println("Invalid Board");
                return false;
            }
            ArrayList<String> list = controller.getRowsColumnsList();

            //TODO: Check if all cells are occupied
            System.out.println("Choose a cell to allocate the Post-It");
            String position = Console.readLine(String.format("The Format used should be row,columns (ex: 5,10) (Board has %s Rows and %s Columns):",
                   list.get(index-1), list.get(index)));
            if (!controller.isCellIdValid(position, Integer.parseInt(list.get(index-1)), Integer.parseInt(list.get(index)))) {
                System.out.println("Invalid Cell Id");
                return false;
            }

            String text = null;
            if (Console.readBoolean("Do you want to add text to the Post-It? (Y/N)")) {
                text = Console.readLine("Text to be written on Post-It:");
            }

            try {
                String str = controller.
                        createPostIt(controller.createBoardPositionTextString(boardChosen, position, text).toString());
                if (str==null){
                    System.out.println("Invalid Operation");
                    return false;
                }
                System.out.println(str);
            } catch (IllegalStateException e) {
                System.out.println("Cell Already Occupied");
                return false;
            }


        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public String headline() {
        return "Create PostIt";
    }
}
