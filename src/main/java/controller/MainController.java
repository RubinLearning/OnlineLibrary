package controller;

import domain.Book;
import domain.User;
import dto.UserDTO;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import service.BookService;
import service.UserService;
import utils.OnlineLibraryException;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    protected static Logger logger = Logger.getLogger("org/controller");

    @Resource(name="userService")
    private UserService userService;

    @Resource(name="bookService")
    private BookService bookService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMainPage(Model model, Principal principal) {
        logger.debug("Received request to show main page");
        if (principal != null){
            model.addAttribute("username", principal.getName());
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Book> userBooks = bookService.getAllByUser(user);
            model.addAttribute("userBooks", userBooks);
        } else {
            model.addAttribute("username", "Guest");
        }

        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(Model model) {
        logger.debug("Received request to show login page");
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO accountDTO, BindingResult result) {

        if (result.hasErrors()) {
            return "registration";
        }

        try {
            userService.registerNewUser(accountDTO);
            return "registration-success";
        } catch (OnlineLibraryException e) {
            result.reject("username", e.getMessage());
            return "registration";
        }
    }

    @RequestMapping(value = "/favorites/add", method = RequestMethod.GET)
    public String getAdd(@RequestParam("id") Long bookId) {
        logger.debug("Received request to add book to favorites");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookService.addToFavorites(user, bookId);
        return "redirect:/book/list";
    }

    @RequestMapping(value = "/favorites/delete", method = RequestMethod.GET)
    public String getDelete(@RequestParam("id") Long bookId) {
        logger.debug("Received request to delete book from favorites");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookService.deleteFromFavorites(user, bookId);
        return "redirect:/";
    }

}