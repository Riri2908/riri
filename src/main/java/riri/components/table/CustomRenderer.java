package riri.components.table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CustomRenderer extends DefaultTableCellRenderer {

    private final Map<Integer, TableCellRenderer> renderers = new HashMap<>();

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column){

        TableCellRenderer renderer = renderers.get(column);

        if(renderer != null){
            return renderer.getTableCellRendererComponent(
                    table,value,isSelected,hasFocus,row,column);
        }

        return super.getTableCellRendererComponent(
                table,value,isSelected,hasFocus,row,column);
    }

    public void addLabel(int column, TableCellRenderer renderer){
        renderers.put(column, renderer);
    }
    public void addDefaultSetting(int column, Function<JLabel, JLabel> custom) {
        renderers.put(column, (table, value, isSelected, hasFocus, row, columnIndex) -> {

            JLabel label = new JLabel(value == null ? "" : value.toString());

            label.setBorder(new EmptyBorder(10,20,10,10));
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setOpaque(false);

            if (custom != null) {
                label = custom.apply(label);
            }

            return label;
        });
    }
}