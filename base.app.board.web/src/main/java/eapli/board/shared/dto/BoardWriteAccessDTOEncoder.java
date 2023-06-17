package eapli.board.shared.dto;

import eapli.base.board.domain.Board;
import eapli.board.shared.SBEncoder;

/**
 * BoardWriteAccessDTOEncoder
 */
public final class BoardWriteAccessDTOEncoder
        implements SBEncoder<Board, BoardWriteAccessDTO> {

    @Override
    public CharSequence fieldSeparator() {
        return "\t";
    }

    @Override
    public CharSequence multiSeparator() {
        return "\f";
    }

    @Override
    public String encode(Board b) {
        final var sb = new StringBuilder();
        final var delim = fieldSeparator();

        sb.append(b.getBoardTitle().title());
        sb.append(delim);
        sb.append(b.getNumRows());
        sb.append(delim);
        sb.append(b.getNumColumns());

        return sb.toString();
    }

    @Override
    public BoardWriteAccessDTO decode(String str) {
        var fields = str.split("" + fieldSeparator());

        int rows = Integer.parseInt(fields[1]);
        int cols = Integer.parseInt(fields[2]);

        return new BoardWriteAccessDTO(fields[0], rows, cols);
    }
}
