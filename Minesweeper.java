import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private final int width;
    private final int height;
    private final int[][] board;
    private final boolean[][] mines;
    private final boolean[][] revealed;
    private boolean gameEnded;

    public Minesweeper(int width, int height, int numMines) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
        this.mines = new boolean[width][height];
        this.revealed = new boolean[width][height];
        this.gameEnded = false;

        placeMines(numMines);
        calculateHints();
    }

    private void placeMines(int numMines) {
        Random rand = new Random();
        for (int i = 0; i < numMines; i++) {
            int x, y;
            do {
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            } while (mines[x][y]);
            mines[x][y] = true;
        }
    }

    private void calculateHints() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (mines[x][y]) {
                    continue;
                }
                int numMines = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx, ny = y + dy;
                        if (nx >= 0 && nx < width && ny >= 0 && ny < height && mines[nx][ny]) {
                            numMines++;
                        }
                    }
                }
                board[x][y] = numMines;
            }
        }
    }

    public void reveal(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height || revealed[x][y]) {
            return;
        }
        revealed[x][y] = true;
        if (mines[x][y]) {
            gameEnded = true;
            System.out.println("Game Over! You hit a mine.");
            return;
        }
        if (board[x][y] == 0) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    reveal(x + dx, y + dy);
                }
            }
        }
    }

    public void printBoard() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!revealed[x][y]) {
                    System.out.print(".");
                } else if (mines[x][y]) {
                    System.out.print("*");
                } else {
                    System.out.print(board[x][y]);
                }
            }
            System.out.println();
        }
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public static void main(String[] args) {
        Minesweeper game = new Minesweeper(10, 10, 10);
        Scanner scanner = new Scanner(System.in);
        while (!game.isGameEnded()) {
            game.printBoard();
            System.out.println("Enter X and Y coordinates to reveal (e.g., \"4 5\"): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            game.reveal(x, y);
        }
        scanner.close();
    }
}