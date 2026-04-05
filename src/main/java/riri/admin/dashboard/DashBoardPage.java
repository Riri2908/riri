package riri.admin.dashboard;

import riri.admin.customers.controller.CustomerController;
import riri.admin.customers.item.CustomerForm;
import riri.admin.customers.item.TableCustomer;
import riri.admin.customers.item.TitleCustomerPanel;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.page.StatPanel;
import riri.components.sidebar.SideBar;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class DashBoardPage extends BorderPanel {
    public BorderPanel mainPanel = new BorderPanel(0,new Color(247, 248, 249),0,0,null,0);
    public DashBoardPage() {
        super(0,new Color(247, 248, 249),0,0,null,0);

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

        JScrollPane scrollPane = BasePanel.createScroll(mainPanel);

        add(scrollPane,BorderLayout.CENTER);

    }

    public JPanel statusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        statusPanel.setLayout(new BoxLayout(statusPanel,BoxLayout.X_AXIS));

        int totalBookCurrentMonth = AppContext.TRANSACTION_SERVICE.totalQuantityWeek(LocalDate.now(),"Nhập");
        int totalBookLastMonth = AppContext.TRANSACTION_SERVICE.totalQuantityWeek(LocalDate.now().minusMonths(1),"Nhập");
        double percentBook = ((double) (totalBookCurrentMonth-totalBookLastMonth) /totalBookLastMonth)*100;

        BorderPanel bookStat = statCard(
                "Tổng số sách đã nhập",new Color(54, 91, 236),"homepage/book",
                totalBookCurrentMonth,percentBook,"% so với tháng trước"
                ,Color.WHITE,new Color(221, 232, 251));

        double totalPriceCurrentMonth = AppContext.CUSTOMER_SERVICE.totalPriceWeek(LocalDate.now());
        double totalPriceLastMonth = AppContext.CUSTOMER_SERVICE.totalPriceWeek(LocalDate.now().minusDays(1));
        double percentPrice = totalPriceLastMonth == 0 ? Double.NaN : ((totalPriceCurrentMonth - totalPriceLastMonth) / totalPriceLastMonth) * 100;

        BorderPanel revenueStat = statCard("Doanh thu tháng này",new Color(0,0,0),"homepage/book",
                totalPriceCurrentMonth,percentPrice, "% so với tháng trước",
                Color.WHITE,Color.BLACK);
        BorderPanel revenueArgStat = statCard("Tổng số sách",new Color(0,0,0),"homepage/book",0,0," so với tháng trước",Color.WHITE,Color.BLACK);
        BorderPanel ArgStat = statCard("Tổng số sách",new Color(0,0,0),"homepage/book",0,0," so với tháng trước",Color.WHITE,Color.BLACK);

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
        JLabel titleLabel = BasePanel.createTitle(title,"Arial",Font.BOLD,17,titleColor);
        titleLabel.setIcon(icon);
        titleLabel.setIconTextGap(5);

        JLabel valueLabel = BasePanel.createTitle(BasePanel.formatNumber((long) value),"Arial",Font.BOLD,20,Color.BLACK);
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
