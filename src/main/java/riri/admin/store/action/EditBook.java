package riri.admin.store.action;

import riri.model.*;
import riri.util.AppContext;
import riri.components.field.FieldPanel;
import riri.components.combobox.ComboBoxPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;

public class EditBook extends JDialog {

    private final Map<Integer, Area> areas   = AppContext.AREA_SERVICE.getAll();
    private final Map<Integer, Shelf> shelves = AppContext.SHELF_SERVICE.getAll();

    public EditBook(Book book, Runnable onSuccess) {

        setModal(true);
        setSize(480, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 24, 10, 24));

        JLabel title = new JLabel("Sửa thông tin sách");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(16, 16, 16));
        header.add(title, BorderLayout.WEST);

        // ===== FORM =====
        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(10, 24, 10, 24));

        FieldPanel fpName      = createFieldPanel(book.getName());
        FieldPanel fpAuthor    = createFieldPanel(book.getAuthor());
        FieldPanel fpPublisher = createFieldPanel(book.getPublisher());
        FieldPanel fpPrice     = createFieldPanel(String.valueOf(book.getPrice()));
        FieldPanel fpQuantity  = createFieldPanel(String.valueOf(book.getQuantity()));

        FieldPanel fpCategory = createFieldPanel(book.getCategory());

        // ===== AREA =====
        ComboBoxPanel cbArea = new ComboBoxPanel();
        for (Area a : areas.values()) {
            cbArea.addItem(a.getName());
        }

        if (areas.containsKey(book.getIdArea())) {
            cbArea.getComboBox().setSelectedItem(areas.get(book.getIdArea()).getName());
        }
        styleCombo(cbArea);

        // ===== SHELF =====
        ComboBoxPanel cbShelf = new ComboBoxPanel();
        styleCombo(cbShelf);

        Runnable loadShelves = () -> {
            cbShelf.getComboBox().removeAllItems();

            String areaName = (String) cbArea.getComboBox().getSelectedItem();
            if (areaName == null) return;

            for (Area a : areas.values()) {
                if (a.getName().equals(areaName)) {
                    for (Shelf s : shelves.values()) {
                        if (s.getAreaId() == a.getId()) {
                            cbShelf.addItem(s.getName());
                        }
                    }
                    break;
                }
            }
        };

        loadShelves.run();

        if (shelves.containsKey(book.getIdShelf())) {
            cbShelf.getComboBox().setSelectedItem(shelves.get(book.getIdShelf()).getName());
        }

        cbArea.getComboBox().addActionListener(e -> loadShelves.run());

        // ===== ADD FORM =====
        form.add(createLabel("Tên sách"));     form.add(fpName);      form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Tác giả"));      form.add(fpAuthor);    form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Nhà xuất bản")); form.add(fpPublisher); form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Thể loại"));     form.add(fpCategory);  form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Giá (đ)"));      form.add(fpPrice);     form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Số lượng"));     form.add(fpQuantity);  form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Khu vực"));      form.add(cbArea);      form.add(Box.createVerticalStrut(12));
        form.add(createLabel("Kệ sách"));      form.add(cbShelf);     form.add(Box.createVerticalStrut(12));

        // ===== FOOTER =====
        JPanel footer = new JPanel(new GridLayout(1, 2, 12, 0));
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(10, 24, 24, 24));

        JButton btnCancel = createButton("Hủy", new Color(245,245,245), new Color(57,57,57));
        btnCancel.addActionListener(e -> dispose());

        JButton btnSave = createButton("Lưu thay đổi", new Color(41,121,255), Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));

        btnSave.addActionListener(e -> {
            try {
                String areaName  = (String) cbArea.getComboBox().getSelectedItem();
                String shelfName = (String) cbShelf.getComboBox().getSelectedItem();

                int idArea = 0;
                int idShelf = 0;


                // Map Tên Area -> ID Area
                for (Area a : areas.values()) {
                    if (a.getName().equals(areaName)) {
                        idArea = a.getId();
                        break;
                    }
                }

                // Map Tên Shelf -> ID Shelf
                for (Shelf s : shelves.values()) {
                    if (s.getName().equals(shelfName)) {
                        idShelf = s.getId();
                        break;
                    }
                }

                // Map Tên Category -> ID Category
                String categoryName = fpCategory.getTextField().trim();

                Book updated = new Book(
                        book.getId(),
                        fpName.getTextField().trim(),
                        fpAuthor.getTextField().trim(),
                        categoryName, // Sửa thành truyền idCategory (int)
                        fpPublisher.getTextField().trim(),
                        Double.parseDouble(fpPrice.getTextField().trim()),
                        Integer.parseInt(fpQuantity.getTextField().trim()),
                        idArea,
                        idShelf
                );

                AppContext.BOOK_SERVICE.update(updated);

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
        add(form, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ===== STYLE BUTTON =====
    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setForeground(fg);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ===== STYLE COMBO =====
    private void styleCombo(ComboBoxPanel cb) {
        cb.getComboBox().setFont(new Font("Arial", Font.PLAIN, 14));
        cb.setBorder(new EmptyBorder(2, 10, 2, 10));
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        cb.setPreferredSize(new Dimension(0, 44));
        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private FieldPanel createFieldPanel(String value) {
        FieldPanel fp = new FieldPanel(value);
        fp.setTextField(value);
        fp.field.setForeground(Color.BLACK);
        fp.field.setCaretPosition(0);
        fp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        fp.setPreferredSize(new Dimension(0, 44));
        fp.setAlignmentX(Component.LEFT_ALIGNMENT);
        return fp;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(new Color(100, 100, 100));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }
}