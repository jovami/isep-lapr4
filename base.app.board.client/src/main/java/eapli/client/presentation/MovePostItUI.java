package eapli.client.presentation;

import com.ibm.icu.impl.Pair;
import eapli.board.shared.dto.BoardFromToDTO;
import eapli.board.shared.dto.BoardWriteAccessDTO;
import eapli.client.application.MovePostItController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class MovePostItUI extends AbstractUI {
    private final InetAddress ip;
    private final int port;
    private MovePostItController ctrl;

    public MovePostItUI(InetAddress ip, int port) {
        super();
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected boolean doShow() {
        try {
            this.ctrl = new MovePostItController(ip, port);
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

            System.out.println("Moving from\n===========");
            int[] positionFrom = selectPositions(selected);

            /*var rowFrom = Console.readInteger("-> Board row:");
            if (rowFrom > selected.rows() || rowFrom < 1) {
                System.out.println("Invalid Row");
                return false;
            }

            var colFrom = Console.readInteger("-> Board column:");
            if (colFrom > selected.columns() || colFrom < 1) {
                System.out.println("Invalid Column");
                return false;
            }*/

            System.out.println("Moving to\n=========");
            int[] positionTo = selectPositions(selected);
            /*var rowTo = Console.readInteger("-> Board row:");
            if (rowTo > selected.rows() || rowTo < 1) {
                System.out.println("Invalid Row");
                return false;
            }

            var colTo = Console.readInteger("-> Board column:");
            if (colTo > selected.columns() || colTo < 1) {
                System.out.println("Invalid Column");
                return false;
            }*/

            var dto = new BoardFromToDTO(selected.title(), positionFrom[0] - 1,
                    positionFrom[1] - 1, positionTo[0] - 1, positionTo[1] - 1);

            if (this.ctrl.movePostIt(dto))
                System.out.println("Post-It updated successfully");
            else
                System.out.println("Failed to update Post-It");
        } catch (IOException | ReceivedERRCode e) {
            System.out.println("Could not move Post-It, check if the post-it exists and if the destination is empty");
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
        return "Move Post-It";
    }
}