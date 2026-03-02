package riri.service;

import riri.dao.TransactionDAO;
import riri.model.Book;
import riri.model.Transaction;
import riri.util.AppContext;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final List<Transaction> transactions;

    public TransactionService() {
        transactions = new ArrayList<>(transactionDAO.findAll());
    }

    public List<Transaction> getAll() {
        return transactions;
    }

    public void add(Transaction transaction) {

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

        transactions.add(transaction);

        transactionDAO.saveAll(transactions);
        AppContext.BOOK_SERVICE.update(book);
    }
    public int getTotalImportQuantity() {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Nhập"))
                .mapToInt(Transaction::getQuantity)
                .sum();
    }
    public int getTotalExportQuantity() {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Xuất"))
                .mapToInt(Transaction::getQuantity)
                .sum();
    }
}