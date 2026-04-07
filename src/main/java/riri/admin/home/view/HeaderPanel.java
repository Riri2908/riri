package riri.admin.home.view;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HeaderPanel extends BorderPanel {
    private final int HEIGHT = 250;

    private final Locale locale=Locale.of("vi","VN");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE', 'dd' tháng 'MM', 'yyy", locale);

    public GridBagConstraints gbc = new GridBagConstraints();

    public BorderPanel revenueCard;
    public BorderPanel orderCard;
    public BorderPanel bookCard;
    public BorderPanel warningCard;

    public HeaderPanel() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20,20,10,20));

        setPreferredSize(new Dimension(0,HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE,HEIGHT));

        gbc.insets = new Insets(0,0,15,20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        addItem(createTitle(),0,0);
        gbc.weighty = 0.5;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;

        addItem(statusTitle(),3,0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1.;

        revenueCard = statCard("Doanh thu", new Color(22, 163, 74),
                "revenue",0, Double.NaN, "% so với hôm qua",
                new Color(240, 253, 244), new Color(187, 247, 208));

        orderCard = statCard("Số đơn hàng", new Color(37, 99, 235),
                "total_price",0, Double.NaN, "% so với hôm qua",
                new Color(239, 246, 255), new Color(191, 219, 254));

        bookCard = statCard("Số lượng sách đã bán", new Color(147, 51, 234),
                "user",0, Double.NaN, "% so với hôm qua",
                new Color(250, 245, 255), new Color(233, 213, 255));

        warningCard = statCard("Cảnh báo", new Color(234, 88, 12),
                "warning",0, -Double.MAX_VALUE, "Sách trong kho sắp hết",
                new Color(255, 247, 237), new Color(254, 215, 170));

        addItem(revenueCard,0, 1);
        addItem(orderCard,1, 1);
        addItem(bookCard,2, 1);
        addItem(warningCard,3, 1);
    }

    public void updateCard(BorderPanel card, double value, double percent, String subText) {
        for (Component c : card.getComponents()) {
            if (!(c instanceof JLabel label)) continue;
            String name = label.getName() == null ? "" : label.getName();
            switch (name) {
                case "valueLabel" -> label.setText(BasePanel.formatNumber((long) value));
                case "subLabel"   -> {
                    Color color = (Color) card.getClientProperty("titleColor");
                    updateSubLabel(label, percent, subText, color);
                }
            }
        }
        card.revalidate();
        card.repaint();
    }

    public BorderPanel createTitle(){

        BorderPanel titlePanel = new BorderPanel(0,Color.WHITE,0,0,null,0);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel time = BasePanel.createTitle("Tổng quan tuần này","Arial",Font.PLAIN,15,new Color(115, 115, 115));
        titlePanel.add(BasePanel.createTitle("Tổng quan tuần này","Arial",Font.BOLD,20,Color.BLACK));
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(time);
        startClock(time);
        return titlePanel;
    }

    public BorderPanel statusTitle(){
        BorderPanel statusPanel = new BorderPanel(20,new Color(240, 253, 244),0,0,null,0);
        statusPanel.setBorder(new EmptyBorder(2,10,2,10));
        JLabel label = BasePanel.createTitle("● Hoạt động tốt","Arial",Font.PLAIN,15,new Color(21, 128, 61));
        statusPanel.add(label);
        return statusPanel;
    }

    public BorderPanel statCard(String title,Color titleColor,String iconName,double value,double percent,String subText,Color backgroundColor, Color borderColor){
        BorderPanel cardPanel = new BorderPanel(17,backgroundColor,0,0,borderColor,1);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(15,15,10,10));
        cardPanel.putClientProperty("titleColor", titleColor);
        cardPanel.putClientProperty("iconName", iconName);

        ImageIcon icon = new ImageIcon(BasePanel.createImageLogo(getClass(),"homepage/"+iconName,20,20));
        JLabel titleLabel = BasePanel.createTitle(title,"Arial",Font.BOLD,17,titleColor);
        titleLabel.setIcon(icon);
        titleLabel.setIconTextGap(5);

        JLabel valueLabel = BasePanel.createTitle(BasePanel.formatNumber((long) value),"Arial",Font.BOLD,20,Color.BLACK);
        valueLabel.setName("valueLabel");

        Icon iconUp = BasePanel.createIcon(getClass(),"homepage/up",15,15,titleColor);
        Icon iconDown = BasePanel.createIcon(getClass(),"homepage/down",15,15,titleColor);

        JLabel subLabel = BasePanel.createTitle(String.format("%.1f", percent) + subText,"Arial",Font.PLAIN,14,titleColor);
        subLabel.setName("subLabel");

        if(percent==-Double.MAX_VALUE){
            subLabel.setIcon(null);
            subLabel.setText(subText);
        }else {
            if(percent >=0) {
                subLabel.setIcon(iconUp);
            }
            else{
                subLabel.setIcon(iconDown);
            }
        }

        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(valueLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(subLabel);

        return cardPanel;
    }

    public void addItem(Component component, int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        add(component,gbc);
    }

    private void updateSubLabel(JLabel label, double percent, String subText, Color color) {

        label.setForeground(color);
        if (percent == -Double.MAX_VALUE || Double.isNaN(percent)) {
            label.setIcon(null);
            label.setText(subText);
        } else {
            label.setText(String.format("%.1f", percent) + subText);
            Icon up = BasePanel.createIcon(getClass(), "homepage/up",   15, 15, color);
            Icon down = BasePanel.createIcon(getClass(), "homepage/down", 15, 15, color);
            label.setIcon(percent >= 0 ? up : down);
        }
    }

    private void updateTime(JLabel time) {
        LocalDateTime now = LocalDateTime.now();
        time.setText(now.format(formatter));
    }

    public void startClock(JLabel time) {
        updateTime(time);
        Timer timer = new Timer(1000, _ -> updateTime(time));
        timer.start();
    }
}
