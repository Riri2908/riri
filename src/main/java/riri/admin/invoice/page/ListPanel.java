package riri.admin.invoice.page;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.table.CustomRenderer;
import riri.components.table.TablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ListPanel extends BorderPanel {

    public JPanel emptyPanel = new JPanel();
    public CustomRenderer renderer = new CustomRenderer();
    public TablePanel tablePanel = new TablePanel();

    CardLayout cardLayout = new CardLayout();
    public JPanel root = new JPanel(cardLayout);

    public ListPanel() {

        setLayout(new BorderLayout());
        tableSetting();
        tableEmty();

        root.add(tablePanel, "Có sách");
        root.add(emptyPanel, "Không sách");
        add(root, BorderLayout.CENTER);
    }

    public void tableSetting(){
        tablePanel.setTitle("Danh sách mua hàng");

        tablePanel.addColumn(0,"TÊN SÁCH");
        tablePanel.addColumn(1,"GIÁ");
        tablePanel.addColumn(2,"SỐ LƯỢNG");
        tablePanel.addColumn(3,"THÀNH TIỀN");
        tablePanel.addColumn(4,"THAO TÁC");

        renderer.setDefaultSetting((label,row)->{

            boolean hover = row == tablePanel.getHoveredRow();

            label.setBorder(new EmptyBorder(10,20,10,10));

            label.setBackground(
                    hover ? new Color(245,245,245) : Color.WHITE
            );

            label.setBorder(BorderFactory.createMatteBorder(
                    0,0,1,0,
                    hover ? new Color(245,245,245) : new Color(230,230,230)
            ));

            label.setHorizontalAlignment(JLabel.CENTER);
            label.setForeground(new Color(57,57,57));

            label.setIcon(null);
            label.setFont(new Font("Arial",Font.PLAIN,14));

            label.setOpaque(true);

            return label;
        });

        renderer.setHeaderSetting(label -> {
            label.setBorder(new EmptyBorder(10,0,10,0));
            label.setBackground(new Color(247, 248, 249));
            label.setForeground(new Color(140, 140, 140));
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            return label;
        });

        renderer.applyHeader(tablePanel.getTable());
        tablePanel.setRenderer(renderer);

    }

    public void tableEmty(){

    }
}
