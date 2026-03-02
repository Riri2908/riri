package riri.admin.management.managerbook;

import riri.components.CreateButton;
import riri.components.SearchPanel;
import riri.dao.BookDAO;
import riri.model.Book;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class BookManagementPanel extends JPanel {
    private final JPanel listContainer;
    private final BookDAO bookDAO;
    private final JLabel lblTotalBooks;
    private final SearchPanel searchBar;
    private List<Book> cachedBooks;

    //Ti le cac muc
    public static final double[] COLUMN_WEIGHTS = {0.30, 0.2, 0.1, 0.15, 0.1, 0.15};


    public BookManagementPanel() {
        this.bookDAO = new BookDAO();
        this.listContainer = new JPanel();
        this.lblTotalBooks = new JLabel("Tổng số: 0 đầu sách");
        this.searchBar = new SearchPanel();
        initUI();
        refreshData();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(242, 245, 250));
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        add(createTopHeader(), BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setOpaque(false);

        // Khung tìm kiếm
        JPanel searchCard = new RoundedPanel(20, Color.WHITE);
        searchCard.setLayout(new BorderLayout());
        searchCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        searchCard.setBorder(new EmptyBorder(15, 20, 15, 20));
        searchBar.addSearchEvent(this::filterBooks);
        searchCard.add(searchBar, BorderLayout.CENTER);

        // Khung danh sách
        JPanel listCard = new RoundedPanel(20, Color.WHITE);
        listCard.setLayout(new BorderLayout());
        listCard.add(createColumnHeader(), BorderLayout.NORTH);

        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);

        JScrollPane scroll = new JScrollPane(listContainer);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        listCard.add(scroll, BorderLayout.CENTER);

        bodyPanel.add(searchCard);
        bodyPanel.add(Box.createVerticalStrut(20));
        bodyPanel.add(listCard);
        add(bodyPanel, BorderLayout.CENTER);
    }

    private JPanel createTopHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Quản lý sách");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));

        lblTotalBooks.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTotalBooks.setForeground(new Color(108, 117, 125));

        titlePanel.add(title);
        titlePanel.add(lblTotalBooks);
        header.add(titlePanel, BorderLayout.WEST);

        CreateButton btnAdd = new CreateButton();
        btnAdd.setBackground(new Color(45, 110, 255));
        btnAdd.setPreferredSize(new Dimension(150, 40));

        // Dùng GridBagLayout cho nút để căn giữa tuyệt đối phần chữ bên trong
        btnAdd.setLayout(new GridBagLayout());

        JLabel lblAdd = new JLabel("+ Thêm sách mới"); // Không cần SwingConstants.CENTER nữa
        lblAdd.setForeground(Color.WHITE);
        lblAdd.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // Thêm chữ vào giữa nút
        btnAdd.add(lblAdd);

        // Tạo hộp bảo vệ (wrapper)
        JPanel buttonWrapper = new JPanel(new GridBagLayout());
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(btnAdd);

        // Bỏ hộp vào Header
        header.add(buttonWrapper, BorderLayout.EAST);

        return header;
    }

    private JPanel createColumnHeader() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(235, 238, 242)),
                BorderFactory.createEmptyBorder(18, 25, 18, 25) // Giống border của item
        ));

        String[] columns = {"TÊN SÁCH", "TÁC GIẢ", "MÃ SÁCH", "GIÁ", "SỐ LƯỢNG", "     THAO TÁC"};
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < columns.length; i++) {
            gbc.gridx = i;
            gbc.weightx = COLUMN_WEIGHTS[i];
            gbc.insets = new Insets(0, 0, 0, 0);

            JLabel lbl = new JLabel(columns[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lbl.setForeground(new Color(85, 95, 110));

            int padLeft = 0;
            if (i == 0) padLeft = 47; // Bù cho icon (32 + 15 gap)

            lbl.setBorder(BorderFactory.createEmptyBorder(0, padLeft, 0, 0));

            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setOpaque(false);
            wrapper.setPreferredSize(new Dimension(0, lbl.getPreferredSize().height));
            wrapper.add(lbl, BorderLayout.WEST);
            panel.add(wrapper, gbc);
        }
        return panel;
    }

    private void filterBooks() {
        String kw = searchBar.getSearchText().toLowerCase();
        List<Book> filtered = cachedBooks.stream()
                .filter(b -> b.getName().toLowerCase().contains(kw) ||
                        b.getAuthor().toLowerCase().contains(kw) ||
                        b.getId().toLowerCase().contains(kw))
                .collect(Collectors.toList());
        renderList(filtered);
    }

    private void renderList(List<Book> books) {
        listContainer.removeAll();
        for (Book b : books) {
            listContainer.add(new BookItemPanel(b));
            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(245, 246, 248));

            //khoa do day
            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            listContainer.add(sep);
        }

        //Hang doi :)
        listContainer.add(Box.createVerticalGlue());

        listContainer.revalidate();
        listContainer.repaint();
    }

    public void refreshData() {
        this.cachedBooks = bookDAO.findAll();
        renderList(cachedBooks);
        lblTotalBooks.setText("Tổng số: " + cachedBooks.size() + " đầu sách");
    }

    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bgColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius;
            this.bgColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}