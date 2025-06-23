<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-900 via-black to-gray-800 flex items-center justify-center relative overflow-hidden">
    <!-- 背景动画效果 -->
    <div class="absolute inset-0 overflow-hidden">
      <div class="absolute -top-40 -right-40 w-80 h-80 bg-purple-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse"></div>
      <div class="absolute -bottom-40 -left-40 w-80 h-80 bg-cyan-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse animation-delay-2000"></div>
      <div class="absolute top-40 left-1/2 w-80 h-80 bg-pink-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse animation-delay-4000"></div>
    </div>

    <!-- 登录表单 -->
    <div class="relative z-10 w-full max-w-md p-8">
      <div class="glass-effect rounded-2xl p-8 shadow-2xl">
        <!-- Logo和标题 -->
        <div class="text-center mb-8">
          <div class="w-16 h-16 mx-auto mb-4 bg-gradient-to-r from-cyan-500 to-purple-600 rounded-full flex items-center justify-center">
            <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"></path>
            </svg>
          </div>
          <h1 class="text-3xl font-bold gradient-text mb-2">IoT Platform</h1>
          <p class="text-gray-400">智能物联网管理平台</p>
        </div>

        <!-- 登录表单 -->
        <form @submit.prevent="handleLogin" class="space-y-6">
          <div>
            <label class="block text-gray-300 text-sm font-medium mb-2">用户名</label>
            <input
              v-model="loginForm.username"
              type="text"
              class="w-full px-4 py-3 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500 focus:border-transparent transition-all duration-300"
              placeholder="请输入用户名"
              required
            />
          </div>
          <div>
            <label class="block text-gray-300 text-sm font-medium mb-2">密码</label>
            <input
              v-model="loginForm.password"
              type="password"
              class="w-full px-4 py-3 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500 focus:border-transparent transition-all duration-300"
              placeholder="请输入密码"
              required
            />
          </div>
          
          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3 bg-gradient-to-r from-cyan-500 to-purple-600 text-white font-semibold rounded-lg hover:from-cyan-600 hover:to-purple-700 transform hover:scale-105 transition-all duration-300 shadow-lg disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="loading">登录中...</span>
            <span v-else>登录</span>
          </button>
        </form>

        <!-- 注册链接 -->
        <div class="mt-6 text-center">
          <button
            @click="showRegister = !showRegister"
            class="text-cyan-400 hover:text-cyan-300 transition-colors duration-300"
          >
            {{ showRegister ? '已有账号？点击登录' : '没有账号？点击注册' }}
          </button>
        </div>

        <!-- 注册表单 -->
        <div v-if="showRegister" class="mt-6 pt-6 border-t border-gray-700/50">
          <form @submit.prevent="handleRegister" class="space-y-4">
            <div>
              <label class="block text-gray-300 text-sm font-medium mb-2">注册用户名</label>
              <input
                v-model="registerForm.username"
                type="text"
                class="w-full px-4 py-3 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-300"
                placeholder="请输入用户名"
                required
              />
            </div>
            <div>
              <label class="block text-gray-300 text-sm font-medium mb-2">注册密码</label>
              <input
                v-model="registerForm.password"
                type="password"
                class="w-full px-4 py-3 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-300"
                placeholder="请输入密码"
                required
              />
            </div>
            <button
              type="submit"
              :disabled="loading"
              class="w-full py-3 bg-gradient-to-r from-purple-500 to-pink-600 text-white font-semibold rounded-lg hover:from-purple-600 hover:to-pink-700 transform hover:scale-105 transition-all duration-300 shadow-lg disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <span v-if="loading">注册中...</span>
              <span v-else>注册</span>
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const loading = ref(false)
const showRegister = ref(false)

const loginForm = ref({
  username: '',
  password: ''
})

const registerForm = ref({
  username: '',
  password: ''
})

const handleLogin = async () => {
  loading.value = true
  try {
    const formData = new URLSearchParams()
    formData.append('username', loginForm.value.username)
    formData.append('password', loginForm.value.password)
    
    const result = await axios.post('/user/login', formData, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    
    if (result.data.code === 0) {
      localStorage.setItem('token', result.data.data)
      localStorage.setItem('username', loginForm.value.username)
      router.push('/dashboard')
    } else {
      alert(result.data.message || '登录失败')
    }
  } catch (error) {
    console.error('登录失败:', error)
    alert('登录失败，请检查用户名和密码')
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  loading.value = true
  try {
    const formData = new URLSearchParams()
    formData.append('username', registerForm.value.username)
    formData.append('password', registerForm.value.password)
    
    const result = await axios.post('/user/register', formData, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    
    if (result.data.code === 0) {
      alert('注册成功，请登录')
      showRegister.value = false
      registerForm.value = { username: '', password: '' }
    } else {
      alert(result.data.message || '注册失败')
    }
  } catch (error) {
    console.error('注册失败:', error)
    alert('注册失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.animation-delay-2000 {
  animation-delay: 2s;
}

.animation-delay-4000 {
  animation-delay: 4s;
}
</style>
