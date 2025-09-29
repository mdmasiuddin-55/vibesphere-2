package com.vibesphere.controller;

import com.vibesphere.model.Post;
import com.vibesphere.model.User;
import com.vibesphere.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<Post> posts = postService.getFeedPosts(user.getId());
        model.addAttribute("posts", posts);
        return "home";
    }

    @GetMapping("/explore")
    public String explore(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "explore";
    }
}