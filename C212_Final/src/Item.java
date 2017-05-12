import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Item{
 
  int quantity;
  String name;
  String itemInformation;
  String category;
  private Scanner userInp;
  private Scanner changer;
  
  public Item(String _name, String information, String _category, int _quantity){
    name = _name;
    itemInformation = information;
    category = _category;
    quantity = _quantity;
  }
  
  private void add(int numberOfItemsBeingAdded){
	  //This method will add a specified number of items to the quantity of the Item.  
	  this.quantity += numberOfItemsBeingAdded;
    
  }
  
  public void updateInfo() {
	  //This method will allow the user to update the item description, category, or information- making public so seller can access
	  while(true){
	  System.out.println("What would you like to update?");
	  System.out.println("Name: 'name' \nItem Information: 'info'\n Item Category 'category' \nTo get out of this page: 'end'");
	  userInp = new Scanner(System.in);
	  String inp = userInp.nextLine();
	  if(inp.equals("name")){
		  changer = new Scanner(System.in);
		  System.out.println("Please input the new Item name:");
		  String newName = changer.nextLine();
		  this.name = newName;
		  break;
	  }
	  else if(inp.equals("info")){
		  changer = new Scanner(System.in);
		  System.out.println("Please input the new Item information:");
		  String newInfo = changer.nextLine();
		  this.itemInformation = newInfo;
		  break;
	  }
	  else if(inp.equals("category")){
		  changer = new Scanner(System.in);
		  System.out.println("Please input the new Item category:");
		  String newCategory = changer.nextLine();
		  this.category = newCategory;
		  break;
	  }
	  else if(inp.equals("end")){
		  break;
	  }
	  else{
		  System.out.println("Please enter a correct input");
		  
		  
	  }
	  
	  }
  }
  
  private void delete(){
	  //This method will allow the user to take items out of inventory, or completely delete an item.  
    
  }
  
  private void notifySeller(){
	  //This method will notify a seller when a buyer buys a selected amount of an item.  
    
  }
  
}