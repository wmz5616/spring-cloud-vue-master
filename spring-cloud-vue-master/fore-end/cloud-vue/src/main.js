import Vue from 'vue'
import App from './App.vue'     // 导入主外壳组件 App.vue
import router from './router'   // 导入我们配置好的路由
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'

Vue.use(ElementUI)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router, // 关键：将router实例注入到Vue应用中
  render: h => h(App)
}).$mount('#app')
