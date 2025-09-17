import { createRouter, createWebHistory } from "vue-router"
import routes from "./routes.ts"

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

router.beforeEach((_, __, next) => {
  // TODO: 检查登录状态
  next()
})

export default router
