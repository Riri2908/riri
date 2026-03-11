package riri;

import riri.components.page.ContentPanel;
import riri.components.sidebar.*;

import javax.swing.*;
import java.awt.*;

public class BookStoreApp {

    void main(){
        JFrame frame = new JFrame("App");
        frame.setSize(SideBar.WIDTH, SideBar.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ContentPanel contentPanel = new ContentPanel();
        SideBarMain sideBarMain = new SideBarMain(contentPanel);

        frame.add(sideBarMain, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);

    }
}