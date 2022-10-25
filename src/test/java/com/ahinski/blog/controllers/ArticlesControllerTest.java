package com.ahinski.blog.controllers;

import com.ahinski.blog.models.Article;
import com.ahinski.blog.models.Comment;
import com.ahinski.blog.models.User;
import com.ahinski.blog.repositories.ArticleRepository;
import com.ahinski.blog.repositories.CommentRepository;
import com.ahinski.blog.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticlesControllerTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ArticlesController articlesController;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Test
    void addCommentTest() {
        Long id = 1L;
        String comment = "Sadio Mane, I really think he's the best player in the weeeld";
        Article article = new Article();
        when(articleRepository.findById(id)).thenReturn(java.util.Optional.of(article));
        User user = userRepository.findByUsername(authentication.getName());
        Comment commentToSave = new Comment(comment, article, user);

        articlesController.addComment(id, model, authentication, comment);

        verify(commentRepository).saveAndFlush(ArgumentMatchers.refEq(commentToSave));
    }
}