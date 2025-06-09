package com.cloud.article.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // 表示这是一个大对象，对应数据库的TEXT类型
    private String content;

    // 关键字段：用来记录这篇文章是哪个用户写的
    // 我们只在这里存储用户的ID
    private Long authorId;

    @CreationTimestamp // 在创建时自动记录时间
    @Column(updatable = false) // 创建后此字段不可更新
    private Date createdAt;

    @UpdateTimestamp // 在更新时自动记录时间
    private Date updatedAt;
}
