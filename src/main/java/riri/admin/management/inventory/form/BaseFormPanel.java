package riri.admin.management.inventory.form;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.dao.BookDAO;
import riri.model.Book;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.util.List;

public class BaseFormPanel extends BorderPanel {
    private final BookDAO bookDAO = new BookDAO();
    private final List<Book> bookList = bookDAO.findAll();

    protected final GridBagConstraints gbc = new GridBagConstraints();

    public final SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, null, 1);
    public final JSpinner spinner = new JSpinner(spinnerModel);
    public final JComboBox<String> cbBook = new JComboBox<>();

    public BaseFormPanel() {
        super(0,Color.WHITE,0,0,null,0);

        setLayout(new GridBagLayout());
        setOpaque(false);

        gbc.insets = new Insets(0,5,0,5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(BasePanel.createTitle("Tên sách","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(BasePanel.createTitle("Số lượng","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(BasePanel.createTitle("Ghi chú","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(selectWrapper(),gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(spinnerWrapper(),gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        add(noteWrapper(),gbc);
    }

    private BorderPanel selectWrapper(){
        BorderPanel selectBook = new BorderPanel(12, Color.WHITE, 0, 0, new Color(214, 214, 214), 1);
        selectBook.setBorder(new EmptyBorder(0,10,0,0));
        selectBook.setLayout(new BorderLayout());

        cbBook.setBorder(null);
        cbBook.setOpaque(false);

        cbBook.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                label.setBorder(new EmptyBorder(8, 12, 8, 12));
                label.setFont(new Font("Arial", Font.PLAIN, 14));

                if (isSelected) {
                    label.setBackground(new Color(230, 240, 255));
                    label.setForeground(Color.BLACK);
                } else {
                    label.setBackground(new Color(255, 255, 255));
                    label.setForeground(new Color(83, 83, 83));
                }

                return label;
            }
        });


        cbBook.setUI(new BasicComboBoxUI() {

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {}

            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton() {

                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        int w = getWidth();
                        int h = getHeight();
                        int size = 6;
                        int x = w / 2;
                        int y = h / 2;

                        Polygon triangle = new Polygon();
                        triangle.addPoint(x - size, y );
                        triangle.addPoint(x - size / 2 , y - size / 2);

                        triangle.addPoint(x, y + size / 2 - 2);

                        triangle.addPoint(x + size / 2 , y - size / 2);
                        triangle.addPoint(x + size, y);

                        triangle.addPoint(x, y + size);

                        g2.setColor(new Color(80, 80, 80));
                        g2.fill(triangle);

                        g2.dispose();
                    }
                };

                button.setBorder(null);
                button.setContentAreaFilled(false);
                button.setOpaque(false);
                button.setFocusPainted(false);

                return button;
            }
            @Override
            protected ComboPopup createPopup() {
                BasicComboPopup popup = new BasicComboPopup(comboBox);

                JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
                scrollPane.setBorder(null);
                scrollPane.setBackground(Color.WHITE);

                JViewport viewport = scrollPane.getViewport();
                viewport.setBackground(Color.WHITE);

                JList<?> list = popup.getList();
                list.setBackground(Color.WHITE);

                return popup;
            }
        });
        cbBook.addItem("--Chọn sách--");

        ((JComponent) cbBook.getRenderer()).setOpaque(false);

        for(Book book : bookList){
            cbBook.addItem(book.getName());
        }
        selectBook.add(cbBook, BorderLayout.CENTER);
        return selectBook;
    }

    private BorderPanel spinnerWrapper(){
        BorderPanel spinnerWrapper = new BorderPanel(12, Color.WHITE, 0, 0, new Color(200,200,200), 1);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");

        spinner.setEditor(editor);
        spinner.setOpaque(false);
        spinner.setBorder(null);
        spinner.setBackground(new Color(0,0,0,0));

        spinner.setUI(new BasicSpinnerUI() {

            @Override
            protected void installDefaults() {
                super.installDefaults();
                spinner.setBorder(BorderFactory.createEmptyBorder(0,10,0,20));
            }
            @Override
            protected Component createNextButton() {
                JButton btn = new JButton("▲");
                btn.setBorder(new EmptyBorder(0,0,0,10));
                btn.setContentAreaFilled(false);
                btn.setOpaque(false);
                btn.setMargin(new Insets(0, 0, 0, 8));
                btn.addActionListener(e -> {
                    spinner.setValue(spinner.getNextValue());
                });
                btn.setFocusPainted(false);
                return btn;
            }

            @Override
            protected Component createPreviousButton() {
                JButton btn = new JButton("▼");
                btn.setBorder(new EmptyBorder(0,0,0,10));
                btn.setContentAreaFilled(false);
                btn.setOpaque(false);
                btn.setMargin(new Insets(0, 0, 0, 8));
                btn.addActionListener(e -> {
                    spinner.setValue(spinner.getPreviousValue());
                });
                btn.setFocusPainted(false);
                return btn;
            }
        });

        JFormattedTextField textField = editor.getTextField();
        textField.setHorizontalAlignment(SwingConstants.LEFT);

        JComponent editorComp = spinner.getEditor();
        editorComp.setOpaque(false);


        if (editorComp instanceof JSpinner.DefaultEditor) {
            JFormattedTextField tf = ((JSpinner.DefaultEditor) editorComp).getTextField();
            tf.setOpaque(false);
            tf.setBorder(null);
            tf.setBackground(new Color(0,0,0,0));
            tf.setUI(new javax.swing.plaf.basic.BasicFormattedTextFieldUI());
        }

        spinnerWrapper.setLayout(new BorderLayout());
        spinnerWrapper.add(spinner, BorderLayout.CENTER);
        return spinnerWrapper;
    }

    private BorderPanel noteWrapper(){
        BorderPanel noteWrapper = new BorderPanel(12, Color.WHITE, 0, 0, new Color(214, 214, 214), 1);
        noteWrapper.setBorder(new EmptyBorder(0,10,0,0));
        noteWrapper.setLayout(new BorderLayout());

        JTextField field = new JTextField(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setColor(new Color(174, 174, 174));
                    g2.setFont(getFont());
                    g2.drawString("VD: Nhập từ NXB...", 5, getHeight()/2 + 5);
                    g2.dispose();
                }
            }
        };
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setOpaque(false);
        field.setFont(new Font("Arial",Font.PLAIN,15));

        noteWrapper.add(field, BorderLayout.CENTER);

        return noteWrapper;
    }
}