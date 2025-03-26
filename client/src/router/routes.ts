import type { RouteRecordRaw } from "vue-router"

const routes: RouteRecordRaw[] = [
  {
    name: "VocabularyList",
    path: "/vocabulary/list",
    meta: { title: "词汇表", isMenu: true },
    component: () => import("@/views/VocabularyView.vue")
  },
  {
    name: "VocabularyEditor",
    path: "/vocabulary/detail/:id",
    meta: { title: "编辑词汇", isMenu: false },
    component: () => import("@/views/VocabularyEditor.vue")
  },
  {
    name: "Schema",
    path: "/schema/list",
    meta: { title: "数据格式", isMenu: true },
    component: () => import("@/views/SchemaView.vue")
  },
  {
    name: "SchemaEditor",
    path: "/schema/detail/:id",
    meta: { title: "数据格式", isMenu: false },
    component: () => import("@/views/SchemaEditor.vue")
  },
  {
    name: "Form",
    path: "/form/list",
    meta: { title: "表单设计", isMenu: true },
    component: () => import("@/views/FormView.vue")
  },
  {
    name: "Flow",
    path: "/flow/list",
    meta: { title: "流程设计", isMenu: true },
    component: () => import("@/views/FlowView.vue")
  },
  {
    name: "record",
    path: "/record/list",
    meta: { title: "数据分析", isMenu: true },
    component: () => import("@/views/RecordView.vue")
  }
]

export default routes
