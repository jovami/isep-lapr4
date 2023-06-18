package eapli.client.presentation;

import eapli.board.shared.dto.BoardRowColDataDTO;
import eapli.client.application.UpdatePostItController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;
import jovami.util.io.ConsoleUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class UpdatePostItUI extends AbstractUI {
    private final InetAddress ip;
    private final int port;
    private UpdatePostItController ctrl;

    public UpdatePostItUI(InetAddress ip, int port) {
        super();
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected boolean doShow() {
        try {
            this.ctrl = new UpdatePostItController(ip, port);
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

            var type = new SelectWidget<>("Content Type\n============", List.of("Text", "Image"));
            type.show();
            if (type.selectedOption() == 0) {
                return false;
            }

            var content = "";
            switch (type.selectedOption()) {
                case 1:
                    content = Console.readLine("Content to be added\n===================");
                    break;

                case 2:
                    File f = ConsoleUtils.chooseFile("base.app.board.client/src/main/java/eapli/client/www/images");
                    content = f.getAbsolutePath().replaceAll("\\\\", "/");
                    content = "\"" + content + "\"";
                    break;
            }

            var dto = new BoardRowColDataDTO(selected.title(), row - 1, col - 1, content);

            if (this.ctrl.updatePostIt(dto))
                System.out.println("Post-It updated successfully");
            else
                System.out.println("Failed to update Post-It");
        } catch (IOException | ReceivedERRCode e) {
            System.out.println(e.getMessage());
        } finally {
            ctrl.closeSocket();
        }

        return false;
    }

    @Override
    public String headline() {
        return "Update Post-It Content";
    }
}
