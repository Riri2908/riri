package riri.admin.invoice;

import riri.admin.invoice.information.InformationPanel;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InvoicePanel extends JPanel {

    public BorderPanel invoice;
    public BorderPanel invoiceList;

    public CardLayout cardLayout = new CardLayout();
    public GridBagConstraints gbc = new GridBagConstraints();
    public String tilte;

    public InvoicePanel() {
        setOpaque(true);
        setBackground(new Color(247, 248, 249));
        setLayout(cardLayout);
        setFocusable(true);
        setBorder(new EmptyBorder(25, 25, 25, 25));


        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        invoice = new BorderPanel(0, new Color(247, 248, 249), 0, 0, null, 0);
        invoice.setOpaque(false);

        invoice.setLayout(new GridBagLayout());

        addItem(invoice,BasePanel.createTitle("Tạo hóa đơn bán hàng","Arial", Font.BOLD, 25,Color.BLACK),0,0,6);
        addItem(invoice,createButton("Xem danh sách hóa đơn","Danh sách hóa đơn"),8,0,1);
        addItem(invoice,new InformationPanel(),0,1,6);

        invoiceList = new BorderPanel(0, new Color(247, 248, 249), 0, 0, null, 0);
        invoiceList.setLayout(new GridBagLayout());

        invoiceList.add(createButton("Tạo hóa đơn","Hóa đơn"));

        add(invoice,"Hóa đơn");
        add(invoiceList, "Danh sách hóa đơn");

    }

    public String getTilte() {
        return this.tilte;
    }

    public void setTilte(String title) {
        this.tilte = title;
    }

    public void addItem(JPanel panel , Component item, int x, int y, int weight){
        this.gbc.weightx = weight;
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        panel.add(item,gbc);
        this.gbc.weightx=1;
    }

    private BorderPanel createButton(String title,String constructor) {
        BorderPanel button = new BorderPanel(16,new Color(51, 113, 250),0,0,null,0);
        button.setBorder(new EmptyBorder(10,0,10,0));
        button.add(BasePanel.createTitle(title,"Arial",Font.BOLD,14,Color.BLACK));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPageButton(constructor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 91, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(51, 113, 250));
            }


        });
        return button;
    }


    public void showPageButton(String constructor){
        cardLayout.show(this,constructor);
    }
}


