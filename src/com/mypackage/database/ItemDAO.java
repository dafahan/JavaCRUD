package com.mypackage.database;


import java.io.InputStream;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
public class ItemDAO {
    private Connection connection;
    public ItemDAO() {
       
        String url = "jdbc:mysql://localhost:3306/penjualan";
        String user = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    public List<Item> UpdateItem(int id,String name,String type,String price){
         List<Item> items = new ArrayList<>();
        String updateQuery = "UPDATE item SET name = ? ,type = ?, price = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, name);
            statement.setString(2, type);
            statement.setString(3,price);
            statement.setInt(4,id);
            statement.executeUpdate();
           
        String query = "SELECT * FROM item";
        try (PreparedStatement statements = connection.prepareStatement(query); ResultSet resultSets = statements.executeQuery()) {

            while (resultSets.next()) {
                int itemId = resultSets.getInt("id"); // Replace "id" with your column name
                String names = resultSets.getString("name"); // Replace "name" with your column name
                String types = resultSets.getString("type"); // Replace "type" with your column name
                String prices = resultSets.getString("price"); // Replace "type" with your column name
                System.out.println(names+" "+types+" "+prices);
                Item item = new Item(itemId, names, type,prices);
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
       

        
            
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        System.out.println(items);
        return items;
    }
    public void deleteItem(int id) {
        String deleteQuery = "DELETE FROM item WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);

            // Execute the delete query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Item with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Item with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   public void addItem(Item item) {
        String insertQuery = "INSERT INTO item (name, type,price) VALUES (?, ?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getType());
            statement.setString(3,item.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
     public Item getWhere(int id) {
    Item item = null;
    String selectQuery = "SELECT * FROM item WHERE id = ?";
    
    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
        preparedStatement.setInt(1, id);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String price = resultSet.getString("price");
                item = new Item(id, name, type, price);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return item;
}   
     public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM item";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int itemId = resultSet.getInt("id"); // Replace "id" with your column name
                String name = resultSet.getString("name"); // Replace "name" with your column name
                String type = resultSet.getString("type"); // Replace "type" with your column name
                String price = resultSet.getString("price"); // Replace "type" with your column name
                System.out.println(name+" "+type+" "+price);
                Item item = new Item(itemId, name, type,price);
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
       

        return items;
    }
     public void generateReport() {
        try {
          // Load JRXML template
          // Load JRXML template
        InputStream templateStream = getClass().getResourceAsStream("/com/mypackage/database/resources/template.jrxml");

          // Compile Jasper report template
          JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

          // Fetch data from the database
          List<Item> items = getAllItems();

          // Create data source
          JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);

          // Prepare parameters
          Map<String, Object> parameters = new HashMap<>();

          // Fill the report
          JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

          // Export the report to a PDF file (you can change the format as needed)
          JasperExportManager.exportReportToPdfFile(jasperPrint, "src/resources/report.pdf");

          System.out.println("Report generated successfully.");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
}

