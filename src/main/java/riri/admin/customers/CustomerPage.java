package riri.admin.customers;

import riri.admin.customers.controller.CustomerController;
import riri.admin.customers.view.CustomerForm;
import riri.admin.customers.view.StatCardPanel;
import riri.admin.customers.view.TableCustomer;
import riri.admin.customers.view.TitleCustomerPanel;
import riri.admin.invoice.InvoicePage;
import riri.components.BorderPanel;
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
        StatCardPanel statCardPanel = new StatCardPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        mainPanel.add(statCardPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(titleCustomerPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(tableCustomer);
        mainPanel.setBounds(0, 0, 600, 400);

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(customerForm, JLayeredPane.PALETTE_LAYER);

        add(layeredPane,BorderLayout.CENTER);

        CustomerController _ = new CustomerController(this,statCardPanel,customerForm,tableCustomer,titleCustomerPanel);
    }


}
