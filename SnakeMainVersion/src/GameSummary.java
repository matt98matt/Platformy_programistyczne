import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GameSummary extends JDialog {
    private JLabel _firstName;
    private JTextField firstnameGamer;
    private JLabel _result;
    private JButton ok;

    public GameSummary() {
        setTitle("GAME OVER");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(270, 160);
        setModal(true);
        setLocation(new Point(310, 230));
        setResizable(false);

        _firstName = new JLabel("Your nick:");
        _firstName.setBounds(45, 20, 65, 20);
        _firstName.setBackground(Color.white);

        firstnameGamer = new JTextField("");
        firstnameGamer.setBounds(115, 20, 100, 20);

        _result = new JLabel(""+View.result);
        _result.setBounds(80, 50, 110, 20);
        _result.setBackground(Color.white);

        ok = new JButton("OK");
        ok.setBounds(110, 85, 50, 30);
        ok.setFocusable(false);
        ok.setMargin(new Insets(1, 1, 1, 1));
        ok.addActionListener(new END());

        View.result = 0;
        Board.result.setText("Result: 0");
        Board.start.setText("Start");

        add(ok);
        add(_firstName);
        add(firstnameGamer);
        add(_result);

        setVisible(true);
    }

    public void saveResult() throws IOException {
     ArrayList<String> players = new ArrayList<String>();
     ArrayList<String> scores = new ArrayList<String>();

        boolean b = true;

        try {
            BufferedReader br = new BufferedReader(new FileReader("file.txt"));
            String tmp = "";

            while ((tmp = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(tmp);

                while (st.hasMoreTokens()) {
                    String temp = st.nextToken();
                    if (temp.equals(";")) {
                        scores.add(st.nextToken());
                    }
                    else if (b) {
                        players.add(temp);
                        b = false;
                    }
                }
                b = true;
            }
            br.close();
        }
        catch (IOException ex) {
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("file.txt"));

            int helper = 0;
            players.add(firstnameGamer.getText());
            scores.add(_result.getText());
            int i = players.size()-1;
            if(players.size() < 10)
                helper = players.size();
            else
                helper = 10;
            for (int j = 0; j < helper; j++) {
                    if(players.size() != 0)
                    bw.write(players.get(i) + " ; " + scores.get(i));
                    --i;

                bw.newLine();
            }

            bw.close();
        }
        catch (IOException ex) {
        }
    }

    private class END implements ActionListener {
        public void actionPerformed(ActionEvent e) {


            if(!(firstnameGamer.getText().equals(""))){
                if (Character.isLetterOrDigit((firstnameGamer.getText()).charAt(0))) {
                    try {
                        saveResult();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    dispose();
                }}
                else {
                    firstnameGamer.setText("Anonymous");
                }
        }
    }
}
