package eapli.board.shared.dto;

import eapli.board.shared.SBClientSideEncoder;

public class BoardFromToDTOEncoder implements SBClientSideEncoder<BoardFromToDTO> {
    @Override
    public CharSequence fieldSeparator() {
        return "\t";
    }

    @Override
    public CharSequence multiSeparator() {
        return "\f";
    }

    @Override
    public String encode(BoardFromToDTO obj) {
        var sb = new StringBuilder();
        final var delim = fieldSeparator();

        sb.append(obj.boardName());
        sb.append(delim);
        sb.append(obj.rowFrom());
        sb.append(delim);
        sb.append(obj.columnFrom());
        sb.append(delim);
        sb.append(obj.rowTo());
        sb.append(delim);
        sb.append(obj.columnTo());

        return sb.toString();
    }

    @Override
    public BoardFromToDTO decode(String str) {
        var fields = str.split("" + fieldSeparator());

        int rowFrom = Integer.parseInt(fields[1]);
        int colFrom = Integer.parseInt(fields[2]);
        int rowTo = Integer.parseInt(fields[3]);
        int colTo = Integer.parseInt(fields[4]);

        return new BoardFromToDTO(fields[0], rowFrom, colFrom, rowTo, colTo);
    }
}

