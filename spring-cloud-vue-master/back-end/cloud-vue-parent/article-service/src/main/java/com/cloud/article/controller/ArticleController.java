package com.cloud.article.controller;

import com.cloud.article.domain.Article;
import com.cloud.article.dto.ArticleDetailDTO;
import com.cloud.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // 确保导入了 java.util.List

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 👇 这是我们新增的API接口
    /**
     * 获取所有文章的列表（包含作者信息）
     * @return 文章详情列表
     */
    @GetMapping("/articles")
    public List<ArticleDetailDTO> findAllArticles() {
        return articleService.findAllArticlesWithAuthor();
    }


    // --- 已有的方法保持不变 ---

    @PostMapping("/articles")
    public Article createArticle(@RequestBody Article article) {
        return articleService.createArticle(article);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDetailDTO> getArticleDetail(@PathVariable Long id) {
        ArticleDetailDTO articleDetail = articleService.getArticleWithAuthor(id);
        if (articleDetail != null) {
            return ResponseEntity.ok(articleDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
