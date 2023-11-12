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

public class MultiButtonTable {

    public static void main(String[] args) {
        new MultiButtonTable();
    }

    public MultiButtonTable() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                MyTableModel model = new MyTableModel();
                model.add(new Data(1, "AD", "Blah 1"));
                model.add(new Data(2, "SD", "Blah 2"));
                model.add(new Data(3, "PD", "Blah 3"));
                model.add(new Data(4, "DD", "Blah 4"));
                model.add(new Data(5, "MD", "Blah 5"));

                JTable table = new JTable(model);
                AcceptRejectRenderer renderer = new AcceptRejectRenderer();
                table.getColumnModel().getColumn(3).setCellRenderer(renderer);
                table.getColumnModel().getColumn(3).setCellEditor(new AcceptRejectEditor());
                table.setRowHeight(renderer.getTableCellRendererComponent(table, null, true, true, 0, 0).getPreferredSize().height);

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new JScrollPane(table));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class Data {

        private int id;
        private String name;
        private String application;

        public Data(int id, String name, String application) {
            this.id = id;
            this.name = name;
            this.application = application;
        }

        public int getID() {
            return id;
        }

        public void setID(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApplication() {
            return application;
        }

        public void setApplication(String application) {
            this.application = application;
        }
    }

    public class MyTableModel extends AbstractTableModel {

        private List<Data> data;

        public MyTableModel() {
            data = new ArrayList<>(25);
        }

        @Override
        public String getColumnName(int column) {
            String value = null;
            switch (column) {
                case 0:
                    value = "ID";
                    break;
                case 1:
                    value = "Name";
                    break;
                case 2:
                    value = "Application for leave";
                    break;
                case 3:
                    value = "Accept/Reject";
                    break;
            }
            return value;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class value = Object.class;
            switch (columnIndex) {
                case 0:
                    value = Integer.class;
                    break;
                case 1:
                    value = String.class;
                    break;
                case 2:
                    value = String.class;
                    break;
            }
            return value;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Data obj = data.get(rowIndex);
            Object value = null;
            switch (columnIndex) {
                case 0:
                    value = obj.getID();
                    break;
                case 1:
                    value = obj.getName();
                    break;
                case 2:
                    value = obj.getApplication();
                    break;
                case 3:
                    break;
            }
            return value;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 3) {

                System.out.println(aValue);

                Data value = data.get(rowIndex);
                if ("accept".equals(aValue)) {
                    System.out.println("Accepted");
                } else {
                    System.out.println("Rejected");
                }
                fireTableCellUpdated(rowIndex, columnIndex);
                remove(value);

            }
        }

        public void add(Data value) {
            int startIndex = getRowCount();
            data.add(value);
            fireTableRowsInserted(startIndex, getRowCount() - 1);
        }

        public void remove(Data value) {
            int startIndex = data.indexOf(value);
            System.out.println("startIndex = " + startIndex);
            data.remove(value);
            fireTableRowsInserted(startIndex, startIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 3;
        }
    }

    public class AcceptRejectPane extends JPanel {

        private JButton accept;
        private JButton reject;
        private String state;

        public AcceptRejectPane() {
            setLayout(new GridBagLayout());
            accept = new JButton("Accept");
            accept.setActionCommand("accept");
            reject = new JButton("Reject");
            reject.setActionCommand("reject");

            add(accept);
            add(reject);

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                }
            };

            accept.addActionListener(listener);
            reject.addActionListener(listener);
        }

        public void addActionListener(ActionListener listener) {
            accept.addActionListener(listener);
            reject.addActionListener(listener);
        }

        public String getState() {
            return state;
        }
    }

    public class AcceptRejectRenderer extends DefaultTableCellRenderer {

        private AcceptRejectPane acceptRejectPane;

        public AcceptRejectRenderer() {
            acceptRejectPane = new AcceptRejectPane();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                acceptRejectPane.setBackground(table.getSelectionBackground());
            } else {
                acceptRejectPane.setBackground(table.getBackground());
            }
            return acceptRejectPane;
        }
    }

    public class AcceptRejectEditor extends AbstractCellEditor implements TableCellEditor {

        private AcceptRejectPane acceptRejectPane;

        public AcceptRejectEditor() {
            acceptRejectPane = new AcceptRejectPane();
            acceptRejectPane.addActionListener(new ActionListener() {
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
            return acceptRejectPane.getState();
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                acceptRejectPane.setBackground(table.getSelectionBackground());
            } else {
                acceptRejectPane.setBackground(table.getBackground());
            }
            return acceptRejectPane;
        }
    }
}