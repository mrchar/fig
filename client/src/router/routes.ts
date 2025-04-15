import type { RouteRecordRaw } from "vue-router"
import LandingPage from "@/views/LandingPage.vue"
import Framework from "@/layouts/Framework.vue"

const routes: RouteRecordRaw[] = [
  {
    name:"LandingPage",
    path:"/",
    meta:{title:"首页"},
    component: LandingPage
  },
  {
    name:"Framework",
    path:"/",
    component: Framework,
    children: [
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
        meta: { title: "编辑数据格式", isMenu: false, parent: "StructList" },
        component: () => import("@/views/StructEditor.vue")
      },
      {
        name: "FormList",
        path: "/form/list",
        meta: { title: "表单设计", isMenu: true },
        component: () => import("@/views/FormList.vue")
      },
      {
        name: "FormEditor",
        path: "/form/detail/:id",
        meta: { title: "编辑表单", isMenu: false, parent: "FormList" },
        component: () => import("@/views/FormEditor.vue")
      },
      {
        name: "Flow",
        path: "/flow/list",
        meta: { title: "流程设计", isMenu: true },
        component: () => import("@/views/FlowList.vue")
      },
      {
        name: "RecordList",
        path: "/record/list",
        meta: { title: "数据分析", isMenu: true },
        component: () => import("@/views/RecordList.vue")
      },
      {
        name: "RecordEditor",
        path: "/record/detail/:id",
        meta: { title: "编辑数据", isMenu: false, parent: "RecordList" },
        component: () => import("@/views/RecordEditor.vue")
      }]
  },
]

export default routes
