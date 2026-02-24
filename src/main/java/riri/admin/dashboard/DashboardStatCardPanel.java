package riri.admin.dashboard;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class DashboardStatCardPanel extends JPanel {
    private final int GAP = 20;
    public DashboardStatCardPanel(){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(GAP, GAP, GAP, GAP));
        JPanel statCardPanel = new JPanel();
        statCardPanel.setLayout(new GridLayout(1,4, GAP,0));

        setBorder(new EmptyBorder(25,0,0,0));
        statCardPanel.add(new DashboardStat("Tổng số sách"));
        statCardPanel.add(new DashboardStat("Doanh thu tháng này"));
        statCardPanel.add(new DashboardStat("Số hóa đơn"));
        statCardPanel.add(new DashboardStat("Khách hàng"));
        add(statCardPanel, BorderLayout.CENTER);
    }


}
