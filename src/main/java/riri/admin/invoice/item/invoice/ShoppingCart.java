package riri.admin.invoice.item.invoice;

import riri.components.BorderPanel;
import riri.components.combobox.ComboBoxCustomRenderer;
import riri.components.combobox.ComboBoxPanel;
import riri.components.field.FieldPanel;
import riri.components.page.BasePanel;
import riri.components.spinner.SpinnerPanel;
import riri.components.table.TablePanel;
import riri.model.Book;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class ShoppingCart extends BorderPanel {
    public Map<Integer, Book> books = AppContext.BOOK_SERVICE.getAll();

    private int sumQuantity = 0;

    public GridBagConstraints gbc = new GridBagConstraints();
    public JLabel titleLabel;
    public ListPanel listPanel;
    public TotalAmountPanel totalAmountPanel;

    public ComboBoxPanel<Book> bookBox = new ComboBoxPanel<>();
    public FieldPanel search = new FieldPanel("Tìm kiếm sách...");
    public TablePanel table = new TablePanel();
    public SpinnerPanel spinner = new SpinnerPanel();
    public BorderPanel button;
    public JLabel icon;

    public ShoppingCart(ListPanel listPanel,TotalAmountPanel totalAmountPanel) {
        this.listPanel = listPanel;
        this.totalAmountPanel = totalAmountPanel;

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20,20,10,20));

        search.setBorder(null,0);
        search.setOpaque(false);
        search.getField().setOpaque(false);

        gbc.insets = new Insets(0,0,15,20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;

        titleLabel = BasePanel.createTitle("Thêm sách vào giỏ hàng","Segue UI",Font.BOLD, 18, new Color(25, 25, 25));
        titleLabel.setBorder(new EmptyBorder(0,0,10,0));

        addItem(titleLabel,0,0);
        bookBox();
        addItem(BasePanel.createItem("Chọn sách",bookBox),0,1);
        addItem(BasePanel.createItem("Số lượng",spinner),1,1);
        spinner.setBorder(new EmptyBorder(10,10,10,10));

        gbc.weighty = 0.5;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;

        addItem(buttonAdd(),2,1);



    }

    public void addItem(Component component, int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        add(component,gbc);
    }

    public void bookBox(){

        bookBox.getComboBox().setBorder(new EmptyBorder(0,10,0,10));

        bookBox.addItem(null);
        for(Book book:books.values()){
            bookBox.addItem(book);
        }

        ComboBoxCustomRenderer<Book> renderer = new ComboBoxCustomRenderer<>((book, index) -> {
            if (index == -1) {
                return new JLabel(book.getName());
            }
            JPanel panel = new JPanel(new BorderLayout(10,5));

            panel.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));

            JLabel name = new JLabel(book.getName());
            name.setFont(new Font("Arial", Font.BOLD, 14));

            JLabel price = new JLabel(book.getPrice() + " đ");
            price.setFont(new Font("Arial", Font.PLAIN, 14));

            JLabel stock = new JLabel();

            if (book.getQuantity() == 0) {
                stock.setText("Đã hết hàng");
                stock.setForeground(Color.RED);
            }else {
                stock.setText("Còn: "+book.getQuantity());
                stock.setForeground(new Color(0,140,70));
            }
            JPanel left = new JPanel(new GridLayout(2,1));
            left.setOpaque(false);
            left.add(name);
            left.add(price);

            panel.add(left, BorderLayout.CENTER);
            panel.add(stock, BorderLayout.EAST);

            return panel;
        });
        this.bookBox.getComboBox().addActionListener(e-> {
            Book selected = (Book) bookBox.getComboBox().getSelectedItem();
            if(selected==null){
                button.setBackground(new Color(113, 113, 113));
                icon.setForeground(new Color(25,25,25));
            }else {
                button.setBackground(new Color(0, 82, 255));
                icon.setForeground(new Color(255, 255, 255));
            }
        });

        this.bookBox.getComboBox().setRenderer(renderer);
    }

    public JPanel buttonAdd(){
        this.button = new BorderPanel(16,new Color(113, 113, 113),0,0,null,0);
        this.button.setPreferredSize(new Dimension(50,35));
        this.button.setMaximumSize(new Dimension(50,35));
        this.button.setLayout(new BorderLayout());
        this.button.setBorder(new EmptyBorder(0,0,0,0));

        this.icon = BasePanel.createTitle("+", "Arial", Font.PLAIN, 20, new Color(25, 25, 25));
        this.icon.setHorizontalAlignment(SwingConstants.CENTER);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book selected = (Book) bookBox.getComboBox().getSelectedItem();
                if(selected!=null&&selected.getQuantity()>0){
                    double price = selected.getPrice();
                    int quantity = spinner.getValue();
                    sumQuantity+=quantity;
                    addOrUpdateBook(selected.getId(),selected.getName(), price, quantity);

                    totalAmountPanel.updateTotals();

                    //Logic sau khi đã add xong
                    bookBox.getComboBox().setSelectedItem(null);
                    bookBox.setBorder(new Color(221, 221, 221),1);
                }else {
                    bookBox.setBorder(new Color(255, 0, 0),2);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(bookBox.getComboBox().getSelectedItem()==null){
                    button.setBackground(new Color(113, 113, 113));
                }else {
                    button.setBackground(new Color(0, 64, 193));
                    icon.setForeground(new Color(255, 255, 255));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(bookBox.getComboBox().getSelectedItem()==null){
                    button.setBackground(new Color(113, 113, 113));
                }else {
                    button.setBackground(new Color(0, 82, 255));
                    icon.setForeground(new Color(255, 255, 255));
                }
            }
        });

        button.add(icon, BorderLayout.CENTER);
        return button;
    }

    public void addOrUpdateBook(int bookId,String name, double price, int quantity) {

        JTable table = listPanel.tablePanel.getTable();

        for (int i = 0; i < table.getRowCount(); i++) {

            String existName = (String) table.getValueAt(i, 0);

            if (existName.equals(name)) {

                int oldQuantity = (int) table.getValueAt(i, 2);
                int newQuantity = oldQuantity + quantity;

                table.setValueAt(newQuantity, i, 2);
                table.setValueAt(price * newQuantity, i, 3);

                table.repaint();
                return;
            }
        }
        listPanel.addData(new Object[]{bookId,name, price, quantity, price * quantity, null});
    }

}
