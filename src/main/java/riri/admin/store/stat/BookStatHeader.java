package riri.admin.store.stat;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BookStatHeader extends JPanel {

    public BookStatHeader() {
        setOpaque(false);
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        setPreferredSize(new Dimension(0, 70));

        // Bên trái: tiêu đề + tổng số
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Quản lý sách");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(16, 16, 16));

        int total = AppContext.BOOK_SERVICE.getAll().size();
        JLabel subtitle = new JLabel("Tổng số: " + total + " đầu sách");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(140, 140, 140));

        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(subtitle);

        // Bên phải: nút thêm sách
        BorderPanel button = new BorderPanel(16,new Color(1, 99, 255),0,0,null,0);
        button.setLayout(new BorderLayout());
        button.add(BasePanel.createTitle("Thêm sách mới", "Arial",Font.BOLD, 14, Color.WHITE));
        button.setBorder(new EmptyBorder(0,20,0,20));
        add(left,   BorderLayout.WEST);
        add(button, BorderLayout.EAST);

    }
}