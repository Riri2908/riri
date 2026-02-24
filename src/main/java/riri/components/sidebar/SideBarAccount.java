package riri.components.sidebar;

import  riri.components.page.BasePanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class SideBarAccount extends JPanel {

    public  SideBarAccount() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(SideBar.SIDEBAR_WIDTH,SideBar.SIDEBAR_HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE,SideBar.SIDEBAR_HEIGHT));
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0 ,new Color(188, 188, 188)));

        JLabel icon = new JLabel(new ImageIcon(BasePanel.createImageLogo(getClass(), "sidebar/account", 24, 24)));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(BasePanel.createTitle("Admin User", "Arial",Font.BOLD, 15, Color.BLACK));
        textPanel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));
        textPanel.add(BasePanel.createTitle("admin@bookstore.com", "Arial",Font.PLAIN, 12, Color.GRAY));

        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(SideBar.SIZE_BUTTON);
        contentPanel.setMaximumSize(SideBar.MAX_SIZE_BUTTON);

        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.setBorder(new EmptyBorder(
                0,
                SideBar.MENU_PADDING_LEFT+12,
                0,
                0));

        contentPanel.add(icon);
        contentPanel.add(Box.createHorizontalStrut(SideBar.MENU_PADDING_LEFT));
        contentPanel.add(textPanel);

        JButton btn=new JButton();
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.setBackground(new Color(244, 244, 244));
        btn.setPreferredSize(SideBar.SIZE_BUTTON);
        btn.setMaximumSize(SideBar.MAX_SIZE_BUTTON);

        btn.add(contentPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(SideBar.SIZE_BUTTON);
        buttonPanel.setMaximumSize(SideBar.MAX_SIZE_BUTTON);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(
                SideBar.MENU_PADDING_TOP,
                SideBar.MENU_PADDING_LEFT,
                SideBar.MENU_PADDING_BOTTOM,
                SideBar.MENU_PADDING_RIGHT));

        buttonPanel.add(btn);


        add(buttonPanel,BorderLayout.CENTER);
    }
}
