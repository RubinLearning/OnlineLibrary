package service;

import domain.Genre;
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

@Service("genreService")
@Transactional
public class GenreService {

    protected static Logger logger = Logger.getLogger("org/service");

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public List<Genre> getAll() {
        logger.debug("Retrieving all categories");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Genre");
        return query.list();
    }

    public Genre get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Genre genre = (Genre) session.get(Genre.class, id);
        return genre;
    }

    public void add(Genre genre) {
        logger.debug("Adding new genre");
        Session session = sessionFactory.getCurrentSession();
        session.save(genre);
    }

    public void delete(Long id) throws OnlineLibraryException{
        logger.debug("Deleting existing genre");
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("SELECT b FROM Book as b WHERE b.genre.id = :genreId");
        query.setParameter("genreId", id);
        if (query.list().size() == 0) {
            Genre genre = (Genre) session.get(Genre.class, id);
            session.delete(genre);
        } else {
            throw new OnlineLibraryException(OnlineLibraryErrorType.OPERATION_NOT_ALLOWED);
        }
    }

    public void edit(Genre genre) {
        logger.debug("Editing existing genre");
        Session session = sessionFactory.getCurrentSession();
        Genre existingGenre = (Genre) session.get(Genre.class, genre.getId());
        existingGenre.setName(genre.getName());
        session.save(existingGenre);
    }

}