package riri.service;

import riri.dao.InvoiceDAO;
import riri.dao.InvoiceDetailDAO;
import riri.model.Book;
import riri.model.Customer;
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

        mapDetailsToInvoices();
    }

    public Map<Integer,Invoice> getAll() {
        return invoices;
    }

    public InvoiceDetail getDetailsByInvoiceId(Integer invoiceId) {
        return details.get(invoiceId);
    }

    public Invoice addInvoice(Integer customerId, Integer employeeId, Map<Integer,InvoiceDetail> invoiceDetails) {
        Collection<InvoiceDetail> detailValues = invoiceDetails.values();

        if (AppContext.CUSTOMER_SERVICE.findById(employeeId) == null) {
            throw new RuntimeException("Sách không tồn tại");
        }

        if (AppContext.EMPLOYEE_SERVICE.findById(employeeId) == null) {
            throw new RuntimeException("Employee không tồn tại");
        }

        int invoiceId = generateId();

        double totalAmount = 0;

        Invoice invoice = new Invoice(invoiceId, customerId ,employeeId, LocalDate.now(), totalAmount);

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

            d.setId(generateDetailId());
            d.setInvoiceId(invoiceId);

            details.put(generateDetailId(),d);
            invoice.addDetail(d);
        }

        invoice.setTotalAmount(totalAmount);
        invoices.put(invoiceId,invoice);

        invoiceDAO.saveAll(invoices);
        detailDAO.saveAll(details);

        AppContext.BOOK_SERVICE.save();

        return invoice;
    }

    private void mapDetailsToInvoices() {

        for (InvoiceDetail d : details.values()) {
            Invoice invoice = invoices.get(d.getInvoiceId());

            if (invoice != null) {
                invoice.addDetail(d);
            }
        }
    }

    private Integer generateId() {
        return invoices.size()+1;
    }

    private Integer generateDetailId() {
        return details.size() + 1;
    }
}