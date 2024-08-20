import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    //Tile Class To Hold Attributes 
    private class Tile{ 
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    //Board Variables 
    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    
    //Board Objects
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    Tile food; 
    
    Random random; 

    //Game Logic 
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOverWin;
    boolean gameOverLose;

    //Constructor
    SnakeGame(int boardHeight, int boardWidth){
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        velocityX = 0;
        velocityY = 0;
        
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();
        food = new Tile(10,10);
        random = new Random();
        
        placeFood();

        gameLoop = new Timer(100,this);
        gameLoop.start();

    }

    //Methods
    public void move(){
        //eat food 
        if (collison(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake Body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if(i==0){ 
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        
        //Snake Head        
        snakeHead.x += velocityX;
        snakeHead.y += velocityY; 

        //Game Over Lose Conditions 
        for(int i = 0; i < snakeBody.size(); i++){ 
            Tile snakePart = snakeBody.get(i);

            if(collison(snakeHead, snakePart)){
                 gameOverLose = true;
            }
        }
            if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
                snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight){
                gameOverLose = true;
            }

        //Game Over Win Conditions
        if(snakeBody.size() == tileSize*tileSize){ 
            gameOverWin = true;
        }
    }

    public boolean collison(Tile t1, Tile t2){ 
        return t1.x == t2.x && t1.y == t2.y;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){ 
        // //grid lines 
        // //(x1, y1, x2, y2)
        // for (int i = 0; i < boardWidth/tileSize; i++) {
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);   
        // }

        //food 
        g.setColor(Color.red);
        // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize); //solid color 
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        //Snake Head
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize); //solid color 
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true); 
        
        //Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        
        }
        
        //score 
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOverLose){  //Game Over Lose Screen
            g.setColor(Color.red);
            g.drawString("GAME OVER", tileSize - 16, tileSize);
            g.drawString("Score : " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize + g.getFontMetrics().getHeight());
            
        }else if (gameOverWin){ //Game Over Win Screen
            g.setColor(Color.GREEN);
            g.drawString("WINNER WINNER WINNER ", tileSize - 16, tileSize);
            g.drawString("Score : " + String.valueOf(snakeBody.size()) + " Great Job! ", tileSize - 16, tileSize + g.getFontMetrics().getHeight());
        }
        else{ 
            g.drawString("Score: " +  String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } 
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize); //600 divided by 25 = 24 * (0-24)
        food.y = random.nextInt(boardHeight/tileSize);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // this is called over and over again due to lines 51-52
       
       move(); // moves over and over again. Velocity is set to null on game start so when the player inputs a direction the game will then start
       repaint(); //calls draw over and over again

       if (gameOverLose){ 
        gameLoop.stop();
       }
       else if(gameOverWin){ 
        gameLoop.stop();
       }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
             case KeyEvent.VK_UP, KeyEvent.VK_W -> {
                velocityX = 0;
                velocityY = -1; // going up bc negative
             }
             case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                velocityX = 0;
                velocityY = 1; // going down bc positive
             }
             case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                velocityX = 1; // going right bc positive
                velocityY = 0;
             }
             case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                velocityX = -1; // going left bc negative
                velocityY = 0;
             }
             case KeyEvent.VK_SPACE -> { 
                checkGameOver();
             }
             case KeyEvent.VK_ESCAPE -> { 
                gameOverLose = true;
             }
        }
}

    public void checkGameOver(){
        if(gameOverLose || gameOverWin){ 
            
            //resetting game variables
            snakeBody.clear();
            snakeHead = new Tile(5,5);
            velocityX=0;
            velocityY=0;
            gameOverLose = false;
            gameOverWin = false;

            //start game
            gameLoop.start();
        }
    }
  
    //do not need 
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
