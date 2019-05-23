import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class View extends JPanel implements ActionListener {

    private Image head;
    private Image dot;
    private Image apple;
    private Image rock;
    private ImageIcon ib = new ImageIcon(this.getClass().getResource("body.jpg"));
    private ImageIcon iBodyBlue = new ImageIcon(this.getClass().getResource("bodyBlue.jpg"));
    private final int heightMAX = 600;
    private final int widthMAX = 700;
    private static int DELAY = 150;
    static boolean status = true;
    static int result = 0;
    private Snake snake;
    private Apple appleObiect;
    private ArrayList<Wall> walls = new ArrayList<>();
    static Timer timer;
    private boolean clickOnTickTimer = true;


    public View() {
        addKeyListener(new KAdapter());
        setBackground(Color.black);

        ImageIcon ih = new ImageIcon(this.getClass().getResource("head.jpg"));
        head = ih.getImage();

        dot = ib.getImage();

        ImageIcon ia = new ImageIcon(this.getClass().getResource("apple.jpg"));
        apple = ia.getImage();

        ImageIcon ic = new ImageIcon(this.getClass().getResource("rock.jpg"));
        rock = ic.getImage();

        setFocusable(true);
        initGame();
    }

    private void initObiects() {
        snake = new Snake();
        appleObiect = new Apple();
        for (int i = 0; i < snake.dots; i++) {
            snake.x.add(250 + i*10);
            snake.y.add(250);
        }
        appleObiect.generateApple(widthMAX,heightMAX,snake,walls);
    }

    private void initGame() {
        initObiects();

        walls.add(new Wall(12,true,90,90));
        walls.add(new Wall(15,false,70, 300));
        walls.add(new Wall(15,false,70, 500));
        walls.add(new Wall(22,true,320,170));
        walls.add(new Wall(15,false,450, 70));
        walls.add(new Wall(7,true,450,70));
        walls.add(new Wall(15,false,450, 200));
        walls.add(new Wall(7,true,590,130));
        walls.add(new Wall(5,true,560,420));
        walls.add(new Wall(15,false,410, 460));

        for (Wall element : this.walls) {
            for(int i = 1; i < element.lenght; i++)
            {
                if(element.upright)
                {
                    element.x.add(element.x.get(0));
                    element.y.add(element.y.get(0)+10*i);
                } else {
                    element.x.add(element.x.get(0)+10*i);
                    element.y.add(element.y.get(0));
                }
            }
        }
        timer = new Timer(DELAY, this);
    }

    private void upSpeed() {
        if(!Board.mediumMode.isEnabled() || !Board.hardMode.isEnabled())
        {
            if(result == 30) {
                timer.setDelay(120);
            }
            else if(result == 60) {
                timer.setDelay(90);
            } else if(result == 90) {
                timer.setDelay(60);
            } else if(result > 200) {
                timer.setDelay(40);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
       if(status) {
           upSpeed();
           result = appleObiect.eatApple(snake,result,widthMAX,heightMAX,walls);
           Board.result.setText("Result: " + result);
           status = snake.checkCollision(this.walls,widthMAX,heightMAX);
           snake.moveSnake();
           if(!Board.hardMode.isEnabled()) {
               appleObiect.killAppleAfterPeriodOfTime(widthMAX,heightMAX,walls,snake);
               appleObiect.moveApple(snake,walls);
           }
            clickOnTickTimer = true;
           repaint();
       }
       else {
           timer.stop();
           timer.setDelay(DELAY);
           initObiects();

           status = true;
           snake.left = true;
           snake.right = false;
           snake.up = false;
           snake.down = false;

           repaint();

           new GameSummary();
       }
    }

    public void paint(Graphics graph) {
        super.paint(graph);

        drawSnake(graph);
        drawBordersMap(graph);
        if(!Board.hardMode.isEnabled())
        {
            drawWalls(graph);
        }
    }

    private void drawWalls(Graphics graph) {
        for (Wall element : this.walls) {

            for(int i=0; i < element.lenght; i++)
            {
                graph.drawImage(rock,element.x.get(i),element.y.get(i),this);
            }
        }
    }

    private void drawSnake(Graphics graph) {
        if(result >= 50) {
            dot = iBodyBlue.getImage();
        } else {
            dot = ib.getImage();
        }
        if(status) {
            graph.drawImage(apple,appleObiect.apple_x,appleObiect.apple_y,this);

            for(int i = 0; i < snake.dots; i++) {
                if(i == 0)
                    graph.drawImage(head,snake.x.get(i),snake.y.get(i),this);
                else
                    graph.drawImage(dot,snake.x.get(i),snake.y.get(i),this);
            }
        }
    }

    private void drawBordersMap(Graphics graph) {
        for (int i = 0; i < widthMAX; i += 10) {
            int heightMIN = 0;
            graph.drawImage(rock, i, heightMIN, this);
            graph.drawImage(rock, i, heightMAX - 10, this);
        }

        for (int i = 10; i < heightMAX; i += 10) {
            int widthMIN = 0;
            graph.drawImage(rock, widthMIN, i, this);
            graph.drawImage(rock, widthMAX - 10, i, this);
        }
    }

    private class KAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if(clickOnTickTimer)
            {
                if ((key == KeyEvent.VK_LEFT) && (!snake.right)) {
                    snake.left = true;
                    snake.up = false;
                    snake.down = false;
                    clickOnTickTimer = false;
                }

                if ((key == KeyEvent.VK_RIGHT) && (!snake.left)) {
                    snake.right = true;
                    snake.up = false;
                    snake.down = false;
                    clickOnTickTimer = false;
                }

                if ((key == KeyEvent.VK_UP) && (!snake.down)) {
                    snake.up = true;
                    snake.right = false;
                    snake.left = false;
                    clickOnTickTimer = false;
                }

                if ((key == KeyEvent.VK_DOWN) && (!snake.up)) {
                    snake.down = true;
                    snake.right = false;
                    snake.left = false;
                    clickOnTickTimer = false;
                }
            }
        }
    }
}
