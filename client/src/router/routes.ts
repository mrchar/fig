import type { RouteRecordRaw } from "vue-router"

const routes: RouteRecordRaw[] = [
  {
    name: "Vocabulary",
    path: "/vocabulary/list",
    meta: { title: "词汇表" },
    component: () => import("@/views/VocabularyView.vue")
  },
  {
    name: "Schema",
    path: "/schema/list",
    meta: { title: "数据格式" },
    component: () => import("@/views/SchemaView.vue")
  },
  {
    name: "Form",
    path: "/form/list",
    meta: { title: "表单设计" },
    component: () => import("@/views/FormView.vue")
  },
  {
    name: "Flow",
    path: "/flow/list",
    meta: { title: "流程设计" },
    component: () => import("@/views/FlowView.vue")
  },
  {
    name: "record",
    path: "/record/list",
    meta: { title: "数据分析" },
    component: () => import("@/views/RecordView.vue")
  }
]

export default routes
