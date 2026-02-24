package riri.admin.management.stat;

import riri.components.BorderPanel;
import riri.components.CreateIconPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagementStat extends BorderPanel  {

    public ManagementStat(String text,double value,Color color,String logoName) {
        super();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,15,20,15));

        JPanel statPanel=new JPanel();
        statPanel.setLayout( new BoxLayout(statPanel, BoxLayout.Y_AXIS));
        statPanel.setOpaque(false);


        JLabel title = BasePanel.createTitle(text,"Arial", Font.PLAIN,14, new Color(136, 136, 136));

        statPanel.add(title);
        statPanel.add(Box.createVerticalStrut(6));
        statPanel.add(BasePanel.createTitle(String.valueOf(value),"Arial",Font.BOLD,20, new Color(0, 0, 0)));
        statPanel.add(Box.createVerticalStrut(6));
        statPanel.add(BasePanel.createTitle("sản phẩm","Arial",Font.PLAIN,14, new Color(136, 136, 136)));


        Image icon = BasePanel.createImageLogo(getClass(), "management/package_"+logoName, 24, 24);

        JLabel label = new JLabel(new ImageIcon(icon));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        CreateIconPanel iconPanel = new CreateIconPanel(label,color);

        JPanel rightWrapper = new JPanel(new GridBagLayout());
        rightWrapper.setBorder(new EmptyBorder(0,0,30,0));
        rightWrapper.setOpaque(false);
        rightWrapper.add(iconPanel);

        add(statPanel, BorderLayout.WEST);
        add(rightWrapper, BorderLayout.EAST);
    }
}
