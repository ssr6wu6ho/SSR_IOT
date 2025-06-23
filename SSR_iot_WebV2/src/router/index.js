import { createRouter, createWebHistory } from "vue-router"
import Login from "../views/login.vue"
import Dashboard from "../views/dashboard.vue"
import Devices from "../views/devices.vue"
import DataMonitor from "../views/data-monitor.vue"
import DeviceSharing from "../views/device-sharing.vue"
import ApiTester from "../views/api-tester.vue"

const routes = [
  {
    path: "/",
    redirect: "/login",
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/dashboard",
    name: "Dashboard",
    component: Dashboard,
    meta: { requiresAuth: true },
  },
  {
    path: "/devices",
    name: "Devices",
    component: Devices,
    meta: { requiresAuth: true },
  },
  {
    path: "/data-monitor",
    name: "DataMonitor",
    component: DataMonitor,
    meta: { requires: true },
  },
  {
    path: "/device-sharing",
    name: "DeviceSharing",
    component: DeviceSharing,
    meta: { requiresAuth: true },
  },
  {
    path: "/api-tester",
    name: "ApiTester",
    component: ApiTester,
    meta: { requiresAuth: true },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token")

  if (to.meta.requiresAuth && !token) {
    next("/login")
  } else if (to.path === "/login" && token) {
    next("/dashboard")
  } else {
    next()
  }
})

export default router
