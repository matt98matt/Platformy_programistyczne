import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class ViewTenLastResults extends JDialog {
    private JTextArea field;
    private String text = "Last results from game" + "\n";
    private JButton ok;

    public ViewTenLastResults() {
        setTitle("Results");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(330, 280);
        setResizable(false);
        setModal(true);
        setLocation(new Point(310, 200));

        field = new JTextArea();
        field.setBounds(70, 15, 200, 180);
        field.setEditable(false);
        field.setBackground(new Color(238,238,238));
        field.setFont(new Font("Verdana", Font.BOLD, 12));

        ok = new JButton("OK");
        ok.setBounds(115, 210, 50, 30);
        ok.setFocusable(false);
        ok.setMargin(new Insets(1,1,1,1));
        ok.addActionListener(new Close());

        showResults();

        add(field);
        add(ok);

        setVisible(true);
    }

    public void showResults() {
        try {

            FileReader fr = new FileReader("file.txt");
            BufferedReader br = new BufferedReader(fr);

            String tmp = "";
            int i = 1;
            while ((tmp = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(tmp);
                text += String.valueOf(i) + ". ";

                while (st.hasMoreTokens()) {
                    String temp = st.nextToken();
                    if (temp.equals(";"))
                        text += " -  " + st.nextToken() + "\n";
                    else
                        text += temp + " ";
                }
                ++i;
            }

            br.close();
        }
        catch(FileNotFoundException ex) {
            field.setText("Didnt find file !!!");
        }
        catch (IOException ex) {
            field.setText("Error read file");
        }

        field.setText(text);
    }

    private class Close implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
