package riri.dao;

import riri.model.InvoiceDetail;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class InvoiceDetailDAO extends BaseFileDAO {

    public InvoiceDetailDAO() {
        super("invoice_details.txt");
    }

    public Map<Integer,InvoiceDetail> findAll() {
        Map<Integer,InvoiceDetail> detailMap = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] d = line.split(";");

                detailMap.put(Integer.parseInt(d[0]),new InvoiceDetail(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]), Double.parseDouble(d[3])));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read invoice details", e);
        }

        return detailMap;
    }

    public void saveAll(Map<Integer,InvoiceDetail> detail) {
        List<String> lines = new ArrayList<>();
        Collection<InvoiceDetail> details = detail.values();

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