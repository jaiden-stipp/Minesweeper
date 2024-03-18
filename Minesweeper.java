import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private int rows;
    private int cols;
    private final int MAX_ROWS = 10;
    private final int MAX_COLS = 10;
    private final int MIN_ROWS = 3;
    private final int MIN_COLS = 3;
    private final int MAX_MINES;
    private final char MINE = '*';
    private final char HIDDEN = '-';
    private char[][] board;
    private boolean[][] revealed;
    private Random random;

    public Minesweeper(int rows, int cols) {
        if (rows < MIN_ROWS || rows > MAX_ROWS || cols < MIN_COLS || cols > MAX_COLS) {
            throw new IllegalArgumentException("Rows and columns must be between " + MIN_ROWS + " and " + MAX_ROWS);
        }
        this.rows = rows;
        this.cols = cols;
        this.MAX_MINES = (int) (rows * cols * 0.3); // Adjust mine density as per board size
        board = new char[rows][cols];
        revealed = new boolean[rows][cols];
        random = new Random();
        initBoard();
        placeMines();
        calculateNumbers();
    }

    private void initBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = HIDDEN;
                revealed[i][j] = false;
            }
        }
    }

    private void placeMines() {
        int minesPlaced = 0;
        while (minesPlaced < MAX_MINES) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            if (board[row][col] != MINE) {
                board[row][col] = MINE;
                minesPlaced++;
            }
        }
    }

    private void calculateNumbers() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] != MINE) {
                    int count = 0;
                    for (int row = i - 1; row <= i + 1; row++) {
                        for (int col = j - 1; col <= j + 1; col++) {
                            if (row >= 0 && row < rows && col >= 0 && col < cols && board[row][col] == MINE) {
                                count++;
                            }
                        }
                    }
                    board[i][j] = (char) (count + '0');
                }
            }
        }
    }

    private void printBoard(boolean showMines) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (revealed[i][j] || (showMines && board[i][j] == MINE)) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print(HIDDEN + " ");
                }
            }
            System.out.println();
        }
    }

    private void reveal(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || revealed[row][col]) {
            return;
        }
        revealed[row][col] = true;
        if (board[row][col] == '0') {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    reveal(i, j);
                }
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard(false);
            System.out.print("Enter row and column (e.g., 0 0): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            if (board[row][col] == MINE) {
                System.out.println("Game Over! You hit a mine.");
                printBoard(true);
                break;
            } else {
                reveal(row, col);
                boolean allRevealed = true;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (!revealed[i][j] && board[i][j] != MINE) {
                            allRevealed = false;
                            break;
                        }
                    }
                }
                if (allRevealed) {
                    System.out.println("Congratulations! You won!");
                    printBoard(true);
                    break;
                }
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter number of rows (3-10): ");
        int rows = input.nextInt();
        System.out.print("Enter number of columns (3-10): ");
        int cols = input.nextInt();
        Minesweeper game = new Minesweeper(rows, cols);
        game.play();
        input.close();
    }
}
