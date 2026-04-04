import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CLone {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Draggable Floating Panel");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ✅ Dùng JLayeredPane thay vì setLayout(null)
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 400));
        frame.setContentPane(layeredPane);

        // ── Tầng nền (layer 0): các panel bình thường ──
        JPanel background = new JPanel(null);
        background.setBounds(0, 0, 600, 400);
        background.setBackground(Color.WHITE);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setBounds(50, 50, 200, 150);
        panel1.add(new JLabel("Panel nền 1"));

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.ORANGE);
        panel2.setBounds(300, 80, 200, 150);
        panel2.add(new JLabel("Panel nền 2"));

        JButton toggleBtn = new JButton("Bật / Tắt Float");
        toggleBtn.setBounds(20, 20, 140, 30);

        background.add(panel1);
        background.add(panel2);
        background.add(toggleBtn);

        // ✅ Thêm background vào tầng DEFAULT (0)
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // ── Tầng nổi (layer PALETTE): panel đè lên tất cả ──
        JPanel floatingPanel = new JPanel(new FlowLayout());
        floatingPanel.setBackground(Color.CYAN);
        floatingPanel.setBounds(180, 120, 220, 130);
        floatingPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        floatingPanel.add(new JLabel("Form nhập liệu"));
        floatingPanel.add(new JTextField(10));
        floatingPanel.setVisible(false);

        // ✅ Thêm floatingPanel vào tầng PALETTE (cao hơn DEFAULT)
        layeredPane.add(floatingPanel, JLayeredPane.PALETTE_LAYER);

        // ── Logic kéo thả ──
        Point clickPoint = new Point();

        floatingPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                clickPoint.setLocation(e.getPoint());
                // ✅ Khi click thì đưa lên tầng cao nhất
                layeredPane.moveToFront(floatingPanel);
            }
        });

        floatingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = floatingPanel.getX() + e.getX() - clickPoint.x;
                int y = floatingPanel.getY() + e.getY() - clickPoint.y;
                floatingPanel.setLocation(x, y);
            }
        });

        // ── Toggle hiện/ẩn ──
        toggleBtn.addActionListener(e ->
                floatingPanel.setVisible(!floatingPanel.isVisible())
        );

        frame.pack();
        frame.setVisible(true);
    }
}