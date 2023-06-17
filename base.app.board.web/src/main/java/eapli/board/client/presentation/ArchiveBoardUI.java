package eapli.board.client.presentation;

import eapli.board.client.application.ArchiveBoardController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class ArchiveBoardUI extends AbstractUI {

    private final int serverPort;
    private final InetAddress serverIP;

    public ArchiveBoardUI(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    protected boolean doShow() {

        ArchiveBoardController controller = new ArchiveBoardController(serverIP, serverPort);

        //Get All board Names
        try {

            List<String> boards = controller.listBoardsUserOwnsNotArchived();

            if (boards == null) {
                System.out.println("\n[INFO] You must first create a board in order to use this feature\n");
                return false;
            }

            //list boards and ask to choose
            SelectWidget<String> widget = new SelectWidget<>("Choose a Board\n==============", boards);
            widget.show();

            if (widget.selectedOption() <= 0)
                return false;

            //archived.Board
            String[] boardsArchived = controller.archivedBoards(widget.selectedElement());
            if (boardsArchived == null) {
                System.out.println("\n[INFO] There was an error choosing the board");
                return false;
            }

            new ListWidget<>("\nList of boards user owns archived",List.of(boardsArchived)).show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ReceivedERRCode e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            controller.closeSocket();
        }



        return false;
    }


    @Override
    public String headline() {
        return "Archive board";
    }

}
