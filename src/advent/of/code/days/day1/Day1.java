package advent.of.code.days.day1;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import advent.of.code.days.Day;
import advent.of.code.util.AdventUtils;

public class Day1 implements Day {

    private static final String INPUT = "resources/day1/input.txt";
    private static final Map<String, String> STRING_NUMBER_MAP = Map.of("one", "1", "two", "2", "three", "3", "four", "4", "five", "5", "six", "6", "seven", "7", "eight", "8", "nine", "9");
    private static final Pattern FIRST_NUMERICAL_DIGIT_PATTERN = Pattern.compile("\\d");
    private static final Pattern LAST_NUMERICAL_DIGIT_PATTERN = Pattern.compile("\\d(?!\\d)");

    @Override
    public void run() {
        List<String> lines = AdventUtils.readFile(INPUT);

        int originalSumOfValues = calculateSumOfCalibrationValues(lines, this::extractCalibrationValueFromDigits);
        int newSumOfValues = calculateSumOfCalibrationValues(lines, this::extractCalibrationValueFromDigitsAndWords);

        System.out.println(AdventUtils.formatSectionHeader("DAY 1"));
        System.out.println("Sum of original calibration values = " + originalSumOfValues);
        System.out.println("Sum of new calibration values = " + newSumOfValues);
        System.out.println(AdventUtils.SECTION_FOOTER);
        System.out.println();
    }

    private int calculateSumOfCalibrationValues(List<String> lines, ToIntFunction<String> lineProcessor) {
        return lines.stream()
                .mapToInt(lineProcessor)
                .sum();
    }

    private int extractCalibrationValueFromDigits(String line) {
        String firstDigit = findFirstNumericDigit(line);
        String lastDigit = findLastNumericDigit(line);

        return Integer.parseInt(firstDigit + lastDigit);
    }

    private int extractCalibrationValueFromDigitsAndWords(String line) {
        String firstDigit = findAnyFirstNumber(line);
        String lastDigit = findAnyLastNumber(line);

        return Integer.parseInt(firstDigit + lastDigit);
    }

    private String findFirstNumericDigit(String line) {
        Matcher matcher = FIRST_NUMERICAL_DIGIT_PATTERN.matcher(line);
        return matcher.find() ? String.valueOf(matcher.group()) : "0";
    }

    private String findLastNumericDigit(String line) {
        Matcher matcher = LAST_NUMERICAL_DIGIT_PATTERN.matcher(line);
        String lastDigit = "0";

        while (matcher.find()) {
            lastDigit = matcher.group();
        }
        return lastDigit;
    }

    private String findAnyFirstNumber(String line) {
        String firstNumericDigit = findFirstNumericDigit(line);
        String substringBeforeFirstDigit = line.substring(0, line.indexOf(firstNumericDigit));
        Optional<String> potentialStringNumber = extractFirstStringNumber(substringBeforeFirstDigit);

        return potentialStringNumber.orElse(firstNumericDigit);
    }

    private String findAnyLastNumber(String line) {
        String lastNumericDigit = findLastNumericDigit(line);
        String substring = line.substring(line.lastIndexOf(lastNumericDigit));
        Optional<String> potentialStringNumber = extractLastStringNumber(substring);

        return potentialStringNumber.orElse(lastNumericDigit);
    }

    private Optional<String> extractFirstStringNumber(String input) {
        return IntStream.range(0, input.length())
                .boxed()
                .flatMap(i -> STRING_NUMBER_MAP.entrySet().stream()
                        .filter(entry -> input.startsWith(entry.getKey(), i))
                        .map(Map.Entry::getValue))
                .findFirst();
    }

    private Optional<String> extractLastStringNumber(String input) {
        return STRING_NUMBER_MAP.entrySet().stream()
                .flatMap(entry -> {
                    int index = input.lastIndexOf(entry.getKey());
                    return (index != -1) ? Stream.of(new AbstractMap.SimpleEntry<>(index, entry.getValue())) : Stream.empty();
                })
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue);
    }
}
