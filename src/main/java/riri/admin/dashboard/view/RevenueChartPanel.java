package riri.admin.dashboard.view;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.DefaultCategoryDataset;
import riri.components.BorderPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RevenueChartPanel extends BorderPanel {

    private final DefaultCategoryDataset revenueDataset  = new DefaultCategoryDataset();
    private final DefaultCategoryDataset quantityDataset = new DefaultCategoryDataset();

    public BorderPanel mainPanel = new BorderPanel(0, new Color(247, 248, 249), 0, 0, null, 0);

    public RevenueChartPanel() {
        super(0, new Color(247, 248, 249), 0, 0, null, 0);
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        mainPanel.setLayout(new GridLayout(1, 2, 15, 0));
        mainPanel.add(buildRevenueChart());
        mainPanel.add(buildQuantityChart());

        add(mainPanel, BorderLayout.CENTER);
    }

    public void setRevenueDataset(DefaultCategoryDataset dataset) {
        revenueDataset.clear();
        for (int r = 0; r < dataset.getRowCount(); r++) {
            for (int c = 0; c < dataset.getColumnCount(); c++) {
                revenueDataset.addValue(
                        dataset.getValue(r, c),
                        dataset.getRowKey(r),
                        dataset.getColumnKey(c));
            }
        }
    }

    public void setQuantityDataset(DefaultCategoryDataset dataset) {
        quantityDataset.clear();
        for (int r = 0; r < dataset.getRowCount(); r++) {
            for (int c = 0; c < dataset.getColumnCount(); c++) {
                quantityDataset.addValue(
                        dataset.getValue(r, c),
                        dataset.getRowKey(r),
                        dataset.getColumnKey(c));
            }
        }
    }

    private BorderPanel buildRevenueChart() {
        BorderPanel panel = new BorderPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JFreeChart chart = ChartFactory.createLineChart(
                "Biểu đồ doanh thu", "", "", revenueDataset,
                PlotOrientation.VERTICAL, false, true, false);
        styleLineChart(chart);

        panel.add(createScalableChartPanel(chart), BorderLayout.CENTER);
        return panel;
    }

    private BorderPanel buildQuantityChart() {
        BorderPanel panel = new BorderPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JFreeChart chart = ChartFactory.createBarChart(
                "Số lượng sách theo khu vực", "", "", quantityDataset,
                PlotOrientation.VERTICAL, false, true, false);
        styleBarChart(chart);

        panel.add(createScalableChartPanel(chart), BorderLayout.CENTER);
        return panel;
    }

    private ChartPanel createScalableChartPanel(JFreeChart chart) {
        ChartPanel cp = new ChartPanel(chart, false);
        ToolTipManager.sharedInstance().registerComponent(cp);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        cp.setOpaque(false);
        cp.setBackground(new Color(0, 0, 0, 0));
        cp.setPreferredSize(new Dimension(100, 300));
        cp.setMouseWheelEnabled(false);
        cp.setDomainZoomable(false);
        cp.setRangeZoomable(false);
        cp.setDisplayToolTips(true);
        cp.setMaximumDrawWidth(5000);
        cp.setMaximumDrawHeight(5000);
        cp.setMinimumDrawWidth(0);
        cp.setMinimumDrawHeight(0);
        return cp;
    }

    private void styleLineChart(JFreeChart chart) {
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        chart.setBorderVisible(false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(0, 0, 0, 0));
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(140, 140, 140));

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(59, 130, 246));
        renderer.setSeriesStroke(0, new BasicStroke(3f));
        renderer.setDefaultShapesVisible(true);
        renderer.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());
        renderer.setItemMargin(0.1);

        CategoryAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible(false);
        axis.setTickMarksVisible(false);
    }

    private void styleBarChart(JFreeChart chart) {
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        chart.setBorderVisible(false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(0, 0, 0, 0));
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(230, 230, 230));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(16, 185, 129));
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setShadowVisible(false);
        renderer.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());

        CategoryAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible(false);
        axis.setTickMarksVisible(false);
    }
}