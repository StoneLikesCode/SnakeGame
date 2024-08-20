import java.awt.Color;
import javax.swing.*;

public class App {

    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeigth = boardWidth;
        Color bgColor = Color.black;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeigth);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); //opens in the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        
    }
}
