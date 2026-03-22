package riri.util;

import riri.dao.InvoiceDetailDAO;
import riri.service.*;

/**
 * AppContext là nơi giữ toàn bộ Service dùng chung cho toàn hệ thống.
 * UI chỉ được phép gọi Service thông qua class này.
 */
public final class AppContext {

    public static final BookService BOOK_SERVICE = new BookService();
    public static final EmployeeService EMPLOYEE_SERVICE = new EmployeeService();
    public static final TransactionService TRANSACTION_SERVICE = new TransactionService();
    public static final InvoiceService INVOICE_SERVICE = new InvoiceService();
    public static final CustomerService CUSTOMER_SERVICE = new CustomerService();

    private AppContext() {
        throw new UnsupportedOperationException("Cannot instantiate AppContext");
    }
}