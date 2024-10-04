package cleancode.minesweeper.exercism;

import cleancode.minesweeper.exercism.gamelevel.Beginner;
import cleancode.minesweeper.exercism.gamelevel.GameLevel;
import cleancode.minesweeper.exercism.io.ConsoleInputHandler;
import cleancode.minesweeper.exercism.io.ConsoleOutputHandler;
import cleancode.minesweeper.exercism.io.InputHandler;
import cleancode.minesweeper.exercism.io.OutputHandler;

/**
 * 프로그램 실행 진입점
 */
public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new Beginner();
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        Minesweeper minesweeper = new Minesweeper(gameLevel, inputHandler, outputHandler);
        minesweeper.initialize();
        minesweeper.run();
    }

}
