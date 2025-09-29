package com.vibesphere.controller;

import com.vibesphere.model.Post;
import com.vibesphere.model.User;
import com.vibesphere.service.PostService;
import com.vibesphere.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private S3Service s3Service;

    @PostMapping("/create")
    public String createPost(@RequestParam("media") MultipartFile media,
                           @RequestParam("caption") String caption,
                           @RequestParam("mediaType") String mediaType,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            String mediaUrl = s3Service.uploadFile(media, "posts");
            Post post = new Post();
            post.setUserId(user.getId());
            post.setCaption(caption);
            post.setMediaUrl(mediaUrl);
            post.setMediaType(Post.MediaType.valueOf(mediaType.toUpperCase()));

            postService.createPost(post);
            redirectAttributes.addFlashAttribute("success", "Post created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create post: " + e.getMessage());
        }

        return "redirect:/";
    }

    @PostMapping("/{postId}/like")
    @ResponseBody
    public String likePost(@PathVariable Long postId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "error";
        }

        boolean success = postService.likePost(postId, user.getId());
        return success ? "success" : "error";
    }

    @PostMapping("/{postId}/comment")
    public String addComment(@PathVariable Long postId,
                           @RequestParam String commentText,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        boolean success = postService.addComment(postId, user.getId(), commentText);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Comment added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to add comment");
        }

        return "redirect:/";
    }
}