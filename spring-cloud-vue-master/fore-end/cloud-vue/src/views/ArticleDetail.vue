<template>
  <div class="article-detail-container" v-if="article">
    <h1>{{ article.title }}</h1>
    <div class="meta">
      <span>作者: {{ article.author ? article.author.username : '未知' }}</span>
      <span>发布于: {{ article.createdAt }}</span>
    </div>
    <hr>
    <div class="content" v-html="article.content"></div>
  </div>
  <div v-else>
    <p>正在加载文章...</p>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ArticleDetailView',
  data() {
    return {
      article: null // 用来存放从后端获取的文章详情
    }
  },
  methods: {
    // 定义一个根据ID获取单篇文章的方法
    fetchArticle(id) {
      // 调用我们之前测试过的获取单篇文章的API
      axios.get(`/api/article/articles/${id}`)
          .then(response => {
            // 将后端返回的文章详情数据，赋值给组件内的article变量
            this.article = response.data
          })
          .catch(error => {
            console.error('获取文章详情失败:', error)
            alert('加载文章失败！可能是文章不存在。')
          })
    }
  },
  // created 是一个Vue的“生命周期钩子”，它会在组件被创建后立即自动执行
  created() {
    // this.$route.params.id 可以从当前URL中获取到动态参数 :id 的值
    const articleId = this.$route.params.id

    // 调用方法去获取数据
    this.fetchArticle(articleId)
  }
}
</script>

<style scoped>
.article-detail-container {
  width: 800px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #eee;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.meta {
  color: #888;
  margin: 20px 0;
  font-size: 0.9em;
}

.meta span {
  margin-right: 20px;
}

.content {
  line-height: 1.8;
  margin-top: 20px;
}
</style>
