package riri.components.sidebar;

import riri.components.page.ContentPanel;
import riri.components.page.TopBar;

import javax.swing.*;
import java.awt.*;

public class SideBarMain extends JPanel {

    public SideBarMain(ContentPanel contentPanel) {
        setLayout(new BorderLayout());

        SideBar sideBar = new SideBar();

        SideBarHeader header = new SideBarHeader();

        SideBarMenu menu = new SideBarMenu(contentPanel);

        SideBarAccount account = new SideBarAccount();

        sideBar.add(header, BorderLayout.NORTH);
        sideBar.add(menu, BorderLayout.CENTER);
        sideBar.add(account, BorderLayout.SOUTH);

        add(sideBar, BorderLayout.CENTER);
    }
}
