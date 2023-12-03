package advent.of.code.days.day2;

public record Cube(Colour colour) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cube cube = (Cube) o;
        return colour == cube.colour;
    }
}
