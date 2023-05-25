package jovami.util.io;

import eapli.framework.io.util.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class ConsoleUtils {
    private static final DateTimeFormatter fmtLocalDate = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter fmtLocalTime = DateTimeFormatter.ofPattern("H:m");
    private static final DateTimeFormatter fmtLocalDateTime = DateTimeFormatter.ofPattern("d/M/yyyy H:m");

    public static Optional<LocalDateTime> readLocalDateTime(String prompt) {
        try {
            var line = Console.readLine(prompt);
            return Optional.of(LocalDateTime.parse(line, fmtLocalDateTime));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public static Optional<LocalDate> readLocalDate(String prompt) {
        try {
            var line = Console.readLine(prompt);
            return Optional.of(LocalDate.parse(line, fmtLocalDate));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public static Optional<LocalTime> readLocalTime(final String prompt) {
        System.out.println(prompt);
        try {
            var line = Console.readLine(prompt);
            return Optional.of(LocalTime.parse(line, fmtLocalTime));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
