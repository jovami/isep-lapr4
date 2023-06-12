package eapli.board.client.presentation;


import eapli.base.board.domain.BoardParticipantPermissions;
import eapli.board.client.application.ShareBoardController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ShareBoardUI extends AbstractUI {

    private final int serverPort;
    private final InetAddress serverIP;

    public ShareBoardUI(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    protected boolean doShow() {
        ShareBoardController controller = new ShareBoardController(serverIP, serverPort);

        //Get All board Names
        try {

            List<String> boards = controller.listBoardsUserOwns();

            if (boards == null) {
                System.out.println("\n[INFO] You must first create a board in order to use this feature\n");
                return false;
            }

            //list boards and ask to choose
            SelectWidget<String> widget = new SelectWidget<>("Choose a board to share:", boards);
            widget.show();

            if (widget.selectedOption() <= 0)
                return false;

            //choose.Board
            if (!controller.chooseBoard(widget.selectedElement())) {
                System.out.println("\n[INFO] There was an error choosing the board");
                return false;
            }

            if (controller.users().isEmpty()) {
                System.out.println("\n[INFO] There are no users to be invited");
                return false;
            }


            if (!inviteUsers(controller)) return false;

            if (controller.shareBoard()) {
                System.out.println("[INFO] Board shared with success");
            } else {
                System.out.println("[INFO] There was a problem sharing a board");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ReceivedERRCode e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    private static boolean inviteUsers(ShareBoardController controller) throws IOException {
        List<String> usersList;
        List<BoardParticipantPermissions> perms = new ArrayList<>();
        perms.add(BoardParticipantPermissions.WRITE);
        perms.add(BoardParticipantPermissions.READ);
        SelectWidget<BoardParticipantPermissions> permWid = new SelectWidget<>("With permissions of:", perms);

        int invites=0;
        boolean invite = true;
        try {
            do {
                usersList = controller.users();
                if (usersList.isEmpty()) {
                    System.out.println("\n[INFO] There are no more users to be invited");
                    break;
                }

                SelectWidget<String> widgetx = new SelectWidget<>("Choose User:", usersList);
                widgetx.show();
                if (widgetx.selectedOption() != 0) {
                    permWid.show();
                    if (permWid.selectedOption() != 0) {
                        controller.prepareInvite(widgetx.selectedElement(), permWid.selectedElement());
                        invites++;
                    }
                } else {
                    invite = false;
                }
            } while (invite);

        } catch (ConcurrencyException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return invites>0;
    }

    @Override
    public String headline() {
        return "Share board";
    }


}
