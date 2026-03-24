package riri.admin.management.inventory.form;

import riri.admin.management.history.HistoryPanel;
import riri.admin.management.stat.ManagementStatCard;
import riri.components.BorderPanel;
import riri.components.combobox.ComboBoxPanel;
import riri.components.field.FieldPanel;
import riri.components.page.BasePanel;
import riri.components.spinner.SpinnerPanel;
import riri.model.*;

import riri.util.AppContext;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Map;

public class BaseFormPanel extends BorderPanel {
    public Map<Integer, Book> bookList =AppContext.BOOK_SERVICE.getAll();
    public String type;

    protected final GridBagConstraints gbc = new GridBagConstraints();

    public CardLayout cardLayout = new CardLayout();
    public BorderPanel bookPanel = new BorderPanel(16, Color.WHITE,0,0,new Color(214, 214, 214),1);
    public ComboBoxPanel<String> comboBoxBook = new ComboBoxPanel<>();
    public SpinnerPanel spinnerPanel = new SpinnerPanel();

    public FieldPanel bookField = new FieldPanel("Nhập sách mới... (Nhấn ESC để có thể lựa chọn sách...)");
    public FieldPanel authorField = new FieldPanel("Nhập tên tác giả...");
    public FieldPanel categoryField = new FieldPanel("Nhập thể loại...");
    public FieldPanel noteField= new FieldPanel("Ghi chú...");
    public FieldPanel publisherField = new FieldPanel("Nhập nhà sản xuất...");
    public FieldPanel priceField = new FieldPanel("Nhập giá bán cho sách...");

    protected HistoryPanel historyPanel;
    protected ManagementStatCard statCard;

    public BaseFormPanel(HistoryPanel historyPanel, ManagementStatCard statCard, String type) {
        super(0,Color.WHITE,0,0,null,0);
        this.historyPanel = historyPanel;
        this.statCard= statCard;
        this.type = type;

        setLayout(new GridBagLayout());

        gbc.insets = new Insets(0,0,0,10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        bookField.addBorderFocus();
        authorField.addBorderFocus();
        categoryField.addBorderFocus();
        noteField.addBorderFocus();
        publisherField.addBorderFocus();
        priceField.addBorderFocus();

        addItem(BasePanel.createTitle("Tên sách","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),0,0);
        addItem(BasePanel.createTitle("Tác giả","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),1,0);
        addItem(BasePanel.createTitle("Thể loại","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),2,0);
        addItem(BasePanel.createTitle("Số lượng","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),3,0);
        addItem(BasePanel.createTitle("Nhà xuất bản","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),0,2);
        addItem(BasePanel.createTitle("Giá bán","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),1,2);
        addItem(BasePanel.createTitle("Ghi chú","Segue UI",Font.BOLD, 13, new Color(71, 71, 71)),2,2);

        addItem(comboBoxBook(),0,1);
        addItem(authorField,1,1);
        addItem(categoryField,2,1);
        addItem(spinnerPanel(),3,1);
        addItem(publisherField,0,3);
        addItem(priceField,1,3);
        addItem(noteField,2,3);

        fillBookInfo();
    }

    public void addItem(Component component, int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        add(component,gbc);
    }

    private BorderPanel comboBoxBook(){

        bookPanel.setLayout(cardLayout);

        this.comboBoxBook.addItem("--Chọn sách--");

        for(Book book : bookList.values()){ this.comboBoxBook.addItem(book.getName()); }

        if(type.equals("Nhập")){
            this.comboBoxBook.addItem("--Thêm sách mới--");
        }

        bookPanel.add(comboBoxBook.getComboBox(),"comboBoxBook");
        bookPanel.add(bookField,"bookField");

        return this.bookPanel;
    }

    private SpinnerPanel spinnerPanel(){
        return this.spinnerPanel;
    }

    private void fillBookInfo(){
        comboBoxBook.getComboBox().addActionListener(_->{
            String bookName = (String)comboBoxBook.getComboBox().getSelectedItem();
            if(bookName==null || bookName.equalsIgnoreCase("--Chọn sách--")){
                showPlaceholder();

                return;
            }

            if( bookName.equalsIgnoreCase("--Thêm sách mới--")){
                cardLayout.show(bookPanel,"bookField");

                showPlaceholder();

                bookField.getField().addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                            cardLayout.show(bookPanel,"comboBoxBook");
                            e.consume();
                        }
                    }
                });

                return;
            }

            Book book = AppContext.BOOK_SERVICE.findByName(bookName);

            showPlaceholder(book);

        });
    }

    public void addData(String type){
        double price;
        String bookName = (String) comboBoxBook.getComboBox().getSelectedItem();

        if( bookName == null || bookName.equals("--Chọn sách--")) {
            return;
        }

        try{
            price  = Double.parseDouble(priceField.getTextField());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Vui lòng nhập số nguyên","Không phải số nguyên",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = new Book(
                0,
                bookField.getTextField(),
                authorField.getTextField(),
                categoryField.getTextField(),
                publisherField.getTextField(),
                price,
                spinnerPanel.getValue(),
                1, 1);

        if(bookName.equals("--Thêm sách mới--")){
            AppContext.BOOK_SERVICE.add(book);
        }else {
            book = AppContext.BOOK_SERVICE.findByName(bookName);
            book = new Book(
                    book.getId(),
                    book.getName(),
                    authorField.getTextField(),
                    categoryField.getTextField(),
                    publisherField.getTextField(),
                    price,
                    spinnerPanel.getValue(),
                    1, 1);
            AppContext.BOOK_SERVICE.update(book);
        }

        Transaction transaction = new Transaction(
                0,
                book.getId(),
                1,
                spinnerPanel.getValue(),
                type,
                LocalDate.now(),
                noteField.getTextField().equals(noteField.getTitle())? " " : noteField.getTextField()
        );

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

    public void showPlaceholder(){
        bookField.showPlaceholder();
        authorField.showPlaceholder();
        categoryField.showPlaceholder();
        publisherField.showPlaceholder();
        priceField.showPlaceholder();
    }

    public void showPlaceholder(Book item){
        authorField.showPlaceholder(item.getAuthor(),Color.BLACK);
        categoryField.showPlaceholder(item.getCategory(),Color.BLACK);
        publisherField.showPlaceholder(item.getPublisher(),Color.BLACK);
        priceField.showPlaceholder(String.valueOf(item.getPrice()),Color.BLACK);
    }

}