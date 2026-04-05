import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class CLone extends JPanel {

    public CLone() {
        setLayout(new BorderLayout());

        // DATA giống categoryData bên React
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(120, "Số lượng", "Tiểu thuyết");
        dataset.addValue(80, "Số lượng", "Khoa học");
        dataset.addValue(60, "Số lượng", "Lịch sử");
        dataset.addValue(150, "Số lượng", "Thiếu nhi");
        dataset.addValue(90, "Số lượng", "Kinh doanh");

        // Tạo chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Số lượng sách theo thể loại", // title
                "Thể loại",                   // X axis
                "Số lượng",                   // Y axis
                dataset
        );

        // Style giống UI React
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(255, 0, 0));

        // Màu cột (#8b5cf6)
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(80, 0, 255));
        renderer.setMaximumBarWidth(0.08);

        // Panel hiển thị
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(600,300));

        add(chartPanel, BorderLayout.CENTER);
    }

        public static void main(String[] args) {

            JFrame frame = new JFrame("Category Chart");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700,400);

            frame.add(new CLone());

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
}