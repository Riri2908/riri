package riri.admin.invoice.page;

import riri.components.BorderPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TotalAmountPanel extends JPanel {
    public ImageIcon icon = new ImageIcon( BasePanel.createImageLogo(getClass(),"invoice/pay",16,16));

    public TotalAmountPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        BorderPanel borderPanel = new BorderPanel();
        borderPanel.setLayout(new BoxLayout(borderPanel,BoxLayout.Y_AXIS));
        borderPanel.setBorder(new EmptyBorder(10,20,10,20));
        borderPanel.setPreferredSize(new Dimension(370,310));

        JLabel title = BasePanel.createTitle("Tổng kết đơn hàng","Segoe UI",Font.BOLD, 18, new Color(25, 25, 25));
        title.setBorder(new EmptyBorder(0,0,0,50));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        borderPanel.add(title);

        borderPanel.add(Box.createVerticalStrut(20));
        borderPanel.add(
                item(
                        BasePanel.createTitle("Số lượng sản phẩm:","Segoe UI Semibold",Font.PLAIN, 15, new Color(140, 140, 140)),
                        BasePanel.createTitle("0","Segoe UI Semibold",Font.PLAIN, 15, new Color(106, 106, 106))
                )
        );
        borderPanel.add(Box.createVerticalStrut(10));
        borderPanel.add(
                item(
                        BasePanel.createTitle("Tổng loại sách:","Segoe UI Semibold",Font.PLAIN, 15, new Color(140, 140, 140)),
                        BasePanel.createTitle("0","Segoe UI Semibold",Font.PLAIN, 15, new Color(106, 106, 106))
                )
        );
        borderPanel.add(Box.createVerticalStrut(10));
        borderPanel.add(
                item(
                        BasePanel.createTitle("Số lượng sản phẩm:","Segoe UI Semibold",Font.PLAIN, 15, new Color(140, 140, 140)),
                        BasePanel.createTitle("0","Segoe UI Semibold",Font.PLAIN, 15, new Color(106, 106, 106))
                )
        );
        borderPanel.add(Box.createVerticalStrut(10));
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(188, 188, 188)));
        panel.setOpaque(false);
        borderPanel.add(panel);
        borderPanel.add(Box.createVerticalStrut(10));
        borderPanel.add(
                item(
                        BasePanel.createTitle("Tạm tính:","Segoe UI Semibold",Font.PLAIN, 15, new Color(140, 140, 140)),
                        BasePanel.createTitle("0 đ","Segoe UI Semibold",Font.PLAIN, 15, new Color(106, 106, 106))
                )
        );
        borderPanel.add(Box.createVerticalStrut(10));
        borderPanel.add(
                item(
                        BasePanel.createTitle("Thành tiền:","Segoe UI",Font.BOLD, 17, new Color(0, 0, 0)),
                        BasePanel.createTitle("0 đ","Arial",Font.BOLD, 19, new Color(0, 58, 255))
                )
        );
        borderPanel.add(Box.createVerticalStrut(15));

        borderPanel.add(button());
        borderPanel.add(Box.createVerticalStrut(5));

        JLabel label = BasePanel.createTitle("Hóa đơn sẽ được tạo và xuất hàng tự động","Segoe UI",Font.PLAIN, 10,  new Color(106, 106, 106));
        label.setBorder( new EmptyBorder(0,0,0,50));
        borderPanel.add(label);


        add(borderPanel,BorderLayout.NORTH);
    }
    public JPanel item(JLabel label, JLabel number){

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        panel.add(label);
        panel.add(Box.createHorizontalGlue());
        panel.add(number);

        return panel;
    }
    public BorderPanel button(){

        BorderPanel button = new BorderPanel(20,new Color(0, 165, 62),0,0,null,0);
        JLabel label = BasePanel.createTitle("Hoàn tất thanh toán","Segoe UI Semibold",Font.PLAIN, 15, new Color(255, 255, 255));
        label.setBorder(new EmptyBorder(5,0,5,0));
        label.setIcon(icon);
        label.setIconTextGap(5);
        button.add(label);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 138, 52));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 165, 62));
            }
        });

        return button;
    }

}
