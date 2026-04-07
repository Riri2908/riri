package riri.admin.dashboard;

import riri.admin.dashboard.controller.DashBoardController;
import riri.admin.dashboard.view.DashBoardStatPanel;
import riri.admin.dashboard.view.RevenueChartPanel;
import riri.admin.dashboard.view.WarningPanel;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashBoardPage extends BorderPanel {

    public DashBoardStatPanel statPanel;
    public RevenueChartPanel revenueChartPanel;
    public WarningPanel warningPanel;

    public BorderPanel mainPanel = new BorderPanel(0,new Color(247, 248, 249),0,0,null,0);
    public DashBoardPage() {
        super(0, new Color(247, 248, 249), 0, 0, null, 0);
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(25, 25, 25, 25));

        this.revenueChartPanel = new RevenueChartPanel();
        this.warningPanel = new WarningPanel();
        this.statPanel = new DashBoardStatPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(statPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(revenueChartPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(warningPanel);

        JScrollPane scrollPane = BasePanel.createScroll(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        new DashBoardController(this,statPanel,revenueChartPanel,warningPanel);

    }

}
