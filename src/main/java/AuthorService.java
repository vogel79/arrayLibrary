import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class AuthorService {

    private static final Logger loggerInfo = LoggerFactory.getLogger("info");
    private static final Logger loggerWarn = LoggerFactory.getLogger("warn");
    private static final Logger loggerError = LoggerFactory.getLogger("error");

    private AuthorDAO authorDAO = new AuthorDAO();

    public void createAuthor(Author author) {
        loggerInfo.info("Начало создания автора: " + author.getFirstName() + " " + author.getLastName());
        if (!author.getLastName().isEmpty()) {
           boolean exist = authorDAO.ifExistLastName(author.getLastName());
            if (exist) {
                loggerError.error("Такой автор уже есть!");
                System.out.println("Такой автор уже есть!");
            } else {
                authorDAO.createAuthor(author);
                System.out.println("Автор " + author.getFirstName() + " " + author.getLastName() + " создан");
                loggerInfo.info("Автор " + author.getFirstName() + " " + author.getLastName() + " создан");
            }
        } else {
            loggerError.error("Фамилия автора не может быть пустой!");
            System.out.println("Фамилия автора не может быть пустой!");
       }
    }

    public void deleteAuthor(String lastName) {
        Collection<Author> list = authorDAO.findAuthors();
       // System.out.println(list);

        Author author = null;
        for (Author bookAuthor : list) {
            if (bookAuthor.getLastName().equalsIgnoreCase(lastName)) {
                author = bookAuthor;
                break;
            }
        }
        if (author != null) {
                loggerWarn.warn("Начало удаления автора " + lastName + " по authorId: ");
           //     System.out.println(author);
                authorDAO.deleteAuthor(author.getAuthorId());
                System.out.println("Автор " + author.getFirstName() + " " + author.getLastName() + " удалён");
                loggerWarn.warn("Автор удалён");
            } else {
                loggerError.error("Такого автора нет в базе!");
                System.out.println("Такого автора нет в базе!");
            }
    }

  /*  public void deleteAuthor(String authorId) {
            loggerWarn.warn("Начало удаления автора по authorId: " + authorId);
            authorDAO.deleteAuthor(authorId);
            loggerWarn.warn("Автор удалён");
    }*/

    public void updateAuthor(Author author) {
        loggerWarn.warn("Начало обновления автора по authorId: " + author.getAuthorId());
        authorDAO.updateAuthor(author);
        loggerWarn.warn("Автор обновлён");
    }

    public Collection<Author> findAuthors() {
       // loggerInfo.info("Вывод списка авторов: ");
        return authorDAO.findAuthors();
    }

    public Author findAuthors(String authorId) {
     //   loggerInfo.info("Вывод автора по authorId: " + authorId);
        return authorDAO.findAuthors(authorId);
    }

   /* private boolean existByLastName(Author author) {
        if (author.getLastName() != null) {
            boolean exist = authorDAO.findByLastName(author.getLastName());
            if (exist) {
                loggerError.error("Этот автор уже есть в базе!");
            }
            return false;
        } else {
            loggerError.error("Фамилия автора не может быть пустой!");
        }
        return true;
    }*/

}
