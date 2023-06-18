package eapli.client.presentation;

import eapli.board.shared.dto.BoardWriteAccessDTO;
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

            System.out.printf("[Dimensions: %s (rows) x %s (columns)]\n",
                    selected.rows(), selected.columns());
            int[] position = selectPositions(selected);


            // Convert to 0-based indexing
            var dto = new BoardRowColDTO(selected.title(), position[0] - 1, position[1] - 1);

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

    private int[] selectPositions(BoardWriteAccessDTO selected) {
        int out;
        var row = 0;
        var column = 0;
        do {
            out = 0;
            row = Console.readInteger("-> Board row:");
            if (row > selected.rows() || row < 1) {
                System.out.println("Invalid Row");
                out++;
            }
            if (out == 0) {
                do {
                    out = 0;
                    column = Console.readInteger("-> Board column:");
                    if (column > selected.columns() || column < 1) {
                        System.out.println("Invalid Column");
                        out++;
                    }

                } while (out != 0 || row > selected.rows());
            }
        } while (out != 0);
        int[] rowColumn = new int[2];
        rowColumn[0] = row;
        rowColumn[1] = column;
        return rowColumn;
    }

    @Override
    public String headline() {
        return "Undo last change on a Post-It";
    }

}
