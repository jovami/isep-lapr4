package eapli.board.server;

import eapli.base.board.domain.Board;
import eapli.base.board.domain.BoardTitle;
import eapli.base.board.domain.Cell;
import eapli.board.SBProtocol;
import eapli.board.server.application.ShareBoardService;
import eapli.board.server.application.newChangeEvent.NewChangeWatchDog;
import eapli.framework.validations.Preconditions;
import jovami.util.exceptions.ReceivedERRCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ViewBoardRequestHandler implements Runnable {
    private final Socket sock;
    private DataInputStream inS;
    private DataOutputStream outS;
    private SBProtocol request;


    public ViewBoardRequestHandler(Socket sock, SBProtocol request) {
        Preconditions.areEqual(request.getCode(), SBProtocol.VIEW_ALL_BOARDS);
        this.request = request;
        this.sock = sock;
    }

    @Override
    public void run(){
        try {
            inS = new DataInputStream(sock.getInputStream());
            outS = new DataOutputStream(sock.getOutputStream());

            if (!sentBoards()){
                throw new RuntimeException("It was not possible to send boards");
            }

            String[] boardStr = getBoardChoosen();

            if (boardStr == null){
                throw new RuntimeException("Error choosing Board");
            }

            SBProtocol html = new SBProtocol();
            html.setCode(SBProtocol.GET_BOARD);
            Board board = SBPServerApp.boards.get(BoardTitle.valueOf(boardStr[0]));
            if (board==null) {
                System.out.println("Board does not exist");
                return;
            }

            StringBuilder builder = new StringBuilder();
            //First Argument is the title
            builder.append(board.getBoardTitle().title()).append('\0');
            //Second Argument is the numRows
            builder.append(board.getNumRows()).append('\0');
            //Second Argument is the numCols
            builder.append(board.getNumColumns()).append('\0');

            for (Cell cell : board.getCells() ) {
                if (cell.hasPostIt()){
                    if (cell.getPostIt().hasData()){
                        builder.append(cell.getPostIt().getData()).append('\0');
                    }
                }
                builder.append(" ").append('\0');
            }
            html.setContentFromString(builder.toString());
            html.send(outS);
            SBPServerApp.activeAuths.get(sock.getInetAddress()).setPort(Integer.parseInt(boardStr[1]));


            addSubsriber(board.getBoardTitle().title(),sock.getInetAddress());

            sock.close();
        } catch (IOException | ReceivedERRCode e) {
            throw new RuntimeException(e);
        }
    }

    private void addSubsriber(String board, InetAddress inetAddress) {
        NewChangeWatchDog.addSub(board, SBPServerApp.activeAuths.get(inetAddress));
    }

    private String[] getBoardChoosen() throws IOException, ReceivedERRCode {
        SBProtocol chooseBoard;

        chooseBoard = new SBProtocol(inS);

        //Verify if the received packet has the right SBPMessageCode
        if (chooseBoard.getCode() != SBProtocol.CHOOSE_BOARD) {
            System.out.println("Code message should be " + SBProtocol.CHOOSE_BOARD + " to choose board to view");
            // TODO: SEND ERR
            return null;
        }

        return chooseBoard.getContentAsString().split("\0");
    }

    private boolean sentBoards() throws IOException {
        SBProtocol responseSent = new SBProtocol();
        responseSent.setCode(SBProtocol.LIST_BOARDS);

        ShareBoardService srv = new ShareBoardService();

        Iterable<Board> boards = srv.getBoardsByParticipant(SBPServerApp.activeAuths.get(sock.getInetAddress()).getUserLoggedIn());
        StringBuilder builder = new StringBuilder();

        //TODO: single service
        for (Board board : boards) {
            builder.append(board.getBoardTitle().title()).append("\0");
        }
        for (Board board : srv.listBoardsUserOwns(SBPServerApp.activeAuths.get(sock.getInetAddress()).getUserLoggedIn())) {
            builder.append(board.getBoardTitle().title()).append("\0");
        }
        String b = builder.toString();
        responseSent.setContentFromString(b);
        return responseSent.send(outS);
    }
}