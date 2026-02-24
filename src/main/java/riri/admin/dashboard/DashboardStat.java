package riri.admin.dashboard;

import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class DashboardStat extends JPanel {
    private final int PADDING_LEFT =15;
    private final int PADDING_TOP =20;
    private final int PADDING_GAP =6;
    private final int WIDTH =218;
    private final int HEIGHT =125;
    public DashboardStat(String text) {
        setOpaque(false);
        setLayout( new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setMaximumSize(new Dimension(WIDTH,HEIGHT));
        setBorder(new EmptyBorder(PADDING_TOP,PADDING_LEFT,0,0));
        JLabel title = BasePanel.createTitle(text,"Arial",Font.PLAIN,14,new Color(66, 66, 66));

        add(title);
        add(Box.createVerticalStrut(PADDING_GAP));
        add(new JLabel("1234"));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

        g2.dispose();
    }
}
