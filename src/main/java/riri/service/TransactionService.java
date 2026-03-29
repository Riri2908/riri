package riri.service;

import riri.dao.TransactionDAO;
import riri.model.Book;
import riri.model.Employee;
import riri.model.Transaction;
import riri.util.AppContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TransactionService {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
            throw new RuntimeException("Sách không tồn tại");
        }

        Employee employee = AppContext.EMPLOYEE_SERVICE.findById(transaction.getEmployeeId());
        if (employee == null) {
            throw new RuntimeException("Khách hàng không tồn tại");
        }

        if (transaction.getType().equalsIgnoreCase("Nhập")) {
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
        return transactions.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }
}