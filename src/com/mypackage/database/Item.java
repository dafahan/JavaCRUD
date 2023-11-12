package com.mypackage.database;

public class Item {
     private int id;
    private String name;
    private String type;
    private String price;
    
    public Item(){
        
    }
    
    public Item(String name, String type) {
        this.id = -1; // Set a default value for ID or use a constructor argument for ID
        this.name = name;
        this.type = type;
        this.price = "0";
    }
    public Item(int id,String name, String type,String price) {
        this.id = id; // Set a default value for ID or use a constructor argument for ID
        this.name = name;
        this.type = type;
        this.price = price;
    }
    public Item(String name, String type,String price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }
    // Getter and setter methods for ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Other getter and setter methods for name and description
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
