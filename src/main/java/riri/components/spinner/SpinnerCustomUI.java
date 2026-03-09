package riri.components.spinner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;

public class SpinnerCustomUI extends BasicSpinnerUI {

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
        btn.setFocusPainted(false);

        btn.addActionListener(e -> spinner.setValue(spinner.getNextValue()));

        return btn;
    }

    @Override
    protected Component createPreviousButton() {

        JButton btn = new JButton("▼");

        btn.setBorder(new EmptyBorder(0,0,0,10));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setFocusPainted(false);

        btn.addActionListener(e -> spinner.setValue(spinner.getPreviousValue()));

        return btn;
    }
}