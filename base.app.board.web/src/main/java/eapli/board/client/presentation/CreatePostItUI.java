package eapli.board.client.presentation;

import com.ibm.icu.impl.Pair;
import eapli.board.client.application.CreatePostItController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.File;
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
            ArrayList<String> boardRowColumn = controller.getBoardsList();
            ArrayList<String> arr = new ArrayList<>();
            ArrayList<Pair<String,String>> list = new ArrayList<>();
            for (int i = 0; i < boardRowColumn.size(); i=i+3) {
                arr.add(boardRowColumn.get(i));
                list.add(Pair.of(boardRowColumn.get(i+1),boardRowColumn.get(i+2)));
            }


            var widget = new SelectWidget<>("Choose a board to create a postIt on:", arr);
            widget.show();
            String boardChosen = widget.selectedElement();
            int index = widget.selectedOption()-1;
            if (boardChosen == null) {
                System.out.println("Invalid Board");
                return false;
            }
            //ArrayList<String> list = controller.getRowsColumnsList();

            System.out.println("Choose a cell to allocate the Post-It");
            String position = Console.readLine(String.format("The Format used should be row,columns (Board has %s Rows and %s Columns):",
                   list.get(index).first, list.get(index).second));
            if (!controller.isCellIdValid(position, Integer.parseInt(list.get(index).first), Integer.parseInt(list.get(index).second))) {
                System.out.println("Invalid Cell Id");
                return false;
            }

            String text;
            do {
                text = Console.readLine("Content to be added to Post-It\n(if the content is image please provide the path between \"\"):");

                //If image
                if (text.startsWith("\"") && text.endsWith("\"")) {
                    File f = new File(text.replaceAll("\"", ""));

                    if (!f.exists()) {
                        System.out.println("[WARN] the following image path does not exist, please provide a valid one");
                        text=null;
                    }
                }

            } while (text==null);

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
