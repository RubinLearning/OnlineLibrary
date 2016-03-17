package controller;

import controller.property_editor.GenrePropertyEditor;
import domain.Book;
import domain.BookContent;
import domain.Genre;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.BookService;
import service.GenreService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    protected static Logger logger = Logger.getLogger("org/controller");

    @Resource(name = "bookService")
    private BookService bookService;

    @Resource(name = "genreService")
    private GenreService genreService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getAll(@RequestParam(name = "gid", required = false) Long genreId, @RequestParam(name = "search", required = false) String searchString, Model model, Principal principal) {
        logger.debug("Received request to show books page");
        List<Book> books;
        if (searchString != null) {
            books = bookService.getBySearchString(searchString);
        } else if (genreId != null) {
            books = bookService.getAllByGenreId(genreId);
        } else {
            books = bookService.getAll();
        }
        List<Genre> genres = genreService.getAll();
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", "Guest");
        }
        model.addAttribute("books", books);
        model.addAttribute("booksQuantity", books.size());
        model.addAttribute("genres", genres);
        return "book-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAdd(Model model) {
        logger.debug("Received request to show add books page");

        Book book = new Book();
        List<Genre> genres = genreService.getAll();

        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("type", "add");

        return "book";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String postAdd(@RequestParam("file") MultipartFile file, @ModelAttribute("book") Book book) throws IOException {
        logger.debug("Received request to add new book");
        bookService.add(book);

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            BookContent content = bookService.getContentByBookId(book.getId());
            if (content == null) {
                content = new BookContent();
                content.setBook(book);
            }
            content.setContent(bytes);
            bookService.updateContent(content);
        }

        return "redirect:list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String getDelete(@RequestParam("id") Long bookId) {
        logger.debug("Received request to delete book");
        bookService.delete(bookId);
        return "redirect:list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEdit(@RequestParam("id") Long bookId, Model model) {
        logger.debug("Received request to show edit book page");

        Book existingBook = bookService.get(bookId);
        BookContent existingContent = bookService.getContentByBookId(bookId);
        List<Genre> genres = genreService.getAll();

        model.addAttribute("book", existingBook);
        model.addAttribute("genres", genres);
        model.addAttribute("type", "edit");
        model.addAttribute("contentAvailable", (existingContent != null));

        return "book";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String postEdit(@RequestParam("id") Long bookId, @RequestParam("file") MultipartFile file, @ModelAttribute("book") Book book) throws IOException {
        logger.debug("Received request to add new book");

        book.setId(bookId);
        bookService.edit(book);

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            BookContent content = bookService.getContentByBookId(bookId);
            if (content == null) {
                content = new BookContent();
                content.setBook(book);
            }
            content.setContent(bytes);
            bookService.updateContent(content);
        }

        return "redirect:list";
    }

    // pass type = "read" if we only need to read book, not to download
    @RequestMapping(value = "/content/{type}", method = RequestMethod.GET)
    public void readBook(@PathVariable("type") String type, @RequestParam("id") Long bookId, HttpServletResponse response) throws IOException {

        String filename = "book_" + bookService.get(bookId).getId().toString() + ".pdf";
        BookContent content = bookService.getContentByBookId(bookId);
        response.setContentType("application/pdf");
        response.setContentLength(content.getContent().length);
        String contentDispositionType = type.equals("read") ? "inline" : "attachment";
        response.setHeader("Content-Disposition", String.format(contentDispositionType + "; filename=\"" + filename + "\""));

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getContent());
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = byteArrayInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byteArrayInputStream.close();
        outputStream.close();
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(Genre.class, "genre", new GenrePropertyEditor(genreService));
    }

}