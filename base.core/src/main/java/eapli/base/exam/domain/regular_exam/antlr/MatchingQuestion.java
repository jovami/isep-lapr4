package eapli.base.exam.domain.regular_exam.antlr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.util.Pair;

import java.util.List;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class MatchingQuestion implements Question {
    private final String description;
    private final List<String> phrase1;
    private final List<String> phrase2;
}
