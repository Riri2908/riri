package riri.components.combobox;

import riri.components.ModernScrollBarUI;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ComboBoxCustomUI extends BasicComboBoxUI {


    private int hoverIndex;

    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
    }

    @Override
    protected JButton createArrowButton() {

        JButton button = new JButton() {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                int w = getWidth();
                int h = getHeight();

                int size = 6;
                int x = w / 2;
                int y = h / 2;

                Polygon triangle = new Polygon();

                triangle.addPoint(x - size, y);
                triangle.addPoint(x, y + size);
                triangle.addPoint(x + size, y);

                g2.setColor(new Color(80,80,80));
                g2.fill(triangle);

                g2.dispose();
            }
        };

        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }

    @Override
    protected ComboPopup createPopup() {

        BasicComboPopup popup = new BasicComboPopup(comboBox);

        JList<?> list = popup.getList();

        list.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hoverIndex = list.locationToIndex(e.getPoint());
                list.repaint();
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverIndex = -1;
                list.repaint();
            }
        });

        return popup;
    }

    public static void customizeComboBoxScroll(JComboBox<?> comboBox) {

        comboBox.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

                Object child = comboBox.getAccessibleContext().getAccessibleChild(0);

                if (child instanceof JPopupMenu popup) {

                    JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);

                    scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

                    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8,0));
                }
            }

            @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            @Override public void popupMenuCanceled(PopupMenuEvent e) {}
        });
    }

}
