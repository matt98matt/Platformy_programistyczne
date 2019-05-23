import java.util.ArrayList;

public class Snake {
    public Snake(){
        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();
        dots = 3;
    }
    public ArrayList<Integer> x;
    public ArrayList<Integer> y;
    public int dots;
    public boolean left = true;
    public boolean right = false;
    public boolean up = false;
    public boolean down = false;

    public void moveSnake() {
        for(int i = this.dots - 1; i > 0; i--) {
            this.x.set(i,this.x.get(i-1));
            this.y.set(i, this.y.get(i-1));
        }
        int tmp;

        if (left) {
            tmp = this.x.get(0);
            this.x.set(0, tmp-10);
        }
        if (right) {
            tmp = this.x.get(0);
            this.x.set(0, tmp+10);
        }
        if (up) {
            tmp = this.y.get(0);
            this.y.set(0, tmp-10);
        }
        if (down) {
            tmp = this.y.get(0);
            this.y.set(0, tmp+10);
        }
    }

    public boolean checkCollision(ArrayList<Wall> walls,int widthMAX,int heightMAX) {
        for(int i=1; i < this.dots; i++) {
            if((this.x.get(i).equals(this.x.get(0))) && (this.y.get(i).equals(this.y.get(0)))) {
                return false;
            }
        }

        if(!Board.hardMode.isEnabled()) {
            for (Wall element : walls) {
                for(int i = 0; i < element.lenght; i++) {
                    if(this.x.get(0).equals(element.x.get(i)) && this.y.get(0).equals(element.y.get(i))) {
                        return false;
                    }
                }
            }
        }

        if(this.x.get(0) >= widthMAX -10 || this.x.get(0) < 10)
            return false;

        if(this.y.get(0) >= heightMAX -10  || this.y.get(0) < 10)
            return false;

        return true;
    }
}
