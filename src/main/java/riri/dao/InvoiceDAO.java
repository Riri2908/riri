package riri.dao;

import riri.model.Invoice;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO extends BaseFileDAO {

    public InvoiceDAO() {
        super("invoices.txt");
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<Invoice> findAll() {
        List<Invoice> list = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");

                list.add(new Invoice(d[0], d[1], LocalDate.parse(d[2],formatter), Double.parseDouble(d[3])));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read invoices", e);
        }

        return list;
    }

    public void saveAll(List<Invoice> invoices) {
        List<String> lines = new ArrayList<>();

        for (Invoice i : invoices) {
            lines.add(i.toFileString());
        }

        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save invoices", e);
        }
    }
}