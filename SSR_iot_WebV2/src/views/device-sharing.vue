<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-900 via-black to-gray-800">
    <Sidebar :isOpen="sidebarOpen" @toggle="toggleSidebar" />
    
    <div :class="['transition-all duration-300', sidebarOpen ? 'ml-64' : 'ml-16']">
      <TopNav @toggleSidebar="toggleSidebar" />
      
      <div class="p-6">
        <!-- 页面标题 -->
        <div class="flex justify-between items-center mb-8">
          <div>
            <h1 class="text-3xl font-bold text-white mb-2">设备共享</h1>
            <p class="text-gray-400">管理设备共享权限和协作</p>
          </div>
          <button
            @click="showShareModal = true"
            class="px-6 py-3 bg-gradient-to-r from-purple-500 to-pink-600 text-white font-semibold rounded-lg hover:from-purple-600 hover:to-pink-700 transform hover:scale-105 transition-all duration-300 shadow-lg"
          >
            + 共享设备
          </button>
        </div>

        <!-- 统计卡片 -->
        <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
          <div class="glass-effect rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-gray-400 text-sm">我的设备</p>
                <p class="text-2xl font-bold text-white">{{ stats.myDevices }}</p>
              </div>
              <div class="w-12 h-12 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-full flex items-center justify-center">
                📱
              </div>
            </div>
          </div>
          <div class="glass-effect rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-gray-400 text-sm">已共享</p>
                <p class="text-2xl font-bold text-white">{{ stats.sharedDevices }}</p>
              </div>
              <div class="w-12 h-12 bg-gradient-to-r from-green-500 to-emerald-500 rounded-full flex items-center justify-center">
                🤝
              </div>
            </div>
          </div>
          <div class="glass-effect rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-gray-400 text-sm">接收共享</p>
                <p class="text-2xl font-bold text-white">{{ stats.receivedShares }}</p>
              </div>
              <div class="w-12 h-12 bg-gradient-to-r from-purple-500 to-pink-500 rounded-full flex items-center justify-center">
                📥
              </div>
            </div>
          </div>
          <div class="glass-effect rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-gray-400 text-sm">协作用户</p>
                <p class="text-2xl font-bold text-white">{{ stats.collaborators }}</p>
              </div>
              <div class="w-12 h-12 bg-gradient-to-r from-yellow-500 to-orange-500 rounded-full flex items-center justify-center">
                👥
              </div>
            </div>
          </div>
        </div>

        <!-- 选项卡 -->
        <div class="flex space-x-4 mb-6">
          <button
            v-for="tab in tabs"
            :key="tab.key"
            @click="activeTab = tab.key"
            :class="[
              'px-6 py-3 rounded-lg font-medium transition-all duration-300',
              activeTab === tab.key
                ? 'bg-gradient-to-r from-purple-500 to-pink-600 text-white shadow-lg'
                : 'bg-gray-800/50 text-gray-300 border border-gray-600/50 hover:bg-gray-700/50'
            ]"
          >
            {{ tab.name }}
          </button>
        </div>

        <!-- 我共享的设备 -->
        <div v-if="activeTab === 'myShares'" class="glass-effect rounded-xl overflow-hidden">
          <div class="p-6 border-b border-gray-700/50">
            <h3 class="text-xl font-semibold text-white">我共享的设备</h3>
          </div>
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead class="bg-gray-800/50">
                <tr>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase">设备</th>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase">共享给</th>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase">权限</th>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase">共享时间</th>
                  <th class="px-6 py-4 text-left text-xs font-medium text-gray-300 uppercase">操作</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-700/50">
                <tr v-for="share in myShares" :key="share.id" class="hover:bg-gray-800/30">
                  <td class="px-6 py-4">
                    <div class="flex items-center">
                      <div class="w-10 h-10 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-full flex items-center justify-center mr-3">
                        <span class="text-white font-semibold">{{ share.deviceName.charAt(0) }}</span>
                      </div>
                      <div>
                        <p class="text-white font-medium">{{ share.deviceName }}</p>
                        <p class="text-gray-400 text-sm">ID: {{ share.deviceId }}</p>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4">
                    <div class="flex items-center">
                      <div class="w-8 h-8 bg-gradient-to-r from-purple-500 to-pink-500 rounded-full flex items-center justify-center mr-2">
                        <span class="text-white text-sm">{{ share.sharedWith.charAt(0) }}</span>
                      </div>
                      <span class="text-white">{{ share.sharedWith }}</span>
                    </div>
                  </td>
                  <td class="px-6 py-4">
                    <span :class="[
                      'px-2 py-1 text-xs font-medium rounded-full',
                      share.permission === 'read' ? 'bg-blue-500/20 text-blue-400' : 'bg-green-500/20 text-green-400'
                    ]">
                      {{ share.permission === 'read' ? '只读' : '读写' }}
                    </span>
                  </td>
                  <td class="px-6 py-4 text-gray-300">{{ share.sharedAt }}</td>
                  <td class="px-6 py-4 space-x-2">
                    <button
                      @click="editPermission(share)"
                      class="text-yellow-400 hover:text-yellow-300 transition-colors"
                    >
                      编辑
                    </button>
                    <button
                      @click="revokeShare(share)"
                      class="text-red-400 hover:text-red-300 transition-colors"
                    >
                      撤销
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- 共享给我的设备 -->
        <div v-if="activeTab === 'receivedShares'" class="glass-effect rounded-xl overflow-hidden">
          <div class="p-6 border-b border-gray-700/50">
            <h3 class="text-xl font-semibold text-white">共享给我的设备</h3>
          </div>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 p-6">
            <div v-for="device in receivedShares" :key="device.id" class="bg-gray-800/30 rounded-xl p-6 hover:bg-gray-700/30 transition-colors">
              <div class="flex items-center justify-between mb-4">
                <div class="w-12 h-12 bg-gradient-to-r from-green-500 to-emerald-500 rounded-full flex items-center justify-center">
                  <span class="text-white font-semibold">{{ device.deviceName.charAt(0) }}</span>
                </div>
                <span :class="[
                  'px-2 py-1 text-xs font-medium rounded-full',
                  device.permission === 'read' ? 'bg-blue-500/20 text-blue-400' : 'bg-green-500/20 text-green-400'
                ]">
                  {{ device.permission === 'read' ? '只读' : '读写' }}
                </span>
              </div>
              <h4 class="text-white font-semibold mb-2">{{ device.deviceName }}</h4>
              <p class="text-gray-400 text-sm mb-2">来自: {{ device.owner }}</p>
              <p class="text-gray-400 text-sm mb-4">共享时间: {{ device.sharedAt }}</p>
              <div class="flex space-x-2">
                <button class="flex-1 px-3 py-2 bg-cyan-500/20 text-cyan-400 border border-cyan-500/50 rounded hover:bg-cyan-500/30 transition-colors text-sm">
                  查看数据
                </button>
                <button
                  v-if="device.permission === 'write'"
                  class="flex-1 px-3 py-2 bg-green-500/20 text-green-400 border border-green-500/50 rounded hover:bg-green-500/30 transition-colors text-sm"
                >
                  控制设备
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- 协作用户 -->
        <div v-if="activeTab === 'collaborators'" class="glass-effect rounded-xl p-6">
          <h3 class="text-xl font-semibold text-white mb-6">协作用户</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div v-for="user in collaborators" :key="user.id" class="bg-gray-800/30 rounded-xl p-6">
              <div class="flex items-center mb-4">
                <div class="w-12 h-12 bg-gradient-to-r from-purple-500 to-pink-500 rounded-full flex items-center justify-center mr-4">
                  <span class="text-white font-semibold">{{ user.name.charAt(0) }}</span>
                </div>
                <div>
                  <h4 class="text-white font-semibold">{{ user.name }}</h4>
                  <p class="text-gray-400 text-sm">{{ user.email }}</p>
                </div>
              </div>
              <div class="space-y-2">
                <div class="flex justify-between text-sm">
                  <span class="text-gray-400">共享设备:</span>
                  <span class="text-white">{{ user.sharedDevices }}</span>
                </div>
                <div class="flex justify-between text-sm">
                  <span class="text-gray-400">最后活动:</span>
                  <span class="text-white">{{ user.lastActive }}</span>
                </div>
              </div>
              <button class="w-full mt-4 px-3 py-2 bg-red-500/20 text-red-400 border border-red-500/50 rounded hover:bg-red-500/30 transition-colors text-sm">
                移除协作
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 共享设备模态框 -->
    <div v-if="showShareModal" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div class="glass-effect rounded-xl p-6 w-full max-w-md mx-4">
        <h3 class="text-xl font-semibold text-white mb-4">共享设备</h3>
        <form @submit.prevent="shareDevice" class="space-y-4">
          <div>
            <label class="block text-gray-300 text-sm font-medium mb-2">选择设备</label>
            <select
              v-model="shareForm.deviceId"
              class="w-full px-4 py-2 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-purple-500"
              required
            >
              <option value="">选择要共享的设备</option>
              <option v-for="device in myDevices" :key="device.id" :value="device.id">{{ device.name }}</option>
            </select>
          </div>
          <div>
            <label class="block text-gray-300 text-sm font-medium mb-2">用户ID</label>
            <input
              v-model="shareForm.userId"
              type="number"
              class="w-full px-4 py-2 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-500"
              placeholder="输入要共享给的用户ID"
              required
            />
          </div>
          <div>
            <label class="block text-gray-300 text-sm font-medium mb-2">权限</label>
            <select
              v-model="shareForm.permission"
              class="w-full px-4 py-2 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-purple-500"
              required
            >
              <option value="">选择权限</option>
              <option value="read">只读</option>
              <option value="write">读写</option>
            </select>
          </div>
          <div class="flex space-x-4 pt-4">
            <button
              type="button"
              @click="showShareModal = false"
              class="flex-1 px-4 py-2 bg-gray-700/50 text-gray-300 rounded-lg hover:bg-gray-600/50 transition-colors"
            >
              取消
            </button>
            <button
              type="submit"
              class="flex-1 px-4 py-2 bg-gradient-to-r from-purple-500 to-pink-600 text-white rounded-lg hover:from-purple-600 hover:to-pink-700 transition-all"
            >
              共享
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import Sidebar from '../components/sidebar.vue'
import TopNav from '../components/top-nav.vue'

const sidebarOpen = ref(true)
const showShareModal = ref(false)
const activeTab = ref('myShares')

const tabs = [
  { key: 'myShares', name: '我的共享' },
  { key: 'receivedShares', name: '接收的共享' },
  { key: 'collaborators', name: '协作用户' }
]

const stats = ref({
  myDevices: 12,
  sharedDevices: 8,
  receivedShares: 5,
  collaborators: 3
})

const myShares = ref([
  {
    id: 1,
    deviceId: 1,
    deviceName: '温度传感器 #001',
    sharedWith: '张三',
    permission: 'read',
    sharedAt: '2024-01-15'
  },
  {
    id: 2,
    deviceId: 2,
    deviceName: '湿度传感器 #002',
    sharedWith: '李四',
    permission: 'write',
    sharedAt: '2024-01-14'
  }
])

const receivedShares = ref([
  {
    id: 1,
    deviceName: '智能开关 #003',
    owner: '王五',
    permission: 'write',
    sharedAt: '2024-01-13'
  },
  {
    id: 2,
    deviceName: '空气质量监测器',
    owner: '赵六',
    permission: 'read',
    sharedAt: '2024-01-12'
  }
])

const collaborators = ref([
  {
    id: 1,
    name: '张三',
    email: 'zhangsan@example.com',
    sharedDevices: 3,
    lastActive: '2小时前'
  },
  {
    id: 2,
    name: '李四',
    email: 'lisi@example.com',
    sharedDevices: 2,
    lastActive: '1天前'
  }
])

const myDevices = ref([])

const shareForm = ref({
  deviceId: '',
  userId: '',
  permission: ''
})

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value
}

const loadMyDevices = async () => {
  try {
    const token = localStorage.getItem('token')
    const response = await axios.get('/user/devices', {
      headers: {
        'Authorization': token
      }
    })
    if (response.data.code === 0) {
      myDevices.value = response.data.data
    }
  } catch (error) {
    console.error('获取我的设备失败:', error)
  }
}

const shareDevice = async () => {
  try {
    const response = await axios.post('/user-device/share', shareForm.value)
    if (response.data.code === 0) {
      showShareModal.value = false
      shareForm.value = { deviceId: '', userId: '', permission: '' }
      alert('设备共享成功')
      // 刷新共享列表
      loadShares()
    }
  } catch (error) {
    console.error('共享设备失败:', error)
    alert('共享设备失败')
  }
}

const loadShares = async () => {
  // 这里可以调用API获取共享列表
  console.log('加载共享列表')
}

const editPermission = (share) => {
  console.log('编辑权限:', share)
}

const revokeShare = async (share) => {
  if (confirm(`确定要撤销对 "${share.sharedWith}" 的设备共享吗？`)) {
    try {
      const response = await axios.delete(`/user-device/${share.id}`)
      if (response.data.code === 0) {
        alert('共享已撤销')
        loadShares()
      }
    } catch (error) {
      console.error('撤销共享失败:', error)
      alert('撤销共享失败')
    }
  }
}

onMounted(() => {
  loadMyDevices()
  loadShares()
})
</script>
