import java.util.*;
public class EvolutionCell{

   int color;
   int width;
   int xLocation;
   int yLocation;
   int moveDistance;
   double currentMove;
   int panelMinimum;
   int panelMaximum;
   int direction;
   int diffToEat = 10;
   int frameNum=0;
   int frameNum2=0;
   int chanceToEvolve;
   int number;
   int currentState = 0;
   int fearThreshold=1000;
   int huntThreshold=1;
   int closestPrey;
   int closestFood;
   int closestFear;
   int generation;
   int speed = 33;
   int strength = 33;
   int see = 33;
   
   boolean newYear;
   public static int removedNum;
   public static int removedNum2;
   public static int numberGet=0;
   public static ArrayList<EvolutionCell> fellowCreatures = new ArrayList<EvolutionCell>();
   ArrayList<EvolutionCell> heritage = new ArrayList<EvolutionCell>();
   ArrayList<EvolutionCell> creaturesSeen = new ArrayList<EvolutionCell>();
   ArrayList<EvolutionFood> foodSeen = new ArrayList<EvolutionFood>();
   //ArrayList<ocean> oceans = new ArrayList<>(Arrays.asList(new ocean(100,400,50), new ocean(700,40,80), new ocean(500,200,80)));
   public static ArrayList<Integer> removedNumbers = new ArrayList<Integer>();
   public static ArrayList<Integer> removedNumbers2 = new ArrayList<Integer>();
   public static int creatureNum;
   public static int foodNum;
   public static ArrayList<EvolutionFood> foodStock = new ArrayList<EvolutionFood>();


   public EvolutionCell(int panelMin, int panelMax){
      number = numberGet;
      numberGet++;
      generation = 0;
      panelMinimum = panelMin;
      panelMaximum = panelMax;
      color = (int)(Math.random()*10);
      //width = (int)(Math.random()*10)+21;
      moveDistance = (int)(Math.random()*98)+50;
      width = (int)((150-moveDistance)/3);
      //check if the x and y location is within the max width of another creature's x or y
      xLocation = (int)(Math.random()*(panelMaximum-panelMinimum)+panelMinimum);
      yLocation = (int)(Math.random()*(panelMaximum-panelMinimum)+panelMinimum);
      
      fellowCreatures.add(this);
      boolean independant = false;
      while(!independant){
         independant=true;
         for(int i=0;i<creatureNum;i++){
            if(((fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())>xLocation&&xLocation>fellowCreatures.get(i).getXLocation())&&(((fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())>yLocation&&yLocation>fellowCreatures.get(i).getYLocation())||(fellowCreatures.get(i).getYLocation()<yLocation&&yLocation<(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())))){
               independant=false;
            }
            if((fellowCreatures.get(i).getXLocation()<xLocation&&xLocation<(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth()))&&(((fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())>yLocation&&yLocation>fellowCreatures.get(i).getYLocation())||(fellowCreatures.get(i).getYLocation()<yLocation&&yLocation<(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())))){
               independant=false;
            }
         }
         // for(int i=0;i<3;i++){ //oceanNum = 3
//             if(((oceans.get(i).getXLocation()+oceans.get(i).getWidth())>xLocation&&xLocation>oceans.get(i).getXLocation())&&(((oceans.get(i).getYLocation()+oceans.get(i).getWidth())>yLocation&&yLocation>oceans.get(i).getYLocation())||(oceans.get(i).getYLocation()<yLocation&&yLocation<(oceans.get(i).getYLocation()+oceans.get(i).getWidth())))){
//                independant=false;
//             }
//             if((oceans.get(i).getXLocation()<xLocation&&xLocation<(oceans.get(i).getXLocation()+oceans.get(i).getWidth()))&&(((oceans.get(i).getYLocation()+oceans.get(i).getWidth())>yLocation&&yLocation>oceans.get(i).getYLocation())||(oceans.get(i).getYLocation()<yLocation&&yLocation<(oceans.get(i).getYLocation()+oceans.get(i).getWidth())))){
//                independant=false;
//             }
//             if(!independant){
//                xLocation = (int)(Math.random()*(panelMaximum-panelMinimum)+panelMinimum);
//                yLocation = (int)(Math.random()*(panelMaximum-panelMinimum)+panelMinimum);
//             }
//          }

      }
      creatureNum++;
      heritage.add(this);
   }
   
   public EvolutionCell(int panelMin, int panelMax, EvolutionCell x){
      number = numberGet;
      numberGet++;
      generation = x.getGeneration()+1;
      panelMinimum = panelMin;
      panelMaximum = panelMax;
      color = x.getColor();
      //width = (int)(Math.random()*10)+21;
      
      //EVOLUTION HERE:
      
      if(Math.random()<.5){
         fearThreshold = x.getFearThreshold()+100;
      }else{
         fearThreshold = x.getFearThreshold()-100;
      }
      if(fearThreshold>3000){
         fearThreshold = 3000;
      }
      if(fearThreshold<1){
         fearThreshold = 1;
      }
      if(Math.random()<.5){
         huntThreshold = x.getHuntThreshold()+50;
      }else{
         huntThreshold = x.getHuntThreshold()-50;
      }
      if(huntThreshold>1000){
         huntThreshold = 1000;
      }
      if(huntThreshold<1){
         huntThreshold = 1;
      }   
      speed = x.getSpeed();
      strength = x.getStrength();
      see = x.getSee();
      
      
      //TODO: variables shouldnt be able to go below 0
      if(Math.random()<.33){
         strength++;
         if(Math.random()<.5){
            speed--;
         }else{
            see--;
         }     
      }else if(Math.random()<.66){
         speed++;
         if(Math.random()<.5){
            strength--;
         }else{
            see--;
         }
      }else{
         see++;
         if(Math.random()<.5){
            speed--;
         }else{
            strength--;
         }
      }
  
      if((strength+speed+see)!=99){
         System.out.println("Error, creature has evolved beyond its limits");
      }
      //BELOW CAN BE USED FOR TESTING WHOLE GENERATIONS
      //System.out.println("Number: "+number+" Generation: "+generation+" Fear: "+fearThreshold+" Hunt: "+huntThreshold+" Speed: "+speed+" Sight: "+see+" Strength: "+strength);
      //EVOLUTION END
      
      ArrayList<EvolutionCell> oldHeritage = x.getHeritage();
      
      for(int i=0;i<oldHeritage.size();i++){
         heritage.add(oldHeritage.get(i));
      }
      heritage.add(this);
      
      moveDistance = (int)(Math.random()*98)+50;
      
      width = (int)(x.getWidth()-(Math.random()*(diffToEat-1)));
       
      if(Math.random()<.5){
         xLocation = (int)((x.getXLocation()+x.getWidth())+(Math.random()*20));
      }else{
         xLocation = (int)(x.getXLocation()-(Math.random()*20));
      }
      if(Math.random()<.5){
         yLocation = (int)((x.getYLocation()+x.getWidth())+(Math.random()*20));
      }else{
         yLocation = (int)(x.getYLocation()-(Math.random()*20));
      }
      fellowCreatures.add(this);
      boolean independant = false;
      int counter = 0;
      //check if the x and y location is within the max width of another creature's x or y
      while(!independant){
         counter++;
         independant=true;
         for(int i=0;i<creatureNum;i++){
            if(((fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())>xLocation&&xLocation>fellowCreatures.get(i).getXLocation())&&(((fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())>yLocation&&yLocation>fellowCreatures.get(i).getYLocation())||(fellowCreatures.get(i).getYLocation()<yLocation&&yLocation<(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())))){
               independant=false;
            }
            if((fellowCreatures.get(i).getXLocation()<xLocation&&xLocation<(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth()))&&(((fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())>yLocation&&yLocation>fellowCreatures.get(i).getYLocation())||(fellowCreatures.get(i).getYLocation()<yLocation&&yLocation<(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())))){
               independant=false;
            }
            if(xLocation+width>panelMax||xLocation<panelMin||yLocation+width>panelMax||yLocation<panelMin){
               independant=false;
            }
            if(counter>50){
               independant=true;
               System.out.println("Could not find suitable place for object, placing at random coordinates near parent");
            }
            if(!independant){
               if(Math.random()<.5){
                  xLocation = (int)((x.getXLocation()+x.getWidth())+(Math.random()*20));
               }else{
                  xLocation = (int)((x.getXLocation()-width)+(Math.random()*20));
               }
               if(Math.random()<.5){
                  yLocation = (int)((x.getYLocation()+x.getWidth())+(Math.random()*20));
               }else{
                  yLocation = (int)((x.getYLocation()-width)-(Math.random()*20));
               }
            }
         }
      }
      creatureNum++;
   }

   
   public int chooseDir(){
      double randNum = Math.random();
      if(randNum<.25){
         return 1;                          //xLocation-=moveDistance;
      }else if(randNum<.5){
         return 2;                          //xLocation+=moveDistance;
      }else if(randNum<.75){
         return 3;                          //yLocation-=moveDistance;
      }else{
         return 4;                          //yLocation+=moveDistance;
      }
   
   }
   public boolean eatOthers(int y){
      if(width-diffToEat+(int)(strength/4)>=(fellowCreatures.get(y).getWidth()+(int)(fellowCreatures.get(y).getStrength()/4))){
         removedNumbers.add(y);
         fellowCreatures.remove(y);
         removedNum++;
        //System.out.println(removedNum);
         creatureNum--;
         moveDistance-=20;
         if(moveDistance<=50){
            chanceToEvolve++;
            moveDistance=51;
         }
         width = (int)((150-moveDistance)/3); //max width is 33, min is 1
         return true;
      }
      return false;
   }  
   
   public void sight(){
      int vision = 30+see; //norm val is 30
      int leftVision = xLocation-vision;
      int rightVision = xLocation+width+vision; 
      int topVision = yLocation-vision;
      int bottomVision = yLocation+width+vision;
      for(int i=0;i<creaturesSeen.size();i++){
         creaturesSeen.remove(0);
      }
      
      for(int i=0;i<foodSeen.size();i++){
         foodSeen.remove(0);
      }
      for(int i=0;i<creatureNum;i++){
         int tempLeftLoc = fellowCreatures.get(i).getXLocation();
         int tempRightLoc = fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth();
         int tempTopLoc = fellowCreatures.get(i).getYLocation();
         int tempBottomLoc = fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth();
         
         if(((leftVision<tempLeftLoc)&&(tempLeftLoc<rightVision))&&((topVision<tempTopLoc)&&(tempTopLoc<bottomVision))){
            creaturesSeen.add(fellowCreatures.get(i));
         }else if(((leftVision<tempLeftLoc)&&(tempLeftLoc<rightVision))&&((topVision<tempBottomLoc)&&(tempBottomLoc<bottomVision))){
            creaturesSeen.add(fellowCreatures.get(i));
         }else if(((leftVision<tempRightLoc)&&(tempRightLoc<rightVision))&&((topVision<tempTopLoc)&&(tempTopLoc<bottomVision))){
            creaturesSeen.add(fellowCreatures.get(i));
         }else if(((leftVision<tempRightLoc)&&(tempRightLoc<rightVision))&&((topVision<tempRightLoc)&&(tempRightLoc<bottomVision))){
            creaturesSeen.add(fellowCreatures.get(i));
         }
      
      }
      for(int i=0;i<foodNum;i++){
         int tempLeftLoc = foodStock.get(i).getXLocation();
         int tempRightLoc = foodStock.get(i).getXLocation()+foodStock.get(i).getWidth();
         int tempTopLoc = foodStock.get(i).getYLocation();
         int tempBottomLoc = foodStock.get(i).getYLocation()+foodStock.get(i).getWidth();
         
         if(((leftVision<tempLeftLoc)&&(tempLeftLoc<rightVision))&&((topVision<tempTopLoc)&&(tempTopLoc<bottomVision))){
            foodSeen.add(foodStock.get(i));
         }else if(((leftVision<tempLeftLoc)&&(tempLeftLoc<rightVision))&&((topVision<tempBottomLoc)&&(tempBottomLoc<bottomVision))){
            foodSeen.add(foodStock.get(i));
         }else if(((leftVision<tempRightLoc)&&(tempRightLoc<rightVision))&&((topVision<tempTopLoc)&&(tempTopLoc<bottomVision))){
            foodSeen.add(foodStock.get(i));
         }else if(((leftVision<tempRightLoc)&&(tempRightLoc<rightVision))&&((topVision<tempBottomLoc)&&(tempBottomLoc<bottomVision))){
            foodSeen.add(foodStock.get(i));
         }
      }      
   }
   
   public void collisionCheck(){
      for(int i=0;i<creatureNum;i++){
         if(xLocation==(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())&&yLocation<=(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())&&yLocation>=fellowCreatures.get(i).getYLocation()){
            if(!eatOthers(i)){
               direction = 2;
               fellowCreatures.get(i).setDirection(1);
            }
            //System.out.println("BONK"+number);
         }else if(xLocation==(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())&&(yLocation+width)>=fellowCreatures.get(i).getYLocation()&&(yLocation+width)<=(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())){
            if(!eatOthers(i)){
               direction = 2;
               fellowCreatures.get(i).setDirection(1);
            }
            //System.out.println("BONK"+number);
         }else if(xLocation==(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())&&yLocation<=(fellowCreatures.get(i).getYLocation())&&(yLocation+width)>=(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())){
            if(!eatOthers(i)){
               direction = 2;
               fellowCreatures.get(i).setDirection(1);
            }
            //System.out.println("BONK"+number);
         }else if((xLocation+width)==fellowCreatures.get(i).getXLocation()&&yLocation<=(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())&&yLocation>=fellowCreatures.get(i).getYLocation()){
            if(!eatOthers(i)){
               direction = 1;
               fellowCreatures.get(i).setDirection(2);
            }
            //System.out.println("BONK"+number);
         }else if((xLocation+width)==fellowCreatures.get(i).getXLocation()&&(yLocation+width)>=fellowCreatures.get(i).getYLocation()&&(yLocation+width)<=(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())){
            if(!eatOthers(i)){
               direction = 1;
               fellowCreatures.get(i).setDirection(2);
            }            
            //System.out.println("BONK"+number);
         }else if((xLocation+width)==fellowCreatures.get(i).getXLocation()&&yLocation<=(fellowCreatures.get(i).getYLocation())&&(yLocation+width)>=(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())){
            if(!eatOthers(i)){
               direction = 1;
               fellowCreatures.get(i).setDirection(2);
            }
            //System.out.println("BONK"+number);
         }else if(yLocation==(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())&&xLocation<=(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())&&xLocation>=fellowCreatures.get(i).getXLocation()){
            if(!eatOthers(i)){
               direction = 4;
               fellowCreatures.get(i).setDirection(3);
            }   
            //System.out.println("BONK"+number);
         }else if(yLocation==(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())&&(xLocation+width)>=fellowCreatures.get(i).getXLocation()&&(xLocation+width)<=(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())){
            if(!eatOthers(i)){
               direction = 4;
               fellowCreatures.get(i).setDirection(3);
            }
            //System.out.println("BONK"+number);
         }else if(yLocation==(fellowCreatures.get(i).getYLocation()+fellowCreatures.get(i).getWidth())&&xLocation<=(fellowCreatures.get(i).getXLocation())&&(xLocation+width)>=(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())){
            if(!eatOthers(i)){
               direction = 4;
               fellowCreatures.get(i).setDirection(3);
            }
            //System.out.println("BONK"+number);
         }else if((yLocation+width)==fellowCreatures.get(i).getYLocation()&&xLocation<=(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())&&xLocation>=fellowCreatures.get(i).getXLocation()){
            if(!eatOthers(i)){
               direction = 3;
               fellowCreatures.get(i).setDirection(4);
            }
            //System.out.println("BONK"+number);
         }else if((yLocation+width)==fellowCreatures.get(i).getYLocation()&&(xLocation+width)>=fellowCreatures.get(i).getXLocation()&&(xLocation+width)<=(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())){
            if(!eatOthers(i)){
               direction = 3;
               fellowCreatures.get(i).setDirection(4);
            }            
            //System.out.println("BONK"+number);
         }else if((yLocation+width)==fellowCreatures.get(i).getYLocation()&&xLocation<=(fellowCreatures.get(i).getXLocation())&&(xLocation+width)>=(fellowCreatures.get(i).getXLocation()+fellowCreatures.get(i).getWidth())){
            if(!eatOthers(i)){
               direction = 3;
               fellowCreatures.get(i).setDirection(4);
            }            
            //System.out.println("BONK"+number);
         }
      
      }
   }
   
      public void collisionCheckOcean(){
      //no longer in use
      for(int i=0;i<3;i++){ //oceanNum = 3
         if(xLocation==(oceans.get(i).getXLocation()+oceans.get(i).getWidth())&&yLocation<=(oceans.get(i).getYLocation()+oceans.get(i).getWidth())&&yLocation>=oceans.get(i).getYLocation()){
            direction = 2;
            System.out.println("BONK"+number);
         }else if(xLocation==(oceans.get(i).getXLocation()+oceans.get(i).getWidth())&&(yLocation+width)>=oceans.get(i).getYLocation()&&(yLocation+width)<=(oceans.get(i).getYLocation()+oceans.get(i).getWidth())){
            direction = 2;           
             System.out.println("BONK"+number);
         }else if(xLocation==(oceans.get(i).getXLocation()+oceans.get(i).getWidth())&&yLocation<=(oceans.get(i).getYLocation())&&(yLocation+width)>=(oceans.get(i).getYLocation()+oceans.get(i).getWidth())){
            direction = 2;
            System.out.println("BONK"+number);
         }else if((xLocation+width)==oceans.get(i).getXLocation()&&yLocation<=(oceans.get(i).getYLocation()+oceans.get(i).getWidth())&&yLocation>=oceans.get(i).getYLocation()){
            direction = 1;
            System.out.println("BONK"+number);
         }else if((xLocation+width)==oceans.get(i).getXLocation()&&(yLocation+width)>=oceans.get(i).getYLocation()&&(yLocation+width)<=(oceans.get(i).getYLocation()+oceans.get(i).getWidth())){
            direction = 1;            
            System.out.println("BONK"+number);
         }else if((xLocation+width)==oceans.get(i).getXLocation()&&yLocation<=(oceans.get(i).getYLocation())&&(yLocation+width)>=(oceans.get(i).getYLocation()+oceans.get(i).getWidth())){
            direction = 1;
            System.out.println("BONK"+number);
         }else if(yLocation==(oceans.get(i).getYLocation()+oceans.get(i).getWidth())&&xLocation<=(oceans.get(i).getXLocation()+oceans.get(i).getWidth())&&xLocation>=oceans.get(i).getXLocation()){
            direction = 4; 
            System.out.println("BONK"+number);
         }else if(yLocation==(oceans.get(i).getYLocation()+oceans.get(i).getWidth())&&(xLocation+width)>=oceans.get(i).getXLocation()&&(xLocation+width)<=(oceans.get(i).getXLocation()+oceans.get(i).getWidth())){
            direction = 4;            
            System.out.println("BONK"+number);
         }else if(yLocation==(oceans.get(i).getYLocation()+oceans.get(i).getWidth())&&xLocation<=(oceans.get(i).getXLocation())&&(xLocation+width)>=(oceans.get(i).getXLocation()+oceans.get(i).getWidth())){
            direction = 4;
            System.out.println("BONK"+number);
         }else if((yLocation+width)==oceans.get(i).getYLocation()&&xLocation<=(oceans.get(i).getXLocation()+oceans.get(i).getWidth())&&xLocation>=oceans.get(i).getXLocation()){
           direction = 3;  
            System.out.println("BONK"+number);
         }else if((yLocation+width)==oceans.get(i).getYLocation()&&(xLocation+width)>=oceans.get(i).getXLocation()&&(xLocation+width)<=(oceans.get(i).getXLocation()+oceans.get(i).getWidth())){
            direction = 3;           
            System.out.println("BONK"+number);
         }else if((yLocation+width)==oceans.get(i).getYLocation()&&xLocation<=(oceans.get(i).getXLocation())&&(xLocation+width)>=(oceans.get(i).getXLocation()+oceans.get(i).getWidth())){
            direction = 3;         
            System.out.println("BONK"+number);
         }
      
      }
   }
   
   public void foodPickup(){ 
      boolean foodFound = false; //whenever food spawns inside of it, it moves the food to the top left corner and hangs for a while
      for(int i=0;i<foodNum;i++){ 
      
         //special case
         if(xLocation==foodStock.get(i).getXLocation()&&yLocation==foodStock.get(i).getYLocation()){
            foodFound = true;
         }
      
         if(xLocation==(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())&&yLocation<=(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())&&yLocation>=foodStock.get(i).getYLocation()){
            foodFound = true;
         }
         if(xLocation==(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())&&(yLocation+width)>=foodStock.get(i).getYLocation()&&(yLocation+width)<=(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())){
            foodFound = true;
         }
         if(xLocation==(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())&&yLocation<=(foodStock.get(i).getYLocation())&&(yLocation+width)>=(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())){
            foodFound=true;
         }
         if((xLocation+width)==foodStock.get(i).getXLocation()&&yLocation<=(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())&&yLocation>=foodStock.get(i).getYLocation()){
            foodFound = true;
         }
         if((xLocation+width)==foodStock.get(i).getXLocation()&&(yLocation+width)>=foodStock.get(i).getYLocation()&&(yLocation+width)<=(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())){
            foodFound = true; 
         }
         if((xLocation+width)==foodStock.get(i).getXLocation()&&yLocation<=(foodStock.get(i).getYLocation())&&(yLocation+width)>=(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())){
            foodFound=true;
         }
         if(yLocation==(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())&&xLocation<=(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())&&xLocation>=foodStock.get(i).getXLocation()){
            foodFound = true;
         }
         if(yLocation==(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())&&(xLocation+width)>=foodStock.get(i).getXLocation()&&(xLocation+width)<=(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())){
            foodFound = true;
         }
         if(yLocation==(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())&&xLocation<=(foodStock.get(i).getXLocation())&&(xLocation+width)>=(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())){
            foodFound=true;
         }
         if((yLocation+width)==foodStock.get(i).getYLocation()&&xLocation<=(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())&&xLocation>=foodStock.get(i).getXLocation()){
            foodFound = true;
         }
         if((yLocation+width)==foodStock.get(i).getYLocation()&&(xLocation+width)>=foodStock.get(i).getXLocation()&&(xLocation+width)<=(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())){
            foodFound = true;
         }
         if((yLocation+width)==foodStock.get(i).getYLocation()&&xLocation<=(foodStock.get(i).getXLocation())&&(xLocation+width)>=(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())){
            foodFound=true;
         }   
         if(foodFound){
         
         
            removedNumbers2.add(i);
            removedNum2++;         
            foodStock.get(i).removeFood(i);
            foodStock.remove(i);
            foodNum--;
            moveDistance-=10;
            if(moveDistance<=50){
               chanceToEvolve++;
               moveDistance=51;
            }
            width = (int)((150-moveDistance)/3);        
            foodFound = false;
         }
          // if(xLocation==(foodStock.get(i).getXLocation()+foodStock.get(i).getWidth())){
      //             System.out.println("EATEN by: "+width+":"+xLocation+":"+foodStock.get(i).getXLocation());
      //                       }else if((xLocation+width)==foodStock.get(i).getXLocation()){
      //             System.out.println("EATEN by: "+width+":"+xLocation+":"+foodStock.get(i).getXLocation());
      //             foodStock.get(i).removeFood(i);
      //             foodStock.remove(i);
      //             foodNum--;
      //             moveDistance-=10;
      //             if(moveDistance<50){
      //                chanceToEvolve++;
      //             }
      //             width = (int)((150-moveDistance)/3);
      //           }else if(yLocation==(foodStock.get(i).getYLocation()+foodStock.get(i).getWidth())){
      //             System.out.println("EATEN by: "+width+":"+yLocation+":"+foodStock.get(i).getYLocation());
      //             foodStock.get(i).removeFood(i);
      //             foodStock.remove(i);
      //             foodNum--;
      //             moveDistance-=10;
      //             if(moveDistance<50){
      //                chanceToEvolve++;
      //             }
      //             width = (int)((150-moveDistance)/3);
      //           }else if((yLocation+width)==foodStock.get(i).getYLocation()){
      //             System.out.println("EATEN by: "+width+":"+yLocation+":"+foodStock.get(i).getYLocation());
      //             foodStock.get(i).removeFood(i);
      //             foodStock.remove(i);
      //             foodNum--;
      //             moveDistance-=10;
      //             if(moveDistance<50){
      //                chanceToEvolve++;
      //             }
      //             width = (int)((150-moveDistance)/3);
      //           }
      }
   }
   // 
//    public void reproduce(){
//       
//    }
   
   public void move(){ 
      // if(newYear){
   //          reproduce();
   //          newYear = false;
   //       }
      sight();                      
      currentState = stateChooser();     
      frameNum++;
      frameNum2++;
      //System.out.println(number);
      if(frameNum2==250){ //gets smaller over time
         moveDistance+=5;
         width = (int)((150-moveDistance)/3);
         frameNum2=0;
         //System.out.println("AGE AGE AGE");
         if(moveDistance>145){ 
            int temp=-1;
            for(int i=0;i<fellowCreatures.size();i++){
               if(fellowCreatures.get(i).getNumber()==number){
                  temp = i;
               }
            }
            if(temp!=-1){
               //System.out.println(fellowCreatures.get(temp).getNumber());
               removedNum++;
               creatureNum--;
               removedNumbers.add(temp);
               fellowCreatures.remove(temp);
            }
         }
      }
      if(moveDistance+speed>200){
         System.out.println("ERROR: moveDistance is higher than the max number of frames");
      }
      if(frameNum>=(int)(200/(moveDistance+speed))){
         frameNum=0;
                  
      
                  
            // 3 states of sight are fleeing, attacking, and eating
            // once a state is chosen must complete state OR have object of state's focus exit vision
            // can only attack if it sees something smaller than it
            // will flee if it sees something larger than it
            // will eat 
       
         if(currentState==0){ //roam
         //System.out.println("ROM ROM");
            if(currentMove==0){
               direction = chooseDir();
               currentMove = moveDistance;
            }
            if(direction==1){
               xLocation-=1;
            }
            if(direction==2){
               xLocation+=1;
            }  
            if(direction==3){
               yLocation-=1;
            }
            if(direction==4){
               yLocation+=1;
            }
            currentMove--;
            
         
         }else if(currentState==1){ //eat
            //System.out.println("NOM NOM");
            //System.out.println("CLOSEST"+foodSeen.get(closestFood).getNumber());
            // for(int i=0;i<foodSeen.size();i++){
         //                  System.out.println(foodSeen.get(i).getNumber());
         //             }
            if((Math.abs(foodSeen.get(closestFood).getXLocation()-xLocation)>=Math.abs(foodSeen.get(closestFood).getYLocation()-yLocation))&&xLocation+width!=panelMaximum){
               if(foodSeen.get(closestFood).getXLocation()>=xLocation){
                  xLocation+=1;
               }else if(foodSeen.get(closestFood).getXLocation()<xLocation){
                  xLocation-=1;
               }else{
                  System.out.println("I shouldn't be here, current state=1 movement x");
                  currentState=0;
               }  
            }else{
               if(yLocation+width==panelMaximum){
                  if(foodSeen.get(closestFood).getXLocation()>=xLocation){
                     xLocation+=1;
                  }else if(foodSeen.get(closestFood).getXLocation()<xLocation){
                     xLocation-=1;
                  }else{
                     System.out.println("I shouldn't be here, current state=1 movement collide");
                     currentState=0;
                  }                
               }
               if(foodSeen.get(closestFood).getYLocation()>=yLocation){
                  yLocation+=1;
               }else if(foodSeen.get(closestFood).getYLocation()<yLocation){
                  yLocation-=1;
               }else{
                  System.out.println("I shouldn't be here, current state=1 movement y");
                  currentState=0;
               }
            }           
         }else if(currentState==2){ //flee
            //System.out.println("AHH AHH");
            if(Math.abs(creaturesSeen.get(closestFear).getXLocation()-xLocation)<=Math.abs(creaturesSeen.get(closestFear).getYLocation()-yLocation)){
               if(creaturesSeen.get(closestFear).getXLocation()<xLocation){
                  xLocation+=1;
               }else if(creaturesSeen.get(closestFear).getXLocation()>=xLocation){
                  xLocation-=1;
               }else{
                  System.out.println("I shouldn't be here, current state=2 movement x");
                  currentState=0;
               }  
            }else{
               if(creaturesSeen.get(closestFear).getYLocation()<=yLocation){
                  yLocation+=1;
               }else if(creaturesSeen.get(closestFear).getYLocation()>yLocation){
                  yLocation-=1;
               }else{
                  System.out.println("I shouldn't be here, current state=2 movement y");
                  currentState=0;
               }
            }           
         }else if(currentState==3){ //attack
            //System.out.println(number+" ARG ARG "+creaturesSeen.get(closestPrey).getNumber());
            if(Math.abs(creaturesSeen.get(closestPrey).getXLocation()-xLocation)>=Math.abs(creaturesSeen.get(closestPrey).getYLocation()-yLocation)){
               if(creaturesSeen.get(closestPrey).getXLocation()>xLocation){
                  xLocation+=1;
               }else if(creaturesSeen.get(closestPrey).getXLocation()<xLocation){
                  xLocation-=1;
               }else{
                  System.out.println("I shouldn't be here, current state=3 movement x");
                  currentState=0;
               }  
            }else{
               if(creaturesSeen.get(closestPrey).getYLocation()>yLocation){
                  yLocation+=1;
               }else if(creaturesSeen.get(closestPrey).getYLocation()<yLocation){
                  yLocation-=1;
               }else{
                  System.out.println("I shouldn't be here, current state=3 movement y");
                  currentState=0;
               }
            }         
         }else{
            //System.out.println("WOO WOO");
         } 
      }
      if(xLocation+width>panelMaximum){
         direction=1;
         xLocation = panelMaximum-width-1;
      }
      if(xLocation<panelMinimum){
         direction=2;
         xLocation = panelMinimum+1;
      }
      if(yLocation+width>panelMaximum){
         direction=3;
         yLocation = panelMaximum-width-1;
      }
      if(yLocation<panelMinimum){
         direction=4;
         yLocation = panelMinimum+1;
      }
      collisionCheck();
      //collisionCheckOcean();
      foodPickup();
      //System.out.println("moving"+xLocation+","+yLocation);
   }
   
   public int stateChooser(){ //returns 1 for eat, 2 for flee, and 3 for attack
      boolean roam=true;
      double fearNum=0;
      closestPrey = -1;
      closestFood = -1;
      closestFear = -1;
      double nearestPreyDist=10000;
      double nearestFoodDist=10000;
      double nearestFearDist=10000;
      if(creaturesSeen.size()!=0){
         roam = false;
         for(int i=0;i<creaturesSeen.size();i++){
            if((creaturesSeen.get(i).getWidth()-diffToEat)>=width){
               double distance = Math.sqrt(Math.pow((creaturesSeen.get(i).getXLocation()-xLocation),2)+Math.pow((creaturesSeen.get(i).getYLocation()-yLocation),2));
               if(distance<nearestFearDist){
                  closestFear = i;
                  nearestFearDist = distance;
               }
               fearNum += distance*(creaturesSeen.get(i).getWidth()-width);
            }
            if(((creaturesSeen.get(i).getWidth()+(int)(creaturesSeen.get(i).getStrength()/4))<=(width-diffToEat+(int)(strength/4)))/*&&(creaturesSeen.get(i).getWidth()<=width)*/){
               double distance = Math.sqrt(Math.pow((creaturesSeen.get(i).getXLocation()-xLocation),2)+Math.pow((creaturesSeen.get(i).getYLocation()-yLocation),2));
               if(distance<nearestPreyDist){
                  closestPrey = i;
                  nearestPreyDist = distance;
               }
            }
         }
      }
      if(foodSeen.size()!=0){
         roam = false;
         for(int i=0;i<foodSeen.size();i++){
            double distance = Math.sqrt(Math.pow((foodSeen.get(i).getXLocation()-xLocation),2)+Math.pow((foodSeen.get(i).getYLocation()-yLocation),2));
            if(distance<nearestFoodDist){
               closestFood = i;
               nearestFoodDist = distance;
            }
         }
      }
      if(roam){
         return 0;
      } 
      if(fearNum!=0){
         //System.out.println("SPOOKY"+fearNum);
      }
      if(fearNum>=fearThreshold){
         
         return 2;
      }
      if(closestPrey!=-1&&(Math.random()*1000)<=huntThreshold&&currentState!=1){
         return 3;
      }
      if(closestFood!=-1&&currentState!=3){
         return 1;
      }
      return 0;
   }
   
   public int getCreatureNum(){
      return creatureNum;
   }
   
   public void setFoodStock(ArrayList<EvolutionFood> food){
      foodStock = food;
   }
   
   public ArrayList<EvolutionFood> getFoodStock(){
      return foodStock;
   }
   public void setFoodNum(int x){
      foodNum = x;
   }
   
   public int getFoodNum(){
      return foodNum;
   }

   public int getNumber(){
      return number;
   }
   
   public int getGeneration(){
      return generation;
   }
   
   public ArrayList<EvolutionCell> getHeritage(){
      return heritage;
   }
   
   public int getColor(){
      return color;
   }
   
   public void setColor(int x){
      color = x;
   }
   
   public boolean getNewYear(){
      return newYear;
   }
   
   public void setNewYear(boolean x){
      newYear = x;
   }

   
   public int getWidth(){
      return width;
   }
   
   public int getFearThreshold(){
      return fearThreshold;
   }
   
   public int getHuntThreshold(){
      return huntThreshold;
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
   
   public void setDirection(int x){
      direction = x;
   }

   public int removedNum(){
      int temp = removedNum;
      removedNum=0;
      return temp;
      
   }
   
   public int getSpeed(){
      return speed;
   }
   
   public int getStrength(){
      return strength;
   }
   
   public int getSee(){
      return see;
   }
   
   public int getRemovedNumbers(){
      int temp = removedNumbers.get(0);
      removedNumbers.remove(0);
      return temp;
      
   }
   
   public int removedNum2(){
      int temp = removedNum2;
      removedNum2=0;
      return temp;
      
   }
   
   public int getRemovedNumbers2(){
      int temp = removedNumbers2.get(0);
      removedNumbers2.remove(0);
      return temp;
      
   }



   

}