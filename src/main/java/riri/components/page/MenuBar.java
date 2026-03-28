package riri.components.page;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MenuBar extends JMenuBar {

    public MenuBar(){
        setBorder(new EmptyBorder(0,10,0,0));

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");
        add(fileMenu);
        add(editMenu);
        add(helpMenu);
    }
}
