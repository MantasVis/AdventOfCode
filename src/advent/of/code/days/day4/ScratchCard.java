package advent.of.code.days.day4;

import java.util.Set;

public class ScratchCard {

    private final int id;
    private final Set<Integer> winningNumbers;
    private final Set<Integer> playingNumbers;
    private int count = 1;

    public ScratchCard(int id, Set<Integer> winningNumbers, Set<Integer> playingNumbers) {
        this.id = id;
        this.winningNumbers = winningNumbers;
        this.playingNumbers = playingNumbers;
    }

    public int getId() {
        return id;
    }

    public Set<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public Set<Integer> getPlayingNumbers() {
        return playingNumbers;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
