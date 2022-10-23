package com.ahinski.blog.controllers;

import com.ahinski.blog.models.Article;
import com.ahinski.blog.models.Comment;
import com.ahinski.blog.models.User;
import com.ahinski.blog.repositories.ArticleRepository;
import com.ahinski.blog.repositories.CommentRepository;
import com.ahinski.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticlesController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/articles")
    public String articleMain(Model model) {
        Iterable<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "articles-main";
    }

    @GetMapping("/articles/{id}")
    public String articlesDetails(@PathVariable(value="id") Long id, Model model) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        List<Article> article = new ArrayList<>();
        articleOptional.ifPresent(article :: add);
        model.addAttribute("article", article.get(0));
        model.addAttribute("comments", article.get(0).getComments());
        model.addAttribute("id", id);
        return "articles-details";
    }

    @PostMapping("/articles/{id}")
    public String addComment(@PathVariable(value="id") Long id, Model model,
                             Authentication authentication,
                             @RequestParam String comment) {
        User user = userRepository.findByUsername(authentication.getName());
        Optional<Article> articleOptional = articleRepository.findById(id);
        List<Article> article = new ArrayList<>();
        articleOptional.ifPresent(article :: add);
        Comment commentToSave = new Comment(comment, article.get(0),
                user);
        commentRepository.saveAndFlush(commentToSave);
        model.addAttribute("current-user", user);
        model.addAttribute("article", article);
        model.addAttribute("comments", article.get(0).getComments());
        return "articles-details";
    }

}