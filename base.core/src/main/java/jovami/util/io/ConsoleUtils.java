package jovami.util.io;

import eapli.framework.io.util.Console;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
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
    private static final Logger LOGGER = LogManager.getLogger(ConsoleUtils.class);

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

    public static String readPassword(final String prompt) {
        try {
            java.io.Console console = System.console();
            if (console == null) {
                throw new UnsupportedOperationException("Console input not available.");
            }

            char[] password = console.readPassword(prompt);
            if (password != null) {
                return new String(password);
            }
        } catch (UnsupportedOperationException e) {
            LOGGER.warn("Ignoring but this is really strange that it even happened.", e);
        }

        return "";
    }

    public static File chooseFile(String directoryPath) {
        var chooseFile = new JFileChooser(directoryPath);
        var extension = new FileNameExtensionFilter(null, "jpg", "svg", "jpeg", "png", "gif", "webp");
        chooseFile.setFileFilter(extension);
        chooseFile.showOpenDialog(null);
        return new File(chooseFile.getSelectedFile().getAbsolutePath());
    }
}
