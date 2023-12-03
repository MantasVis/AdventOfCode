package advent.of.code.days.day2;

public enum Colour {

    BLUE("blue"),
    RED("red"),
    GREEN("green");

    private final String colourName;

    Colour(String c) {
        this.colourName = c;
    }

    public String getColourName() {
        return colourName;
    }
}
