<template>
    <div
        class="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 flex items-center justify-center p-4">
        <!-- 登录表单容器 -->
        <div class="flex w-full max-w-6xl items-center justify-between gap-8">
            <!-- 左侧登录框 -->
            <div class="flex-1 max-w-md">
                <div class="bg-white/10 backdrop-blur-lg rounded-2xl p-8 shadow-2xl border border-white/20">
                    <!-- Logo区域 -->
                    <div class="text-center mb-8">
                        <div
                            class="w-16 h-16 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-xl mx-auto mb-4 flex items-center justify-center">
                            <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M13 10V3L4 14h7v7l9-11h-7z" />
                            </svg>
                        </div>
                        <h1 class="text-2xl font-bold text-white mb-2">IoT Platform</h1>
                        <p class="text-gray-300">Welcome back to your smart world</p>
                    </div>

                    <!-- 登录表单 -->
                    <form @submit.prevent="handleLogin" class="space-y-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-300 mb-2">Email</label>
                            <input v-model="loginForm.email" type="email" required
                                class="w-full px-4 py-3 bg-white/10 border border-white/20 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                                placeholder="Enter your email" />
                        </div>

                        <div>
                            <label class="block text-sm font-medium text-gray-300 mb-2">Password</label>
                            <input v-model="loginForm.password" type="password" required
                                class="w-full px-4 py-3 bg-white/10 border border-white/20 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                                placeholder="Enter your password" />
                        </div>

                        <div class="flex items-center justify-between">
                            <label class="flex items-center">
                                <input type="checkbox"
                                    class="w-4 h-4 text-blue-500 bg-transparent border-gray-300 rounded focus:ring-blue-500">
                                <span class="ml-2 text-sm text-gray-300">Remember me</span>
                            </label>
                            <a href="#" class="text-sm text-blue-400 hover:text-blue-300 transition-colors">Forgot
                                password?</a>
                        </div>

                        <button type="submit"
                            class="w-full bg-gradient-to-r from-blue-500 to-cyan-500 text-white py-3 px-4 rounded-lg font-medium hover:from-blue-600 hover:to-cyan-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 focus:ring-offset-gray-800 transition-all transform hover:scale-[1.02]">
                            Sign In
                        </button>
                    </form>

                    <div class="mt-6 text-center">
                        <p class="text-gray-400 text-sm">
                            Don't have an account?
                            <a href="#" class="text-blue-400 hover:text-blue-300 transition-colors">Sign up</a>
                        </p>
                    </div>
                </div>
            </div>

            <!-- 右侧磁力小球效果 -->
            <div class="flex-1 max-w-2xl hidden lg:block">
                <div class="relative">
                    <svg ref="magneticSvg"
                        class="w-full h-[600px] rounded-2xl bg-gradient-to-br from-slate-800/50 to-slate-900/50 backdrop-blur-sm border border-white/10"
                        @mousemove="handleMouseMove" @mouseleave="handleMouseLeave">
                        <defs>
                            <radialGradient id="ballGradient" cx="30%" cy="30%">
                                <stop offset="0%" style="stop-color:#60a5fa;stop-opacity:1" />
                                <stop offset="100%" style="stop-color:#3b82f6;stop-opacity:1" />
                            </radialGradient>
                            <filter id="glow">
                                <feGaussianBlur stdDeviation="3" result="coloredBlur" />
                                <feMerge>
                                    <feMergeNode in="coloredBlur" />
                                    <feMergeNode in="SourceGraphic" />
                                </feMerge>
                            </filter>
                        </defs>

                        <g v-for="(ball, index) in magneticBalls" :key="index">
                            <!-- 连接线 -->
                            <line :x1="ball.originalX" :y1="ball.originalY" :x2="ball.x" :y2="ball.y"
                                stroke="rgba(96, 165, 250, 0.3)" stroke-width="1"
                                class="transition-all duration-300 ease-out" />

                            <!-- 小球 -->
                            <circle :cx="ball.x" :cy="ball.y" :r="ball.radius" fill="url(#ballGradient)"
                                filter="url(#glow)" class="transition-all duration-300 ease-out"
                                :style="{ opacity: ball.opacity }" />

                            <!-- 中心点 -->
                            <circle :cx="ball.x" :cy="ball.y" :r="2" fill="rgba(255, 255, 255, 0.8)"
                                class="transition-all duration-300 ease-out" />
                        </g>
                    </svg>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, reactive } from 'vue'

// 登录表单数据
const loginForm = reactive({
    email: '',
    password: ''
})

// 磁力小球相关
interface MagneticBall {
    x: number
    y: number
    originalX: number
    originalY: number
    radius: number
    opacity: number
}

const magneticSvg = ref<SVGSVGElement | null>(null)
const magneticBalls = ref<MagneticBall[]>([])
const mousePosition = ref({ x: 0, y: 0 })
const isMouseInside = ref(false)

// 配置参数
const config = {
    ballRadius: 8,
    spacing: 60,
    repelDistance: 120,
    repelStrength: 0.8
}

// 初始化磁力小球网格
const initMagneticGrid = () => {
    if (!magneticSvg.value) return

    const rect = magneticSvg.value.getBoundingClientRect()
    const width = rect.width
    const height = rect.height

    magneticBalls.value = []

    const cols = Math.floor(width / config.spacing)
    const rows = Math.floor(height / config.spacing)

    for (let row = 0; row < rows; row++) {
        for (let col = 0; col < cols; col++) {
            const x = col * config.spacing + config.spacing / 2
            const y = row * config.spacing + config.spacing / 2

            magneticBalls.value.push({
                x,
                y,
                originalX: x,
                originalY: y,
                radius: config.ballRadius,
                opacity: 0.6 + Math.random() * 0.4
            })
        }
    }
}

// 处理鼠标移动
const handleMouseMove = (event: MouseEvent) => {
    if (!magneticSvg.value) return

    const rect = magneticSvg.value.getBoundingClientRect()
    mousePosition.value = {
        x: event.clientX - rect.left,
        y: event.clientY - rect.top
    }
    isMouseInside.value = true

    updateBallPositions()
}

// 处理鼠标离开
const handleMouseLeave = () => {
    isMouseInside.value = false
    resetBallPositions()
}

// 更新小球位置
const updateBallPositions = () => {
    magneticBalls.value.forEach(ball => {
        const dx = ball.originalX - mousePosition.value.x
        const dy = ball.originalY - mousePosition.value.y
        const distance = Math.sqrt(dx * dx + dy * dy)

        if (distance < config.repelDistance) {
            // 计算排斥力
            const force = (config.repelDistance - distance) / config.repelDistance
            const repelX = (dx / distance) * force * config.repelStrength * 50
            const repelY = (dy / distance) * force * config.repelStrength * 50

            ball.x = ball.originalX + repelX
            ball.y = ball.originalY + repelY
            ball.opacity = 0.8 + force * 0.2
        }
    })
}

// 重置小球位置
const resetBallPositions = () => {
    magneticBalls.value.forEach(ball => {
        ball.x = ball.originalX
        ball.y = ball.originalY
        ball.opacity = 0.6 + Math.random() * 0.4
    })
}

// 处理登录
const handleLogin = () => {
    console.log('Login attempt:', loginForm)
    // 这里添加登录逻辑
}

// 窗口大小改变时重新初始化
const handleResize = () => {
    setTimeout(initMagneticGrid, 100)
}

onMounted(() => {
    initMagneticGrid()
    window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
    window.removeEventListener('resize', handleResize)
})
</script>

<style scoped>
/* 自定义滚动条 */
::-webkit-scrollbar {
    width: 6px;
}

::-webkit-scrollbar-track {
    background: rgba(255, 255, 255, 0.1);
    border-radius: 3px;
}

::-webkit-scrollbar-thumb {
    background: rgba(96, 165, 250, 0.5);
    border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
    background: rgba(96, 165, 250, 0.7);
}

/* 输入框聚焦动画 */
input:focus {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

/* 按钮悬停效果 */
button:hover {
    box-shadow: 0 8px 25px rgba(59, 130, 246, 0.4);
}
</style>