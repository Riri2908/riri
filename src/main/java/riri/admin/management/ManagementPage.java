package riri.admin.management;

import riri.admin.management.inventory.InventoryPanel;
import riri.admin.management.history.HistoryPanel;
import riri.admin.management.stat.ManagementStatCard;
import riri.components.page.SearchPanel;
import riri.components.page.BasePanel;
import riri.components.table.TablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagementPage extends JPanel {

    public ManagementStatCard stat = new ManagementStatCard();
    public HistoryPanel historyPanel = new HistoryPanel();
    public InventoryPanel iventoryPanel = new InventoryPanel(historyPanel,stat);

    public ManagementPage() {
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

        TablePanel table = historyPanel.getTable();
        SearchPanel searchPanel = new SearchPanel(table,"Tìm kiếm theo số thứ tự, tên sách, tên tác giả...");

        panel.add(stat);
        panel.add(Box.createVerticalStrut(25));
        panel.add(iventoryPanel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(searchPanel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(historyPanel);

        JScrollPane scrollPanel = BasePanel.createScroll(panel);

        add(scrollPanel,BorderLayout.CENTER);

    }
}
