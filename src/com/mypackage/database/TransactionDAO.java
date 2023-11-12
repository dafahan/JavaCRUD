package com.mypackage.database;
import com.mypackage.database.Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TransactionDAO {
    private Connection connection;
    public TransactionDAO() {
       
        String url = "jdbc:mysql://localhost:3306/penjualan";
        String user = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    public void deleteTransaction(int id) {
        String deleteQuery = "DELETE FROM transaction WHERE id = ?";

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
    public void addItem(Transaction item) {
        String insertQuery = "INSERT INTO transaction ( itemId,date,amount,paid) VALUES ( ?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
          
            statement.setInt(1, item.getItemId());
            statement.setString(2,item.getDate());
            statement.setInt(3,item.getAmount());
            statement.setString(4,item.getPaid());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    public List<Transaction> getAllItems() {
        List<Transaction> items = new ArrayList<>();
        String query = "SELECT * FROM transaction";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                
                int id = resultSet.getInt("id");
                int itemId = resultSet.getInt("itemId"); // Replace "id" with your column name
                String date = resultSet.getString("date"); // Replace "name" with your column name
                int amount = resultSet.getInt("amount"); // Replace "type" with your column name
                String paid = resultSet.getString("paid"); // Replace "type" with your column name
                System.out.println(itemId+" "+date+" "+amount+" "+paid);
                Transaction item = new Transaction(id,itemId, date, amount,paid);
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return items;
    }
}