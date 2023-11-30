import javax.swing.*;
import java.awt.*;
public class app {
    public static void main(String[] args) {
        JFrame frame = new JFrame("app");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Открыть");
        JMenuItem m22 = new JMenuItem("Сохранить как");
        m1.add(m11);
        m1.add(m22);


        JPanel panel = new JPanel();
        JLabel label = new JLabel("Введите текст");
        JTextField tf = new JTextField(10);
        JButton send = new JButton("добавить");
        JButton reset = new JButton("Сброс");
        panel.add(label);
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        JTextArea ta = new JTextArea();

        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);

    }
}
