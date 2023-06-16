package eapli.board.client.presentation;

import java.io.IOException;
import java.net.InetAddress;

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

        var widget = new SelectWidget<>("Choose Board to view\n", this.ctrl.requestBoards());
        widget.show();
        if (widget.selectedOption() <= 0)
            return false;

        var boardName = widget.selectedElement();
        var row = Console.readInteger("Board row:");
        var col = Console.readInteger("Board Col:");

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
