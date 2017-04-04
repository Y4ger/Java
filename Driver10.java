/* Jacob Yager
 * 3/30/2017
 * Lab 10
 */

import java.io.FileNotFoundException;

public class Driver10{
  
  public static void main(String[] args)throws FileNotFoundException{
    //Testing CSVReader
    CVSReader test = new CVSReader();
    System.out.println("Rows: "+test.numOfRows());
    System.out.println("Fields: "+test.numOfFields());
    System.out.println("Chosen Field: "+test.fields(3, 4));
  
  
  //Test HTMLConverter
    HTMLConverter testHTML = new HTMLConverter();
    testHTML.convert();
  }
}