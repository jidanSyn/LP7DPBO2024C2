import javax.swing.*;

public class Menu extends JFrame {
    private JButton startButton;
    private JPanel mainPanel;

    public Menu() {
        startButton.addActionListener(e -> {
            startGame();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private void startGame() {
        // Close the main menu window
        dispose();

        // Open the game window
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack(); // Adjust the frame size to fit the FlappyBird panel
        frame.requestFocus(); // Set focus to the frame to enable key events
        frame.setVisible(true);
    }
}
