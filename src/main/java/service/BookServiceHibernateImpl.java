package service;

import domain.*;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import service.interfaces.BookService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class BookServiceHibernateImpl implements BookService{

    protected static Logger logger = Logger.getLogger("org/service");

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public List<Book> getAll() {
        logger.debug("Retrieving all books");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Book as b ORDER BY author, title, year");
        return query.list();
    }

    @Override
    public List<Book> getFavoritesByUser(User user) {
        logger.debug("Retrieving favorite books for user");
        Session session = sessionFactory.getCurrentSession();
        return ((User) session.get(User.class, user.getId())).getBooks();
    }

    @Override
    public void addToFavorites(User user, Long bookId) {
        logger.debug("Adding book to favorites");
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = user.getBooks();
        boolean alreadyIsFavorite = false;
        for (Book book : books) {
            if (book.getId() == bookId) {
                alreadyIsFavorite = true;
                break;
            }
        }
        if (!alreadyIsFavorite) {
            books.add((Book) session.get(Book.class, bookId));
            session.merge(user);
        }
    }

    @Override
    public void deleteFromFavorites(User user, Long bookId) {
        logger.debug("Deleting book from favorites");
        Session session = sessionFactory.getCurrentSession();
        Iterator<Book> iterator = user.getBooks().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == bookId) {
                iterator.remove();
            }
        }
        session.merge(user);
    }

    @Override
    public List<Book> getAllByGenreId(Long genreId) {
        logger.debug("Retrieving books by genre id");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Book as b WHERE b.genre.id = :genreId ORDER BY author, title, year");
        query.setParameter("genreId", genreId);
        return query.list();
    }

    // case insensitive search in Author's name or Book's title
    @Override
    public List<Book> getBySearchString(String searchString) {
        logger.debug("Retrieving books by search string");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Book as b WHERE upper(b.title) like :searchString or upper(b.author) like :searchString ORDER BY author, title, year");
        query.setParameter("searchString", "%" + searchString.toUpperCase() + "%");
        return query.list();
    }

    @Override
    public Book get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = (Book) session.get(Book.class, id);
        return book;
    }

    @Override
    public void add(Book book) {
        logger.debug("Adding new book");
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Deleting existing book");
        Session session = sessionFactory.getCurrentSession();
        Book book = (Book) session.get(Book.class, id);
        session.delete(book);
    }

    @Override
    public void edit(Book book) {
        logger.debug("Editing existing book");
        Session session = sessionFactory.getCurrentSession();
        session.merge(book);
    }

    @Override
    public BookContent getContentByBookId(Long bookId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT bc FROM BookContent as bc WHERE bc.book.id = :bookId");
        query.setParameter("bookId", bookId);
        return (BookContent) query.uniqueResult();
    }

    @Override
    public void updateBookContent(Book book, MultipartFile file) throws IOException {
        logger.debug("Updating content");

        if (file.isEmpty()) {
            return;
        }

        Session session = sessionFactory.getCurrentSession();
        byte[] bytes = file.getBytes();
        BookContent content = getContentByBookId(book.getId());
        if (content == null) {
            content = new BookContent();
            content.setBook(book);
        }
        content.setContent(bytes);
        session.merge(content);
    }

}