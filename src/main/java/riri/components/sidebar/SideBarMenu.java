package riri.components.sidebar;

import riri.components.page.BasePanel;
import riri.components.page.ContentPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SideBarMenu extends JPanel {
    public ContentPanel contentPanel;

    public SideBarItem homepage;
    public SideBarItem dashBoard;
    public SideBarItem store = new SideBarItem("Quản lý sách", "book", null);
    public SideBarItem management = new SideBarItem("Quản lý nhập kho", "package", null);
    public SideBarItem invoice = new SideBarItem("Hóa đơn bán hàng", "bill", null);
    public SideBarItem customer = new SideBarItem("Khách hàng", "users", null);

    public SideBarMenu(ContentPanel contentPanel) {
        this.contentPanel = contentPanel;

        this.homepage = new SideBarItem("Trang chủ", "home", contentPanel);
        this.dashBoard = new SideBarItem("Thống kê", "dashboard", contentPanel);
        this.store = new SideBarItem("Quản lý sách", "book", contentPanel);
        this.management = new SideBarItem("Quản lý nhập kho", "package", contentPanel);
        this.invoice = new SideBarItem("Hóa đơn bán hàng", "bill", contentPanel);
        this.customer = new SideBarItem("Khách hàng", "users", contentPanel);

        contentPanel.registerMenu("Trang chủ", homepage);
        contentPanel.registerMenu("Thống kê", dashBoard);
        contentPanel.registerMenu("Quản lý sách", store);
        contentPanel.registerMenu("Quản lý nhập kho", management);
        contentPanel.registerMenu("Hóa đơn bán hàng", invoice);
        contentPanel.registerMenu("Khách hàng", customer);

        setOpaque(false);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(
                SideBar.MENU_PADDING_TOP,
                SideBar.MENU_PADDING_LEFT,
                SideBar.MENU_PADDING_BOTTOM,
                SideBar.MENU_PADDING_RIGHT
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        panel.add(homepage);
        panel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));
        panel.add(dashBoard);
        panel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));
        panel.add(store);
        panel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));
        panel.add(management);
        panel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));
        panel.add(invoice);
        panel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));
        panel.add(customer);

        JScrollPane scrollPane = BasePanel.createScroll(panel);
        add(scrollPane, BorderLayout.CENTER);
    }
}
