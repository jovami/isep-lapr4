package eapli.client.presentation;

import com.ibm.icu.impl.Pair;
import eapli.client.application.CreatePostItController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;
import jovami.util.io.ConsoleUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class CreatePostItUI extends AbstractUI {
    private final int serverPort;
    private final InetAddress serverIP;

    public CreatePostItUI(InetAddress address, int serverPort) {
        this.serverIP = address;
        this.serverPort = serverPort;
    }

    @Override
    protected boolean doShow() {
        CreatePostItController controller = null;
        try {
            controller = new CreatePostItController(serverIP, serverPort);
        } catch (IOException | ReceivedERRCode e) {
            System.out.println("[WARNING] "+e.getMessage());
            return false;
        }
        try {
            var boardRowColumn = controller.getBoardsList();
            var arr = new ArrayList<String>();
            var list = new ArrayList<Pair<String, String>>();
            for (int i = 0; i < boardRowColumn.size(); i = i + 3) {
                arr.add(boardRowColumn.get(i));
                list.add(Pair.of(boardRowColumn.get(i + 1), boardRowColumn.get(i + 2)));
            }


            var widget = new SelectWidget<>("Choose a Board\n==============", arr);
            widget.show();
            String boardChosen = widget.selectedElement();
            int index = widget.selectedOption() - 1;
            if (boardChosen == null) {
                System.out.println("Invalid Board");
                return false;
            }
            //ArrayList<String> list = controller.getRowsColumnsList();
            System.out.printf("[Dimensions: %s (rows) x %s (columns)]\n",
                    list.get(index).first, list.get(index).second);

            int[] rowColumn = selectPositions(list, index);


            String position = rowColumn[0] + "," + rowColumn[1];

            if (!controller.isCellIdValid(position, Integer.parseInt(list.get(index).first), Integer.parseInt(list.get(index).second))) {
                System.out.println("Invalid Cell Id");
                return false;
            }

            SelectWidget<String> selec = new SelectWidget<>("Content Type\n============", List.of("Text", "Image"));
            selec.show();
            if (selec.selectedOption() == 0) {
                return false;
            }

            String content = "";

            switch (selec.selectedOption()) {
                case 1:
                    content = Console.readLine("Content to be added\n===================");
                    break;

                case 2:
                    File f = ConsoleUtils.chooseFile("base.app.board.client/src/main/java/eapli/client/www/images");
                    content = f.getAbsolutePath().replaceAll("\\\\", "/");
                    content = "\"" + content + "\"";
                    break;
            }

            if (controller.
                    createPostIt(controller.createBoardPositionTextString(boardChosen, position, content).toString())) {
                System.out.println("Post-it create successfully");
            } else {
                System.out.println("Cell Already Occupied");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ReceivedERRCode e) {
            System.out.println(e.getMessage());
        } finally {
            controller.close();
        }
        return false;
    }

    private int[] selectPositions(ArrayList<Pair<String, String>> list, int index) {
        int out;
        var row = 0;
        var column = 0;
        do {
            out = 0;
            row = Console.readInteger("-> Board row:");
            if (row > Integer.parseInt(list.get(index).first) || row < 1) {
                System.out.println("Invalid Row");
                out++;
            }
            if (out == 0) {
                do {
                    out = 0;
                    column = Console.readInteger("-> Board column:");
                    if (column > Integer.parseInt(list.get(index).second) || column < 1) {
                        System.out.println("Invalid Column");
                        out++;
                    }

                } while (out != 0);
            }
        } while (out != 0);
        int[] rowColumn = new int[2];
        rowColumn[0] = row;
        rowColumn[1] = column;
        return rowColumn;
    }

    @Override
    public String headline() {
        return "Create PostIt";
    }
}
