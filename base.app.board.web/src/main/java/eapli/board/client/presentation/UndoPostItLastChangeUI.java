package eapli.board.client.presentation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import com.ibm.icu.impl.Pair;
import eapli.board.client.application.UndoPostItLastChangeController;
import eapli.board.client.dto.BoardRowColDTO;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

/**
 * UndoPostItLastChangeUI
 */
public class UndoPostItLastChangeUI extends AbstractUI {

    private final InetAddress serverIP;
    private final int serverPort;

    private UndoPostItLastChangeController ctrl;

    public UndoPostItLastChangeUI(InetAddress serverIP, int serverPort) {
        super();
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    protected boolean doShow() {
        this.ctrl = new UndoPostItLastChangeController(serverIP, serverPort);
        var list = new ArrayList<String>();
        var dimensions = new ArrayList<Pair<String, String>>();
        var boardRowColumn = ctrl.requestBoards();
        for (int i = 0; i < boardRowColumn.size(); i = i + 3) {
            list.add(boardRowColumn.get(i));
            dimensions.add(Pair.of(boardRowColumn.get(i + 1), boardRowColumn.get(i + 2)));
        }

        var widget = new SelectWidget<>("Choose a Board\n==============", list);
        widget.show();
        int idx = widget.selectedOption() - 1;
        if ((idx + 1) <= 0)
            return false;

        var boardName = widget.selectedElement();
        var row = Console.readInteger("-> Board row:");
        if (row > Integer.parseInt(dimensions.get(idx).first) || row < 1) {
            System.out.println("Invalid Row");
            return false;
        }

        var col = Console.readInteger("-> Board column:");
        if (col > Integer.parseInt(dimensions.get(idx).second) || col < 1) {
            System.out.println("Invalid Column");
            return false;
        }

        var dto = new BoardRowColDTO(boardName, row, col);

        try {
            this.ctrl.undoChange(dto);
        } catch (IOException | ReceivedERRCode e) {
            // TODO
        }

        return false;
    }

    @Override
    public String headline() {
        return "Undo last change on a Post-It";
    }

}
