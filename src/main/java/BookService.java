import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class BookService {

    private static final Logger loggerInfo = LoggerFactory.getLogger("info");
    private static final Logger loggerWarn = LoggerFactory.getLogger("warn");
    private static final Logger loggerError = LoggerFactory.getLogger("error");

    private BookDAO bookDAO = new BookDAO();

    public void createBook(Author author, Book book) {
        loggerInfo.info("Начало создания книги: " + book.getTitle());
       // loggerInfo.info("Начало создания книги: " + book.getTitle());
        if (!book.getTitle().isEmpty()) {
            boolean exist = bookDAO.findByTitle(author, book.getTitle());
            if (exist) {
                loggerError.error("Такая книга уже есть!");
                System.out.println("Такая книга уже есть!");
            } else {
                    bookDAO.createBook(author, book);
                    System.out.println("Книга " + book.getTitle() + " создана");
                    loggerInfo.info("Книга " + book.getTitle() + " создана");
                }
            } else {
                loggerError.error("Назавание книги не может быть пустым!");
                System.out.println("Название книги не может быть пустым!");
            }
    }



  /*  public void createBook(Author author, Book book) {
        loggerInfo.info("Начало создания книги: " + book.getTitle());
        loggerInfo.info("Начало создания книги: " + book.getTitle());
        if (!book.getTitle().isEmpty()) {
            boolean exist = existByTitle(book);
            if (!exist) {
                loggerError.error("Такая книга уже есть!");
                System.out.println("Такая книга уже есть!");
            } else {
                bookDAO.createBook(author, book);
                System.out.println("Книга " + book.getTitle() + " создана");
                loggerInfo.info("Книга " + book.getTitle() + " создана");
            }
        } else {
            loggerError.error("Назавание книги не может быть пустым!");
            System.out.println("Название книги не может быть пустым!");
        }
    }*/


    public void deleteBook(Author author, String bookId) {
        loggerWarn.warn("Начало удаления книги по bookId: " + bookId);
        bookDAO.deleteBook(author, bookId);
        loggerWarn.warn("Книга удалена");
    }

    public void updateBook(Author author, Book book) {
        loggerWarn.warn("Начало обновления книги: " + book.getBookId());
        bookDAO.updateBook(author, book);
        loggerWarn.warn("Книга обновлена");
    }

    public Collection<Book> findBooks(Author author) {
        loggerInfo.info("Вывод списка книг: ");
        return bookDAO.findBooks(author);
    }

    public Book findBooks(Author author, String bookId) {
        loggerInfo.info("Вывод книг по bookId: " + bookId);
        return bookDAO.findBooks(author, bookId);
    }

  /*  private boolean existByTitle(Book book) {
        if (book.getTitle() != null) {
            Book exist = bookDAO.findByTitle(book.getTitle());
            if (exist != null) {
                loggerError.error("Эта книга уже есть!");
            }
            return false;
        } else {
            loggerError.error("Название книге не может быть пустым!");
        }
        return true;
    }*/
}
