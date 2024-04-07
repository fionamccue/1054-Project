import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;


public class Main extends Application
{
   //initialize variables
   FlowPane fp;
   
   Canvas theCanvas = new Canvas(600,600);
   
   GraphicsContext gc;
   DrawableObject thePlayer = new Player(300,300);
   AnimationHandler ah;
   
   double forcex=0;
   double forcey=0;
   
   int cgridy;
   int cgridx;
   int nextGridx;
   int nextGridy;
   Random rand = new Random();
   float xSpot;
   float ySpot;
   boolean works = true;
   int highscore;
   
   Mine origin = new Mine(300,300); //creates a mine for computation but will not be drawn because not added to array list
   ArrayList<Mine> allMines = new ArrayList<Mine>();


   public void start(Stage stage)
   {
      
   
      //create the canvas
      fp = new FlowPane();
      fp.getChildren().add(theCanvas);
      gc = theCanvas.getGraphicsContext2D();
      drawBackground(300,300,gc);
      
      
      
      ah = new AnimationHandler();
      ah.start();
      //add high text score from the file if the game has already ran once
      try
         {
            Scanner scan = new Scanner(new File("highest.txt"));
            int fileNum = scan.nextInt();
            highscore = fileNum;
         }
         catch(FileNotFoundException fnfe)
         {
            highscore = 0; //set the high score to zero if file is not found
         }
      
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      //set action to the key listeners
      fp.setOnKeyPressed(new KeyListenerDown());
      fp.setOnKeyReleased(new KeyListenerUp());
      
      //focus on the screen
      fp.requestFocus();
   }
   
      
   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();
   
   //this piece of code doesn't need to be modified
   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
	  //re-scale player position to make the background move slower. 
      playerx*=.1;
      playery*=.1;
   
	//figuring out the tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      
      int xi = (int) x;
      int yi = (int) y;
      
	  //draw a certain amount of the tiled images
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
      
	  //below repeats with an overlay image
      playerx*=2f;
      playery*=2f;
   
      x = (playerx) / 400;
      y = (playery) / 400;
      
      xi = (int) x;
      yi = (int) y;
      
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   
   
   
   
   public void miniMine(float x,float y)
   {
      
      Mine spot  = new Mine(x*100,y*100); //creates a mine for computation but will not be drawn because not added to array list
      int num = (int)spot.distance(origin)/1000; //find distance from the grid to the origin
      //System.out.println(num);
      for(int i = 0; i<num;i++)
      {
         if(0.3>rand.nextFloat()) //only make 30% of mines
         {
            allMines.add(new Mine((x*100) + rand.nextFloat(100),(y*100)+rand.nextFloat(100))); //create in random position in the grid
         }
      }
   }

   
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         gc.clearRect(0,0,600,600);
         
         //USE THIS CALL ONCE YOU HAVE A PLAYER
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc); 

         
         if(works) //only run if the player has not lost
         {
            thePlayer.act(); //sets action to the key listeners
         
	         //example calls of draw - this should be the player's call for draw
            thePlayer.draw(300,300,gc,true); //all other objects will use false in the parameter.
         }
         
         //get grid spots
         cgridx = ((int)thePlayer.getX())/100;
         cgridy = ((int)thePlayer.getY())/100;
         
         
         //if the grid is in a different spot than the previous time
         if(cgridx != nextGridx || cgridy != nextGridy)
         {
            //upper row
            for(int i=0;i<9;i++)
            {
               miniMine((cgridx-5+i), (cgridy-4));
            }
            //left row
            for(int i = 0;i<9;i++)
            {
               miniMine((cgridx-5), (cgridy-5+i));
            }
            //bottom row
            for(int i=0;i<9;i++)
            {
               miniMine((cgridx-5+i), (cgridy+4));
            }
            //right row
            for(int i = 0;i<9;i++)
            {
               miniMine((cgridx+4), (cgridy-5+i));
            }
         }
        
          for(int i=0;i<allMines.size();i++)
         {
            if(thePlayer.distance(allMines.get(i)) > 800)
            {
               allMines.remove(i);  //get rid of the mine
            }
         } 
         
         //example call of a draw where m is a non-player object. Note that you are passing the player's position in and not m's position.
         
         for( int i=0;i<allMines.size();i++)
         {
            allMines.get(i).draw(thePlayer.getX(),thePlayer.getY(),gc,false); //draw the mines
            if(thePlayer.distance(allMines.get(i))<=24) //if the player and mine are within each other
            {
               works = false; //lose game
            }
         } 
         
         int score = (int)thePlayer.distance(origin);  
         gc.setFill(Color.WHITE);
         gc.fillText("score: " +score, 5,20);
         
         if(score>highscore)
         {
            highscore = score; //high score from file or 0
         }
         try
         {
            FileOutputStream fos = new FileOutputStream("highest.txt", false); //creates a new file called highest.txt for the new input
            PrintWriter pw = new PrintWriter(fos);
            
            pw.println(highscore); //adds to the file
            
            pw.close(); //closes file so the output shows up 
         }
         catch(FileNotFoundException fnfe)
         {     
         }
         gc.fillText("highscore: " + highscore, 5, 50); //prints the high score
         nextGridx = cgridx;
         nextGridy = cgridy; //keeps track of the current grid for the next run
         
         
         
         
      }
   }
   boolean w,a,s,d;
   
   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
         //sets the directions to change force in act
         if (event.getCode() == KeyCode.W) 
         {
            w = true; 
            thePlayer.setterUp(w);     
         }
         if (event.getCode() == KeyCode.A)  
         {
            a = true;
            thePlayer.setterLeft(a);
         }
         if (event.getCode() == KeyCode.S)  
         {
            s= true;
            thePlayer.setterDown(s);
         }
         if (event.getCode() == KeyCode.D)  
         {
            d = true;
            thePlayer.setterRight(d);
         }

      }
   }
   
   public class KeyListenerUp implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
         //sets the directions to false to edit the force in act
         if (event.getCode() == KeyCode.W) 
         {
            w = false; 
            thePlayer.setterUp(w);     
         }
         if (event.getCode() == KeyCode.A)  
         {
            a = false;
            thePlayer.setterLeft(a);
         }
         if (event.getCode() == KeyCode.S)  
         {
            s= false;
            thePlayer.setterDown(s);
         }
         if (event.getCode() == KeyCode.D)  
         {
            d = false;
            thePlayer.setterRight(d);
         }
      }
   }

   public static void main(String[] args)
   {
      launch(args);
   }
}

