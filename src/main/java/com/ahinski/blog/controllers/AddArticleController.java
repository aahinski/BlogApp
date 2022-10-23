package com.ahinski.blog.controllers;

import com.ahinski.blog.models.Article;
import com.ahinski.blog.models.User;
import com.ahinski.blog.repositories.ArticleRepository;
import com.ahinski.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddArticleController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/add-article")
    public String addArticle(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("current-user", user);
        return "add-article";
    }

    @PostMapping("/add-article")
    public String addArticle(Model model, Authentication authentication,
                             @RequestParam String title,
                             @RequestParam String text) {
        User user = userRepository.findByUsername(authentication.getName());
        System.out.println(user.getId());
        model.addAttribute("current-user", user);
        Article article = new Article(title, text, user);
        articleRepository.saveAndFlush(article);
        return "redirect:/articles";
    }

}