package riri.admin.home.view;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.page.ContentPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FeaturePanel extends BorderPanel {
    private final int HEIGHT = 500;

    public GridBagConstraints gbc = new GridBagConstraints();
    public ContentPanel contentPanel;

    public FeaturePanel(ContentPanel contentPanel) {
        super(0, new Color(247, 248, 249), 0, 0, Color.BLACK, 0);
        this.contentPanel = contentPanel;

        setPreferredSize(new Dimension(0, HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, HEIGHT));
        setLayout(new GridBagLayout());

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
    }
    public void addFeatureItem(Icon icon, Color iconBackground, String title, String subText, String tag, Color tagColor, Color tagBackground, int x, int y) {
        addItem(itemPanel(icon, iconBackground, title, subText, tag, tagColor, tagBackground), x, y);
    }

    public BorderPanel itemPanel(Icon icon,Color iconBackground, String title, String subText, String tag, Color tagColor,Color tagBackground) {

        final float[] hoverProgress = {0f};
        final boolean[] isHovering = {false};
        final Timer[] hoverTimer = {null};

        BorderPanel itemPanel = new BorderPanel();
        itemPanel.setBorder(new EmptyBorder(10,10,10,10));
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setBorder(new EmptyBorder(20,20,10,20));
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        BorderPanel iconPanel = BasePanel.createIconPanel(iconLabel,iconBackground);

        JPanel leftWrapper = new JPanel(new GridBagLayout());
        leftWrapper.setBorder(new EmptyBorder(0,0,30,0));
        leftWrapper.setOpaque(false);
        leftWrapper.add(iconPanel);//**//

        JLabel arrowLabel = BasePanel.createTitle("→","Arial",Font.BOLD,16,new Color(150, 150, 150));
        topPanel.add(iconPanel, BorderLayout.WEST);
        topPanel.add(arrowLabel, BorderLayout.EAST);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = BasePanel.createTitle(title,"Arial",Font.BOLD,20,Color.BLACK);
        titleLabel.setBorder(new EmptyBorder(0,10,10,10));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subLabel = BasePanel.createTitle("<html>" + subText + "<html>", "Arial",Font.PLAIN,15,new Color(100, 100, 100));
        subLabel.setBorder(new EmptyBorder(0,10,10,10));
        subLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        BorderPanel bottomPanel = new BorderPanel( 33,tagBackground,0,0,null,0);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 14, 8));

        JLabel tagLabel = BasePanel.createTitle(tag,"Arial",Font.BOLD,14,tagColor);
        bottomPanel.add(tagLabel);
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel bottomWrapper = new JPanel(new GridBagLayout());
        bottomWrapper.setBorder(new EmptyBorder(0,0,10,0));
        bottomWrapper.setOpaque(false);
        bottomWrapper.add(bottomPanel);
        bottomWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        itemPanel.add(topPanel);
        itemPanel.add(Box.createVerticalStrut(4));
        itemPanel.add(titleLabel);
        itemPanel.add(Box.createVerticalStrut(4));
        itemPanel.add(subLabel);
        itemPanel.add(bottomWrapper);

        itemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPanel.showPage(title);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isHovering[0] = true;
                startAnim();
                arrowLabel.setForeground(tagColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovering[0] = false;
                startAnim();
                arrowLabel.setForeground(new Color(150, 150, 150));
            }

            void startAnim() {
                if (hoverTimer[0] != null && hoverTimer[0].isRunning()) {
                    hoverTimer[0].stop();
                }

                Color borderDefault = new Color(220, 220, 220);

                hoverTimer[0] = new Timer(16, null);
                hoverTimer[0].addActionListener(_ -> {
                    if (isHovering[0]) {
                        hoverProgress[0] = Math.min(1f, hoverProgress[0] + 0.08f);
                    } else {
                        hoverProgress[0] = Math.max(0f, hoverProgress[0] - 0.08f);
                    }

                    Color blended = blendColor(borderDefault, tagColor, hoverProgress[0]);
                    itemPanel.setBorder(blended, 1);
                    itemPanel.repaint();

                    if (hoverProgress[0] <= 0f || hoverProgress[0] >= 1f) {
                        hoverTimer[0].stop();
                    }
                });

                hoverTimer[0].start();
            }
        });

        return itemPanel;
    }

    private Color blendColor(Color from, Color to, float t) {
        int r = (int)(from.getRed()   + (to.getRed()   - from.getRed())   * t);
        int g = (int)(from.getGreen() + (to.getGreen() - from.getGreen()) * t);
        int b = (int)(from.getBlue()  + (to.getBlue()  - from.getBlue())  * t);
        return new Color(r, g, b);
    }

    public void addItem(Component component, int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        add(component,gbc);
    }
}
