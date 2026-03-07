package riri.dao;

import riri.model.Invoice;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InvoiceDAO extends BaseFileDAO {

    public InvoiceDAO() {
        super("invoices.txt");
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Map<Integer,Invoice> findAll() {
        Map<Integer,Invoice> invoiceMap = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");

                invoiceMap.put(Integer.parseInt(d[0]) ,new Invoice(Integer.parseInt(d[0]), Integer.parseInt(d[1]), LocalDate.parse(d[2],formatter), Double.parseDouble(d[3])));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read invoices", e);
        }

        return invoiceMap;
    }

    public void saveAll(Map<Integer,Invoice> invoiceMap) {
        List<String> lines = new ArrayList<>();
        Collection<Invoice> invoices = invoiceMap.values();

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