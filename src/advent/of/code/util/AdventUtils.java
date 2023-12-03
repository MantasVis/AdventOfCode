package advent.of.code.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdventUtils {

    private AdventUtils() {}

    private static final String SECTION_HEADER = "====================";
    public static final String SECTION_FOOTER = "=============================================";

    public static List<String> readFile(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }

    public static int getNumberFromString(String string) {
        string = string.replaceAll("\\D", "");
        return Integer.parseInt(string);
    }

    public static String formatSectionHeader(String day) {
        return SECTION_HEADER + day + SECTION_HEADER;
    }
}
