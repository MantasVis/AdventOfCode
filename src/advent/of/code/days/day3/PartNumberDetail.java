package advent.of.code.days.day3;

import java.util.Objects;

public class PartNumberDetail {

    private final int number;
    private final int lineNumber;
    private final int startingIndex;
    private final int endingIndex;

    public PartNumberDetail(int number, int lineNumber, int startingIndex, int endingIndex) {
        this.number = number;
        this.lineNumber = lineNumber;
        this.startingIndex = startingIndex;
        this.endingIndex = endingIndex;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PartNumberDetail that = (PartNumberDetail) o;
        return number == that.number &&
                lineNumber == that.lineNumber &&
                startingIndex == that.startingIndex &&
                endingIndex == that.endingIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, lineNumber, startingIndex, endingIndex);
    }
}
