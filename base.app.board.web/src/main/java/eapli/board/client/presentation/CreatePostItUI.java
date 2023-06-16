package eapli.board.client.presentation;

import com.ibm.icu.impl.Pair;
import eapli.board.client.application.CreatePostItController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
        CreatePostItController controller = new CreatePostItController(serverIP, serverPort);

        try {

            //TODO: Check if user has that board
            ArrayList<String> boardRowColumn = controller.getBoardsList();
            ArrayList<String> arr = new ArrayList<>();
            ArrayList<Pair<String, String>> list = new ArrayList<>();
            for (int i = 0; i < boardRowColumn.size(); i = i + 3) {
                arr.add(boardRowColumn.get(i));
                list.add(Pair.of(boardRowColumn.get(i + 1), boardRowColumn.get(i + 2)));
            }


            var widget = new SelectWidget<>("Choose a board to create a postIt on:", arr);
            widget.show();
            String boardChosen = widget.selectedElement();
            int index = widget.selectedOption() - 1;
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

            SelectWidget<String> selec = new SelectWidget<>("Content Type:", List.of("Text", "Image"));
            selec.show();
            if (selec.selectedOption() == 0) {
                return false;
            }

            String content = "";

            switch (selec.selectedOption()) {
                case 1:
                    content = Console.readLine("Content to be added to Post-It:");
                    break;

                case 2:
                    File f = escolherFicheiro();
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
        }finally {
            controller.close();
        }
        return false;
    }

    private static File escolherFicheiro() {
        JFileChooser escolherFicheiro = new JFileChooser("base.app.board.web/src/main/java/eapli/board/www/images");
        FileNameExtensionFilter extensao = new FileNameExtensionFilter(null, "jpg", "svg", "jpeg", "png", "gif", "webp");
        escolherFicheiro.setFileFilter(extensao);
        escolherFicheiro.showOpenDialog(null);
        return new File(escolherFicheiro.getSelectedFile().getAbsolutePath());
    }


    @Override
    public String headline() {
        return "Create PostIt";
    }
}
