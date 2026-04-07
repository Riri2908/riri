package riri.admin.customers.controller;

import riri.admin.customers.CustomerPage;
import riri.admin.customers.item.CustomerForm;
import riri.admin.customers.item.StatCardPanel;
import riri.admin.customers.item.TableCustomer;
import riri.admin.customers.item.TitleCustomerPanel;
import riri.model.Customer;
import riri.model.CustomerType;
import riri.util.AppContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerController {
    public Map<Integer,Customer> customers = AppContext.CUSTOMER_SERVICE.getAll();

    public FormMode formMode = FormMode.ADD;
    public StatCardPanel statCardPanel;
    public CustomerPage customerPage;
    public CustomerForm customerForm;
    public TableCustomer tableCustomer;
    public TitleCustomerPanel titleCustomer;
    private final KeyEventDispatcher focusBlocker = e -> {
        // Khi infomationPanel đang hiện, chặn tất cả phím từ component khác
        if (customerForm.isVisible()) {
            Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

            // Nếu focus không nằm trong infomationPanel thì chặn
            if (focused == null || !SwingUtilities.isDescendingFrom(focused, customerForm)) {
                customerForm.requestFocusInWindow();
                return true;
            }
        }
        return false;
    };;

    public CustomerController(CustomerPage customerPage, StatCardPanel statCardPanel,CustomerForm customerForm, TableCustomer tableCustomer, TitleCustomerPanel titleCustomerPanel) {
        this.statCardPanel = statCardPanel;
        this.customerPage = customerPage;
        this.customerForm = customerForm;
        this.tableCustomer = tableCustomer;
        this.titleCustomer = titleCustomerPanel;

        initEvent();
        loadData();
    }

    //Nơi chứa Event
    private void initEvent(){
        initFormCustomerEvent();
        initTableCustomerEvent();
    }

    private void loadData(){
        customerPage.layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = customerPage.layeredPane.getWidth();
                int h = customerPage.layeredPane.getHeight();
                customerPage.mainPanel.setBounds(0, 0, w, h);

                customerPage.layeredPane.revalidate();
            }
        });

        loadCustomerTypes();
        tableCustomer.setData(customers);
        initTitleCustomerEvent();
    }


    private void initFormCustomerEvent(){

        //Kéo thả
        Point clickPoint = new Point();

        customerForm.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                clickPoint.setLocation(e.getPoint());
                customerPage.layeredPane.moveToFront(customerForm);
            }
        });

        customerForm.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = customerForm.getX() + e.getX() - clickPoint.x;
                int y = customerForm.getY() + e.getY() - clickPoint.y;
                customerForm.setLocation(x, y);
            }
        });

        //Nút EXIT
        customerForm.BUTTON_EXIT_CLOSE.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hideForm();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                customerForm.BUTTON_EXIT_CLOSE.setBackground(new Color(182, 182, 182));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                customerForm.BUTTON_EXIT_CLOSE.setBackground(new Color(255, 255, 255));
            }
        });

        //Nút Hủy
        customerForm.BUTTON_CANCEL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hideForm();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                customerForm.BUTTON_CANCEL.setBackground(new Color(182, 182, 182));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                customerForm.BUTTON_CANCEL.setBackground(new Color(255, 255, 255));
            }
        });

        //Nút hoàn tất
        customerForm.BUTTON_COMPLETE.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(formMode == FormMode.ADD){
                    addCustomer();
                    statCardPanel.updateQuantity();
                }
                else if(formMode == FormMode.EDIT){
                    updateCustomer();
                }

                customerPage.invoicePage.informationPanel.loadDataCustomer();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                customerForm.BUTTON_COMPLETE.setBackground(new Color(46, 110, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                customerForm.BUTTON_COMPLETE.setBackground(new Color(66, 125, 255));
            }
        });
    }

    public Customer selectedCustomer = null;

    private void initTableCustomerEvent(){
        tableCustomer.tablePanel.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = tableCustomer.tablePanel.getTable();

                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                Object[] model = tableCustomer.tablePanel.getRowData(row);

                if(row<0) return;
                //Logic nút EDIT và REMOVE
                if(col == 7){
                    Rectangle rect = table.getCellRect(row,col,false);

                    int clickX = e.getX() - rect.x;
                    int width = rect.width;
                    if(clickX < width/2){
                        formMode = FormMode.EDIT;

                        selectedCustomer = AppContext.CUSTOMER_SERVICE.findById((int)model[0]);
                        customerForm.infomationTitle.setText("Sửa thông tin khách hàng");
                        customerForm.showPlaceholder(selectedCustomer);
                        showForm();
                    }
                    else if(clickX > width/2){
                        tableCustomer.tablePanel.getModel().removeRow(row);
                        AppContext.CUSTOMER_SERVICE.delete((int)model[0]);
                        statCardPanel.updateQuantity();
                    }
                }
            }
        });
    }

    private void addCustomer(){
        AppContext.CUSTOMER_SERVICE.add(new Customer(
                0,
                customerForm.nameCustomerField.getTextField(),
                customerForm.phoneField.getTextField(),
                customerForm.emailField.getTextField(),
                customerForm.typeCustomerField.getComboBox().getSelectedIndex()+1,
                AppContext.CUSTOMER_SERVICE.parseIntSafe(customerForm.totalOrderField.getTextField()),
                AppContext.CUSTOMER_SERVICE.parseDoubleSafe(customerForm.totalPriceField.getTextField()),
                LocalDate.now(),
                customerForm.noteField.getTextField()
        ));

        hideForm();
        tableCustomer.setData(customers);
    }

    private void updateCustomer(){
        AppContext.CUSTOMER_SERVICE.update(new Customer(
                selectedCustomer.getId(),
                customerForm.nameCustomerField.getTextField(),
                customerForm.phoneField.getTextField(),
                customerForm.emailField.getTextField(),
                customerForm.typeCustomerField.getComboBox().getSelectedIndex()+1,
                AppContext.CUSTOMER_SERVICE.parseIntSafe(customerForm.totalOrderField.getTextField()),
                AppContext.CUSTOMER_SERVICE.parseDoubleSafe(customerForm.totalPriceField.getTextField()),
                LocalDate.now(),
                customerForm.noteField.getTextField()
        ));

        hideForm();
        tableCustomer.setData(customers);
    }

    private void initTitleCustomerEvent(){

        titleCustomer.BUTTON_ADD.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                titleCustomer.BUTTON_ADD.setBackground(new Color(0, 91, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                titleCustomer.BUTTON_ADD.setBackground(new Color(51, 113, 250));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                formMode = FormMode.ADD;
                customerForm.infomationTitle.setText("Thêm khách hàng mới");
                showForm();
                customerForm.showPlaceholder();
                customerForm.totalPriceField.showPlaceholder("0",Color.BLACK);
                customerForm.totalOrderField.showPlaceholder("0",Color.BLACK);
            }
        });

    }

    private void updateData(){

    }

    private void loadCustomerTypes(){
        Map<Integer, CustomerType> customerTypeMap = AppContext.CUSTOMER_TYPE_SERVICE.getAll();

        List<CustomerType> listCustomerType = new ArrayList<>(customerTypeMap.values());

        for(CustomerType customerType : listCustomerType){
            customerForm.typeCustomerField.addItem(customerType.getName());
        }
    }

    public void showForm(){
        customerForm.setVisible(true);
        customerPage.layeredPane.moveToFront(customerForm);
        customerForm.requestFocusInWindow();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(focusBlocker);
    }

    public void hideForm() {
        customerForm.setVisible(false);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(focusBlocker);
    }

}
