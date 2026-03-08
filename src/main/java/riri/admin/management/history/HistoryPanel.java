package riri.admin.management.history;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.table.CustomRenderer;
import riri.components.table.TablePanel;
import riri.model.Book;
import riri.model.Employee;
import riri.model.Transaction;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import java.util.Map;

public class HistoryPanel extends BorderPanel {
    private static final Map<Integer,Book> books = AppContext.BOOK_SERVICE.getAll();
    private static final Map<Integer,Employee> employees = AppContext.EMPLOYEE_SERVICE.getAll();
    private static final Map<Integer,Transaction> transactions = AppContext.TRANSACTION_SERVICE.getAll();

    public TablePanel tablePanel = new TablePanel();
    CustomRenderer renderer = new CustomRenderer();


    private final Icon iconUp = new ImageIcon(BasePanel.createImageLogo(getClass(),"management/trending_up",18,18));
    private final Icon iconDown = new ImageIcon(BasePanel.createImageLogo(getClass(),"management/trending_down",18,18));
    private final Icon date = new ImageIcon(BasePanel.createImageLogo(getClass(),"management/date",18,18));

    public HistoryPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        tablePanel.setTitle("Lịch sử giao dịch");

        tablePanel.addColumn(0,"ID");
        tablePanel.addColumn(1,"LOẠI");
        tablePanel.addColumn(2,"TÊN SÁCH");
        tablePanel.addColumn(3,"TÁC GIẢ");
        tablePanel.addColumn(4,"THỂ LOẠI");
        tablePanel.addColumn(5,"SỐ LƯỢNG");
        tablePanel.addColumn(6,"NGÀY");
        tablePanel.addColumn(7,"NHÂN VIÊN");
        tablePanel.addColumn(8,"GHI CHÚ");

        renderer.addLabel(1,(table,value,isSelected,hasFocus,row,column)->{

            JLabel label = new JLabel(value.toString());
            label.setBorder(new EmptyBorder(10,20,10,10));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font("Arial",Font.BOLD,15));

            if("Nhập".equals(value)){
                label.setForeground(new Color(54,253,112));
                label.setIcon(iconUp);
            }else{
                label.setForeground(new Color(255,46,46));
                label.setIcon(iconDown);
            }

            return label;
        });

        renderer.addLabel(2,(table,value,isSelected,hasFocus,row,column)->{

            JLabel label = new JLabel(value.toString());
            label.setBorder(new EmptyBorder(10,20,10,10));
            label.setFont(new Font("Arial",Font.BOLD,15));
            label.setForeground(new Color(16,16,16));

            return label;
        });

        renderer.addLabel(5,(table,value,isSelected,hasFocus,row,column)->{

            boolean hover = row == tablePanel.getHoveredRow();

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setOpaque(true);
            panel.setBackground(hover ? new Color(245,245,245) : Color.WHITE);

            panel.setBorder(BorderFactory.createMatteBorder(
                    0,0,1,0,
                    hover ? new Color(245,245,245) : new Color(230,230,230)
            ));

            BorderPanel titlePanel = new BorderPanel(30,null,0,0,null,0);

            JLabel qtyLabel = new JLabel();
            qtyLabel.setFont(new Font("Arial",Font.BOLD,15));
            qtyLabel.setBorder(new EmptyBorder(0,10,0,10));

            int qty = Integer.parseInt(value.toString());

            if("Nhập".equals(table.getValueAt(row,1))){
                qtyLabel.setForeground(new Color(0,150,0));
                titlePanel.setBackground(new Color(217,250,229));
                qtyLabel.setText("+"+qty);
            }else{
                qtyLabel.setForeground(new Color(198,0,7));
                titlePanel.setBackground(new Color(253,224,224));
                qtyLabel.setText("-"+qty);
            }

            titlePanel.add(qtyLabel);
            panel.add(titlePanel);

            return panel;
        });

        renderer.addLabel(6,(table,value,isSelected,hasFocus,row,column)->{

            JLabel label = new JLabel(value.toString());
            label.setBorder(new EmptyBorder(10,20,10,10));
            label.setIcon(date);

            return label;
        });

        tablePanel.setRenderer(renderer);

        loadData();

        add(tablePanel, BorderLayout.CENTER);

    }



    private void loadData(){

        for(Transaction transaction : transactions.values()){

            Book book = books.get(transaction.getBookId());
            Employee employee = employees.get(transaction.getEmployeeId());

            tablePanel.addRow(new Object[]{
                    transaction.getId(),
                    transaction.getType(),
                    book.getName(),
                    book.getAuthor(),
                    book.getCategory(),
                    transaction.getQuantity(),
                    transaction.getDate(),
                    employee.getName(),
                    transaction.getNote()
            });
        }
    }
    public TablePanel getTable(){
        return this.tablePanel;
    }


}