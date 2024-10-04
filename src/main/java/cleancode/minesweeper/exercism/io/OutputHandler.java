package cleancode.minesweeper.exercism.io;

import cleancode.minesweeper.exercism.GameBoard;
import cleancode.minesweeper.exercism.GameException;

public interface OutputHandler {


    void showGameStartComments();

    void showBoard(GameBoard board);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();

    void showCommentForUserAction();

    void showExceptionMessage(GameException e);

    void showSimpleMessage(String message);

}
