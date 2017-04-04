/* Jacob Yager
 * jcyager
 * 3/9/2017
 * Lab 8
 */
import javax.swing.*;

public class ShapeWindow2{
  public static void main (String[] args)
  {
    //Testing the entire class
    JFrame frame = new JFrame ("Shape Generator");
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new ShapeDriver2());
    frame.pack();
    frame.setVisible(true);
  }
}