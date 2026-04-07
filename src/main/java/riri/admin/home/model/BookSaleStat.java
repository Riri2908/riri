package riri.admin.home.model;

import riri.model.Book;


public class BookSaleStat {
    private final Book book;
    private int quantity;

    public BookSaleStat(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() { return book; }
    public int getQuantity() { return quantity; }

    public BookSaleStat add(int qty) {
        this.quantity += qty;
        return this;
    }
}