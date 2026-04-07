package riri.admin.store.action;

import riri.components.combobox.ComboBoxPanel;
import riri.model.Area;
import riri.model.BaseModel;
import riri.model.Book;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddBook extends JDialog {

    public AddBook(Runnable onSuccess) {
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("ComboBox.arc", 12);

        setTitle("Thêm sách mới");
        setModal(true);
        setSize(480, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 24, 10, 24));

        JLabel title = new JLabel("Thêm sách mới");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(16, 16, 16));

        header.add(title, BorderLayout.WEST);

        // FORM
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(10, 24, 10, 24));

        JTextField tfName      = createField("");
        JTextField tfAuthor    = createField("");
        JTextField tfPublisher = createField("");
        JTextField tfPrice     = createField("");

        // ===== SETUP CATEGORY =====
        String[] categoryNames = AppContext.CATEGORY_SERVICE.getAll().values()
                .stream().map(c -> c.getName()).toArray(String[]::new);
        JTextField tfCategory = createField("");

        // ===== SETUP AREA =====
        String[] areaNames = AppContext.AREA_SERVICE.getAll().values()
                .stream().map(Area::getName).toArray(String[]::new);
        ComboBoxPanel cbArea = new ComboBoxPanel();
        cbArea.setItems(areaNames);
        cbArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        cbArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ===== SETUP SHELF =====
        ComboBoxPanel cbShelf = new ComboBoxPanel();
        cbShelf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        cbShelf.setAlignmentX(Component.LEFT_ALIGNMENT);

        Runnable loadShelves = () -> {
            cbShelf.getComboBox().removeAllItems();
            String selectedArea = (String) cbArea.getComboBox().getSelectedItem();
            if (selectedArea == null) return;

            AppContext.AREA_SERVICE.getAll().values().stream()
                    .filter(a -> a.getName().equals(selectedArea))
                    .findFirst()
                    .ifPresent(area ->
                            AppContext.SHELF_SERVICE.findByAreaId(area.getId())
                                    .forEach(s -> cbShelf.getComboBox().addItem(s.getName()))
                    );
        };

        loadShelves.run();
        cbArea.getComboBox().addActionListener(e -> loadShelves.run());

        // ===== THÊM VÀO FORM =====
        form.add(createLabel("Tên sách"));
        form.add(tfName);
        form.add(Box.createVerticalStrut(12));

        form.add(createLabel("Tác giả"));
        form.add(tfAuthor);
        form.add(Box.createVerticalStrut(12));

        form.add(createLabel("Nhà xuất bản"));
        form.add(tfPublisher);
        form.add(Box.createVerticalStrut(12));

        form.add(createLabel("Thể loại"));
        form.add(tfCategory);
        form.add(Box.createVerticalStrut(12));

        form.add(createLabel("Giá (đ)"));
        form.add(tfPrice);
        form.add(Box.createVerticalStrut(12));

        form.add(createLabel("Khu vực"));
        form.add(cbArea);
        form.add(Box.createVerticalStrut(12));

        form.add(createLabel("Kệ sách"));
        form.add(cbShelf);
        form.add(Box.createVerticalStrut(12));

        // FOOTER
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

        JButton btnSave = new JButton("Thêm sách");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setBackground(new Color(41, 121, 255));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBorderPainted(false);
        btnSave.setFocusPainted(false);
        btnSave.putClientProperty("JButton.buttonType", "roundRect");
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(e -> {
            try {
                String categoryName = tfCategory.getText().trim();
                String areaName     = (String) cbArea.getComboBox().getSelectedItem();
                String shelfName    = (String) cbShelf.getComboBox().getSelectedItem();

                // Lấy ID thể loại
                int idCategory = AppContext.CATEGORY_SERVICE.getAll().values().stream()
                        .filter(c -> c.getName().equals(categoryName))
                        .findFirst().map(BaseModel::getId).orElse(0);

                // Lấy ID khu vực
                int idArea = AppContext.AREA_SERVICE.getAll().values().stream()
                        .filter(a -> a.getName().equals(areaName))
                        .findFirst().map(BaseModel::getId).orElse(0);

                // Lấy ID kệ sách
                int idShelf = AppContext.SHELF_SERVICE.getAll().values().stream()
                        .filter(s -> s.getName().equals(shelfName))
                        .findFirst().map(BaseModel::getId).orElse(0);

                Book book = new Book(
                        0,
                        tfName.getText().trim(),
                        tfAuthor.getText().trim(),
                        categoryName,        // ← String category (giữ lại)
                        tfPublisher.getText().trim(),
                        Double.parseDouble(tfPrice.getText().trim()),
                        0,
                        idArea,
                        idShelf
                );

                AppContext.BOOK_SERVICE.add(book);
                if (onSuccess != null) onSuccess.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng kiểm tra lại dữ liệu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // In lỗi ra console để dễ debug nếu có
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(180, 180, 180));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 16, 16);
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
}