import javax.swing.*;

public class App {
    public static void main(String[] args) {
//        JFrame frame = new JFrame("Flappy Bird");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(360, 640);
//        frame.setLocationRelativeTo(null);
//        frame.setResizable(false);
////        frame.setVisible(true);
//
//        FlappyBird flappyBird = new FlappyBird();
//        frame.add(flappyBird);
//        frame.pack();
//        frame.requestFocus();
//        frame.setVisible(true);
        Menu menu = new Menu();
        menu.setSize(360, 640);
        menu.setLocationRelativeTo(null);
        menu.setResizable(false);
        menu.setContentPane(menu.getMainPanel());
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
