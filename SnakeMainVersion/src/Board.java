import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame {

    static JButton start;
    static JButton exit;
    static JLabel result;
    static JButton reset;
    static JButton results;

    public Board () {
        super("Snake");
        setSize(715,738);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocation(new Point(240, 120));
        setBackground(Color.GRAY);

        Insets insets = new Insets(1,1,1,1); //margines

        start = new JButton("Start");
        start.setMargin(insets);
        start.setFocusable(false);
        start.setBounds(20, 10, 80, 30);
        start.addActionListener(new Start());

        exit = new JButton("Exit");
        exit.setMargin(insets);
        exit.setFocusable(false);
        exit.setBounds(20, 50, 80, 30);
        exit.addActionListener(new Exit());

        results = new JButton("Wyniki");
        results.setMargin(insets);
        results.setFocusable(false);
        results.setBounds(550, 10, 80, 30);
        results.addActionListener(new Results());

        reset = new JButton("Reset");
        reset.setMargin(insets);
        reset.setEnabled(false);
        reset.setFocusable(false);
        reset.setBounds(550, 50, 80, 30);
        reset.addActionListener(new Reset());

        result = new JLabel("Result: 0");
        result.setBounds(110, 10, 80, 30);


        View view = new View();
        view.setBounds(0, 100, 700, 600);

        add(view);
        add(start);
        add(exit);
        add(result);
        add(reset);
        add(results);

        setVisible(true);
    }

    private class Start implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(start.getText() == "Start")
            {
                start.setText("Stop");
                View.timer.start();
            } else {
                start.setText("Start");
                View.timer.stop();
            }
            reset.setEnabled(true);
        }
    }

    private class Exit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           View.timer.stop();
            dispose();
        }
    }

    private class Reset implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            View.status = false;
        }
    }
    private class Results implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ViewTenLastResults ow = new ViewTenLastResults();
        }
    }
}
