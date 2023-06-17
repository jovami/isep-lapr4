package eapli.board.client.presentation;

import java.io.IOException;
import java.net.InetAddress;

import eapli.board.client.application.UndoPostItLastChangeController;
import eapli.board.shared.dto.BoardRowColDTO;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

/**
 * UndoPostItLastChangeUI
 */
public final class UndoPostItLastChangeUI extends AbstractUI {

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

        var widget = new SelectWidget<>("Choose a Board\n==============",
                ctrl.requestBoards());
        widget.show();

        if (widget.selectedOption() <= 0)
            return false;

        var selected = widget.selectedElement();

        var row = Console.readInteger("-> Board row:");
        if (row > selected.rows() || row < 1) {
            System.out.println("Invalid Row");
            return false;
        }

        var col = Console.readInteger("-> Board column:");
        if (col > selected.columns() || col < 1) {
            System.out.println("Invalid Column");
            return false;
        }

        // Convert to 0-based indexing
        var dto = new BoardRowColDTO(selected.title(), row - 1, col - 1);

        try {
            if (this.ctrl.undoChange(dto))
                System.out.println("Undone successful");
            else
                System.out.println("Error performing undo operation");
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
