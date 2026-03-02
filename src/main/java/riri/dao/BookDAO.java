package riri.dao;

import riri.model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends BaseFileDAO {

    public BookDAO() {
        super("books.txt");
    }

    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();


        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");

                list.add(new Book(d[0], d[1], d[2], d[3],Double.parseDouble(d[4]), Integer.parseInt(d[5])));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read books", e);
        }

        return list;
    }

    public void saveAll(List<Book> books) {
        backup();

        List<String> lines = new ArrayList<>();

        for (Book b : books) {
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
