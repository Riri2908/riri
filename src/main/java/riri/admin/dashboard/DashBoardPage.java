package riri.admin.dashboard;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.service.component.Period;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class DashBoardPage extends BorderPanel {

    public RevenueChartPanel revenueChartPanel;
    public WarningPanel warningPanel;

    public BorderPanel mainPanel = new BorderPanel(0,new Color(247, 248, 249),0,0,null,0);
    public DashBoardPage() {
        super(0,new Color(247, 248, 249),0,0,null,0);

        this.revenueChartPanel = new RevenueChartPanel();
        this.warningPanel =new WarningPanel();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
            }
        });

        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(25,25,25,25));

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        mainPanel.add(statusPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(revenueChartPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(warningPanel);

        JScrollPane scrollPane = BasePanel.createScroll(mainPanel);

        add(scrollPane,BorderLayout.CENTER);

    }

    public JPanel statusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        statusPanel.setLayout(new BoxLayout(statusPanel,BoxLayout.X_AXIS));

        int totalBookCurrentMonth = AppContext.INVOICE_SERVICE.totalQuantityBook(LocalDate.now(), Period.MONTH);
        int totalBookLastMonth = AppContext.INVOICE_SERVICE.totalQuantityBook(LocalDate.now().minusMonths(1),Period.MONTH);
        double percentBook = ((double) (totalBookCurrentMonth-totalBookLastMonth) /totalBookLastMonth)*100;

        BorderPanel bookStat = statCard("Tổng số sách đã bán",new Color(43, 126, 253),"homepage/book",
                totalBookCurrentMonth,percentBook,"% so với tháng trước"
                ,Color.WHITE,new Color(198, 218, 251));

        double totalPriceCurrentMonth = AppContext.INVOICE_SERVICE.totalPrice(LocalDate.now(), Period.MONTH);
        double totalPriceLastMonth = AppContext.INVOICE_SERVICE.totalPrice(LocalDate.now().minusMonths(1), Period.MONTH);
        double percentPrice = totalPriceLastMonth == 0 ? Double.NaN : ((totalPriceCurrentMonth - totalPriceLastMonth) / totalPriceLastMonth) * 100;

        BorderPanel revenueStat = statCard("Doanh thu tháng này",new Color(22,163,74),"homepage/revenue",
                totalPriceCurrentMonth,percentPrice, "% so với tháng trước",
                Color.WHITE, new Color(187,247,208));

        int totalOrderCurrentMonth = AppContext.INVOICE_SERVICE.totalOrders(LocalDate.now(), Period.MONTH);
        int totalOrderLastMonth = AppContext.INVOICE_SERVICE.totalOrders(LocalDate.now().minusMonths(1), Period.MONTH);
        double percentOrder = totalOrderLastMonth == 0 ? Double.NaN : (((double)totalOrderCurrentMonth - totalOrderLastMonth) / totalOrderLastMonth) * 100;

        BorderPanel revenueArgStat = statCard("Số hóa đơn",new Color(172, 70, 253),"homepage/invoice",
                totalOrderCurrentMonth,percentOrder,"% so với tháng trước",
                Color.WHITE,new Color(244, 210, 255));

        int returningCustomersCurrentMonth = AppContext.INVOICE_SERVICE.totalOrders(LocalDate.now(), Period.MONTH);
        int returningCustomersLastMonth = AppContext.INVOICE_SERVICE.totalOrders(LocalDate.now().minusMonths(1), Period.MONTH);
        double percentreturningCustomers = totalOrderLastMonth == 0 ? Double.NaN : (((double)returningCustomersCurrentMonth - returningCustomersLastMonth) / returningCustomersLastMonth) * 100;

        BorderPanel ArgStat = statCard("Số lượng khách quay lại",new Color(253, 104,0),"homepage/users",
                returningCustomersCurrentMonth,percentreturningCustomers," so với tháng trước",
                Color.WHITE,new Color(255, 217, 194));

        statusPanel.add(bookStat);
        statusPanel.add(Box.createHorizontalStrut(15));
        statusPanel.add(revenueStat);
        statusPanel.add(Box.createHorizontalStrut(15));
        statusPanel.add(revenueArgStat);
        statusPanel.add(Box.createHorizontalStrut(15));
        statusPanel.add(ArgStat);

        statusPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusPanel.getPreferredSize().height));
        return statusPanel;
    }

    public BorderPanel statCard(String title,Color titleColor,String iconName,double value,double percent,String subText,Color backgroundColor, Color borderColor){
        BorderPanel cardPanel = new BorderPanel(17,backgroundColor,6,20,borderColor,1);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(15,15,10,10));

        Icon icon = BasePanel.createIcon(getClass(),iconName, 24,24,titleColor);
        JLabel titleLabel = BasePanel.createTitle(title,"Arial",Font.BOLD,18,titleColor);
        titleLabel.setIcon(icon);
        titleLabel.setIconTextGap(5);

        JLabel valueLabel = BasePanel.createTitle(BasePanel.formatNumber((long) value),"Arial",Font.BOLD,20,Color.BLACK);
        valueLabel.setBorder(new EmptyBorder(0,20,0,0));
        Icon iconUp = BasePanel.createIcon(getClass(),"homepage/up",15,15,titleColor);
        Icon iconDown = BasePanel.createIcon(getClass(),"homepage/down",15,15,titleColor);
        JLabel subLabel = BasePanel.createTitle(String.format("%.1f", percent) + subText,"Arial",Font.PLAIN,14,titleColor);
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
}
