import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EvolutionPanel extends JPanel{ //TODO: create map editor program 


   ArrayList<EvolutionCell> creatures = new ArrayList<EvolutionCell>();
   ArrayList<EvolutionFood> food = new ArrayList<EvolutionFood>();
   //ArrayList<ocean> oceans = new ArrayList<>(Arrays.asList(new ocean(100,400,50), new ocean(700,40,80), new ocean(500,200,80)));
   ArrayList<JButton> buttons = new ArrayList<JButton>();
   int divider = 1;
   int dividerMax = 5; //max val of the divider, ensures that the size button works
   int scalar1 = 5;
   int numCreatures = 100*scalar1;
   int foodNum = 0;//numCreatures*3;
   int foodMax = 40*scalar1*scalar1;
   int foodWidth=5;
   int foodCreated = 0;
   int foodCreatedMax=999999999;
//    int xLoc = 40;
//    int yLoc = 40;
//    int spacer = 30;
   int foodCounter=0;
   int panelMin = 25;
   int panelMax = 750*dividerMax;
   int numCycles = 0;
   int cycles = 10000;
   boolean graphicsOn = true;
   private Timer t;
   private Timer t2;
   private int delay=10; //#miliseconds delay between each time the cells move and screen refreshes for the timer
   public Path fileName = Path.of("data.txt");
   public String fileStr = "";
   int generation = 0;

   public EvolutionPanel(){
      //START BUTTONS
      this.setLayout(null);
      JButton gToggle = new JButton("Graphics Toggle");
      gToggle.setBounds(0,0,gToggle.getPreferredSize().width,gToggle.getPreferredSize().height);
      this.add(gToggle);
      gToggle.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               graphicsOn = !graphicsOn;
               System.out.println("Graphics Toggled!");
            }          
         });
      
      JButton speedToggle = new JButton("Speed Toggle");
      speedToggle.setBounds(gToggle.getPreferredSize().width,0,speedToggle.getPreferredSize().width,speedToggle.getPreferredSize().height);
      this.add(speedToggle);
      speedToggle.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {             
               if(delay==10){
                  delay=1;
               }else if(delay==0){
                  delay=100;
               }else if(delay==100){
                  delay=10;
               }else if(delay==1){
                  delay=0;
                  System.out.println("chrono");
               }
               System.out.println("QQ"+delay);
               t.setDelay(delay);
            }		                      
         });
      JButton sizeToggle = new JButton("Size Toggle");
      sizeToggle.setBounds(gToggle.getPreferredSize().width+speedToggle.getPreferredSize().width,0,sizeToggle.getPreferredSize().width,sizeToggle.getPreferredSize().height);
      this.add(sizeToggle);
      sizeToggle.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(divider==1){
               divider = 5;
               }else if(divider==5){
                  divider = 1;
               }
               //panelMax = 750*divider;
            }		                      
         });

      //END BUTTONS 
      
      t = new Timer(delay, new Listener());				
      t.start();
      for(int i=0;i<numCreatures;i++){
         creatures.add(new EvolutionCell(panelMin,panelMax+25));
      }
      for(int i=0;i<foodNum;i++){
         foodCreated++;
         food.add(new EvolutionFood(panelMin,panelMax+25,numCreatures,creatures,foodWidth));
      }
      creatures.get(0).setFoodStock(food);
      creatures.get(0).setFoodNum(foodNum);
     //  for(int i=0;i<numCreatures;i++){
   //          //creatures.get(i).setXLocation(xLoc);
   //          //creatures.get(i).setYLocation(yLoc);
   //          if(Math.random()>=.5){
   //             xLoc+=spacer;
   //          }else
   //             yLoc+=spacer;
   //       }
   }
   
   public void paintComponent(Graphics g)
   {
      if(graphicsOn){
         for(int i=0;i<buttons.size();i++){
            this.remove(buttons.get(i));
         }
         super.paintComponent(g);   
         if(divider==1){                              
         g.setColor(Color.blue);		//draw a blue boarder around the board
         g.fillRect(panelMin-25, panelMin-25, panelMax+50, panelMax+50);
         g.setColor(new Color(56,133,12));
         g.fillRect(panelMin, panelMin, panelMax, panelMax);
         // for(int i=0;i<3;i++){ //oceanNum = 3;
//             g.setColor(Color.blue);
//             g.fillRect(oceans.get(i).getXLocation(),oceans.get(i).getYLocation(),oceans.get(i).getXLocation()+oceans.get(i).getWidth(),oceans.get(i).getYLocation()+oceans.get(i).getWidth());
//          }
         }else{
         g.setColor(Color.blue);		//draw a blue boarder around the board
         g.fillRect(panelMin-25, panelMin-25, ((panelMax+50)/divider)+25, ((panelMax+50)/divider)+25);
         g.setColor(new Color(56,133,12));
         g.fillRect(panelMin-25, panelMin-25, (panelMax+50)/divider, (panelMax+50)/divider);
         // for(int i=0;i<3;i++){ //oceanNum = 3;
//             g.setColor(Color.blue);
//             g.fillRect(oceans.get(i).getXLocation()/divider,oceans.get(i).getYLocation()/divider,(oceans.get(i).getXLocation()+oceans.get(i).getWidth())/divider,(oceans.get(i).getYLocation()+oceans.get(i).getWidth())/divider);
//          }
         }
      }else{
         super.paintComponent(g);                                 
         g.setColor(Color.white);		//draw a blue boarder around the board
         g.fillRect(panelMin-25, panelMin-25, panelMax+50, panelMax+50);
      }
      showBoard(g);					//draw the contents of the array board on the screen
   }

   public void showBoard(Graphics g)	{
      if(numCycles >= cycles)
      {
         System.out.println("One year completed!");
         for(int i=0;i<creatures.size();i++){  
            creatures.get(i).setNewYear(true); 
         }                                     
         fileStr = fileStr + generation + ",";
         generation++;
         //System.out.println("Number: "+number+" Generation: "+generation+" Fear: "+fearThreshold+" Hunt: "+huntThreshold+" Speed: "+speed+" Sight: "+see+" Strength: "+strength);
         int avgFear=0;
          for(int i=0;i<creatures.size();i++){  
            avgFear += creatures.get(i).getFearThreshold(); 
         }
         avgFear = avgFear/creatures.size();
         fileStr = fileStr + avgFear + ",";
         
         int avgHunt=0;
          for(int i=0;i<creatures.size();i++){  
            avgHunt += creatures.get(i).getHuntThreshold(); 
         }
         avgHunt = avgHunt/creatures.size();
         fileStr = fileStr + avgHunt + ",";
         
         int avgSpeed=0;
          for(int i=0;i<creatures.size();i++){  
            avgSpeed += creatures.get(i).getSpeed(); 
         }
         avgSpeed = avgSpeed/creatures.size();
         fileStr = fileStr + avgSpeed + ",";
         
         int avgSight=0;
          for(int i=0;i<creatures.size();i++){  
            avgSight += creatures.get(i).getSee(); 
         }
         avgSight = avgSight/creatures.size();
         fileStr = fileStr + avgSight + ",";
         
         int avgStrength=0;
          for(int i=0;i<creatures.size();i++){  
            avgStrength += creatures.get(i).getStrength(); 
         }
         avgStrength = avgStrength/creatures.size();
         fileStr = fileStr + avgStrength + "\n";        
         //System.out.println(fileStr); 
         try {Files.writeString(fileName, fileStr);}catch(Exception e) {}
         
         numCycles = 0;          
      } 
      int removedNum = creatures.get(0).removedNum();
      for(int i=0;i<removedNum;i++){
         creatures.remove(creatures.get(0).getRemovedNumbers());
      }
      food = creatures.get(0).getFoodStock();
      foodNum = creatures.get(0).getFoodNum();
      for(int i=0;i<creatures.get(0).getCreatureNum();i++){ //when delay is = 0, will create new creatures no matter what when minimized and reopened
         if(creatures.get(i).getNewYear()){
            creatures.get(i).setNewYear(false);
            //System.out.println(creatures.get(i).getNumber());
            creatures.add(new EvolutionCell(panelMin,panelMax+25,creatures.get(i)));
         }
      } 
      for(int i=0;i<creatures.size();i++){
         creatures.get(i).move();
         if(graphicsOn){
            colorSet(g,creatures.get(i).getColor());
            g.fillRect(creatures.get(i).getXLocation()/divider,creatures.get(i).getYLocation()/divider,creatures.get(i).getWidth()/divider,creatures.get(i).getWidth()/divider);
            if(divider==1){
            g.drawString(Integer.toString(creatures.get(i).getNumber()),creatures.get(i).getXLocation(),creatures.get(i).getYLocation()-5);      
            }       
         } 
      }
      foodCounter++;
      if(foodCounter==10&&foodNum<foodMax&&foodCreated<foodCreatedMax){ 
         foodCounter=0;
         foodNum++;
         foodCreated++;
         for(int i=0;i<scalar1;i++){
            food.add(new EvolutionFood(panelMin,panelMax+25,numCreatures,creatures,foodWidth));
            creatures.get(0).setFoodStock(food);
            creatures.get(0).setFoodNum(creatures.get(0).getFoodNum()+1);
         }
         
      }
      if(foodCounter>=10){
         foodCounter=0;
      }
      if(graphicsOn){
         if(!food.isEmpty()){
            for(int i=0;i<food.get(0).getFoodNum();i++){
               g.setColor(new Color(166,15,58));
               g.fillRect(food.get(i).getXLocation()/divider,food.get(i).getYLocation()/divider,food.get(i).getWidth()/divider,food.get(i).getWidth()/divider);
            //g.drawString(Integer.toString(food.get(i).getNumber()),food.get(i).getXLocation(),food.get(i).getYLocation()-5);             
            //System.out.println("new food:"+food.get(i).getXLocation()+","+food.get(i).getYLocation());
            }
         }
      }
      //BEGIN DRAWING FAMILY TREE
      //TODO: many buttons = many lag. create buttons less often OR optimize somehow
      if(!graphicsOn&&numCycles%100==0){
         g.setColor(Color.black);
         for(int i=0;i<buttons.size();i++){
            this.remove(buttons.get(i));
         }
         buttons.clear();        
         int counter=0;
         for(int i=0;i<creatures.size();i++){
            for(int j=0;j<creatures.get(i).getHeritage().size();j++){
               //g.drawString(Integer.toString(creatures.get(i).getHeritage().get(j).getNumber()),i*50,j*20);
               //for some reason buttons are not being drawn properly
               JButton temp = new JButton(""+creatures.get(i).getHeritage().get(j).getNumber());
                String tempstr = "Number: "+creatures.get(i).getHeritage().get(j).getNumber()+" Generation: "+creatures.get(i).getHeritage().get(j).getGeneration()+" Fear: "+creatures.get(i).getHeritage().get(j).getFearThreshold()+" Hunt: "+creatures.get(i).getHeritage().get(j).getHuntThreshold()+" Speed: "+creatures.get(i).getHeritage().get(j).getSpeed()+" Sight: "+creatures.get(i).getHeritage().get(j).getSee()+" Strength: "+creatures.get(i).getHeritage().get(j).getStrength();
                temp.addActionListener(
                   new ActionListener() {
                      public void actionPerformed(ActionEvent e) {
                         System.out.println(tempstr);
                      }          
                   });
               buttons.add(temp);
               buttons.get(counter).setBounds((i*75)+350,j*20,75,20);
               //System.out.println(graphicsOn);
               counter++;
            }
         }   
         for(int i=0;i<buttons.size();i++){
            this.add(buttons.get(i));
         }  
      }
      
   }
   
   public void colorSet(Graphics g, int x){ 
      if(x<1){
         g.setColor(Color.red);
      }else
         if(x<2){
            g.setColor(Color.green);
         }else
            if(x<3){
               g.setColor(Color.cyan);
            }else
               if(x<4){
                  g.setColor(Color.magenta);
               }else
                  if(x<5){
                     g.setColor(Color.yellow);
                  }else
                     if(x<6){
                        g.setColor(Color.pink);
                     }else
                        if(x<7){
                           g.setColor(Color.gray);
                        }else
                           if(x<8){
                              g.setColor(Color.orange);
                           }else
                              if(x<9){
                                 g.setColor(Color.white);
                              }else{
                                 g.setColor(Color.black);
                              }
   
   }

   
   public void processUserInput(int k){
   
//       if(k==KeyEvent.VK_Q){
//          System.out.println("QQ");
//          if(delay==10){
//             delay=1;
//          }else if(delay==0){
//             delay=100;
//          }else if(delay==100){
//             delay=10;
//          }else if(delay==1){
//             delay=0;
//             System.out.println("chrono");
//          }
//          t.setDelay(delay);
//       }
      if(k==KeyEvent.VK_ESCAPE){
         System.exit(1);
      }						  
      repaint();			//refresh the screen
   }
   
   
   
   private class Listener implements ActionListener{
      
      public void actionPerformed(ActionEvent e){	//this is called for each timer iteration - traffic mechanics
         numCycles++;                     
         repaint();
      }
   }



}