package com.mypackage;

import com.mypackage.gui.ItemForm;
import com.mypackage.gui.ItemTable;
import com.mypackage.gui.TransactionForm;
import com.mypackage.gui.TransactionTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;



public class Main {
    private static JFrame frame;
    private static JPanel sidebarPanel;
    private static JPanel contentPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("CRUD Table");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen

            // Create a sidebar panel with BoxLayout
            sidebarPanel = new JPanel();
            sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
            sidebarPanel.setBackground(Color.BLACK);

            // Create menu items for the sidebar
            JButton itemFormButton = createSidebarButton("Item Form");
            JButton transactionFormButton = createSidebarButton("Transaction Form");
            JButton itemTableButton = createSidebarButton("Item Table");
            JButton transactionTableButton = createSidebarButton("Transaction Table");

            // Add action listeners to open corresponding forms
            itemFormButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showItemForm();
                }
            });
            
            transactionFormButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showTransactionForm();
                }
            });

            itemTableButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Show ItemTable or any other form as needed
                    showItemTable();
                }
            });
            transactionTableButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Show ItemTable or any other form as needed
                    showTransactionTable();
                }
            });

            // Add menu items to the sidebar panel with spacing
            sidebarPanel.add(Box.createVerticalGlue()); // Add flexible space
            sidebarPanel.add(itemFormButton);
            sidebarPanel.add(Box.createVerticalGlue()); // Add flexible space
            sidebarPanel.add(transactionFormButton);
            sidebarPanel.add(Box.createVerticalGlue()); // Add flexible space
            sidebarPanel.add(itemTableButton);
            sidebarPanel.add(Box.createVerticalGlue()); // Add flexible space
            sidebarPanel.add(transactionTableButton);
            sidebarPanel.add(Box.createVerticalGlue()); // Add flexible space
            // Create a content panel with BorderLayout
            contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(sidebarPanel, BorderLayout.WEST);

            // Set the content panel as the content pane of the frame
            frame.setContentPane(contentPanel);

            frame.setVisible(true);
        });
    }

    private static JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 40));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        return button;
    }

    private static void showItemForm() {
        ItemForm itemForm = new ItemForm();

        // Remove existing components from the right side
        contentPanel.removeAll();

        // Add ItemForm to the right side
        contentPanel.add(itemForm, BorderLayout.CENTER);

        // Add the sidebarPanel to the left side
        contentPanel.add(sidebarPanel, BorderLayout.WEST);

        // Revalidate and repaint the frame
        frame.revalidate();
        frame.repaint();
    }
    
    private static void showTransactionForm() {
        TransactionForm itemForm = new TransactionForm();

        // Remove existing components from the right side
        contentPanel.removeAll();

        // Add ItemForm to the right side
        contentPanel.add(itemForm, BorderLayout.CENTER);

        // Add the sidebarPanel to the left side
        contentPanel.add(sidebarPanel, BorderLayout.WEST);

        // Revalidate and repaint the frame
        frame.revalidate();
        frame.repaint();
    }

    private static void showItemTable() {
        // Create a new ItemTable
        ItemTable itemTablePanel = new ItemTable();
        JPanel itemTable = itemTablePanel.getItemTable();
        
        contentPanel.removeAll();
        // Add the sidebarPanel to the left side
        contentPanel.add(sidebarPanel, BorderLayout.WEST);
        contentPanel.add(itemTable, BorderLayout.CENTER);

        // Revalidate and repaint the frame
        frame.revalidate();
        frame.repaint();
        }
    private static void showTransactionTable() {
        // Create a new ItemTable
        TransactionTable itemTablePanel = new TransactionTable();
        JPanel itemTable = itemTablePanel.getItemTable();
        
        contentPanel.removeAll();
        // Add the sidebarPanel to the left side
        contentPanel.add(sidebarPanel, BorderLayout.WEST);
        contentPanel.add(itemTable, BorderLayout.CENTER);

        // Revalidate and repaint the frame
        frame.revalidate();
        frame.repaint();
        }
}
