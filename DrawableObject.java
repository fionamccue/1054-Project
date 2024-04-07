import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public abstract class DrawableObject
{
   public DrawableObject(float x, float y)
   {
      this.x = x;
      this.y = y;
   }

   //positions
   private float x;
   private float y;
   
   //initialize variables
   private boolean left;
   private boolean right;
   private boolean up;
   private boolean down;
   private double forcex=0;
   private double forcey=0;
   
   GraphicsContext gc;
   
   //takes the position of the player and calls draw me with appropriate positions
   public void draw(float playerx, float playery, GraphicsContext gc, boolean isPlayer)
   {
      //the 300,300 places the player at 300,300, if you want to change it you will have to modify it here
      
      if(isPlayer)
         drawMe(playerx,playery,gc);
      else
         drawMe(-playerx+300+x,-playery+300+y,gc);
   }
   
   //this method you implement for each object you want to draw. Act as if the thing you want to draw is at x,y.
   //NOTE: DO NOT CALL DRAWME YOURSELF. Let the the "draw" method do it for you. I take care of the math in that method for a reason.
   public abstract void drawMe(float x, float y, GraphicsContext gc);
   
   public void act()
   {
      if(left)
      {
         forcex += -0.1;
      }
      if(right)
      {
         forcex += 0.1;
      }
      //set the force for slowing down
      if(!right && !left)
      {
         if(forcex>0)
         {
            forcex += -0.025;
         }
         if(forcex<0)
         {
            forcex += 0.025;
         }
      }
      if(up)
      {
         forcey += -0.1;
      }
      if(down)
      {
         forcey += 0.1;
      }
      if(!up && !down)
      {
         if(forcey>0)
         {
            forcey += -0.025;
         }
         if(forcey<0)
         {
            forcey += 0.025;
         }
            
      }
      //set the bounds for forces
      if(forcex>5)
      {
         forcex=5;
      }
      if(forcex<-5)
      {
         forcex=-5;
      }
      if(forcey>5)
      {
         forcey=5;
      }
      if(forcey<-5)
      {
         forcey=-5;
      }
     //add the forces to the position
      x+=forcex;
      y+=forcey;
   
   }
   
   //set variables to adjust force
   public void setterRight(boolean scalar_in)
   {
      right = scalar_in;
   }
   public void setterLeft(boolean scalar_in)
   {
      left = scalar_in;
   }
   public void setterUp(boolean scalar_in)
   {
      up = scalar_in;
   }
   public void setterDown(boolean scalar_in)
   {
      down = scalar_in;
   }
   
   
   
   public float getX(){return x;}
   public float getY(){return y;}
   public void setX(float x_){x = x_;}
   public void setY(float y_){y = y_;}
   
   public double distance(DrawableObject other)
   {
      return (Math.sqrt((other.x-x)*(other.x-x) +  (other.y-y)*(other.y-y)   ));
   }
}