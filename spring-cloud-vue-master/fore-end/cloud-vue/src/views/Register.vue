<template>
  <div class="register-container">
    <h1>注册新用户</h1>
    <el-form ref="form" :model="form" label-width="80px" class="register-form">
      <el-form-item label="用户名">
        <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" type="password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'RegisterView',
  data() {
    return {
      form: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    onSubmit() {
      console.log('准备提交的表单数据:', this.form)

      axios.post('/api/auth/register', {
        username: this.form.username,
        password: this.form.password
      })
          .then(response => {
            console.log('注册成功:', response.data)
            alert('恭喜，注册成功！')
          })
          .catch(error => {
            console.error('注册失败:', error.response ? error.response.data : error.message)
            alert('注册失败，请检查控制台获取更多信息。')
          })
    }
  }
}
</script>

<style scoped>
/* 我们将在这里添加页面的CSS样式 */
</style>
