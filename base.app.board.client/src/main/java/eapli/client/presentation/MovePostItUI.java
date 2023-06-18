package eapli.client.presentation;

import com.ibm.icu.impl.Pair;
import eapli.client.application.MovePostItController;
import eapli.client.dto.MovePostItDTO;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class MovePostItUI extends AbstractUI {
    private final MovePostItController ctrl;

    public MovePostItUI(InetAddress serverIP, int serverPort) {
        super();
        this.ctrl = new MovePostItController(serverIP, serverPort);
    }

    @Override
    protected boolean doShow() {
        var list = new ArrayList<String>();
        var dimensions = new ArrayList<Pair<String, String>>();
        var boardRowColumn = ctrl.listBoardString();
        for (int i = 0; i < boardRowColumn.size(); i = i + 3) {
            list.add(boardRowColumn.get(i));
            dimensions.add(Pair.of(boardRowColumn.get(i + 1), boardRowColumn.get(i + 2)));
        }

        var widget = new SelectWidget<>("Choose a Board\n==============", list);
        widget.show();
        int idx = widget.selectedOption() - 1;
        if ((idx + 1) <= 0)
            return false;

        System.out.println("Moving from\n===========");
        System.out.printf("[Dimensions: %s (rows) x %s (columns)]\n",
                dimensions.get(idx).first, dimensions.get(idx).second);

        var rowFrom = Console.readInteger("-> Board row:");
        if (rowFrom > Integer.parseInt(dimensions.get(idx).first) || rowFrom < 1) {
            System.out.println("Invalid Row");
            return false;
        }

        var columnFrom = Console.readInteger("-> Board column:");
        if (columnFrom > Integer.parseInt(dimensions.get(idx).second) || columnFrom < 1) {
            System.out.println("Invalid Column");
            return false;
        }

        System.out.println("Moving To\n=========");
        System.out.printf("[Dimensions: %s (rows) x %s (columns)]\n",
                dimensions.get(idx).first, dimensions.get(idx).second);

        var rowTo = Console.readInteger("-> Board row:");
        if (rowTo > Integer.parseInt(dimensions.get(idx).first) || rowTo < 1) {
            System.out.println("Invalid Row");
            return false;
        }

        var columnTo = Console.readInteger("-> Board column:");
        if (columnTo > Integer.parseInt(dimensions.get(idx).second) || columnTo < 1) {
            System.out.println("Invalid Column");
            return false;
        }

        var dto = new MovePostItDTO(widget.selectedElement(), rowFrom, columnFrom, rowTo, columnTo);

        try {
            this.ctrl.movePostIt(dto);
        } catch (IOException | ReceivedERRCode e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public String headline() {
        return "Move Post-It";
    }

}