package riri.admin.invoice.item.view;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.table.CustomRenderer;
import riri.components.table.TablePanel;
import riri.model.Customer;
import riri.model.Invoice;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

public class ListInvoiceTable extends BorderPanel {
    public Map<Integer, Invoice> invoices = AppContext.INVOICE_SERVICE.getAll();

    public InformationPanel informationPanel;
    public ShoppingCart shoppingCart;

    public CustomRenderer renderer = new CustomRenderer();
    public TablePanel tablePanel = new TablePanel();

    public ListInvoiceTable(InformationPanel informationPanel, ShoppingCart shoppingCart) {
        super(0, new Color(247, 248, 249),0,0,null,0);

        this.informationPanel = informationPanel;
        this.shoppingCart = shoppingCart;

        setLayout(new BorderLayout());

        tablePanel.setTitle("Danh sách hóa đơn");

        tablePanel.addColumn(0,"MÃ HĐ");
        tablePanel.addColumn(1,"NGÀY");
        tablePanel.addColumn(2,"KHÁCH HÀNG");
        tablePanel.addColumn(3,"SĐT");
        tablePanel.addColumn(4,"SỐ LƯỢNG");
        tablePanel.addColumn(5,"TỔNG TIỀN");
        tablePanel.addColumn(6,"TRẠNG THÁI");

        renderer.setDefaultSetting((label,row)->{

            boolean hover = row == tablePanel.getHoveredRow();

            label.setBorder(new EmptyBorder(10,20,10,10));

            label.setBackground(hover ? new Color(245,245,245) : Color.WHITE);

            label.setBorder(BorderFactory.createMatteBorder(
                    0,0,1,0,
                    hover ? new Color(245,245,245) : new Color(230,230,230)
            ));

            label.setHorizontalAlignment(JLabel.CENTER);
            label.setForeground(new Color(57,57,57));

            label.setIcon(null);
            label.setFont(new Font("Arial",Font.PLAIN,14));

            label.setOpaque(true);

            return label;
        });

        renderer.setHeaderSetting(label -> {
            label.setBorder(new EmptyBorder(10,0,10,0));
            label.setBackground(new Color(247, 248, 249));
            label.setForeground(new Color(140, 140, 140));
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            return label;
        });

        renderer.addLabel(0,(label,row)->{
            label.setFont(new Font("Arial", Font.BOLD, 14));
            return label;
        });

        renderer.addLabel(1,(label,row)->{
           label.setIcon(new ImageIcon(BasePanel.createImageLogo(getClass(),"management/date",18,18)));
            return label;
        });

        renderer.addLabel(5,(label, row)->{
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(new Color(17, 156, 40));
            return  label;
        });

        renderer.addLabel(6,(label,row)->{
            boolean hoverRow = row == tablePanel.getHoveredRow();

            BorderPanel panel =  new BorderPanel(0,new Color(255, 255, 255),0,0,null, 0);
            panel.setBackground(hoverRow ? new Color(245,245,245) : Color.WHITE);
            panel.setLayout(new GridBagLayout());

            panel.setBorder(BorderFactory.createMatteBorder(
                    0,0,1,0,
                    hoverRow ? new Color(245,245,245) : new Color(230,230,230)));

            BorderPanel badge = new BorderPanel(
                    14,
                    new Color(209, 240, 218), // nền nhẹ
                    0,0,null,0
            );
            badge.setLayout(new BorderLayout());
            badge.setBorder(new EmptyBorder(3,10,3,10));

            JLabel lb = new JLabel(label.getText());

            lb.setOpaque(false);
            lb.setHorizontalAlignment(JLabel.CENTER);
            lb.setFont(new Font("Arial", Font.BOLD, 13));
            lb.setForeground(new Color(17,156,40));

            badge.add(lb, BorderLayout.CENTER);

            panel.add(badge);

            return panel;
        });
        renderer.applyHeader(tablePanel.getTable());

        tablePanel.setRenderer(renderer);

        loadData();

        add(tablePanel, BorderLayout.NORTH);
    }

    public void loadData(){
        List<Invoice> invoiceList = new ArrayList<>(invoices.values());
        Collections.reverse(invoiceList);

        for(Invoice invoice:invoiceList){
            Customer customer = AppContext.CUSTOMER_SERVICE.findById(invoice.getCustomerId());
            tablePanel.addRow(new Object[]{
                    "INV"+invoice.getId(),
                    String.valueOf(invoice.getDate()),
                    customer.getName(),
                    customer.getPhone(),
                    invoice.getDetails().isEmpty() ? 0 : invoice.getDetails().size() ,
                    invoice.getTotalAmount()+" đ",
                    "Hoàn thành"
            });
        }
    }

    public void updateData(){
        tablePanel.getModel().setRowCount(0);
        loadData();
    }
}
