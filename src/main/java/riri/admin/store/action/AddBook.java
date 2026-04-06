package riri.admin.store.action;

import riri.components.BorderPanel;
import riri.components.combobox.ComboBoxPanel;
import riri.components.field.FieldPanel;
import riri.model.Area;
import riri.model.BaseModel;
import riri.model.Book;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddBook extends JDialog {
    public JPanel header = new JPanel(new BorderLayout());
    public FieldPanel fpName      = new FieldPanel("Tên sách");
    public FieldPanel fpAuthor    = new FieldPanel("Tác giả");
    public FieldPanel fpPublisher = new FieldPanel("Nhà xuất bản");
    public FieldPanel fpCategory  = new FieldPanel("Thể loại");
    public FieldPanel fpPrice     = new FieldPanel("Giá (đ)");
    public ComboBoxPanel<String> cbArea = new ComboBoxPanel<>();
    public ComboBoxPanel<String> cbShelf = new ComboBoxPanel<String>();
    public JPanel footer = new JPanel(new GridLayout(1, 2, 12, 0));
    public BorderPanel BUTTON_CANCEL = new BorderPanel(12, new Color(245, 245, 245), 0, 0, null, 0);
    public BorderPanel BUTTON_SAVE = new BorderPanel(12, new Color(41, 121, 255), 0, 0, null, 0);

    public AddBook(Runnable onSuccess) {
        setTitle("Thêm sách mới");
        setModal(true);
        setSize(480, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // HEADER
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

        this.fpName.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.fpAuthor.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.fpPublisher.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.fpCategory.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.fpPrice.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ===== AREA =====
        String[] areaNames = AppContext.AREA_SERVICE.getAll().values()
                .stream().map(Area::getName).toArray(String[]::new);

        cbArea.setItems(areaNames);
        cbArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        cbArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ===== SHELF =====
        cbShelf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
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

        // ===== ADD FORM (không label) =====
        form.add(fpName);      form.add(Box.createVerticalStrut(12));
        form.add(fpAuthor);    form.add(Box.createVerticalStrut(12));
        form.add(fpPublisher); form.add(Box.createVerticalStrut(12));
        form.add(fpCategory);  form.add(Box.createVerticalStrut(12));
        form.add(fpPrice);     form.add(Box.createVerticalStrut(12));
        form.add(cbArea);      form.add(Box.createVerticalStrut(12));
        form.add(cbShelf);     form.add(Box.createVerticalStrut(12));

        // FOOTER

        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(10, 24, 24, 24));
        footer.setPreferredSize(new Dimension(0, 70));

        // ===== CANCEL =====
        BUTTON_CANCEL.setLayout(new BorderLayout());
        BUTTON_CANCEL.setPreferredSize(new Dimension(0, 48));
        BUTTON_CANCEL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblCancel = new JLabel("Hủy", SwingConstants.CENTER);
        lblCancel.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCancel.setForeground(new Color(57, 57, 57));
        BUTTON_CANCEL.add(lblCancel, BorderLayout.CENTER);

        BUTTON_CANCEL.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        // ===== SAVE =====
        BUTTON_SAVE.setLayout(new BorderLayout());
        BUTTON_SAVE.setPreferredSize(new Dimension(0, 48));
        BUTTON_SAVE.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblSave = new JLabel("Thêm sách", SwingConstants.CENTER);
        lblSave.setFont(new Font("Arial", Font.BOLD, 14));
        lblSave.setForeground(Color.WHITE);
        BUTTON_SAVE.add(lblSave, BorderLayout.CENTER);

        BUTTON_SAVE.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    String categoryName = fpCategory.getTextField().trim();
                    String areaName     = (String) cbArea.getComboBox().getSelectedItem();
                    String shelfName    = (String) cbShelf.getComboBox().getSelectedItem();

                    int idArea = AppContext.AREA_SERVICE.getAll().values().stream()
                            .filter(a -> a.getName().equals(areaName))
                            .findFirst().map(BaseModel::getId).orElse(0);

                    int idShelf = AppContext.SHELF_SERVICE.getAll().values().stream()
                            .filter(s -> s.getName().equals(shelfName))
                            .findFirst().map(BaseModel::getId).orElse(0);

                    Book book = new Book(
                            0,
                            fpName.getTextField().trim(),
                            fpAuthor.getTextField().trim(),
                            categoryName,
                            fpPublisher.getTextField().trim(),
                            Double.parseDouble(fpPrice.getTextField().trim()),
                            0,
                            idArea,
                            idShelf
                    );

                    AppContext.BOOK_SERVICE.add(book);
                    if (onSuccess != null) onSuccess.run();
                    dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddBook.this,
                            "Vui lòng kiểm tra lại dữ liệu!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        footer.add(BUTTON_CANCEL);
        footer.add(BUTTON_SAVE);

        add(header, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        SwingUtilities.invokeLater(() -> getRootPane().requestFocusInWindow());
        setVisible(true);
    }
}