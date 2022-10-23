package com.ahinski.blog.repositories;

import com.ahinski.blog.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}