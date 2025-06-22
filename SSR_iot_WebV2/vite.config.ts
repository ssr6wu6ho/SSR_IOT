import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  server: {
    proxy: {
      "/user": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      // 设备类型接口代理
      "/device-type": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
      // 设备共享接口代理
      "/user-device": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
      // 设备管理接口代理
      "/device": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
      // 物联网数据接口代理
      "/iot-data": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace(/^\/iot-data/, "/iot-data"),
      },
      // 旧版物联网数据接口兼容
      "/iotData": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace(/^\/iotData/, "/iotData"),
      },
    },
  },
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
