package riri.admin.invoice.information;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InformationPanel extends BorderPanel {
    private final int HEIGHT_PANEL=160;

    public InformationPanel(){

        setMaximumSize(new Dimension(Integer.MAX_VALUE, HEIGHT_PANEL));
        setPreferredSize(new Dimension(0, HEIGHT_PANEL));
        setBorder(new EmptyBorder(25,25,25,25));
        setLayout(new BorderLayout());


        JPanel information = new JPanel();
        information.setOpaque(false);
        information.setLayout(new BorderLayout());
        information.add(BasePanel.createTitle("Thông tin khách hàng","Arial",Font.BOLD,15,Color.BLACK),BorderLayout.NORTH);
        add(information,BorderLayout.CENTER);
    }
}
