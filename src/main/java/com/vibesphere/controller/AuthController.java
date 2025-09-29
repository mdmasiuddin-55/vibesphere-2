package com.vibesphere.controller;

import com.vibesphere.model.User;
import com.vibesphere.service.UserService;
import com.vibesphere.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        try {
            User user = userService.authenticate(username, password);
            if (user != null) {
                session.setAttribute("user", user);
                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid username or password");
                return "redirect:/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Login failed: " + e.getMessage());
            return "redirect:/login";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String fullName,
                          RedirectAttributes redirectAttributes) {
        try {
            if (userService.usernameExists(username)) {
                redirectAttributes.addFlashAttribute("error", "Username already exists");
                return "redirect:/register";
            }
            
            if (userService.emailExists(email)) {
                redirectAttributes.addFlashAttribute("error", "Email already exists");
                return "redirect:/register";
            }

            User user = new User(username, email, PasswordUtil.hashPassword(password));
            user.setFullName(fullName);
            userService.saveUser(user);
            
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
