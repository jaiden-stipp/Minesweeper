import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Minesweeper {
    
    int tileSize = 70;
    int numRows = 8;
    int numCols = numRows;
    int boardWidth = numCols * tileSize; 
    int boardHeight = numRows * tileSize;

    JFrame frame = new JFrame("Minesweeper");

    Minesweeper() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }
}
