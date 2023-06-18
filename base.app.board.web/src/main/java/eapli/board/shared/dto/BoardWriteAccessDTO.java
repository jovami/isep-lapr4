package eapli.board.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * BoardWriteAccessDTO
 */
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class BoardWriteAccessDTO {

    private final String title;
    private final Integer rows;
    private final Integer columns;

    @Override
    public String toString() {
        return String.format("%s", this.title);
    }
}
