/* Jacob Yager
 * 3/30/2017
 * Lab 10
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Character;


public class CVSReader{
  private String inputFileName;
  
  public CVSReader(){
      //Prompt for the input and output file names
    Scanner console = new Scanner(System.in);
    System.out.print("Input CSV File: ");
    inputFileName = console.next();
  }
  //retieve the FileName
  public String getFileName(){
    return inputFileName;
  }
  
  public int numOfRows()throws FileNotFoundException{
    //Construct the Scanner and PrintWriter objects for reading and writing 
    File inputFile = new File(getFileName());
    Scanner in = new Scanner(inputFile);
    
    //records the number of rows
    int rows=0;
    
    while(in.hasNextLine()){
      String line = in.nextLine();
      
      //increment rows
      rows++;
      
    }
    in.close();
    return rows;
  }
  
  public int numOfFields()throws FileNotFoundException{
    //Construct the Scanner and PrintWriter objects for reading and writing 
    File inputFile = new File(getFileName());
    Scanner in = new Scanner(inputFile);
    //record fields 
    int fields=0;
    //is used to skip the first row (not considered fields)
    int count=0;
    while(in.hasNextLine()){
      //Skip first row
      if(count == 0){
      String skip = in.nextLine();
      count++;
      //increment field number 
      }else{
        String line =  in.nextLine();
        String[] fldArray = line.split(",");
        fields += fldArray.length;
      }
    }
    in.close();
    return fields;
  }
  
  public String fields(int row, int column)throws FileNotFoundException{
    //Construct the Scanner and PrintWriter objects for reading and writing 
    File inputFile = new File(getFileName());
    Scanner in = new Scanner(inputFile);
    
    int rowCount = 1;
    String answer = "Field not found";
    while(in.hasNextLine()){
      rowCount++;
      String skip = in.nextLine();
      //if the row number is reached make that field the answer
      if(rowCount == row){
        String line = in.nextLine();
        String[] fldArray = line.split(",");
        answer = fldArray[column -1];
      }
    }
    in.close();
    return answer;
  }
}