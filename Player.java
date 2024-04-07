import javafx.scene.paint.*;
import javafx.scene.canvas.*;

//this is an example object
public class Player extends DrawableObject
{
	GraphicsContext gc;
   //takes in its position
   public Player(float x, float y)
   {
      super(x,y);
   }
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      
      gc.setFill(Color.BLACK);
      gc.fillOval(x-14,y-14,27,27);
      gc.setFill(Color.GREEN);
      gc.fillOval(x-13,y-13,25,25);
      gc.setFill(Color.BROWN);
      gc.fillOval(x-10,x-10,19,19);
      gc.setFill(Color.BLUE);
      gc.fillOval(x-4,x-4,7,7);
      
   }
   public void drawScore(int score, GraphicsContext gc)
   {
      gc.setFill(Color.WHITE);
      gc.fillText("Score: " + score, 5,20);
   }
}
