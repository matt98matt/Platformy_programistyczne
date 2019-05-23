import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;

public class View extends JPanel implements ActionListener {

    private Image head;
    private Image dot;
    private Image apple;
    private Image rock;
    private Image dotBlue;
    private int apple_x;
    private int apple_y;
    private final int heightMAX = 600;
    private final int heightMIN = 0;
    private final int widthMIN = 0;
    private final int widthMAX = 700;
    private static int DELAY = 150;
    static Timer timer;
    private boolean left = true;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    static boolean status = true;
    static int result = 0;
    private Snake snake;
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    private boolean clickOnTickTimer = true;
    private static Date dateInit = new Date();
    private static int helper = 0;
    private ImageIcon ib = new ImageIcon(this.getClass().getResource("body.jpg"));
    ImageIcon iBodyBlue = new ImageIcon(this.getClass().getResource("bodyBlue.jpg"));

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
    // inicjalizacja
    public void initGame() {
        snake = new Snake();

        for (int i = 0; i < snake.dots; i++) {
            snake.x.add(250 + i*10);
            snake.y.add(250);
        }
        generateApple();

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


    // metody do sprawdzania,generowania,ruchu
    public void generateApple() {
        while(true) {
            int help = (int) (Math.random()*(widthMAX/10)+1); // rzutowanie na (int) scina to co po przecinku zakres randoma <0 ; 1)
            apple_x = help*10;
            if(apple_x >= 690)
                apple_x = 680;
            help =(int) (Math.random()*(heightMAX/10)+1);
            apple_y = help*10;
            if(apple_y >= 590)
                apple_y = 580;
            System.out.println(apple_x + " | " + apple_y);
            if(!AppleCheckCollisionWithHead() && !AppleCheckCollisionWithWalls())
                break;
        }
    }

    public boolean AppleCheckCollisionWithHead() {
        for(int i=0; i < snake.dots; i++) {
            if(snake.x.get(i).equals(apple_x) && snake.y.get(i).equals(apple_y))
            {
                return true;
            }
        }
        return false;
    }

    public boolean AppleCheckCollisionWithWalls() {
            for (Wall element : this.walls) {
                for(int i = 0; i < element.lenght; i++) {
                    if(element.x.get(i).equals(apple_x) && element.y.get(i).equals(apple_y))
                        return true;
                }
            }
        return false;
    }




    public void moveApple() {
            int help = (int) (Math.random()*4+1);
            int _x = apple_x,_y = apple_y;
            if(help == 1) //right
            {
                apple_x += 10;
                if(AppleCheckCollisionWithWalls() || apple_x >= 690 || AppleCheckCollisionWithHead())
                {
                    apple_x -= 10;
                }
            }
            if(help == 2) //left
            {
                apple_x -= 10;
                if(AppleCheckCollisionWithWalls() || apple_x < 10 || AppleCheckCollisionWithHead())
                {
                    apple_x += 10;
                }
            }
            if(help == 3) //up
            {
                apple_y -= 10;
                if(AppleCheckCollisionWithWalls() || apple_y < 10 || AppleCheckCollisionWithHead() )
                {
                    apple_y += 10;
                }
            }
            if(help == 4) //down
            {
                apple_y += 10;
                if(AppleCheckCollisionWithWalls() || apple_y >= 590 || AppleCheckCollisionWithHead())
                {
                    apple_y -= 10;
                }
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


    public void upSpeed() {
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



    public void killAppleAfterPeriodOfTime(){
        if(!Board.mediumMode.isEnabled() || !Board.hardMode.isEnabled() )
        {
            Date date = new Date();

            if((date.getTime()/1000 - dateInit.getTime()/1000) != 0)
            {
                if((date.getTime()/1000 - dateInit.getTime()/1000)%10 == 0   && helper == 0)
                {
                    helper = 1;
                    generateApple();
                }
            }

            if(helper >= 50) {
                helper = 0;
            }
            if(helper != 0 ){
                helper++;
            }
        }
    }







    public void actionPerformed(ActionEvent e) {
       if(status) {
           upSpeed();
           killAppleAfterPeriodOfTime();
           eatApple();
           status = snake.checkCollision(this.walls,widthMAX,heightMAX);
           snake.moveSnake(left,right,up,down);
           if(!Board.hardMode.isEnabled()) {
               moveApple();
           }
            clickOnTickTimer = true;
           repaint();
       }
       else {
           timer.stop();
           timer.setDelay(DELAY);
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
        if(!Board.hardMode.isEnabled())
        {
            drawWalls(graph);
        }
    }

    public void drawWalls(Graphics graph)
    {
        for (Wall element : this.walls) {

            for(int i=0; i < element.lenght; i++)
            {
                graph.drawImage(rock,element.x.get(i),element.y.get(i),this);
            }
        }
    }

    public void drawSnake(Graphics graph)
    {
        if(result >= 50) {
            dot = iBodyBlue.getImage();
        } else {
            dot = ib.getImage();
        }
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
