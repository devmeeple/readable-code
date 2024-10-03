package cleancode.minesweeper.exercism.gamelevel;

public class VeryBeginner implements GameLevel {

    @Override
    public int getRowSize() {
        return 4;
    }

    @Override
    public int getColSize() {
        return 5;
    }

    @Override
    public int getLaneMineCount() {
        return 2;
    }
}
