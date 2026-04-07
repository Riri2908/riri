package riri.admin.store.action;

import riri.model.Book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookListener {

    public static class Add implements ActionListener {
        private final Runnable onSuccess;

        public Add(Runnable onSuccess) {
            this.onSuccess = onSuccess;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new AddBook(onSuccess);
        }
    }

    public static class Edit implements ActionListener {
        private final Book book;
        private final Runnable onSuccess;

        public Edit(Book book, Runnable onSuccess) {
            this.book = book;
            this.onSuccess = onSuccess;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new EditBook(book, onSuccess);
        }
    }

    public static class Delete implements ActionListener {
        private final Book book;
        private final Runnable onSuccess;

        public Delete(Book book, Runnable onSuccess) {
            this.book = book;
            this.onSuccess = onSuccess;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new DeleteBook(book, onSuccess);
        }
    }
}