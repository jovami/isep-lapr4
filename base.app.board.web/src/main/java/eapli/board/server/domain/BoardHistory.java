package eapli.board.server.domain;

import lombok.Getter;

import java.util.LinkedList;

public class BoardHistory {

    @Getter
    private final LinkedList<String> history;

    public BoardHistory() {
        this.history = new LinkedList<>();
    }

    public void add(String text) {
        history.push(text);
    }




}
