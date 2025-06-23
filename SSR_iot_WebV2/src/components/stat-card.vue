<!-- stat-card.vue -->
<template>
  <div class="glass-effect rounded-xl p-6 hover:scale-105 transition-all duration-300">
    <div class="flex items-center justify-between">
      <div>
        <p class="text-gray-400 text-sm font-medium">{{ title }}</p>
        <p class="text-3xl font-bold text-white mt-2">{{ value }}</p>
        <div v-if="trend !== null && trend !== undefined" class="flex items-center mt-2">
          <span :class="[
            'text-sm font-medium',
            formattedTrend.startsWith('+') ? 'text-green-400' : 'text-red-400'
          ]">
            {{ formattedTrend }}
          </span>
          <span class="text-gray-400 text-sm ml-2">较上期</span>
        </div>
      </div>
      <div :class="[
        'w-16 h-16 rounded-full flex items-center justify-center text-2xl',
        `bg-gradient-to-r ${color}`
      ]">
        {{ icon }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  title: String,
  value: [String, Number],
  icon: String,
  color: String,
  trend: [String, Number]  // 允许字符串或数字类型
})

// 格式化趋势值，确保为字符串并添加百分比符号
const formattedTrend = computed(() => {
  if (props.trend === null || props.trend === undefined) return ''

  const value = String(props.trend)
  return value.startsWith('+') || value.startsWith('-')
    ? `${value}%`
    : `${value}%`
})
</script>