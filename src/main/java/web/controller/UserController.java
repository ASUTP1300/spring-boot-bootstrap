package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String showResults(final HttpServletRequest request, Principal principal, Model model) {
        String currentUser = principal.getName();
        User user = userService.getByFirstName(currentUser);
        String message = "Добро пожаловать";
        model.addAttribute("user", user);
        model.addAttribute("message", message);

        return "user/getById";
    }
}
