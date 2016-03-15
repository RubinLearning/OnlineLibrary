package service;

import domain.*;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("bookService")
@Transactional
public class BookService {

    protected static Logger logger = Logger.getLogger("org/service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Book> getAll() {
        logger.debug("Retrieving all books");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Book as b");
        return query.list();
    }

    //!!!! need to upgrade
    public List<Book> getAllByUser(User user) {
        logger.debug("Retrieving books by user");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Book as b");
//        query.setParameter("user", user);
        return query.list();
    }

    public List<Book> getAllByGenre(Genre genre) {
        logger.debug("Retrieving books by genre");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT b FROM Book as b WHERE b.genre = :genre");
        query.setParameter("genre", genre);
        return query.list();
    }

    public Book get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = (Book) session.get(Book.class, id);
        return book;
    }

    public void add(Book book) {
        logger.debug("Adding new book");
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    public void delete(Long id) {
        logger.debug("Deleting existing book");
        Session session = sessionFactory.getCurrentSession();
        Book book = (Book) session.get(Book.class, id);
        session.delete(book);
    }

    public void edit(Book book) {
        logger.debug("Editing existing book");
        Session session = sessionFactory.getCurrentSession();
        Book existingBook = (Book) session.get(Book.class, book.getId());
        existingBook.setName(book.getName());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setName(book.getName());
        existingBook.setDescription(book.getDescription());
        existingBook.setGenre(book.getGenre());
        session.save(existingBook);
    }
}