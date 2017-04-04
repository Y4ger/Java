/* Jacob Yager
 * jcyager
 * 3/9/2017
 * Lab 8
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ShapeDriver2 extends JPanel{
 
  //setting frame height and width
  private final int WIDTH = 600, HEIGHT = 600;
  //List to hold all shapes as they are added 
  public List<Shape> shapes;
  
  
  MyListener listen = new MyListener();
  Timer t = new Timer(1000/60, listen);
  DirectionListener listener = new DirectionListener();
 
  //constructor
  public ShapeDriver2(){
    //This prepares the window to be displayed
    shapes = new ArrayList<Shape>();
    setBackground(Color.cyan);
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setFocusable(true);
    //The code starts without a mouse listner so the user has to press Space before the mouse can be used.
    addKeyListener(new KeyListen());
    
    
    
  }
  public List<Shape> getList(){
    return shapes;
  }
  
  //paint options
  public void paintComponent (Graphics g)
  {
    super.paintComponent (g);
    for(Shape shape : shapes){
      shape.draw(g);
    }
  }
  //listen to keys
  private class KeyListen implements KeyListener{
    
    public void keyTyped(KeyEvent event){    }
    public void keyPressed(KeyEvent event){
      switch (event.getKeyCode()) {
        //This makes it so that once space is pressed the mouse can be used
        case KeyEvent.VK_SPACE:
          addMouseListener(listener);
          break;
        //this make it so that if escape is pressed the mouse cannot be used  
        case KeyEvent.VK_ESCAPE:
          removeMouseListener(listener);
          break;
      }
      repaint();  
    }
    public void keyReleased(KeyEvent event){}
  }
  //listens to the mouse to add shapes
  private class DirectionListener implements MouseListener {
    //pick a random direction
    public String randDir(int num){
      if (num == 1){
        return "SE";
      }else if(num == 2){
        return "SW";
      }else if(num == 3){
        return "NE";
      }else{
        return "NW";
      }
    }
    //create a random shape with a random direction in it 
    public Shape randShape(int _x, int _y){
      int red = (int)Math.floor(Math.random() * 225);
      int green = (int)Math.floor(Math.random() * 225);
      int blue = (int)Math.floor(Math.random() * 225);
      int pick = (int)Math.floor(Math.random() * 4);
      int dirr = (int)Math.floor(Math.random() * 4);
      int height = (int)Math.ceil(Math.random() * 60+5);
      int width = (int)Math.ceil(Math.random() * 60+5);
      if(pick == 1){
        return new Square2(new Color(red, green, blue), _x, _y,width, randDir(dirr)); 
      }else if(pick == 2){
        return new Rectangle(new Color(red,green,blue), _x,_y,height,width,randDir(dirr));
      }else if(pick == 3){
        return new Circle(new Color(red,green,blue), _x,_y,width,randDir(dirr));
      }else{
        return new Oval(new Color(red,green,blue), _x,_y,width,height,randDir(dirr));
      }
    }
    //listens to the mouse
    public void mousePressed (MouseEvent event) {
      //These create random variables for the shapes to use
      
      int x = (int)event.getPoint().getX();
      int y = (int)event.getPoint().getY();
      
      //delays motion for ten seconds 
      t.setInitialDelay(10000);
      t.start();
      //add new shape to array list 
      shapes.add(randShape(x,y));
      
      repaint();
      }
    public void mouseClicked(MouseEvent event){}
    public void mouseReleased(MouseEvent event){}
    public void mouseEntered(MouseEvent event){}
    public void mouseExited(MouseEvent event){}
    } 

  //This keeps track of the timer and moves the balls
  class MyListener implements ActionListener{
    
    
    //moves the balls
    public void actionPerformed(ActionEvent event){
       //moves through the shapes array 
       for(int i = 0; i < shapes.size();i++){
         
         shapes.get(i).move();
         shapes.get(i).wallCheck();
         
       }  
       repaint();
    }
  }
}
