package riri.admin.customers.item;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.page.StatPanel;
import riri.util.AppContext;

import javax.swing.*;
import java.awt.*;

public class StatCardPanel extends JPanel{
    public StatPanel customerStat;
    public StatPanel revenueStat;
    public StatPanel revenueArgStat;

    public StatCardPanel() {
        setOpaque(false);
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        int customerCount = AppContext.CUSTOMER_SERVICE.totalCustomers();
        double total = AppContext.CUSTOMER_SERVICE.totalPrice();
        double totalOrder = AppContext.CUSTOMER_SERVICE.totalOrders();
        double avg = customerCount == 0 ? 0 : total / totalOrder;

        customerStat = new StatPanel("Tổng khách hàng",String.valueOf(customerCount),"khách hàng",new Color(0, 48, 255),"customer/customer");
        revenueStat = new StatPanel("Tổng doanh thu", BasePanel.formatNumber((long) total),"từ khách hàng",new Color(0, 200, 80),"customer/shoppingbag");
        revenueArgStat = new StatPanel("Giá trị đơn TB",BasePanel.formatNumber((long) avg),"mỗi đơn",new Color(172, 70, 253),"customer/shoppingbag");

        add(customerStat);
        add(Box.createHorizontalStrut(15));
        add(revenueStat);
        add(Box.createHorizontalStrut(15));
        add(revenueArgStat);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height));
    }

    public void updateQuantity(){
        int customerCount = AppContext.CUSTOMER_SERVICE.totalCustomers();
        double total = AppContext.CUSTOMER_SERVICE.totalPrice();
        double totalOrder = AppContext.CUSTOMER_SERVICE.totalOrders();

        double avg = customerCount == 0 ? 0 : total / totalOrder;

        this.customerStat.setValue(customerCount);
        this.revenueStat.setValue(total);
        this.revenueArgStat.setValue(avg);
    }
}
