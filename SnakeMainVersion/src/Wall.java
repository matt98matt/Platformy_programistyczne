import java.util.ArrayList;

public class Wall {
    public Wall(int _lenght,boolean _upright,int _x, int _y){
        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();
        x.add(_x);
        y.add(_y);
        lenght = _lenght;
        upright = _upright;
    }
    public ArrayList<Integer> x;
    public ArrayList<Integer> y;
    public int lenght;
    public boolean upright; //czy pionowa sciana

}
