package riri.admin.management.inventory.form;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.dao.BookDAO;
import riri.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class ImportFormPanel extends BaseFormPanel {

    private final BookDAO bookDAO = new BookDAO();
    private final Map<Integer,Book> bookList=bookDAO.findAll();
    private final BorderPanel importButton = new BorderPanel(16,new Color(0, 165, 62),0,0,Color.WHITE,0);
    public ImportFormPanel() {
        super();

        importButton.setLayout(new BorderLayout());
        JLabel label = BasePanel.createTitle("Nhập hàng", "Arial", Font.PLAIN, 17, Color.WHITE);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        importButton.add(label,BorderLayout.CENTER);

        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                importButton.setBackground(new Color(27, 135, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                importButton.setBackground(new Color(0, 165, 62));
            }
        });
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(importButton,gbc);
    }
}
