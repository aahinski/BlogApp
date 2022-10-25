package com.ahinski.blog.controllers;

import com.ahinski.blog.models.Article;
import com.ahinski.blog.models.User;
import com.ahinski.blog.repositories.ArticleRepository;
import com.ahinski.blog.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddArticleControllerTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddArticleController addArticleController;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Test
    void addArticleTest() {
        String title = "Gus";
        String text = "My name is Gustavo, but you can call me Gus.";
        User user = userRepository.findByUsername(authentication.getName());
        Article article = new Article(title, text, user);

        addArticleController.addArticle(model, authentication, article.getTitle(), article.getText());

        verify(articleRepository).saveAndFlush(ArgumentMatchers.refEq(article));
    }

}