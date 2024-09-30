package cleancode.minesweeper.exercism;

import java.util.Random;
import java.util.Scanner;

/**
 * MinesweeperGame 클래스는 지뢰 찾기 게임을 구현한다.
 * 8x10 크기의 보드에서 진행되며, 10개의 지뢰가 무작위로 배치된다.
 * 플레이어는 셀을 선택하고 열거나 깃발을 꽂을 수 있다.
 */
public class MinesweeperGame {

    private static String[][] board = new String[8][10];
    private static Integer[][] landMineCounts = new Integer[8][10];
    private static boolean[][] landMines = new boolean[8][10];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    /**
     * 게임의 메인 메서드.
     * 게임 초기화, 진행, 종료 역할을 담당한다.
     *
     * @param args 사용하지 않음
     */
    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Scanner scanner = new Scanner(System.in);

        /**
         * 게임 보드를 초기화한다. 모든 셀을 빈 셀로 설정한다.
         */
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = "□";
            }
        }

        /**
         * 무작위로 10개의 지뢰를 보드에 배치한다.
         */
        for (int i = 0; i < 10; i++) {
            int col = new Random().nextInt(10);
            int row = new Random().nextInt(8);
            landMines[row][col] = true;
        }

        /**
         * 셀 주변의 지뢰 개수를 계산한다.
         */
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                int count = 0;
                if (!landMines[row][col]) {
                    if (row - 1 >= 0 && col - 1 >= 0 && landMines[row - 1][col - 1]) {
                        count++;
                    }
                    if (row - 1 >= 0 && landMines[row - 1][col]) {
                        count++;
                    }
                    if (row - 1 >= 0 && col + 1 < 10 && landMines[row - 1][col + 1]) {
                        count++;
                    }
                    if (col - 1 >= 0 && landMines[row][col - 1]) {
                        count++;
                    }
                    if (col + 1 < 10 && landMines[row][col + 1]) {
                        count++;
                    }
                    if (row + 1 < 8 && col - 1 >= 0 && landMines[row + 1][col - 1]) {
                        count++;
                    }
                    if (row + 1 < 8 && landMines[row + 1][col]) {
                        count++;
                    }
                    if (row + 1 < 8 && col + 1 < 10 && landMines[row + 1][col + 1]) {
                        count++;
                    }
                    landMineCounts[row][col] = count;
                    continue;
                }
                landMineCounts[row][col] = 0;
            }
        }

        while (true) {
            /**
             * 게임 보드를 그린다.
             */
            System.out.println("   a b c d e f g h i j");
            for (int row = 0; row < 8; row++) {
                System.out.printf("%d  ", row + 1);
                for (int col = 0; col < 10; col++) {
                    System.out.print(board[row][col] + " ");
                }
                System.out.println();
            }

            /**
             * 게임의 현재 상태를 확인하고 승리 또는 패배 메시지를 출력한다.
             */
            if (gameStatus == 1) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            if (gameStatus == -1) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }

            /**
             * 사용자에게 좌표를 입력받는다. 셀을 열거나 깃발을 꽂는다.
             * @param scanner 사용자 입력을 받기 위한 Scanner 객체
             */
            System.out.println();
            System.out.println("선택할 좌표를 입력하세요. (예: a1)");
            String cellInput = scanner.nextLine();
            System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
            String userActionInput = scanner.nextLine();
            char cellInputCol = cellInput.charAt(0);
            char cellInputRow = cellInput.charAt(1);
            int selectedColIndex; // ??
            switch (cellInputCol) {
                case 'a':
                    selectedColIndex = 0;
                    break;
                case 'b':
                    selectedColIndex = 1;
                    break;
                case 'c':
                    selectedColIndex = 2;
                    break;
                case 'd':
                    selectedColIndex = 3;
                    break;
                case 'e':
                    selectedColIndex = 4;
                    break;
                case 'f':
                    selectedColIndex = 5;
                    break;
                case 'g':
                    selectedColIndex = 6;
                    break;
                case 'h':
                    selectedColIndex = 7;
                    break;
                case 'i':
                    selectedColIndex = 8;
                    break;
                case 'j':
                    selectedColIndex = 9;
                    break;
                default:
                    selectedColIndex = -1;
                    break;
            }

            int selectedRowIndex = Character.getNumericValue(cellInputRow) - 1;

            /**
             * 사용자가 추가로 셀을 열었을 때
             *
             * (1) 지뢰가 발견되면 게임을 종료한다.
             * (2) 지뢰가 발견되지 않으면 게임을 진행한다.
             */
            if (userActionInput.equals("2")) {
                board[selectedRowIndex][selectedColIndex] = "⚑";
                boolean isAllOpened = true;
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 10; col++) {
                        if (board[row][col].equals("□")) {
                            isAllOpened = false;
                        }
                    }
                }
                if (isAllOpened) {
                    gameStatus = 1;
                }

            } else if (userActionInput.equals("1")) {
                if (landMines[selectedRowIndex][selectedColIndex]) {
                    board[selectedRowIndex][selectedColIndex] = "☼";
                    gameStatus = -1;
                    continue;
                } else {
                    open(selectedRowIndex, selectedColIndex);
                }
                boolean isAllOpened = true;
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 10; col++) {
                        if (board[row][col].equals("□")) {
                            isAllOpened = false;
                        }
                    }
                }
                if (isAllOpened) {
                    gameStatus = 1;
                }
            } else {
                System.out.println("잘못된 번호를 선택하셨습니다.");
            }
        }
    }

    /**
     * 지정된 셀을 열고, 필요하면 인접 셀들을 재귀로 열어 확인한다.
     *
     * @param row 열 인덱스
     * @param col 행 인덱스
     */
    private static void open(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 10) {
            return;
        }
        if (!board[row][col].equals("□")) {
            return;
        }
        if (landMines[row][col]) {
            return;
        }
        if (landMineCounts[row][col] != 0) {
            board[row][col] = String.valueOf(landMineCounts[row][col]);
            return;
        } else {
            board[row][col] = "■";
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
