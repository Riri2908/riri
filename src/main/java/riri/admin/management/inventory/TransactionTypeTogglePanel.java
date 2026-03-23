package riri.admin.management.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TransactionTypeTogglePanel extends JPanel {
    public TransactionTypeTogglePanel(InventoryPanel inventoryPanel) {

        setOpaque(false);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, ToggleButton.getHEIGHT_BUTTON()));

        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        ToggleButton importButton = new ToggleButton("Nhập hàng","import");
        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inventoryPanel.showImport();
            }
        });
//
//        ToggleButton exportButton = new ToggleButton("Xuất hàng","export");
//
//        exportButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                inventoryPanel.showExport();
//            }
//        });

        add(importButton);
//        add(Box.createHorizontalStrut(15));
//        add(exportButton);


    }
}
