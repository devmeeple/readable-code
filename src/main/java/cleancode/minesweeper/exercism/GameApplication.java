package cleancode.minesweeper.exercism;

import cleancode.minesweeper.exercism.gamelevel.Beginner;
import cleancode.minesweeper.exercism.gamelevel.GameLevel;

/**
 * 프로그램 실행 진입점
 */
public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new Beginner();

        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.initialize();
        minesweeper.run();
    }

}
