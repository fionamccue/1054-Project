import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import java.util.*;

//this is an example object
public class Mine extends DrawableObject
{
	//initialize variables
   GraphicsContext gc;
   Random rand = new Random();
   float colorChanger;
   float x;
   float y;
   //takes in its position
   public Mine(float x, float y)
   {
      super(x,y);
   }
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      
      this.x =x;
      this.y=y;
      colorChanger = rand.nextFloat(); //changes the colors at a random number
      gc.setFill(Color.MAGENTA.interpolate(Color.CYAN,colorChanger));
      gc.fillOval(x,y,10,10);

   }
}
