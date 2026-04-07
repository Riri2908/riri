package riri.admin.dashboard;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.DefaultCategoryDataset;
import riri.components.BorderPanel;
import riri.model.Book;
import riri.model.Invoice;
import riri.service.component.Period;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;

public class RevenueChartPanel extends BorderPanel {
    public Map<Integer, Integer> data = AppContext.BOOK_SERVICE.totalQuantityByArea();

    public BorderPanel mainPanel = new BorderPanel(0, new Color(247, 248, 249), 0, 0, null, 0);

    public RevenueChartPanel() {
        super(0, new Color(247, 248, 249), 0, 0, null, 0);
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        mainPanel.setLayout(new GridLayout(1, 2, 15, 0));

        mainPanel.add(revenueChart());
        mainPanel.add(quantityChart());
        add(mainPanel, BorderLayout.CENTER);
    }

    private ChartPanel createScalableChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart, false);
        ToolTipManager.sharedInstance().registerComponent(chartPanel);
        ToolTipManager.sharedInstance().setInitialDelay(0);

        chartPanel.setOpaque(false);
        chartPanel.setBackground(new Color(0, 0, 0, 0));
        chartPanel.setPreferredSize(new Dimension(100, 300));
        chartPanel.setOpaque(false);
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);
        chartPanel.setDisplayToolTips(true);

        chartPanel.setMaximumDrawWidth(5000);
        chartPanel.setMaximumDrawHeight(5000);
        chartPanel.setMinimumDrawWidth(0);
        chartPanel.setMinimumDrawHeight(0);

        return chartPanel;
    }

    public BorderPanel revenueChart() {
        BorderPanel panel = new BorderPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,10,10,10));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        LocalDate now = LocalDate.now();

        for (int month = 0; month < 6; month++) {

            LocalDate date = now.minusMonths(5 - month);

            dataset.addValue(AppContext.INVOICE_SERVICE.totalPrice(date, Period.MONTH), "Doanh thu", "Tháng " + date.getMonthValue());
        }

        JFreeChart chart = ChartFactory.createLineChart("Biểu đồ doanh thu", "", "", dataset,
                PlotOrientation.VERTICAL, false, true, false
        );
        styleLineChart(chart);

        panel.add(createScalableChartPanel(chart), BorderLayout.CENTER);
        return panel;
    }

    private void styleLineChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        chart.setBorderVisible(false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(140, 140, 140));
        plot.setBackgroundPaint(new Color(0, 0, 0, 0));

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(59, 130, 246));
        renderer.setSeriesStroke(0, new BasicStroke(3f));
        renderer.setDefaultShapesVisible(true);
        renderer.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());
        renderer.setItemMargin(0.1);

        CategoryAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible(false);
        axis.setTickMarksVisible(false);
        renderer.setDefaultShapesVisible(true);

    }

    public BorderPanel quantityChart() {
        BorderPanel panel = new BorderPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,10,10,10));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        data.forEach((areaId, totalQty) -> {

            String areaName = AppContext.AREA_SERVICE.findById(areaId).getName();

            dataset.addValue(totalQty, "Sách", areaName);
        });

        JFreeChart chart = ChartFactory.createBarChart(
                "Số lượng sách theo khu vực", "", "", dataset,
                PlotOrientation.VERTICAL, false, true, false
        );
        styleBarChart(chart);

        panel.add(createScalableChartPanel(chart), BorderLayout.CENTER);
        return panel;
    }

    private void styleBarChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        chart.setBorderVisible(false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
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