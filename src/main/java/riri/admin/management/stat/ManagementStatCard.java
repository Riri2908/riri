package riri.admin.management.stat;

import riri.components.page.StatPanel;
import riri.util.AppContext;

import javax.swing.*;
import java.awt.*;

public class ManagementStatCard extends JPanel {
    private final double quantity= AppContext.BOOK_SERVICE.totalQuantity();
    private final double importQuantity= AppContext.TRANSACTION_SERVICE.getTotalImportQuantity();
    private final double exportQuantity= AppContext.TRANSACTION_SERVICE.getTotalExportQuantity();

    public StatPanel importStat;
    public StatPanel exportStat;
    public StatPanel quantityStat;

    private final int HEIGHT=120;
    
    public ManagementStatCard(){
        setPreferredSize(new Dimension(0, HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, HEIGHT));
        setLayout(new GridLayout(1,3,25,0));
        setOpaque(false);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

        importStat = new StatPanel("Tổng số nhập",String.valueOf( importQuantity),"sản phẩm",new Color(0, 200, 80),"management/package_import");
        exportStat = new StatPanel("Tổng đã bán",String.valueOf(exportQuantity),"sản phẩm",Color.RED,"management/package_export");
        quantityStat = new StatPanel("Tồn kho hiện tại",String.valueOf(quantity),"sản phẩm",new Color(43, 126, 253),"management/package_inventory");

        panel.add(importStat);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(exportStat);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(quantityStat);
        add(panel);
    }
    public void updateQuantity(){
        double quantity = AppContext.BOOK_SERVICE.totalQuantity();
        double importQuantity = AppContext.TRANSACTION_SERVICE.getTotalImportQuantity();
        double exportQuantity = AppContext.TRANSACTION_SERVICE.getTotalExportQuantity();

        this.importStat.setValue(importQuantity);
        this.exportStat.setValue(exportQuantity);
        this.quantityStat.setValue(quantity);
    }
}
