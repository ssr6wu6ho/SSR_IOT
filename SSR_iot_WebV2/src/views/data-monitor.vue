<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-900 via-black to-gray-800">
    <Sidebar :isOpen="sidebarOpen" @toggle="toggleSidebar" />
    
    <div :class="['transition-all duration-300', sidebarOpen ? 'ml-64' : 'ml-16']">
      <TopNav @toggleSidebar="toggleSidebar" />
      
      <div class="p-6">
        <!-- 页面标题 -->
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-white mb-2">数据监控</h1>
          <p class="text-gray-400">实时监控您的物联网设备数据</p>
        </div>

        <!-- 实时数据卡片 -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <div class="glass-effect rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-gray-400 text-sm">平均温度</p>
                <p class="text-2xl font-bold text-white">{{ realtimeData.temperature }}°C</p>
              </div>
              <div class="w-12 h-12 bg-gradient-to-r from-red-500 to-orange-500 rounded-full flex items-center justify-center">
                🌡️
              </div>
            </div>
            <div class="mt-4">
              <div class="flex items-center text-sm">
                <span :class="realtimeData.temperatureTrend > 0 ? 'text-green-400' : 'text-red-400'">
                  {{ realtimeData.temperatureTrend > 0 ? '↗' : '↘' }} {{ Math.abs(realtimeData.temperatureTrend) }}%
                </span>
                <span class="text-gray-400 ml-2">较昨日</span>
              </div>
            </div>
          </div>

          <div class="glass-effect rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-gray-400 text-sm">平均湿度</p>
                <p class="text-2xl font-bold text-white">{{ realtimeData.humidity }}%</p>
              </div>
              <div class="w-12 h-12 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-full flex items-center justify-center">
                💧
              </div>
            </div>
            <div class="mt-4">
              <div class="flex items-center text-sm">
                <span :class="realtimeData.humidityTrend > 0 ? 'text-green-400' : 'text-red-400'">
                  {{ realtimeData.humidityTrend > 0 ? '↗' : '↘' }} {{ Math.abs(realtimeData.humidityTrend) }}%
                </span>
                <span class="text-gray-400 ml-2">较昨日</span>
              </div>
            </div>
          </div>

          <div class="glass-effect rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-gray-400 text-sm">数据点数</p>
                <p class="text-2xl font-bold text-white">{{ realtimeData.dataPoints }}</p>
              </div>
              <div class="w-12 h-12 bg-gradient-to-r from-purple-500 to-pink-500 rounded-full flex items-center justify-center">
                📊
              </div>
            </div>
            <div class="mt-4">
              <div class="flex items-center text-sm">
                <span class="text-green-400">↗ +{{ realtimeData.dataPointsIncrease }}</span>
                <span class="text-gray-400 ml-2">今日新增</span>
              </div>
            </div>
          </div>

          <div class="glass-effect rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-gray-400 text-sm">活跃设备</p>
                <p class="text-2xl font-bold text-white">{{ realtimeData.activeDevices }}</p>
              </div>
              <div class="w-12 h-12 bg-gradient-to-r from-green-500 to-emerald-500 rounded-full flex items-center justify-center">
                📱
              </div>
            </div>
            <div class="mt-4">
              <div class="flex items-center text-sm">
                <span class="text-green-400">{{ realtimeData.deviceUptime }}%</span>
                <span class="text-gray-400 ml-2">在线率</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 图表区域 -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
          <!-- 温湿度趋势图 -->
          <div class="glass-effect rounded-xl p-6">
            <div class="flex justify-between items-center mb-4">
              <h3 class="text-xl font-semibold text-white">温湿度趋势</h3>
              <select
                v-model="chartTimeRange"
                class="px-3 py-1 bg-gray-800/50 border border-gray-600/50 rounded text-white text-sm focus:outline-none focus:ring-2 focus:ring-cyan-500"
              >
                <option value="1h">最近1小时</option>
                <option value="6h">最近6小时</option>
                <option value="24h">最近24小时</option>
                <option value="7d">最近7天</option>
              </select>
            </div>
            <div class="h-64">
              <canvas ref="trendChart" class="w-full h-full"></canvas>
            </div>
          </div>

          <!-- 设备数据分布 -->
          <div class="glass-effect rounded-xl p-6">
            <h3 class="text-xl font-semibold text-white mb-4">设备数据分布</h3>
            <div class="h-64">
              <canvas ref="distributionChart" class="w-full h-full"></canvas>
            </div>
          </div>
        </div>

        <!-- 数据表格和控制面板 -->
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <!-- 最新数据 -->
          <div class="lg:col-span-2 glass-effect rounded-xl p-6">
            <div class="flex justify-between items-center mb-4">
              <h3 class="text-xl font-semibold text-white">最新数据</h3>
              <div class="flex space-x-2">
                <button
                  @click="refreshData"
                  class="px-3 py-1 bg-cyan-500/20 text-cyan-400 border border-cyan-500/50 rounded hover:bg-cyan-500/30 transition-colors text-sm"
                >
                  刷新
                </button>
                <button
                  @click="exportData"
                  class="px-3 py-1 bg-green-500/20 text-green-400 border border-green-500/50 rounded hover:bg-green-500/30 transition-colors text-sm"
                >
                  导出
                </button>
              </div>
            </div>
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead>
                  <tr class="border-b border-gray-700/50">
                    <th class="text-left py-3 text-gray-300 text-sm">时间</th>
                    <th class="text-left py-3 text-gray-300 text-sm">设备</th>
                    <th class="text-left py-3 text-gray-300 text-sm">温度</th>
                    <th class="text-left py-3 text-gray-300 text-sm">湿度</th>
                    <th class="text-left py-3 text-gray-300 text-sm">状态</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="data in latestData" :key="data.id" class="border-b border-gray-800/50 hover:bg-gray-800/30">
                    <td class="py-3 text-gray-300 text-sm">{{ formatTime(data.timestamp) }}</td>
                    <td class="py-3 text-white text-sm">{{ data.deviceName }}</td>
                    <td class="py-3 text-sm">
                      <span :class="getTemperatureColor(data.temperature)">{{ data.temperature }}°C</span>
                    </td>
                    <td class="py-3 text-sm">
                      <span :class="getHumidityColor(data.humidity)">{{ data.humidity }}%</span>
                    </td>
                    <td class="py-3">
                      <span :class="[
                        'px-2 py-1 text-xs rounded-full',
                        data.status === 'normal' ? 'bg-green-500/20 text-green-400' :
                        data.status === 'warning' ? 'bg-yellow-500/20 text-yellow-400' :
                        'bg-red-500/20 text-red-400'
                      ]">
                        {{ data.status === 'normal' ? '正常' : data.status === 'warning' ? '警告' : '异常' }}
                      </span>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- 控制面板 -->
          <div class="space-y-6">
            <!-- 数据上传 -->
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-white mb-4">数据上传</h4>
              <form @submit.prevent="uploadData" class="space-y-3">
                <input
                  v-model="uploadForm.temperature"
                  type="number"
                  step="0.1"
                  placeholder="温度 (°C)"
                  class="w-full px-3 py-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500 text-sm"
                />
                <input
                  v-model="uploadForm.humidity"
                  type="number"
                  step="0.1"
                  placeholder="湿度 (%)"
                  class="w-full px-3 py-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500 text-sm"
                />
                <select
                  v-model="uploadForm.deviceId"
                  class="w-full px-3 py-2 bg-gray-800/50 border border-gray-600/50 rounded text-white focus:outline-none focus:ring-2 focus:ring-cyan-500 text-sm"
                >
                  <option value="">选择设备</option>
                  <option v-for="device in devices" :key="device.id" :value="device.id">{{ device.name }}</option>
                </select>
                <button
                  type="submit"
                  class="w-full py-2 bg-gradient-to-r from-cyan-500 to-blue-600 text-white rounded hover:from-cyan-600 hover:to-blue-700 transition-all text-sm"
                >
                  上传数据
                </button>
              </form>
            </div>

            <!-- 告警设置 -->
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-white mb-4">告警设置</h4>
              <div class="space-y-3">
                <div>
                  <label class="block text-gray-300 text-sm mb-1">温度上限</label>
                  <input
                    v-model="alertSettings.tempMax"
                    type="number"
                    class="w-full px-3 py-2 bg-gray-800/50 border border-gray-600/50 rounded text-white focus:outline-none focus:ring-2 focus:ring-red-500 text-sm"
                  />
                </div>
                <div>
                  <label class="block text-gray-300 text-sm mb-1">温度下限</label>
                  <input
                    v-model="alertSettings.tempMin"
                    type="number"
                    class="w-full px-3 py-2 bg-gray-800/50 border border-gray-600/50 rounded text-white focus:outline-none focus:ring-2 focus:ring-red-500 text-sm"
                  />
                </div>
                <div>
                  <label class="block text-gray-300 text-sm mb-1">湿度上限</label>
                  <input
                    v-model="alertSettings.humidityMax"
                    type="number"
                    class="w-full px-3 py-2 bg-gray-800/50 border border-gray-600/50 rounded text-white focus:outline-none focus:ring-2 focus:ring-red-500 text-sm"
                  />
                </div>
                <button
                  @click="saveAlertSettings"
                  class="w-full py-2 bg-gradient-to-r from-red-500 to-orange-600 text-white rounded hover:from-red-600 hover:to-orange-700 transition-all text-sm"
                >
                  保存设置
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import axios from 'axios'
import Sidebar from '../components/sidebar.vue'
import TopNav from '../components/top-nav.vue'

const sidebarOpen = ref(true)
const chartTimeRange = ref('1h')
const trendChart = ref(null)
const distributionChart = ref(null)

const realtimeData = ref({
  temperature: 23.5,
  humidity: 65.2,
  dataPoints: 1247,
  activeDevices: 18,
  temperatureTrend: 2.3,
  humidityTrend: -1.2,
  dataPointsIncrease: 156,
  deviceUptime: 94.5
})

const latestData = ref([])
const devices = ref([])

const uploadForm = ref({
  temperature: '',
  humidity: '',
  deviceId: ''
})

const alertSettings = ref({
  tempMax: 35,
  tempMin: 10,
  humidityMax: 80
})

let updateInterval = null

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value
}

const formatTime = (timestamp) => {
  return new Date(timestamp).toLocaleTimeString('zh-CN')
}

const getTemperatureColor = (temp) => {
  if (temp > 30) return 'text-red-400'
  if (temp < 15) return 'text-blue-400'
  return 'text-green-400'
}

const getHumidityColor = (humidity) => {
  if (humidity > 70) return 'text-blue-400'
  if (humidity < 40) return 'text-yellow-400'
  return 'text-green-400'
}

const refreshData = async () => {
  try {
    const response = await axios.get('/iot-data/')
    if (response.data.code === 0) {
      latestData.value = response.data.data.slice(0, 10).map(item => ({
        ...item,
        deviceName: `设备 #${item.deviceId}`,
        timestamp: new Date().getTime() - Math.random() * 3600000,
        status: Math.random() > 0.8 ? 'warning' : Math.random() > 0.95 ? 'error' : 'normal'
      }))
    }
  } catch (error) {
    console.error('获取数据失败:', error)
  }
}

const loadDevices = async () => {
  try {
    const response = await axios.get('/device/')
    if (response.data.code === 0) {
      devices.value = response.data.data
    }
  } catch (error) {
    console.error('获取设备列表失败:', error)
  }
}

const uploadData = async () => {
  try {
    const formData = new URLSearchParams()
    formData.append('temperature', uploadForm.value.temperature)
    formData.append('humidity', uploadForm.value.humidity)
    formData.append('deviceId', uploadForm.value.deviceId)
    
    const response = await axios.post('/iot-data/', formData, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    
    if (response.data.code === 0) {
      uploadForm.value = { temperature: '', humidity: '', deviceId: '' }
      refreshData()
      alert('数据上传成功')
    }
  } catch (error) {
    console.error('上传数据失败:', error)
    alert('上传数据失败')
  }
}

const exportData = () => {
  // 导出数据功能
  const csvContent = "data:text/csv;charset=utf-8," 
    + "时间,设备,温度,湿度,状态\n"
    + latestData.value.map(row => 
        `${formatTime(row.timestamp)},${row.deviceName},${row.temperature},${row.humidity},${row.status}`
      ).join("\n")
  
  const encodedUri = encodeURI(csvContent)
  const link = document.createElement("a")
  link.setAttribute("href", encodedUri)
  link.setAttribute("download", "iot_data.csv")
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}

const saveAlertSettings = () => {
  localStorage.setItem('alertSettings', JSON.stringify(alertSettings.value))
  alert('告警设置已保存')
}

const drawTrendChart = () => {
  const canvas = trendChart.value
  if (!canvas) return
  
  const ctx = canvas.getContext('2d')
  canvas.width = canvas.offsetWidth
  canvas.height = canvas.offsetHeight
  
  ctx.clearRect(0, 0, canvas.width, canvas.height)
  
  // 绘制温度曲线
  ctx.strokeStyle = '#ef4444'
  ctx.lineWidth = 2
  ctx.beginPath()
  for (let i = 0; i < 20; i++) {
    const x = (canvas.width / 20) * i
    const y = canvas.height / 3 + Math.sin(i * 0.3) * 30 + Math.random() * 10
    if (i === 0) ctx.moveTo(x, y)
    else ctx.lineTo(x, y)
  }
  ctx.stroke()
  
  // 绘制湿度曲线
  ctx.strokeStyle = '#3b82f6'
  ctx.lineWidth = 2
  ctx.beginPath()
  for (let i = 0; i < 20; i++) {
    const x = (canvas.width / 20) * i
    const y = canvas.height * 2/3 + Math.sin(i * 0.4) * 25 + Math.random() * 8
    if (i === 0) ctx.moveTo(x, y)
    else ctx.lineTo(x, y)
  }
  ctx.stroke()
}

const drawDistributionChart = () => {
  const canvas = distributionChart.value
  if (!canvas) return
  
  const ctx = canvas.getContext('2d')
  canvas.width = canvas.offsetWidth
  canvas.height = canvas.offsetHeight
  
  ctx.clearRect(0, 0, canvas.width, canvas.height)
  
  // 绘制柱状图
  const bars = 8
  const barWidth = canvas.width / bars * 0.8
  const spacing = canvas.width / bars * 0.2
  
  for (let i = 0; i < bars; i++) {
    const height = Math.random() * canvas.height * 0.8
    const x = i * (barWidth + spacing) + spacing / 2
    const y = canvas.height - height
    
    const gradient = ctx.createLinearGradient(0, y, 0, canvas.height)
    gradient.addColorStop(0, '#8b5cf6')
    gradient.addColorStop(1, '#a855f7')
    
    ctx.fillStyle = gradient
    ctx.fillRect(x, y, barWidth, height)
  }
}

const updateRealtimeData = () => {
  realtimeData.value.temperature = (20 + Math.random() * 15).toFixed(1)
  realtimeData.value.humidity = (50 + Math.random() * 30).toFixed(1)
  realtimeData.value.dataPoints += Math.floor(Math.random() * 5)
  
  // 重绘图表
  drawTrendChart()
  drawDistributionChart()
}

onMounted(() => {
  refreshData()
  loadDevices()
  drawTrendChart()
  drawDistributionChart()
  
  // 加载告警设置
  const savedSettings = localStorage.getItem('alertSettings')
  if (savedSettings) {
    alertSettings.value = JSON.parse(savedSettings)
  }
  
  // 启动实时数据更新
  updateInterval = setInterval(updateRealtimeData, 5000)
})

onUnmounted(() => {
  if (updateInterval) {
    clearInterval(updateInterval)
  }
})
</script>
