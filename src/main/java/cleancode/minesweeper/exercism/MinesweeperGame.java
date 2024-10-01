package cleancode.minesweeper.exercism;

import java.util.Random;
import java.util.Scanner;

/**
 * MinesweeperGame 클래스는 지뢰 찾기 게임을 구현한다.
 * 8x10 크기의 보드에서 진행되며, 10개의 지뢰가 무작위로 배치된다.
 * 플레이어는 셀을 선택하고 열거나 깃발을 꽂을 수 있다.
 */
public class MinesweeperGame {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;

    private static final String[][] BOARD = new String[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final Integer[][] NEARBY_LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final boolean[][] LAND_MINES = new boolean[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final int LAND_MINE_COUNT = 10;

    public static final String FLAG_SIGN = "⚑";
    public static final String LANG_MINE_SIGN = "☼";
    public static final String CLOSED_CELL_SIGN = "□";
    public static final String OPENED_CELL_SIGN = "■";

    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    /**
     * 게임의 메인 메서드.
     * 게임을 초기화하고, 게임을 진행한다. 사용자에게 좌표를 입력받는다.
     *
     * @param args 사용하지 않음
     */
    public static void main(String[] args) {
        showGameStartComments();
        Scanner scanner = new Scanner(System.in);

        initializeGame();

        while (true) {
            showBoard();
            if (doesUserWinTheGame()) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            if (doesUserLoseTheGame()) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }

            String cellInput = getCellInputFromUser(scanner);
            String userActionInput = getUserActionInputFromUser(scanner);

            actOnCell(cellInput, userActionInput);
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
    private static void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex] = FLAG_SIGN;
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex] = LANG_MINE_SIGN;
                changeGameStatusToLose();
                return;
            }
            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        System.out.println("잘못된 번호를 선택하셨습니다.");

    }

    /**
     * 게임에서 패배한다.
     */
    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return LAND_MINES[selectedRowIndex][selectedColIndex];
    }

    /**
     * 사용자가 선택한 입력이 셀 열기인가요?
     *
     * @param userActionInput
     * @return 입력이 1번이 맞으면 true, 아니라면 false
     */
    private static boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    /**
     * 사용자가 선택한 입력이 깃발 꽂기인가요?
     *
     * @param userActionInput
     * @return 입력이 2번이 맞으면 true, 아니라면 false
     */
    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private static int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    private static String getUserActionInputFromUser(Scanner scanner) {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return scanner.nextLine();
    }

    private static String getCellInputFromUser(Scanner scanner) {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return scanner.nextLine();
    }

    /**
     * 게임에서 패배한다.
     *
     * @return -1 패배
     */
    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    /**
     * 게임에서 승리한다.
     *
     * @return 1 승리
     */
    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    /**
     * 게임 종료조건을 확인한다.
     */
    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllCellOpened();
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }

    /**
     * 게임에서 승리한다.
     */
    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    /**
     * 지뢰가 아닌 모든 셀이 열렸는지 확인한다.
     *
     * @return 지뢰가 아닌 셀이 모두 열렸으면 true, 그렇지 않으면 false
     */
    private static boolean isAllCellOpened() {
        boolean isAllOpened = true;
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                if (BOARD[row][col].equals(CLOSED_CELL_SIGN)) {
                    isAllOpened = false;
                }
            }
        }
        return isAllOpened;
    }

    /**
     * 행 문자(1-8)를 배열 인덱스로 변환한다.
     *
     * @param cellInputRow 행을 나타낸다. (예: '1'은 첫 번째 행)
     * @return 행의 0 기반 인덱스
     */
    private static int convertRowFrom(char cellInputRow) {
        return Character.getNumericValue(cellInputRow) - 1;
    }

    /**
     * 열 문자(a-j)를 배열 인덱스로 변환한다.
     *
     * @param cellInputCol 열을 나타낸다. (예: 'a'는 첫 번째 열)
     * @return 열의 0 기반 인덱스, 잘못된 입력은 -1을 반환한다.
     */
    private static int convertColFrom(char cellInputCol) {
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
                return -1;
        }
    }

    /**
     * 게임 보드를 출력한다.
     */
    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                System.out.print(BOARD[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 게임을 초기화한다.
     * (1) 모든 셀을 빈 셀로 설정한다.
     * (2) 무작위로 10개의 지뢰를 보드에 배치한다.
     * (3) 셀 주변의 지뢰 개수를 계산한다.
     */
    private static void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = CLOSED_CELL_SIGN;
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            LAND_MINES[row][col] = true;
        }

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                int count = 0;
                if (!isLandMineCell(row, col)) {
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
                    NEARBY_LAND_MINE_COUNTS[row][col] = count;
                    continue;
                }
                NEARBY_LAND_MINE_COUNTS[row][col] = 0;
            }
        }
    }

    /**
     * 게임 시작 메시지를 출력한다.
     */
    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * 지정된 셀을 열고, 필요하면 인접 셀들을 재귀로 열어 확인한다.
     *
     * @param row 열 인덱스
     * @param col 행 인덱스
     */
    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }
        if (!BOARD[row][col].equals(CLOSED_CELL_SIGN)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        if (NEARBY_LAND_MINE_COUNTS[row][col] != 0) {
            BOARD[row][col] = String.valueOf(NEARBY_LAND_MINE_COUNTS[row][col]);
            return;
        } else {
            BOARD[row][col] = OPENED_CELL_SIGN;
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
