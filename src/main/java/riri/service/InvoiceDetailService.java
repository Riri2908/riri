package riri.service;

import riri.dao.InvoiceDetailDAO;
import riri.model.Book;
import riri.model.InvoiceDetail;
import riri.util.AppContext;

import java.util.*;

public class InvoiceDetailService {

    private final InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();

    private final Map<Integer, InvoiceDetail> details;

    public InvoiceDetailService() {
        details = detailDAO.findAll();
    }

    public Map<Integer, InvoiceDetail> getAll() {
        return details;
    }

    public InvoiceDetail findById(Integer id) {
        return details.get(id);
    }

    public List<InvoiceDetail> findByInvoiceId(Integer invoiceId) {

        List<InvoiceDetail> result = new ArrayList<>();

        for (InvoiceDetail d : details.values()) {
            if (Objects.equals(d.getInvoiceId(), invoiceId)) {
                result.add(d);
            }
        }

        return result;
    }

    public void addDetail(InvoiceDetail detail) {

        Book book = AppContext.BOOK_SERVICE.findById(detail.getBookId());

        if (book == null) {
            throw new RuntimeException("Book không tồn tại");
        }

        if (book.getQuantity() < detail.getQuantity()) {
            throw new RuntimeException("Không đủ số lượng trong kho");
        }

        book.setQuantity(book.getQuantity() - detail.getQuantity());

        detail.setId(generateId());

        details.put(detail.getId(), detail);

        save();
        AppContext.BOOK_SERVICE.save();
    }

    public void updateQuantity(Integer detailId, int newQuantity) {

        InvoiceDetail detail = details.get(detailId);

        if (detail == null) {
            throw new RuntimeException("InvoiceDetail không tồn tại");
        }

        Book book = AppContext.BOOK_SERVICE.findById(detail.getBookId());

        int diff = newQuantity - detail.getQuantity();

        if (book.getQuantity() < diff) {
            throw new RuntimeException("Không đủ sách trong kho");
        }

        book.setQuantity(book.getQuantity() - diff);

        detail.setQuantity(newQuantity);

        save();
        AppContext.BOOK_SERVICE.save();
    }

    public void remove(Integer detailId) {

        InvoiceDetail detail = details.remove(detailId);

        if (detail == null) return;

        // trả lại kho
        Book book = AppContext.BOOK_SERVICE.findById(detail.getBookId());

        if (book != null) {
            book.setQuantity(
                    book.getQuantity() + detail.getQuantity()
            );
        }

        save();
        AppContext.BOOK_SERVICE.save();
    }

    public void save() {
        detailDAO.saveAll(details);
    }
    private Integer generateId() {
        return details.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }
}