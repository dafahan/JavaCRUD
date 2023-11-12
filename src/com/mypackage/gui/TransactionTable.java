package com.mypackage.gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import com.mypackage.database.ItemDAO;
import com.mypackage.database.TransactionDAO;
import com.mypackage.database.Transaction;
import com.mypackage.database.Item;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.FileOutputStream;
import java.util.Observer;
import java.util.Observable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TransactionTable implements Observer {
    private TransactionDAO transactionDAO;
    private JTable table;
    private JPanel panel;
    private EditDeleteRenderer renderer = new EditDeleteRenderer();
    public static void main(String[] args) {
        
    }
     public TransactionDAO getItemDAO() {
        return transactionDAO;
    }
    public JPanel getItemTable() {
        return panel;
    }
    public TransactionTable() {
        
                transactionDAO = new TransactionDAO();
                List<Transaction> items = transactionDAO.getAllItems();
               MyTableModel model = new MyTableModel(items);
              model.addObserver(this);

                 table = new JTable(model);
                table.getColumnModel().getColumn(7).setCellRenderer(renderer);
                table.getColumnModel().getColumn(7).setCellEditor(new EditDeleteEditor());
                table.setRowHeight(renderer.getTableCellRendererComponent(table, null, true, true, 0, 0).getPreferredSize().height);
                JScrollPane scrollPane = new JScrollPane(table);
                int panelWidth = 500; // Replace this with your desired width
                int panelHeight = 300; // Replace this with your desired height
                scrollPane.setPreferredSize(new Dimension(panelWidth, panelHeight));
                DefaultTableCellRenderer renderers = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
                renderers.setHorizontalAlignment( SwingConstants.CENTER );
                table.getColumnModel().getColumn(0).setCellRenderer(renderers); 
                
                 JButton printButton = new JButton("Print to PDF");
            printButton.addActionListener(e -> {
                try {
                    printTableToPDF(table, "output.pdf");
                    JOptionPane.showMessageDialog(panel, "PDF created successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
                // Create the panel and add the scroll pane to it
                panel = new JPanel(new BorderLayout());
                panel.add(printButton,BorderLayout.SOUTH);
                panel.add(scrollPane, BorderLayout.CENTER);
                
                //itemDAO.generateReport();
    }

private static void printTableToPDF(JTable table, String filePath) throws Exception {
    Document document = new Document(PageSize.A4.rotate());
    PdfWriter.getInstance(document, new FileOutputStream(filePath));
    document.open();

    PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
    for (int i = 0; i < table.getColumnCount(); i++) {
        pdfTable.addCell(table.getColumnName(i));
    }

    for (int i = 0; i < table.getRowCount(); i++) {
        for (int j = 0; j < table.getColumnCount(); j++) {
            Object value = table.getValueAt(i, j);
            pdfTable.addCell(value != null ? value.toString() : ""); // Check for null
        }
    }

    document.add(pdfTable);
    document.close();
}


public class TransactionFormEdit extends JFrame {
    private int id; 
    
    private JTextField nameField;
    private JTextField typeField;
    private JTextField priceField;
    private JButton saveButton;
    private ItemDAO itemDAO;
    private  JPanel contentPanel;
    
    public TransactionFormEdit(int id) {
        this.id = id;
        initializeComponents();
        setLayout();
        setListeners();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the frame on exit
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void initializeComponents() {
        
        itemDAO = new ItemDAO();
        Item item = itemDAO.getWhere(id);
        
        nameField = new JTextField(20);
        nameField.setText(""+item.getName());
        typeField = new JTextField(20);
        typeField.setText(""+item.getType());
        priceField = new JTextField(20);
        priceField.setText(""+item.getPrice());
        saveButton = new JButton("Save");
    }

    private void setLayout() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.insets = new Insets(5, 5, 5, 5); // Padding
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST; // Align components to the right
    panel.add(new JLabel("ID = " + id), gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("Name:"), gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("Type:"), gbc);
    gbc.gridx = 1;
    panel.add(typeField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(new JLabel("Price:"), gbc);
    gbc.gridx = 1;
    panel.add(priceField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2; // Empty label as a placeholder
    panel.add(new JLabel(), gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2; // Reset grid width
    panel.add(saveButton, gbc);

    contentPanel = new JPanel(new BorderLayout());
    contentPanel.add(panel, BorderLayout.CENTER);
    add(contentPanel);
}

    private void setListeners() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the save action
                //saveItemChanges();
            }
        });
    }

//    private void saveItemChanges() {
//        List<Transaction> items = itemDAO.UpdateItem(id, nameField.getText(), typeField.getText(), priceField.getText());
//    MyTableModel model = new MyTableModel(items);
//    table.setModel(model); // Update the model of the existing table
//    table.getColumnModel().getColumn(4).setCellRenderer(renderer);
//    table.getColumnModel().getColumn(4).setCellEditor(new EditDeleteEditor());
//     DefaultTableCellRenderer renderers = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
//    table.getColumnModel().getColumn(0).setCellRenderer(renderers); 
//                
//    dispose();
//    }

    
}


    
public class MyTableModel extends AbstractTableModel {

    private List<Transaction> items;
    private String[] columnNames = {"ID","Item ID", "Date", "Amount", "Total Price", "Paid","Change","Aksi"};
    private List<Observer> observers = new ArrayList<>();

    public MyTableModel(List<Transaction> items) {
        this.items = items;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(null, this);
        }
    }


        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class value = Object.class;
            switch (columnIndex) {
                case 0:
                    value = Integer.class;
                    break;
                case 1:
                    value = Integer.class;
                    break;
                case 2:
                    value = String.class;
                    break;
                case 3:
                    value = Integer.class;
                    break;
                case 4:
                    value = String.class;
                    break;
                case 5:
                    value = String.class;
                    break;
                case 6:
                    value = String.class;
                    break;
                
            }
            return value;
        }
        
        @Override
        public String getColumnName(int column) {
        return columnNames[column];
        }
        
        @Override
        public int getRowCount() {
            return items.size();
        }

        @Override
         public int getColumnCount() {
        return columnNames.length;
        }

        @Override
        //{"ID","Item ID", "Date", "Amount", "Total Price", "Paid","Change"};
        public Object getValueAt(int rowIndex, int columnIndex) {
            Transaction obj = items.get(rowIndex);
            Object value = null;
            switch (columnIndex) {
                case 0:
                    value =(int) obj.getId();
                    break;
                case 1:
                    value = obj.getItemId();
                    break;
                case 2:
                    value = obj.getDate();
                    break;
                case 3:
                    value = obj.getAmount();
                    break;
                case 4:
                    value = obj.getTotalPrice();
                    break;
                case 5:
                    value = obj.getPaid();
                    break;
                case 6:
                    value = obj.getChange();
                    break;
            }
            return value;
        }

        @Override
       
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 4) {
            Transaction value = items.get(rowIndex);

            if ("Edit".equals(aValue)) {
                System.out.println((int) getValueAt(rowIndex, 0));
                System.out.println("Accepted");
               // TransactionItemFormEdit editForm = new TransactionItemFormEdit((int) getValueAt(rowIndex, 0));
                // Notify observers about the update
                
            } else {
                transactionDAO.deleteTransaction((int) getValueAt(rowIndex, 0));
                System.out.println("Rejected");
                remove(value);
                // Notify observers about the update
                notifyObservers();
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

       
        public void add(Transaction value) {
            int startIndex = getRowCount();
            items.add(value);
            fireTableRowsInserted(startIndex, getRowCount() - 1);
        }

        public void remove(Transaction value) {
            int startIndex = items.indexOf(value);
            
            items.remove(value);
            fireTableRowsInserted(startIndex, startIndex);
        }
        
        private void refreshTable() {
        System.out.println("refresh");
        
         List<Transaction> newitems = transactionDAO.getAllItems();
               MyTableModel newmodel = new MyTableModel(newitems);

                JTable newTable = new JTable(newmodel);
                table = newTable;
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 4;
        }
    }

    public class EditDeletePane extends JPanel {

    private JButton edit;
    private JButton delete;
    private String state;
    private int itemId; // store the item ID

    public EditDeletePane() {
        setLayout(new GridBagLayout());
        edit = new JButton("Edit");
        edit.setActionCommand("Edit");
        delete = new JButton("Delete");
        delete.setActionCommand("Delete");

        //add(edit);
        add(delete);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = e.getActionCommand();
               
                if ("Delete".equals(state)) {
                    // Call the deleteItem method from ItemDAO
                    if (itemId > 0) {
                        transactionDAO.deleteTransaction(itemId);
                        
                        refreshTable();
                    }
                }
                System.out.println("State = " + state);
            }
        };

        edit.addActionListener(listener);
        delete.addActionListener(listener);
    }

    public void addActionListener(ActionListener listener) {
        edit.addActionListener(listener);
        delete.addActionListener(listener);
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getState() {
        return state;
    }

    private void refreshTable() {
        System.out.println("refresh");
         List<Transaction> items = transactionDAO.getAllItems();
               MyTableModel newmodel = new MyTableModel(items);

                JTable newTable = new JTable(newmodel);
                table = newTable;
    }
}


    public class EditDeleteRenderer extends DefaultTableCellRenderer {

        private EditDeletePane editDeletePane;

        public EditDeleteRenderer() {
            editDeletePane = new EditDeletePane();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                editDeletePane.setBackground(table.getSelectionBackground());
            } else {
                editDeletePane.setBackground(table.getBackground());
            }
            return editDeletePane;
        }
    }

    public class EditDeleteEditor extends AbstractCellEditor implements TableCellEditor {

        private EditDeletePane editDeletePane;

        public EditDeleteEditor() {
            editDeletePane = new EditDeletePane();
            editDeletePane.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            stopCellEditing();
                        }
                    });
                }
            });
        }

        @Override
        public Object getCellEditorValue() {
            return editDeletePane.getState();
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                
                editDeletePane.setBackground(table.getSelectionBackground());
            } else {
                editDeletePane.setBackground(table.getBackground());
            }
            return editDeletePane;
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        
        System.out.println("Table updated");
    }
    
}
