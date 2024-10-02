package cleancode.minesweeper.exercism;

import cleancode.minesweeper.exercism.io.ConsoleInputHandler;
import cleancode.minesweeper.exercism.io.ConsoleOutputHandler;

import java.util.Arrays;
import java.util.Random;

/**
 * MinesweeperGame 클래스는 지뢰 찾기 게임을 구현한다.
 * 8x10 크기의 보드에서 진행되며, 10개의 지뢰가 무작위로 배치된다.
 * 플레이어는 셀을 선택하고 열거나 깃발을 꽂을 수 있다.
 */
public class Minesweeper {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    private static final Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final int LAND_MINE_COUNT = 10;

    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    /**
     * 게임의 메인 메서드.
     * 게임을 초기화하고, 게임을 진행한다. 사용자에게 좌표를 입력받는다.
     */
    public void run() {
        consoleOutputHandler.showGameStartComments();
        initializeGame();

        while (true) {
            try {
                consoleOutputHandler.showBoard(BOARD);

                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameLosingComment();
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (GameException e) {
                consoleOutputHandler.printExceptionMessage(e);
            } catch (Exception e) {
                consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
            }

        }
    }

    /**
     * 사용자는 추가로 셀을 열었을 때
     * <p>
     * (1) 깃발을 꽂고 지뢰가 발견되면 게임을 종료한다.
     * (2) 지뢰가 발견되지 않으면 게임을 진행한다.
     *
     * @param cellInput       선택한 셀
     * @param userActionInput 번호
     */
    private void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex].flag();
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex].open();
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        throw new GameException("잘못된 번호를 선택하셨습니다.");
    }

    /**
     * 게임에서 패배한다.
     */
    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return BOARD[selectedRowIndex][selectedColIndex].isLandMine();
    }

    /**
     * 사용자가 선택한 입력이 셀 열기인가요?
     *
     * @param userActionInput 선택 번호
     * @return 입력이 1번이 맞으면 true, 아니라면 false
     */
    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    /**
     * 사용자가 선택한 입력이 깃발 꽂기인가요?
     *
     * @param userActionInput 선택 번호
     * @return 입력이 2번이 맞으면 true, 아니라면 false
     */
    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    private String getUserActionInputFromUser() {
        consoleOutputHandler.printCommentForUserAction();
        return consoleInputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();
        return consoleInputHandler.getUserInput();
    }

    /**
     * 게임에서 패배한다.
     *
     * @return -1 패배
     */
    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    /**
     * 게임에서 승리한다.
     *
     * @return 1 승리
     */
    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    /**
     * 게임 종료조건을 확인한다.
     */
    private void checkIfGameIsOver() {
        boolean isAllChecked = isAllCellChecked();
        if (isAllChecked) {
            changeGameStatusToWin();
        }
    }

    /**
     * 게임에서 승리한다.
     */
    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

    /**
     * 지뢰가 아닌 모든 셀이 열렸는지 확인한다.
     *
     * @return 지뢰가 아닌 셀이 모두 열렸으면 true, 그렇지 않으면 false
     */

    private boolean isAllCellChecked() {
        return Arrays.stream(BOARD)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }

    /**
     * 행 문자(1-8)를 배열 인덱스로 변환한다.
     *
     * @param cellInputRow 행을 나타낸다. (예: '1'은 첫 번째 행)
     * @return 행의 0 기반 인덱스, 잘못된 입력은 예외가 발생한다.
     */
    private int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex >= BOARD_ROW_SIZE) {
            throw new GameException("잘못된 입력입니다");
        }

        return rowIndex;
    }

    /**
     * 열 문자(a-j)를 배열 인덱스로 변환한다.
     *
     * @param cellInputCol 열을 나타낸다. (예: 'a'는 첫 번째 열)
     * @return 열의 0 기반 인덱스, 잘못된 입력은 예외가 발생한다.
     */
    private int convertColFrom(char cellInputCol) {
        switch (cellInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new GameException("잘못된 입력입니다");
        }
    }

    /**
     * 게임을 초기화한다.
     * (1) 모든 셀을 빈 셀로 설정한다.
     * (2) 무작위로 10개의 지뢰를 보드에 배치한다.
     * (3) 셀 주변의 지뢰 개수를 계산한다.
     */
    private void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            BOARD[row][col].turnOnLandMine();
        }

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearbyLaneMines(row, col);
                BOARD[row][col].updateNearbyLandMineCount(count);
            }
        }
    }

    /**
     * 근처 지뢰 개수를 센다.
     *
     * @param row 행
     * @param col 열
     * @return 근처 지뢰 개수를 반환한다.
     */
    private int countNearbyLaneMines(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < BOARD_COL_SIZE && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < BOARD_COL_SIZE && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col + 1 < BOARD_COL_SIZE && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    /**
     * 지정된 셀을 열고, 필요하면 인접 셀들을 재귀로 열어 확인한다.
     *
     * @param row 열 인덱스
     * @param col 행 인덱스
     */
    private void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }
        if (BOARD[row][col].isOpened()) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        BOARD[row][col].open();

        if (BOARD[row][col].hasLandMineCount()) {
            return;
        }

        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }
}
