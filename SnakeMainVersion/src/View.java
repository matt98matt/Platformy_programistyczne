import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class View extends JPanel implements ActionListener {

    private Image head;
    private Image dot;
    private Image apple;
    private Image rock;
    private int apple_x;
    private int apple_y;
    private final int height = 360;
    private final int width = 390;
    private final int DELAY = 150;
    static Timer timer;
    private boolean left = true;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    static boolean status = true;
    static int result = 0;
    private Snake snake;

    public View() {
        addKeyListener(new KAdapter());

        setBackground(Color.black);


        ImageIcon ih = new ImageIcon(this.getClass().getResource("head.jpg"));
        head = ih.getImage();

        ImageIcon ib = new ImageIcon(this.getClass().getResource("body.jpg"));
        dot = ib.getImage();

        ImageIcon ia = new ImageIcon(this.getClass().getResource("apple.jpg"));
        apple = ia.getImage();

        ImageIcon ic = new ImageIcon(this.getClass().getResource("rock.jpg"));
        rock = ic.getImage();

        setFocusable(true);
        initGame();
    }

    public void initGame() {
        snake = new Snake();

        for (int i = 0; i < snake.dots; i++) {
            snake.x.add(180 + i*10);
            snake.y.add(180);
        }

        generateApple();

        timer = new Timer(DELAY, this);
    }

    public void paint(Graphics graph) {
        super.paint(graph);

        if(status) {
            graph.drawImage(apple,apple_x,apple_y,this);

            for(int i = 0; i < snake.dots; i++) {
                if(i == 0)
                    graph.drawImage(head,snake.x.get(i),snake.y.get(i),this);
                else
                    graph.drawImage(dot,snake.x.get(i),snake.y.get(i),this);
            }
        }

        for (int i = 0; i <= width; i += 10) {
            graph.drawImage(rock, i, 0, this);
            graph.drawImage(rock, i, height, this);
        }

        for (int i = 10; i < height; i += 10) {
            graph.drawImage(rock, 0, i, this);
            graph.drawImage(rock, width, i, this);
        }

    }

    public void generateApple() {
        boolean helper = true;
        while(helper) {
            int help = (int) (Math.random()*38+1); // rzutowanie na (int) scina to co po przecinku zakres randoma <0 ; 1)
            apple_x = help * 10;
            help = (int) (Math.random()*35+1);
            apple_y = help * 10;
            System.out.println(apple_x + " | " + apple_y);
            if(!(snake.x.contains(apple_x)) && !(snake.y.contains(apple_y)))
            {
                helper = false;
            }
        }
    }



    public void actionPerformed(ActionEvent e) {
       if(status) {

           repaint();
       }
       else {

           repaint();
       }
    }

    private class KAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
