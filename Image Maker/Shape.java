/* Jacob Yager
 * jcyager
 * 3/9/2017
 * Lab 8
 */

import java.awt.Color;
import java.awt.Graphics;


public abstract class Shape {
  //these are to give some value to the colors 
  public static final Color white = new Color(25,25,25);
  public static final Color black = new Color(100, 100, 100);
 
  //private fields of a shape
  private Color fillColor;
  private Color borderColor;
  private Boolean isFilled;
  private Point Location;
  private String direction;
  private Point center;

 
  //constructor
  public Shape(Color fillColor, Color borderColor, int x, int y) {
    this.fillColor = fillColor;
    this.borderColor = borderColor;
    Point loc = new Point(x, y);
    Location = loc;
  }
  //constructor
  public Shape(Color fillColor, int x, int y) { 
    this.fillColor = fillColor;
    this.borderColor = black;
    Point loc = new Point(x, y);
    Location = loc;
  }
  //constructor
  public Shape(int x, int y ){
    isFilled = true;
    this.fillColor = white;
    this.borderColor = black;
    Point loc = new Point(x, y);
    Location = loc;
  }
  //change fill color
  public void setFillColor(Color c) {
    fillColor = c;
  }
  //get color
  public Color getFillColor() {
    return fillColor;
  }
  //change border color
  public void setBorderColor(Color c) {
    borderColor = c;
  }
  //get border color
  public Color getBorderColor() {
    return borderColor;
  }
  //get location
  public Point getLocation() {
    return Location;
  }
  //set location
  public void setLocation(Point xx){
    Location = xx;
  }
  //get x value
  public int getX() {
    return Location.x();
  }
  //get y value
  public int getY() {
    return Location.y();
  }
  //determine if filled
  public boolean isFilled() {
    if(this.isFilled.equals(white)){
      this.isFilled = true;
      return true;
    }else{
      this.isFilled = false;
      return false;
    }
  }
  public void swapDir(Shape one, Shape two){
    String swap = two.getDir();
    two.setDir(one.getDir());
    one.setDir(swap);
  }
  //move the balls
  public void move() {
      if(getDir().equals("SE")){
         moveSE();
         
      }else if (getDir().equals("SW")){
        moveSW();
        
      }else if (getDir().equals("NW")){
        moveNW();
        
      }else if (getDir().equals("NE")){
        moveNE();
        
      }
  }
  //Change if hit wall
  public void wallCheck(){
    if(Location.x() >= 600 && direction.equals("SE")){
      setDir("SW");
    }else if (Location.x() >= 600 && direction.equals("NE")){
      setDir("NW");
    }else if(Location.x() <= 0 && direction.equals("NW")){
      setDir("NE");
    }else if(Location.x() <= 0 && direction.equals("SW")){
      setDir("SE");
    }else if(Location.y() <= 0 && direction.equals("NW")){
      setDir("SW");
    }else if(Location.y() <= 0 && direction.equals("NE")){
      setDir("SE");
    }else if(Location.y() >= 600 && direction.equals("SE")){
      setDir("NE");
    }else if(Location.y() >= 600 && direction.equals("SW")){
      setDir("NW");
    }
  }
  
  //movement based on direction, change location and center
  public void moveSE() {
    Point loc = new Point(getX()+1, getY()+1);
    Location = loc;

  }
  public void moveSW() {
    Point loc = new Point(getX()-1, getY()+1);
    Location = loc;

  }
  public void moveNE() {
    Point loc = new Point(getX()+1, getY()-1);
    Location = loc;

  }
  public void moveNW() {
      Point loc = new Point(getX()-1, getY()-1);
      Location = loc;

  }
  
  //retrieve radius
  public int getRadius(){
    return getRadius();
  }
  
  //change dirrection
  public void setDir(String m){
    direction = m;
  }
  
  //retrieve the direction 
  public String getDir(){
    return direction;
  }
  
  //change the center
  public void setCenter(Point p){
    center = p;
  }
  
  //retrieve the center
  public Point getCenter(){
    return center;
  }
  //get x value of center
  public int getCenX() {
    return center.x();
  }
  //get y value
  public int getCenY() {
    return center.y();
  }
  
  //swap the colors of two people
  public void swapColor(Shape one, Shape two){
     //this stores the variable to change
     Color swap = two.getFillColor();
     two.setFillColor(one.getFillColor());
     one.setFillColor(swap);
  }
  
  //Check to see if the current shape and one other are touching 
  public Boolean isTouch(Shape other){
    if(
       
       //these are the tests to see if they are touching
       (((other.getCenX()-other.getRadius())<=(getCenX()+getRadius()) &&
        (other.getCenX()+other.getRadius())>=(getCenX()-getRadius())) &&
      ((other.getCenY()-other.getRadius())<=(getCenY()+getRadius()) &&
       (other.getCenY()+other.getRadius())>=(getCenY()-getRadius())))
         ||
       (((other.getCenX()+other.getRadius())>=(getCenX()-getRadius()) &&
        (other.getCenX()-other.getRadius())>=(getCenX()+getRadius())) &&
      ((other.getCenY()+other.getRadius())<=(getCenY()-getRadius()) &&
       (other.getCenY()-other.getRadius())>=(getCenY()+getRadius())))  ){
      return true;
    }else{
      return false;
    }
  }
  //abstract for calculating area
  abstract double getArea();
  //abstract for calculating perimeter
  abstract double getPerimeter();
  //abstract method to draw the shapes
  abstract void draw(Graphics g);
 
}