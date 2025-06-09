/* eslint-disable indent */
import Vue from 'vue'
import VueRouter from 'vue-router'
import RegisterView from '../views/Register.vue'
import LoginView from '../views/Login.vue'
import ArticleListView from '../views/ArticleList.vue'
// 👇 1. 导入我们新建的文章详情页面组件
import ArticleDetailView from '../views/ArticleDetail.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/articles'
    },
    {
        path: '/register',
        name: 'register',
        component: RegisterView
    },
    {
        path: '/login',
        name: 'login',
        component: LoginView
    },
    {
        path: '/articles',
        name: 'articles',
        component: ArticleListView
    },
    // 👇 2. 在这里添加文章详情页面的动态路由规则
    {
        // :id 是一个占位符，表示这里将接收一个动态的参数，并命名为 id
        path: '/articles/:id',
        name: 'articleDetail',
        component: ArticleDetailView
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
