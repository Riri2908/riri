package riri.dao;

import riri.model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class BookDAO extends BaseFileDAO {

    public BookDAO() {
        super("books.txt");
    }

    public Map<Integer,Book> findAll() {
        Map<Integer,Book> bookMap = new LinkedHashMap<>();
        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");
                bookMap.put(Integer.parseInt(d[0]),new Book(Integer.parseInt(d[0]), d[1], d[2], d[3],d[4],Double.parseDouble(d[5]), Integer.parseInt(d[6])));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read books", e);
        }

        return bookMap;
    }

    public void saveAll(Map<Integer,Book> books) {
        backup();

        List<String> lines = new ArrayList<>();
        Collection<Book> values = books.values();

        for (Book b : values) {
            lines.add(b.toFileString());
        }

        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save books", e);
        }
    }

    public void add(Book book) {
        try {
            Files.write(file, List.of(book.toFileString()), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add book", e);
        }
    }
}
