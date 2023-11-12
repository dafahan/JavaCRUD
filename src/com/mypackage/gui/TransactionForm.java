package com.mypackage.gui;
import com.toedter.calendar.JDateChooser;
import com.mypackage.database.Item;
import com.mypackage.database.ItemDAO;
import com.mypackage.database.Transaction;
import com.mypackage.database.TransactionDAO;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionForm extends JPanel {
    private TransactionDAO transactionDAO;
    private ItemDAO itemDAO;
    private JTextField amountField,paidField;

    
     private class Opt {
        private int value;
        private String displayText;

        public Opt(int value, String displayText) {
            this.value = value;
            this.displayText = displayText;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return displayText;
        }
    } 
     private static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    public TransactionForm() {
        transactionDAO = new TransactionDAO();
        itemDAO = new ItemDAO();
        List<Item> listItems = new ArrayList<>();
        listItems = itemDAO.getAllItems();
        DefaultComboBoxModel<Opt> comboBoxModel = new DefaultComboBoxModel<>();

        for (Item item : listItems) {
            comboBoxModel.addElement(new Opt(item.getId(), item.getName()));
        }
        
        JComboBox<Opt> comboBox = new JComboBox<>(comboBoxModel);
        
        JLabel itemLabel = new JLabel("Item Name:");
        JLabel dateLabel = new JLabel("Date:");
        JDateChooser dateChooser = new JDateChooser();
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(50);
        JLabel paidtLabel = new JLabel("Paid:");
        paidField = new JTextField(50);
        JButton addButton = new JButton("Add Transaction");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedValue=-1;
                Opt selectedOpt = (Opt) comboBox.getSelectedItem();
                if (selectedOpt != null) {
                     selectedValue = selectedOpt.getValue();
                    String selectedDisplayText = selectedOpt.toString();

                    System.out.println("Selected Value: " + selectedValue);
                    System.out.println("Selected Display Text: " + selectedDisplayText);
                } else {
                    System.out.println("No item selected");
                }
                int itemId = selectedValue;
                Date selectedDate = dateChooser.getDate();
                String date = formatDate(selectedDate);
                int amount =  Integer.parseInt(amountField.getText());
                String paid = paidField.getText();
                Transaction newItem = new Transaction(itemId, date,amount,paid);
                transactionDAO.addItem(newItem);
                clearForm();
            }
        });
        
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Align components to the right
       

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(itemLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(comboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(amountLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(amountField, gbc);
        
         gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(paidtLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(paidField, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Make the button span 2 columns
        formPanel.add(addButton, gbc);

        // Set up the panel
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
    }
    
   

    private void clearForm() {
        amountField.setText("");
        paidField.setText("");
    }
}
