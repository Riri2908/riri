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

    public HomePage homePage;
    public DashBoardPage dashBoardPage;
    public BookPage bookPage;
    public ManagementPage managementPage;
    public InvoicePage invoicePage;
    public CustomerPage customerPage;

    private final CardLayout cardLayout=new CardLayout();
    private final JPanel contentPanel;
    private final TopBar topBar=new TopBar("Trang chủ");

    public ContentPanel() {
        setOpaque(true);
        setPreferredSize(new Dimension(600,600));
        setLayout(new BorderLayout());
        setBackground(new Color(247, 248, 249));
        this.homePage = new HomePage(this);
        this.dashBoardPage = new DashBoardPage();
        this.bookPage = new BookPage();
        this.managementPage = new ManagementPage();
        this.invoicePage = new InvoicePage(this);
        this.customerPage =new CustomerPage(invoicePage);

        this.contentPanel=new JPanel();
        contentPanel.setLayout(cardLayout);

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
