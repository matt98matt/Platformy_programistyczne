import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.Random;
import javax.swing.Timer;

class MainWindow extends JFrame {
    private JPanel panelMain;
    private JButton randomButton;

    static  double x2;
    static  double y2;
    static  int flag;

    public MainWindow() {
        super("Window");



        panelMain.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        randomButton.setBounds(screenSize.width/2,screenSize.height/2,150,40);
        randomButton.addActionListener(  e -> {


            Random generator = new Random();

            double x1 = generator.nextInt(screenSize.width-150);
            double y1 = generator.nextInt(screenSize.height-40);
            x2 = randomButton.getX();
            y2 = randomButton.getY();
            double x3 = (x1-x2)/100;
            double y3 = (y1-y2)/100;
            Timer timer = new Timer(10, new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                flag++;

                Point point = new Point();
                x2 += x3;
                y2 += y3;
                point.setLocation(x2, y2);
                randomButton.setLocation(point);
                System.out.println(flag);

            }});
                timer.start();

                Timer timerStop = new Timer(10, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                   if(timer.isRunning()){
                       if(flag == 100){
                           timer.stop();
                           flag = 0;
                           System.out.println(x1);
                           System.out.println(x2);
                       }
                   }
                    }
                });
                timerStop.start();
        });

       // timer = new Timer(50, new ActionListener() {
           // @Override
           // public void actionPerformed(ActionEvent e) {

         //   }
        //})

        panelMain.add(randomButton);
        setContentPane(panelMain);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setSize(screenSize.width,screenSize.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
