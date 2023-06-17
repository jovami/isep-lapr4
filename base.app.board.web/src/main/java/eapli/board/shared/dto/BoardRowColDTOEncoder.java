package eapli.board.shared.dto;

import eapli.board.shared.SBClientSideEncoder;

/**
 * BoardRowColDTOEncoder
 */
public final class BoardRowColDTOEncoder implements SBClientSideEncoder<BoardRowColDTO> {

    @Override
    public CharSequence fieldSeparator() {
        return "\t";
    }

    @Override
    public CharSequence multiSeparator() {
        return "\f";
    }

    @Override
    public String encode(BoardRowColDTO obj) {
        var sb = new StringBuilder();
        final var delim = fieldSeparator();

        sb.append(obj.boardName());
        sb.append(delim);
        sb.append(obj.row());
        sb.append(delim);
        sb.append(obj.column());

        return sb.toString();
    }

    @Override
    public BoardRowColDTO decode(String str) {
        var fields = str.split("" + fieldSeparator());

        int row = Integer.parseInt(fields[1]);
        int col = Integer.parseInt(fields[2]);

        return new BoardRowColDTO(fields[0], row, col);
    }

}
