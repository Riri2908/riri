package riri.admin.management.inventory;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.sidebar.SideBarItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToggleButton extends BorderPanel {
    private static final int WIDTH_BUTTON = 150;
    private static final int HEIGHT_BUTTON = 40;

    private boolean active=false;
    private static ToggleButton activeItem = null;
    private final ImageIcon iconOn;
    private final ImageIcon iconOff;
    private final String nameButton;

    public static int getHEIGHT_BUTTON() {
        return HEIGHT_BUTTON;
    }

    private final JLabel title;
    public ToggleButton(String text, String logoName){
        super(16,new Color(241, 242, 244),0,0,Color.BLACK,0);

        this.iconOn = new ImageIcon(BasePanel.createImageLogo(getClass(),"management/toggle_"+logoName+"_on",20,20));
        this.iconOff = new ImageIcon(BasePanel.createImageLogo(getClass(),"management/toggle_"+logoName+"_off",20,20));
        this.nameButton = logoName;
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(WIDTH_BUTTON, HEIGHT_BUTTON));
        setBorder(new EmptyBorder(5,15,5,0));

        this.title=BasePanel.createTitle(text,"Arial",Font.PLAIN,17,new Color(94, 94, 94));
        this.title.setIcon(iconOff);
        this.title.setIconTextGap(10);

        add(title, BorderLayout.CENTER);

        if (logoName.equals("import")) {
            if (activeItem != null) {
                activeItem.setActive(false);
            }
            activeItem = this;
            setActive(true);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(activeItem!=null){
                    activeItem.setActive(false);
                }
                activeItem = ToggleButton.this;
                activeItem.setActive(true);

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!active) setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!active) setBackground(new Color(241, 242, 244));
            }
        });
    }
    public void setActive(boolean active){
        this.active=active;
        if(active){
            if(nameButton.equals("import")){
                setBackground(new Color(0, 165, 62));
            }else if (nameButton.equals("export")){
                setBackground(new Color(255, 0, 0));
            }
        }else{
            setBackground(new Color(241, 242, 244));
        }

        title.setIcon(active ? iconOn : iconOff);
        title.setForeground(active ? Color.WHITE : new Color(94, 94, 94));
    }
}
