/* Jacob Yager
 * jcyager
 * 3/9/2017
 * Lab 8
 */

import java.awt.*;

public class Rectangle extends Shape{
  //private variables for a rectangle
  private int width; 
  private int height;
  //constructor
  public Rectangle(Color fillColor, Color borderColor, int x, int y, int w, int h) {
    super(fillColor, borderColor, x, y);
    width = w;
    height = h;
  }
  //constructor
  public Rectangle(Color fillColor, int x, int y, int w, int h, String dir) { 
    super(fillColor, x, y);
    width = w;
    height = h;
    setDir(dir);
  }
  //constructor
  public Rectangle(int x, int y, int w, int h){
    super(x, y);
    width = w;
    height = h;
  }
  //returns area of a rectangle
  public double getArea(){
    return width*height;
  }
  //returns the perimeter of a rectangle
  public double getPerimeter(){
    return (2*width)+(2*height);
  }
  //retrieves the value of width 
  public int getWidth(){
    return width;
  }
  //retrieves the height value
  public int getHeight(){
    return height;
  }
  //creates a graphic of the rectangle
  public void draw(Graphics g){
    int j = getX();
    int k = getY();
    g.setColor(getFillColor());
    g.fillRect(j,k,width,height);
  }
  //converts a rectangle to a string
  public String toString(){
    return "The location of the rectangle is ("+getX()+","+getY()+").\nThe width is "+getWidth()+".\nThe height is "+getHeight()+".";
 }
  public static void main(String[] args){
    //testing methods and objects 
    Rectangle test = new Rectangle(20, 20, 3, 4);
    System.out.println(test.getArea());
    System.out.println(test.getPerimeter());
    System.out.println(test.toString());
  }
}