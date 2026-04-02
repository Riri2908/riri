package riri.components.combobox;

import riri.components.BorderPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComboBoxUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ComboBoxPanel<T> extends BorderPanel {

    public JComboBox<T> comboBox;
    public ComboBoxCustomRenderer<T> comboBoxRenderer = new ComboBoxCustomRenderer<>();
    public ComboBoxCustomUI comboBoxCustomUI = new ComboBoxCustomUI();

    public ComboBoxPanel() {
        super(20, Color.WHITE, 0, 0, new Color(214, 214, 214), 1);

        setBorder(new EmptyBorder(10,10,10,10));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 36));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        this.comboBox = new JComboBox<>();

        comboBox.setBorder(null);
        comboBox.setOpaque(false);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setRenderer(comboBoxRenderer);
        comboBox.setUI(comboBoxCustomUI);
        ComboBoxCustomUI.customizeComboBoxScroll(comboBox);

        comboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(new Color(31, 95, 255),2);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(new Color(221, 221, 221),1);
            }
        });

        add(comboBox,BorderLayout.CENTER);

    }


    public JComboBox<T> getComboBox() {
        return comboBox;
    }

    public void setComboBox(JComboBox<T> cbBook) {
        this.comboBox = cbBook;
    }

    public void addItem(T item){
        comboBox.addItem(item);
    }

    public void setItems(T[] items){
        comboBox.removeAllItems();
        for(T item : items){
            comboBox.addItem(item);
        }
    }

    public void setCustomRenderer(ListCellRenderer<? super T> renderer){
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

    public void showPlaceholder(){
       this.comboBox.setSelectedIndex(0);
    }

    public void showPlaceholder(int index){
        if(index>=0 && index<comboBox.getItemCount()){
            this.comboBox.setSelectedIndex(index-1);
        }
    }
}
