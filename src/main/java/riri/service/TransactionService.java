package riri.service;

import riri.dao.TransactionDAO;
import riri.model.Book;
import riri.model.Transaction;
import riri.util.AppContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final Map<Integer,Transaction> transactions;

    public TransactionService() {
        transactions = transactionDAO.findAll();
    }

    public Map<Integer,Transaction> getAll() {
        return transactions;
    }

    public void add(Transaction transaction) {
        transaction.setId(generateId());

        Book book = AppContext.BOOK_SERVICE.findById(transaction.getBookId());
        if (book == null) {
            throw new RuntimeException("Book không tồn tại");
        }

        if (AppContext.EMPLOYEE_SERVICE.findById(transaction.getEmployeeId()) == null) {
            throw new RuntimeException("Khách hàng không tồn tại");
        }

        if (transaction.getType().equalsIgnoreCase("IMPORT")) {
            book.setQuantity(book.getQuantity() + transaction.getQuantity());
        } else {
            if (book.getQuantity() < transaction.getQuantity()) {
                throw new RuntimeException("Không đủ số lượng trong kho");
            }
            book.setQuantity(book.getQuantity() - transaction.getQuantity());
        }

        transactions.put(transaction.getId(),transaction);

        transactionDAO.saveAll(transactions);
        AppContext.BOOK_SERVICE.update(book);
    }
    public int getTotalImportQuantity() {
        Collection<Transaction> transaction =  transactions.values();
        return transaction.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Nhập"))
                .mapToInt(Transaction::getQuantity)
                .sum();
    }
    public int getTotalExportQuantity() {
        Collection<Transaction> transaction =  transactions.values();
        return transaction.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Xuất"))
                .mapToInt(Transaction::getQuantity)
                .sum();
    }

    private Integer generateId() {
        return (int) (Math.random() * 9000000) + 1000000;
    }
}