package riri.admin.dashboard;

import riri.components.sidebar.SideBar;

import javax.swing.*;
import java.awt.*;

public class DashBoardPage extends JPanel {
    public DashBoardPage() {
        setOpaque(true);
        setBackground(new Color(239, 239, 239));

        setPreferredSize(new Dimension(SideBar.WIDTH-280,0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE,0));
        setLayout(new BorderLayout());

        DashboardStatCardPanel  dashboardStatCardPanel = new DashboardStatCardPanel();

        add(dashboardStatCardPanel,BorderLayout.NORTH);
    }
}
