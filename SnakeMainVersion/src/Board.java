import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame {

    static JButton start;
    static JButton exit;
    static JLabel result;

    public Board () {
        super("Snake");
        setSize(505,410);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocation(new Point(240, 120));


        Insets insets = new Insets(1,1,1,1); //margines

        start = new JButton("Start");
        start.setMargin(insets);
        start.setFocusable(false);
        start.setBounds(405, 90, 80, 30);
        start.addActionListener(new Start());

        exit = new JButton("Exit");
        exit.setMargin(insets);
        exit.setFocusable(false);
        exit.setBounds(405, 130, 80, 30);
        exit.addActionListener(new Exit());

        result = new JLabel("0");
        result.setBounds(440, 30, 42, 30);

        View view = new View();
        view.setBounds(0, 0, 400, 410);

        add(view);
        add(start);
        add(exit);
        add(result);

        setVisible(true);
    }

    private class Start implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            start.setEnabled(false);
            View.timer.start();
        }
    }

    private class Exit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           View.timer.stop();
            dispose();
        }
    }




}
