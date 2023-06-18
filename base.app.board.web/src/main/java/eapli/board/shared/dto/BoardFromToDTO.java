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
public class BoardFromToDTO {
    @NonNull
    private final String boardName;
    @NonNull
    private final Integer rowFrom;
    @NonNull
    private final Integer columnFrom;
    @NonNull
    private final Integer rowTo;
    @NonNull
    private final Integer columnTo;
}
