package eapli.client.presentation;

import eapli.client.application.UndoPostItLastChangeController;
import eapli.board.shared.dto.BoardRowColDTO;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;

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
        try {
            this.ctrl = new UndoPostItLastChangeController(serverIP, serverPort);
        } catch (IOException e) {
            System.out.println("Failed to connect to provided SERVER-ADDRESS and SERVER-PORT.");
            return false;
        }
        try {

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

            if (this.ctrl.undoChange(dto))
                System.out.println("Undone successful");
            else
                System.out.println("Error performing undo operation");
        } catch (IOException | ReceivedERRCode e) {
            System.out.println(e.getMessage());
        } finally {
            ctrl.closeSocket();
        }

        return false;
    }

    @Override
    public String headline() {
        return "Undo last change on a Post-It";
    }

}
