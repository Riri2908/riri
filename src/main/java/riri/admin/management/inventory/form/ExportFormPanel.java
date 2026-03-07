package riri.admin.management.inventory.form;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import java.awt.*;

public class ExportFormPanel extends BaseFormPanel{
    public ExportFormPanel() {
        super();
        BorderPanel exportButton = new BorderPanel(16,new Color(255, 0, 0),0,0,Color.WHITE,0);
        exportButton.setLayout(new BorderLayout());
        JLabel label = BasePanel.createTitle("Xuất hàng", "Arial", Font.PLAIN, 17, Color.WHITE);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        exportButton.add(label,BorderLayout.CENTER);
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(exportButton,gbc);
    }
}
