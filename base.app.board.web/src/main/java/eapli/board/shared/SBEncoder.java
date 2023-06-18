package eapli.board.shared;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Encoder
 *
 * @param <T> source from side 1
 * @param <U> target from side 2
 */
public interface SBEncoder<T, U> {

    /**
     * @return char by which to separate object fields
     */
    CharSequence fieldSeparator();

    /**
     * @return char by which to separate multiple objects
     */
    CharSequence multiSeparator();

    /**
     * Encode an object to a plain string delimited by a separator
     *
     * @param obj object to encode
     * @return encoded string
     *
     * @see #fieldSeparator()
     */
    String encode(T obj);

    /**
     * Encode many objects to a single string.
     * Default implementation calls {@link #encode(T)} on each element
     * and joins them by {@link #multiSeparator()}
     *
     * @param objs objects to encode
     * @return encoded string
     *
     * @see #fieldSeparator()
     * @see #multiSeparator()
     */
    default String encode(Iterable<T> objs) {
        return StreamSupport.stream(objs.spliterator(), false)
                .map(this::encode)
                .collect(Collectors.joining(multiSeparator()));
    }

    /**
     * Decode an object from a plain string delimited by a separator
     *
     * @param str string to decode
     * @return decoded object
     *
     * @see #fieldSeparator()
     */
    U decode(String str);

    /**
     * Decode a single string into many objects.
     * Default implementation splits by {@link #multiSeparator()}
     * and then calls {@link #decode(String)} on each of the result elements
     *
     * @param str string to decode
     * @return decoded objects
     *
     * @see #fieldSeparator()
     * @see #multiSeparator()
     */
    default List<U> decodeMany(String str) {
        var objs = str.split("" + multiSeparator());

        return Arrays.stream(objs)
                .map(this::decode)
                .collect(Collectors.toList());
    }
}
