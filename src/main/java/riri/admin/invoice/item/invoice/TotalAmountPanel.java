package riri.admin.invoice.item.invoice;

import riri.admin.invoice.item.invoicelist.ListInvoiceTable;
import riri.components.BorderPanel;
import riri.components.page.BasePanel;
import riri.model.Book;
import riri.model.Customer;
import riri.model.Invoice;
import riri.model.InvoiceDetail;
import riri.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TotalAmountPanel extends JPanel {
    public Map<Integer,Invoice> invoices = AppContext.INVOICE_SERVICE.getAll();

    public ImageIcon icon = new ImageIcon( BasePanel.createImageLogo(getClass(),"invoice/pay",16,16));

    public ListPanel listPanel;
    public InformationPanel informationPanel;
    public ListInvoiceTable  listInvoiceTable;

    public JLabel totalQuantityLabel;
    public JLabel totalBookLabel;
    public JLabel totalTempLabel;
    public JLabel voucherLabel;
    public JLabel totalVoucherLabel;
    public JLabel totalAmountLabel;

    public double totalAmount = 0;

    public TotalAmountPanel(ListPanel listPanel, InformationPanel informationPanel, ListInvoiceTable  listInvoiceTable) {

        this.listPanel = listPanel;
        this.informationPanel = informationPanel;
        this.listInvoiceTable = listInvoiceTable;

        setOpaque(false);
        setLayout(new BorderLayout());

        BorderPanel borderPanel = new BorderPanel();
        borderPanel.setLayout(new BoxLayout(borderPanel,BoxLayout.Y_AXIS));
        borderPanel.setBorder(new EmptyBorder(10,20,10,20));
        borderPanel.setPreferredSize(new Dimension(370,330));

        JLabel title = BasePanel.createTitle("Tổng kết đơn hàng","Segoe UI",Font.BOLD, 18, new Color(25, 25, 25));
        title.setBorder(new EmptyBorder(0,0,0,50));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        borderPanel.add(title);

        this.totalQuantityLabel = BasePanel.createTitle("0","Segoe UI Semibold",Font.PLAIN, 15, new Color(106, 106, 106));
        borderPanel.add(Box.createVerticalStrut(20));
        borderPanel.add(item(
                BasePanel.createTitle("Số lượng sản phẩm:","Segoe UI Semibold",Font.PLAIN, 15, new Color(140, 140, 140)),
                this.totalQuantityLabel));
        borderPanel.add(Box.createVerticalStrut(10));

        this.totalBookLabel = BasePanel.createTitle("0","Segoe UI Semibold",Font.PLAIN, 15, new Color(106, 106, 106));
        borderPanel.add(item(
                BasePanel.createTitle("Tổng loại sách:","Segoe UI Semibold",Font.PLAIN, 15, new Color(140, 140, 140)),
                this.totalBookLabel));
        borderPanel.add(Box.createVerticalStrut(10));

        borderPanel.add(line());
        borderPanel.add(Box.createVerticalStrut(10));

        this.totalTempLabel = BasePanel.createTitle("0 đ","Segoe UI Semibold",Font.PLAIN, 15, new Color(106, 106, 106));
        borderPanel.add(item(
                BasePanel.createTitle("Tạm tính:","Segoe UI Semibold",Font.PLAIN, 15, new Color(140, 140, 140)),
                this.totalTempLabel));
        borderPanel.add(Box.createVerticalStrut(10));

        this.voucherLabel = BasePanel.createTitle("% Chiếc khấu (0%)","Segoe UI",Font.PLAIN, 15, new Color(5, 181, 14));
        this.totalVoucherLabel = BasePanel.createTitle("-0 đ","Segoe UI",Font.PLAIN, 15,new Color(5, 181, 14));
        borderPanel.add(item(this.voucherLabel,this.totalVoucherLabel));
        borderPanel.add(Box.createVerticalStrut(10));

        borderPanel.add(line());
        borderPanel.add(Box.createVerticalStrut(10));

        this.totalAmountLabel = BasePanel.createTitle("0 đ","Arial",Font.BOLD, 19, new Color(0, 58, 255));
        borderPanel.add(item(
                BasePanel.createTitle("Thành tiền:","Segoe UI",Font.BOLD, 17, new Color(0, 0, 0)),
                this.totalAmountLabel));
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

    public JPanel line(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(188, 188, 188)));
        panel.setOpaque(false);
        return panel;
    }

    public BorderPanel button(){

        BorderPanel button = new BorderPanel(20,new Color(0, 165, 62),0,0,null,0);
        JLabel label = BasePanel.createTitle("Hoàn tất thanh toán","Segoe UI Semibold",Font.PLAIN, 15, new Color(255, 255, 255));
        label.setBorder(new EmptyBorder(5,0,5,0));
        label.setIcon(icon);
        label.setIconTextGap(5);
        label.setHorizontalAlignment(JLabel.CENTER);
        button.add(label);

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (Objects.equals(informationPanel.customerBox.getComboBox().getSelectedItem(), "--Chọn khách hàng--")) {

                    informationPanel.customerPanel.setBorder(new Color(255,0,0),2);

                    return;
                }

                JTable table = listPanel.tablePanel.getTable();

                if (table.getRowCount() == 0) return;

                int customerId = informationPanel.customerBox.getComboBox().getSelectedIndex();

                Customer customer = null;

                if (Objects.equals(informationPanel.customerBox.getComboBox().getSelectedItem(), "--Thêm khách hàng mới--")) {

                    customer = AppContext.CUSTOMER_SERVICE.add(new Customer(
                            0,
                            informationPanel.customerField.getTextField(),
                            informationPanel.phoneField.getTextField(),
                            "Không có",
                            informationPanel.customerTypeBox.getComboBox().getSelectedIndex()+1,
                            1,
                            0
                            )
                    );
                    customerId = customer.getId();
                }

                int voucher =  informationPanel.voucher.getValue();

                int employeeId = 1; // nữa cần phải sửa dúng với account

                Map<Integer,InvoiceDetail> detailMap = new HashMap<>();

                for (int i = 0; i < table.getRowCount(); i++) {

                    Object[] row = listPanel.tablePanel.getRowData(i);

                    int bookId = (Integer) row[0];
                    double tmpPrice = (Double) row[2];
                    double price = tmpPrice*(1- (double) voucher /100);
                    int quantity = (Integer) row[3];

                    InvoiceDetail detail = new InvoiceDetail(0, 0, bookId, quantity, price);

                    detailMap.put(i, detail);
                }

                try {

                    Invoice invoice = AppContext.INVOICE_SERVICE.addInvoice(customerId, employeeId, detailMap);

                    if (Objects.equals(informationPanel.customerBox.getComboBox().getSelectedItem(), "--Thêm khách hàng mới--")) {

                        assert customer != null;
                        customer.setTotalPrice(invoice.getTotalAmount());
                        AppContext.CUSTOMER_SERVICE.update(customer);

                    }else {
                        Customer updateCustomer = AppContext.CUSTOMER_SERVICE.findById(customerId);
                        int order = updateCustomer.getTotalOrders() + 1;
                        updateCustomer.setTotalOrders(order);
                        double totalPrice = invoice.getTotalAmount()+updateCustomer.getTotalPrice();
                        updateCustomer.setTotalPrice(totalPrice);
                        AppContext.CUSTOMER_SERVICE.update(updateCustomer);
                    }

                    JOptionPane.showMessageDialog(TotalAmountPanel.this, "Thanh toán thành công!\nMã hóa đơn: " + invoice.getId());

                    listPanel.tableClear();
                    updateTotals();

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(TotalAmountPanel.this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

                informationPanel.loadDataCustomer();
                informationPanel.customerCard.show(informationPanel.customerPanel,"customerBox");
                listInvoiceTable.updateData();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 138, 52));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 165, 62));
            }
        });

        return button;
    }

    public void updateTotals() {
        int totalQuantity = 0;
        int totalBooks = listPanel.tablePanel.getTable().getRowCount();
        double totalTemp = 0;
        double voucher = informationPanel.voucher.getValue();
        double totalVoucher =0;


        JTable table = listPanel.tablePanel.getTable();
        for (int i = 0; i < table.getRowCount(); i++) {
            int quantity = (int) table.getValueAt(i, 2);
            double price = (double) table.getValueAt(i, 1);
            totalQuantity += quantity;

            totalTemp += price * quantity;
        }

        totalQuantityLabel.setText(String.valueOf(totalQuantity));

        totalBookLabel.setText(String.valueOf(totalBooks));

        totalTempLabel.setText(String.format("%,.0f đ", totalTemp));

        voucherLabel.setText("% Chiếc khấu ("+voucher+"%)");

        totalVoucher = totalTemp*(voucher /100);
        totalAmount = totalTemp-totalVoucher;

        totalVoucherLabel.setText(String.format("-%,.0f đ",totalVoucher));

        totalAmountLabel.setText(String.format("%,.0f đ", totalAmount));
    }

}
