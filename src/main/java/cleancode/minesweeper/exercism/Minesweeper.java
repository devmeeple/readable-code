package cleancode.minesweeper.exercism;

import cleancode.minesweeper.exercism.gamelevel.GameLevel;
import cleancode.minesweeper.exercism.io.ConsoleInputHandler;
import cleancode.minesweeper.exercism.io.ConsoleOutputHandler;

/**
 * MinesweeperGame 클래스는 지뢰 찾기 게임을 구현한다.
 * 8x10 크기의 보드에서 진행되며, 10개의 지뢰가 무작위로 배치된다.
 * 플레이어는 셀을 선택하고 열거나 깃발을 꽂을 수 있다.
 */
public class Minesweeper {

    private final GameBoard gameBoard;
    private final BoardIndexConvertor boardIndexConvertor = new BoardIndexConvertor();
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameLevel gameLevel) {
        gameBoard = new GameBoard(gameLevel);
    }

    /**
     * 게임의 메인 메서드.
     * 게임을 초기화하고, 게임을 진행한다. 사용자에게 좌표를 입력받는다.
     */
    public void run() {
        consoleOutputHandler.showGameStartComments();
        gameBoard.initializeGame();

        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);

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
        int selectedColIndex = boardIndexConvertor.getSelectedColIndex(cellInput, gameBoard.getColSize());
        int selectedRowIndex = boardIndexConvertor.getSelectedRowIndex(cellInput, gameBoard.getRowSize());

        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flag(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex, selectedColIndex);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
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
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    /**
     * 게임에서 승리한다.
     */
    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

}
