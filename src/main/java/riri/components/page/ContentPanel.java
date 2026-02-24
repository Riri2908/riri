package riri.components.page;

import riri.admin.dashboard.DashBoardPage;
import riri.admin.management.ManagementPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ContentPanel extends JPanel {

    private final CardLayout cardLayout=new CardLayout();
    private final JPanel textPanel;
    private final TopBar topBar=new TopBar("DashBoard");

    public ContentPanel() {
        setOpaque(true);
        setPreferredSize(new Dimension(600,600));
        setLayout(new BorderLayout());
        setBackground(new Color(247, 248, 249));

        this.textPanel=new JPanel();
        textPanel.setLayout(cardLayout);
        textPanel.add(new DashBoardPage(), "DashBoard");
        textPanel.add(new JLabel("Quản lý sách"), "Quản lý sách");
        textPanel.add(new ManagementPage(), "Quản lý tồn kho");
        textPanel.add(new JLabel("Hóa đơn"), "Hóa đơn");
        textPanel.add(new JLabel("Khách hàng"), "Khách hàng");

        add(topBar,BorderLayout.NORTH);
        add(textPanel,BorderLayout.CENTER);
    }
    public void showPage(String page) {
        topBar.setTitle(page);
        cardLayout.show(textPanel, page);
    }

}
