package riri.admin.management.inventory.form;

import riri.admin.management.history.HistoryPanel;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.table.TablePanel;
import riri.model.Book;
import riri.model.Employee;
import riri.model.Transaction;

import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Map;

public class BaseFormPanel extends BorderPanel {
    public Map<Integer, Book> bookList =AppContext.BOOK_SERVICE.getAll();
    public Map<Integer, Employee> employeeList = AppContext.EMPLOYEE_SERVICE.getAll();
    public Map<Integer, Transaction> transactionList = AppContext.TRANSACTION_SERVICE.getAll();

    protected final GridBagConstraints gbc = new GridBagConstraints();

    public final SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, null, 1);
    public final JSpinner spinner = new JSpinner(spinnerModel);
    public final JComboBox<String> cbBook = new JComboBox<>();
    public final JTextField authorField= new JTextField();
    public final JTextField categoryField = new JTextField();
    public final JTextField noteField= new JTextField();
    protected HistoryPanel historyPanel;

    private int hoverIndex = -1;

    public BaseFormPanel(HistoryPanel historyPanel) {
        super(0,Color.WHITE,0,0,null,0);
        this.historyPanel = historyPanel;
        setLayout(new GridBagLayout());
        setOpaque(false);

        gbc.insets = new Insets(0,0,0,5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(BasePanel.createTitle("Tên sách","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(selectWrapper(),gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(BasePanel.createTitle("Tác giả","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(noteWrapper("Nhập tên tác giả",authorField),gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(BasePanel.createTitle("Thể loại","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        add(noteWrapper("Nhập thể loại", categoryField),gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        add(BasePanel.createTitle("Số lượng","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        add(spinnerWrapper(),gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        add(BasePanel.createTitle("Ghi chú","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(noteWrapper("VD: Nhập từ NXB...",noteField),gbc);
        gbc.gridwidth = 1;

        fillBookInfo();
    }

    private BorderPanel selectWrapper(){
        BorderPanel selectBook = new BorderPanel(12, Color.WHITE, 0, 0, new Color(214, 214, 214), 1);
        selectBook.setBorder(new EmptyBorder(0,10,0,0));
        selectBook.setLayout(new BorderLayout());

        cbBook.setBorder(null);
        cbBook.setOpaque(false);
        cbBook.setFont(new Font("Arial", Font.PLAIN, 14));

        cbBook.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, false, false);

                label.setOpaque(false);
                label.setBorder(new EmptyBorder(8, 12, 8, 12));
                label.setFont(new Font("Arial", Font.PLAIN, 14));

                if(index >= 0){
                    label.setOpaque(true);

                    if(index == hoverIndex){
                        label.setBackground(new Color(240,245,255));
                    }
                    else if(isSelected){
                        label.setBackground(new Color(210,225,255));
                    }
                    else{
                        label.setBackground(Color.WHITE);
                    }
                }
                else{
                    label.setOpaque(false);
                }

                label.setForeground(new Color(83,83,83));
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
                        Polygon triangle = getPolygon(w);

                        g2.setColor(new Color(80, 80, 80));
                        g2.fill(triangle);

                        g2.dispose();
                    }

                    private Polygon getPolygon(int w) {
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
                        return triangle;
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

                list.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        hoverIndex = list.locationToIndex(e.getPoint());
                        list.repaint();
                    }
                });

                list.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent e) {
                        hoverIndex = -1;
                        list.repaint();
                    }
                });
                return popup;
            }
        });
        cbBook.addItem("--Chọn sách--");

        ((JComponent) cbBook.getRenderer()).setOpaque(false);

        for(Book book : bookList.values()){
            cbBook.addItem(book.getName());
        }
        cbBook.addItem("--Thêm sách mới--");

        cbBook.addFocusListener(new  FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                selectBook.setBorder(new Color(31, 95, 255),2);
            }

            @Override
            public void focusLost(FocusEvent e) {
                selectBook.setBorder(new Color(221, 221, 221),1);
            }
        });

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
                btn.addActionListener(_ -> spinner.setValue(spinner.getNextValue()));
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
                btn.addActionListener(_ -> spinner.setValue(spinner.getPreviousValue()));
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

    private BorderPanel noteWrapper(String text, JTextField field){
        BorderPanel noteWrapper = new BorderPanel(12, Color.WHITE, 0, 0, new Color(214, 214, 214), 1);
        noteWrapper.setBorder(new EmptyBorder(0,10,0,0));
        noteWrapper.setLayout(new BorderLayout());

        showPlaceholder(text, field, new Color(174,174,174));

        field.addFocusListener(new  FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                noteWrapper.setBorder(new Color(31, 95, 255),2);
                if (field.getText().equals(text)){
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                noteWrapper.setBorder(new Color(221, 221, 221),1);
                if(field.getText().isEmpty()){
                    showPlaceholder(text, field,new Color(174,174,174));
                }
            }
        });
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setOpaque(false);
        field.setFont(new Font("Arial",Font.PLAIN,15));
        field.setPreferredSize(new Dimension(0, 34));

        noteWrapper.add(field, BorderLayout.CENTER);

        return noteWrapper;
    }

    private void showPlaceholder(String text, JTextField field,Color colorForeground) {
        field.setText(text);
        field.setForeground(colorForeground);
    }

    private void fillBookInfo(){
        cbBook.addActionListener(_->{
            String bookName = (String)cbBook.getSelectedItem();
            if(bookName==null || bookName.equalsIgnoreCase("--Chọn sách--")){
                showPlaceholder("Nhập tên tác giả",authorField, new Color(174,174,174));
                showPlaceholder("Nhập thể loại",categoryField, new Color(174,174,174));
            }else {
                Book book = AppContext.BOOK_SERVICE.findByName(bookName);
                showPlaceholder(book.getAuthor(),authorField,Color.BLACK);
                showPlaceholder(book.getCategory(),categoryField,Color.BLACK);
            }
        });
    }

    public void addData(String type){

        String bookName = (String) cbBook.getSelectedItem();

        if(bookName == null || bookName.equals("--Thêm sách mới--") || bookName.equals("--Chọn sách--")) {
            return;
        }
        Book book = AppContext.BOOK_SERVICE.findByName(bookName);
        Transaction transaction = new Transaction(0,book.getId(),1,(Integer)spinner.getValue(),type,LocalDate.now(),noteField.getText());

        AppContext.TRANSACTION_SERVICE.add(transaction);
        historyPanel.getTable().addRow(new Object[]{
                transaction.getId(),
                transaction.getType(),
                book.getName(),
                book.getAuthor(),
                book.getCategory(),
                transaction.getQuantity(),
                transaction.getDate(),
                "Admin",
                transaction.getNote()
        });
        historyPanel.updateData();
    }


}