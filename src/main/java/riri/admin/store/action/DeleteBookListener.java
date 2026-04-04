package riri.admin.store.action;

import riri.model.Book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteBookListener implements ActionListener {

    private final Book book;
    private final Runnable onSuccess;

    public DeleteBookListener(Book book, Runnable onSuccess) {
        this.book = book;
        this.onSuccess = onSuccess;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new DeleteBook(book, onSuccess);
    }
}