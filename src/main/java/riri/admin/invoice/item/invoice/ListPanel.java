package riri.admin.invoice.item.invoice;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.table.CustomRenderer;
import riri.components.table.TablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListPanel extends BorderPanel {

    public BorderPanel emptyPanel;
    public CustomRenderer renderer = new CustomRenderer();
    public TablePanel tablePanel = new TablePanel();

    public TotalAmountPanel totalAmountPanel;

    CardLayout cardLayout = new CardLayout();

    public ListPanel(TotalAmountPanel totalAmountPanel) {
        this.totalAmountPanel = totalAmountPanel;

        setLayout(cardLayout);
        tableSetting();
        tableEmpty();

        add(tablePanel, "Có sách");
        add(emptyPanel, "Không sách");
        if(tableIsEmpty()){
            cardLayout.show(this,"Không sách");
        }else {
            cardLayout.show(this,"Có sách");
        }

    }

    public void tableSetting(){
        tablePanel.setTitle("Danh sách mua hàng");

        tablePanel.addColumn(0,"ID");
        tablePanel.addColumn(1,"TÊN SÁCH");
        tablePanel.addColumn(2,"GIÁ");
        tablePanel.addColumn(3,"SỐ LƯỢNG");
        tablePanel.addColumn(4,"THÀNH TIỀN");
        tablePanel.addColumn(5,"THAO TÁC");

        TableColumnModel columnModel = tablePanel.getTable().getColumnModel();
        TableColumn idColumn = columnModel.getColumn(0);

        columnModel.removeColumn(idColumn);


        renderer.setDefaultSetting((label,row)->{

            boolean hover = row == tablePanel.getHoveredRow();

            label.setBorder(new EmptyBorder(10,20,10,10));

            label.setBackground(hover ? new Color(245,245,245) : Color.WHITE);

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

        renderer.addLabel(0,(label, row)->{
            label.setFont(new Font("Arial", Font.BOLD, 14));
            return label;
        });

        renderer.addLabel(2,(label,row)->{

            boolean hoverRow = row == tablePanel.getHoveredRow();

            BorderPanel panel = new BorderPanel(0,Color.WHITE,0,0,null,0);
            panel.setLayout(new GridBagLayout());

            panel.setBackground(hoverRow ? new Color(245,245,245) : Color.WHITE);

            panel.setBorder(BorderFactory.createMatteBorder(
                    0,0,1,0,
                    hoverRow ? new Color(245,245,245) : new Color(230,230,230)));

            int quantity = (int) tablePanel.getTable().getValueAt(row,2);

            JLabel minus = new JLabel("- ");
            JLabel value = new JLabel(String.valueOf(quantity));
            JLabel plus = new JLabel(" +");

            minus.setFont(new Font("Arial",Font.BOLD,16));
            plus.setFont(new Font("Arial",Font.BOLD,16));
            value.setFont(new Font("Arial",Font.PLAIN,14));

            panel.add(minus);
            panel.add(Box.createHorizontalStrut(10));
            panel.add(value);
            panel.add(Box.createHorizontalStrut(10));
            panel.add(plus);

            return panel;
        });

        renderer.addLabel(3,(label, row)->{
            label.setFont(new Font("Arial", Font.BOLD, 14));
            return label;
        });

        renderer.addLabel(4,(label,row)->{
            boolean hoverRow = row == tablePanel.getHoveredRow();
            boolean hoverCol = hoverRow && tablePanel.getHoveredCol() == 4;

            BorderPanel panel =  new BorderPanel(0,new Color(255, 255, 255),0,0,null, 0);
            panel.setBackground(hoverRow ? new Color(245,245,245) : Color.WHITE);
            panel.setLayout(new GridBagLayout());

            panel.setBorder(BorderFactory.createMatteBorder(
                    0,0,1,0,
                    hoverRow ? new Color(245,245,245) : new Color(230,230,230)));

            BorderPanel buttonRemove =  new BorderPanel(14,new Color(253, 164, 164),0,0,null, 0);
            buttonRemove.setBorder(new EmptyBorder(1,1,1,1));

            Icon icon = new ImageIcon(BasePanel.createImageLogo(getClass(), "invoice/listpanel/remove", 17,17));
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            buttonRemove.add(iconLabel);

            if(hoverCol){
                buttonRemove.setBackground(new Color(253, 164, 164));
            }
            else {
                buttonRemove.setBackground(new Color(255, 255, 255));
            }

            panel.add(buttonRemove);

            return panel;
        });

        renderer.applyHeader(tablePanel.getTable());
        tablePanel.setRenderer(renderer);

        tablePanel.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = tablePanel.getTable();

                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row < 0) return;

                if (col == 4) {
                    tablePanel.getModel().removeRow(row);
                    totalAmountPanel.updateTotals();
                    if (tableIsEmpty()) {
                        cardLayout.show(ListPanel.this,"Không sách");
                    }
                    return;
                }

                if (col == 2) {
                    Rectangle rect = table.getCellRect(row,col,false);

                    int clickX = e.getX() - rect.x;
                    int width = rect.width;

                    int quantity = (int) table.getValueAt(row,2);

                    if(clickX < width/2){
                        if(quantity > 1) quantity--;
                    }

                    else if(clickX > width/2){
                        quantity++;
                    }
                    else{
                        return;
                    }

                    table.setValueAt(quantity,row,2);

                    double price = (double) table.getValueAt(row,1);
                    table.setValueAt(price * quantity,row,3);

                    table.repaint();

                    totalAmountPanel.updateTotals();
                }

            }
        });

    }

    public void tableEmpty(){
        emptyPanel = new BorderPanel();
        emptyPanel.setLayout(new GridBagLayout());

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        Icon icon = new ImageIcon(BasePanel.createImageLogo(getClass(), "invoice/listpanel/empty",80,80));

        JLabel label = new JLabel(icon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = BasePanel.createTitle("Giỏ hàng hiện tại đang trống", "Arial", Font.BOLD, 20, new Color(191,191,191));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(label);
        content.add(Box.createVerticalStrut(10));
        content.add(title);

        emptyPanel.add(content);
    }

    public void addData(Object[] data){
        this.tablePanel.addRow(data);
        cardLayout.show(this,"Có sách");
    }

    public void tableClear() {

        tablePanel.getModel().setRowCount(0);
        cardLayout.show(this,"Không sách");
    }

    public boolean tableIsEmpty(){
        return tablePanel.getTable().getRowCount()==0;
    }
}
