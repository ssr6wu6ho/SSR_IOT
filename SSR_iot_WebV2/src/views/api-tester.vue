<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-900 via-black to-gray-800">
    <Sidebar :isOpen="sidebarOpen" @toggle="toggleSidebar" />
    
    <div :class="['transition-all duration-300', sidebarOpen ? 'ml-64' : 'ml-16']">
      <TopNav @toggleSidebar="toggleSidebar" />
      
      <div class="p-6">
        <!-- 页面标题 -->
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-white mb-2">API 测试工具</h1>
          <p class="text-gray-400">测试和调试您的物联网API接口</p>
        </div>

        <!-- 功能选项卡 -->
        <div class="flex flex-wrap gap-4 justify-center mb-8">
          <button
            v-for="tab in tabs"
            :key="tab.key"
            @click="activeTab = tab.key"
            :class="[
              'px-6 py-3 rounded-lg font-medium transition-all duration-300 transform hover:scale-105',
              activeTab === tab.key
                ? 'bg-gradient-to-r from-cyan-500 to-purple-600 text-white shadow-lg'
                : 'bg-gray-800/50 text-gray-300 border border-gray-600/50 hover:bg-gray-700/50'
            ]"
          >
            {{ tab.name }}
          </button>
        </div>

        <!-- 用户管理测试 -->
        <div v-if="activeTab === 'user'" class="space-y-6">
          <h3 class="text-2xl font-bold text-white mb-6">用户管理测试</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-cyan-400 mb-4">获取用户设备</h4>
              <button
                @click="getUserDevices"
                class="w-full py-2 bg-cyan-500/20 text-cyan-400 border border-cyan-500/50 rounded-lg hover:bg-cyan-500/30 transition-all duration-300"
              >
                获取我的设备
              </button>
            </div>
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-purple-400 mb-4">获取共享设备</h4>
              <button
                @click="getUserSharedDevices"
                class="w-full py-2 bg-purple-500/20 text-purple-400 border border-purple-500/50 rounded-lg hover:bg-purple-500/30 transition-all duration-300"
              >
                获取共享设备
              </button>
            </div>
          </div>
        </div>

        <!-- 设备类型管理测试 -->
        <div v-if="activeTab === 'deviceType'" class="space-y-6">
          <h3 class="text-2xl font-bold text-white mb-6">设备类型管理测试</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-green-400 mb-4">获取所有设备类型</h4>
              <button
                @click="getAllDeviceTypes"
                class="w-full py-2 bg-green-500/20 text-green-400 border border-green-500/50 rounded-lg hover:bg-green-500/30 transition-all duration-300"
              >
                获取设备类型
              </button>
            </div>
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-yellow-400 mb-4">添加设备类型</h4>
              <input
                v-model="newDeviceType.name"
                type="text"
                placeholder="设备类型名称"
                class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-yellow-500"
              />
              <button
                @click="addDeviceType"
                class="w-full py-2 bg-yellow-500/20 text-yellow-400 border border-yellow-500/50 rounded-lg hover:bg-yellow-500/30 transition-all duration-300"
              >
                添加类型
              </button>
            </div>
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-red-400 mb-4">删除设备类型</h4>
              <input
                v-model="deleteDeviceTypeId"
                type="number"
                placeholder="设备类型ID"
                class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500"
              />
              <button
                @click="deleteDeviceType"
                class="w-full py-2 bg-red-500/20 text-red-400 border border-red-500/50 rounded-lg hover:bg-red-500/30 transition-all duration-300"
              >
                删除类型
              </button>
            </div>
          </div>
        </div>

        <!-- 设备管理测试 -->
        <div v-if="activeTab === 'device'" class="space-y-6">
          <h3 class="text-2xl font-bold text-white mb-6">设备管理测试</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-blue-400 mb-4">获取所有设备</h4>
              <button
                @click="getAllDevices"
                class="w-full py-2 bg-blue-500/20 text-blue-400 border border-blue-500/50 rounded-lg hover:bg-blue-500/30 transition-all duration-300"
              >
                获取设备列表
              </button>
            </div>
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-green-400 mb-4">添加设备</h4>
              <input
                v-model="newDevice.name"
                type="text"
                placeholder="设备名称"
                class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-green-500"
              />
              <input
                v-model="newDevice.typeId"
                type="number"
                placeholder="设备类型ID"
                class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-green-500"
              />
              <button
                @click="addDevice"
                class="w-full py-2 bg-green-500/20 text-green-400 border border-green-500/50 rounded-lg hover:bg-green-500/30 transition-all duration-300"
              >
                添加设备
              </button>
            </div>
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-orange-400 mb-4">获取设备数据</h4>
              <input
                v-model="deviceDataId"
                type="number"
                placeholder="设备ID"
                class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-orange-500"
              />
              <button
                @click="getDeviceData"
                class="w-full py-2 bg-orange-500/20 text-orange-400 border border-orange-500/50 rounded-lg hover:bg-orange-500/30 transition-all duration-300"
              >
                获取数据
              </button>
            </div>
          </div>
        </div>

        <!-- IoT数据管理测试 -->
        <div v-if="activeTab === 'iotData'" class="space-y-6">
          <h3 class="text-2xl font-bold text-white mb-6">IoT数据管理测试</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-cyan-400 mb-4">上传数据</h4>
              <input
                v-model="iotData.temperature"
                type="number"
                step="0.1"
                placeholder="温度"
                class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500"
              />
              <input
                v-model="iotData.humidity"
                type="number"
                step="0.1"
                placeholder="湿度"
                class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500"
              />
              <input
                v-model="iotData.deviceId"
                type="number"
                placeholder="设备ID"
                class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500"
              />
              <button
                @click="postIotData"
                class="w-full py-2 bg-cyan-500/20 text-cyan-400 border border-cyan-500/50 rounded-lg hover:bg-cyan-500/30 transition-all duration-300"
              >
                上传数据
              </button>
            </div>
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-purple-400 mb-4">获取数据</h4>
              <button
                @click="getAllIotData"
                class="w-full py-2 mb-3 bg-purple-500/20 text-purple-400 border border-purple-500/50 rounded-lg hover:bg-purple-500/30 transition-all duration-300"
              >
                获取所有数据
              </button>
              <input
                v-model="searchDeviceId"
                type="number"
                placeholder="搜索设备ID"
                class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-500"
              />
              <button
                @click="searchIotData"
                class="w-full py-2 bg-purple-500/20 text-purple-400 border border-purple-500/50 rounded-lg hover:bg-purple-500/30 transition-all duration-300"
              >
                搜索数据
              </button>
            </div>
          </div>
        </div>

        <!-- 设备共享管理测试 -->
        <div v-if="activeTab === 'userDevice'" class="space-y-6">
          <h3 class="text-2xl font-bold text-white mb-6">设备共享管理测试</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-pink-400 mb-4">共享设备</h4>
              <input
                v-model="shareDevice.userId"
                type="number"
                placeholder="用户ID"
                class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-pink-500"
              />
              <input
                v-model="shareDevice.deviceId"
                type="number"
                placeholder="设备ID"
                class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-pink-500"
              />
              <select
                v-model="shareDevice.permission"
                class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white focus:outline-none focus:ring-2 focus:ring-pink-500"
              >
                <option value="">选择权限</option>
                <option value="read">只读</option>
                <option value="write">读写</option>
              </select>
              <button
                @click="shareDeviceToUser"
                class="w-full py-2 bg-pink-500/20 text-pink-400 border border-pink-500/50 rounded-lg hover:bg-pink-500/30 transition-all duration-300"
              >
                共享设备
              </button>
            </div>
            <div class="glass-effect rounded-xl p-6">
              <h4 class="text-lg font-semibold text-indigo-400 mb-4">查询共享</h4>
              <input
                v-model="queryUserId"
                type="number"
                placeholder="用户ID"
                class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
              <button
                @click="getUserDeviceShares"
                class="w-full py-2 bg-indigo-500/20 text-indigo-400 border border-indigo-500/50 rounded-lg hover:bg-indigo-500/30 transition-all duration-300"
              >
                查询用户设备
              </button>
            </div>
          </div>
        </div>

        <!-- 响应结果显示 -->
        <div v-if="apiResponse" class="glass-effect rounded-xl p-6 mt-8">
          <h4 class="text-lg font-semibold text-green-400 mb-4">API响应结果</h4>
          <pre class="bg-gray-800/50 p-4 rounded-lg text-green-300 text-sm overflow-auto max-h-96 border border-gray-600/50">{{ JSON.stringify(apiResponse, null, 2) }}</pre>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import Sidebar from '../components/sidebar.vue'
import TopNav from '../components/top-nav.vue'

const sidebarOpen = ref(true)
const activeTab = ref('user')
const apiResponse = ref(null)

const tabs = [
  { key: 'user', name: '用户管理' },
  { key: 'deviceType', name: '设备类型' },
  { key: 'device', name: '设备管理' },
  { key: 'iotData', name: 'IoT数据' },
  { key: 'userDevice', name: '设备共享' }
]

const newDeviceType = ref({ name: '' })
const deleteDeviceTypeId = ref('')
const newDevice = ref({ name: '', typeId: '' })
const deviceDataId = ref('')
const iotData = ref({ temperature: '', humidity: '', deviceId: '' })
const searchDeviceId = ref('')
const shareDevice = ref({ userId: '', deviceId: '', permission: '' })
const queryUserId = ref('')

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value
}

// API测试方法
const getUserDevices = async () => {
  try {
    const token = localStorage.getItem('token')
    const result = await axios.get('/user/devices', {
      headers: { 'Authorization': token }
    })
    apiResponse.value = result.data
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const getUserSharedDevices = async () => {
  try {
    const token = localStorage.getItem('token')
    const result = await axios.get('/user/shared-devices', {
      headers: { 'Authorization': token }
    })
    apiResponse.value = result.data
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const getAllDeviceTypes = async () => {
  try {
    const result = await axios.get('/device-type/')
    apiResponse.value = result.data
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const addDeviceType = async () => {
  try {
    const result = await axios.post('/device-type/', newDeviceType.value)
    apiResponse.value = result.data
    newDeviceType.value.name = ''
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const deleteDeviceType = async () => {
  try {
    const result = await axios.delete(`/device-type/${deleteDeviceTypeId.value}`)
    apiResponse.value = result.data
    deleteDeviceTypeId.value = ''
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const getAllDevices = async () => {
  try {
    const result = await axios.get('/device/')
    apiResponse.value = result.data
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const addDevice = async () => {
  try {
    const result = await axios.post('/device/', newDevice.value)
    apiResponse.value = result.data
    newDevice.value = { name: '', typeId: '' }
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const getDeviceData = async () => {
  try {
    const result = await axios.get(`/device/${deviceDataId.value}/data`)
    apiResponse.value = result.data
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const postIotData = async () => {
  try {
    const formData = new URLSearchParams()
    formData.append('temperature', iotData.value.temperature)
    formData.append('humidity', iotData.value.humidity)
    formData.append('deviceId', iotData.value.deviceId)
    
    const result = await axios.post('/iot-data/', formData, {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    })
    apiResponse.value = result.data
    iotData.value = { temperature: '', humidity: '', deviceId: '' }
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const getAllIotData = async () => {
  try {
    const result = await axios.get('/iot-data/')
    apiResponse.value = result.data
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const searchIotData = async () => {
  try {
    const result = await axios.get(`/iot-data/search?deviceId=${searchDeviceId.value}`)
    apiResponse.value = result.data
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const shareDeviceToUser = async () => {
  try {
    const result = await axios.post('/user-device/share', shareDevice.value)
    apiResponse.value = result.data
    shareDevice.value = { userId: '', deviceId: '', permission: '' }
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}

const getUserDeviceShares = async () => {
  try {
    const result = await axios.get(`/user-device/user/${queryUserId.value}`)
    apiResponse.value = result.data
  } catch (err) {
    apiResponse.value = err.response?.data || { error: err.message }
  }
}
</script>
