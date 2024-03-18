package Minesweeper;
import java.awt.*;
import java.util.*;


public class minesweeper {
    // Square Size and initilaizing variables 
    static final int sqr = 8;
    static int sideLength = 50;
    static int originX = 50;
    static int originY = 50;
    static boolean[][] mineLocations = new boolean[sqr][sqr];
    static boolean[][] cellStates = new boolean[sqr][sqr];
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        DrawingPanel draw = new DrawingPanel(500,500);
        Graphics g = draw.getGraphics();
        createBoard(draw, g);
        
        System.out.println("Enter coordinate on grid: ");
    
        distributeMines(10, g);

    }

    public static void createBoard(DrawingPanel d, Graphics g) {
        // Loop to draw horizontal grid lines
        for(int i = 0; i < sqr+1; i++) {
            /*  Draw a horizontal line starting from the left edge of the grid to the right edge.
            The vertical position of the line  increases with each iteration, effectively drawing each horizontal line one "sideLength" apart. */
            g.drawLine(originX, originY + i * sideLength, originX + sqr * sideLength, originY + i * sideLength);
        }
        for(int i = 0; i < sqr+1; i++) {
            g.drawLine(originX + i * sideLength, originY, originX + i * sideLength, originY + sqr * sideLength);
        }
    }
    public static String checkCell(int x, int y, DrawingPanel d, Graphics g) {
        int xCell = (x - originX) / sideLength;
        int yCell = (y - originY) / sideLength;
        return "("+ xCell + ", " + yCell + ")";
    }
    public static void distributeMines(int numberOfMines, Graphics g) {
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < numberOfMines) {
            int row = rand.nextInt(sqr);
            int col = rand.nextInt(sqr);
            int x = originX + col * sideLength;
            int y = originY + row * sideLength;

            // Only place a mine if there isn't already one there
            if (!mineLocations[row][col]) {
                mineLocations[row][col] = true;
                minesPlaced++;
                g.setColor(Color.BLUE);
                g.fillRect(x, y, sideLength, sideLength);
            }
        }
    }
    public static boolean checkGuess(String cellId, )
    public static void colorCell(String cellId, Graphics g, Color color) {
        if (cellId.length() != 2) {
            System.out.println("Invalid cell format");
            return;
        }

        // Convert from letter/number to row/column
        int col = cellId.charAt(0) - 'a'; // 'a' becomes 0, 'b' becomes 1, etc.
        int row = Character.getNumericValue(cellId.charAt(1)) - 1; // '1' becomes 0, '2' becomes 1, etc.

        if (row < 0 || row >= sqr || col < 0 || col >= sqr) {
            System.out.println("Out of bounds");
            return;
        }

        // Calculate the top-left corner of the cell
        int x = originX + col * sideLength;
        int y = originY + row * sideLength;

        // Color the cell
        g.setColor(color);
        g.fillRect(x, y, sideLength, sideLength);
    }
}