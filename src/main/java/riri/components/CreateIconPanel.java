package riri.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateIconPanel extends BorderPanel {

    public CreateIconPanel(JLabel iconLabel,Color color) {
        super(24, color, 0, 0,Color.WHITE,0);
        setOpaque(false);

        setPreferredSize(new Dimension(48, 48));
        setMinimumSize(new Dimension(48, 48));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));

        add(iconLabel, BorderLayout.CENTER);
    }
}

