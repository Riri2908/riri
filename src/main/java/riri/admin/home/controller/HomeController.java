package riri.admin.home.controller;

import riri.admin.home.HomePage;
import riri.admin.home.model.BookSaleStat;
import riri.admin.home.view.FeaturePanel;
import riri.admin.home.view.HeaderPanel;
import riri.admin.home.view.TopPanel;
import riri.components.page.BasePanel;
import riri.model.Book;
import riri.model.Customer;
import riri.model.Invoice;
import riri.model.InvoiceDetail;
import riri.service.component.Period;
import riri.util.AppContext;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class HomeController {
    private static final int TOP_LIMIT = 5;

    public HomePage  homePage;
    public FeaturePanel featurePanel;
    public HeaderPanel headerPanel;
    public TopPanel topPanel;

    public HomeController(HomePage homePage, HeaderPanel headerPanel,FeaturePanel featurePanel, TopPanel topPanel) {
        this.homePage = homePage;
        this.featurePanel = featurePanel;
        this.headerPanel = headerPanel;
        this.topPanel = topPanel;

        loadData();
    }

    public void loadData() {
        //Thuôc Header
        loadRevenue();
        loadOrders();
        loadBooks();
        loadWarning();

        //Thuộc Feature
        loadFeatures();

        //Thuộc Top
        loadTopBooks();
        loadTopCustomers();
    }

    private void loadRevenue() {
        LocalDate now = LocalDate.now();
        double current = AppContext.INVOICE_SERVICE.totalPrice(now, Period.DAY);
        double last = AppContext.INVOICE_SERVICE.totalPrice(now.minusDays(1), Period.DAY);
        double pct = calcPercent(current, last);
        headerPanel.updateCard(headerPanel.revenueCard, current, pct, "% so với hôm qua");
    }

    private void loadOrders() {
        LocalDate now = LocalDate.now();
        int current = AppContext.INVOICE_SERVICE.totalOrders(now, Period.DAY);
        int last = AppContext.INVOICE_SERVICE.totalOrders(now.minusDays(1), Period.DAY);
        double pct = calcPercent(current, last);
        headerPanel.updateCard(headerPanel.orderCard, current, pct, "% so với hôm qua");
    }

    private void loadBooks() {
        LocalDate now = LocalDate.now();
        int current = AppContext.INVOICE_SERVICE.totalQuantityBook(now, Period.DAY);
        int last = AppContext.INVOICE_SERVICE.totalQuantityBook(now.minusDays(1), Period.DAY);
        double pct = calcPercent(current, last);
        headerPanel.updateCard(headerPanel.bookCard, current, pct, "% so với hôm qua");
    }

    private void loadWarning() {
        int lowStock = AppContext.BOOK_SERVICE.getLowStockBooks().size();
        headerPanel.updateCard(headerPanel.warningCard, lowStock, -Double.MAX_VALUE, "Sách trong kho sắp hết");
    }

    private double calcPercent(double current, double last) {
        if (last == 0) return Double.NaN;
        return ((current - last) / last) * 100;
    }

    private void loadFeatures() {
        addFeature("homepage/dashboard", new Color(0, 40, 255),   new Color(232, 241, 255),
                "Thống kê",          "Xem tổng quan doanh thu, sách bán chạy và các chỉ số khác",
                "Biểu đồ và thống kê", new Color(37, 99, 235),  new Color(222, 238, 255), 0, 0);

        addFeature("homepage/book",      new Color(113, 19, 213), new Color(242, 223, 255),
                "Quản lý sách",      "Thêm, xóa, sửa, quản lý khu vực kho và cảnh báo tồn kho",
                "Theo dõi tồn kho",  new Color(113, 19, 213),   new Color(242, 223, 255), 1, 0);

        addFeature("homepage/package",   new Color(23, 172, 53),  new Color(224, 253, 224),
                "Quản lý nhập kho",  "Quản lý nhập hàng, theo dõi lịch sử nhập xuất hàng",
                "Nhập hàng",         new Color(23, 172, 53),    new Color(224, 253, 224), 2, 0);

        addFeature("homepage/shopping",  new Color(255, 114, 9),  new Color(253, 231, 224),
                "Hóa đơn bán hàng",  "Tạo hóa đơn, áp dụng chiết khấu theo từng loại khách hàng và tự động xuất hàng",
                "Thanh toán nhanh",  new Color(255, 114, 9),    new Color(253, 231, 224), 0, 1);

        addFeature("homepage/users",     new Color(255, 0, 165),  new Color(255, 225, 243),
                "Khách hàng",        "Quản lý thông tin và lịch sử mua hàng của khách",
                "Biểu đồ và thống kê", new Color(255, 0, 165),  new Color(255, 225, 243), 1, 1);
    }

    private void addFeature(String iconName, Color iconColor, Color iconBg,
                            String title, String subText,
                            String tag, Color tagColor, Color tagBg,
                            int x, int y) {
        featurePanel.addFeatureItem(
                BasePanel.createIcon(featurePanel.getClass(), iconName, 25, 25, iconColor),
                iconBg, title, subText, tag, tagColor, tagBg, x, y
        );
    }

    private void loadTopCustomers() {
        Map<Integer, Customer> data = AppContext.CUSTOMER_SERVICE.getAll();
        List<Customer> customers = new ArrayList<>(data.values());
        customers.sort(Comparator.comparing(Customer::getTotalPrice).reversed());

        if(customers.isEmpty()){
            return;
        }
        int i = 0;
        for (Customer c : customers) {
            topPanel.addCustomerRow(c);

            if (++i == TOP_LIMIT) break;
        }
    }

    private void loadTopBooks() {
        topPanel.clearBookRows();
        int count = 0;
        for (BookSaleStat stat : buildBookSaleStats().values()) {
            topPanel.addBookRow(stat);
            if (++count == TOP_LIMIT) break;
        }
    }

    private Map<Integer, BookSaleStat> buildBookSaleStats() {
        Map<Integer, Invoice> invoices = AppContext.INVOICE_SERVICE.getAll();
        Map<Integer, Book> books = AppContext.BOOK_SERVICE.getAll();
        Map<Integer, BookSaleStat> result = new HashMap<>();

        for (Invoice invoice : invoices.values()) {
            for (InvoiceDetail detail : invoice.getDetails()) {
                int bookId = detail.getBookId();
                Book book  = books.get(bookId);
                if (book == null) continue;

                result.compute(bookId, (_, stat) ->
                        stat == null ? new BookSaleStat(book, detail.getQuantity())
                                : stat.add(detail.getQuantity())
                );
            }
        }

        return result.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue().getQuantity(), a.getValue().getQuantity()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, _) -> e1, LinkedHashMap::new));
    }

}
