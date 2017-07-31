import javax.swing.JFrame;
import java.awt.*;

/**
 * Created by Андрей on 30.07.2017.
 */
public class AppMain {
    public static void main(String[] args) {
        JFrame f=new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        f.getContentPane().add(new UserUI());
        f.setSize(600, 280);
        f.setVisible(true);
    }
}