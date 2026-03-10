import riri.components.BorderPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;

private BorderPanel spinnerWrapper(){
    BorderPanel spinnerWrapper = new BorderPanel(12, Color.WHITE, 0, 0, new Color(200,200,200), 1);

    JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");

    spinner.setEditor(editor);
    spinner.setOpaque(false);
    spinner.setBorder(null);
    spinner.setBackground(new Color(0,0,0,0));

    spinner.setUI(new BasicSpinnerUI() {

        @Override
        protected void installDefaults() {
            super.installDefaults();
            spinner.setBorder(BorderFactory.createEmptyBorder(0,10,0,20));
        }
        @Override
        protected Component createNextButton() {
            JButton btn = new JButton("▲");
            btn.setBorder(new EmptyBorder(0,0,0,10));
            btn.setContentAreaFilled(false);
            btn.setOpaque(false);
            btn.setMargin(new Insets(0, 0, 0, 8));
            btn.addActionListener(_ -> spinner.setValue(spinner.getNextValue()));
            btn.setFocusPainted(false);
            return btn;
        }

        @Override
        protected Component createPreviousButton() {
            JButton btn = new JButton("▼");
            btn.setBorder(new EmptyBorder(0,0,0,10));
            btn.setContentAreaFilled(false);
            btn.setOpaque(false);
            btn.setMargin(new Insets(0, 0, 0, 8));
            btn.addActionListener(_ -> spinner.setValue(spinner.getPreviousValue()));
            btn.setFocusPainted(false);
            return btn;
        }
    });

    JFormattedTextField textField = editor.getTextField();
    textField.setHorizontalAlignment(SwingConstants.LEFT);

    JComponent editorComp = spinner.getEditor();
    editorComp.setOpaque(false);


    if (editorComp instanceof JSpinner.DefaultEditor) {
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editorComp).getTextField();
        tf.setOpaque(false);
        tf.setBorder(null);
        tf.setBackground(new Color(0,0,0,0));
        tf.setUI(new javax.swing.plaf.basic.BasicFormattedTextFieldUI());
    }

    spinnerWrapper.setLayout(new BorderLayout());
    spinnerWrapper.add(spinner, BorderLayout.CENTER);
    return spinnerWrapper;
}