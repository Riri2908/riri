package riri.admin.management.inventory.form;

import riri.admin.management.history.HistoryPanel;
import riri.admin.management.stat.ManagementStat;
import riri.admin.management.stat.ManagementStatCard;
import riri.components.BorderPanel;
import riri.components.field.FieldPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ImportFormPanel extends BaseFormPanel {

    private final BorderPanel importButton = new BorderPanel(16,new Color(0, 165, 62),0,0,Color.WHITE,0);


    public ImportFormPanel(HistoryPanel historyPanel) {
        super(historyPanel, "Nhập");

        importButton.setLayout(new BorderLayout());
        JLabel label = BasePanel.createTitle("Nhập hàng", "Arial", Font.PLAIN, 17, Color.WHITE);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        importButton.add(label,BorderLayout.CENTER);

        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addData("Nhập");
                ManagementStatCard.updateQuantity();
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
        addItem(importButton,3,3);
    }
}
