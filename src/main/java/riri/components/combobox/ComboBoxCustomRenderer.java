package riri.components.combobox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ComboBoxCustomRenderer extends DefaultListCellRenderer {

    private int hoverIndex = -1;

    private final Map<Integer, BiFunction<JList<?>, Object, Component>> renderers = new HashMap<>();

    public void setHoverIndex(int index){
        this.hoverIndex = index;
    }

    public void addItemRenderer(int index, BiFunction<JList<?>, Object, Component> renderer){

        renderers.put(index, renderer);
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        BiFunction<JList<?>, Object, Component> renderer = renderers.get(index);

        if(renderer != null){
            Component comp = renderer.apply(list, value);

            comp.setBackground(getBackgroundColor(index, isSelected));
            return comp;
        }

        JLabel label = (JLabel) super.getListCellRendererComponent(list,value,index,false,false);

        label.setOpaque(false);
        label.setBorder(new EmptyBorder(8,12,8,12));
        label.setFont(new Font("Arial",Font.PLAIN,14));

        if(index >= 0){
            label.setOpaque(true);
            if(index == hoverIndex){
                label.setBackground(new Color(230,240,255));
            } else if (isSelected){
                label.setBackground(new Color(195,194,194));
            } else{
                label.setBackground(Color.WHITE);
            }
        }

        label.setForeground(new Color(83,83,83));

        return label;
    }

    private Color getBackgroundColor(int index, boolean isSelected){
        if(index == hoverIndex){
            return new Color(230,240,255);
        }
        else if(isSelected){
            return new Color(195,194,194);
        }
        return Color.WHITE;
    }
}