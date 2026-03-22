package riri.components.spinner;

import riri.components.BorderPanel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicFormattedTextFieldUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SpinnerPanel extends BorderPanel {

    private SpinnerModel spinnerModel;

    private final JSpinner spinner;

    public SpinnerPanel() {

        super(20, Color.WHITE, 0,0,new Color(200,200,200),1);
        setLayout(new BorderLayout());

        this.spinnerModel = new SpinnerNumberModel(0, 0, null, 1);

        this.spinner = new JSpinner(spinnerModel);

        spinner.setOpaque(false);
        spinner.setBorder(null);

        spinner.setUI(new SpinnerCustomUI());

        initEditor();

        add(spinner,BorderLayout.CENTER);
    }

    private void initEditor(){

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner,"#");

        spinner.setEditor(editor);

        JFormattedTextField textField = editor.getTextField();

        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setBorder(null);
        textField.setOpaque(false);
        textField.setBackground(new Color(0,0,0,0));

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(new Color(31, 95, 255),2);
            }
            @Override
            public void focusLost(FocusEvent e) {
                setBorder(new Color(221, 221, 221),1);
            }
        });

        JComponent editorComp = spinner.getEditor();
        editorComp.setOpaque(false);

        if (editorComp instanceof JSpinner.DefaultEditor) {
            JFormattedTextField tf = ((JSpinner.DefaultEditor) editorComp).getTextField();
            tf.setOpaque(false);
            tf.setBorder(null);
            tf.setBackground(new Color(0,0,0,0));
            tf.setUI(new BasicFormattedTextFieldUI());
        }
    }

    public JSpinner getSpinner(){
        return spinner;
    }

    public SpinnerModel getSpinnerModel(){
        return spinnerModel;
    }

    public void setSpinnerModel(SpinnerModel spinnerModel){
        this.spinnerModel = spinnerModel;
    }

    public int getValue(){
        return (int) this.spinner.getValue();
    }

    public void setValue(int value){
        this.spinner.setValue(value);
    }

    public void showPlaceholder(){
        this.spinner.setValue(0);
    }
    public void showPlaceholder(int value){
        this.spinner.setValue(value);
    }
}