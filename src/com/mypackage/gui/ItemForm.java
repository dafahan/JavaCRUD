package com.mypackage.gui;

import com.mypackage.database.Item;
import com.mypackage.database.ItemDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ItemForm extends JPanel {
    private ItemDAO itemDAO;
    private JTextField itemNameField, itemPriceField, itemTypeField;

    public ItemForm() {
        itemDAO = new ItemDAO();

       
        JLabel itemNameLabel = new JLabel("Item Name:");
        itemNameField = new JTextField(20);
        JLabel itemTypeLabel = new JLabel("Type:");
        itemTypeField = new JTextField(50);
        JLabel itemPriceLabel = new JLabel("Price:");
        itemPriceField = new JTextField(50);

        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = itemNameField.getText();
                String price = itemPriceField.getText();
                String type = itemTypeField.getText();
                Item newItem = new Item(name, type,price);
                itemDAO.addItem(newItem);
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
        formPanel.add(itemNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(itemNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(itemTypeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(itemTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(itemPriceLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(itemPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Make the button span 2 columns
        formPanel.add(addButton, gbc);

        // Set up the panel
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
    }
    
   

    private void clearForm() {
        itemNameField.setText("");
        itemTypeField.setText("");
        itemPriceField.setText("");
    }
}
  