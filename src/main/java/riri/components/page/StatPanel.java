package riri.components.page;

import riri.components.BorderPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatPanel extends BorderPanel  {

    private final JLabel valueLabel;

    public StatPanel(String text1, String value, String text2, Color colorBackGroundIcon, String logoName) {
        super();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,15,20,15));

        JPanel statPanel=new JPanel();
        statPanel.setLayout( new BoxLayout(statPanel, BoxLayout.Y_AXIS));
        statPanel.setOpaque(false);

        JLabel title = BasePanel.createTitle(text1,"Arial", Font.PLAIN,14, new Color(136, 136, 136));

        statPanel.add(title);
        statPanel.add(Box.createVerticalStrut(6));

        valueLabel = BasePanel.createTitle(value,"Arial",Font.BOLD,20,Color.BLACK);
        statPanel.add(valueLabel);
        statPanel.add(Box.createVerticalStrut(6));

        statPanel.add(BasePanel.createTitle(text2,"Arial",Font.PLAIN,14, new Color(136, 136, 136)));

        Image icon = BasePanel.createImageLogo(getClass(), logoName, 24, 24);

        JLabel label = new JLabel(new ImageIcon(icon));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        BorderPanel iconPanel = BasePanel.createIconPanel(label,colorBackGroundIcon);

        JPanel rightWrapper = new JPanel(new GridBagLayout());
        rightWrapper.setBorder(new EmptyBorder(0,0,30,0));
        rightWrapper.setOpaque(false);
        rightWrapper.add(iconPanel);

        add(statPanel, BorderLayout.WEST);
        add(rightWrapper, BorderLayout.EAST);
    }

    public void setValue(double value){
        valueLabel.setText(BasePanel.formatNumber((long)value));
    }
}
