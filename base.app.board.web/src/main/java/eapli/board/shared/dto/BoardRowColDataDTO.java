package eapli.board.shared.dto;

import eapli.framework.representations.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@DTO
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public class BoardRowColDataDTO {
    @NonNull
    private final String name;
    @NonNull
    private final Integer row;
    @NonNull
    private final Integer column;
    @NonNull
    private final String data;
}
