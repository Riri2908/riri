package riri.components.page;

import riri.admin.customers.CustomerPage;
import riri.admin.dashboard.DashBoardPage;
import riri.admin.invoice.InvoicePage;
import riri.admin.management.ManagementPage;
import riri.admin.store.BookPage;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {


    private final CardLayout cardLayout=new CardLayout();
    private final JPanel contentPanel;
    private final TopBar topBar=new TopBar("DashBoard");

    public ContentPanel() {
        setOpaque(true);
        setPreferredSize(new Dimension(600,600));
        setLayout(new BorderLayout());
        setBackground(new Color(247, 248, 249));

        this.contentPanel=new JPanel();
        contentPanel.setLayout(cardLayout);

        DashBoardPage dashBoardPage = new DashBoardPage();
        BookPage bookPage = new BookPage();
        ManagementPage managementPage = new ManagementPage();
        InvoicePage invoicePage = new InvoicePage();
        CustomerPage customerPage = new CustomerPage(invoicePage);

        contentPanel.add(dashBoardPage, "DashBoard");
        contentPanel.add(bookPage, "Quản lý sách");
        contentPanel.add(managementPage, "Quản lý tồn kho");
        contentPanel.add(invoicePage, "Hóa đơn");
        contentPanel.add(customerPage, "Khách hàng");
        add(topBar,BorderLayout.NORTH);
        add(contentPanel,BorderLayout.CENTER);
    }
    public void showPage(String page) {
        topBar.setTitle(page);
        cardLayout.show(contentPanel, page);
    }

}
