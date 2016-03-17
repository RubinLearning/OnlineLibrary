package controller;

import controller.property_editor.GenrePropertyEditor;
import domain.Book;
import domain.Genre;
import domain.User;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.BookService;
import service.GenreService;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
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
    public String getAll(Model model, Principal principal) {
        logger.debug("Received request to show books page");
        List<Book> books = bookService.getAll();
        if (principal != null){
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", "Guest");
        }
        model.addAttribute("books", books);
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
    public String postAdd(@ModelAttribute("book") Book book) {
        logger.debug("Received request to add new book");
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
        List<Genre> genres = genreService.getAll();

        model.addAttribute("book", existingBook);
        model.addAttribute("genres", genres);
        model.addAttribute("type", "edit");

        return "book";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String postEdit(@RequestParam("id") Long bookId, @ModelAttribute("book") Book book) {
        logger.debug("Received request to add new book");

        book.setId(bookId);
        bookService.edit(book);

        return "redirect:list";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String uploadFile(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location=" + serverFile.getAbsolutePath());
                return "You successfully uploaded file=" + name;

            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(Genre.class, "genre", new GenrePropertyEditor(genreService));
    }

}