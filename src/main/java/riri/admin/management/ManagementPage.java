package riri.admin.management;

import riri.admin.management.inventory.InventoryPanel;
import riri.admin.management.history.HistoryPanel;
import riri.admin.management.stat.ManagementStatCard;
import riri.components.SearchPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagementPage extends JPanel {

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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setFocusable(true);

        ManagementStatCard stat = new ManagementStatCard();
        InventoryPanel iventoryPanel = new InventoryPanel();
        HistoryPanel historyPanel = new HistoryPanel();
        JTable table = historyPanel.getTable();
        SearchPanel searchPanel = new SearchPanel(table);

        panel.add(stat);
        panel.add(Box.createVerticalStrut(25));
        panel.add(iventoryPanel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(searchPanel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(historyPanel);

        JScrollPane scrollPanel = BasePanel.createScroll(panel);
        scrollPanel.setBorder(new EmptyBorder(25,25,25,25));

        add(scrollPanel,BorderLayout.CENTER);

    }
}
