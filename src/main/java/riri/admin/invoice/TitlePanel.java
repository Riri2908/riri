package riri.admin.invoice;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitlePanel extends BorderPanel {

    private final int HEIGHT_TITLEPANEL = 35;
    private final int WEIGHT_BUTTON = 200;

    public String tilte;
    public String textButton;
    public String constructor;

    public InvoicePage titlePanel;

    public TitlePanel(String title, String textButton, String constructor, InvoicePage titlePanel) {
        super(0,new Color(247, 248, 249),0,0,null,0);
        this.tilte = title;
        this.textButton = textButton;
        this.constructor = constructor;
        this.titlePanel = titlePanel;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, HEIGHT_TITLEPANEL));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, HEIGHT_TITLEPANEL));

        add(BasePanel.createTitle(title,"Arial", Font.BOLD, 25,Color.BLACK),BorderLayout.WEST);

        add(createButton(textButton),BorderLayout.EAST);
    }

    private BorderPanel createButton(String title) {
        BorderPanel button = new BorderPanel(16,new Color(51, 113, 250),0,0,null,0);

        button.setPreferredSize(new Dimension(WEIGHT_BUTTON, HEIGHT_TITLEPANEL));
        button.setMaximumSize(new Dimension(WEIGHT_BUTTON, HEIGHT_TITLEPANEL));
        button.setLayout(new BorderLayout());
        button.setBorder(new EmptyBorder(10,0,10,0));

        JLabel titleLabel = BasePanel.createTitle(title,"Arial",Font.BOLD,14,Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        button.add(titleLabel,BorderLayout.CENTER);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                titlePanel.showPageButton(constructor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 91, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(51, 113, 250));
            }
        });
        return button;
    }





}
