import java.util.*;
public class ocean{

   int color;
   int width;
   int xLocation;
   int yLocation;
   public static int oceanNum;

   public ocean(int xLoc, int yLoc, int w){
      xLocation = xLoc;
      yLocation = yLoc;
      color = 3;
      width = w;
      oceanNum++;
   }
   public int getXLocation(){
      return xLocation;
   } 
   public int getYLocation(){
      return yLocation;
   } 
   public int getWidth(){
      return width;
   } 
   
}