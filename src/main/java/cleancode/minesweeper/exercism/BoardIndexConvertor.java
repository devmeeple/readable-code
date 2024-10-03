package cleancode.minesweeper.exercism;

public class BoardIndexConvertor {

    private static final char BASE_CHAR_FOR_COL = 'a';

    public int getSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow, rowSize);
    }

    public int getSelectedColIndex(String cellInput, int colSize) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol, colSize);
    }

    /**
     * 행 문자(1-8)를 배열 인덱스로 변환한다.
     *
     * @param cellInputRow 행을 나타낸다. (예: '1'은 첫 번째 행)
     * @param rowSize
     * @return 행의 0 기반 인덱스, 잘못된 입력은 예외가 발생한다.
     */
    private int convertRowFrom(String cellInputRow, int rowSize) {
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if (rowIndex < 0 || rowIndex >= rowSize) {
            throw new GameException("잘못된 입력입니다");
        }

        return rowIndex;
    }

    /**
     * 열 입력을 배열 인덱스로 변환한다.
     *
     * @param cellInputCol 열을 나타낸다. (예: 'a'는 첫 번째 열)
     * @param colSize
     * @return 열의 0 기반 인덱스, 잘못된 입력은 예외가 발생한다.
     */
    private int convertColFrom(char cellInputCol, int colSize) {
        int colIndex = cellInputCol - BASE_CHAR_FOR_COL;
        if (colIndex < 0 || colIndex >= colSize) {
            throw new GameException("잘못된 입력입니다.");
        }

        return colIndex;
    }
}
