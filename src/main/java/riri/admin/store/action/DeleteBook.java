package riri.admin.store.action;

import riri.model.Book;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteBook extends JDialog {

    public DeleteBook(Book book, Runnable onSuccess) {
        setModal(true);
        setSize(420, 240);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 24, 10, 24));

        JLabel title = new JLabel("Xóa sách");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(16, 16, 16));

        header.add(title, BorderLayout.WEST);

        JPanel body = new JPanel();
        body.setBackground(Color.WHITE);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(10, 24, 10, 24));

        JLabel message = new JLabel("<html>Bạn có chắc muốn xóa sách <b>\""
                + book.getName() + "\"</b> không?<br>Hành động này không thể hoàn tác.</html>");
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        message.setForeground(new Color(57, 57, 57));
        message.setAlignmentX(Component.LEFT_ALIGNMENT);

        body.add(message);

        JPanel footer = new JPanel(new GridLayout(1, 2, 12, 0));
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(10, 24, 24, 24));

        JButton btnCancel = new JButton("Hủy") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancel.setForeground(new Color(57, 57, 57));
        btnCancel.setContentAreaFilled(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dispose());

        JButton btnDelete = new JButton("Xóa sách") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(220, 38, 38));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setContentAreaFilled(false);
        btnDelete.setBorderPainted(false);
        btnDelete.setFocusPainted(false);
        btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDelete.addActionListener(e -> {
            try {
                AppContext.BOOK_SERVICE.delete(book.getId());
                if (onSuccess != null) onSuccess.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Xóa sách thất bại, vui lòng thử lại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        footer.add(btnCancel);
        footer.add(btnDelete);

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }
}