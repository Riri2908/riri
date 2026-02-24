package riri.components.page;

import riri.components.sidebar.SideBar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TopBar extends JPanel {
    private final JLabel titleName;
    private final JLabel date;
    private final Locale locale=Locale.of("vi","VN");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE', 'dd' tháng 'MM', 'yyy", locale);

    public TopBar(String title) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, SideBar.SIDEBAR_HEIGHT-5));
        setMaximumSize(new Dimension(Integer.MAX_VALUE,SideBar.SIDEBAR_HEIGHT-5));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1,0,1,0,new Color(188, 188, 188)));

        titleName=BasePanel.createTitle(title,"Segue UI",Font.PLAIN,25,Color.BLACK);
        titleName.setBorder(new EmptyBorder(0,SideBar.PADDING_LEFT,0,0));

        date=BasePanel.createTitle("","Arial",Font.BOLD,14,Color.BLACK);
        date.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel timePanel=new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel,BoxLayout.Y_AXIS));
        timePanel.setBackground(Color.WHITE);
        timePanel.setBorder(new EmptyBorder(12,0,0,20));

        JLabel today=BasePanel.createTitle("Hôm nay","Arial",Font.PLAIN,14,new Color(112, 112, 112));
        today.setAlignmentX(Component.RIGHT_ALIGNMENT);
        timePanel.add(today);
        timePanel.add(Box.createVerticalStrut(SideBar.MENU_PADDING_GAP));
        timePanel.add(date);

        add(timePanel,BorderLayout.EAST);
        add(titleName,BorderLayout.WEST);

        startClock();
    }

    public void setTitle(String title) {    
        titleName.setText(title);
    }

    private void updateTime() {
        LocalDateTime now = LocalDateTime.now();
        date.setText(now.format(formatter));
    }

    public void startClock() {
        updateTime();
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();
    }


}
