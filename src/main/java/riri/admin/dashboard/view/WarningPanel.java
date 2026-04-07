package riri.admin.dashboard.view;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.table.CustomRenderer;
import riri.components.table.TablePanel;
import riri.model.*;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;


public class WarningPanel extends BorderPanel {

    public TablePanel tablePanel = new TablePanel();
    public CustomRenderer renderer = new CustomRenderer();

    public WarningPanel(){
        super(0, new Color(247, 248, 249), 0, 0, null, 0);
        setLayout(new BorderLayout());

        tablePanel.setTitle("Sách sắp hết hàng");
        Icon icon = BasePanel.createIcon(getClass(), "homepage/warning_book", 26, 26, new Color(255, 68, 0));
        tablePanel.getTitle().setIcon(icon);
        tablePanel.getTitle().setIconTextGap(10);

        tablePanel.addColumn(0, "ID");
        tablePanel.addColumn(1, "Tên sách");
        tablePanel.addColumn(2, "Tác giả");
        tablePanel.addColumn(3, "Số lượng còn");
        tablePanel.addColumn(4, "Vị trí");

        // Ẩn cột ID
        TableColumnModel columnModel = tablePanel.getTable().getColumnModel();
        TableColumn idColumn = columnModel.getColumn(0);
        columnModel.removeColumn(idColumn);

        setupRenderer();
        renderer.applyHeader(tablePanel.getTable());
        tablePanel.setRenderer(renderer);

        add(tablePanel, BorderLayout.CENTER);
    }

    public void clearRows() {
        tablePanel.getModel().setRowCount(0);
    }

    public void addRow(Object[] rowData) {
        tablePanel.addRow(rowData);
    }

    private void setupRenderer() {
        renderer.setDefaultSetting((label, row) -> {
            boolean hover = row == tablePanel.getHoveredRow();
            label.setBorder(BorderFactory.createMatteBorder(
                    0, 0, 1, 0,
                    hover ? new Color(245, 245, 245) : new Color(230, 230, 230)));
            label.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setForeground(new Color(57, 57, 57));
            label.setIcon(null);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setOpaque(true);
            return label;
        });

        renderer.addLabel(0, (label, _) -> {
            label.setFont(new Font("Arial", Font.BOLD, 14));
            return label;
        });

        renderer.addLabel(2, (label, row) -> {
            boolean hover = row == tablePanel.getHoveredRow();

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setOpaque(true);
            panel.setBackground(hover ? new Color(245, 245, 245) : Color.WHITE);
            panel.setBorder(BorderFactory.createMatteBorder(
                    0, 0, 1, 0,
                    hover ? new Color(245, 245, 245) : new Color(230, 230, 230)));

            BorderPanel badge = new BorderPanel(20, null, 0, 0, null, 0);
            badge.setBackground(new Color(255, 255, 255));

            JLabel qtyLabel = new JLabel(label.getText() + " cuốn");
            qtyLabel.setFont(new Font("Arial", Font.BOLD, 17));
            qtyLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
            qtyLabel.setIconTextGap(5);

            int qty = Integer.parseInt(label.getText());

            Icon icon1 = BasePanel.createIcon(getClass(),"homepage/package",24,24,new Color(207, 134, 0));
            Icon icon2 = BasePanel.createIcon(getClass(),"homepage/package",24,24,new Color(255, 78, 0));
            Icon icon3 = BasePanel.createIcon(getClass(),"homepage/package",24,24,new Color(229, 0, 11));


            if (qty <= 5) {
                qtyLabel.setForeground(new Color(229, 0, 11));
                badge.setBackground(new Color(253, 225, 227));
                qtyLabel.setIcon(icon3);
            } else if (qty <= 10) {
                qtyLabel.setForeground(new Color(255, 78, 0));
                badge.setBackground(new Color(255, 236, 229));
                qtyLabel.setIcon(icon2);
            } else {
                qtyLabel.setForeground(new Color(207, 134, 0));
                badge.setBackground(new Color(248, 242, 231));
                qtyLabel.setIcon(icon1);
            }

            badge.add(qtyLabel);
            panel.add(badge);
            return panel;
        });

        renderer.addLabel(3, (label, object, _) -> {
            Shelf shelf = (Shelf) object;
            Area area = AppContext.AREA_SERVICE.findById(shelf.getAreaId());
            label.setText(String.format(
                    "<html>"
                            + "<span style='color:#000000; font-weight:bold; font-family:Arial; font-size:12px;'>%s</span>"
                            + "<span style='color:#94a3b8; margin:0 7px;'> &bull; </span>"
                            + "<span style='color:#475569; font-family:Arial; font-size:12px;'>%s</span>"
                            + "</html>",
                    area.getName(), shelf.getName()));
            return label;
        });

        renderer.setHeaderSetting(label -> {
            label.setBorder(new EmptyBorder(10, 0, 10, 0));
            label.setBackground(new Color(247, 248, 249));
            label.setForeground(new Color(140, 140, 140));
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            return label;
        });
    }
}
