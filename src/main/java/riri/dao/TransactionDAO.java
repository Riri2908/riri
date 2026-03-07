package riri.dao;

import riri.model.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TransactionDAO extends BaseFileDAO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TransactionDAO() {
        super("transactions.txt");
    }

        public Map<Integer,Transaction> findAll() {
            Map<Integer,Transaction> transactionMap = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");

                transactionMap.put(Integer.parseInt(d[0]), new Transaction(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]), Integer.parseInt(d[3]), d[4], LocalDate.parse(d[5],formatter), d[6]));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read transactions", e);
        }

        return transactionMap;
    }

    public void saveAll(Map<Integer,Transaction> transactionMap) {

        List<String> lines = new ArrayList<>();
        Collection<Transaction> transactions = transactionMap.values();

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