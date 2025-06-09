package com.cloud.article.service;

import com.cloud.article.client.UserClient;
import com.cloud.article.domain.Article;
import com.cloud.article.dto.ArticleDetailDTO;
import com.cloud.article.dto.UserDTO;
import com.cloud.article.repository.ArticleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserClient userClient; // 注入我们之前创建的Feign客户端

    // 👇 这是我们新增的方法
    /**
     * 获取所有文章的列表（包含作者信息）
     * @return 文章详情列表
     */
    public List<ArticleDetailDTO> findAllArticlesWithAuthor() {
        // 1. 从数据库查出所有文章
        List<Article> articles = articleRepository.findAll();

        // 2. 使用Java 8的Stream API，为每一篇文章都去获取作者信息，并转换成DTO
        return articles.stream().map(article -> {
            UserDTO author = userClient.findUserById(article.getAuthorId());
            ArticleDetailDTO dto = new ArticleDetailDTO();
            BeanUtils.copyProperties(article, dto);
            dto.setAuthor(author);
            return dto;
        }).collect(Collectors.toList());
    }
    public ArticleDetailDTO getArticleWithAuthor(Long articleId) {
        // 1. 从数据库查找文章
        return articleRepository.findById(articleId)
                .map(article -> {
                    // 2. 如果找到了文章，就通过Feign客户端远程调用user-service获取作者信息
                    UserDTO author = userClient.findUserById(article.getAuthorId());

                    // 3. 将文章信息和作者信息组合成ArticleDetailDTO
                    ArticleDetailDTO dto = new ArticleDetailDTO();
                    BeanUtils.copyProperties(article, dto); // 快速拷贝属性
                    dto.setAuthor(author);

                    return dto;
                })
                .orElse(null); // 如果文章找不到，返回null
    }

    /**
     * 创建新文章
     * @param article 要保存的文章对象
     * @return 保存后的文章对象
     */
    public Article createArticle(Article article) {
        // 在真实项目中，这里应该从JWT Token中获取当前登录用户的ID，并设置到authorId
        // article.setAuthorId(currentUserId);
        return articleRepository.save(article);
    }
}
