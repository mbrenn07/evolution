import javax.swing.JFrame;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.*;
public class EvolutionDriver						//Driver Program
{
   public static EvolutionPanel screen;					//Game window

   public static void main(String[]args)
   {
   
      screen = new EvolutionPanel();
      JFrame frame = new JFrame("Evolution");	//window title
      frame.setSize(850, 900);					//Size of game window
      frame.setLocation(100, 50);				//location of game window on the screen
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	     
      frame.setVisible(true);
      
      screen.setPreferredSize(new Dimension(100000, 3800));
      JScrollPane scrollPane = new JScrollPane(screen);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setBounds(0, 0, 850, 900); //850, 900 is orginial
      scrollPane.setMinimumSize(new Dimension(200, 200));
      scrollPane.getViewport().setPreferredSize(new Dimension(850, 900));     
      frame.add(scrollPane);
      frame.setVisible(true);
      frame.pack();
        
      
      frame.setVisible(true);
      frame.addKeyListener(new listen());		//Get input from the keyboard
   
   }
   
   public static class listen implements KeyListener 
   {
   
      public void keyTyped(KeyEvent e)
      {
      
      }
      
      public void keyPressed(KeyEvent e)
      {
      
      }
   
      public void keyReleased(KeyEvent e)
      {
         screen.processUserInput(e.getKeyCode());
      }
   }

}
