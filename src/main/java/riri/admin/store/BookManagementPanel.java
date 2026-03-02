package riri.admin.store;

import riri.components.CreateButton;
import riri.components.ModernScrollBarUI;
import riri.components.SearchPanel;
import riri.dao.BookDAO;
import riri.model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookManagementPanel extends JPanel {

    private final JPanel listContainer;
//    private final BookDAO bookDAO = new BookDAO();

    public BookManagementPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(247, 248, 249));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));


        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Quản lý sách");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.add(title, BorderLayout.WEST);

        CreateButton btnAdd = new CreateButton();
        btnAdd.setBackground(new Color(45, 110, 255)); // Màu xanh dương
        btnAdd.setLayout(new BorderLayout());
        btnAdd.setPreferredSize(new Dimension(180, 42));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblAdd = new JLabel("+ Thêm sách mới", SwingConstants.CENTER);
        lblAdd.setForeground(Color.WHITE);
        lblAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.add(lblAdd, BorderLayout.CENTER);

        header.add(btnAdd, BorderLayout.EAST);

        SearchPanel searchBar = new SearchPanel();

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);

        JScrollPane scroll = new JScrollPane(listContainer);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // Gom nhóm các phần trên lại
        JPanel topWrapper = new JPanel(new BorderLayout(0, 15));
        topWrapper.setOpaque(false);
        topWrapper.add(header, BorderLayout.NORTH);
        topWrapper.add(searchBar, BorderLayout.CENTER);

        add(topWrapper, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}

//    private void loadBooksToUI() {
//        listContainer.removeAll();
//        // Dùng hàm findAll() để lấy dữ liệu [cite: 8]
//        List<Book> books = bookDAO.findAll();
//
//        for (Book b : books) {
//            // Mỗi dòng sách là một BookItemPanel đã được bo góc
//            listContainer.add(new BookItemPanel(b));
//            // Tạo khoảng hở 12px giữa các dòng để lộ viền bo tròn
//            listContainer.add(Box.createRigidArea(new Dimension(0, 12)));
//        }
//
//        listContainer.revalidate();
//        listContainer.repaint();
//    }