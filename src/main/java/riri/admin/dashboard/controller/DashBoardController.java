package riri.admin.dashboard.controller;

import org.jfree.data.category.DefaultCategoryDataset;
import riri.admin.dashboard.DashBoardPage;
import riri.admin.dashboard.view.DashBoardStatPanel;
import riri.admin.dashboard.view.RevenueChartPanel;
import riri.admin.dashboard.view.WarningPanel;
import riri.model.Book;
import riri.model.Shelf;
import riri.service.component.Period;
import riri.util.AppContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashBoardController {
    private static final int LOW_STOCK_THRESHOLD = 15;

    private final DashBoardPage view;
    private final DashBoardStatPanel statPanel;
    private final RevenueChartPanel revenueChartPanel;
    private final WarningPanel warningPanel;

    public DashBoardController(DashBoardPage view, DashBoardStatPanel statPanel, RevenueChartPanel revenueChartPanel, WarningPanel warningPanel) {
        this.view = view;
        this.statPanel = statPanel;
        this.revenueChartPanel = revenueChartPanel;
        this.warningPanel = warningPanel;
        loadData();
    }

    public void loadData() {

        //Thuộc StatPanel
        loadBookStat();
        loadRevenueStat();
        loadOrderStat();
        loadCustomerStat();

        //Thuộc Chart
        revenueChartPanel.setRevenueDataset(buildRevenueDataset());
        revenueChartPanel.setQuantityDataset(buildQuantityDataset());

        //Thuộc Warning
        loadWarningData();
    }

    private void loadBookStat() {
        LocalDate now = LocalDate.now();
        int current = AppContext.INVOICE_SERVICE.totalQuantityBook(now, Period.MONTH);
        int last = AppContext.INVOICE_SERVICE.totalQuantityBook(now.minusMonths(1), Period.MONTH);
        double pct = calcPercent(current, last);

        statPanel.updateStatCard(statPanel.bookStatCard, current, pct, "% so với tháng trước");
    }
    private void loadRevenueStat() {
        LocalDate now = LocalDate.now();
        double current = AppContext.INVOICE_SERVICE.totalPrice(now, Period.MONTH);
        double last = AppContext.INVOICE_SERVICE.totalPrice(now.minusMonths(1), Period.MONTH);
        double pct = calcPercent(current, last);

        view.statPanel.updateStatCard(view.statPanel.revenueStatCard, current, pct, "% so với tháng trước");
    }

    private void loadOrderStat() {
        LocalDate now = LocalDate.now();
        int current = AppContext.INVOICE_SERVICE.totalOrders(now, Period.MONTH);
        int last = AppContext.INVOICE_SERVICE.totalOrders(now.minusMonths(1), Period.MONTH);
        double pct = calcPercent(current, last);

        view.statPanel.updateStatCard(view.statPanel.orderStatCard, current, pct, "% so với tháng trước");
    }

    private void loadCustomerStat() {
        LocalDate now = LocalDate.now();
        int current = AppContext.INVOICE_SERVICE.totalOrders(now, Period.MONTH);
        int last = AppContext.INVOICE_SERVICE.totalOrders(now.minusMonths(1), Period.MONTH);
        double pct = calcPercent(current, last);

        view.statPanel.updateStatCard(view.statPanel.customerStatCard, current, pct, "% so với tháng trước");
    }

    private double calcPercent(double current, double last) {
        if (last == 0) return Double.NaN;
        return ((current - last) / last) * 100;
    }

    private DefaultCategoryDataset buildRevenueDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        LocalDate now = LocalDate.now();

        for (int i = 0; i < 6; i++) {
            LocalDate date = now.minusMonths(5 - i);
            double revenue = AppContext.INVOICE_SERVICE.totalPrice(date, Period.MONTH);
            dataset.addValue(revenue, "Doanh thu", "Tháng " + date.getMonthValue());
        }
        return dataset;
    }

    private DefaultCategoryDataset buildQuantityDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        AppContext.BOOK_SERVICE.totalQuantityByArea().forEach((areaId, totalQty) -> {
            String areaName = AppContext.AREA_SERVICE.findById(areaId).getName();
            dataset.addValue(totalQty, "Sách", areaName);
        });
        return dataset;
    }

    public void loadWarningData() {
        List<Book> books = new ArrayList<>(AppContext.BOOK_SERVICE.getAll().values());
        Collections.reverse(books);

        warningPanel.clearRows();

        for (Book book : books) {
            if (book.getQuantity() >= LOW_STOCK_THRESHOLD) continue;

            Shelf shelf = AppContext.SHELF_SERVICE.findById(book.getIdShelf());
            if (shelf == null) continue;

            warningPanel.addRow(new Object[]{
                    book.getId(),
                    book.getName(),
                    book.getAuthor(),
                    book.getQuantity(),
                    shelf,
            });
        }
    }

}