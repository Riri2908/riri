package riri.components;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SearchPanel extends JPanel {

    private JTextField textField;
    private boolean isFocused = false;

    public void addSearchEvent(Runnable event) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { event.run(); }
            @Override
            public void removeUpdate(DocumentEvent e) { event.run(); }
            @Override
            public void changedUpdate(DocumentEvent e) { event.run(); }
        });
    }

    public String getSearchText() {
        return textField.getText().trim().toLowerCase();
    }
    public SearchPanel() {
        setOpaque(false);
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        setPreferredSize(new Dimension(800, 48));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        JLabel searchIcon = new JLabel(new SearchIcon());

        // Ô nhập chữ
        textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ chữ Placeholder mờ
                if (getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(150, 150, 150));
                    g2.setFont(getFont());
                    FontMetrics fm = g2.getFontMetrics();
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    g2.drawString("Tìm kiếm theo tên sách, tác giả, hoặc thể loại...", 2, y);
                    g2.dispose();
                }
            }
        };

        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textField.setForeground(new Color(50, 50, 50));
        textField.setCaretColor(new Color(45, 110, 255));

        // 3. THÊM SỰ KIỆN FOCUS ĐỂ ĐỔI MÀU VIỀN
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                isFocused = true; // Khi click chuột vào ô search
                SearchPanel.this.repaint(); // Cập nhật lại màu viền
            }

            @Override
            public void focusLost(FocusEvent e) {
                isFocused = false; // Khi click chuột ra chỗ khác
                SearchPanel.this.repaint(); // Trả lại viền màu xám
            }
        });

        add(searchIcon, BorderLayout.WEST);
        add(textField, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Nen trang
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);

        // Nhap vao nhap nhay
        if (isFocused) {
            g2.setColor(new Color(45, 110, 255)); // Màu viền xanh dương khi đang click
            g2.setStroke(new BasicStroke(1.5f));
        } else {
            g2.setColor(new Color(220, 220, 220)); // Màu viền xám nhạt mặc định
            g2.setStroke(new BasicStroke(1.0f));
        }

        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);

        g2.dispose();
    }

    // Class vẽ kính lúp
    private static class SearchIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(150, 150, 150));
            g2.setStroke(new BasicStroke(2f));

            g2.drawOval(x + 2, y + 4, 12, 12);
            g2.drawLine(x + 11, y + 13, x + 17, y + 19);

            g2.dispose();
        }

        @Override
        public int getIconWidth() { return 22; }

        @Override
        public int getIconHeight() { return 22; }
    }
}