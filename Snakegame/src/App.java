import javax.swing.*;

public class App {

    public static void main(String[] args) throws Exception {

        int boardWidth = 600;
        int boardHeight = boardWidth;

        //build frame
        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); //opens in the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel inside of frame
        SnakeGame snakeGame = new SnakeGame(boardHeight, boardWidth);
        frame.add(snakeGame);
        frame.pack(); // to ensure full dimensions
        snakeGame.requestFocus();
        
    }
}