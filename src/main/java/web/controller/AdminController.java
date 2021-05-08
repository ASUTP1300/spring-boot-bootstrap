package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.service.RoleServiceImp;
import web.service.UserService;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleServiceImp roleService;

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleService(RoleServiceImp roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     *
     * @param model
     * @param principal
     * @return
     */
    @GetMapping()
    public String example(Model model, Principal principal) {
        User user = new User();
        User userActive = userService.getByFirstName(principal.getName());
        List<Role> listRoles = roleRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("userActive", userActive);
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("listRoles", listRoles);
        return "admin/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));

        return "admin/getById";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(required = false, name = "listRolesResponse") List<String> roles) {
        user.setRoles(roleService.getRoleByName(roles));
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("roleNames") List<String> roles) {
        user.setRoles(roleService.getRoleByName(roles));
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String delete(@ModelAttribute("user") User user) {
        userService.remove(user.getId());
        return "redirect:/admin";
    }

    @GetMapping("/getOne")
    @ResponseBody
    public User getOne(long id) {
        return userService.getById(id);
    }

    @PostMapping(value = "/update")
    public String update2(@ModelAttribute("user") User user,
                          @RequestParam("listRolesResponse") List<String> roles) {
        user.setRoles(roleService.getRoleByName(roles));
        userService.update(user);
        return "redirect:/admin";
    }
}
