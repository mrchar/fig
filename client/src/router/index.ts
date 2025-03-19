import { createRouter, createWebHistory } from "vue-router"
import routes from "./routes.ts"

const index = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default index
