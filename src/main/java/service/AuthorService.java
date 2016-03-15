package service;

import domain.Author;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.OnlineLibraryErrorType;
import utils.OnlineLibraryException;

import javax.annotation.Resource;
import java.util.List;

@Service("authorService")
@Transactional
public class AuthorService {

    protected static Logger logger = Logger.getLogger("org/service");

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public List<Author> getAll() {
        logger.debug("Retrieving all currencies");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Author");
        return query.list();
    }

    public Author get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Author author = (Author) session.get(Author.class, id);
        return author;
    }

    public void add(Author author) {
        logger.debug("Adding new author");
        Session session = sessionFactory.getCurrentSession();
        session.save(author);
    }

    public void delete(Long id) throws OnlineLibraryException {
        logger.debug("Deleting existing author");
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT b FROM Book as b WHERE b.author.id = :authorId");
        query.setParameter("authorId", id);
        if (query.list().size() == 0) {
            Author author = (Author) session.get(Author.class, id);
            session.delete(author);
        } else {
            throw new OnlineLibraryException(OnlineLibraryErrorType.OPERATION_NOT_ALLOWED);
        }
    }

    public void edit(Author author) {
        logger.debug("Editing existing author");
        Session session = sessionFactory.getCurrentSession();
        Author existingAuthor = (Author) session.get(Author.class, author.getId());
        existingAuthor.setName(author.getName());
        session.save(existingAuthor);
    }

}