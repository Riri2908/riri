package riri.components.page;

import riri.components.ModernScrollBarUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public abstract class BasePanel {

    public static Image createImageLogo(Class <?> clazz, String name, int width, int height) {
        ImageIcon logo=new ImageIcon(Objects.requireNonNull(clazz.getResource("/icons/"+name+".png")));
        return logo.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
    }

    public static JLabel createTitle(String text, String font, int style, int size, Color color) {
        JLabel title = new JLabel(text);
            title.setFont(new Font(font, style, size));
        title.setForeground(color);
        return title;
    }

    public static JScrollPane createScroll(JPanel panel){
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

        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }


}
