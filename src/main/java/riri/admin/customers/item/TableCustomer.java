package riri.admin.customers.item;

import riri.admin.customers.CustomerPage;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.components.page.SearchPanel;
import riri.components.table.CustomRenderer;
import riri.components.table.TablePanel;
import riri.model.Customer;
import riri.model.CustomerType;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class TableCustomer extends BorderPanel {
    public final int PADDING = 25;

    public CustomRenderer renderer = new CustomRenderer();
    public TablePanel tablePanel = new TablePanel();

    public CustomerPage customerPage;

    public TableCustomer(CustomerPage customerPage) {
        super(0,new Color(247, 248, 249),0,0,null,0);
        this.customerPage = customerPage;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        tablePanel.setTitle("Thông tin khách hàng");

        tablePanel.addColumn(0,"ID");
        tablePanel.addColumn(1,"KHÁCH HÀNG");
        tablePanel.addColumn(2,"LIÊN HỆ");
        tablePanel.addColumn(3,"LOẠI KHÁCH & GIẢM GIÁ");
        tablePanel.addColumn(4,"SỐ ĐƠN");
        tablePanel.addColumn(5,"TỔNG CHI TIÊU");
        tablePanel.addColumn(6,"NGÀY MUA GẦN NHẤT");
        tablePanel.addColumn(7,"GHI CHÚ");
        tablePanel.addColumn(8,"THAO TÁC");

        TableColumnModel columnModel = tablePanel.getTable().getColumnModel();
        TableColumn idColumn = columnModel.getColumn(0);

        columnModel.removeColumn(idColumn);

        tablePanel.getTable().setRowHeight(75);

        renderer.setDefaultSetting((label,row)->{

            boolean hover = row == tablePanel.getHoveredRow();

            label.setBackground(hover ? new Color(245,245,245) : Color.WHITE);

            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0,
                            hover ? new Color(245, 245, 245) : new Color(230, 230, 230)),
                    new EmptyBorder(10, PADDING+10, 10, 10)
            ));

            label.setHorizontalAlignment(JLabel.LEFT);
            label.setForeground(new Color(57,57,57));

            label.setIcon(null);
            label.setFont(new Font("Arial",Font.PLAIN,14));

            label.setOpaque(true);

            return label;
        });

        renderer.addLabel(0,(label, row)->{
            label.setFont(new Font("Arial",Font.BOLD,14));
            return label;
        });

        renderer.addLabel(1,(label,value,row)->{

            boolean hoverRow = row == tablePanel.getHoveredRow();

            Customer customer = (Customer) value;

            BorderPanel panel = new BorderPanel(0,Color.WHITE,0,0,null,0);
            panel.setBorder(new EmptyBorder(10,PADDING,10,10));

            panel.setBackground(hoverRow ? new Color(245,245,245) : Color.WHITE);

            panel.setBorder(BorderFactory.createMatteBorder(
                    0,0,1,0,
                    hoverRow ? new Color(245,245,245) : new Color(230,230,230)));

            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

            ImageIcon phoneIcon = new ImageIcon(BasePanel.createImageLogo(getClass(),"customer/phone",15,15));

            JLabel phone = BasePanel.createTitle(customer.getPhone(),"Arial",Font.PLAIN,14,new Color(112,112,112));
            phone.setIcon(phoneIcon);
            phone.setIconTextGap(5);
            phone.setAlignmentX(Component.LEFT_ALIGNMENT);
            phone.setBorder(new EmptyBorder(5,0,5,0));

            ImageIcon mailIcon = new ImageIcon(BasePanel.createImageLogo(getClass(),"customer/mail",15,15));

            JLabel mail = BasePanel.createTitle(customer.getEmail(),"Arial",Font.PLAIN,14,new Color(112,112,112));
            mail.setIcon(mailIcon);
            mail.setIconTextGap(5);
            mail.setAlignmentX(Component.LEFT_ALIGNMENT);
            mail.setBorder(new EmptyBorder(5,0,5,0));

            panel.add(phone);
            panel.add(Box.createVerticalStrut(5));
            panel.add(mail);

            return panel;
        });

        renderer.addLabel(2, (label, value, row) -> {
            boolean hoverRow = row == tablePanel.getHoveredRow();
            CustomerType type = (CustomerType) value;

            BorderPanel panel = new BorderPanel(0,Color.WHITE,0,0,null,0);

            panel.setBackground(hoverRow ? new Color(245,245,245) : Color.WHITE);

            panel.setBorder(BorderFactory.createMatteBorder(
                    0,0,1,0,
                    hoverRow ? new Color(245,245,245) : new Color(230,230,230)));

            panel.setLayout(new GridBagLayout());
            panel.setAlignmentX(SwingConstants.LEFT);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.insets = new Insets(4, 0, 4, 0);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weightx = 1.0;

            BorderPanel badgePanel = new BorderPanel(25,Color.WHITE,0,0,null,0);
            badgePanel.setAlignmentX(SwingConstants.LEFT);

            JLabel badgeLabel = BasePanel.createTitle(String.valueOf(type.getName()),"Arial",Font.BOLD,14,new Color(112,112,112));
            int typeName = type.getId();

            if (typeName==4){
                badgeLabel.setForeground(new Color(112, 34, 232));
                badgePanel.setBackground(new Color(193, 154, 255));
                badgeLabel.setIcon(new ImageIcon(BasePanel.createImageLogo(getClass(),"customer/tag_4",15,15)));
            }else if(typeName==3){
                badgeLabel.setForeground(new Color(7, 152, 18));
                badgePanel.setBackground(new Color(180, 255, 183));
                badgeLabel.setIcon(new ImageIcon(BasePanel.createImageLogo(getClass(),"customer/tag_3",15,15)));
            }else if (typeName==2) {
                badgeLabel.setForeground(new Color(37, 99, 235));
                badgePanel.setBackground(new Color(147, 180, 255));
                badgeLabel.setIcon(new ImageIcon(BasePanel.createImageLogo(getClass(),"customer/tag_2",15,15)));
            }
            else {
                badgeLabel.setForeground(new Color(32, 32, 32));
                badgePanel.setBackground(new Color(165, 165, 165));
                badgeLabel.setIcon(new ImageIcon(BasePanel.createImageLogo(getClass(),"customer/tag_1",15,15)));
            }

            badgeLabel.setBorder(new EmptyBorder(4, 4, 4, 4));
            badgeLabel.setAlignmentX(SwingConstants.LEFT);
            badgeLabel.setIconTextGap(5);

            gbc.gridy = 0;
            badgePanel.add(badgeLabel,gbc);
            panel.add(badgePanel,gbc);

            JLabel discount = BasePanel.createTitle("Giảm " + type.getDiscountRate()*100 + "%","Arial",Font.BOLD,13,new Color(39, 168, 87));
            discount.setAlignmentX(SwingConstants.LEFT);
            gbc.gridy = 1;


            panel.add(discount,gbc);

            return panel;
        });

        renderer.addLabel(3,(label, row)->{
            label.setText(label.getText()+" đơn");
            label.setFont(new Font("Arial",Font.BOLD,14));
            label.setForeground(new Color(89, 71, 228));
            return label;
        });

        renderer.addLabel(4,(label, row)->{
            label.setText(label.getText()+" đ");
            label.setFont(new Font("Arial",Font.BOLD,14));
            label.setForeground(new Color(89, 165, 62));
            return label;
        });

        renderer.addLabel(7,(label,row)->{
            boolean hoverRow = row == tablePanel.getHoveredRow();
            boolean hoverCol = hoverRow && tablePanel.getHoveredCol() == 7;

            Rectangle cellRect = tablePanel.getTable().getCellRect(row, 7, true);
            int colStart = cellRect.x;
            int colWidth = cellRect.width;
            int relX = tablePanel.getHoveredX() - colStart;

            boolean hoverEdit   = hoverCol && relX < colWidth / 2;
            boolean hoverDelete = hoverCol && relX >= colWidth / 2;

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = 0;
            gbc.insets = new Insets(4, 4, 4, 4);
            gbc.anchor = GridBagConstraints.WEST;

            BorderPanel panel =  new BorderPanel(0,new Color(255, 255, 255),0,0,null, 0);
            panel.setBackground(hoverRow ? new Color(245,245,245) : Color.WHITE);
            panel.setLayout(new GridBagLayout());

            panel.setBorder(BorderFactory.createMatteBorder(
                    0,0,1,0,
                    hoverRow ? new Color(245,245,245) : new Color(230,230,230)));

            BorderPanel buttonRemove =  new BorderPanel(14,new Color(253, 164, 164),0,0,null, 0);
            buttonRemove.setBorder(new EmptyBorder(1,1,1,1));

            ImageIcon imageRemove = new ImageIcon(BasePanel.createImageLogo(getClass(), "baseicon/remove", 17,17));
            JLabel iconRemove = new JLabel(imageRemove);
            iconRemove.setAlignmentX(Component.LEFT_ALIGNMENT);

            buttonRemove.add(iconRemove);

            BorderPanel buttonEdit =  new BorderPanel(14,new Color(253, 164, 164),0,0,null, 0);
            buttonEdit.setBorder(new EmptyBorder(1,1,1,1));

            ImageIcon imageEdit = new ImageIcon(BasePanel.createImageLogo(getClass(), "baseicon/edit", 17,17));
            JLabel iconEdit = new JLabel(imageEdit);
            iconEdit.setAlignmentX(Component.LEFT_ALIGNMENT);

            buttonEdit.add(iconEdit);

            if(hoverEdit){
                buttonEdit.setBackground(new Color(164, 203, 253));
                buttonRemove.setBackground(new Color(245,245,245));
            }
            else {
                buttonEdit.setBackground(new Color(255, 255, 255));
            }

            if(hoverDelete){
                buttonRemove.setBackground(new Color(253, 164, 164));
                buttonEdit.setBackground(new Color(245,245,245));
            }
            else {
                buttonRemove.setBackground(new Color(255, 255, 255));
            }

            gbc.gridx = 0;
            panel.add(buttonEdit,gbc);

            gbc.gridx = 1;
            panel.add(buttonRemove,gbc);

            return panel;
        });

        renderer.setHeaderSetting(label -> {
            label.setBorder(new EmptyBorder(10,0,10,30 ));
            label.setBackground(new Color(247, 248, 249));
            label.setForeground(new Color(110, 110, 110));
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            return label;
        });
        renderer.applyHeader(tablePanel.getTable());
        tablePanel.setRenderer(renderer);

        add(new SearchPanel(tablePanel,"Tìm kiếm theo tên, sđt hoặc email...."));
        add(Box.createVerticalStrut(15));

        add(tablePanel);
    }

    public void setData(Map<Integer,Customer> customers){
        List<Customer> customer = new ArrayList<>(customers.values());
        Collections.reverse(customer);

        tablePanel.getModel().setRowCount(0);

        for(Customer c : customer){

            CustomerType customerType =AppContext.CUSTOMER_TYPE_SERVICE.findById(c.getIdType());

            tablePanel.addRow(new Object[]{
                    c.getId(), c.getName(), c,
                    customerType, c.getTotalOrders(),
                    c.getTotalPrice(), c.getRecentDate(), c.getNote(), null
            });
        }
    }

}
