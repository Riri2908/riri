package riri.admin.dashboard.view;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashBoardStatPanel extends JPanel {
    public BorderPanel bookStatCard;
    public BorderPanel revenueStatCard;
    public BorderPanel orderStatCard;
    public BorderPanel customerStatCard;

    public  DashBoardStatPanel() {
        setOpaque(false);
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        bookStatCard = statCard("Tổng số sách đã bán", new Color(43, 126, 253),"homepage/book", 0, Double.NaN, "% so với tháng trước", Color.WHITE, new Color(198, 218, 251));
        revenueStatCard = statCard("Doanh thu tháng này", new Color(22, 163, 74), "homepage/revenue", 0, Double.NaN, "% so với tháng trước", Color.WHITE, new Color(187, 247, 208));
        orderStatCard = statCard("Số hóa đơn", new Color(172, 70, 253), "homepage/invoice", 0, Double.NaN, "% so với tháng trước", Color.WHITE, new Color(244, 210, 255));
        customerStatCard = statCard("Số lượng khách quay lại", new Color(253,104,0), "homepage/users",   0, Double.NaN, "% so với tháng trước", Color.WHITE, new Color(255, 217, 194));

        add(bookStatCard);
        add(Box.createHorizontalStrut(15));
        add(revenueStatCard);
        add(Box.createHorizontalStrut(15));
        add(orderStatCard);
        add(Box.createHorizontalStrut(15));
        add(customerStatCard);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height));
    }

    public BorderPanel statCard(String title, Color titleColor, String iconName, double value, double percent, String subText, Color backgroundColor, Color borderColor){
        BorderPanel cardPanel = new BorderPanel(17,backgroundColor,6,20,borderColor,1);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(15,15,10,10));
        cardPanel.putClientProperty("titleColor", titleColor);
        cardPanel.putClientProperty("iconName", iconName);

        Icon icon = BasePanel.createIcon(getClass(),iconName, 24,24,titleColor);
        JLabel titleLabel = BasePanel.createTitle(title,"Arial",Font.BOLD,18,titleColor);
        titleLabel.setIcon(icon);
        titleLabel.setIconTextGap(5);

        JLabel valueLabel = BasePanel.createTitle(BasePanel.formatNumber((long) value),"Arial",Font.BOLD,20,Color.BLACK);
        valueLabel.setName("valueLabel");
        valueLabel.setBorder(new EmptyBorder(0,20,0,0));

        Icon iconUp = BasePanel.createIcon(getClass(),"homepage/up",15,15,titleColor);
        Icon iconDown = BasePanel.createIcon(getClass(),"homepage/down",15,15,titleColor);

        JLabel subLabel = BasePanel.createTitle(String.format("%.1f", percent) + subText,"Arial",Font.PLAIN,14,titleColor);
        subLabel.setName("subLabel");
        if(percent==-Double.MAX_VALUE){
            subLabel.setIcon(null);
            subLabel.setText(subText);
        }else {
            if(percent >=0)
                subLabel.setIcon(iconUp);
            else
                subLabel.setIcon(iconDown);
        }

        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(valueLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(subLabel);

        return cardPanel;
    }

    public void updateStatCard(BorderPanel card, double value, double percent, String subText) {
        Color titleColor = (Color) card.getClientProperty("titleColor");
        String iconName  = (String) card.getClientProperty("iconName");

        for (Component c : card.getComponents()) {
            if (!(c instanceof JLabel label)) continue;
            switch (label.getName() == null ? "" : label.getName()) {
                case "valueLabel" -> label.setText(BasePanel.formatNumber((long) value));
                case "subLabel"   -> {
                    JLabel fresh = buildSubLabel(percent, subText, titleColor, iconName);
                    label.setIcon(fresh.getIcon());
                    label.setText(fresh.getText());
                    label.setForeground(fresh.getForeground());
                }
            }
        }
        card.revalidate();
        card.repaint();
    }

    private JLabel buildSubLabel(double percent, String subText, Color titleColor, String iconName) {
        JLabel subLabel = BasePanel.createTitle(String.format("%.1f", percent) + subText, "Arial", Font.PLAIN, 14, titleColor);
        Icon _ = BasePanel.createIcon(getClass(),iconName, 24,24,titleColor);

        if (Double.isNaN(percent)) {
            subLabel.setIcon(null);
            subLabel.setText(subText);
        } else {
            Icon iconUp   = BasePanel.createIcon(getClass(), "homepage/up",   15, 15, titleColor);
            Icon iconDown = BasePanel.createIcon(getClass(), "homepage/down", 15, 15, titleColor);
            subLabel.setIcon(percent >= 0 ? iconUp : iconDown);
        }
        return subLabel;
    }
}
