package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.BoardIndexConvertor;
import cleancode.minesweeper.tobe.position.CellPosition;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    public static final Scanner SCANNER = new Scanner(System.in);
    private final BoardIndexConvertor boardIndexConvertor = new BoardIndexConvertor();

    @Override
    public String getUserInput() {
        return SCANNER.nextLine();
    }

    @Override
    public CellPosition getCellPositionFromUser() {
        String userInput = SCANNER.nextLine();

        int colIndex = boardIndexConvertor.getSelectedColIndex(userInput);
        int rowIndex = boardIndexConvertor.getSelectedRowIndex(userInput);
        return CellPosition.of(colIndex, rowIndex);
    }
}
