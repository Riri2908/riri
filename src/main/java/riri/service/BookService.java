package riri.service;

import riri.dao.BookDAO;
import riri.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private final BookDAO bookDAO = new BookDAO();
    private final List<Book> books;

    public BookService() {
        books = new ArrayList<>(bookDAO.findAll());
    }

    public List<Book> getAll() {
        return books;
    }

    public Book findById(String id) {
        return books.stream()
                .filter(b -> b.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public void add(Book book) {
        books.add(book);
        bookDAO.saveAll(books);
    }

    public void update(Book book) {

        boolean found = false;

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equalsIgnoreCase(book.getId())) {
                books.set(i, book);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("Book không tồn tại");
        }

        bookDAO.saveAll(books);
    }

    public void updateAll() {
        bookDAO.saveAll(books);
    }

    public void delete(String id) {
        books.removeIf(b -> b.getId().equalsIgnoreCase(id));
        bookDAO.saveAll(books);
    }
    public double totalInventoryValue() {
        return books.stream()
                .mapToDouble(b->b.getPrice()*b.getQuantity())
                .sum();
    }

    public int totalQuantity() {
        return books.stream()
                .mapToInt(Book::getQuantity)
                .sum();
    }
}