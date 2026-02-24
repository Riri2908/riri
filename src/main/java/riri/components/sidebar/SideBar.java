package riri.components.sidebar;

import javax.swing.*;
import java.awt.*;

public class SideBar extends JPanel {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static final int SIDEBAR_WIDTH = 280;
    public static final int SIDEBAR_HEIGHT = 80;

    public static int PADDING_LEFT =25;

    public static int MENU_PADDING_TOP = 20;
    public static int MENU_PADDING_BOTTOM = 20;
    public static int MENU_PADDING_LEFT = 15;
    public static int MENU_PADDING_RIGHT = 15;

    public static int MENU_PADDING_GAP=6;

    public static final Dimension SIZE_BUTTON = new Dimension(200, 45);
    public static final Dimension MAX_SIZE_BUTTON = new Dimension(Integer.MAX_VALUE, 45);


    public SideBar(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(SideBar.SIDEBAR_WIDTH, 0));
        setMinimumSize(new Dimension(Integer.MAX_VALUE, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1 ,new Color(188, 188, 188)));
    }
}
