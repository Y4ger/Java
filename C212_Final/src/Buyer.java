import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Buyer{
	 //Create Scanners
	  private Scanner input;
	private CSVReader reader;
	private String currentUsername = "";
	private Scanner updater;
	private Scanner passGetter;
	private Scanner emailGetter;
	private CSVReader reader3;
	private CSVReader reader4;
	   
	    public Buyer(){ //Upon creation run login
	        try {
	            login();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	   
	    public Buyer(String username){
	        try {
	        	currentUsername = username; //Set a current username for the buyer that is logged in
	            login();
	            
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
  

  
  private void login() throws IOException{

		System.out.println("\nLogged in as Buyer\n\n");
		  
		String help = "Enter a command below (without the quotes) to continue.\n\n";
		  
		
		help += "\"view\" to view items for sale\n";
		help += "\"search\" to search for items\n";
		help += "\"buy\" to buy items in cart\n";
		help += "\"update\" to update credentials\n";
		help += "\"logout\" to logout\n\n";
		help += "\"help\" anytime to see this message again\n";
		
		System.out.println(help);  //Set up the interface and based on the user interface, run the appropriate method
		
		Scanner buyInput = new Scanner(System.in);
		String input = buyInput.next().toLowerCase();
		
		if(input.equals("view")){
			listItems();
			login();
			
		}else if(input.equals("search")){
			System.out.println("What are you looking for?");
			Scanner item = new Scanner(System.in);
			String find = item.next();
			find = find.toLowerCase();
			search(find);
			login();
			
		}else if(input.equals("buy")){
			buy();
			login();
			
		}else if(input.equals("update")){
			updateCreds();
			login();
			
		}else if(input.equals("logout")){
			
		}else if (input.equals("help")){
			login();
			
		}else{
			System.out.println("Sorry, I cannot recognize your command, please try again\n");
			login();
		}
  }
 
	

  private void buy() throws IOException {
	  //buy method will allow a Buyer to purchase a chosen item.  Throw contraint if the Buyer selects an item that doesnt exist
      System.out.println("What do you want to buy?");
      Scanner buying = new Scanner(System.in);
      String itemToBuy = buying.next();
      int quantity = 0;
      String sellerOfItem = "";
      int amountToBuy = 0;
      boolean valid = false;
      
      reader = new CSVReader(new FileReader("bin/items.csv"), ','); //Read the items.csv
      List<String[]> csvBody = reader.readAll();
      for (String[] items : csvBody) {
          if (itemToBuy.equals(items[1])) {
        	  valid = true;
        	  sellerOfItem = items[0];
              quantity = Integer.parseInt(items[4]);
              if (quantity > 0) {
                  while (true) {
                      System.out.println("How many would you like to buy?");
                      amountToBuy = buying.nextInt(); //If they buy more than available, throw constraint
                      if (amountToBuy <= quantity) {
                          items[4] = String.valueOf(quantity - amountToBuy);
                          System.out.println("You bought " + amountToBuy + " " + itemToBuy); //Tell buyer they have bought the items
                          break;
                      }else{
                    	  System.out.println("There are not that many for sale.");
                      }
                  }
              }
          }
      }
      if(!valid){buy();}
      CSVWriter writer = new CSVWriter(new FileWriter("bin/items.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
      writer.writeAll(csvBody);
      writer.flush();
      quantity = quantity - amountToBuy;
      notifySeller(sellerOfItem, itemToBuy, quantity, amountToBuy);
      history(itemToBuy, sellerOfItem); //Add to the buyer history in the Purch csv.
  }
  
  

  
  private void search(String itemName) throws FileNotFoundException{
	  //This method will search for a chosen item.
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
						rows.get(i); //If item is within the csv, return the item and the info.  Such as who sells it. 
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
  private void listItems() throws FileNotFoundException{
	  //This method will list all items that are currently being sold by all sellers in the system.  
	  BufferedReader reader = new BufferedReader(new FileReader("bin/items.csv"));
	   try {
	    String line;
	    ArrayList<String> rows = new ArrayList<>();
	    ArrayList<String> items = new ArrayList<>();
	    while((line = reader.readLine())!= null){
	    	rows.add(line);
	    	String raws[] = line.split(",");
	     	items.add(raws[1]);
	       }
	    for(String item : items){
	    	if(!(item.equals("Name"))){
	    	System.out.println(item); //From created arraylist, print all possible items to the console
	    	}
	    }
	  
	  
	  
	 }catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	 }
	
    
  }
  
  public void updateCreds() {
	  //This method allows a Buyer to update their credntials.  The CSV file will be updated accordingly. 
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
	  //Changes the first letter a capital and the rets lowercase, helps with user error checking
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
	  //helper to check pass
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
	  //helper to change pass
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
 
  private void notifySeller(String seller, String item, int quantity, int amountToBuy) throws IOException{
	  //This method will notify a seller upon their next login if a Buyer has bought an item(s) in their inventory.  Updates CSV accordingly
      try {
          reader3 = new CSVReader(new FileReader("bin/creds.csv"), ',');
          List<String[]> csvBody = reader3.readAll();
          for (String[] cred : csvBody){
              if(cred[1].equals(seller)){
                  cred[5] += (" " + amountToBuy + " " + item + "s has been sold! "+ quantity + " " + item + "s remain! ");
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
  
  /**
   * 
   * @param item
   * @param seller
   */
  private void history(String item, String seller){
	  //Creates a Buyer history, of all things been bought.
      try {
        reader4 = new CSVReader(new FileReader("bin/creds.csv"), ',');
        List<String[]> csvBody = reader4.readAll();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        for (String[] cred : csvBody){
             if(cred[1].equals(seller)){
                   cred[6] += ("("+item+" "+timeStamp+ ") ");
             }if(cred[1].equals(currentUsername)){
                   cred[6] += ("("+item+" "+timeStamp+ ") ");
             }
        }
        CSVWriter writer = new CSVWriter(new FileWriter("bin/creds.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
        writer.writeAll(csvBody);
        writer.flush();
      }catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}