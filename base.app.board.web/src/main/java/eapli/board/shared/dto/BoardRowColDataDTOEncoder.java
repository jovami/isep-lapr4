package eapli.board.shared.dto;

import eapli.board.shared.SBClientSideEncoder;

public class BoardRowColDataDTOEncoder implements SBClientSideEncoder<BoardRowColDataDTO> {
    @Override
    public CharSequence fieldSeparator() {
        return "\t";
    }

    @Override
    public CharSequence multiSeparator() {
        return "\f";
    }

    @Override
    public String encode(BoardRowColDataDTO obj) {
        var sb = new StringBuilder();
        final var delim = fieldSeparator();

        sb.append(obj.name());
        sb.append(delim);
        sb.append(obj.row());
        sb.append(delim);
        sb.append(obj.column());
        sb.append(delim);
        sb.append(obj.data());

        return sb.toString();
    }

    @Override
    public BoardRowColDataDTO decode(String str) {
        var fields = str.split("" + fieldSeparator());

        int row = Integer.parseInt(fields[1]);
        int col = Integer.parseInt(fields[2]);

        return new BoardRowColDataDTO(fields[0], row, col, fields[3]);
    }
}
