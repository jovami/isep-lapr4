package eapli.base.exam.domain.regular_exam.antlr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class MultipleChoiceQuestion implements Question {
    private final String description;
    private final List<String> options;
}
