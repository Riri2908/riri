package riri.admin.store;

import riri.admin.store.listbook.ListBookPanel;
import riri.admin.store.stat.BookStatHeader;
import riri.components.page.BasePanel;
import riri.components.SearchPanel;
import riri.components.table.TablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BookPage extends JPanel {

    public BookPage() {
        setOpaque(true);
        setBackground(new Color(247, 248, 249));
        setLayout(new BorderLayout());
        setFocusable(true);

        JPanel panel = new JPanel();

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });

        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setFocusable(true);

        ListBookPanel listBookPanel = new ListBookPanel();
        TablePanel table = listBookPanel.getTable();
        SearchPanel searchPanel = new SearchPanel(table, "Tìm kiếm theo tên sách, tác giả, thể loại...");

        panel.add(searchPanel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(listBookPanel);

        JScrollPane scrollPanel = BasePanel.createScroll(panel);
        add(scrollPanel, BorderLayout.CENTER);

        BookStatHeader statHeader = new BookStatHeader();
        panel.add(statHeader);
        panel.add(Box.createVerticalStrut(20));
        panel.add(searchPanel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(listBookPanel);
    }
}