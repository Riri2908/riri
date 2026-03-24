package riri.admin.invoice.page;

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

import java.awt.event.ActionListener;
import java.util.Map;

public class ShoppingCart extends BorderPanel {
    public Map<Integer, Book> books = AppContext.BOOK_SERVICE.getAll();

    public GridBagConstraints gbc = new GridBagConstraints();
    public JLabel titleLabel;

    public ComboBoxPanel<Book> bookBox = new ComboBoxPanel<>();
    public FieldPanel search = new FieldPanel("Tìm kiếm sách...");
    public TablePanel table = new TablePanel();
    public SpinnerPanel spinner = new SpinnerPanel();
    public FieldPanel noteField = new FieldPanel("Ghi chú...");

    public ShoppingCart() {

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20,20,10,20));

        search.setBorder(null,0);
        search.setOpaque(false);
        search.getField().setOpaque(false);

        gbc.insets = new Insets(0,0,15,20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        titleLabel = BasePanel.createTitle("Thêm sách vào giỏ hàng","Segue UI",Font.BOLD, 18, new Color(25, 25, 25));
        titleLabel.setBorder(new EmptyBorder(0,0,10,0));

        addItem(titleLabel,0,0);
        bookBox();
        addItem(BasePanel.createItem("Chọn sách",bookBox),0,1);
        addItem(BasePanel.createItem("Số lượng",spinner),1,1);
        gbc.gridwidth = 2;
        noteField.addBorderFocus();
        addItem(BasePanel.createItem("Ghi chú",noteField),0,2);
        gbc.gridwidth = 1;
    }

    public void bookBox(){


        bookBox.getComboBox().setBorder(new EmptyBorder(0,0,0,0));

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

            JLabel stock = new JLabel("Còn: " + book.getQuantity());
            stock.setForeground(new Color(0,140,70));

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
            assert selected != null;
            System.out.println(selected.getName());
        });

        this.bookBox.getComboBox().setRenderer(renderer);
    }

    public void addItem(Component component, int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        add(component,gbc);
    }

}
