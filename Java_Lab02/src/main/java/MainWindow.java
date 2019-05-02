import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.Random;

class MainWindow extends JFrame {
    private JPanel panelMain;
    private JButton randomButton;

    public MainWindow() {
        super("Window");
        panelMain.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        randomButton.setBounds(screenSize.width/2,screenSize.height/2,150,40);
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random generator = new Random();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x1 = generator.nextInt(screenSize.width-150);
                int y1 = generator.nextInt(screenSize.height-40);
                randomButton.setBounds(x1,y1,150,40);
            }
        });

        panelMain.add(randomButton);
        setContentPane(panelMain);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setSize(screenSize.width,screenSize.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
