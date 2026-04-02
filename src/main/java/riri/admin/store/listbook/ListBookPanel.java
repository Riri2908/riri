package riri.admin.store.listbook;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.table.CustomRenderer;
import riri.components.table.TablePanel;
import riri.model.Area;
import riri.model.Book;
import riri.model.Shelf;
import riri.util.AppContext;
//import riri.admin.store.action.EditBook;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.Map;

public class ListBookPanel extends JPanel {

    private final Map<Integer, Book> books = AppContext.BOOK_SERVICE.getAll();
    private final Map<Integer, Shelf> shelf = AppContext.SHELF_SERVICE.getAll();
    private final Map<Integer, Area> Area = AppContext.AREA_SERVICE.getAll();

    public TablePanel tablePanel = new TablePanel();
    public CustomRenderer renderer = new CustomRenderer();
    public TableRowSorter<DefaultTableModel> sorter;

    private final Icon bookIcon   = new ImageIcon(BasePanel.createImageLogo(getClass(), "store/book-open", 20, 20));
    private final Icon editIcon   = new ImageIcon(BasePanel.createImageLogo(getClass(), "baseicon/edit",      20, 20));
    private final Icon deleteIcon = new ImageIcon(BasePanel.createImageLogo(getClass(), "baseicon/remove",    20, 20));

    public ListBookPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(10, 0, 0, 0));

        BorderPanel whiteBoard = new BorderPanel(15, Color.WHITE, 0, 0, null, 0);
        whiteBoard.setLayout(new BorderLayout());

        tablePanel.setTitle("Danh sách");

        tablePanel.addColumn(0, "TÊN SÁCH");
        tablePanel.addColumn(1, "TÁC GIẢ");
        tablePanel.addColumn(2, "NHÀ XUẤT BẢN");
        tablePanel.addColumn(3, "THỂ LOẠI");
        tablePanel.addColumn(4, "KHU VỰC");
        tablePanel.addColumn(5, "KỆ");
        tablePanel.addColumn(6, "GIÁ TIỀN");
        tablePanel.addColumn(7, "SỐ LƯỢNG");
        tablePanel.addColumn(8, "THAO TÁC");

        sorter = new TableRowSorter<>(tablePanel.getModel());
        tablePanel.getTable().setRowSorter(sorter);

        tablePanel.getTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tablePanel.getTable().rowAtPoint(e.getPoint());
                int col = tablePanel.getTable().columnAtPoint(e.getPoint());
                if (col != 8 || row < 0) return;

                int colStart = tablePanel.getTable().getCellRect(row, 8, true).x;
                int relX = e.getX() - colStart;

                Book[] bookArray = books.values().toArray(new Book[0]);
                Book book = bookArray[row];

//                if (relX < 55) {
//                    new EditBook(book, () -> updateData());
//                }
            }
        });

        renderer.setDefaultSetting((label, row) -> {
            boolean hover = row == tablePanel.getHoveredRow();
            label.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0,
                            hover ? new Color(245, 245, 245) : new Color(230, 230, 230)),
                    new EmptyBorder(10, 20, 10, 10)
            ));
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setForeground(new Color(57, 57, 57));
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setIcon(null);
            label.setOpaque(true);
            return label;
        });

        renderer.setHeaderSetting(label -> {
            label.setBorder(new EmptyBorder(12, 20, 12, 0));
            label.setBackground(new Color(247, 248, 249));
            label.setForeground(new Color(140, 140, 140));
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setOpaque(true);
            return label;
        });
        renderer.applyHeader(tablePanel.getTable());

        renderer.addLabel(0, (label, row) -> {
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(new Color(16, 16, 16));
            label.setIcon(bookIcon);
            label.setIconTextGap(10);
            return label;
        });

        renderer.addLabel(3, (label, row) -> createLeftPill(label.getText(), row, new Color(243, 232, 255), new Color(147, 51, 234)));
        renderer.addLabel(4, (label, row) -> createLeftPill(label.getText(), row, new Color(225, 238, 255), new Color(41, 121, 255)));
        renderer.addLabel(5, (label, row) -> createLeftPill(label.getText(), row, new Color(240, 240, 240), new Color(100, 100, 100)));

        renderer.addLabel(7, (label, row) -> {
            boolean hover = row == tablePanel.getHoveredRow();
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
            panel.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
            panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                    hover ? new Color(245, 245, 245) : new Color(230, 230, 230)));

            BorderPanel pill = new BorderPanel(20, null, 0, 0, null, 0);
            JLabel lbl = new JLabel(label.getText());
            lbl.setFont(new Font("Arial", Font.BOLD, 14));
            lbl.setBorder(new EmptyBorder(2, 12, 2, 12));

            try {
                int qty = Integer.parseInt(label.getText().toString().trim());
                if (qty < 10) {
                    lbl.setForeground(new Color(198, 0, 7));
                    pill.setBackground(new Color(253, 224, 224));
                } else if (qty <= 50) {
                    lbl.setForeground(new Color(180, 90, 0));
                    pill.setBackground(new Color(255, 235, 200));
                } else {
                    lbl.setForeground(new Color(0, 150, 0));
                    pill.setBackground(new Color(217, 250, 229));
                }
            } catch (Exception ignored) {}

            pill.add(lbl);
            panel.add(pill);
            return panel;
        });

        renderer.addLabel(8, (label, row) -> {
            boolean hoverRow = row == tablePanel.getHoveredRow();
            boolean hoverCol = hoverRow && tablePanel.getHoveredCol() == 8;

            int colStart    = tablePanel.getTable().getCellRect(row, 8, true).x;
            int relX        = tablePanel.getHoveredX() - colStart;
            boolean hoverEdit   = hoverCol && relX < 55;
            boolean hoverDelete = hoverCol && relX >= 55;

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            panel.setOpaque(true);
            panel.setBackground(hoverRow ? new Color(245, 245, 245) : Color.WHITE);
            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0,
                            hoverRow ? new Color(245, 245, 245) : new Color(230, 230, 230)),
                    new EmptyBorder(10, 20, 10, 10)
            ));

            BorderPanel editBox = new BorderPanel(16,
                    hoverEdit ? new Color(219, 234, 254) : new Color(248, 248, 248),
                    0, 0, null, 0);
            editBox.setPreferredSize(new Dimension(34, 34));
            editBox.setLayout(new GridBagLayout());
            editBox.add(new JLabel(editIcon));

            BorderPanel deleteBox = new BorderPanel(16,
                    hoverDelete ? new Color(254, 226, 226) : new Color(248, 248, 248),
                    0, 0, null, 0);
            deleteBox.setPreferredSize(new Dimension(34, 34));
            deleteBox.setLayout(new GridBagLayout());
            deleteBox.add(new JLabel(deleteIcon));

            panel.add(editBox);
            panel.add(deleteBox);
            return panel;
        });

        tablePanel.setRenderer(renderer);
        loadData();
        tablePanel.getTable().setRowHeight(55);

        whiteBoard.add(tablePanel, BorderLayout.CENTER);
        add(whiteBoard, BorderLayout.CENTER);
    }

    private JPanel createLeftPill(String text, int row, Color bg, Color fg) {
        boolean hover = row == tablePanel.getHoveredRow();
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        p.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                hover ? new Color(245, 245, 245) : new Color(230, 230, 230)));

        BorderPanel pill = new BorderPanel(15, bg, 0, 0, null, 0);
        JLabel lbl = new JLabel(text);
        lbl.setForeground(fg);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setBorder(new EmptyBorder(4, 12, 4, 12));
        pill.add(lbl);
        p.add(pill);
        return p;
    }

    private void loadData() {
        for (Book b : books.values()) {
            if (b == null) continue;
            if (b.getName() == null || b.getName().isBlank()) continue;

            String areaName = "";
            if (b.getIdArea() > 0) {
                Area area = AppContext.AREA_SERVICE.findById(b.getIdArea());
                if (area != null) areaName = area.getName();
            }

            String shelfName = "";
            if (b.getIdShelf() > 0) {
                Shelf shelf = AppContext.SHELF_SERVICE.findById(b.getIdShelf());
                if (shelf != null) shelfName = shelf.getName();
            }

            tablePanel.addRow(new Object[]{
                    b.getName(),
                    b.getAuthor()    != null ? b.getAuthor()    : "",
                    b.getPublisher() != null ? b.getPublisher() : "",
                    b.getCategory()  != null ? b.getCategory()  : "",
                    areaName,
                    shelfName,
                    String.format("%,.0f đ", b.getPrice()),
                    b.getQuantity(),
                    ""
            });
        }
    }

    public void updateData() {
        tablePanel.getModel().setRowCount(0);
        loadData();
    }

    public TablePanel getTable() {
        return this.tablePanel;
    }
}