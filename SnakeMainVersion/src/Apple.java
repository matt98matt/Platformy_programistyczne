import java.util.ArrayList;
import java.util.Date;

public class Apple {
    public int apple_x;
    public int apple_y;
    private static Date dateInit = new Date();
    private static int helper = 0;

    public void generateApple(int widthMAX,int heightMAX,Snake snake, ArrayList<Wall> walls) {
        do {
            int help = (int) (Math.random() * (widthMAX / 10) + 1); // rzutowanie na (int) scina to co po przecinku zakres randoma <0 ; 1)
            apple_x = help * 10;
            if (apple_x >= 690)
                apple_x = 680;
            help = (int) (Math.random() * (heightMAX / 10) + 1);
            apple_y = help * 10;
            if (apple_y >= 590)
                apple_y = 580;
            System.out.println(apple_x + " | " + apple_y);
        } while (AppleCheckCollisionWithHead(snake) || AppleCheckCollisionWithWalls(walls));
    }

    public void killAppleAfterPeriodOfTime(int widthMAX,int heightMAX,ArrayList<Wall> walls,Snake snake){
        Date date = new Date();

        if((date.getTime()/1000 - dateInit.getTime()/1000) != 0)
        {
            if((date.getTime()/1000 - dateInit.getTime()/1000)%10 == 0   && helper == 0)
            {
                helper = 1;
                generateApple(widthMAX,heightMAX,snake,walls);
            }
        }

        if(helper >= 150) {
            helper = 0;
        }
        if(helper != 0 ){
            helper++;
        }
    }

    public void moveApple(Snake snake, ArrayList<Wall> walls) {
        int help = (int) (Math.random()*4+1);
        if(help == 1) //right
        {
            apple_x += 10;
            if(AppleCheckCollisionWithWalls(walls) || apple_x >= 690 || AppleCheckCollisionWithHead(snake))
            {
                apple_x -= 10;
            }
        }
        if(help == 2) //left
        {
            apple_x -= 10;
            if(AppleCheckCollisionWithWalls(walls) || apple_x < 10 || AppleCheckCollisionWithHead(snake))
            {
                apple_x += 10;
            }
        }
        if(help == 3) //up
        {
            apple_y -= 10;
            if(AppleCheckCollisionWithWalls(walls) || apple_y < 10 || AppleCheckCollisionWithHead(snake) )
            {
                apple_y += 10;
            }
        }
        if(help == 4) //down
        {
            apple_y += 10;
            if(AppleCheckCollisionWithWalls(walls) || apple_y >= 590 || AppleCheckCollisionWithHead(snake))
            {
                apple_y -= 10;
            }
        }
    }

    public boolean AppleCheckCollisionWithHead(Snake snake) {
        for(int i=0; i < snake.dots; i++) {
            if(snake.x.get(i).equals(apple_x) && snake.y.get(i).equals(apple_y))
            {
                return true;
            }
        }
        return false;
    }

    public boolean AppleCheckCollisionWithWalls(ArrayList<Wall> walls) {
        for (Wall element : walls) {
            for(int i = 0; i < element.lenght; i++) {
                if(element.x.get(i).equals(apple_x) && element.y.get(i).equals(apple_y))
                    return true;
            }
        }
        return false;
    }

    public int eatApple(Snake snake,int result,int widthMAX,int heightMAX,ArrayList<Wall> walls ) {
        if ((snake.x.get(0) == apple_x) && (snake.y.get(0) == apple_y)) {
            ++snake.dots;
            result += 10;

            snake.x.add(0);
            snake.y.add(0);

            generateApple(widthMAX,heightMAX,snake,walls);
        }
        return result;
    }

}
