import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Timer;

class Widok extends JPanel implements ActionListener {

    private Image head;
    private Image dot;
    private Image apple;
    private Image cegla;
    private int apple_x;
    private int apple_y;
    private final int WYSOKOSC = 350;
    private final int SZEROKOSC = 380;
    private final int DELAY = 150;
    private ArrayList<Integer> x,y;
    static int dots;
    static Timer timer;
    private boolean left = true;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private int tmp = 0;
    static boolean status = true;
    static int wynik = 0;

    public Widok() {

        addKeyListener(new KAdapter());

        setBackground(Color.black);

        ImageIcon ih = new ImageIcon(this.getClass().getResource("head.jpg"));
        head = ih.getImage();

        ImageIcon ib = new ImageIcon(this.getClass().getResource("body.jpg"));
        dot = ib.getImage();

        ImageIcon ia = new ImageIcon(this.getClass().getResource("apple.jpg"));
        apple = ia.getImage();

        ImageIcon ic = new ImageIcon(this.getClass().getResource("cegla2.jpg"));
        cegla = ic.getImage();

        setFocusable(true);
        initGame();
    }

    public void initGame() {
        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();

        dots = 3;

        for (int i = 0; i < dots; i++) {
            x.add(180 + i*10);
            y.add(180);
        }

        locateApple();

        timer = new Timer(DELAY, this);
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (status) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int i = 0; i < dots; i++) {
                if (i == 0)
                    g.drawImage(head, x.get(i), y.get(i), this);
                else
                    g.drawImage(dot, x.get(i), y.get(i), this);
            }
        }

        for (int i = 0; i <= 390; i += 10) {
            g.drawImage(cegla, i, 0, this);
            g.drawImage(cegla, i, 360, this);
        }

        for (int i = 10; i < 360; i += 10) {
            g.drawImage(cegla, 0, i, this);
            g.drawImage(cegla, 390, i, this);
        }
    }

    public void checkCollision() {

        for(int i=1; i<dots; i++){
            if((x.get(i).equals(x.get(0))) && (y.get(i).equals(y.get(0)))) {
                status = false;
                break;
            }
        }

        if (x.get(0) > SZEROKOSC)
            status = false;

        if (x.get(0) < 10)
            status = false;

        if (y.get(0) > WYSOKOSC)
            status = false;

        if (y.get(0) < 10)
            status = false;
    }

    public void move() {

        for (int i = dots-1; i > 0; i--) {
            x.set(i, x.get(i-1));
            y.set(i, y.get(i-1));
        }

        if (left) {
            tmp = x.get(0);
            x.set(0, tmp-10);
        }
        if (right) {
            tmp = x.get(0);
            x.set(0, tmp+10);
        }
        if (up) {
            tmp = y.get(0);
            y.set(0, tmp-10);
        }
        if (down) {
            tmp = y.get(0);
            y.set(0, tmp+10);
        }

    }

    public void locateApple() {
        boolean bl = true;
        while (bl) {
            int r = (int) (Math.random()*38+1);
            apple_x = r*10;
            r = (int) (Math.random()*35+1);
            apple_y = r*10;

            if ((x.contains(apple_x)) && (y.contains(apple_y)))
                continue;
            else
                bl = false;
        }
    }

    public void checkApple() {
        if ((x.get(0) == apple_x) && (y.get(0) == apple_y)) {
            ++dots;
            wynik += 10;
            Snake.wynik.setText(String.valueOf(wynik));

            x.add(x.get(1));
            y.add(y.get(1));

            locateApple();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (status) {
            checkApple();
            checkCollision();
            move();
            repaint();
        }
        else {
            timer.stop();
            x.clear();
            y.clear();
            dots = 3;

            for (int i = 0; i < dots; i++) {
                x.add(180 + i*10);
                y.add(180);
            }

            status = true;
            left = true;
            right = false;
            up = false;
            down = false;

            Snake.start.setEnabled(true);
            Snake.wyniki.setEnabled(true);

            repaint();

            KoniecGry koniec = new KoniecGry();
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