package riri.admin.invoice.page;

import riri.components.BorderPanel;
import riri.components.SearchPanel;
import riri.components.combobox.ComboBoxPanel;
import riri.components.field.FieldPanel;
import riri.components.page.BasePanel;
import riri.components.spinner.SpinnerPanel;
import riri.components.table.TablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ShoppingCart extends BorderPanel {

    public GridBagConstraints gbc = new GridBagConstraints();

    public JLabel titleLabel;
    public ComboBoxPanel bookBox = new ComboBoxPanel();
    public SpinnerPanel spinner = new SpinnerPanel();
    public FieldPanel noteField = new FieldPanel("Ghi chú...");

    public ShoppingCart() {

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20,20,10,20));

        gbc.insets = new Insets(0,0,15,20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        titleLabel = BasePanel.createTitle("Thêm sách vào giỏ hàng","Segue UI",Font.BOLD, 18, new Color(25, 25, 25));
        titleLabel.setBorder(new EmptyBorder(0,0,10,0));

        addItem(titleLabel,0,0);
        addItem(BasePanel.createItem("Tìm kiếm và chọn sách",bookBox),0,1);
        addItem(BasePanel.createItem("Số lượng",spinner),1,1);
        gbc.gridwidth = 2;
        addItem(BasePanel.createItem("Ghi chú",noteField),0,2);
        gbc.gridwidth = 1;


    }
    public void addItem(Component component, int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
        add(component,gbc);
    }
}
