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
    private final int heightMAX = 600;
    private final int heightMIN = 0;
    private final int widthMIN = 0;
    private final int widthMAX = 700;
    private final int DELAY = 150;
    static Timer timer;
    private boolean left = true;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    static boolean status = true;
    static int result = 0;
    private Snake snake;
    private boolean clickOnTickTimer = true;

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
    // inicjalizacja
    public void initGame() {
        snake = new Snake();

        for (int i = 0; i < snake.dots; i++) {
            snake.x.add(250 + i*10);
            snake.y.add(250);
        }
        generateApple();

        timer = new Timer(DELAY, this);
    }


    // metody do sprawdzania,generowania,ruchu
    public void generateApple() {
        boolean helper = true;
        while(helper) {
            int help = (int) (Math.random()*(widthMAX/10)+1); // rzutowanie na (int) scina to co po przecinku zakres randoma <0 ; 1)
            apple_x = help*10;
            if(apple_x >= 700)
                apple_x = 680;
            help =(int) (Math.random()*(heightMAX/10)+1);
            apple_y = help*10;
            if(apple_y >= 600)
                apple_y = 580;
            System.out.println(apple_x + " | " + apple_y);
            if(!(snake.x.contains(apple_x)) && !(snake.y.contains(apple_y)))
            {
                helper = false;
            }
        }
    }

    public void checkCollision() {
        for(int i=1; i < snake.dots; i++) {
            if((snake.x.get(i).equals(snake.x.get(0))) && (snake.y.get(i).equals(snake.y.get(0)))) {
                status = false;
                break;
            }
        }

        if(snake.x.get(0) >= widthMAX -10 || snake.x.get(0) < 10)
            status = false;

        if(snake.y.get(0) >= heightMAX -10  || snake.y.get(0) < 10)
            status = false;
    }

    public void move() {
        for(int i = snake.dots - 1; i > 0; i--) {
            snake.x.set(i,snake.x.get(i-1));
            snake.y.set(i, snake.y.get(i-1));
        }
        int tmp;

        if (left) {
            tmp = snake.x.get(0);
            snake.x.set(0, tmp-10);
        }
        if (right) {
            tmp = snake.x.get(0);
            snake.x.set(0, tmp+10);
        }
        if (up) {
            tmp = snake.y.get(0);
            snake.y.set(0, tmp-10);
        }
        if (down) {
            tmp = snake.y.get(0);
            snake.y.set(0, tmp+10);
        }

    }

    public void eatApple() {
        if ((snake.x.get(0) == apple_x) && (snake.y.get(0) == apple_y)) {
            ++snake.dots;
            result += 10;
            Board.result.setText("Result: " + result);

            snake.x.add(0); //tak move go przetworzy i da mu punkty
            snake.y.add(0);

            generateApple();
        }
    }










    public void actionPerformed(ActionEvent e) {
       if(status) {
           eatApple();
            checkCollision();
            move();
            clickOnTickTimer = true;
            System.out.println("hih");
           repaint();
       }
       else {
           System.out.println("hehe  " + snake.x.get(0) + "   " + snake.y.get(0));

           timer.stop();
           snake = new Snake();

           for (int i = 0; i < snake.dots; i++) {
               snake.x.add(250 + i*10);
               snake.y.add(250);
           }
           generateApple();

           status = true;
           left = true;
           right = false;
           up = false;
           down = false;

           repaint();

           GameSummary gameSummary = new GameSummary();
       }
    }

    public void paint(Graphics graph) {
        super.paint(graph);

        drawSnake(graph);
        drawBordersMap(graph);
    }

    public void drawSnake(Graphics graph)
    {
        if(status) {
            graph.drawImage(apple,apple_x,apple_y,this);

            for(int i = 0; i < snake.dots; i++) {
                if(i == 0)
                    graph.drawImage(head,snake.x.get(i),snake.y.get(i),this);
                else
                    graph.drawImage(dot,snake.x.get(i),snake.y.get(i),this);
            }
        }
    }

    public void drawBordersMap(Graphics graph)
    {
        for (int i = 0; i < widthMAX; i += 10) {
            graph.drawImage(rock, i, heightMIN, this);
            graph.drawImage(rock, i, heightMAX - 10, this);
        }

        for (int i = 10; i < heightMAX; i += 10) {
            graph.drawImage(rock, widthMIN, i, this);
            graph.drawImage(rock, widthMAX - 10, i, this);
        }
    }

    private class KAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if(clickOnTickTimer)
            {
                if ((key == KeyEvent.VK_LEFT) && (!right)) {
                    left = true;
                    up = false;
                    down = false;
                    clickOnTickTimer = false;
                }

                if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                    right = true;
                    up = false;
                    down = false;
                    clickOnTickTimer = false;
                }

                if ((key == KeyEvent.VK_UP) && (!down)) {
                    up = true;
                    right = false;
                    left = false;
                    clickOnTickTimer = false;
                }

                if ((key == KeyEvent.VK_DOWN) && (!up)) {
                    down = true;
                    right = false;
                    left = false;
                    clickOnTickTimer = false;
                }
            }
        }
    }
}
