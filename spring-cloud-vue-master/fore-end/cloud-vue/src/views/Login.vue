<template>
  <div class="login-container">
    <div v-if="!isLoggedIn">
      <h1>用户登录</h1>
      <el-form :model="form" label-width="80px" class="login-form">
        <el-form-item label="用户名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onLogin">登 录</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div v-else>
      <h1>登录成功!</h1>
      <p>您已成功登录，获取到的Token已存入浏览器。</p>
      <el-button type="success" @click="getUsers">获取用户列表 (受保护的API)</el-button>
      <pre>{{ users }}</pre> </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'LoginView',
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      isLoggedIn: false, // 新增一个状态，用来控制页面显示
      users: null // 新增一个变量，用来存放获取到的用户列表
    }
  },
  methods: {
    onLogin() {
      axios.post('/api/auth/login', this.form)
          .then(response => {
            const token = response.data.token
            alert('登录成功！')
            localStorage.setItem('jwt-token', token)
            this.isLoggedIn = true // 登录成功后，改变状态
          })
          .catch(error => {
            alert('登录失败，用户名或密码错误！')
            console.error(error.response ? error.response.data : error.message)
          })
    },
    getUsers() {
      // 1. 从本地存储中读取我们之前存好的Token
      const token = localStorage.getItem('jwt-token')

      // 2. 如果没有Token，就提示用户
      if (!token) {
        alert('请先登录！')
        return
      }

      // 3. 发送GET请求到受保护的 /api/users 接口
      axios.get('/api/users', {
        // 4. 关键一步：在请求头中附带上我们的Token
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
          .then(response => {
            // 请求成功，将返回的用户数据存起来
            this.users = response.data
          })
          .catch(error => {
            // 如果Token无效或过期，这里会收到403等错误
            alert('获取用户列表失败！')
            console.error(error.response ? error.response.data : error.message)
          })
    }
  }
}
</script>

<style scoped>
.login-container {
  width: 500px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
pre {
  background-color: #f4f4f4;
  padding: 10px;
  border: 1px solid #ddd;
}
</style>

<style scoped>
/* 您可以添加一些样式让页面更好看 */
.login-container {
  width: 400px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>
