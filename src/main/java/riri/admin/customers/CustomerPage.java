package riri.admin.customers;

import riri.admin.customers.controller.CustomerController;
import riri.admin.customers.item.CustomerForm;
import riri.admin.customers.item.TableCustomer;
import riri.admin.customers.item.TitleCustomerPanel;
import riri.admin.invoice.InvoicePage;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.page.StatPanel;
import riri.model.Customer;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class CustomerPage extends BorderPanel {

    public Map<Integer, Customer> customers = AppContext.CUSTOMER_SERVICE.getAll();
    public InvoicePage invoicePage;

    public BorderPanel mainPanel = new BorderPanel(0,new Color(247, 248, 249),0,0,null,0);
    public JLayeredPane layeredPane = new JLayeredPane();

    public CustomerPage(InvoicePage invoicePage) {
        super(0,new Color(247, 248, 249),0,0,null,0);

        this.invoicePage = invoicePage;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
            }
        });

        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(25,25,25,25));

        CustomerForm customerForm = new CustomerForm();
        TableCustomer tableCustomer = new TableCustomer(this);
        TitleCustomerPanel titleCustomerPanel = new TitleCustomerPanel("Quản lý khách hàng","+ Thêm khách hàng",this,tableCustomer);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        mainPanel.add(statusPanel());
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(titleCustomerPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(tableCustomer);
        mainPanel.setBounds(0, 0, 600, 400);

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(customerForm, JLayeredPane.PALETTE_LAYER);

        add(layeredPane,BorderLayout.CENTER);

        CustomerController _ = new CustomerController(this,customerForm,tableCustomer,titleCustomerPanel);
    }

    public JPanel statusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        statusPanel.setLayout(new BoxLayout(statusPanel,BoxLayout.X_AXIS));

        double total = AppContext.CUSTOMER_SERVICE.totalPrice();
        double totalOrder = AppContext.CUSTOMER_SERVICE.totalOrders();

        int customerCount = customers.size();

        double avg = customerCount == 0 ? 0 : total / totalOrder;

        BorderPanel customerStat = new StatPanel("Tổng khách hàng",String.valueOf(customerCount),"khách hàng",new Color(0, 48, 255),"customer/customer");
        BorderPanel revenueStat = new StatPanel("Tổng doanh thu", BasePanel.formatNumber((long) total),"từ khách hàng",new Color(0, 200, 80),"customer/shoppingbag");
        BorderPanel revenueArgStat = new StatPanel("Giá trị đơn TB",BasePanel.formatNumber((long) avg),"mỗi đơn",new Color(172, 70, 253),"customer/shoppingbag");

        statusPanel.add(customerStat);
        statusPanel.add(Box.createHorizontalStrut(15));
        statusPanel.add(revenueStat);
        statusPanel.add(Box.createHorizontalStrut(15));
        statusPanel.add(revenueArgStat);

        statusPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusPanel.getPreferredSize().height));
        return statusPanel;
    }

}
