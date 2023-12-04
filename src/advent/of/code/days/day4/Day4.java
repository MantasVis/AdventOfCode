package advent.of.code.days.day4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import advent.of.code.days.Day;
import advent.of.code.util.AdventUtils;

public class Day4 implements Day {

    private static final String INPUT = "resources/day4/input.txt";

    @Override
    public void run() {
        List<String> scratchCardLines = AdventUtils.readFile(INPUT);

        int calculateTotalPointsFromScratchCards = calculateTotalPointsFromScratchCards(scratchCardLines);
        long calculateTotalCountOfWonScratchCards = calculateTotalNumberOfWonScratchCards(scratchCardLines);

        System.out.println(AdventUtils.formatSectionHeader("DAY 4"));
        System.out.println("Sum of scratchcard points = " + calculateTotalPointsFromScratchCards);
        System.out.println("Total won scratchcards = " + calculateTotalCountOfWonScratchCards);
        System.out.println(AdventUtils.SECTION_FOOTER);
    }

    private int calculateTotalPointsFromScratchCards(List<String> scratchCardLines) {
        return scratchCardLines.stream()
                .map(this::parseScratchCard)
                .mapToInt(this::calculatePointsForScratchCard)
                .sum();
    }

    private ScratchCard parseScratchCard(String scratchardLine) {
        String[] cardIdAndNumbers = scratchardLine.split(":");
        int cardNumber = parseScratchCardId(cardIdAndNumbers[0]);

        return Arrays.stream(cardIdAndNumbers[1].split("\\|"))
                .map(this::parseAllNumbersFromString)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        numbers -> new ScratchCard(cardNumber, numbers.get(0), numbers.get(1))
                ));
    }

    private int parseScratchCardId(String cardNumber) {
        return AdventUtils.getNumberFromString(cardNumber);
    }

    private Set<Integer> parseAllNumbersFromString(String cardNumber) {
        return Pattern.compile("\\s+")
                .splitAsStream(cardNumber.trim())
                .filter(str -> !str.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    private int calculatePointsForScratchCard(ScratchCard scratchCard) {
        int numberOfMatches = countMatchingNumbers(scratchCard);

        return numberOfMatches == 0 ? 0 : (int) Math.pow(2, numberOfMatches - 1);
    }

    private long calculateTotalNumberOfWonScratchCards(List<String> scratchCardLines) {
        Map<Integer, ScratchCard> scratchCardMap = scratchCardLines.stream()
                .map(this::parseScratchCard)
                .collect(Collectors.toMap(ScratchCard::getId, Function.identity()));

        scratchCardMap.values().forEach(scratchCard -> accumulateWonScratchCards(scratchCard, scratchCardMap));

        return scratchCardMap.values().stream()
                .mapToInt(ScratchCard::getCount)
                .sum();
    }

    private void accumulateWonScratchCards(ScratchCard currentScratchCard, Map<Integer, ScratchCard> scratchCardMap) {
        int numberOfMatches = countMatchingNumbers(currentScratchCard);

        IntStream.rangeClosed(1, numberOfMatches)
                .mapToObj(match -> scratchCardMap.get(currentScratchCard.getId() + match))
                .filter(Objects::nonNull)
                .forEach(wonScratchCard -> wonScratchCard.setCount(wonScratchCard.getCount() + currentScratchCard.getCount()));
    }

    private int countMatchingNumbers(ScratchCard currentScratchCard) {
        return (int) currentScratchCard.getWinningNumbers().stream()
                .filter(currentScratchCard.getPlayingNumbers()::contains)
                .count();
    }
}
