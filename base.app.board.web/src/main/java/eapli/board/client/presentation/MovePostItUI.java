package eapli.board.client.presentation;

import com.ibm.icu.impl.Pair;
import eapli.board.client.application.MovePostItController;
import eapli.board.client.application.UndoPostItLastChangeController;
import eapli.board.client.dto.BoardRowColDTO;
import eapli.board.client.dto.MovePostItDTO;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class MovePostItUI extends AbstractUI {
    private MovePostItController ctrl;

    public MovePostItUI(InetAddress serverIP, int serverPort) {
        super();
        this.ctrl = new MovePostItController(serverIP, serverPort);
    }

    @Override
    protected boolean doShow() {
        var list = new ArrayList<String>();
        var boardRowColumn = ctrl.listBoardString();
        for (int i = 0; i < boardRowColumn.size(); i = i + 3) {
            list.add(boardRowColumn.get(i));
        }

        var widget = new SelectWidget<>("Choose Board to view\n", list);
        widget.show();
        if (widget.selectedOption() <= 0)
            return false;

        var boardName = widget.selectedElement();
        System.out.println("Moving from\n===========");
        var rowFrom = Console.readInteger("Board row:");
        var columnFrom = Console.readInteger("Board Col:");
        System.out.println("Moving To\n=========");
        var rowTo = Console.readInteger("Board row:");
        var columnTo = Console.readInteger("Board Col:");

        var dto = new MovePostItDTO(boardName, rowFrom, columnFrom, rowTo, columnTo);

        try {
            this.ctrl.movePostIt(dto);
        } catch (IOException | ReceivedERRCode e) {
            // TODO
        }

        return false;
    }

    @Override
    public String headline() {
        return "Move Post-It";
    }

}