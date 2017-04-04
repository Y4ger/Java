/* Jacob Yager
 * jcyager
 * 3/9/2017
 * Lab 8
 */

import java.awt.*;

public class Circle extends Shape{
  //private variable for circle
  private int radius;
  
  //constructor
  public Circle(Color fillColor, Color borderColor, int x, int y, int r) {
    super(fillColor, borderColor, x, y);
    radius = r;
  }
  //constructor
  public Circle(Color fillColor,  int x, int y, int r, String dir) {
    super(fillColor,  x, y);
    radius = r;
    setDir(dir);
    setCenter(new Point(x-(radius/2), y-(radius/2)));
  }
  //constructor
  public Circle(int x, int y, int r) {
    super(x, y);
    radius = r;
    setDir("SE");
  }
  //retieves the circle's radius
  public int getRadius(){
    return radius;
  }
  //calculates the circle's area
  public double getArea(){
    return 3.14*radius*radius;
  }
  //calculates the circle's perimeter
  public double getPerimeter(){
    return 2*3.14*radius;
  }
  //creates a graphic of the circle
  public void draw(Graphics g){
    int j = getX();
    int k = getY();
    g.setColor(getFillColor());
    g.fillOval(j,k,radius,radius);
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
