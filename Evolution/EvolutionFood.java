import java.util.*;
public class EvolutionFood{

   int width;
   int xLocation;
   int yLocation;
   int panelMinimum;
   int panelMaximum;
   int number;
   public static int counter =0;
   public static ArrayList<EvolutionFood> foodStock = new ArrayList<EvolutionFood>();
   public static int foodNum;
   public static ArrayList<EvolutionCell> fellowCreatures;
   //ArrayList<ocean> oceans = new ArrayList<>(Arrays.asList(new ocean(100,400,50), new ocean(700,40,80), new ocean(500,200,80)));


   public EvolutionFood(int panelMax,int panelMin,int creatureNum,ArrayList<EvolutionCell> creatures,int foodWidth){
      fellowCreatures = creatures;
      number = counter;
      counter++;
      panelMinimum = panelMin;
      panelMaximum = panelMax;
      width=foodWidth;
      xLocation = (int)(Math.random()*(panelMaximum-panelMinimum+width)+panelMinimum-width);
      yLocation = (int)(Math.random()*(panelMaximum-panelMinimum+width)+panelMinimum-width);
      
      foodStock.add(this);
      boolean independant = false;
      boolean independant2 = false;
      boolean independant3 = false;
      while(!independant||!independant2|!independant3){
         while(!independant){
            independant=true;
            if(creatureNum!=fellowCreatures.size()){
               creatureNum = fellowCreatures.size();
            }
            for(int i=0;i<creatureNum;i++){
               if((fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())>xLocation&&xLocation>fellowCreatures.get(i).getXLocation()){
                  independant=false;
               }
               if(fellowCreatures.get(i).getXLocation()<xLocation&&xLocation<(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())){
                  independant=false;
               }
               if((fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())>yLocation&&yLocation>fellowCreatures.get(i).getYLocation()){
                  independant=false;
               }
               if(fellowCreatures.get(i).getYLocation()<yLocation&&(yLocation+fellowCreatures.get(i).getWidth())<(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())){
                  independant=false;
               }
               if(!independant){
                  xLocation = (int)(Math.random()*(panelMaximum-panelMinimum+width)+panelMinimum-width);
                  yLocation = (int)(Math.random()*(panelMaximum-panelMinimum+width)+panelMinimum-width);
               }
            }
         }
         independant2 = false;
         while(!independant2){
            independant2=true;
            for(int i=0;i<foodNum;i++){
               if((foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())>xLocation&&xLocation>foodStock.get(i).getXLocation()){
                  independant2=false;
               }
               if(foodStock.get(i).getXLocation()<xLocation&&xLocation<(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())){
                  independant2=false;
               }
               if((foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())>yLocation&&yLocation>foodStock.get(i).getYLocation()){
                  independant2=false;
               }
               if(foodStock.get(i).getYLocation()<yLocation&&yLocation<(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())){
                  independant2=false;
               }
               if(!independant2){
                  xLocation = (int)(Math.random()*(panelMaximum-panelMinimum+width)+panelMinimum-width);
                  yLocation = (int)(Math.random()*(panelMaximum-panelMinimum+width)+panelMinimum-width);
               }
            }
         }
         independant3 = false;
         while(!independant3){
            independant3=true;
            // for(int i=0;i<3;i++){ //oceanNum = 3;
//                if((oceans.get(i).getXLocation()+oceans.get(i).getWidth())>xLocation&&xLocation>oceans.get(i).getXLocation()){
//                   independant3=false;
//                }
//                if(oceans.get(i).getXLocation()<xLocation&&xLocation<(oceans.get(i).getXLocation()+oceans.get(i).getWidth())){
//                   independant3=false;
//                }
//                if((oceans.get(i).getYLocation()+oceans.get(i).getWidth())>yLocation&&yLocation>oceans.get(i).getYLocation()){
//                   independant3=false;
//                }
//                if(oceans.get(i).getYLocation()<yLocation&&(yLocation+oceans.get(i).getWidth())<(oceans.get(i).getYLocation()+oceans.get(i).getWidth())){
//                   independant3=false;
//                }
//                if(!independant3){
//                   xLocation = (int)(Math.random()*(panelMaximum-panelMinimum+width)+panelMinimum-width);
//                   yLocation = (int)(Math.random()*(panelMaximum-panelMinimum+width)+panelMinimum-width);
//                   System.out.println("wowweeeeeeeee");
//                }else
//                   System.out.println("wowwee");
//             }
         }
      }
      foodNum++;
   }
   public void removeFood(int x){
      foodStock.remove(x);   
      foodNum--;
   }
   
   public int getFoodNum(){
      return foodNum;
   }

   
   public int getWidth(){
      return width;
   }
   
   public int getNumber(){
      return number;
   }
   
   public void setWidth(int x){
      width = x;
   }

   
   public int getXLocation(){
      return xLocation;
   }   
   
   public void setXLocation(int x){
      xLocation = x;
   }
   
   public int getYLocation(){
      return yLocation;
   }   
   
   public void setYLocation(int x){
      yLocation = x;
   }


}