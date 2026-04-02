package riri.admin.customers.item;

import riri.components.BorderPanel;
import riri.components.combobox.ComboBoxPanel;
import riri.components.field.FieldPanel;
import riri.components.page.BasePanel;
import riri.model.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomerForm extends BorderPanel {

    public FieldPanel nameCustomerField = new FieldPanel("");
    public FieldPanel phoneField = new FieldPanel("");
    public FieldPanel emailField = new FieldPanel("");
    public FieldPanel noteField = new FieldPanel("");
    public ComboBoxPanel<String> typeCustomerField = new ComboBoxPanel<>();
    public FieldPanel totalPriceField = new FieldPanel("");
    public FieldPanel totalOrderField =  new FieldPanel("");

    public JLabel infomationTitle = BasePanel.createTitle("","Arial",Font.BOLD,14,Color.BLACK);
    public BorderPanel BUTTON_EXIT_CLOSE = new BorderPanel(16,Color.WHITE,0,0,new Color(220,220,220),1);
    public BorderPanel BUTTON_CANCEL = new BorderPanel(16,null,0,0,new Color(220,220,220),1);;
    public BorderPanel BUTTON_COMPLETE =  new BorderPanel(16,null,0,0,new Color(220,220,220),1);;

    public CustomerForm(){
        super(16,Color.WHITE,0,0,new Color(220,220,220),1);

        setLayout(null);
        setBounds(200, 100, 400, 450);

        this.setBounds(200, 100, 400, 550);
        this.setVisible(false);
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10,10,10,10));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

        BorderPanel field = new BorderPanel(0,Color.WHITE,0,0,null,0);
        field.setBorder(new EmptyBorder(10,0,10,0));
        field.setLayout(new BoxLayout(field,BoxLayout.Y_AXIS));

        field.add(BasePanel.createItem("Họ và tên", nameCustomerField));
        field.add(Box.createVerticalStrut(10));
        field.add(BasePanel.createItem("Số điện thoại", phoneField));
        field.add(Box.createVerticalStrut(10));
        field.add(BasePanel.createItem("Email", emailField));
        field.add(Box.createVerticalStrut(10));
        field.add(BasePanel.createItem("Ghi chú", noteField));
        field.add(Box.createVerticalStrut(10));
        field.add(BasePanel.createItem("Loại khách hàng", typeCustomerField));
        field.add(Box.createVerticalStrut(10));
        field.add(BasePanel.createItem("Tổng đơn mua", totalOrderField));
        field.add(Box.createVerticalStrut(10));
        field.add(BasePanel.createItem("Tổng tiền đã mua", totalPriceField));

        this.add(titleInfoPanel(), BorderLayout.NORTH);
        this.add(field,BorderLayout.CENTER);
        this.add(BUTTON_CANCEL_COMPLETE("Hủy",Color.WHITE,"Hoàn tất",new Color(66, 125, 255)),BorderLayout.SOUTH);

    }

    public BorderPanel titleInfoPanel(){
        BorderPanel titlePanel = new BorderPanel(0,Color.WHITE,0,0,null,0);
        titlePanel.setPreferredSize(new Dimension(0,24));
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,24));

        titlePanel.setLayout(new BorderLayout());


        BUTTON_EXIT_CLOSE.setLayout(new BorderLayout());
        BUTTON_EXIT_CLOSE.setBorder(new EmptyBorder(5,5,5,5));
        JLabel textButton = BasePanel.createTitle("X","Arial",Font.BOLD,14,Color.BLACK);
        BUTTON_EXIT_CLOSE.add(textButton,BorderLayout.CENTER);

        titlePanel.add(infomationTitle,BorderLayout.WEST);
        titlePanel.add(BUTTON_EXIT_CLOSE,BorderLayout.EAST);

        return titlePanel;
    }

    public BorderPanel BUTTON_CANCEL_COMPLETE(String textButton1, Color colorButton1,String textButton2,Color colorButton2){
        BorderPanel buttonPanel = new BorderPanel(16,Color.WHITE,0,0,null,0);
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        buttonPanel.setPreferredSize(new Dimension(0,48));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,48));

        this.BUTTON_CANCEL.setLayout(new BorderLayout());
        this.BUTTON_CANCEL.setBackground(colorButton1);
        JLabel text1 = BasePanel.createTitle(textButton1,"Arial",Font.BOLD,14,Color.BLACK);
        text1.setHorizontalAlignment(SwingConstants.CENTER);
        this.BUTTON_CANCEL.add(text1, BorderLayout.CENTER);

        this.BUTTON_COMPLETE.setLayout(new BorderLayout());
        this.BUTTON_COMPLETE.setBackground(colorButton2);
        JLabel text2 = BasePanel.createTitle(textButton2,"Arial",Font.BOLD,14,Color.BLACK);
        text2.setHorizontalAlignment(SwingConstants.CENTER);
        this.BUTTON_COMPLETE.add(text2, BorderLayout.CENTER);

        buttonPanel.add(BUTTON_CANCEL);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(BUTTON_COMPLETE);

        return buttonPanel;
    }

    public void showPlaceholder(){
        nameCustomerField.showPlaceholder();
        phoneField.showPlaceholder();
        emailField.showPlaceholder();
        noteField.showPlaceholder();
        typeCustomerField.showPlaceholder();
        totalOrderField.showPlaceholder();
        totalPriceField.showPlaceholder();
    }

    public void showPlaceholder(Customer customer){
        nameCustomerField.showPlaceholder(customer.getName(),Color.BLACK);
        phoneField.showPlaceholder(customer.getPhone(),Color.BLACK);
        emailField.showPlaceholder(customer.getEmail(),Color.BLACK);
        noteField.showPlaceholder(customer.getNote(),Color.BLACK);
        typeCustomerField.showPlaceholder(customer.getIdType());
        totalOrderField.showPlaceholder(String.valueOf(customer.getTotalOrders()),Color.BLACK);
        totalPriceField.showPlaceholder(String.valueOf(customer.getTotalPrice()),Color.BLACK);
    }
}
