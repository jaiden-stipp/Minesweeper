import java.util.Scanner;

public class Minesweeper {
    private final int[][] board;
    private final boolean[][] revealed;
    private final int numRows;
    private final int numCols;
    private final int numMines;

    public Minesweeper(int numRows, int numCols, int numMines) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numMines = numMines;
        this.board = new int[numRows][numCols];
        this.revealed = new boolean[numRows][numCols];
        initializeBoard();
        placeMines();
        calculateNumbers();
    }

    private void initializeBoard() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                board[i][j] = 0;
                revealed[i][j] = false;
            }
        }
    }

    private void placeMines() {
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int row = (int) (Math.random() * numRows);
            int col = (int) (Math.random() * numCols);
            if (board[row][col] != -1) {
                board[row][col] = -1;
                minesPlaced++;
            }
        }
    }

    private void calculateNumbers() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (board[i][j] != -1) {
                    int count = 0;
                    for (int ii = Math.max(0, i - 1); ii <= Math.min(numRows - 1, i + 1); ii++) {
                        for (int jj = Math.max(0, j - 1); jj <= Math.min(numCols - 1, j + 1); jj++) {
                            if (board[ii][jj] == -1) {
                                count++;
                            }
                        }
                    }
                    board[i][j] = count;
                }
            }
        }
    }

    public void printBoard(boolean showMines) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (revealed[i][j] || showMines && board[i][j] == -1) {
                    System.out.print(board[i][j] == -1 ? "* " : board[i][j] + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
