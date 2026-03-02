package riri.admin.management.managerbook;

import riri.model.Book;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class BookItemPanel extends JPanel {
    public BookItemPanel(Book book) {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);

        double[] weights = BookManagementPanel.COLUMN_WEIGHTS;
        DecimalFormat df = new DecimalFormat("#,### đ");

        // Cột 1: Tên sách (có icon)
        gbc.gridx = 0;
        gbc.weightx = weights[0];
        add(createCellWrapper(createNameCol(book), 0), gbc);

        // Cột 2: Tác giả - KHÔNG padding để thẳng với header
        gbc.gridx = 1;
        gbc.weightx = weights[1];
        JLabel lblAuthor = new JLabel(book.getAuthor());
        lblAuthor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(createCellWrapper(lblAuthor, 17), gbc);

        // Cột 3: Mã sách - KHÔNG padding
        gbc.gridx = 2;
        gbc.weightx = weights[2];
        BadgeUI idBadge = new BadgeUI(book.getId(), new Color(243, 232, 255), new Color(147, 51, 234));
        add(createCellWrapper(idBadge, -15), gbc);

        // Cột 4: Giá tiền - KHÔNG padding
        gbc.gridx = 3;
        gbc.weightx = weights[3];
        JLabel lblPrice = new JLabel(df.format(book.getPrice()));
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(createCellWrapper(lblPrice, 0), gbc);

        // Cột 5: Số lượng - KHÔNG padding
        gbc.gridx = 4;
        gbc.weightx = weights[4];
        BadgeUI qtyBadge = new BadgeUI(String.valueOf(book.getQuantity()), new Color(254, 243, 199), new Color(217, 119, 6));
        add(createCellWrapper(qtyBadge, -20), gbc);

        // Cột 6: Thao tác - KHÔNG padding
        gbc.gridx = 5;
        gbc.weightx = weights[5];
        add(createCellWrapper(createActionCol(), 0), gbc);
    }

    // Hàm Wrapper - KHÔNG thêm padding
    private JPanel createCellWrapper(JComponent comp, int paddingLeft) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(0, comp.getPreferredSize().height));

        p.add(comp, BorderLayout.WEST);
        return p;
    }

    private JPanel createNameCol(Book book) {
        JPanel p = new JPanel(new BorderLayout(15, 0));
        p.setOpaque(false);
        p.add(new JLabel(new BookIconUI()), BorderLayout.WEST);

        JLabel lblName = new JLabel(book.getName());
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(lblName, BorderLayout.CENTER);
        return p;
    }

    private JPanel createActionCol() {
        // Dùng FlowLayout để xếp 2 icon nằm ngang, cách nhau 15 pixel
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);

        // --- 1. Tạo Nút Sửa ---
        JButton btnEdit = new JButton();
        // Gọi hàm getScaledIcon đã viết sẵn bên dưới.
        // LƯU Ý: Sửa lại đường dẫn "/icons/edit.png" cho đúng với thư mục của bạn
        btnEdit.setIcon(getScaledIcon("/icons/management/edit.png", 20, 20));

        // Xóa viền, xóa nền của nút mặc định
        btnEdit.setContentAreaFilled(false);
        btnEdit.setBorderPainted(false);
        btnEdit.setFocusPainted(false);
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- 2. Tạo Nút Xóa ---
        JButton btnDelete = new JButton();
        // LƯU Ý: Sửa lại đường dẫn "/icons/delete.png" cho đúng với thư mục của bạn
        btnDelete.setIcon(getScaledIcon("/icons/management/delete.png", 20, 20));

        // Xóa viền, xóa nền
        btnDelete.setContentAreaFilled(false);
        btnDelete.setBorderPainted(false);
        btnDelete.setFocusPainted(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thêm 2 nút vào panel
        p.add(btnEdit);
        p.add(btnDelete);

        return p;
    }
    // --- THÊM HÀM NÀY VÀO CUỐI CLASS BookItemPanel ---
    @Override
    public Dimension getMaximumSize() {
        // Khóa cứng chiều cao, thả rông chiều ngang
        return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
    }
    // Hàm hỗ trợ load và chỉnh kích thước icon
    private ImageIcon getScaledIcon(String path, int width, int height) {
        try {
            // Thay đổi đường dẫn này tùy thuộc vào vị trí thư mục icons của bạn
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
            Image img = originalIcon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (Exception e) {
            System.err.println("Không tìm thấy ảnh tại: " + path);
            return null; // Trả về null nếu lỗi để code không bị crash
        }
    }

}

// Lớp BadgeUI
class BadgeUI extends JPanel {
    private final Color bgColor;
    private final String text;
    private final Color fgColor;

    public BadgeUI(String text, Color bgColor, Color fgColor) {
        this.text = text;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        setOpaque(false);
        setPreferredSize(new Dimension(70, 28));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ background
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

        // Vẽ text
        g2.setColor(fgColor);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 2;
        g2.drawString(text, x, y);

        g2.dispose();
    }
}

// Lớp BookIconUI
class BookIconUI implements Icon {
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ nền icon
        g2.setColor(new Color(224, 236, 255));
        g2.fillRoundRect(x, y, 32, 32, 8, 8);

        // Vẽ quyển sách
        g2.setColor(new Color(45, 110, 255));
        g2.setStroke(new BasicStroke(1.5f));

        // Vẽ hình chữ nhật (quyển sách)
        g2.drawRoundRect(x + 8, y + 8, 16, 16, 4, 4);

        // Vẽ đường gáy sách
        g2.drawLine(x + 16, y + 8, x + 16, y + 24);

        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return 32;
    }

    @Override
    public int getIconHeight() {
        return 32;
    }


}