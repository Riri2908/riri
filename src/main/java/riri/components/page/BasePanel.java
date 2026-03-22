package riri.components.page;

import riri.components.BorderPanel;
import riri.components.ModernScrollBarUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public abstract class BasePanel {

    public static Image createImageLogo(Class <?> clazz, String name, int width, int height) {
        ImageIcon logo=new ImageIcon(Objects.requireNonNull(clazz.getResource("/icons/"+name+".png")));
        return logo.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
    }

    public static JLabel createTitle(String text, String font, int style, int size, Color color) {
        JLabel title = new JLabel(text);title.setFont(new Font(font, style, size));
        title.setForeground(color);
        return title;
    }

    public static JPanel createItem(String title, Component component){
        JPanel item = new JPanel();
        item.setOpaque(false);
        item.setLayout(new BorderLayout(0,5));
        JLabel label = createTitle(title,"Segue UI",Font.BOLD, 13, new Color(71, 71, 71));
        label.setBorder(new EmptyBorder(0,5,0,0));
        item.add(label, BorderLayout.NORTH);
        item.add(component,BorderLayout.CENTER);
        return item;
    }

    public static JScrollPane createScroll(Component panel){
        JScrollPane scrollPane = new JScrollPane(panel);

        JScrollBar vBar = scrollPane.getVerticalScrollBar();
        vBar.setUI(new ModernScrollBarUI());
        vBar.setPreferredSize(new Dimension(6, 0));
        vBar.setUnitIncrement(16);
        vBar.setOpaque(false);

        JScrollBar hBar = scrollPane.getHorizontalScrollBar();
        hBar.setUI(new ModernScrollBarUI());
        hBar.setPreferredSize(new Dimension(0, 6));
        hBar.setOpaque(false);

        scrollPane.setBorder(new EmptyBorder(0,0,8,8));

        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

}
