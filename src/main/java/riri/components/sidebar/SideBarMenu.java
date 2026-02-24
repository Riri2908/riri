package riri.components.sidebar;

import riri.components.page.BasePanel;
import riri.components.page.ContentPanel;
import riri.components.page.TopBar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SideBarMenu extends JPanel {

    public SideBarMenu(ContentPanel  contentPanel) {

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

        panel.add(new SideBarItem("DashBoard","dashboard", contentPanel));
        panel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));

        panel.add(new SideBarItem("Quản lý sách","book", contentPanel));
        panel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));

        panel.add(new SideBarItem("Quản lý tồn kho", "package", contentPanel));
        panel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));

        panel.add(new SideBarItem("Hóa đơn", "bill", contentPanel));
        panel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));

        panel.add(new SideBarItem("Khách hàng","users", contentPanel));

        JScrollPane scrollPane = BasePanel.createScroll(panel);

        add(scrollPane, BorderLayout.CENTER);
    }
}
