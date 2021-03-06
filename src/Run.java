import javax.swing.*;

public class Run extends JFrame {
    Settings settings;
    BallPanel ballPanel;

    private Run() {
        this.setBounds(0, 0, 1280, 720);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("fizik");
        this.setLayout(null);
        settings = new Settings(this);
        this.add(settings);
        ballPanel = new BallPanel(this);
        Menu menu = new Menu(ballPanel);
        this.setJMenuBar(menu);
        this.add(ballPanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Run();
    }

    Settings getSettings() {
        return settings;
    }
}
