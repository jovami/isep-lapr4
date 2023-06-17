package eapli.board.shared.dto;

import eapli.framework.representations.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * BoardRowCol
 */
@DTO
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public final class BoardRowColDTO {

    @NonNull
    private final String boardName;
    @NonNull
    private final Integer row;
    @NonNull
    private final Integer column;

}
