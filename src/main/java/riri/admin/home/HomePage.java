package riri.admin.home;

import riri.admin.home.items.FeaturePanel;
import riri.admin.home.items.HeaderPanel;
import riri.admin.home.items.TopPanel;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.page.ContentPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomePage extends JPanel {
    public BorderPanel root = new BorderPanel(0,new Color(247, 248, 249),0,0,null,0);

    public ContentPanel contentPanel;
    public HeaderPanel headerPanel = new HeaderPanel();
    public FeaturePanel featurePanel = new FeaturePanel(null);
    public TopPanel topPanel = new TopPanel(null);

    public HomePage(ContentPanel contentPanel) {
        this.contentPanel=contentPanel;
        this.featurePanel.contentPanel = contentPanel;
        this.topPanel.contentPanel = contentPanel;
        setLayout(new BorderLayout());

        root.setBorder(new EmptyBorder(25, 25, 25, 25));
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        featurePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        root.add(headerPanel);
        root.add(Box.createVerticalStrut(10));
        JLabel title = BasePanel.createTitle("Các chức năng chính","Arial",Font.BOLD,25,Color.BLACK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        root.add(title);
        root.add(Box.createVerticalStrut(10));
        root.add(featurePanel);
        root.add(Box.createVerticalStrut(10));
        root.add(topPanel);

        JScrollPane scroll = BasePanel.createScroll(root);
        add(scroll,BorderLayout.CENTER);


    }
}
