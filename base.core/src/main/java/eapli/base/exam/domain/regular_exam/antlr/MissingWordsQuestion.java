package eapli.base.exam.domain.regular_exam.antlr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class MissingWordsQuestion implements Question {
    private final String description;
    private final Map<String, List<String>> groups;
    private final List<String> choices;
}