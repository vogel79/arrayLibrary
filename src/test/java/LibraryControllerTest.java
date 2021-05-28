import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryControllerTest {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    private static AuthorService authorService = new AuthorService();
    private static BookService bookService = new BookService();

    @BeforeAll
    public static void setUp() {
        System.out.println(ANSI_GREEN + "\nTests start: \n" + ANSI_RESET);

        for (int i = 0; i < 5; i++) {
            Author author = new Author();
            author.setFirstName("Автор" + i);
            author.setLastName("Автор" + i);
            authorService.createAuthor(author);
        }
        Assertions.assertTrue(authorService.findAuthors().size() != 0);

        Author author = new Author();
        author.setFirstName("Ольга");
        author.setLastName("Хлудова");
        authorService.createAuthor(author);

        author = new Author();
        author.setFirstName("Александр");
        author.setLastName("Волков");
        authorService.createAuthor(author);

        Collection<Author> list = authorService.findAuthors();

        for (Author value : list) {
            author = value;
            if (author.getLastName().equals("Хлудова")) {
                Book book = new Book();
                book.setTitle("Волны над нами");
                book.setYear(1960);
                bookService.createBook(author, book);
                break;
            }
            if (author.getLastName().equals("Волков")) {
                Book book = new Book();
                book.setTitle("Волшебник изумрудного города");
                book.setYear(1991);
                bookService.createBook(author, book);
                break;
            }
        }
    }

    @Test
    @Order(1)
    public void createAuthor() {
        Author author = new Author();
        author.setFirstName("Артур");
        author.setLastName("Конан Дойль");
        authorService.createAuthor(author);

        author = new Author();
        author.setFirstName("Жюль");
        author.setLastName("Верн");
        authorService.createAuthor(author);

        Collection<Author> list = authorService.findAuthors();
        Assertions.assertEquals(list.size(), 9);
    }

    @Test
    @Order(2)
    public void findAllAuthors() {
        Collection<Author> authors = authorService.findAuthors();
        Assertions.assertTrue(authors.size() != 0);
    }

    @Test
    @Order(3)
    public void createBook() {
        Collection<Author> list = authorService.findAuthors();

        for (Author author : list) {
            if (author.getLastName().equals("Хлудова")) {
               /* Book book = new Book();
                book.setTitle("Волны над нами");
                book.setYear(1960);
                bookService.createBook(author, book);
                */
                Book book = new Book();
                book.setTitle("За голубым порогом");
                book.setYear(1963);
                bookService.createBook(author, book);
                //  Assertions.assertSame("Волны над нами", author.getBooks()[0].getTitle());
                // Assertions.assertSame("За голубым порогом", author.getBooks()[1].getTitle());
                //   Assertions.assertTrue(author.getBooks().equals(null));
                Collection<Book> books = bookService.findBooks(author);
                Assertions.assertEquals(books.size(), 2);
                break;
            }
        }
    }

    @Test
    @Order(4)
    public void updateAuthor() {
        Collection<Author> list = authorService.findAuthors();
        for (Author author : list) {
            if (author.getLastName().equals("Верн")) {
                author.setFirstName("Жюльь");
                author.setLastName("Вернн");
                authorService.updateAuthor(author);
                Assertions.assertEquals("Вернн", author.getLastName());
            }
        }
    }

    @Test
    @Order(5)
    public void removeAuthorById() {
       Collection<Author> list = authorService.findAuthors();

       // System.out.println(list);

     //   for (Author author : list) {
       //     if (author.getLastName().equals("Конан Дойль")) {
               // authorService.deleteAuthor(author.getAuthorId());
                authorService.deleteAuthor("Конан Дойль");
              //  break;
        list = authorService.findAuthors();
        for (Author author : list) {
            if (author.getLastName().equals("Конан Дойль")) {
                Assertions.assertNull(author);
                // break;
            }
        }
      /*  System.out.println(list);
        Assertions.assertEquals(list.size(), 9);*/
    }

    @Test
    @Order(6)
    public void removeBook() {
        Collection<Author> list = authorService.findAuthors();
        for (Author author : list) {
            if (author.getLastName().equals("Хлудова")) {
                for (Book book : author.getBooks()) {
                    // if (book.getTitle().equals("Волны над нами")) {
                    if (book.getTitle().equals("За голубым порогом")) {
                        bookService.deleteBook(author, book.getBookId());
                        break;
                    }
                }
                Assertions.assertNull(author.getBooks()[2]);
            }
        }
    }

    @Test
    @Order(7)
    public void removeAllBooks() {
        Collection<Author> list = authorService.findAuthors();
        for (Author author : list) {
            Collection<Book> books = bookService.findBooks(author);
            System.out.println(books);
            for (Book book : author.getBooks()) {
                if (book != null) {
                    bookService.deleteBook(author, book.getBookId());
                }
            }
            books = bookService.findBooks(author);
            System.out.println(books);
            Assertions.assertEquals(books.size(), 0);
        }
    }

    @Test
    @Order(8)
    public void createAuthor2() {
        Author author = new Author();
        author.setFirstName("Ольга");
        author.setLastName("");
        authorService.createAuthor(author);

        Collection<Author> list = authorService.findAuthors();
        Assertions.assertEquals(list.size(), 8);
    }

    @AfterAll
    public static void close() {
        System.out.println(ANSI_GREEN + "\nTests finished \n" + ANSI_RESET);
    }
}
