import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class BookDAO {
    private static final Logger loggerError = LoggerFactory.getLogger("error");

    private int size = 0;
    Book[] books;

    public void createBook(Author author, Book book) {
        books = author.getBooks();
        if (size == books.length) {
            increaseCapacity();
        }
        book.setBookId(generateId(UUID.randomUUID().toString()));
        books[size++] = book;
        author.setBooks(books);
    }

    public void updateBook(Author author, Book book) {
        if (StringUtils.isNotBlank(book.getBookId())) {
            books = author.getBooks();
            Book current = getBookById(book.getBookId());
            if (current == null) {
                throw new RuntimeException("Книга не существует!");
            }
            try {
                BeanUtils.copyProperties(current, book);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            } catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
            }
        }
        else {
            throw new RuntimeException("Книга не существует!");
        }
    }

    public void deleteBook(Author author, String bookId) {
        if (StringUtils.isNotBlank(bookId)) {
            books = author.getBooks();
            Book current = getBookById(bookId);
            if (current == null) {
                throw new RuntimeException("Книга не существует!");
            }
            for (int i = 0; i < books.length; i++) {
                if (((Book) books[i]).getBookId().equals((bookId))) {
                    books[i] = null;
                    break;
                }
            }
            Book[] temp = new Book[books.length];
            for (int i = 0, j = 0; i < books.length; i++) {
                if (books[i] != null) {
                    temp[j] = books[i];
                    j++;
                }
            }
            books = Arrays.copyOf(temp, temp.length);
            author.setBooks(books);
        }
        else {
            throw new RuntimeException("Книга не существует!");
        }
    }

    public Collection<Book> findBooks(Author author) {
        books = author.getBooks();
        return Arrays.stream(books).map(book -> ((Book) book)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Book findBooks(Author author, String bookId) {
        if (StringUtils.isNotBlank(bookId)) {
            books = author.getBooks();
            Book current = getBookById(bookId);
            if (current == null) {
                throw new RuntimeException("Книга не существует!");
            }
            return current;
        }
        else {
            throw new RuntimeException("Книга не существует!");
        }
    }

    public boolean findByTitle(Author author, String title) {
        books = author.getBooks();
        return Arrays.stream(books).filter(Objects::nonNull).anyMatch(book -> book.getTitle().equals(title));
    }

 /*   public Book findByTitle(String title) {
        return Arrays.stream(books).filter(book -> book.getTitle().equals(title)).findFirst().orElse(null);
    }*/


    private Book getBookById(String bookId) {
        return (Book) Arrays.stream(books)
            .filter(book -> ((Book) book).getBookId().equals(bookId))
            .findAny()
            .orElse(null);
    }

    private String generateId(String bookId) {
        if (Arrays.stream(books)
            .filter(Objects::nonNull)
            .anyMatch(book -> Objects.equals(((Book) book).getBookId(), bookId))) {
            return generateId(UUID.randomUUID().toString());
        }
        return bookId;
    }

    private void increaseCapacity() {
        int IncreasedCapacity = books.length * 2;
        books = Arrays.copyOf(books, IncreasedCapacity);
    }
}
