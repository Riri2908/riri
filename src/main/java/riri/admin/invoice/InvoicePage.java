package riri.admin.invoice;

import riri.admin.invoice.item.invoice.InformationPanel;
import riri.admin.invoice.item.invoice.ListPanel;
import riri.admin.invoice.item.invoice.ShoppingCart;
import riri.admin.invoice.item.invoice.TotalAmountPanel;
import riri.admin.invoice.item.invoicelist.ListInvoiceTable;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InvoicePage extends JPanel {
    public InformationPanel informationPanel = new InformationPanel(null);
    public TotalAmountPanel totalAmountPanel = new TotalAmountPanel(null,informationPanel,null);
    public ListPanel listPanel = new ListPanel(totalAmountPanel);
    public ShoppingCart shoppingCart = new ShoppingCart(listPanel, totalAmountPanel);
    public ListInvoiceTable listInvoiceTable = new ListInvoiceTable(informationPanel, shoppingCart);

    public BorderPanel invoice = new BorderPanel(0, new Color(247, 248, 249), 0, 0, null, 0);

    public BorderPanel invoiceList;

    public CardLayout cardLayout = new CardLayout();
    private final JPanel root = new JPanel();

    public InvoicePage() {
        this.totalAmountPanel.listPanel = listPanel;
        this.informationPanel.totalAmountPanel = totalAmountPanel;
        this.totalAmountPanel.listInvoiceTable = listInvoiceTable;

        setLayout(new BorderLayout());
        root.setOpaque(true);
        root.setBackground(new Color(247, 248, 249));
        root.setLayout(cardLayout);
        root.setFocusable(true);
        root.setBorder(new EmptyBorder(25, 25, 25, 25));
        root.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });

        invoice.setLayout(new BorderLayout(0, 15));
        invoice.add(new TitlePanel("Tạo hóa đơn bán hàng", "Xem danh sách hóa đơn", "Xem danh sách hóa đơn", this), BorderLayout.NORTH);
        invoice.add(customerPanel(), BorderLayout.CENTER);
        invoice.add(totalAmountPanel, BorderLayout.EAST);

        invoiceList = new BorderPanel(0, new Color(247, 248, 249), 0, 0, null, 0);
        invoiceList.setOpaque(false);
        invoiceList.setLayout(new BorderLayout(0,15));

        invoiceList.add(new TitlePanel("Danh sách hóa đơn", "Tạo hóa đơn mới", "Tạo hoá đơn", this),BorderLayout.NORTH);
        invoiceList.add(listInvoiceTable, BorderLayout.CENTER);

        root.add(invoice, "Tạo hoá đơn");
        root.add(invoiceList, "Xem danh sách hóa đơn");

        JScrollPane panel = BasePanel.createScroll(root);
        add(panel, BorderLayout.CENTER);

    }

    //Trang để tính toán hóa đơn
    public BorderPanel customerPanel() {
        BorderPanel customerPanel = new BorderPanel(0, new Color(247, 248, 249), 0, 0, null, 0) {
            @Override
            public Component add(Component comp) {
                addImpl(comp, null, -1);
                addImpl(Box.createVerticalStrut(15), null, -1);
                return comp;
            }
        };

        customerPanel.setBorder(new EmptyBorder(0, 0, 0, 25));
        customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));


        customerPanel.add(informationPanel);
        customerPanel.add(shoppingCart);
        customerPanel.add(listPanel);
        add(customerPanel, BorderLayout.CENTER);
        return customerPanel;
    }

    public void showPageButton(String constructor) {
        cardLayout.show(root, constructor);
    }
}


