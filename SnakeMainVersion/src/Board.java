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
    static JButton easyMode;
    static JButton mediumMode;
    static JButton hardMode;

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

        results = new JButton("Results");
        results.setMargin(insets);
        results.setFocusable(false);
        results.setBounds(550, 10, 80, 30);
        results.addActionListener(new Results());

        easyMode = new JButton("EASY");
        easyMode.setEnabled(false);
        easyMode.setMargin(insets);
        easyMode.setFocusable(false);
        easyMode.setBounds(200, 10, 80, 30);
        easyMode.addActionListener(new Easy());

        mediumMode = new JButton("MEDIUM");
        mediumMode.setMargin(insets);
        mediumMode.setFocusable(false);
        mediumMode.setBounds(300, 10, 80, 30);
        mediumMode.addActionListener(new Medium());

        hardMode = new JButton("HARD");
        hardMode.setMargin(insets);
        hardMode.setFocusable(false);
        hardMode.setBounds(400, 10, 80, 30);
        hardMode.addActionListener(new Hard());

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
        add(easyMode);
        add(mediumMode);
        add(hardMode);

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
             new ViewTenLastResults();
        }
    }

    private class Easy implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!View.timer.isRunning())
            {
                easyMode.setEnabled(false);
                mediumMode.setEnabled(true);
                hardMode.setEnabled(true);
            }
        }
    }

    private class Medium implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!View.timer.isRunning())
            {
                easyMode.setEnabled(true);
                mediumMode.setEnabled(false);
                hardMode.setEnabled(true);
            }
        }
    }

    private class Hard implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!View.timer.isRunning())
            {
                easyMode.setEnabled(true);
                mediumMode.setEnabled(true);
                hardMode.setEnabled(false);
            }
        }
    }
}
