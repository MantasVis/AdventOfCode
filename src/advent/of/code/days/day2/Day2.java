package advent.of.code.days.day2;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import advent.of.code.util.AdventUtils;

public class Day2 {

    private static final String INPUT = "resources/Day2/input.txt";
    private static final Map<Cube, Integer> CONDITIONS = Map.of(new Cube(Colour.RED), 12, new Cube(Colour.GREEN), 13, new Cube(Colour.BLUE), 14);

    public void run() {
        int sumOfPossibleGameIds = calculateTotalPossibleGameIds();
        int productOfMinimumCubeValues = calculateTotalMinimumCubeProduct();

        System.out.println(AdventUtils.formatSectionHeader("DAY 2"));
        System.out.println("Sum of possible game ids = " + sumOfPossibleGameIds);
        System.out.println("Sum of minimum cube product = " + productOfMinimumCubeValues);
        System.out.println(AdventUtils.SECTION_FOOTER);
        System.out.println();
    }

    private int calculateTotalPossibleGameIds() {
        return getPossibleGameIds().stream().reduce(0, Integer::sum);
    }

    private List<Integer> getPossibleGameIds() {
        return parseAllGames().stream()
                .filter(this::gameIsPossible)
                .map(Game::id)
                .toList();
    }

    private List<Game> parseAllGames() {
        return AdventUtils.readFile(INPUT).stream()
                .map(this::parseGameLine)
                .toList();
    }

    private Game parseGameLine(String line) {
        String[] gameComponents = line.split(":");

        if (gameComponents.length != 2) {
            throw new IllegalArgumentException("Malformed game line: " + line);
        }

        int id = parseGameId(gameComponents[0]);
        List<Draw> draws = parseDraws(gameComponents[1]);
        return new Game(id, draws);
    }

    private int parseGameId(String idPart) {
        return AdventUtils.getNumberFromString(idPart);
    }

    private List<Draw> parseDraws(String drawsString) {
        return Arrays.stream(drawsString.split(";"))
                .map(this::parseIndividualDraws)
                .map(Draw::new)
                .toList();
    }

    private Map<Cube, Integer> parseIndividualDraws(String draw) {
        return Arrays.stream(draw.split(","))
                .map(this::parseCubeString)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<Cube, Integer> parseCubeString(String cubeString) {
        int count = AdventUtils.getNumberFromString(cubeString);
        Cube cube = determineCubeType(cubeString);
        return new AbstractMap.SimpleEntry<>(cube, count);
    }

    private int calculateTotalMinimumCubeProduct() {
        return getTotalMinimumCubeValues().stream()
                .mapToInt(gameMinimum -> gameMinimum.values().stream().reduce(1, (a, b) -> a * b))
                .sum();
    }

    private List<Map<Cube, Integer>> getTotalMinimumCubeValues() {
        return parseAllGames().stream()
                .map(this::calculateGameMinimumCubes)
                .toList();
    }

    private Map<Cube, Integer> calculateGameMinimumCubes(Game game) {
        return Arrays.stream(Colour.values())
                .collect(Collectors.toMap(
                        Cube::new,
                        colour -> game.draws().stream()
                                .mapToInt(draw -> findMinCubesPerDraw(colour, draw))
                                .max().orElse(0)
                ));
    }

    private int findMinCubesPerDraw(Colour colour, Draw draw) {
        return draw.cubes().entrySet().stream()
                .filter(entry -> entry.getKey().colour() == colour)
                .mapToInt(Map.Entry::getValue)
                .max()
                .orElse(0);
    }

    private boolean gameIsPossible(Game game) {
        return game.draws().stream()
                .allMatch(this::meetsConditionsForDraw);
    }

    private boolean meetsConditionsForDraw(Draw draw) {
        return draw.cubes().entrySet().stream()
                .allMatch(this::meetsConditions);
    }

    private boolean meetsConditions(Map.Entry<Cube, Integer> cubeEntry) {
        return CONDITIONS.getOrDefault(cubeEntry.getKey(), Integer.MAX_VALUE) >= cubeEntry.getValue();
    }

    private Cube determineCubeType(String cubesString) {
        return Arrays.stream(Colour.values())
                .filter(colour -> cubesString.contains(colour.getColourName()))
                .findFirst()
                .map(Cube::new)
                .orElse(null);
    }
}
