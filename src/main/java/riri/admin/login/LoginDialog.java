package riri.admin.login;

import riri.components.BorderPanel;
import riri.components.field.FieldPanel;
import riri.components.page.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JDialog {

    private boolean loginSuccess = false;

    private final FieldPanel usernameField;
    private final FieldPanel passwordField;
    private final JCheckBox chkRemember;
    private final JLabel lblForgot ;
    private final JPanel rowRemember;
    private final BorderPanel BUTTON_LOGIN;
    private final BorderPanel demoBox;

    private static final Color BLUE     = new Color(11, 86, 255);
    private static final Color BLUE_BUTTON = new Color(0, 13, 255);
    private static final Color BG_PAGE  = new Color(240, 244, 248);
    private static final Color BG_CARD  = Color.WHITE;
    private static final Color BG_DEMO  = new Color(240, 246, 255);
    private static final Color TEXT_MAIN= new Color(17,  17,  17);
    private static final Color TEXT_SUB = new Color(102, 102, 102);

    public LoginDialog() {
        setTitle("Đăng nhập - BookStore");
        setSize(460, 620);
        setModal(true);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setResizable(false);

        JPanel page = new JPanel(new GridBagLayout());
        page.setBackground(BG_PAGE);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(36, 32, 28, 32));
        card.setPreferredSize(new Dimension(400, 530));

        JLabel lblTitle = BasePanel.createTitle("Đăng nhập", "Segoe UI", Font.BOLD, 24, TEXT_MAIN);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = BasePanel.createTitle("Chào mừng trở lại! Vui lòng đăng nhập để tiếp tục.", "Segoe UI", Font.PLAIN, 13, TEXT_SUB);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        usernameField = new FieldPanel("Nhập tên đăng nhập");
        passwordField = new FieldPanel("Nhập mật khẩu", true);
        fillDemoAccount();


        JPanel userItem  = buildItem("Tên đăng nhập", usernameField);
        JPanel passItem  = buildItem("Mật khẩu", passwordField);

        chkRemember = new JCheckBox("Ghi nhớ đăng nhập");
        chkRemember.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkRemember.setForeground(new Color(68, 68, 68));
        chkRemember.setOpaque(false);
        chkRemember.setSelected(true);

        lblForgot = new JLabel("Quên mật khẩu?");
        lblForgot.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblForgot.setForeground(BLUE);
        lblForgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rowRemember = new JPanel(new BorderLayout());
        rowRemember.setOpaque(false);
        rowRemember.setAlignmentX(Component.LEFT_ALIGNMENT);
        rowRemember.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        rowRemember.add(chkRemember, BorderLayout.WEST);
        rowRemember.add(lblForgot,   BorderLayout.EAST);

        BUTTON_LOGIN = createLoginButton();
        BUTTON_LOGIN.setAlignmentX(Component.LEFT_ALIGNMENT);
        BUTTON_LOGIN.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        BUTTON_LOGIN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleLogin();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                BUTTON_LOGIN.setBackground(BLUE_BUTTON);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                BUTTON_LOGIN.setBackground(BLUE);
            }
        });

        demoBox = buildDemoBox();
        demoBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(6));
        card.add(lblSub);
        card.add(Box.createVerticalStrut(24));
        card.add(userItem);
        card.add(Box.createVerticalStrut(14));
        card.add(passItem);
        card.add(Box.createVerticalStrut(12));
        card.add(rowRemember);
        card.add(Box.createVerticalStrut(18));
        card.add(BUTTON_LOGIN);
        card.add(Box.createVerticalStrut(16));
        card.add(demoBox);
        card.add(Box.createVerticalGlue());


        page.add(card);
        setContentPane(page);
    }

    private JPanel buildItem(String label, JComponent field) {
        JPanel item = new JPanel();
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
        item.setOpaque(false);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = BasePanel.createTitle(label, "Segoe UI", Font.BOLD, 13, new Color(51, 51, 51));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        field.setPreferredSize(new Dimension(0, 46));

        item.add(lbl);
        item.add(Box.createVerticalStrut(6));
        item.add(field);
        return item;
    }

    private BorderPanel createLoginButton() {
        BorderPanel btn = new BorderPanel(16,new Color(0, 0, 255),0,0,null,0);
        btn.setLayout(new BorderLayout());
        JLabel label = BasePanel.createTitle("Đăng nhập","Arial", Font.BOLD, 20, new Color(243, 243, 243));
        label.setBorder(new EmptyBorder(10,10,10,10));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        btn.add(label,BorderLayout.CENTER);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private BorderPanel buildDemoBox() {
        BorderPanel box = new BorderPanel(16,BG_DEMO,0,0,new Color(191, 219, 254),1);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(new EmptyBorder(12, 16, 12, 16));
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        Color DEMO = new Color(37, 99, 235);
        JLabel title = BasePanel.createTitle("Tài khoản demo:", "Segoe UI", Font.BOLD, 13, DEMO);
        JLabel u = BasePanel.createTitle("• Tên đăng nhập: admin",  "Segoe UI", Font.PLAIN, 13, new Color(68,68,68));
        JLabel p = BasePanel.createTitle("• Mật khẩu: admin123",    "Segoe UI", Font.PLAIN, 13, new Color(68,68,68));

        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        u.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(title);
        box.add(Box.createVerticalStrut(5));
        box.add(u);
        box.add(Box.createVerticalStrut(3));
        box.add(p);
        return box;
    }


    private void handleLogin() {
        String username = usernameField.getTextField();
        String password = "";

        if (passwordField.getField() instanceof JPasswordField pf) {
            password = new String(pf.getPassword());
        }

        if (username.equals("admin") && password.equals("admin123")) {
            loginSuccess = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Sai tài khoản hoặc mật khẩu!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    private void fillDemoAccount() {
        usernameField.setTextField("admin");
        passwordField.setTextField("admin123");
    }
}