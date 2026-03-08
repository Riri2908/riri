package riri.admin.management.stat;

import riri.util.AppContext;
import riri.admin.management.stat.ManagementStat;

import javax.swing.*;
import java.awt.*;

public class ManagementStatCard extends JPanel {
    private final double quantity= AppContext.BOOK_SERVICE.totalQuantity();
    private final double importQuantity= AppContext.TRANSACTION_SERVICE.getTotalImportQuantity();
    private final double exportQuantity= AppContext.TRANSACTION_SERVICE.getTotalExportQuantity();

    private static ManagementStat importStat;
    private static ManagementStat exportStat;
    private static ManagementStat quantityStat;

    private final int HEIGHT=120;
    
    public ManagementStatCard(){
        setPreferredSize(new Dimension(0, HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, HEIGHT));
        setLayout(new GridLayout(1,3,25,0));
        setOpaque(false);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

        importStat = new ManagementStat("Tổng số nhập",importQuantity,new Color(0, 200, 80),"import");
        exportStat = new ManagementStat("Tổng số xuất",exportQuantity,Color.RED,"export");
        quantityStat = new ManagementStat("Tồn kho hiện tại",quantity,new Color(43, 126, 253),"inventory");
        panel.add(importStat);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(exportStat);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(quantityStat);
        add(panel);
    }
    public static void updateQuantity(){
        double quantity = AppContext.BOOK_SERVICE.totalQuantity();
        double importQuantity = AppContext.TRANSACTION_SERVICE.getTotalImportQuantity();
        double exportQuantity = AppContext.TRANSACTION_SERVICE.getTotalExportQuantity();

        importStat.setValue(importQuantity);
        exportStat.setValue(exportQuantity);
        quantityStat.setValue(quantity);
    }
}
