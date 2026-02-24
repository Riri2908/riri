package riri.components.sidebar;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SideBarHeader extends JPanel {
    public SideBarHeader() {
        setBackground(Color.WHITE);
        setOpaque(true);

        setPreferredSize(new Dimension(SideBar.SIDEBAR_WIDTH, SideBar.SIDEBAR_HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, SideBar.SIDEBAR_HEIGHT));

        setLayout(new BorderLayout());

        setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(188, 188, 188)));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        content.setOpaque(false);

        ImageIcon icon=new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/sidebar/header.png")));
        Image icon_scaled = icon.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);

        JLabel iconHeader=new JLabel(new ImageIcon(icon_scaled));
        iconHeader.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel textPanel=new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("BookStore");
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel author = new JLabel("Admin Panel");
        author.setFont(new Font("Arial", Font.PLAIN, 13));
        author.setForeground(Color.GRAY);

        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));
        textPanel.add(author);

        content.add(Box.createHorizontalStrut(SideBar.PADDING_LEFT));
        content.add(iconHeader);
        content.add(Box.createHorizontalStrut(SideBar.MENU_PADDING_GAP+3));
        content.add(textPanel);
        add(content, BorderLayout.CENTER);

    }

}
