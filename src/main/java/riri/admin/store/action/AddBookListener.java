package riri.admin.store.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookListener implements ActionListener {

    private final Runnable onSuccess;

    public AddBookListener(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new AddBook(onSuccess);
    }
}