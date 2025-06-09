package com.cloud.article.dto;

import lombok.Data;
import java.util.Date;

// 这个类用来封装一篇完整的文章详情，包含了文章自身信息和作者信息
@Data
public class ArticleDetailDTO {
    private Long id;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    // 作者信息，我们直接复用刚才创建的UserDTO
    private UserDTO author;
}
