package riri.admin.management.history;

import riri.admin.management.ManagementPage;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.model.Book;
import riri.model.Employee;
import riri.model.Transaction;
import riri.service.BookService;
import riri.service.EmployeeService;
import riri.service.TransactionService;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryPanel extends BorderPanel {
    private Map<Integer,Book> books= AppContext.BOOK_SERVICE.getAll();
    private Map<Integer,Employee> employees= AppContext.EMPLOYEE_SERVICE.getAll();
    private Map<Integer,Transaction> transactions= AppContext.TRANSACTION_SERVICE.getAll();

    private final String[] columns = {"LOẠI", "TÊN SÁCH", "TÁC GIẢ", "THỂ LOẠI", "SỐ LƯỢNG", "NGÀY", "NHÂN VIÊN", "GHI CHÚ"};
    private Object[] dataTable;

    private int hoveredRow = -1;
    public DefaultTableModel model = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(model);
    public JTable getTable() {
        return table;
    }

    private final Icon iconUp = new ImageIcon(BasePanel.createImageLogo(getClass(),"management/trending_up",18,18));
    private final Icon iconDown = new ImageIcon(BasePanel.createImageLogo(getClass(),"management/trending_down",18,18));
    private final Icon date = new ImageIcon(BasePanel.createImageLogo(getClass(),"management/date",18,18));

    public HistoryPanel() {
        super();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        loadData();
        JLabel title = BasePanel.createTitle("Lịch sử giao dịch", "Arial", Font.BOLD, 20, Color.BLACK);
        title.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(title, BorderLayout.NORTH);

        table.setRowHeight(45);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.WHITE);

        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hoveredRow = table.rowAtPoint(e.getPoint());
                table.repaint();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                table.repaint();
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, false, false, row, column);

                label.setBorder(new EmptyBorder(10, 20, 10, 10));
                label.setHorizontalAlignment(JLabel.LEFT);
                label.setOpaque(true);
                label.setBackground(row == hoveredRow ? new Color(245, 245, 245) : Color.WHITE);

                if (column == 0) {
                    label.setFont(new Font("Arial", Font.BOLD, 15));
                    if ("Nhập".equals(value)) {
                        label.setForeground(new Color(54, 253, 112));
                        label.setIcon(iconUp);
                    } else {
                        label.setForeground(new Color(255, 46, 46));
                        label.setIcon(iconDown);
                    }
                } else if (column == 4) {

                    JPanel panel = new JPanel(new GridBagLayout());
                    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    panel.setOpaque(true);
                    panel.setBorder(BorderFactory.createMatteBorder(
                            0,0,1,0,
                            row == hoveredRow ? new Color(245, 245, 245) : new Color(230, 230, 230))
                    );
                    BorderPanel titlePanel = new BorderPanel(30,null,0,0,null,0);
                    JLabel qtyLabel = new JLabel();
                    qtyLabel.setFont(new Font("Arial", Font.BOLD, 15));
                    qtyLabel.setHorizontalAlignment(JLabel.LEFT);
                    qtyLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
                    qtyLabel.setOpaque(false);

                    int qty = Integer.parseInt(value.toString());

                    if ("Nhập".equals(table.getValueAt(row, 0))) {
                        qtyLabel.setForeground(new Color(0,150,0));
                        titlePanel.setBackground(new Color(217,250,229));
                        qtyLabel.setText("+" + qty);
                    } else {
                        qtyLabel.setForeground(new Color(198,0,7));
                        titlePanel.setBackground(new Color(253,224,224));
                        qtyLabel.setText("-" + qty);
                    }

                    panel.setBackground(row == hoveredRow ? new Color(245,245,245) : Color.WHITE);

                    titlePanel.add(qtyLabel);
                    panel.add(titlePanel);
                    return panel;
                }
                else if(column == 5) {
                    label.setIcon(date);
                }
                else{
                        label.setIcon(null);
                        label.setOpaque(true);
                        label.setForeground(new Color(90, 90, 90));
                }

                return label;

            }
        });

        JTableHeader header = table.getTableHeader();
        header.setUI(new BasicTableHeaderUI());

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, false, false, row, column);

                label.setBorder(new EmptyBorder(10, 20, 10, 10));
                label.setHorizontalAlignment(JLabel.LEFT);

                label.setFont(new Font("Arial", Font.BOLD, 14));
                label.setBackground(new Color(243, 243, 243));
                label.setForeground(new Color(119, 119, 119));
                label.setOpaque(true);
                return label;
            }
        });

        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(230, 230, 230));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.setBorder(new EmptyBorder(0, 1, 10, 4));
        scroll.getViewport().setOpaque(false);

        add(scroll, BorderLayout.CENTER);
    }

    private void loadData(){
        for(Transaction transaction : transactions.values()){
            Book book = books.get(transaction.getBookId());
            Employee employee = employees.get(transaction.getEmployeeId());
            if(book != null){
                dataTable= new Object[]{
                        transaction.getType(),
                        book.getName(),
                        book.getAuthor(),
                        book.getCategory(),
                        transaction.getQuantity(),
                        transaction.getDate(),
                        employee.getName(),
                        transaction.getNote()};
                model.addRow(dataTable);
            }
        }

    }
}