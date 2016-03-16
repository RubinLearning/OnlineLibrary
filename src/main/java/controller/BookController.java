package controller;

import controller.property_editor.GenrePropertyEditor;
import domain.Book;
import domain.Genre;
import domain.User;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import service.BookService;
import service.GenreService;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    protected static Logger logger = Logger.getLogger("org/controller");

    @Resource(name="bookService")
    private BookService bookService;

    @Resource(name="genreService")
    private GenreService genreService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getBooksWithAmount(Model model) {
        logger.debug("Received request to show books page");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Book> books = bookService.getAll();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("books", books);
        return "book-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAdd(Model model) {
        logger.debug("Received request to show add books page");

        Book book = new Book();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Genre> genres = genreService.getAll();

        model.addAttribute("bookAttribute", book);
        model.addAttribute("genreAttribute", genres);
        model.addAttribute("type", "add");

        return "book";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String postAdd(@ModelAttribute("bookAttribute") Book book) {
        logger.debug("Received request to add new book");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookService.add(book);

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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Genre> genres = genreService.getAll();

        model.addAttribute("bookAttribute", existingBook);
        model.addAttribute("genreAttribute", genres);
        model.addAttribute("type", "edit");

        return "book";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String postEdit(@RequestParam("id") Long bookId, @ModelAttribute("bookAttribute") Book book) {
        logger.debug("Received request to add new book");

        book.setId(bookId);
        bookService.edit(book);

        return "redirect:list";
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(Genre.class, "genre", new GenrePropertyEditor(genreService));
    }

}