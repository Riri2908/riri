package riri.admin.invoice.page;

import riri.components.BorderPanel;
import riri.components.combobox.ComboBoxPanel;
import riri.components.field.FieldPanel;
import riri.components.page.BasePanel;
import riri.components.spinner.SpinnerPanel;
import riri.model.Book;
import riri.model.Customer;
import riri.model.CustomerType;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class InformationPanel extends BorderPanel {
    public Map<Integer, CustomerType> customerTypeList = AppContext.CUSTOMER_TYPE_SERVICE.getAll();
    public Map<Integer,Customer> customerList = AppContext.CUSTOMER_SERVICE.getAll();

    protected final GridBagConstraints gbc = new GridBagConstraints();
    public final CardLayout customerCard = new CardLayout();
    public final CardLayout customerTypeCard = new CardLayout();

    public JLabel titleLabel;

    public ComboBoxPanel customerBox = new ComboBoxPanel();
    public BorderPanel customerPanel = new BorderPanel(16, Color.WHITE,0,0,new Color(214, 214, 214),1);
    public FieldPanel customerField = new FieldPanel("Nhập tên khách hàng mới... (Ấn ESC để quay về)");
    public ComboBoxPanel customerTypeBox = new ComboBoxPanel();
    public BorderPanel customerTypePanel = new BorderPanel(16, Color.WHITE,0,0,new Color(214, 214, 214),1);
    public FieldPanel customerTypeField = new FieldPanel("Nhập tên khách hàng mới... (Ấn ESC để quay về)");
    public FieldPanel phoneField = new FieldPanel("Nhập số điện thoại");
    public SpinnerPanel voucher = new SpinnerPanel();

    public InformationPanel(){

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        titleLabel = BasePanel.createTitle("Thông tin khách hàng","Segue UI",Font.BOLD, 18, new Color(25, 25, 25));
        titleLabel.setBorder(new EmptyBorder(0,0,10,0));

        gbc.insets = new Insets(0,0,15,20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        addItem(titleLabel,0,0);
        addItem(BasePanel.createItem("Chọn khách hàng", customerBox()),0,1);
        addItem(BasePanel.createItem("Số điện thoại",phoneField),1,1);
        addItem(BasePanel.createItem("Loại khách hàng",customerTypeBox() ),0,2);
        addItem(BasePanel.createItem("Chiếc khấu (%)", voucher),1,2);

        customerField.getField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    customerCard.show(customerPanel,"customerBox");
                    e.consume();
                }
            }
        });

        customerTypeField.getField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    customerTypeCard.show(customerTypePanel,"customerTypeBox");
                    e.consume();
                }
            }
        });

        fillCustomerInfo();
    }

    public void addItem(Component component, int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        add(component,gbc);
    }

    private BorderPanel customerBox(){

        customerPanel.setLayout(customerCard);

        this.customerBox.addItem("--Chọn khách hàng--");

        for(Customer customer: customerList.values()){
            this.customerBox.addItem(customer.getName());
        }
        this.customerBox.addItem("--Thêm khách hàng mới--");

        customerPanel.add(customerBox.getComboBox(),"customerBox");
        customerPanel.add(customerField,"customerField");

        return this.customerPanel;
    }

    private BorderPanel customerTypeBox(){

        customerTypePanel.setLayout(customerTypeCard);

        for(CustomerType customerType: customerTypeList.values()){
            this.customerTypeBox.addItem(customerType.getName());
        }
        this.customerTypeBox.addItem("--Thêm loại khách hàng--");

        customerTypePanel.add(customerTypeBox.getComboBox(),"customerTypeBox");
        customerTypePanel.add(customerTypeField,"customerTypeField");
        return this.customerTypePanel;
    }

    private void fillCustomerInfo(){

        customerBox.getComboBox().addActionListener(_->{
            String customerName = (String) customerBox.getComboBox().getSelectedItem();
            if(customerName==null || customerName.equalsIgnoreCase("--Chọn khách hàng--")){
                showPlaceholder();
                return;
            }

            if( customerName.equalsIgnoreCase("--Thêm khách hàng mới--")){
                customerCard.show(customerPanel,"customerField");
                showPlaceholder();
                customerTypeCard.show(customerTypePanel,"customerTypeField");
                return;
            }

            Customer customer = AppContext.CUSTOMER_SERVICE.findByName(customerName);

            showPlaceholder(customer);
        });
    }

    public void showPlaceholder(){
        customerField.showPlaceholder();
        phoneField.showPlaceholder();
        customerTypeCard.show(customerTypePanel,"customerTypeBox");
        customerTypeBox.showPlaceholder();
        voucher.showPlaceholder();
    }

    public void showPlaceholder(Customer customer){
        customerField.showPlaceholder(customer.getName(),Color.BLACK);
        phoneField.showPlaceholder(customer.getPhone(),Color.BLACK);
        customerTypeCard.show(customerTypePanel,"customerTypeBox");
        customerTypeBox.showPlaceholder(customer.getIdType());
        CustomerType customerType = AppContext.CUSTOMER_TYPE_SERVICE.findById(customer.getIdType());
        int discountRate = (int) (customerType.getDiscountRate() * 100);
        voucher.showPlaceholder(discountRate);
    }


    public void loadData(){

    }

}
