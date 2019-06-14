import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class Menu extends JMenuBar {

    Menu(BallPanel ballPanel) {
        JMenu meno = new JMenu("Menu");
        this.add(meno);
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem exit = new JMenuItem("Exit");
        load.addActionListener(e -> {
            final JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(ballPanel);
            File file = new File(fc.getSelectedFile().getPath());
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(file));
                String st;
                String[] ball;
                ArrayList<Ball> balls = new ArrayList<>();
                while ((st = br.readLine()) != null) {
                    ball = st.split(" ");
                    balls.add(new Ball(Double.parseDouble(ball[0]), Double.parseDouble(ball[1]),
                            Double.parseDouble(ball[2]), Float.parseFloat(ball[4])
                            , Float.parseFloat(ball[5]), Integer.parseInt(ball[3]), ballPanel));
                }
                ballPanel.setBalls(balls);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });
        save.addActionListener(e -> {
            final JFileChooser fc = new JFileChooser();
            fc.showSaveDialog(ballPanel);
            ArrayList<String> lines = new ArrayList<>();
            Ball ball;
            for (int i = 0; i < ballPanel.getBalls().size(); i++) {
                ball = ballPanel.getBalls().get(i);
                lines.add(ball.getCenterX() + " " + ball.getCenterY() + " " +
                        ball.getRadius() + " " + ball.getMass() + " " +
                        ball.getSpeed()[0] + " " + ball.getSpeed()[1]);
            }
            Path file = Paths.get(fc.getSelectedFile().getPath());
            try {
                Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        exit.addActionListener(e -> System.exit(0));
        meno.add(load);
        meno.add(save);
        meno.add(exit);
    }
}
