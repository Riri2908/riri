package riri.service;

import riri.dao.InvoiceDAO;
import riri.dao.InvoiceDetailDAO;
import riri.model.Book;
import riri.model.Invoice;
import riri.model.InvoiceDetail;
import riri.util.AppContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvoiceService {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();

    private final List<Invoice> invoices;
    private final List<InvoiceDetail> details;

    public InvoiceService() {
        invoices = new ArrayList<>(invoiceDAO.findAll());
        details = new ArrayList<>(detailDAO.findAll());
    }

    public List<Invoice> getAllInvoices() {
        return invoices;
    }

    public List<InvoiceDetail> getDetailsByInvoiceId(String invoiceId) {
        return details.stream()
                .filter(d -> d.getInvoiceId().equalsIgnoreCase(invoiceId))
                .toList();
    }

    public void createInvoice(String employeeId, List<InvoiceDetail> invoiceDetails) {

        if (AppContext.EMPLOYEE_SERVICE.findById(employeeId) == null) {
            throw new RuntimeException("Employee không tồn tại");
        }

        String invoiceId = generateInvoiceId();

        double totalAmount = 0;

        for (InvoiceDetail d : invoiceDetails) {

            Book book = AppContext.BOOK_SERVICE.findById(d.getBookId());

            if (book == null) {
                throw new RuntimeException("Book không tồn tại");
            }

            if (book.getQuantity() < d.getQuantity()) {
                throw new RuntimeException("Không đủ số lượng sách trong kho");
            }

            book.setQuantity(book.getQuantity() - d.getQuantity());

            totalAmount += d.getQuantity() * d.getPrice();

            d.setInvoiceId(invoiceId);

            details.add(d);
        }

        Invoice invoice = new Invoice(invoiceId, employeeId, LocalDate.now(), totalAmount);

        invoices.add(invoice);

        invoiceDAO.saveAll(invoices);
        detailDAO.saveAll(details);

        AppContext.BOOK_SERVICE.updateAll();
    }

    private String generateInvoiceId() {
        return "INV" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}