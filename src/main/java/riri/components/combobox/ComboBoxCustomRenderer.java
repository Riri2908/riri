package riri.components.combobox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ComboBoxCustomRenderer extends DefaultListCellRenderer {

    private int hoverIndex = -1;

    public void setHoverIndex(int index){
        hoverIndex = index;
    }

    @Override
    public Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(
                list,value,index,false,false);

        label.setOpaque(false);

        label.setBorder(new EmptyBorder(8,12,8,12));
        label.setFont(new Font("Arial",Font.PLAIN,14));

        if(index >= 0){

            label.setOpaque(true);
            if(index == hoverIndex){
                label.setBackground(new Color(230,240,255));
            }
            else if(isSelected){
                label.setBackground(new Color(195,194,194));
            }
            else{
                label.setBackground(Color.WHITE);
            }
        }

        label.setForeground(new Color(83,83,83));

        return label;
    }
}
