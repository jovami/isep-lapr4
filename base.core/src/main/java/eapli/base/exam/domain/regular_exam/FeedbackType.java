package eapli.base.exam.domain.regular_exam;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum FeedbackType {

    NONE(0),
    ON_SUBMISSION(1),
    AFTER_CLOSING(2);

    private static Map<Integer, FeedbackType> lookup;

    static {
        var values = FeedbackType.values();
        lookup = new HashMap<>(values.length);
        for (var type : values)
            lookup.put(type.prefix, type);
        lookup = Collections.unmodifiableMap(lookup);
    }

    public final int prefix;

    public static FeedbackType getType(int prefix) {
        return lookup.get(prefix);
    }

    FeedbackType(int i) {
        this.prefix = i;
    }
}
