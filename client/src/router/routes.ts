import type { RouteRecordRaw } from "vue-router"

const routes: RouteRecordRaw[] = [
  {
    name: "VocabularyList",
    path: "/vocabulary/list",
    meta: { title: "词汇表", isMenu: true },
    component: () => import("@/views/VocabularyList.vue")
  },
  {
    name: "VocabularyEditor",
    path: "/vocabulary/detail/:id",
    meta: { title: "编辑词汇", isMenu: false, parent: "VocabularyList" },
    component: () => import("@/views/VocabularyEditor.vue")
  },
  {
    name: "StructList",
    path: "/struct/list",
    meta: { title: "数据格式", isMenu: true },
    component: () => import("@/views/StructList.vue")
  },
  {
    name: "StructEditor",
    path: "/struct/detail/:id",
    meta: { title: "数据格式", isMenu: false, parent: "StructList" },
    component: () => import("@/views/StructEditor.vue")
  },
  {
    name: "Form",
    path: "/form/list",
    meta: { title: "表单设计", isMenu: true },
    component: () => import("@/views/FormList.vue")
  },
  {
    name: "Flow",
    path: "/flow/list",
    meta: { title: "流程设计", isMenu: true },
    component: () => import("@/views/FlowList.vue")
  },
  {
    name: "record",
    path: "/record/list",
    meta: { title: "数据分析", isMenu: true },
    component: () => import("@/views/RecordList.vue")
  }
]

export default routes
