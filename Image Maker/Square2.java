/* Jacob Yager
 * jcyager
 * 3/9/2017
 * Lab 8
 */

import java.awt.*;

public class Square2 extends Rectangle{
  //constructor
  public Square2(Color fillColor, Color borderColor, int x, int y, int w) {
    super(fillColor, borderColor, x, y,w,w);
  }
  //constructor
  public Square2(Color fillColor, int x, int y, int w, String dir) {
    super(fillColor, x, y,w,w, dir);
    
  }
  //constructor
  public Square2(int x, int y, int w) {
    super(x, y,w,w);
  }
  //converts a square to a string
  public String toString(){
    return "The location of the square is ("+super.getX()+","+super.getY()+").\nThe width is "+super.getWidth()+".\n";
  }
  
   public static void main(String[] args){
    //testing methods and objects 
    Square2 test = new Square2(20, 20, 3);
    System.out.println(test.getArea());
    System.out.println(test.getPerimeter());
    System.out.println(test.toString());
  }
}