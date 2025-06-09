<template>
  <div class="article-container">
    <h1>文章列表</h1>
    <div v-for="article in articles" :key="article.id" class="article-item">
      <router-link :to="`/articles/${article.id}`">
        <h2>{{ article.title }}</h2>
      </router-link>
      <p>作者: {{ article.author ? article.author.username : '未知' }}</p>
      <p>发布于: {{ article.createdAt }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ArticleListView',
  data() {
    return {
      articles: []
    }
  },
  // 👇 我们新增了 filters 部分
  filters: {
    formatDate(value) {
      if (!value) return ''
      return new Date(value).toLocaleDateString()
    }
  },
  methods: {
    fetchArticles() {
      axios.get('/api/article/articles')
          .then(response => {
            this.articles = response.data
          })
          .catch(error => {
            console.error('获取文章列表失败:', error)
            alert('获取文章列表失败！')
          })
    }
  },
  created() {
    this.fetchArticles()
  }
}
</script>

<style scoped>
.article-container {
  width: 800px;
  margin: 50px auto;
}
.article-item {
  border-bottom: 1px solid #eee;
  padding: 20px 0;
}
/* 👇 我们新增了链接的样式 */
.article-title-link {
  text-decoration: none;
  color: #333;
}
.article-title-link:hover {
  color: #409EFF;
}
h2 {
  margin-bottom: 10px;
}
p {
  color: #666;
}
</style>
