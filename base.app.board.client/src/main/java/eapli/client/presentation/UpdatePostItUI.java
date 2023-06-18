package eapli.client.presentation;

import com.ibm.icu.impl.Pair;
import eapli.client.application.UpdatePostItController;
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

public class UpdatePostItUI extends AbstractUI {
    private final UpdatePostItController ctrl;

    public UpdatePostItUI(InetAddress address, int serverPort) {
        this.ctrl = new UpdatePostItController(address, serverPort);
    }

    @Override
    protected boolean doShow() {
        try {
            var boardList = new ArrayList<String>();
            var list = new ArrayList<Pair<String, String>>();

            var boardRowColumn = ctrl.listBoardString();
            for (int i = 0; i < boardRowColumn.size(); i = i + 3) {
                boardList.add(boardRowColumn.get(i));
                list.add(Pair.of(boardRowColumn.get(i + 1), boardRowColumn.get(i + 2)));
            }

            var selectedBoard = new SelectWidget<>("Choose a Board:", boardList);
            selectedBoard.show();
            var board = selectedBoard.selectedElement();
            if (board == null) {
                System.out.println("Invalid Board");
                return false;
            }

            var idx = selectedBoard.selectedOption() - 1;
            System.out.println("Choose a Cell:");
            var position = Console.readLine(String.format(
                    "The Format used should be row,columns (Board has %s Rows and %s Columns):",
                    list.get(idx).first, list.get(idx).second));

            var type = new SelectWidget<>("Content Type:", List.of("Text", "Image"));
            type.show();
            if (type.selectedOption() == 0) {
                return false;
            }

            var content = "";
            switch (type.selectedOption()) {
                case 1:
                    content = Console.readLine("Content to be added to Post-It:");
                    break;
                case 2:
                    File f = ConsoleUtils.chooseFile("base.app.board.client/src/main/java/eapli/client/www/images");
                    content = f.getAbsolutePath().replaceAll("\\\\", "/");
                    content = "\"" + content + "\"";
                    break;
                default:
                    System.out.println("Invalid Option");
                    return false;
            }

            if (ctrl.updatePostIt(ctrl.createBoardPositionTextString(board, position, content))) {
                System.out.println("Post-it updated successfully");
            } else {
                System.out.println("Cell is empty");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ReceivedERRCode e) {
            System.out.println(e.getMessage());
        } finally {
            ctrl.close();
        }
        return false;
    }

    @Override
    public String headline() {
        return "Update Post-It Content";
    }
}
