package Minesweeper;
import java.awt.*;
import java.util.*;


public class minesweeper {
    // Square Size and initilaizing variables 
    static final int sqr = 8;
    static int screenSize = 800;
    static int sideLength = 50;
    static int originX = 25;
    static int originY = 25;
    // Creates a grid of 2d arrays to the specifications of the sqr variable size
    static boolean[][] mineLocations = new boolean[sqr][sqr];
    static boolean[][] cellStates = new boolean[sqr][sqr];
    // If gameState is true, game ends
    static boolean gameState = false;

    static boolean validSquare = true;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        DrawingPanel draw = new DrawingPanel(screenSize, screenSize);
        Graphics g = draw.getGraphics();
        createBoard(draw, g);
        distributeMines(10, g);
        while(!gameState) {
            
            validSquare = true;
            System.out.print("Enter a cell guess: ");
            String cellGuess = sc.nextLine();
            
            if(cellGuess.equals("q") || cellGuess.equals("quit")){
                break;
            };
            gameState = checkGuess(cellGuess, g);
            if(!gameState && validSquare) {
                System.out.println(cellGuess + " is free of mines!");
            }
        }
        System.out.println("Game Over!");
        g.dispose();

    }

    public static void createBoard(DrawingPanel d, Graphics g) {
        // Loop to draw horizontal grid lines
        for(int i = 0; i < sqr+1; i++) {
            /*  Draw a horizontal line starting from the left edge of the grid to the right edge.
            The vertical position of the line  increases with each iteration, effectively drawing each horizontal line one "sideLength" apart. */
            g.drawLine(originX, originY + i * sideLength, originX + sqr * sideLength, originY + i * sideLength);
            
        }
        for(int i = 0; i < sqr+1; i++) {
            // Same thing but for vertical lines
            g.drawLine(originX + i * sideLength, originY, originX + i * sideLength, originY + sqr * sideLength);
            
        }
        //Draws the numbers on the side of the board
        for (int i = 1; i < sqr+1; i++) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.PLAIN, 20));
            g.drawString(Integer.toString(i), originX-25, originY+35 + (i-1) * sideLength);
        }
        
        for (int i = 0; i < sqr; i++) {
            char column = (char) ('a' + i); // Calculate the letter for the column
            int x = originX + i * sideLength + sideLength / 2 - 5; // Center the letter in the column
            g.drawString(String.valueOf(column), x, originY - 10); // Draw the letter above the column
    }
        
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
                // Uncomment to see where mines are placed for testing
                //g.setColor(Color.BLUE);
                //g.fillRect(x, y, sideLength, sideLength);
            }
        }
    }
    public static int countAdjacentMines(int row, int col) {
        int count = 0;
        // Loop through the 3x3 grid centered on (row, col)
        for (int i = Math.max(0, row - 1); i <= Math.min(sqr - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(sqr - 1, col + 1); j++) {
                // Skip the center cell itself
                if (i == row && j == col) {
                    continue;
                }
                // If the cell has a mine, increment the count
                if (mineLocations[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    public static boolean checkGuess(String cellId, Graphics g) {
        if (cellId.length() != 2) {
            System.out.println("Invalid cell format");
            validSquare = false;
            return false;
        }
        int col = cellId.charAt(0) - 'a'; // 'a' becomes 0, 'b' becomes 1, etc.
        int row = Character.getNumericValue(cellId.charAt(1)) - 1; // '1' becomes 0, '2' becomes 1, etc.
        if (row < 0 || row >= sqr || col < 0 || col >= sqr) {
            System.out.println("Out of bounds");
            validSquare = false;
            return false;
        }
        if(cellStates[row][col]){
            System.out.println(cellId + " is already free of mines! Choose another cell.");
            validSquare = false;
            return false;
        }
        int x = originX + col * sideLength;
        int y = originY + row * sideLength;
        
        if(!mineLocations[row][col]) {
            revealAdjacentSquares(row, col, g);
            return false;
        } else {
            g.setColor(Color.RED);
            g.fillRect(x+1, y+1, sideLength-1, sideLength-1);
            return true;
        }
        }
        public static void revealAdjacentSquares(int row, int col, Graphics g) {
            if(row < 0 || row >= sqr || col < 0 || col >= sqr) return; // Check boundaries
            if(cellStates[row][col]) return; // Already revealed
            cellStates[row][col] = true; // Mark as revealed
            int x = originX + col * sideLength;
            int y = originY + row * sideLength;
            int squareCheck = countAdjacentMines(row, col);
            g.setColor(Color.lightGray);
            g.fillRect(x+1, y+1, sideLength-1, sideLength-1);
            cellStates[row][col] = true;
            g.setColor(Color.BLACK);
            if (squareCheck != 0) {
                g.drawString(Integer.toString(squareCheck), x+25, y+25);
            } 
            if(squareCheck > 0) return; // Stop recursion if there are mines around
            
            // Recursively reveal adjacent squares
            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(i == 0 && j == 0) continue; // Skip the square itself
                    
                    revealAdjacentSquares(row + i, col + j, g);
                }
            }
        }
    }
   
