package riri.service;

import riri.dao.BookDAO;
import riri.model.Book;

import java.util.List;

public class BookStatistics {
    public static double totalInventoryValue(List<Book> books) {
        return books.stream()
                .mapToDouble(b->b.getPrice()*b.getQuantity())
                .sum();
    }

    public static int totalQuantity(List<Book> books) {
        return books.stream()
                .mapToInt(Book::getQuantity)
                .sum();
    }
}
