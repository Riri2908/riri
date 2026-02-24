package riri.admin.management.stat;

import riri.dao.BookDAO;
import riri.model.Book;
import riri.service.BookStatistics;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManagementStatCard extends JPanel {

    private final BookDAO bookDAO = new BookDAO();
    private final List<Book> bookList=bookDAO.findAll();

    private final double quantity= BookStatistics.totalQuantity(bookList);
    private final int HEIGHT=120;
    public ManagementStatCard(){
        setPreferredSize(new Dimension(0, HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, HEIGHT));
        setLayout(new GridLayout(1,3,25,0));
        setOpaque(false);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

        panel.add(new ManagementStat("Tổng số nhập",quantity,new Color(0, 200, 80),"import"));
        panel.add(Box.createHorizontalStrut(15));
        panel.add(new ManagementStat("Tổng số xuất",quantity,Color.RED,"export"));
        panel.add(Box.createHorizontalStrut(15));
        panel.add(new ManagementStat("Tồn kho hiện tại",quantity,new Color(43, 126, 253),"inventory"));
        add(panel);
    }
}
