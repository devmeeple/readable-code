package cleancode.minesweeper.exercism.io;

import cleancode.minesweeper.exercism.GameBoard;
import cleancode.minesweeper.exercism.GameException;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler {

    /**
     * 게임 시작 메시지를 출력한다.
     */
    @Override
    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * 게임 보드를 출력한다.
     */
    @Override
    public void showBoard(GameBoard board) {
        String alphabets = generateColAlphabets(board);

        System.out.println("    " + alphabets);
        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < board.getColSize(); col++) {
                System.out.print(board.getSign(row, col) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 게임판 출력에 사용하는 알파벳을 만든다.
     *
     * @param board 게임판
     * @return 만들어진 알파벳을 반환한다.
     */
    private String generateColAlphabets(GameBoard board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())
                .mapToObj(index -> (char) ('a' + index))
                .map(Object::toString)
                .toList();
        return String.join(" ", alphabets);
    }

    @Override
    public void showGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    @Override
    public void showGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void showCommentForSelectingCell() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    @Override
    public void showCommentForUserAction() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showExceptionMessage(GameException e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void showSimpleMessage(String message) {
        System.out.println(message);
    }

}
