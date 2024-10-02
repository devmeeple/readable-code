package cleancode.minesweeper.exercism;

public class GameException extends RuntimeException {
    public GameException(String message) {
        super(message);
    }
}
