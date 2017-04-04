/* Jacob Yager
 * 3/30/2017
 * Lab 10
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Character;

public class HTMLConverter{
 
  private String inputName;
  private String outputName;
  
  public HTMLConverter(){
    //Prompt for the input and output file names
    Scanner console = new Scanner(System.in);
    System.out.print("Input txt File: ");
    inputName = console.next();
    System.out.print("Output HTML file: ");
    outputName = console.next();
  }
  //retrieve inputName
  public String getIn(){
    return inputName;
  }
  //retrieve outputName
  public String getOut(){
    return outputName;
  }
  //this writes the HTML header in the outFile
  public void writeHeader(PrintWriter out){
    //print the text in the file
    out.printf("<html>\n<title>\nThis is my Java html converter\n</title>\n<body>\n\n");     
  }
  //converts a txt file into HTML
  public void convert()throws FileNotFoundException{
     //Construct the Scanner and PrintWriter objects for reading and writing 
    File inputFile = new File(inputName);
    Scanner in = new Scanner(inputFile);
    PrintWriter out = new PrintWriter(outputName);
    
    writeHeader(out);
    //puts a <br> after each line
    while(in.hasNextLine()){
      String line = in.nextLine();
      out.printf(line + "<br>\n");
    }
    out.printf("\n");
    //Closes the body and html tags
    out.printf("</body>\n</html>");

    in.close();
    out.close();
  }
}