package riri.admin.customers.item;

import riri.admin.customers.CustomerPage;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitleCustomerPanel extends BorderPanel {

    private final int HEIGHT_TITLEPANEL = 35;
    private final int WEIGHT_BUTTON = 200;

    public BorderPanel BUTTON_ADD = new BorderPanel(16,new Color(51, 113, 250),0,0,null,0);

    public CustomerPage customerPage;
    public TableCustomer tableCustomer;

    public String tilte;
    public String textButton;

    public TitleCustomerPanel(String title, String textButton, CustomerPage customerPage, TableCustomer tableCustomer) {
        super(0,new Color(247, 248, 249),0,0,null,0);
        this.tilte = title;
        this.textButton = textButton;
        this.customerPage = customerPage;
        this.tableCustomer = tableCustomer;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, HEIGHT_TITLEPANEL));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, HEIGHT_TITLEPANEL));

        add(BasePanel.createTitle(title,"Arial", Font.BOLD, 25,Color.BLACK),BorderLayout.WEST);

        add(createButton(textButton),BorderLayout.EAST);
    }

    private BorderPanel createButton(String title) {

        BUTTON_ADD.setPreferredSize(new Dimension(WEIGHT_BUTTON, HEIGHT_TITLEPANEL));
        BUTTON_ADD.setMaximumSize(new Dimension(WEIGHT_BUTTON, HEIGHT_TITLEPANEL));
        BUTTON_ADD.setLayout(new BorderLayout());
        BUTTON_ADD.setBorder(new EmptyBorder(10,0,10,0));

        JLabel titleLabel = BasePanel.createTitle(title,"Arial",Font.BOLD,14,Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        BUTTON_ADD.add(titleLabel,BorderLayout.CENTER);

        return BUTTON_ADD;
    }

}
