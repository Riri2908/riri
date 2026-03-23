package riri.admin.store;

// Import đúng file ListBookPanel của bạn
import riri.admin.store.listbook.ListBookPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BookPage extends JPanel {

    public BookPage() {
        setOpaque(true);
        setBackground(new Color(247, 248, 249));
        setLayout(new BorderLayout());
        setFocusable(true);

        JPanel panel = new JPanel();

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });

        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setFocusable(true);

        // ==========================================
        // KHỞI TẠO CÁC THÀNH PHẦN
        // ==========================================

        // Gọi ListBookPanel thay vì StoreTablePanel
        ListBookPanel listBookPanel = new ListBookPanel();

        // ==========================================
        // LẮP RÁP VÀO TRANG TỔNG
        // ==========================================

        panel.add(listBookPanel);

        // Bọc vào thanh cuộn
        JScrollPane scrollPanel = BasePanel.createScroll(panel);
        add(scrollPanel, BorderLayout.CENTER);
    }
}