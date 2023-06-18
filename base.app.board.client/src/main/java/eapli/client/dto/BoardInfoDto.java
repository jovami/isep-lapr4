package eapli.client.dto;

import java.util.Arrays;

public class BoardInfoDto {
    public static final int HEADER_SIZE = 3;
    private final String[] dataContent;
    private final String title;

    private final int numCols;

    private final int numRows;

    public  BoardInfoDto(String[] boardInfo){
        this.title = boardInfo[0];
        this.numRows = Integer.parseInt(boardInfo[1]);
        this.numCols = Integer.parseInt(boardInfo[2]);
        dataContent = Arrays.copyOfRange(boardInfo,3,boardInfo.length);
    }
    public String[] getDataContent() {
        return dataContent;
    }

    public String getTitle() {
        return title;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void addPostIt(int row, int col, String info) {
        synchronized (this){
            dataContent[((row - 1) * numCols) + (col - 1)] = info;
        }
    }

    public void movePostIt(int rowFrom, int colFrom, int rowTo, int colTo) {
        synchronized (this){
            dataContent[((rowTo - 1) * numCols) + (colTo - 1)] = dataContent[((rowFrom - 1) * numCols) + (colFrom - 1)];
            dataContent[((rowFrom - 1) * numCols) + (colFrom - 1)] = "";
        }
    }
}
