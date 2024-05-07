package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String postInfo(@PathVariable String id, Model model) {
        long postId;
        try {
            postId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return "PostPage";
        }

        Post post = postService.findById(postId);
        model.addAttribute("viewedPost", post);
        model.addAttribute("viewedСomment", new Comment());
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String sendComment(HttpSession httpSession, @Valid @ModelAttribute("comment") Comment comment, BindingResult result, Model model, @PathVariable("id") String id) {
        Post post = postService.findById(Long.parseLong(id));
        if (post == null) {
            return "redirect:/post/" + id;
        }
        if (result.hasErrors()) {
            model.addAttribute("postToShow", post);
            return "PostPage";
        }
        comment.setUser(getUser(httpSession));
        comment.setPost(post);
        postService.addComment(comment);
        putMessage(httpSession, "You have sucessfully add Comment");
        return "redirect:/post/" + id;
    }
}

