import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class Seller{
	ArrayList<Item> itemList;
	private Scanner nameScan;
	private Scanner infoScan;
	private Scanner categoryScan;
	private Scanner quantityScan;
	private Scanner input;
	private String currentUsername = "";
	private Scanner updater;
	private Scanner passGetter;
	private Scanner emailGetter;
	private CSVReader reader4;
	private CSVReader reader3;
	
 
  public Seller(){
	  //Login upon creation of seller
	  try {
		login();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public Seller(String name){
	  //Another constructor to take a name
	  currentUsername = name;
	  try {
		login();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  private void register(){
	  //Register as a Seller
   try {
	login();
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} 
  }
  
  private void login()throws FileNotFoundException{
	  //Log in as a seller
	  System.out.println("\nLogged in as Seller\n\n");
		 
		String help = (motd() + "\n\nEnter a command below (without the quotes) to continue.\n\n");
		  
			
		help += "\"add\" to add item to inventory\n";
		help += "\"update_i\" to update item info\n";
		help += "\"delete\" to delete item \n";
		help += "\"search\" to search for items\n";
		help += "\"update\" to update credentials\n";
		help += "\"logout\" to logout\n\n";
		help += "\"help\" anytime to see this message again\n";
		
		System.out.println(help);
		
		input = new Scanner(System.in); 
			  String userInput = input.nextLine();
			  userInput = userInput.toLowerCase();			  
			  if(userInput.equals("add")){
				  addInvItem();
			  }else if(userInput.equals("update_i")){
				  updInvItem();
				  login();
			  }
			  else if(userInput.equals("delete")){
				  delInvItem();
				  login();
			  }
			  else if(userInput.equals("search")){
				  System.out.println("What are you lookingh for?");
					Scanner item = new Scanner(System.in);
					String find = item.next();
					find = find.toLowerCase();
					search(find);
					login();
			  }
			  else if(userInput.equals("update")){
				  updateCreds();
				  login();
			  }
			 
			  else if(userInput.equals("logout")){				  
				  
			  }
			  else if(userInput.equals("help")){
				  login();				  
			  }
			  else{
				  System.out.println("Wrong input, please enter valid input");
				  this.login();
			  }
  }
   
  private void addInvItem() throws FileNotFoundException{
	  //Add an item to the item CSV with a marker indicating which seller is seling it
	  //Add a new item to the Seller's List of items
	  System.out.println("Please enter Item name:");
	  nameScan = new Scanner(System.in);
	  String name = nameScan.nextLine();
	  name = name.toLowerCase();
	  System.out.println("Please enter description of the item:");
	  infoScan = new Scanner(System.in); 
	  String info = infoScan.nextLine();
	  info = info.toLowerCase();
	  System.out.println("Please enter Item category (Sport, Clothes, Gaming, etc:");
	  categoryScan = new Scanner(System.in);
	  String category = categoryScan.nextLine();
	  category = category.toLowerCase();
	  System.out.println("Please enter how many you're selling");
	  quantityScan = new Scanner(System.in);
	  int quantity = quantityScan.nextInt();
	  
	  Item i = new Item(name, info, category, quantity);
	  			
	try {
		FileWriter pw = new FileWriter("bin/items.csv",true);
		StringBuilder sb = new StringBuilder();
		
		sb.append(currentUsername());
		sb.append(",");
		sb.append(name);
		sb.append(",");
		sb.append(info);
		sb.append(",");
		sb.append(category);
		sb.append(",");
		sb.append(quantity);
		sb.append("\n");
		
		pw.write(sb.toString());
		pw.close();
			
	} catch (IOException e) {
		e.printStackTrace();
	}
			
	  
	System.out.println(i.quantity + " " + i.name + " has/have been added to your inventory.");
    login();
  }
  
  private void delInvItem(){
      //Delete an item from a seller's list of items
	  //Remove completely form the item.csv
      String userResponse = "";
      boolean valid = false;
      int index = -1;
      try {
            CSVReader reader = new CSVReader(new FileReader("bin/items.csv"), ',');
            List<String[]> csvBody = reader.readAll();
            updater = new Scanner(System.in);
            while (!valid){
                 System.out.println("Which item would you like to delete?");
                 for (String[] item : csvBody){
                	 if(item[0].equals(currentUsername)){
                		 System.out.print(item[1] + ", ");
                	 }
                 }
                 userResponse = updater.next();
                 
                 for (String[] item : csvBody){
                	 if(userResponse.equals(item[1])){
                		 index = csvBody.indexOf(item);
                         valid = true;
                         break;
                     }else{
                    	 
                     }
                 }
                 
                
                 if (valid){
                     break;
                 }else{
                     System.out.print("Not a valid Item");
                 }
            }
            csvBody.remove(index);
            CSVWriter writer = new CSVWriter(new FileWriter("bin/items.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
            writer.writeAll(csvBody);
            writer.flush();
            System.out.println("It should be updated now!");
           
      }
      catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
     
  }
  
  private void updInvItem(){
	  //Update the inventory item fields- changes mad ein the csv
	    //Run the update item function on a seller's specified item.
	      String userResponse = "";
	     boolean valid = false;
	     
	      try {
	        CSVReader reader = new CSVReader(new FileReader("bin/items.csv"), ',');
	        List<String[]> csvBody = reader.readAll();
	        updater = new Scanner(System.in);
	        while (!valid){
	             System.out.println("Which item would you like to update?\n");
	             System.out.println("Your choices:");
	             for (String[] item : csvBody){
	            	 if(item[0].equals(currentUsername)){
	            		 System.out.print(item[1] + ", ");
	            	 }
	             }
	             userResponse = updater.next();
	             for (String[] item : csvBody){
	                 if(userResponse.equals(item[1])){
	                     System.out.print("\nPerfect, your item is: " + item[1]);
	                     valid = true;
	                 }
	             }
	             if (valid){
	                 break;
	             }else{
	                 System.out.println("\nNot a valid Item");
	             }
	        }
	        System.out.println("\nWhat field would you like to update?");
	        System.out.println("\nChoices: ");
	        for(String title: csvBody.get(0)){
	        	
	            System.out.print(title + ", ");
	        }
	       
	        boolean fieldValid = false;
	        int count = 0;
	        while(!fieldValid){
	            String fieldChoice = updater.next();
	            for(String title: csvBody.get(0)){
	                if(fieldChoice.equals(title.toLowerCase())){
	                    System.out.println("Valid Choice!\n");
	                    fieldValid = true;
	                }else{count++;}
	            }
	            if (fieldValid){
	             break;
	         }else{
	             count = 0;
	             System.out.print("Not a valid Choice. Try again.\n");
	             
	         }
	        }
	       
	        System.out.println("What would you like to update it to?");
	        String newField = updater.next();
	        for (String[] item : csvBody){
	            if(item[1].equals(userResponse)){
	                item[count] = newField;
	            }
	        }
	        CSVWriter writer = new CSVWriter(new FileWriter("bin/items.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
	        writer.writeAll(csvBody);
	        writer.flush();
	        System.out.println("It should be updated now!");
	       
	       
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	  }
  
 
  String currentUsername(){
	  //Show the current user logged in- tracking
	  return currentUsername;
  }
 
  private void search(String itemName) throws FileNotFoundException{
	  //Seller version of serch function to search for a specific item in the marketplace
	  BufferedReader reader = new BufferedReader(new FileReader("bin/items.csv"));
	  String answer= "";
	  try {
		  String line;
		  ArrayList<String> rows = new ArrayList<>();
		  while((line = reader.readLine())!= null){
			  rows.add(line);
		  }
		  for(int i = 0; i<rows.size(); i++){
			  String [] columns = rows.get(i).split(",");
			  if(columns[1].equals(itemName)){
				 answer = itemName + " found!  Here's the info:\n" +
						rows.get(i);
			  }
		  }
		  if(answer.equals("")){
			  System.out.println( itemName + " not currently being sold by any seller");
		  }else{
			  System.out.println(answer);
		  }
		
	}catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		  
	    
  }
  
  public void updateCreds() {
	  //Update credentials, changes made in the csv
      String userResponse;
 
      String username;
 
      boolean pass = false;
      boolean email = true;
      CSVReader reader = null;
     
          try {
              reader = new CSVReader(new FileReader("bin/creds.csv"), ',');
              List<String[]> csvBody = reader.readAll();
              updater = new Scanner(System.in);
 
              while (true) {
                  System.out.println("What would you like to update, Password or Email?");
                  userResponse = updater.next();
                  userResponse = toTitleCase(userResponse);
                  if (userResponse.equals("Password")) {
                      pass = true;
                      break;
                  } else if (userResponse.equals("Email")) {
                      email = true;
                      break;
                  } else {
                      System.out.println("Invalid Response");
                  }
              }
              System.out.println("Current Username: " + currentUsername);
              for (String[] creds : csvBody) {
                  if (currentUsername.equals(creds[1])) {
                      if (pass) {
                          System.out.println("Current Password: " + creds[2]);
                          creds[2] = newPassword();
                      } else if (email) {
                          System.out.println("Current Email: " + creds[3]);
                          creds[3] = newEmail();
                      }
                  }
              }
             
             
 
              CSVWriter writer = new CSVWriter(new FileWriter("bin/creds.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
              writer.writeAll(csvBody);
              writer.flush();
             
 
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }
     
     
  }
  private static String toTitleCase(String input) {
	  //Sring formatting
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
 
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
 
            titleCase.append(c);
        }
 
        return titleCase.toString();
    }
  private String newPassword(){
	  //Create a new password
        String newPassword;
        String confimationPassword;
       
        passGetter = new Scanner(System.in);
       
        while(true){
            System.out.print("\nPlease enter a new password: ");
            newPassword = passGetter.next();
           
            System.out.print("Please enter your password again: ");
            confimationPassword = passGetter.next();
           
            if (newPassword.equals(confimationPassword)) break;
            else System.out.print("Passwords did not match, try again!\n");
        }
       
        return newPassword;
    }
  private String newEmail(){
	  //Create a new email for current seller
        String newEmail;
        String confimationEmail;
       
        emailGetter = new Scanner(System.in);
       
        //RFC822 compliant regex for email
        Pattern ptr = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
       
        while(true){
            System.out.print("\nPlease enter a new email: ");
            newEmail = emailGetter.next();
           
            System.out.print("Please enter your email again: ");
            confimationEmail = emailGetter.next();
           
            if (newEmail.equals(confimationEmail) && ptr.matcher(newEmail).matches()) break;
            else if (!(ptr.matcher(newEmail).matches())) System.out.print("Not a legal email!\n");
            else  System.out.print("Emails did not match, try again!\n");
        }
       
        return newEmail;
    }
  private void clearMOTD(){
	  //Stop the mesage of the day and default back to the Welcome! message after the seller os notified on login
      try {
          reader3 = new CSVReader(new FileReader("bin/creds.csv"), ',');
          List<String[]> csvBody = reader3.readAll();
          for (String[] cred : csvBody){
              if(cred[1].equals(currentUsername)){
                  cred[5] = ("Welcome!");
              }
          }
         
          CSVWriter writer = new CSVWriter(new FileWriter("bin/creds.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
          writer.writeAll(csvBody);
          writer.flush();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
 
  }
 
  private String motd(){
	  //generate a message of the day for a seller who has sold items to a buyer().  Shos message on login
     
      String toReturn = "";
      try {
          reader4 = new CSVReader(new FileReader("bin/creds.csv"), ',');
          List<String[]> csvBody = reader4.readAll();
          for (String[] cred : csvBody){
              if(cred[1].equals(currentUsername)){
                  toReturn = cred[5];
              }
          }
          clearMOTD();
  }catch (FileNotFoundException e) {
      e.printStackTrace();
  } catch (IOException e) {
      e.printStackTrace();
  }
      return toReturn;
  }
}