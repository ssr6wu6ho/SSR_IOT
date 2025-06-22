<template>
    <div id="app" class="min-h-screen bg-gradient-to-br from-gray-900 via-black to-gray-800">
        <!-- 背景动画效果 -->
        <div class="fixed inset-0 overflow-hidden pointer-events-none">
            <div
                class="absolute -top-40 -right-40 w-80 h-80 bg-purple-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse">
            </div>
            <div
                class="absolute -bottom-40 -left-40 w-80 h-80 bg-cyan-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse animation-delay-2000">
            </div>
            <div
                class="absolute top-40 left-1/2 w-80 h-80 bg-pink-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse animation-delay-4000">
            </div>
        </div>

        <!-- 主容器 -->
        <div class="relative z-10">
            <!-- 顶部导航 -->
            <nav class="bg-black/50 backdrop-blur-md border-b border-gray-700/50">
                <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div class="flex items-center justify-between h-16">
                        <div class="flex items-center">
                            <h1
                                class="text-2xl font-bold bg-gradient-to-r from-cyan-400 to-purple-500 bg-clip-text text-transparent">
                                IoT API Tester
                            </h1>
                        </div>
                        <div class="flex items-center space-x-4">
                            <div v-if="userJWT" class="text-green-400 text-sm">
                                已登录 ✓
                            </div>
                            <button v-if="userJWT" @click="logout"
                                class="px-4 py-2 bg-red-500/20 text-red-400 border border-red-500/50 rounded-lg hover:bg-red-500/30 transition-all duration-300">
                                退出登录
                            </button>
                        </div>
                    </div>
                </div>
            </nav>

            <!-- 主内容区域 -->
            <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                <!-- 登录表单 -->
                <div v-if="!userJWT" class="max-w-md mx-auto">
                    <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-2xl p-8 shadow-2xl">
                        <h2
                            class="text-3xl font-bold text-center mb-8 bg-gradient-to-r from-cyan-400 to-purple-500 bg-clip-text text-transparent">
                            用户登录
                        </h2>
                        <form @submit.prevent="login" class="space-y-6">
                            <div>
                                <label class="block text-gray-300 text-sm font-medium mb-2">用户名</label>
                                <input v-model="user.username" type="text"
                                    class="w-full px-4 py-3 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500 focus:border-transparent transition-all duration-300"
                                    placeholder="请输入用户名" required />
                            </div>
                            <div>
                                <label class="block text-gray-300 text-sm font-medium mb-2">密码</label>
                                <input v-model="user.password" type="password"
                                    class="w-full px-4 py-3 bg-gray-800/50 border border-gray-600/50 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500 focus:border-transparent transition-all duration-300"
                                    placeholder="请输入密码" required />
                            </div>
                            <button type="submit"
                                class="w-full py-3 bg-gradient-to-r from-cyan-500 to-purple-600 text-white font-semibold rounded-lg hover:from-cyan-600 hover:to-purple-700 transform hover:scale-105 transition-all duration-300 shadow-lg">
                                登录
                            </button>
                        </form>

                        <!-- 注册按钮 -->
                        <div class="mt-6 text-center">
                            <button @click="register"
                                class="text-cyan-400 hover:text-cyan-300 transition-colors duration-300">
                                没有账号？点击注册
                            </button>
                        </div>
                    </div>
                </div>

                <!-- 主功能区域 -->
                <div v-else class="space-y-8">
                    <!-- 功能选项卡 -->
                    <div class="flex flex-wrap gap-4 justify-center">
                        <button v-for="tab in tabs" :key="tab.key" @click="activeTab = tab.key" :class="[
                            'px-6 py-3 rounded-lg font-medium transition-all duration-300 transform hover:scale-105',
                            activeTab === tab.key
                                ? 'bg-gradient-to-r from-cyan-500 to-purple-600 text-white shadow-lg'
                                : 'bg-gray-800/50 text-gray-300 border border-gray-600/50 hover:bg-gray-700/50'
                        ]">
                            {{ tab.name }}
                        </button>
                    </div>

                    <!-- 用户管理 -->
                    <div v-if="activeTab === 'user'" class="space-y-6">
                        <h3 class="text-2xl font-bold text-white mb-6">用户管理</h3>
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-cyan-400 mb-4">获取用户设备</h4>
                                <button @click="getUserDevices"
                                    class="w-full py-2 bg-cyan-500/20 text-cyan-400 border border-cyan-500/50 rounded-lg hover:bg-cyan-500/30 transition-all duration-300">
                                    获取我的设备
                                </button>
                            </div>
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-purple-400 mb-4">获取共享设备</h4>
                                <button @click="getUserSharedDevices"
                                    class="w-full py-2 bg-purple-500/20 text-purple-400 border border-purple-500/50 rounded-lg hover:bg-purple-500/30 transition-all duration-300">
                                    获取共享设备
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- 设备类型管理 -->
                    <div v-if="activeTab === 'deviceType'" class="space-y-6">
                        <h3 class="text-2xl font-bold text-white mb-6">设备类型管理</h3>
                        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-green-400 mb-4">获取所有设备类型</h4>
                                <button @click="getAllDeviceTypes"
                                    class="w-full py-2 bg-green-500/20 text-green-400 border border-green-500/50 rounded-lg hover:bg-green-500/30 transition-all duration-300">
                                    获取设备类型
                                </button>
                            </div>
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-yellow-400 mb-4">添加设备类型</h4>
                                <input v-model="newDeviceType.name" type="text" placeholder="设备类型名称"
                                    class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-yellow-500" />
                                <button @click="addDeviceType"
                                    class="w-full py-2 bg-yellow-500/20 text-yellow-400 border border-yellow-500/50 rounded-lg hover:bg-yellow-500/30 transition-all duration-300">
                                    添加类型
                                </button>
                            </div>
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-red-400 mb-4">删除设备类型</h4>
                                <input v-model="deleteDeviceTypeId" type="number" placeholder="设备类型ID"
                                    class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-red-500" />
                                <button @click="deleteDeviceType"
                                    class="w-full py-2 bg-red-500/20 text-red-400 border border-red-500/50 rounded-lg hover:bg-red-500/30 transition-all duration-300">
                                    删除类型
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- 设备管理 -->
                    <div v-if="activeTab === 'device'" class="space-y-6">
                        <h3 class="text-2xl font-bold text-white mb-6">设备管理</h3>
                        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-blue-400 mb-4">获取所有设备</h4>
                                <button @click="getAllDevices"
                                    class="w-full py-2 bg-blue-500/20 text-blue-400 border border-blue-500/50 rounded-lg hover:bg-blue-500/30 transition-all duration-300">
                                    获取设备列表
                                </button>
                            </div>
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-green-400 mb-4">添加设备</h4>
                                <input v-model="newDevice.name" type="text" placeholder="设备名称"
                                    class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-green-500" />
                                <input v-model="newDevice.typeId" type="number" placeholder="设备类型ID"
                                    class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-green-500" />
                                <button @click="addDevice"
                                    class="w-full py-2 bg-green-500/20 text-green-400 border border-green-500/50 rounded-lg hover:bg-green-500/30 transition-all duration-300">
                                    添加设备
                                </button>
                            </div>
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-orange-400 mb-4">获取设备数据</h4>
                                <input v-model="deviceDataId" type="number" placeholder="设备ID"
                                    class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-orange-500" />
                                <button @click="getDeviceData"
                                    class="w-full py-2 bg-orange-500/20 text-orange-400 border border-orange-500/50 rounded-lg hover:bg-orange-500/30 transition-all duration-300">
                                    获取数据
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- IoT数据管理 -->
                    <div v-if="activeTab === 'iotData'" class="space-y-6">
                        <h3 class="text-2xl font-bold text-white mb-6">IoT数据管理</h3>
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-cyan-400 mb-4">上传数据</h4>
                                <input v-model="iotData.temperature" type="number" step="0.1" placeholder="温度"
                                    class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500" />
                                <input v-model="iotData.humidity" type="number" step="0.1" placeholder="湿度"
                                    class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500" />
                                <input v-model="iotData.deviceId" type="number" placeholder="设备ID"
                                    class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-cyan-500" />
                                <button @click="postIotData"
                                    class="w-full py-2 bg-cyan-500/20 text-cyan-400 border border-cyan-500/50 rounded-lg hover:bg-cyan-500/30 transition-all duration-300">
                                    上传数据
                                </button>
                            </div>
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-purple-400 mb-4">获取数据</h4>
                                <button @click="getAllIotData"
                                    class="w-full py-2 mb-3 bg-purple-500/20 text-purple-400 border border-purple-500/50 rounded-lg hover:bg-purple-500/30 transition-all duration-300">
                                    获取所有数据
                                </button>
                                <input v-model="searchDeviceId" type="number" placeholder="搜索设备ID"
                                    class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-500" />
                                <button @click="searchIotData"
                                    class="w-full py-2 bg-purple-500/20 text-purple-400 border border-purple-500/50 rounded-lg hover:bg-purple-500/30 transition-all duration-300">
                                    搜索数据
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- 设备共享管理 -->
                    <div v-if="activeTab === 'userDevice'" class="space-y-6">
                        <h3 class="text-2xl font-bold text-white mb-6">设备共享管理</h3>
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-pink-400 mb-4">共享设备</h4>
                                <input v-model="shareDevice.userId" type="number" placeholder="用户ID"
                                    class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-pink-500" />
                                <input v-model="shareDevice.deviceId" type="number" placeholder="设备ID"
                                    class="w-full px-3 py-2 mb-2 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-pink-500" />
                                <select v-model="shareDevice.permission"
                                    class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white focus:outline-none focus:ring-2 focus:ring-pink-500">
                                    <option value="">选择权限</option>
                                    <option value="read">只读</option>
                                    <option value="write">读写</option>
                                </select>
                                <button @click="shareDeviceToUser"
                                    class="w-full py-2 bg-pink-500/20 text-pink-400 border border-pink-500/50 rounded-lg hover:bg-pink-500/30 transition-all duration-300">
                                    共享设备
                                </button>
                            </div>
                            <div class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                                <h4 class="text-lg font-semibold text-indigo-400 mb-4">查询共享</h4>
                                <input v-model="queryUserId" type="number" placeholder="用户ID"
                                    class="w-full px-3 py-2 mb-3 bg-gray-800/50 border border-gray-600/50 rounded text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                                <button @click="getUserDeviceShares"
                                    class="w-full py-2 bg-indigo-500/20 text-indigo-400 border border-indigo-500/50 rounded-lg hover:bg-indigo-500/30 transition-all duration-300">
                                    查询用户设备
                                </button>
                            </div>
                        </div>
                    </div>

                    <!-- 响应结果显示 -->
                    <div v-if="apiResponse"
                        class="bg-gray-900/50 backdrop-blur-md border border-gray-700/50 rounded-xl p-6">
                        <h4 class="text-lg font-semibold text-green-400 mb-4">API响应结果</h4>
                        <pre
                            class="bg-gray-800/50 p-4 rounded-lg text-green-300 text-sm overflow-auto max-h-96 border border-gray-600/50">{{ JSON.stringify(apiResponse, null, 2) }}</pre>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'App',
    data() {
        return {
            userJWT: '',
            user: {
                username: '',
                password: ''
            },
            activeTab: 'user',
            tabs: [
                { key: 'user', name: '用户管理' },
                { key: 'deviceType', name: '设备类型' },
                { key: 'device', name: '设备管理' },
                { key: 'iotData', name: 'IoT数据' },
                { key: 'userDevice', name: '设备共享' }
            ],
            apiResponse: null,
            newDeviceType: {
                name: ''
            },
            deleteDeviceTypeId: '',
            newDevice: {
                name: '',
                typeId: ''
            },
            deviceDataId: '',
            iotData: {
                temperature: '',
                humidity: '',
                deviceId: ''
            },
            searchDeviceId: '',
            shareDevice: {
                userId: '',
                deviceId: '',
                permission: ''
            },
            queryUserId: ''
        }
    },
    methods: {
        async login() {
            try {
                const formData = new URLSearchParams()
                formData.append('username', this.user.username)
                formData.append('password', this.user.password)

                const result = await axios.post('/user/login', formData, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })

                this.userJWT = result.data.data
                this.apiResponse = result.data
                console.log('登录成功:', result)
            } catch (err) {
                console.error('登录失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async register() {
            try {
                const formData = new URLSearchParams()
                formData.append('username', this.user.username)
                formData.append('password', this.user.password)

                const result = await axios.post('/user/register', formData, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })

                this.apiResponse = result.data
                console.log('注册成功:', result)
                alert('注册成功，请登录')
            } catch (err) {
                console.error('注册失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        logout() {
            this.userJWT = ''
            this.user = { username: '', password: '' }
            this.apiResponse = null
        },

        async getUserDevices() {
            try {
                const result = await axios.get('/user/devices', {
                    headers: {
                        'Authorization': this.userJWT
                    }
                })
                this.apiResponse = result.data
                console.log('用户设备:', result)
            } catch (err) {
                console.error('获取用户设备失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async getUserSharedDevices() {
            try {
                const result = await axios.get('/user/shared-devices', {
                    headers: {
                        'Authorization': this.userJWT
                    }
                })
                this.apiResponse = result.data
                console.log('共享设备:', result)
            } catch (err) {
                console.error('获取共享设备失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async getAllDeviceTypes() {
            try {
                const result = await axios.get('/device-type/')
                this.apiResponse = result.data
                console.log('设备类型:', result)
            } catch (err) {
                console.error('获取设备类型失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async addDeviceType() {
            try {
                const result = await axios.post('/device-type/', this.newDeviceType)
                this.apiResponse = result.data
                console.log('添加设备类型:', this.newDeviceType.name)
                this.newDeviceType.name = ''
                console.log('添加设备类型成功:', result)
            } catch (err) {
                console.error('添加设备类型失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async deleteDeviceType() {
            try {
                const result = await axios.delete(`/device-type/${this.deleteDeviceTypeId}`)
                this.apiResponse = result.data
                this.deleteDeviceTypeId = ''
                console.log('删除设备类型成功:', result)
            } catch (err) {
                console.error('删除设备类型失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async getAllDevices() {
            try {
                const result = await axios.get('/device/')
                this.apiResponse = result.data
                console.log('所有设备:', result)
            } catch (err) {
                console.error('获取设备失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async addDevice() {
            try {
                const result = await axios.post('/device/', this.newDevice)
                this.apiResponse = result.data
                this.newDevice = { name: '', typeId: '' }
                console.log('添加设备成功:', result)
            } catch (err) {
                console.error('添加设备失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async getDeviceData() {
            try {
                const result = await axios.get(`/device/${this.deviceDataId}/data`)
                this.apiResponse = result.data
                console.log('设备数据:', result)
            } catch (err) {
                console.error('获取设备数据失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async postIotData() {
            try {
                const formData = new URLSearchParams()
                formData.append('temperature', this.iotData.temperature)
                formData.append('humidity', this.iotData.humidity)
                formData.append('deviceId', this.iotData.deviceId)

                const result = await axios.post('/iot-data/', formData, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                this.apiResponse = result.data
                this.iotData = { temperature: '', humidity: '', deviceId: '' }
                console.log('上传IoT数据成功:', result)
            } catch (err) {
                console.error('上传IoT数据失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async getAllIotData() {
            try {
                const result = await axios.get('/iot-data/')
                this.apiResponse = result.data
                console.log('所有IoT数据:', result)
            } catch (err) {
                console.error('获取IoT数据失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async searchIotData() {
            try {
                const result = await axios.get(`/iot-data/search?deviceId=${this.searchDeviceId}`)
                this.apiResponse = result.data
                console.log('搜索IoT数据:', result)
            } catch (err) {
                console.error('搜索IoT数据失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async shareDeviceToUser() {
            try {
                const result = await axios.post('/user-device/share', this.shareDevice)
                this.apiResponse = result.data
                this.shareDevice = { userId: '', deviceId: '', permission: '' }
                console.log('共享设备成功:', result)
            } catch (err) {
                console.error('共享设备失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        },

        async getUserDeviceShares() {
            try {
                const result = await axios.get(`/user-device/user/${this.queryUserId}`)
                this.apiResponse = result.data
                console.log('用户设备共享:', result)
            } catch (err) {
                console.error('获取用户设备共享失败:', err)
                this.apiResponse = err.response?.data || { error: err.message }
            }
        }
    }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap');

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Inter', sans-serif;
    background: #000;
    color: #fff;
}

#app {
    min-height: 100vh;
}

.animation-delay-2000 {
    animation-delay: 2s;
}

.animation-delay-4000 {
    animation-delay: 4s;
}

/* 自定义滚动条 */
::-webkit-scrollbar {
    width: 8px;
}

::-webkit-scrollbar-track {
    background: rgba(31, 41, 55, 0.5);
    border-radius: 4px;
}

::-webkit-scrollbar-thumb {
    background: rgba(59, 130, 246, 0.5);
    border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
    background: rgba(59, 130, 246, 0.7);
}

/* 输入框聚焦效果 */
input:focus,
select:focus {
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

/* 按钮悬停效果 */
button:hover {
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
}

/* 卡片悬停效果 */
.bg-gray-900\/50:hover {
    background-color: rgba(17, 24, 39, 0.7);
    transform: translateY(-2px);
    transition: all 0.3s ease;
}
</style>
