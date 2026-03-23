package riri.admin.store.action;

import riri.model.Book;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.RenderingHints;
import java.awt.Graphics2D;

public class EditBook extends JDialog {

    public EditBook(Book book, Runnable onSuccess) {
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("ComboBox.arc", 12);

        setTitle("Sửa thông tin sách");
        setModal(true);
        setSize(480, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // ---- HEADER ----
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 24, 10, 24));

        JLabel title = new JLabel("Sửa thông tin sách");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(16, 16, 16));

        JButton btnClose = new JButton("✕");
        btnClose.setFont(new Font("Arial", Font.PLAIN, 16));
        btnClose.setForeground(new Color(100, 100, 100));
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> dispose());

        header.add(title, BorderLayout.WEST);
        header.add(btnClose, BorderLayout.EAST);

        // ---- FORM ----
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(10, 24, 10, 24));

        JTextField tfName      = createField(book.getName());
        JTextField tfAuthor    = createField(book.getAuthor());
        JTextField tfPublisher = createField(book.getPublisher());
        JTextField tfPrice     = createField(String.valueOf((int) book.getPrice()));
        JTextField tfQuantity  = createField(String.valueOf(book.getQuantity()));
        JTextField tfShelf     = createField(book.getShelf() != null ? book.getShelf() : "");

        String[] categories = {"Kỹ năng sống", "Tiểu thuyết", "Lịch sử", "Phát triển bản thân", "Văn học", "Công nghệ"};
        JComboBox<String> cbCategory = createCombo(categories, book.getCategory());

        String[] areas = {"Khu A", "Khu B", "Khu C"};
        JComboBox<String> cbArea = createCombo(areas, book.getArea() != null ? book.getArea() : "Khu A");

        form.add(createLabel("Tên sách"));     form.add(tfName);      form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Tác giả"));      form.add(tfAuthor);    form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Nhà xuất bản")); form.add(tfPublisher); form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Thể loại"));     form.add(cbCategory);  form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Giá (đ)"));      form.add(tfPrice);     form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Số lượng"));     form.add(tfQuantity);  form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Khu vực"));      form.add(cbArea);      form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Kệ sách"));      form.add(tfShelf);     form.add(Box.createVerticalStrut(12));

        // ---- FOOTER ----
        JPanel footer = new JPanel(new GridLayout(1, 2, 12, 0));
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(10, 24, 24, 24));

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancel.setBackground(new Color(245, 245, 245));
        btnCancel.setForeground(new Color(57, 57, 57));
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusPainted(false);
        btnCancel.putClientProperty("JButton.buttonType", "roundRect");
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dispose());

        JButton btnSave = new JButton("Cập nhật");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setBackground(new Color(41, 121, 255));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBorderPainted(false);
        btnSave.setFocusPainted(false);
        btnSave.putClientProperty("JButton.buttonType", "roundRect");
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            try {
                book.setName(tfName.getText().trim());
                book.setAuthor(tfAuthor.getText().trim());
                book.setPublisher(tfPublisher.getText().trim());
                book.setCategory((String) cbCategory.getSelectedItem());
                book.setPrice(Integer.parseInt(tfPrice.getText().trim()));
                book.setQuantity(Integer.parseInt(tfQuantity.getText().trim()));
                book.setArea((String) cbArea.getSelectedItem());
                book.setShelf(tfShelf.getText().trim());
                AppContext.BOOK_SERVICE.update(book);
                if (onSuccess != null) onSuccess.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng kiểm tra lại dữ liệu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        footer.add(btnCancel);
        footer.add(btnSave);

        add(header, BorderLayout.NORTH);
        add(form,   BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(new Color(100, 100, 100));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField createField(String value) {
        JTextField tf = new JTextField(value) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(220, 220, 220));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
            }
        };
        tf.setFont(new Font("Arial", Font.PLAIN, 14));
        tf.setOpaque(false);
        tf.setBorder(new EmptyBorder(10, 14, 10, 14));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return tf;
    }

    private JComboBox<String> createCombo(String[] items, String selected) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setSelectedItem(selected);
        cb.setFont(new Font("Arial", Font.PLAIN, 14));
        cb.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        return cb;
    }
}