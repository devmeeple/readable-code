package cleancode.minesweeper.exercism;

/**
 * 프로그램 실행 진입점
 */
public class GameApplication {

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.run();
    }

}
