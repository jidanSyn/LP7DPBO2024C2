import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    private Image backgroundImage;
    private Image birdImage;
    private Image lowerPipeImage;
    private Image upperPipeImage;

    private int frameWidth = 360;
    private int frameHeight = 640;

    // init player attr

    private int playerStartPosX = frameWidth / 8;
    private int playerStartPosY = frameHeight / 2;

    private int playerWidth = 34;
    private int playerHeight = 24;
    private int score;

    Player player;

    int gravity = 1;
    Timer gameLoop;

    private boolean gameOver;

    // pipe attr
    ArrayList<Pipe> pipes;
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    Timer pipesCooldown;
    public FlappyBird(){
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.blue);
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        score = 0;

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                System.out.println("Pipe");
                placePipes();
            }
        });

        pipesCooldown.start();


        gameLoop = new Timer(1000/60, this);
        gameLoop.start();


    }

    public void endGame() {
        gameLoop.stop();
        pipesCooldown.stop();
        System.out.println("Game Over!");
        gameOver = true;

    }

    public void restartGame() {
        score = 0;
        pipes.clear();
        player.setPosY(playerStartPosY);
        gameOver = false;
        gameLoop.start();
        pipesCooldown.start();
    }

    public boolean pipeHit(Player player, Pipe pipe) {
        Rectangle playerHitbox = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
        Rectangle pipeHitbox = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
        return playerHitbox.intersects(pipeHitbox);
    }

    public void showScore(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        graphics.drawString("Score: " + score, 10, 20);
    }

    public void showRestartPrompt(Graphics graphics){
        String gameOverText = "Game Over!";
        String restartText = "Press 'R' to restart.";
        String scoreText = String.format("Score: %d", score);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, 24));

        // Calculate text width and height
        FontMetrics fm = graphics.getFontMetrics();
        int gameOverWidth = fm.stringWidth(gameOverText);
        int restartWidth = fm.stringWidth(restartText);
        int scoreWidth = fm.stringWidth(scoreText);
        int textHeight = fm.getHeight();

        // Calculate position to center the text
        int gameOverX = (frameWidth - gameOverWidth) / 2;
        int restartX = (frameWidth - restartWidth) / 2;
        int scoreX = (frameWidth - scoreWidth) / 2;
        int centerY = (frameHeight - textHeight) / 2;

        // Draw "Game Over!" at the calculated position
        graphics.drawString(gameOverText, gameOverX, centerY - textHeight);

        // Draw final score
        graphics.drawString(scoreText, scoreX, centerY + textHeight );

        // Draw "Press 'R' to restart." at the calculated position
        graphics.drawString(restartText, restartX, centerY + textHeight * 3);

    }

    public void placePipes() {
        int randomPosY = (int)(pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight/4;
        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);

        if (gameOver) {
            showRestartPrompt(graphics);
        } else {
            showScore(graphics);
        }

    }
    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);

        graphics.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            graphics.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);

        }

    }

    public void move() {
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());


        }

        for (int i = pipes.size() - 1; i >= 0; i--) {
            Pipe pipe = pipes.get(i);
            if (pipe.getPosX() + pipe.getWidth() < 0) {
                pipes.remove(i);
            }
        }

        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);

//            System.out.print("Pipe status: ");
//            System.out.println(pipe.isPassed());
//
//            System.out.print("Score: ");
//            System.out.println(score);


            if(pipeHit(player, pipe)) {
                endGame();
                return;
            } else if (!pipe.isPassed() && (playerStartPosX > (pipe.getPosX() + pipe.getWidth()) )  ) {
                pipe.setPassed(true);
//                score++;    // this counts 2 because upper and lower pipe...
                if(i > 0) {
                    int lastPipeX = pipes.get(i-1).getPosX();
                    if(lastPipeX == pipe.getPosX()) score++;
                }
            }

        }

        if (player.getPosY() >= frameHeight) {
            endGame();
        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setVelocityY(-15);
        }

        if (gameOver && keyEvent.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
