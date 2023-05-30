package jovami.util.dto;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Mapper
 * Functional interface that reduces code duplication when mapping objects into DTOs
 *
 * @param <T> the mapped class
 * @param <R> the dto target
 */
@FunctionalInterface
public interface Mapper<T, R> {

    R toDTO(T obj);

    default List<R> toDTO(Iterable<T> objs) {
        return StreamSupport.stream(objs.spliterator(), false)
                            .map(this::toDTO)
                            .collect(toList());
    }

    default List<R> toDTO(Iterable<T> objs, Comparator<? super T> cmp) {
        return StreamSupport.stream(objs.spliterator(), false)
                            .sorted(cmp)
                            .map(this::toDTO)
                            .collect(toList());
    }
}
