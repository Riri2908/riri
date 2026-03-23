package riri.components.combobox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ComboBoxCustomRenderer<T> extends DefaultListCellRenderer {

    private int hoverIndex = -1;

    // Dùng cho Index
    private final Map<Integer, BiFunction<JList<? extends T>, T, Component>> indexRenderers = new HashMap<>();

    //Dùng cho Object
    private BiFunction<T, Integer, JComponent> componentBuilder;

    public ComboBoxCustomRenderer() {}

    public ComboBoxCustomRenderer(BiFunction<T, Integer, JComponent> builder) {
        this.componentBuilder = builder;
    }

    public void setComponentBuilder(BiFunction<T, Integer, JComponent> builder) {
        this.componentBuilder = builder;
    }
    public void setHoverIndex(int index) {
        this.hoverIndex = index;
    }

    public void addItemRenderer(int index, BiFunction<JList<? extends T>, T, Component> renderer) {
        indexRenderers.put(index, renderer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        T item = (T) value;

        //Renderer theo Index
        BiFunction<JList<? extends T>, T, Component> indexRenderer = indexRenderers.get(index);

        if (indexRenderer != null) {
            Component comp = indexRenderer.apply((JList<? extends T>) list, item);
            applyBackground(comp, index, isSelected);
            return comp;
        }

        //Renderer theo Object
        if (componentBuilder != null && item != null) {
            JComponent comp = componentBuilder.apply(item, index);
            applyBackground(comp, index, isSelected);
            return comp;
        }

        //Default Renderer
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, false, false);
        label.setOpaque(false);
        label.setBorder(new EmptyBorder(8,12,8,12));
        label.setFont(new Font("Arial",Font.PLAIN,14));
        applyBackground(label, index, isSelected);
        label.setForeground(new Color(83,83,83));


        return label;
    }

    private void applyBackground(Component comp, int index, boolean isSelected) {

        if (!(comp instanceof JComponent jc)) {
            return;
        }

        if (index >= 0) {

            jc.setOpaque(true);

            if (index == hoverIndex) {
                jc.setBackground(new Color(230, 240, 255));
            }
            else if (isSelected) {
                jc.setBackground(new Color(195, 194, 194));
            }
            else {
                jc.setBackground(Color.WHITE);
            }
        }
        else {
            jc.setOpaque(false);
        }

        jc.setForeground(new Color(83, 83, 83));
    }
}