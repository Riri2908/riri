package riri.components.combobox;

import riri.components.BorderPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComboBoxUI;
import java.awt.*;

public class ComboBoxPanel extends BorderPanel {

    public JComboBox<String> comboBox;
    public ComboBoxCustomRenderer comboBoxRenderer = new ComboBoxCustomRenderer();
    public ComboBoxCustomUI comboBoxCustomUI = new ComboBoxCustomUI();

    public ComboBoxPanel() {
        super(12, Color.WHITE, 0, 0, new Color(214, 214, 214), 1);

        setBorder(new EmptyBorder(0,10,0,0));
        setLayout(new BorderLayout());

        this.comboBox = new JComboBox<>();

        comboBox.setBorder(null);
        comboBox.setOpaque(false);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setRenderer(comboBoxRenderer);
        comboBox.setUI(comboBoxCustomUI);

        add(comboBox,BorderLayout.CENTER);

    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public void setComboBox(JComboBox<String> cbBook) {
        this.comboBox = cbBook;
    }
    public void addItem(String item){
        comboBox.addItem(item);
    }

    public void setItems(String[] items){
        comboBox.removeAllItems();
        for(String item : items){
            comboBox.addItem(item);
        }
    }

    public void setCustomRenderer(ListCellRenderer<? super String> renderer){
        comboBox.setRenderer(renderer);
    }

    public void setFontStyle(Font font){
        comboBox.setFont(font);
    }

    public void setBorderColor(Color color,int thickness){
        setBorder(color,thickness);
    }

    public void setComboBoxUI(ComboBoxUI comboBoxUI){
        comboBox.setUI(comboBoxUI);
    }
}
