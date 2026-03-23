package riri.admin.store.listbook;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.table.CustomRenderer;
import riri.components.table.TablePanel;
import riri.model.Book;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.Map;

// Đã đổi thành extends JPanel để làm cái khung tàng hình bọc bên ngoài
public class ListBookPanel extends JPanel {

    // Lấy toàn bộ sách từ database
    private static final Map<Integer, Book> books = AppContext.BOOK_SERVICE.getAll();

    // 3 thành phần chính của bảng
    public TablePanel tablePanel = new TablePanel();
    public CustomRenderer renderer = new CustomRenderer();
    public TableRowSorter<DefaultTableModel> sorter;

    // Load 3 icon từ resource
    private final Icon bookIcon   = new ImageIcon(BasePanel.createImageLogo(getClass(), "store/book-open", 20, 20));
    private final Icon editIcon   = new ImageIcon(BasePanel.createImageLogo(getClass(), "store/edit",      20, 20));
    private final Icon deleteIcon = new ImageIcon(BasePanel.createImageLogo(getClass(), "store/delete",    20, 20));

    public ListBookPanel() {
        // --- BƯỚC 1: KHUNG NGOÀI TÀNG HÌNH ---
        setLayout(new BorderLayout());
        setOpaque(false); // Trong suốt để lộ màu nền app
        // Đẩy UI xuống 40px và chừa lề trái/phải/dưới 20px cho đẹp
        setBorder(new EmptyBorder(200, 0, 0, 0));

        // --- BƯỚC 2: BẢNG TRẮNG CHỨA NỘI DUNG ---
        // Dùng BorderPanel của bạn để giữ góc bo tròn 15px
        BorderPanel whiteBoard = new BorderPanel(15, Color.WHITE, 0, 0, null, 0);
        whiteBoard.setLayout(new BorderLayout());

        // Tiêu đề bảng
        tablePanel.setTitle("Danh sách");

        // Khai báo 9 cột
        tablePanel.addColumn(0, "TÊN SÁCH");
        tablePanel.addColumn(1, "TÁC GIẢ");
        tablePanel.addColumn(2, "NHÀ XUẤT BẢN");
        tablePanel.addColumn(3, "THỂ LOẠI");
        tablePanel.addColumn(4, "KHU VỰC");
        tablePanel.addColumn(5, "KỆ");
        tablePanel.addColumn(6, "GIÁ TIỀN");
        tablePanel.addColumn(7, "SỐ LƯỢNG");
        tablePanel.addColumn(8, "THAO TÁC");

        // Cho phép sort khi click header
        sorter = new TableRowSorter<>(tablePanel.getModel());
        tablePanel.getTable().setRowSorter(sorter);

        // ---- STYLE MẶC ĐỊNH cho tất cả ô ----
        renderer.setDefaultSetting((label, row) -> {
            boolean hover = row == tablePanel.getHoveredRow(); // đang hover hàng này không
            label.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, // đường kẻ dưới
                            hover ? new Color(245, 245, 245) : new Color(230, 230, 230)),
                    new EmptyBorder(10, 20, 10, 10) // padding trong
            ));
            label.setHorizontalAlignment(JLabel.LEFT); // căn trái
            label.setForeground(new Color(57, 57, 57)); // màu chữ
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setIcon(null); // xóa icon cũ tránh bị dính sang cột khác
            label.setOpaque(true);
            return label;
        });

        // ---- STYLE HEADER ----
        renderer.setHeaderSetting(label -> {
            label.setBorder(new EmptyBorder(12, 20, 12, 0));
            label.setBackground(new Color(247, 248, 249)); // nền xám nhạt
            label.setForeground(new Color(140, 140, 140)); // chữ xám
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setOpaque(true);
            return label;
        });
        renderer.applyHeader(tablePanel.getTable()); // áp dụng header style

        // ---- CỘT 0: TÊN SÁCH - khung xanh + icon ----
        renderer.addLabel(0, (label, row) -> {
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(new Color(16, 16, 16));
            label.setIcon(bookIcon);
            label.setIconTextGap(10);
            return label;
        });

        // ---- CỘT 3,4,5: PILL màu (tím, xanh, xám) ----
        renderer.addLabel(3, (label, row) -> createLeftPill(label.getText(), row, new Color(243, 232, 255), new Color(147, 51, 234)));
        renderer.addLabel(4, (label, row) -> createLeftPill(label.getText(), row, new Color(225, 238, 255), new Color(41, 121, 255)));
        renderer.addLabel(5, (label, row) -> createLeftPill(label.getText(), row, new Color(240, 240, 240), new Color(100, 100, 100)));

        // ---- CỘT 7: SỐ LƯỢNG - 3 màu đỏ/cam/xanh ----
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
                if (qty < 10) {                          // đỏ: sắp hết
                    lbl.setForeground(new Color(198, 0, 7));
                    pill.setBackground(new Color(253, 224, 224));
                } else if (qty <= 50) {                  // cam: trung bình
                    lbl.setForeground(new Color(180, 90, 0));
                    pill.setBackground(new Color(255, 235, 200));
                } else {                                 // xanh: còn nhiều
                    lbl.setForeground(new Color(0, 150, 0));
                    pill.setBackground(new Color(217, 250, 229));
                }
            } catch (Exception ignored) {}

            pill.add(lbl);
            panel.add(pill);
            return panel;
        });

        // ---- CỘT 8: THAO TÁC - icon sửa/xóa có hover ----
        renderer.addLabel(8, (label, row) -> {
            boolean hoverRow = row == tablePanel.getHoveredRow();
            boolean hoverCol = hoverRow && tablePanel.getHoveredCol() == 8;

            // Tính vị trí x chuột trong cột để phân biệt hover nút nào
            int colStart    = tablePanel.getTable().getCellRect(row, 8, true).x;
            int relX        = tablePanel.getHoveredX() - colStart;
            boolean hoverEdit   = hoverCol && relX < 55;  // trái → sửa
            boolean hoverDelete = hoverCol && relX >= 55; // phải → xóa

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            panel.setOpaque(true);
            panel.setBackground(hoverRow ? new Color(245, 245, 245) : Color.WHITE);
            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0,
                            hoverRow ? new Color(245, 245, 245) : new Color(230, 230, 230)),
                    new EmptyBorder(10, 20, 10, 10)
            ));

            // Nút sửa: xanh khi hover, xám nhạt bình thường
            BorderPanel editBox = new BorderPanel(16,
                    hoverEdit ? new Color(219, 234, 254) : new Color(248, 248, 248),
                    0, 0, null, 0);
            editBox.setPreferredSize(new Dimension(34, 34));
            editBox.setLayout(new GridBagLayout());
            editBox.add(new JLabel(editIcon));

            // Nút xóa: đỏ khi hover, xám nhạt bình thường
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

        // --- BƯỚC 3: RÁP LẠI VÀO NHAU ---
        // Cho cái bảng vào nền trắng
        whiteBoard.add(tablePanel, BorderLayout.CENTER);

        // Cho nền trắng vào cái panel tàng hình ngoài cùng
        add(whiteBoard, BorderLayout.CENTER);
    }

    // Tạo pill bo tròn dạt trái — dùng chung cho cột 3, 4, 5
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

    // Load dữ liệu từ map books vào bảng
    private void loadData() {
        for (Book b : books.values()) {
            if (b == null) continue;
            if (b.getName() == null || b.getName().isBlank()) continue;
            tablePanel.addRow(new Object[]{
                    b.getName(),
                    b.getAuthor()    != null ? b.getAuthor()    : "",
                    b.getPublisher() != null ? b.getPublisher() : "",
                    b.getCategory()  != null ? b.getCategory()  : "",
                    b.getArea()      != null ? b.getArea()      : "",
                    b.getShelf()     != null ? b.getShelf()     : "",
                    String.format("%,.0f đ", b.getPrice()),
                    b.getQuantity(),
                    ""
            });
        }
    }

    // Xóa bảng và load lại — gọi khi có thay đổi dữ liệu
    public void updateData() {
        tablePanel.getModel().setRowCount(0);
        loadData();
    }

    public TablePanel getTable() {
        return this.tablePanel;
    }
}