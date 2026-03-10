package riri.admin.management.inventory.form;

import riri.admin.management.history.HistoryPanel;
import riri.admin.management.stat.ManagementStatCard;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExportFormPanel extends BaseFormPanel{
    public ExportFormPanel(HistoryPanel historyPanel, ManagementStatCard statCard) {
        super(historyPanel, statCard,"Xuất");

        BorderPanel exportButton = new BorderPanel(16,new Color(255, 0, 0),0,0,Color.WHITE,0);
        exportButton.setLayout(new BorderLayout());
        JLabel label = BasePanel.createTitle("Xuất hàng", "Arial", Font.PLAIN, 17, Color.WHITE);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        exportButton.add(label,BorderLayout.CENTER);

        exportButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addData("Xuất");
                statCard.updateQuantity();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                exportButton.setBackground(new Color(161, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exportButton.setBackground(new Color(255, 0, 0));
            }
        });
        addItem(exportButton,3,3);
    }
}
