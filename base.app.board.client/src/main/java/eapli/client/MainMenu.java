package eapli.client;


import eapli.client.presentation.*;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

import java.net.InetAddress;

public class MainMenu extends AbstractUI {
    private final int serverPort;
    private final InetAddress serverIP;

    private static final String RETURN_LABEL = "Return ";

    // MENU OPTIONS
    private static final int SHARE_BOARD = 1;
    private static final int VIEW_BOARD = 2;
    private static final int CREATE_POSTIT = 3;
    private static final int UPDATE_POSTIT = 4;
    private static final int MOVE_POSTIT = 5;
    private static final int UNDO_POSTIT = 6;
    private static final int VIEW_BOARD_HISTORY = 7;
    private static final int ARCHIVE_BOARD = 8;
    private static final int SHARED_NOTIFICATIONS = 9;

    private static final int EXIT_OPTION = 0;

    private static final String SEPARATOR_LABEL = "--------------";

    public MainMenu(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);

        return renderer.render();
    }

    @Override
    public String headline() {
        return "SBP Menu";
    }

    private Menu buildMainMenu() {
        Menu menu = new Menu("Shared Board App - SBP ");
        menu.addItem(MenuItem.separator(SEPARATOR_LABEL));

        menu.addItem(SHARE_BOARD, "Share board ",
                new ShareBoardUI(serverIP, serverPort)::show);
        menu.addItem(VIEW_BOARD, "View board ",
                new ViewBoardRequestUI(serverIP, serverPort)::show);
        menu.addItem(CREATE_POSTIT, "Create Post-It ",
                new CreatePostItUI(serverIP, serverPort)::show);
        menu.addItem(UPDATE_POSTIT, "Update Post-It ",
                new UpdatePostItUI(serverIP, serverPort)::show);
        menu.addItem(MOVE_POSTIT, "Move Post-It ",
                new MovePostItUI(serverIP, serverPort)::show);
        menu.addItem(UNDO_POSTIT, "Undo last change on a Post-It",
                new UndoPostItLastChangeUI(serverIP, serverPort)::show);
        menu.addItem(VIEW_BOARD_HISTORY, "View Board History ",
                new ViewBoardHistoryUI(serverIP, serverPort)::show);
        menu.addItem(ARCHIVE_BOARD, "Archive board ",
                new ArchiveBoardUI(serverIP, serverPort)::show);
        menu.addItem(SHARED_NOTIFICATIONS, "Notifications ",
                new NotificationsUI(serverIP, serverPort)::show);

        menu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));
        return menu;
    }

}
