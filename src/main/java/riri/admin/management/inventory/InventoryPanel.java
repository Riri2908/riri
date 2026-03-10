package riri.admin.management.inventory;

import riri.admin.management.history.HistoryPanel;
import riri.admin.management.inventory.form.ExportFormPanel;
import riri.admin.management.inventory.form.ImportFormPanel;
import riri.admin.management.stat.ManagementStatCard;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.table.TablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InventoryPanel extends BorderPanel {
    private final int HEIGHT_INVENTORYPANEL = 260;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public InventoryPanel(HistoryPanel historyPanel, ManagementStatCard statCard) {
        super();

        setMaximumSize(new Dimension(Integer.MAX_VALUE, HEIGHT_INVENTORYPANEL));
        setPreferredSize(new Dimension(0, HEIGHT_INVENTORYPANEL));
        setBorder(new EmptyBorder(20, 20, 20, 15));
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        JLabel title = BasePanel.createTitle("Nhập / Xuất hàng", "Segue UI", Font.BOLD, 18, new Color(53, 53, 53));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        TransactionTypeTogglePanel togglePanel = new TransactionTypeTogglePanel(this);
        togglePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardLayout=new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new ImportFormPanel(historyPanel, statCard),"IMPORT");
        cardPanel.add(new ExportFormPanel(historyPanel, statCard),"EXPORT");
        cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(togglePanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cardPanel);


        add(panel, BorderLayout.CENTER);
    }

    public void showImport(){
        cardLayout.show(cardPanel,"IMPORT");
    }
    public void showExport(){
        cardLayout.show(cardPanel,"EXPORT");
    }
}