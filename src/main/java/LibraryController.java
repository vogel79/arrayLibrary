import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LibraryController {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    AuthorService authorService = new AuthorService();
    BookService bookService = new BookService();

    public void readConsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Добро пожаловать в библиотеку!");
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - занесение информации в библиотеку");
        System.out.println("2 - просмотр библиотеки");
        System.out.println("3 - обновление информации");
        System.out.println("4 - удаление информации");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                    createInfo(reader);
                    break;
                case "2":
                    readInfo(reader);
                    break;
                case "3":
                    updateInfo(reader);
                    break;
                case "4":
                    deleteInfo(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - занесение информации в библиотеку");
            System.out.println("2 - просмотр библиотеки");
            System.out.println("3 - обновление информации");
            System.out.println("4 - удаление информации");
            input = reader.readLine();
        }
    }

    private void createInfo(BufferedReader reader) throws IOException {
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - создание автора");
        System.out.println("2 - создание книги");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                    createAuthor(reader);
                    break;
                case "2":
                    createBook(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - создание автора");
            System.out.println("2 - создание книги");
            input = reader.readLine();
            //  break;
        }
    }

    private void readInfo(BufferedReader reader) throws IOException {
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - просмотр всех авторов");
        System.out.println("2 - поиск автора");
        System.out.println("3 - просмотр книг автора");
        System.out.println("4 - поиск книги");
        System.out.println("5 - просмотр всех книг");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                    readAllAuthors(reader);
                    break;
                case "2":
                    findAuthor(reader);
                    break;
                case "3":
                    readBooksByAuthor(reader);
                    break;
                case "4":
                    findBook(reader);
                    break;
                case "5":
                    readAllBooks(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - просмотр всех авторов");
            System.out.println("2 - поиск автора");
            System.out.println("3 - просмотр книг автора");
            System.out.println("4 - поиск книги");
            System.out.println("5 - просмотр всех книг");
            input = reader.readLine();
            //  break;
        }
    }

    private void updateInfo(BufferedReader reader) throws IOException {
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - обновление автора");
        System.out.println("2 - обновление книги");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                    updateAuthor(reader);
                    break;
                case "2":
                    updateBook(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - обновление автора");
            System.out.println("2 - обновление книги");
            input = reader.readLine();
            //   break;
        }
    }

    private void deleteInfo(BufferedReader reader) throws IOException {
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - удаление автора");
        System.out.println("2 - удаление книги");
        System.out.println("3 - удаление всех книг автора");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                    deleteAuthor(reader);
                    break;
                case "2":
                    deleteBook(reader);
                    break;
                case "3":
                    deleteAllBooks(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            //  break;
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - удаление автора");
            System.out.println("2 - удаление книги");
            System.out.println("3 - удаление всех книг автора");
            input = reader.readLine();
        }
    }

    private void createAuthor(BufferedReader reader) throws IOException {
        Author author = new Author();
        System.out.println("Введите имя автора:");
        String name = reader.readLine();
        author.setFirstName(name);
        System.out.println("Введите фамилию:");
        String surname = reader.readLine();
        author.setLastName(surname);
        authorService.createAuthor(author);
    }

    private void createBook(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        System.out.println(list);
        System.out.println("Введите фамилию автора: ");
        String name = reader.readLine();

        Author bookAuthor = null;
        for (Author author : list) {
            if (author.getLastName().equalsIgnoreCase(name)) {
                bookAuthor = author;
                break;
            }
        }
        if (bookAuthor != null) {
            Book book = new Book();
            System.out.println("Введите назание книги:");
            String title = reader.readLine();
            book.setTitle(title);
            // System.out.println("Год издания:");
            // int year = Integer.parseInt(reader.readLine());
            //  book.setYear(year);
            bookService.createBook(bookAuthor, book);
        } else {
            System.out.println("Такого автора нет в базе! " +
                "Прежде чем добавить книгу создайте автора");
        }
    }

    private void readAllAuthors(BufferedReader reader) {
        Collection<Author> list = authorService.findAuthors();
        list.forEach(System.out::println);
    }

    private void readAllBooks(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Author author = (Author) iterator.next();
            Collection<Book> books = bookService.findBooks(author);
            System.out.println("Книги автора " + ANSI_GREEN + author.getFirstName() + " "
                + author.getLastName() + ANSI_RESET + ":");
            books.forEach(System.out::println);
        }
    }

    private void readBooksByAuthor(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        System.out.println("Введите фамилию автора: ");
        String name = reader.readLine();

        Author bookAuthor = null;
        for (Author author : list) {
            if (author.getLastName().equalsIgnoreCase(name)) {
                bookAuthor = author;
                break;
            }
        }
        if (bookAuthor != null) {
            if ((int) Arrays.stream(bookAuthor.getBooks()).filter(Objects::nonNull).count() != 0) {
                System.out.println("Список книг автора " + ANSI_GREEN + bookAuthor.getFirstName() + " "
                    + bookAuthor.getLastName() + ANSI_RESET + ":");
                Collection<Book> books = bookService.findBooks(bookAuthor);
                books.forEach(System.out::println);
                //  break;
            } else System.out.println("У данного автора нет книг!");
        } else System.out.println("Такого автора нет в базе!");
    }

    private void updateAuthor(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        System.out.println("Введите фамилию автора для обновления: ");
        String name = reader.readLine();
        Author bookAuthor = null;
        for (Author author : list) {
            if (author.getLastName().equalsIgnoreCase(name)) {
                bookAuthor = author;
                break;
            }
        }
        if (bookAuthor != null) {
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - изменение имени автора");
            System.out.println("2 - изменение фамилии автора");
            String input = reader.readLine();
            while (!"0".equals(input)) {
                switch (input) {
                    case "1":
                        System.out.println("Введите новое имя автора:");
                        bookAuthor.setFirstName(reader.readLine());
                        authorService.updateAuthor(bookAuthor);
                        break;
                    case "2":
                        System.out.println("Введите новую фамилию автора:");
                        bookAuthor.setLastName(reader.readLine());
                        authorService.updateAuthor(bookAuthor);
                        break;
                    default:
                        throw new IllegalArgumentException("Неизвестный выбор!");
                }
                System.out.println("Выберите действие или нажмите '0' для выхода: ");
                System.out.println("1 - изменение имени автора");
                System.out.println("2 - изменение фамилии автора");
                input = reader.readLine();
            }
            System.out.println("Автор " + ANSI_GREEN + bookAuthor.getFirstName() + " "
                + bookAuthor.getLastName() + ANSI_RESET + " изменён");
        } else System.out.println("Такого автора нет в базе!");
    }

    private void updateBook(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        System.out.println("Введите фамилию автора: ");
        String name = reader.readLine();
        Author bookAuthor = null;
        for (Author author : list) {
            if (author.getLastName().equalsIgnoreCase(name)) {
                bookAuthor = author;
                break;
            }
        }
        if (bookAuthor != null) {
            System.out.println("Введите название книги: ");
            String title = reader.readLine();
            Book bookBook = null;
            for (Book book : bookAuthor.getBooks()) {
                if (book != null) {
                    if (book.getTitle().equals(title)) {
                        bookBook = book;
                        break;
                    }
                }
            }
            if (bookBook != null) {
                System.out.println("Выберите действие или нажмите '0' для выхода: ");
                System.out.println("1 - изменение названия книги");
                System.out.println("2 - изменение года издания");
                String input = reader.readLine();
                while (!"0".equals(input)) {
                    switch (input) {
                        case "1":
                            System.out.println("Введите новое название книги:");
                            bookBook.setTitle(reader.readLine());
                            bookService.updateBook(bookAuthor, bookBook);
                            break;
                        case "2":
                            System.out.println("Введите новый год издания:");
                            bookBook.setYear(Integer.parseInt(reader.readLine()));
                            bookService.updateBook(bookAuthor, bookBook);
                            break;
                        default:
                            throw new IllegalArgumentException("Неизвестный выбор!");
                    }
                    //   break;
                    System.out.println("Выберите действие или нажмите '0' для выхода: ");
                    System.out.println("1 - изменение названия книги");
                    System.out.println("2 - изменение года издания");
                    input = reader.readLine();
                }
                System.out.println("Книга " + "\"" + title + "\"" + " изменена");
            } else System.out.println("Такой книги нет в базе!");
        } else System.out.println("Такого автора нет в базе!");
    }

    private void deleteAuthor(BufferedReader reader) throws IOException {
      //  Collection<Author> list = authorService.findAuthors();
        System.out.println("Введите фамилию автора для удаления: ");
        String name = reader.readLine();
                authorService.deleteAuthor(name);
    }

    private void deleteBook(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        System.out.println("Введите фамилию автора: ");
        String name = reader.readLine();

        Author bookAuthor = null;
        for (Author author : list) {
            if (author.getLastName().equalsIgnoreCase(name)) {
                bookAuthor = author;
                break;
            }
        }
        if (bookAuthor != null) {
            System.out.println("Введите название книги: ");
            String title = reader.readLine();
            Book[] l = bookAuthor.getBooks();
            //  System.out.println(Arrays.toString(l));
            Book bookBook = null;
            // for (Book book : bookAuthor.getBooks()) {
            for (Book book : l) {
                //  System.out.println(Arrays.toString(l));
                if (book != null) {
                    if (book.getTitle().equalsIgnoreCase(title)) {
                        //       System.out.println(book);
                        bookBook = book;
                        break;
                    }
                }
            }
            if (bookBook != null) {
                bookService.deleteBook(bookAuthor, bookBook.getBookId());
                System.out.println("Книга " + "\"" + title + "\"" + " удалена");
            } else System.out.println("Такой книги нет в базе!");
        } else System.out.println("Такого автора нет в базе!");
    }


   /* private void deleteAllBooks(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Author author = (Author) iterator.next();
            for (Book book : author.getBooks()) {
                bookService.deleteBook(author, book.getBookId());
                break;
            }
        }
    }*/


    private void deleteAllBooks(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        System.out.println("Введите фамилию автора: ");
        String name = reader.readLine();
        Author bookAuthor = null;
        for (Author author : list) {
            if (author.getLastName().equalsIgnoreCase(name)) {
                bookAuthor = author;
                break;
            }
        }
        if (bookAuthor != null) {
            System.out.println(Arrays.toString(bookAuthor.getBooks()));
            if ((int) Arrays.stream(bookAuthor.getBooks()).filter(Objects::nonNull).count() != 0) {
                for (Book book : bookAuthor.getBooks()) {
                    if (book != null) {
                        //   if (bookAuthor.getBooks() != null)
                        System.out.println(book);
                        bookService.deleteBook(bookAuthor, book.getBookId());
                        // break;
                    }
                }
                System.out.println("Книги удалены");
            } else System.out.println("У данного автора нет книг!");
        } else System.out.println("Такого автора нет в базе!");
    }

    private void findAuthor(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        System.out.println("Введите фамилию автора для поиска: ");
        String name = reader.readLine();
        Author bookAuthor = null;
        for (Author author : list) {
            if (author.getLastName().equalsIgnoreCase(name)) {
                bookAuthor = author;
                break;
            }
        }
        if (bookAuthor != null) {
            System.out.println(authorService.findAuthors(bookAuthor.getAuthorId()));
        } else System.out.println("Такого автора нет в базе!");
    }

    private void findBook(BufferedReader reader) throws IOException {
        Collection<Author> list = authorService.findAuthors();
        System.out.println("Введите фамилию автора: ");
        String name = reader.readLine();
        Author bookAuthor = null;
        for (Author author : list) {
            if (author.getLastName().equalsIgnoreCase(name)) {
                bookAuthor = author;
                break;
            }
        }
        if (bookAuthor != null) {
            authorService.findAuthors(bookAuthor.getAuthorId());
            System.out.println("Введите название книги: ");
            String title = reader.readLine();
            Book bookBook = null;
            for (Book book : bookAuthor.getBooks()) {
                if (book != null) {
                    if (book.getTitle().equals(title)) {
                        bookBook = book;
                        break;
                    }
                }
            }
            if (bookBook != null) {
                System.out.println(bookService.findBooks(bookAuthor, bookBook.getBookId()));
                //  break;
            } else System.out.println("Такой книги нет в базе!");
        } else System.out.println("Такого автора нет в базе!");
    }
}
