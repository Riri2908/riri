package riri.components.page;

import riri.admin.customers.CustomerPage;
import riri.admin.dashboard.DashBoardPage;
import riri.admin.home.HomePage;
import riri.admin.invoice.InvoicePage;
import riri.admin.management.ManagementPage;
import riri.admin.store.BookPage;
import riri.components.sidebar.SideBarItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ContentPanel extends JPanel {
    private final Map<String, SideBarItem> menuMap = new HashMap<>();
    private SideBarItem activeItem;

    private final CardLayout cardLayout=new CardLayout();
    private final JPanel contentPanel;
    private final TopBar topBar=new TopBar("Trang chủ");

    public ContentPanel() {
        setOpaque(true);
        setPreferredSize(new Dimension(600,600));
        setLayout(new BorderLayout());
        setBackground(new Color(247, 248, 249));

        this.contentPanel=new JPanel();
        contentPanel.setLayout(cardLayout);

        HomePage homePage =new HomePage(this);
        DashBoardPage dashBoardPage = new DashBoardPage();
        BookPage bookPage = new BookPage();
        ManagementPage managementPage = new ManagementPage();
        InvoicePage invoicePage = new InvoicePage();
        CustomerPage customerPage = new CustomerPage(invoicePage);

        contentPanel.add(homePage,"Trang chủ");
        contentPanel.add(dashBoardPage, "Thống kê");
        contentPanel.add(bookPage, "Quản lý sách");
        contentPanel.add(managementPage, "Quản lý nhập kho");
        contentPanel.add(invoicePage, "Hóa đơn bán hàng");
        contentPanel.add(customerPage, "Khách hàng");
        add(topBar,BorderLayout.NORTH);
        add(contentPanel,BorderLayout.CENTER);
    }

    public void registerMenu(String page, SideBarItem item) {
        menuMap.put(page, item);
    }

    public void showPage(String page) {

        topBar.setTitle(page);
        cardLayout.show(contentPanel, page);

        if (activeItem != null) {
            activeItem.setActive(false);
        }

        activeItem = menuMap.get(page);

        if (activeItem != null) {
            activeItem.setActive(true);
        }
    }

}
