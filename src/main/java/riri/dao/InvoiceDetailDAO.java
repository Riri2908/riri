package riri.dao;

import riri.model.InvoiceDetail;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO extends BaseFileDAO {

    public InvoiceDetailDAO() {
        super("invoice_details.txt");
    }

    public List<InvoiceDetail> findAll() {
        List<InvoiceDetail> list = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");

                list.add(new InvoiceDetail(d[0], d[1], Integer.parseInt(d[2]), Double.parseDouble(d[3])));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read invoice details", e);
        }

        return list;
    }

    public void saveAll(List<InvoiceDetail> details) {
        List<String> lines = new ArrayList<>();

        for (InvoiceDetail d : details) {
            lines.add(d.toFileString());
        }

        try {
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save invoice details", e);
        }
    }
}