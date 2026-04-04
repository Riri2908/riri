package riri.admin.home.items;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HeaderPanel extends BorderPanel {
    private final int HEIGHT = 250;

    private final Locale locale=Locale.of("vi","VN");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE', 'dd' tháng 'MM', 'yyy", locale);

    public GridBagConstraints gbc = new GridBagConstraints();

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

        double totalPriceCurrentWeek = AppContext.CUSTOMER_SERVICE.totalPriceWeek(LocalDate.now());
        double totalPriceLastWeek = AppContext.CUSTOMER_SERVICE.totalPriceWeek(LocalDate.now().minusDays(1));
        double percentPrice = (totalPriceCurrentWeek /totalPriceLastWeek)*100;

        addItem(statCard(
                "Doanh thu", new Color(22,163,74),
                "revenue",
                totalPriceCurrentWeek,
                percentPrice, "% so với ngày tuần trước",
                new Color(240, 253, 244),
                new Color(187,247,208)),
                0,1
        );

        int totalOrderCurrentWeek = AppContext.CUSTOMER_SERVICE.totalOrdersWeek(LocalDate.now());
        int totalOrderLastWeek = AppContext.CUSTOMER_SERVICE.totalOrdersWeek(LocalDate.now().minusDays(1));
        double percentOrder = ((double) totalOrderCurrentWeek /totalOrderLastWeek)*100;

        addItem(statCard(
                "Số đơn hàng", new Color(37, 99, 235),
                "total_price",
                totalOrderCurrentWeek,
                percentOrder,"% so với ngày tuần trước",
                new Color(239, 246, 255),
                new Color(191, 219, 254)),
                1,1
        );

        int totalBookCurrentWeek = AppContext.INVOICE_SERVICE.totalOrdersWeek(LocalDate.now());
        int totalBookLastWeek = AppContext.INVOICE_SERVICE.totalOrdersWeek(LocalDate.now().minusDays(1));
        double percentBook = ((double) totalBookCurrentWeek /totalBookLastWeek)*100;

        addItem(statCard(
                "Số sách đã được bán", new Color(147, 51, 234),
                "user",
                totalBookCurrentWeek,
                percentBook, "% so với ngày tuần trước",
                new Color(250, 245, 255),
                new Color(233, 213, 255)),
                2,1
        );

        int lowStockBooks = AppContext.BOOK_SERVICE.getLowStockBooks().size();

        addItem(statCard(
                "Cảnh báo", new Color(234, 88, 12),
                "warning",
                lowStockBooks,-Double.MAX_VALUE,"Sách trong kho sắp hết",
                new Color(255, 247, 237),
                new Color(254, 215, 170)),
                3,1
        );
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

        ImageIcon icon = new ImageIcon(BasePanel.createImageLogo(getClass(),"homepage/"+iconName,20,20));
        JLabel titleLabel = BasePanel.createTitle(title,"Arial",Font.BOLD,17,titleColor);
        titleLabel.setIcon(icon);
        titleLabel.setIconTextGap(5);

        JLabel valueLabel = BasePanel.createTitle(BasePanel.formatNumber((long) value),"Arial",Font.BOLD,20,Color.BLACK);
        Icon iconUp = BasePanel.createIcon(getClass(),"homepage/up",15,15,titleColor);
        Icon iconDown = BasePanel.createIcon(getClass(),"homepage/down",15,15,titleColor);
        JLabel subLabel = BasePanel.createTitle(percent+subText,"Arial",Font.PLAIN,14,titleColor);
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

    public void addItem(Component component, int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        add(component,gbc);
    }

    private void updateTime(JLabel time) {
        LocalDateTime now = LocalDateTime.now();
        time.setText(now.format(formatter));
    }

    public void startClock(JLabel time) {
        updateTime(time);
        Timer timer = new Timer(1000, e -> updateTime(time));
        timer.start();
    }
}
