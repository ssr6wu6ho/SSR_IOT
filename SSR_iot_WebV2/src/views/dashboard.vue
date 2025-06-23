<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-900 via-black to-gray-800">
    <!-- 侧边栏 -->
    <Sidebar :isOpen="sidebarOpen" @toggle="toggleSidebar" />

    <!-- 主内容区域 -->
    <div :class="['transition-all duration-300', sidebarOpen ? 'ml-64' : 'ml-16']">
      <!-- 顶部导航 -->
      <TopNav @toggleSidebar="toggleSidebar" />

      <!-- 仪表板内容 -->
      <div class="p-6">
        <!-- 欢迎信息 -->
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-white mb-2">欢迎回来，{{ username }}</h1>
          <p class="text-gray-400">今天是 {{ currentDate }}，您的物联网设备运行状态良好</p>
        </div>

        <!-- 统计卡片 -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <StatCard title="设备总数" :value="stats.totalDevices" icon="📱" color="from-blue-500 to-cyan-500"
            :trend="'+12%'" />
          <StatCard title="在线设备" :value="stats.onlineDevices" icon="🟢" color="from-green-500 to-emerald-500"
            :trend="'+5%'" />
          <StatCard title="今日数据" :value="stats.todayData" icon="📊" color="from-purple-500 to-pink-500"
            :trend="'+23%'" />
          <StatCard title="告警数量" :value="stats.alerts" icon="⚠️" color="from-red-500 to-orange-500" :trend="-8" />
        </div>

        <!-- 图表区域 -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
          <!-- 实时数据图表 -->
          <div class="glass-effect rounded-xl p-6">
            <h3 class="text-xl font-semibold text-white mb-4">实时数据监控</h3>
            <div class="h-64 flex items-center justify-center">
              <canvas ref="realtimeChart" class="w-full h-full"></canvas>
            </div>
          </div>

          <!-- 设备状态分布 -->
          <div class="glass-effect rounded-xl p-6">
            <h3 class="text-xl font-semibold text-white mb-4">设备状态分布</h3>
            <div class="h-64 flex items-center justify-center">
              <canvas ref="statusChart" class="w-full h-full"></canvas>
            </div>
          </div>
        </div>

        <!-- 最近活动和设备列表 -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <!-- 最近活动 -->
          <div class="glass-effect rounded-xl p-6">
            <h3 class="text-xl font-semibold text-white mb-4">最近活动</h3>
            <div class="space-y-4">
              <div v-for="activity in recentActivities" :key="activity.id"
                class="flex items-center space-x-3 p-3 bg-gray-800/30 rounded-lg">
                <div
                  :class="['w-2 h-2 rounded-full', activity.type === 'success' ? 'bg-green-500' : activity.type === 'warning' ? 'bg-yellow-500' : 'bg-red-500']">
                </div>
                <div class="flex-1">
                  <p class="text-white text-sm">{{ activity.message }}</p>
                  <p class="text-gray-400 text-xs">{{ activity.time }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- 设备快速访问 -->
          <div class="glass-effect rounded-xl p-6">
            <h3 class="text-xl font-semibold text-white mb-4">设备快速访问</h3>
            <div class="space-y-3">
              <div v-for="device in quickAccessDevices" :key="device.id"
                class="flex items-center justify-between p-3 bg-gray-800/30 rounded-lg hover:bg-gray-700/30 transition-colors cursor-pointer">
                <div class="flex items-center space-x-3">
                  <div :class="['w-3 h-3 rounded-full', device.online ? 'bg-green-500' : 'bg-red-500']"></div>
                  <div>
                    <p class="text-white text-sm font-medium">{{ device.name }}</p>
                    <p class="text-gray-400 text-xs">{{ device.type }}</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="text-white text-sm">{{ device.value }}</p>
                  <p class="text-gray-400 text-xs">{{ device.unit }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import Sidebar from '../components/sidebar.vue'
import TopNav from '../components/top-nav.vue'
import StatCard from '../components/stat-card.vue'

const sidebarOpen = ref(true)
const username = ref(localStorage.getItem('username') || '用户')

const currentDate = computed(() => {
  return new Date().toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    weekday: 'long'
  })
})

const stats = ref({
  totalDevices: 24,
  onlineDevices: 18,
  todayData: 1247,
  alerts: 3
})

const recentActivities = ref([
  { id: 1, type: 'success', message: '温度传感器 #001 数据上传成功', time: '2分钟前' },
  { id: 2, type: 'warning', message: '湿度传感器 #003 数值异常', time: '5分钟前' },
  { id: 3, type: 'success', message: '新设备 #024 已连接', time: '10分钟前' },
  { id: 4, type: 'error', message: '设备 #012 离线', time: '15分钟前' },
])

const quickAccessDevices = ref([
  { id: 1, name: '温度传感器 #001', type: '环境监测', online: true, value: '23.5', unit: '°C' },
  { id: 2, name: '湿度传感器 #002', type: '环境监测', online: true, value: '65', unit: '%' },
  { id: 3, name: '智能开关 #003', type: '控制设备', online: false, value: '关闭', unit: '' },
  { id: 4, name: '空气质量 #004', type: '环境监测', online: true, value: '良好', unit: 'AQI' },
])

const realtimeChart = ref(null)
const statusChart = ref(null)

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value
}

onMounted(() => {
  // 初始化图表
  initCharts()
  // 模拟实时数据更新
  setInterval(updateRealtimeData, 3000)
})

const initCharts = () => {
  // 这里可以集成 Chart.js 或其他图表库
  // 为了简化，我们使用简单的Canvas绘制
  drawRealtimeChart()
  drawStatusChart()
}

const drawRealtimeChart = () => {
  const canvas = realtimeChart.value
  if (!canvas) return

  const ctx = canvas.getContext('2d')
  canvas.width = canvas.offsetWidth
  canvas.height = canvas.offsetHeight

  // 绘制简单的折线图
  ctx.clearRect(0, 0, canvas.width, canvas.height)
  ctx.strokeStyle = '#60a5fa'
  ctx.lineWidth = 2
  ctx.beginPath()

  const points = 20
  for (let i = 0; i < points; i++) {
    const x = (canvas.width / points) * i
    const y = canvas.height / 2 + Math.sin(i * 0.5) * 50 + Math.random() * 20
    if (i === 0) ctx.moveTo(x, y)
    else ctx.lineTo(x, y)
  }
  ctx.stroke()
}
const drawStatusChart = () => {
  const canvas = statusChart.value
  if (!canvas) return

  const ctx = canvas.getContext('2d')
  
  // 获取实际渲染尺寸
  const width = canvas.offsetWidth
  const height = canvas.offsetHeight
  
  // 检查尺寸是否有效
  if (width <= 10 || height <= 10) {
    console.warn("Canvas dimensions too small, skipping draw")
    return
  }
  
  // 设置画布内部尺寸（避免模糊）
  const devicePixelRatio = window.devicePixelRatio || 1
  canvas.width = width * devicePixelRatio
  canvas.height = height * devicePixelRatio
  ctx.scale(devicePixelRatio, devicePixelRatio)

  // 清除画布
  ctx.clearRect(0, 0, width, height)

  // 计算半径并确保为正数
  const centerX = width / 2
  const centerY = height / 2
  const minDimension = Math.min(centerX, centerY)
  
  // 确保半径至少为10像素
  const radius = Math.max(minDimension - 20, 10)

  const data = [
    { label: '在线', value: 75, color: '#10b981' },
    { label: '离线', value: 15, color: '#ef4444' },
    { label: '维护', value: 10, color: '#f59e0b' }
  ]

  let currentAngle = 0
  data.forEach(item => {
    const sliceAngle = (item.value / 100) * 2 * Math.PI

    ctx.beginPath()
    ctx.moveTo(centerX, centerY)
    ctx.arc(centerX, centerY, radius, currentAngle, currentAngle + sliceAngle)
    ctx.closePath()
    ctx.fillStyle = item.color
    ctx.fill()

    currentAngle += sliceAngle
  })
}
const updateRealtimeData = () => {
  // 更新统计数据
  stats.value.todayData += Math.floor(Math.random() * 10)

  // 重绘实时图表
  drawRealtimeChart()
}
</script>
