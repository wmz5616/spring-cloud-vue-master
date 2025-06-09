package com.cloud.article.repository;

import com.cloud.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 文章数据仓库接口
 * 继承 JpaRepository<Article, Long> 后，
 * 自动拥有对 Article 实体的增、删、改、查等所有基础数据库操作能力。
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
