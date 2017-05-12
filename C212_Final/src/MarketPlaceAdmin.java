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

public class MarketPlaceAdmin{
	
	private Scanner input;
	private String currentUsername = "";
	private Scanner updater;
	private Scanner passGetter;
	private Scanner emailGetter;
	private CSVReader reader2;
	private CSVReader reader3;
  
  public MarketPlaceAdmin() throws FileNotFoundException{
    login();
  }
  
  private void login(){
	  //Login as admin and view interface for an admin
	  System.out.println("\nLogged in as Admin\n\n");
	  
		String help = "Enter a command below (without the quotes) to continue.\n\n";
			  
		help += "\"buyers\" to view all buyers\n";	
		help += "\"sellers\" to view all sellers\n";
		help += "\"items\" to view items for sale\n";
		help += "\"history_b\" to view a purchase history\n";
		help += "\"history_s\" to view a sale history\n";
		help += "\"search\" to search for items\n";
		help += "\"other_creds\" to update another user's credentials\n";
		help += "\"logout\" to logout\n";
		help += "\"help\" anytime to see this message again\n";
			
		System.out.println(help); 
		
		Scanner adminInput = new Scanner(System.in);
		String userInput = adminInput.next().toLowerCase();
		
		
			  if(userInput.equals("buyers")){
				  try {
					listBuyers();
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				  login();
			  }
			  else if(userInput.equals("sellers")){
				  try {
					listSellers();
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				  login();
			  }
			  else if(userInput.equals("items")){
				  try {
					listItems();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  login();
			  }
			  else if(userInput.equals("history_b")){
				  purchaseHistory();
				  login();
			  }
			  else if(userInput.equals("history_s")){
				  purchaseHistory();
				  login();
			  }
			  else if(userInput.equals("search")){
				  System.out.println("What are you lookingh for?");
					Scanner item = new Scanner(System.in);
					String find = item.next();
					find = find.toLowerCase();
					try {
						search(find);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					login();
				  
			  }
			  else if(userInput.equals("other_creds")){
				  try {
					updateOtherCreds();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
  private void listBuyers() throws FileNotFoundException{
	  //List all of the buyers within the csv files
	  BufferedReader reader = new BufferedReader(new FileReader("bin/creds.csv"));
	   try {
	    String line;
	    ArrayList<String> rows = new ArrayList<>();
	    ArrayList<String> buyers = new ArrayList<>();
	    while((line = reader.readLine())!= null){
	    	rows.add(line);
	    	String raws[] = line.split(",");
	        if(raws[4].equals("Buyer")){  
	           buyers.add(raws[1]);
	        }
	       }
	    for(String buyer : buyers){
	    	System.out.println(buyer);
	    }
	  
	  
	  
	 }catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	 }
	}
	
    
  
  
  private void listSellers() throws FileNotFoundException{
	  //List all of the sellers within the csv files
	  BufferedReader reader = new BufferedReader(new FileReader("bin/creds.csv"));
	   try {
	    String line;
	    ArrayList<String> rows = new ArrayList<>();
	    ArrayList<String> sellers = new ArrayList<>();
	    while((line = reader.readLine())!= null){
	    	rows.add(line);
	    	String raws[] = line.split(",");
	        if(raws[4].equals("Seller")){  
	           sellers.add(raws[1]);
	        }
	       }
	    for(String seller : sellers){
	    	System.out.println(seller);
	    }
	  
	  
	  
	 }catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	 }
    
  }
  
  
  
  private void listItems() throws FileNotFoundException{
	  //List all of the items in the items csv
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
	    	System.out.println(item);
	    	}
	    }
	  
	  
	  
	 }catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	 }
	
    
  }
  
  private void purchaseHistory(){
	  //SHow purchase history of a particular buyer
      String userResponse;
      boolean valid = false;
      Scanner chooser = new Scanner(System.in);
    try {

          
           reader3 = new CSVReader(new FileReader("bin/creds.csv"), ',');
          
           List<String[]> csvBody = reader3.readAll();
           while(!valid){
                 System.out.println("Which users history do you want to search:");
                 userResponse = chooser.next();
                 for(String[] cred : csvBody){
                       if(userResponse.equals(cred[1])){
                              valid = true;
                              System.out.println("History:\n"+ cred[6]);
                              break;
                        }
                 }
                
                 if (valid){
                  break;
            }else{
                  System.out.println("Not a valid Username\n");
            }
                
           }
          
          
    } catch (FileNotFoundException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
    } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
    }
   
   
 
  }
  

  
  
  
  private void search(String itemName) throws FileNotFoundException{
	  //Search for an item, same as the others, but run as admin
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
  private void updateOtherCreds() throws IOException{
	  //Update ANY users creds.  Only accessible as admin
      String userResponse;
      String userToChange = "";
  boolean valid = false;
   
    Scanner chooser = new Scanner(System.in);
   
    while (!valid){
        System.out.println("Which user would you like to update credentials for? ");
        userToChange = chooser.next();
        reader2 = new CSVReader(new FileReader("bin/creds.csv"), ',');
        List<String[]> csvBody = reader2.readAll();
       String user = "Invalid Username, try again";
        for (String[] cred : csvBody){
            if(cred[1].equals(userToChange)){
                System.out.println("Valid user.");
                valid = true;
                user = cred[1];
                break;
            }
        }
        System.out.println(user);
        
    }
   
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
           
            System.out.println("Changing Username for: " + userToChange);
            for (String[] creds : csvBody) {
                if (userToChange.equals(creds[1])) {
                    if (pass) {
                        System.out.println("Current password: "+creds[2]);
                        creds[2] = newPassword();
                    } else if (email) {
                        System.out.println("Current Email: "+ creds[3]);
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
        	//String formatting
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
    	  //Create a new password for admin
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
    	  //Create a new email for admin
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
}