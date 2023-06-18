package eapli.server.application;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.domain.Cell;
import eapli.board.SBProtocol;
import eapli.server.SBServerApp;
import eapli.server.application.newChangeEvent.NewChangeWatchDog;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.IOException;
import java.net.Socket;

//TODO ABSTRACT HANDLER
public class ViewBoardRequestHandler extends AbstractSBServerHandler {
    public ViewBoardRequestHandler(Socket sock, SBProtocol request) {
        super(sock, request);
    }

    @Override
    public void run() {
        try {

            if (!sentBoards()) {
                System.out.println("[WARNING] It was not possible to send boards");
                SBProtocol.sendErr(null, outS);
                return;
            }

            String[] boardStr = getBoardChoosen();

            if (boardStr == null) {
                System.out.println("[WARNING] Error choosing Board");
                SBProtocol.sendErr("Error while choosing board", outS);
                return;
            }

            SBProtocol html = new SBProtocol();
            html.setCode(SBProtocol.GET_BOARD);

            Board board = SBServerApp.boards.get(BoardTitle.valueOf(boardStr[0]));
            if (board == null) {
                System.out.println("Board does not exist");
                SBProtocol.sendErr("Board does not exist", outS);
                return;
            } else {
                StringBuilder builder = new StringBuilder();
                //First Argument is the title
                builder.append(board.getBoardTitle().title()).append('\0');
                //Second Argument is the numRows
                builder.append(board.getNumRows()).append('\0');
                //Second Argument is the numCols
                builder.append(board.getNumColumns()).append('\0');

                for (Cell cell : board.getCells()) {
                    if (cell.hasPostIt() && cell.getPostIt().hasData()) {
                        builder.append(cell.getPostIt().getData()).append('\0');
                    } else {
                        builder.append(" ").append('\0');
                    }
                }
                html.setContentFromString(builder.toString());
                html.send(outS);
                SBServerApp.activeAuths.get(authToken).setPort(Integer.parseInt(boardStr[1]));

                addSubsriber(board.getBoardTitle().title());
                System.out.printf("[INFO] %s requested to view %s\n",SBServerApp.activeAuths.get(authToken).getUserLoggedIn().username().toString(),board.getBoardTitle().title());
            }

        } catch (IOException | ReceivedERRCode ignored) {

        }
    }

    private void addSubsriber(String board) {
        NewChangeWatchDog.addSub(board, SBServerApp.activeAuths.get(authToken));
    }

    private String[] getBoardChoosen() throws IOException, ReceivedERRCode {
        SBProtocol chooseBoard;

        chooseBoard = new SBProtocol(inS);

        //Verify if the received packet has the right SBPMessageCode
        if (chooseBoard.getCode() != SBProtocol.CHOOSE_BOARD) {
            return null;
        }

        return chooseBoard.getContentAsString().split("\0");
    }

    private boolean sentBoards() throws IOException {
        SBProtocol responseSent = new SBProtocol();
        responseSent.setCode(SBProtocol.LIST_BOARDS);


        ViewBoardRequestService srv = new ViewBoardRequestService();

        Iterable<Board> boards = srv.listReadableNonArchivedBoardsForUser(SBServerApp.activeAuths.get(authToken).getUserLoggedIn());
        StringBuilder builder = new StringBuilder();

        for (Board board : boards) {
            builder.append(board.getBoardTitle().title()).append("\0");
        }

        String b = builder.toString();
        responseSent.setContentFromString(b);
        return responseSent.send(outS);
    }
}