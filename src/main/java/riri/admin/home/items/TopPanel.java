package riri.admin.home.items;

import riri.admin.home.model.BookSaleStat;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.page.ContentPanel;
import riri.components.table.CustomRenderer;
import riri.components.table.TablePanel;
import riri.model.*;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TopPanel extends BorderPanel {
    public ContentPanel contentPanel;

    public GridBagConstraints gbc = new GridBagConstraints();
    public TablePanel topBookTable = new TablePanel();
    public CustomRenderer bookRenderer = new CustomRenderer();
    public TablePanel customerTable = new TablePanel();
    public CustomRenderer customerRenderer = new CustomRenderer();

    public TopPanel(ContentPanel contentPanel) {
        super(0,new Color(247, 248, 249),0,0,null,0);
        this.contentPanel=contentPanel;

        setLayout(new GridBagLayout());

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        settingTopBookTable();
        settingCustomerTable();
        addItem(topBookTable,0,0);
        addItem(customerTable,1,0);
    }

    public void settingTopBookTable(){
        topBookTable.setTitle("Top sách bán chạy");
        topBookTable.getTitle().setIcon(BasePanel.createIcon(getClass(),"homepage/flame",30,30,new Color(255, 88, 0)));
        topBookTable.addColumn(0,"Book");
        topBookTable.getTable().setRowHeight(100);

        bookRenderer.setDefaultSetting((label,row) ->{
            boolean hover = row == topBookTable.getHoveredRow();
            label.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0,
                            hover ? new Color(245, 245, 245) : new Color(230, 230, 230)),
                    new EmptyBorder(10, 20, 10, 10)
            ));
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setForeground(new Color(57, 57, 57));
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setIcon(null);
            label.setOpaque(true);
            return label;
        });


        bookRenderer.addLabel(0,(label, stat,row)->{
            boolean hover = row == topBookTable.getHoveredRow();
            BookSaleStat book = (BookSaleStat) stat;

            JPanel panel = new JPanel();
            panel.setOpaque(false);
            panel.setBorder(new EmptyBorder(10, 30, 10, 30));
            panel.setLayout(new BorderLayout());

            BorderPanel infomationPanel = new BorderPanel(16,Color.WHITE,0,0,null,0);
            infomationPanel.setBorder(new EmptyBorder(2,10,2,10));
            infomationPanel.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
            infomationPanel.setLayout(new BorderLayout());

            Icon icon = BasePanel.createIcon(getClass(),"homepage/book_check",26,26,new Color(0, 80, 255));
            JLabel cover = new JLabel(icon);
            cover.setBorder(new EmptyBorder(0,0,0,20));
            infomationPanel.add(cover, BorderLayout.WEST);

            JPanel info = new JPanel();
            info.setOpaque(false);
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

            JLabel titleLabel = BasePanel.createTitle(book.getBook().getName(),"Arial",Font.BOLD,13,Color.BLACK);
            titleLabel.setBorder(new EmptyBorder(2,0,2,0));
            JLabel authorLabel = BasePanel.createTitle(book.getBook().getAuthor(),"Arial",Font.PLAIN,13,Color.BLACK);
            authorLabel.setBorder(new EmptyBorder(2,0,2,0));
            JLabel priceLabel =  BasePanel.createTitle(book.getBook().getPrice()+" đ","Arial",Font.BOLD,13,new Color(0, 89, 255));
            priceLabel.setBorder(new EmptyBorder(2,0,2,0));

            info.add(Box.createVerticalStrut(10));
            info.add(titleLabel);
            info.add(authorLabel);
            info.add(priceLabel);

            infomationPanel.add(info, BorderLayout.CENTER);

            JLabel saleLabel = BasePanel.createTitle("Đã bán "+book.getQuantity(),"Arial",Font.BOLD,13,Color.BLACK);

            infomationPanel.add(saleLabel, BorderLayout.EAST);
            panel.add(infomationPanel, BorderLayout.CENTER);

            return panel;
        });

        bookRenderer.setHeaderSetting(label -> {
            label.setBackground(new Color(255, 255, 255));
            label.setOpaque(true);
            label.setText("");
            return label;
        });

        bookRenderer.applyHeader(topBookTable.getTable());
        topBookTable.setRenderer(bookRenderer);
        loadBookTable();
        topBookTable.add(BUTTON_SHOWPAGE("Xem tất cả sách", new Color(12, 16, 255),new Color(231, 239, 255), "Quản lý sách"),BorderLayout.SOUTH);
    }

    public void loadBookTable(){
        Map<Integer, BookSaleStat> data = getBookSaleStats();

        if(data.isEmpty()){
            return;
        }
        int i = 0;
        for (BookSaleStat stat : data.values()) {
            topBookTable.addRow(new Object[]{stat});

            if (++i == 5) break;
        }
    }

    public Map<Integer, BookSaleStat> getBookSaleStats() {

        Map<Integer, Invoice> invoices = AppContext.INVOICE_SERVICE.getAll();
        Map<Integer, Book> books = AppContext.BOOK_SERVICE.getAll();
        Map<Integer, BookSaleStat> result = new HashMap<>();

        for (Invoice invoice : invoices.values()) {

            for (InvoiceDetail detail : invoice.getDetails()) {

                int bookId = detail.getBookId();
                int qty = detail.getQuantity();

                Book book = books.get(bookId);
                if (book == null) continue;

                result.compute(bookId, (id, stat) -> {
                    if (stat == null)
                        return new BookSaleStat(book, qty);

                    stat.add(qty);
                    return stat;
                });
            }
        }

        return result.entrySet().stream()
                .sorted((a, b) ->
                        Integer.compare(b.getValue().getQuantity(), a.getValue().getQuantity()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public void settingCustomerTable(){
        customerTable.setTitle("Khách mua nhiều");
        customerTable.getTitle().setIcon(BasePanel.createIcon(getClass(),"homepage/flame",30,30,new Color(255, 88, 0)));
        customerTable.addColumn(0,"Customer");
        customerTable.getTable().setRowHeight(100);

        customerRenderer.setDefaultSetting((label,row) ->{
            boolean hover = row == topBookTable.getHoveredRow();
            label.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0,
                            hover ? new Color(245, 245, 245) : new Color(230, 230, 230)),
                    new EmptyBorder(10, 20, 10, 10)
            ));
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setForeground(new Color(57, 57, 57));
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setIcon(null);
            label.setOpaque(true);
            return label;
        });

        customerRenderer.addLabel(0,(label, stat,row)->{
            boolean hover = row == customerTable.getHoveredRow();
            Customer customer = (Customer) stat;

            JPanel panel = new JPanel();
            panel.setOpaque(false);
            panel.setBorder(new EmptyBorder(10, 30, 10, 30));
            panel.setLayout(new BorderLayout());

            BorderPanel infomationPanel = new BorderPanel(16,Color.WHITE,0,0,null,0);
            infomationPanel.setBorder(new EmptyBorder(2,10,2,10));
            infomationPanel.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
            infomationPanel.setLayout(new BorderLayout());

            Icon icon = BasePanel.createIcon(getClass(),"homepage/users",26,26,new Color(255, 86, 188));
            JLabel cover = new JLabel(icon);
            cover.setBorder(new EmptyBorder(0,0,0,20));
            infomationPanel.add(cover, BorderLayout.WEST);

            JPanel info = new JPanel();
            info.setOpaque(false);
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

            JLabel titleLabel = BasePanel.createTitle(customer.getName(),"Arial",Font.BOLD,15,Color.BLACK);
            titleLabel.setBorder(new EmptyBorder(2,0,2,0));
            CustomerType c = AppContext.CUSTOMER_TYPE_SERVICE.findById(customer.getIdType());

            JLabel typeLabel = BasePanel.createTitle(c.getName()+" ● "+customer.getTotalOrders()+ " đơn","Arial",Font.PLAIN,13,Color.BLACK);
            typeLabel.setBorder(new EmptyBorder(2,0,2,0));

            info.add(Box.createVerticalStrut(10));
            info.add(titleLabel);
            info.add(typeLabel);

            infomationPanel.add(info, BorderLayout.CENTER);

            JLabel saleLabel = BasePanel.createTitle("Đã chi tiêu "+BasePanel.formatNumber((long) customer.getTotalPrice()),"Arial",Font.BOLD,13,Color.BLACK);

            infomationPanel.add(saleLabel, BorderLayout.EAST);
            panel.add(infomationPanel, BorderLayout.CENTER);

            return panel;
        });

        customerRenderer.setHeaderSetting(label -> {
            label.setBackground(new Color(255, 255, 255));
            label.setOpaque(true);
            label.setText("");
            return label;
        });

        customerRenderer.applyHeader(customerTable.getTable());
        customerTable.setRenderer(customerRenderer);
        loadCustomerable();
        customerTable.add(BUTTON_SHOWPAGE("Xem tất cả khách hàng", new Color(210, 12, 255),new Color(247, 231, 255),"Khách hàng"),BorderLayout.SOUTH);
    }

    public void loadCustomerable(){
        Map<Integer, Customer> data = AppContext.CUSTOMER_SERVICE.getAll();
        List<Customer> customers = new ArrayList<>(data.values());
        customers.sort(Comparator.comparing(Customer::getTotalPrice).reversed());

        if(customers.isEmpty()){
            return;
        }
        int i = 0;
        for (Customer c : customers) {
            customerTable.addRow(new Object[]{c});

            if (++i == 5) break;
        }
    }


    public JPanel BUTTON_SHOWPAGE(String text,Color textColor,Color background,String Constructor){
        BorderPanel BUTTON = new BorderPanel(16,Color.WHITE,0,0,textColor,1);
        BUTTON.setLayout(new BorderLayout());

        JLabel label = BasePanel.createTitle(text,"Arial",Font.BOLD,14,textColor);
        label.setBorder(new EmptyBorder(10,20,10,20));
        label.setHorizontalAlignment(JLabel.CENTER);
        BUTTON.add(label,BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBorder(new EmptyBorder(15,0,15,0));
        wrapper.setOpaque(false);
        wrapper.add(BUTTON);

        wrapper.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPanel.showPage(Constructor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                BUTTON.setBackground(background);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                BUTTON.setBackground(new Color(255, 255, 255));
            }
        });

        return wrapper;
    }

    public void addItem(Component component, int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        add(component,gbc);
    }
}
