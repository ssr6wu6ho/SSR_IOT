<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-900 via-black to-gray-800">
    <Sidebar :isOpen="sidebarOpen" @toggle="toggleSidebar" />
    
    <div :class="['transition-all duration-300', sidebarOpen ? 'ml-64' : 'ml-16']">
      <TopNav @toggleSidebar="toggleSidebar" />
      
      <div class="p-6">
        <!-- 页面标题和操作 -->
        <div class="flex justify-between items-center mb-8">
          <div>
            <h1 class="text-3xl font-bold text-white mb-2">设备管理</h1>
            <p class="text-gray-400">管理您的所有物联网设备</p>
          </div>
          <button
            @click="showAddDevice = true"
            class="px-6 py-3 bg-gradient-to-r from-cyan-500 to-blue-600 text-white font-semibold rounded-lg hover:from-cyan-600 hover:to-blue-700 transform hover:scale-105 transition-all duration-300 shadow-lg"
          >
            + 添加设备
          </button>
        </div>

        <!-- 搜索和筛选 -->
        <div class="glass-effect rounded-xl p-6 mb-6">
          <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div>
              <label class="block text-gray-300 text-sm font-medium mb-2">搜索设备</label>
              <input
                v-model="searchQuery"
                type="text"
                placeholder="输入设备名称或ID"
                class="w-full px-4 py-2 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500"
              />
            </div>
            <div>
              <label class="block text-gray-300 text-sm font-medium mb-2">设备类型</label>
              <select
                v-model="selectedType"
                class="w-full px-4 py-2 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-cyan-500"
              >
                <option value="">所有类型</option>
                <option v-for="type in deviceTypes" :key="type.id" :value="type.id">{{ type.name }}</option>
              </select>
            </div>
            <div>
              <label class="block text-gray-300 text-sm font-medium mb-2">状态</label>
              <select
                v-model="selectedStatus"
                class="w-full px-4 py-2 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-cyan-500"
              >
                <option value="">所有状态</option>
                <option value="online">在线</option>
                <option value="offline">离线</option>
                <option value="maintenance">维护中</option>
              </select>
            </div>
            <div class="flex items-end">
              <button
                @click="refreshDevices"
                class="w-full px-4 py-2 bg-gray-700/50 text-gray-300 border border-gray-600/50 rounded-lg hover:bg-gray-600/50 transition-all duration-300"
              >
                刷新列表
              </button>
            </div>
          </div>
        </div>

        <!-- 设备列表 -->
        <div class="glass-effect rounded-xl overflow-hidden">
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead class="bg-gray-800/50">
                <tr>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">设备信息</th>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">类型</th>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">状态</th>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">最后数据</th>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase tracking-wider">操作</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-700/50">
                <tr v-for="device in filteredDevices" :key="device.id" class="hover:bg-gray-800/30 transition-colors">
                  <td class="px-6 py-4">
                    <div class="flex items-center">
                      <div class="flex-shrink-0 h-10 w-10">
                        <div class="h-10 w-10 rounded-full bg-gradient-to-r from-cyan-500 to-blue-600 flex items-center justify-center">
                          <span class="text-white font-semibold">{{ device.name.charAt(0) }}</span>
                        </div>
                      </div>
                      <div class="ml-4">
                        <div class="text-sm font-medium text-white">{{ device.name }}</div>
                        <div class="text-sm text-gray-400">ID: {{ device.id }}</div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4">
                    <span class="px-2 py-1 text-xs font-medium bg-purple-500/20 text-purple-400 rounded-full">
                      {{ getDeviceTypeName(device.typeId) }}
                    </span>
                  </td>
                  <td class="px-6 py-4">
                    <span :class="[
                      'px-2 py-1 text-xs font-medium rounded-full',
                      device.status === 'online' ? 'bg-green-500/20 text-green-400' :
                      device.status === 'offline' ? 'bg-red-500/20 text-red-400' :
                      'bg-yellow-500/20 text-yellow-400'
                    ]">
                      {{ device.status === 'online' ? '在线' : device.status === 'offline' ? '离线' : '维护中' }}
                    </span>
                  </td>
                  <td class="px-6 py-4 text-sm text-gray-300">
                    {{ device.lastData || '暂无数据' }}
                  </td>
                  <td class="px-6 py-4 text-sm font-medium space-x-2">
                    <button
                      @click="viewDevice(device)"
                      class="text-cyan-400 hover:text-cyan-300 transition-colors"
                    >
                      查看
                    </button>
                    <button
                      @click="editDevice(device)"
                      class="text-yellow-400 hover:text-yellow-300 transition-colors"
                    >
                      编辑
                    </button>
                    <button
                      @click="deleteDevice(device)"
                      class="text-red-400 hover:text-red-300 transition-colors"
                    >
                      删除
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- 分页 -->
        <div class="flex justify-between items-center mt-6">
          <div class="text-sm text-gray-400">
            显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ Math.min(currentPage * pageSize, totalDevices) }} 条，共 {{ totalDevices }} 条
          </div>
          <div class="flex space-x-2">
            <button
              @click="currentPage--"
              :disabled="currentPage === 1"
              class="px-3 py-1 bg-gray-700/50 text-gray-300 rounded disabled:opacity-50 hover:bg-gray-600/50 transition-colors"
            >
              上一页
            </button>
            <button
              @click="currentPage++"
              :disabled="currentPage * pageSize >= totalDevices"
              class="px-3 py-1 bg-gray-700/50 text-gray-300 rounded disabled:opacity-50 hover:bg-gray-600/50 transition-colors"
            >
              下一页
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加设备模态框 -->
    <div v-if="showAddDevice" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div class="glass-effect rounded-xl p-6 w-full max-w-md mx-4">
        <h3 class="text-xl font-semibold text-white mb-4">添加新设备</h3>
        <form @submit.prevent="addDevice" class="space-y-4">
          <div>
            <label class="block text-gray-300 text-sm font-medium mb-2">设备名称</label>
            <input
              v-model="newDevice.name"
              type="text"
              class="w-full px-4 py-2 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500"
              placeholder="输入设备名称"
              required
            />
          </div>
          <div>
            <label class="block text-gray-300 text-sm font-medium mb-2">设备类型</label>
            <select
              v-model="newDevice.typeId"
              class="w-full px-4 py-2 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-cyan-500"
              required
            >
              <option value="">选择设备类型</option>
              <option v-for="type in deviceTypes" :key="type.id" :value="type.id">{{ type.name }}</option>
            </select>
          </div>
          <div class="flex space-x-4 pt-4">
            <button
              type="button"
              @click="showAddDevice = false"
              class="flex-1 px-4 py-2 bg-gray-700/50 text-gray-300 rounded-lg hover:bg-gray-600/50 transition-colors"
            >
              取消
            </button>
            <button
              type="submit"
              class="flex-1 px-4 py-2 bg-gradient-to-r from-cyan-500 to-blue-600 text-white rounded-lg hover:from-cyan-600 hover:to-blue-700 transition-all"
            >
              添加
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import Sidebar from '../components/sidebar.vue'
import TopNav from '../components/top-nav.vue'

const sidebarOpen = ref(true)
const showAddDevice = ref(false)
const searchQuery = ref('')
const selectedType = ref('')
const selectedStatus = ref('')
const currentPage = ref(1)
const pageSize = ref(10)

const devices = ref([])
const deviceTypes = ref([])
const totalDevices = computed(() => devices.value.length)

const newDevice = ref({
  name: '',
  typeId: ''
})

const filteredDevices = computed(() => {
  let filtered = devices.value

  if (searchQuery.value) {
    filtered = filtered.filter(device => 
      device.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      device.id.toString().includes(searchQuery.value)
    )
  }

  if (selectedType.value) {
    filtered = filtered.filter(device => device.typeId === parseInt(selectedType.value))
  }

  if (selectedStatus.value) {
    filtered = filtered.filter(device => device.status === selectedStatus.value)
  }

  return filtered.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value)
})

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value
}

const getDeviceTypeName = (typeId) => {
  const type = deviceTypes.value.find(t => t.id === typeId)
  return type ? type.name : '未知类型'
}

const refreshDevices = async () => {
  try {
    const response = await axios.get('/device/')
    if (response.data.code === 0) {
      devices.value = response.data.data.map(device => ({
        ...device,
        status: Math.random() > 0.3 ? 'online' : Math.random() > 0.5 ? 'offline' : 'maintenance',
        lastData: Math.random() > 0.5 ? `${(Math.random() * 30 + 10).toFixed(1)}°C` : null
      }))
    }
  } catch (error) {
    console.error('获取设备列表失败:', error)
  }
}

const loadDeviceTypes = async () => {
  try {
    const response = await axios.get('/device-type/')
    if (response.data.code === 0) {
      deviceTypes.value = response.data.data
    }
  } catch (error) {
    console.error('获取设备类型失败:', error)
  }
}

const addDevice = async () => {
  try {
    const response = await axios.post('/device/', newDevice.value)
    if (response.data.code === 0) {
      showAddDevice.value = false
      newDevice.value = { name: '', typeId: '' }
      refreshDevices()
      alert('设备添加成功')
    }
  } catch (error) {
    console.error('添加设备失败:', error)
    alert('添加设备失败')
  }
}

const viewDevice = (device) => {
  // 跳转到设备详情页面
  console.log('查看设备:', device)
}

const editDevice = (device) => {
  // 编辑设备
  console.log('编辑设备:', device)
}

const deleteDevice = async (device) => {
  if (confirm(`确定要删除设备 "${device.name}" 吗？`)) {
    try {
      const response = await axios.delete(`/device/${device.id}`)
      if (response.data.code === 0) {
        refreshDevices()
        alert('设备删除成功')
      }
    } catch (error) {
      console.error('删除设备失败:', error)
      alert('删除设备失败')
    }
  }
}

onMounted(() => {
  refreshDevices()
  loadDeviceTypes()
})
</script>
