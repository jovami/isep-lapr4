package eapli.base.exam.domain.regular_exam.antlr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class NumericalQuestion implements Question {
    private final String description;
}
