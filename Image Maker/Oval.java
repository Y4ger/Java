/* Jacob Yager
 * jcyager
 * 3/9/2017
 * Lab 8
 */

import java.awt.*;

public class Oval extends Shape{
  //private variable for circle
  private int width;
  private int height;
  
  //constructor
  public Oval(Color fillColor, Color borderColor, int _x, int _y, int _width, int _height) {
    super(fillColor, borderColor, _x, _y);
    width = _width;
    height = _height;
  }
  //constructor
  public Oval(Color fillColor,  int x, int y, int _width, int _height, String dir) {
    super(fillColor,  x, y);
    width = _width;
    height = _height;
    setDir(dir);
  }
  //constructor
  public Oval(int x, int y, int _width, int _height,String dir) {
    super(x, y);
    width = _width;
    height = _height;
    setDir(dir);
  }

  
  //calculates the circle's area
  public double getArea(){
    return 3.14*width*height;
  }
  //calculates the circle's perimeter
  public double getPerimeter(){
    return 2*3.14*width;
  }
  //creates a graphic of the circle
  public void draw(Graphics g){
    int j = getX();
    int k = getY();
    g.setColor(getFillColor());
    g.fillOval(j,k,width,height);
  }
  //converts a circle to a string
  public String toString(){
    return "The location of the circle is ("+getX()+","+getY()+").\nThe radius is "+getRadius()+".\n";
  }

  public static void main(String[] args){
    //testing methods and object   
    Circle test = new Circle(20, 20, 3);
    System.out.println(test.getArea());
    System.out.println(test.getPerimeter());
    System.out.println(test.toString());
  }
}
