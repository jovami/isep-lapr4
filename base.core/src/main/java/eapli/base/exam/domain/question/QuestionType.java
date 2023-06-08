package eapli.base.exam.domain.question;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum QuestionType {

    MATCHING(0),
    MULTIPLE_CHOICE(1),
    SHORT_ANSWER(2),
    NUMERICAL(3),
    MISSING_WORDS(4),
    TRUE_FALSE(5);

    private static Map<Integer, QuestionType> lookup;

    static {
        var values = QuestionType.values();
        lookup = new HashMap<>(values.length);
        for (var type : values)
            lookup.put(type.prefix, type);
        lookup = Collections.unmodifiableMap(lookup);
    }

    public final int prefix;

    public static QuestionType getType(int prefix) {
        return lookup.get(prefix);
    }

    QuestionType(int i) {
        this.prefix = i;
    }
}
