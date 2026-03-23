package riri.components.table;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class TablePanel extends BorderPanel {
    public DefaultTableCellRenderer renderer;
    public Map<Integer,String> column= new HashMap<>();

    private final JLabel titleLabel;
    private int hoveredRow = -1;
    private int hoveredCol = -1;
    private int hoveredX = -1;

    public JTable table;
    private final DefaultTableModel model;

    public TablePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        titleLabel = BasePanel.createTitle("", "Arial", Font.BOLD, 20, Color.BLACK);
        titleLabel.setBorder(new EmptyBorder(20,20,20,20));
        add(titleLabel, BorderLayout.NORTH);

        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS );
        table.setRowHeight(45);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.WHITE);

        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hoveredRow = table.rowAtPoint(e.getPoint());
                hoveredCol = table.columnAtPoint(e.getPoint());
                hoveredX = e.getPoint().x;
                table.repaint();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                hoveredCol = -1;
                hoveredX = -1;
                table.repaint();
            }
        });

        table.setDefaultRenderer(Object.class,renderer);

        JScrollPane scroll = BasePanel.createScroll(table);
        scroll.setPreferredSize(new Dimension(400,400));

        scroll.setOpaque(false);
        scroll.setBorder(new EmptyBorder(0, 1, 10, 4));
        scroll.getViewport().setOpaque(false);
        add(scroll, BorderLayout.CENTER);

    }
    public void addColumn(int order, String name){
        column.put(order,name);
        model.addColumn(name);
    }

    public void addColumn(int order, String name,int width){
        column.put(order,name);
        model.addColumn(name);
    }

    public Map<Integer,String> getColumn(){
        return column;
    }

    public void setTitle(String title){
        titleLabel.setText(title);
    }

    public void setRenderer(DefaultTableCellRenderer renderer){
        this.renderer = renderer;
        this.table.setDefaultRenderer(Object.class, renderer);
    }
    public DefaultTableCellRenderer getRenderer(){
        return renderer;
    }

    public void addRow(Object[] row){
        model.addRow(row);
    }

    public DefaultTableModel getModel(){
        return this.model;
    }
    public JTable getTable(){
        return table;
    }

    public void setRowSorter(RowSorter<? extends TableModel> sorter){
        table.setRowSorter(sorter);
    }

    public int getHoveredRow(){
        return this.hoveredRow;
    }
    public int getHoveredCol() {
        return this.hoveredCol;
    }
    public int getHoveredX() {
        return this.hoveredX;
    }
    public void autoResizeColumns() {

        JTable table = this.table;

        for (int col = 0; col < table.getColumnCount(); col++) {

            TableColumn column = table.getColumnModel().getColumn(col);

            int maxWidth = 50;

            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();

            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table,
                    column.getHeaderValue(),
                    false,
                    false,
                    0,
                    col
            );

            maxWidth = Math.max(maxWidth, headerComp.getPreferredSize().width);

            for (int row = 0; row < table.getRowCount(); row++) {

                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);

                Component comp = table.prepareRenderer(cellRenderer, row, col);

                maxWidth = Math.max(maxWidth, comp.getPreferredSize().width + 20);
            }

            column.setPreferredWidth(maxWidth);
        }
    }


}
