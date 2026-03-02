package riri.dao;

import riri.model.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends BaseFileDAO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TransactionDAO() {
        super("transactions.txt");
    }

    public List<Transaction> findAll() {
        List<Transaction> list = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");

                list.add(new Transaction(d[0], d[1], d[2], Integer.parseInt(d[3]), d[4], LocalDate.parse(d[5],formatter), d[6]));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read transactions", e);
        }

        return list;
    }

    public void saveAll(List<Transaction> transactions) {

        List<String> lines = new ArrayList<>();

        for (Transaction t : transactions) {
            lines.add(t.toFileString());
        }

        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save transactions", e);
        }
    }
}