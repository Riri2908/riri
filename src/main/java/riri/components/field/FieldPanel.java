package riri.components.field;

import riri.components.BorderPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FieldPanel extends BorderPanel {
    public String title;

    public JTextField field;

    public FieldPanel(String title) {
        super(20, Color.WHITE, 0, 0, new Color(214, 214, 214), 1);
        setBorder(new EmptyBorder(10,10,10,10));
        setLayout(new BorderLayout());

        this.title = title;
        field = new JTextField();

        this.setFontField(new Font("Noto Sans",Font.PLAIN,16 ));
        this.showPlaceholder();

        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder());

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(new Color(31, 95, 255),2);
                if(getTextField().equals(title)){
                    setTextField("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(new Color(221, 221, 221),1);
                if(getTextField().isEmpty()){
                    showPlaceholder(title,new Color(174,174,174));
                }
            }
        });


        add(field, BorderLayout.CENTER);

    }
    public void setField(JTextField field){
        this.field = field;
    }

    public JTextField getField(){
        return this.field;
    }

    public void setTextField(String text) {
        field.setText(text);
    }

    public String getTextField() {
        return field.getText();
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void showPlaceholder(String text, Color colorForeground) {
        this.field.setText(text);
        this.field.setForeground(colorForeground);
    }

    public void showPlaceholder() {
        this.field.setText(title);
        this.field.setForeground(new Color(133, 133, 133));
    }

    public void setFontField(Font font) {
        field.setFont(font);
    }
}
