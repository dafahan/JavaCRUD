
package com.mypackage.database;
import com.mypackage.database.ItemDAO;

public class Transaction {
     private int id;
    private String date;
    private int itemId;
    private int amount;
    private String totalPrice;
    private String paid;
    private String change;
    
    public Transaction(){
        
    }
    public int toInt(String s){
        return Integer.parseInt(s);
    }
    
    public Transaction(int id,int itemId, String date,int amount,String paid) {
        ItemDAO itemDAO = new ItemDAO();
        int price = toInt(itemDAO.getWhere(itemId).getPrice());
        System.out.println(paid);
        this.id = id; // Set a default value for ID or use a constructor argument for ID
        this.itemId = itemId;
        this.date = date;
        this.amount = amount;
        this.totalPrice = ""+(amount*price);
        this.paid = paid;
        this.change = ""+(toInt(paid)-toInt(totalPrice));
    }
    
    public Transaction(int itemId, String date,int amount,String paid) {
        ItemDAO itemDAO = new ItemDAO();
        int price = toInt(itemDAO.getWhere(itemId).getPrice());
        this.id = -1; // Set a default value for ID or use a constructor argument for ID
        this.itemId = itemId;
        this.date = date;
        this.amount = amount;
        this.totalPrice = ""+(amount*price);
        this.paid = paid;
        this.change = ""+(toInt(totalPrice)-toInt(paid));
    }
    
    // Getter and setter methods for ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
    

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
    
    // Other getter and setter methods for name and description
    public String getDate() {
        return date;
    }

}
