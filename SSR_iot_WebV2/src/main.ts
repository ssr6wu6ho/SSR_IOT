import './assets/main.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import axios from "axios"
import App from './App.vue'
import router from './router/index.js'



// 配置axios默认设置
axios.defaults.baseURL = ""
axios.interceptors.request.use((config) => {
  const token = localStorage.getItem("token")
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

const app = createApp(App)
app.use(createPinia())
app.use(router)

app.mount('#app')
