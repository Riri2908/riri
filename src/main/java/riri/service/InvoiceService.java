package riri.service;

import riri.dao.InvoiceDAO;
import riri.dao.InvoiceDetailDAO;
import riri.model.Book;
import riri.model.Invoice;
import riri.model.InvoiceDetail;
import riri.util.AppContext;

import java.time.LocalDate;
import java.util.*;

public class InvoiceService {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();

    private final Map<Integer,Invoice> invoices;
    private final Map<Integer,InvoiceDetail> details;

    public InvoiceService() {
        invoices = invoiceDAO.findAll();
        details = detailDAO.findAll();
    }

    public Map<Integer,Invoice> getAllInvoices() {
        return invoices;
    }

    public InvoiceDetail getDetailsByInvoiceId(Integer invoiceId) {
        return details.get(invoiceId);
    }

    public void createInvoice(Integer employeeId, Map<Integer,InvoiceDetail> invoiceDetails) {
        Collection<InvoiceDetail> detailValues = invoiceDetails.values();

        if (AppContext.EMPLOYEE_SERVICE.findById(employeeId) == null) {
            throw new RuntimeException("Employee không tồn tại");
        }

        Integer invoiceId = generateId();

        double totalAmount = 0;

        for (InvoiceDetail d : detailValues) {

            Book book = AppContext.BOOK_SERVICE.findById(d.getBookId());

            if (book == null) {
                throw new RuntimeException("Book không tồn tại");
            }

            if (book.getQuantity() < d.getQuantity()) {
                throw new RuntimeException("Không đủ số lượng sách trong kho");
            }

            book.setQuantity(book.getQuantity() - d.getQuantity());

            totalAmount += d.getQuantity() * d.getPrice();

            d.setId(invoiceId);

            details.put(invoiceId,d);
        }

        Invoice invoice = new Invoice(invoiceId, employeeId, LocalDate.now(), totalAmount);

        invoices.put(invoiceId,invoice);

        invoiceDAO.saveAll(invoices);
        detailDAO.saveAll(invoiceDetails);

        AppContext.BOOK_SERVICE.save();
    }

    private Integer generateId() {
        return (int) (Math.random() * 9000000) + 1000000;
    }
}