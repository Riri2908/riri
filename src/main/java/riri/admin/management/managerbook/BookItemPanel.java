package riri.admin.management.managerbook;

import riri.components.BorderPanel;
import riri.model.Book;

import javax.swing.*;
import java.awt.*;

public class BookItemPanel extends BorderPanel {
    public BookItemPanel(Book book) {
        super(15, Color.WHITE, 0, 0, null, 0);
        setOpaque(false);

        setLayout(new GridLayout(1, 6, 10, 0));
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblName = new JLabel(book.getName());
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblName.setForeground(new Color(51, 51, 51));

        JLabel lblAuthor = new JLabel(book.getAuthor());
        lblAuthor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblAuthor.setForeground(new Color(102, 102, 102));

        JLabel lblCategory = new JLabel("  Kỹ năng  ", SwingConstants.CENTER);
        lblCategory.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCategory.setForeground(new Color(156, 39, 176));
        lblCategory.setBackground(new Color(243, 229, 245));
        lblCategory.setOpaque(true);
        JPanel categoryWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        categoryWrapper.setOpaque(false);
        categoryWrapper.add(lblCategory);

        JLabel lblPrice = new JLabel(String.format("%,.0f đ", book.getPrice()));
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel lblQuantity = new JLabel(String.valueOf(book.getQuantity()));
        lblQuantity.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblQuantity.setForeground(new Color(255, 152, 0));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionPanel.setOpaque(false);

        JButton btnEdit = new JButton("Sửa");
        btnEdit.setForeground(new Color(0, 123, 255)); // Chữ xanh
        btnEdit.setFocusPainted(false);
        btnEdit.setBackground(Color.WHITE);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setForeground(Color.RED); // Chữ đỏ
        btnDelete.setFocusPainted(false);
        btnDelete.setBackground(Color.WHITE);

        actionPanel.add(btnEdit);
        actionPanel.add(btnDelete);

        add(lblName);
        add(lblAuthor);
        add(categoryWrapper);
        add(lblPrice);
        add(lblQuantity);
        add(actionPanel);
    }
}