package riri.service;

import riri.dao.BookDAO;
import riri.model.Book;

import java.util.*;

public class BookService {

    private final BookDAO bookDAO = new BookDAO();
    private final Map<Integer, Book> books;

    public BookService() {
        books = bookDAO.findAll();
    }

    public Map<Integer, Book> getAll() {
        return books;
    }

    public Book findById(Integer id) {
        return books.get(id);
    }

    public Book findByName(String name) {
        return books.values()
                .stream()
                .filter(b -> b.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void add(Book book) {
        book.setId(this.generateId());
        books.put(book.getId(), book);
        save();
    }

    public void update(Book book) {

        if(!books.containsKey(book.getId())){
            throw new RuntimeException("Book không tồn tại");
        }

        books.put(book.getId(), book);
        save();
    }

    public void delete(Integer id) {
        books.remove(id);
        save();
    }

    public void save(){
        bookDAO.saveAll(books);
    }

    public double totalInventoryValue() {
        return books.values()
                .stream()
                .mapToDouble(b -> b.getPrice() * b.getQuantity())
                .sum();
    }

    public int totalQuantity() {
        return books.values()
                .stream()
                .mapToInt(Book::getQuantity)
                .sum();
    }
    private Integer generateId() {
        return books.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
    }
}