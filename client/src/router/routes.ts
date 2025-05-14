import type { RouteRecordRaw } from "vue-router"
import LandingPage from "@/views/LandingPage/LandingPage.vue"
import Login from "@/views/Login.vue"
import Framework from "@/layouts/Framework.vue"

const routes: RouteRecordRaw[] = [
  {
    name: "LandingPage",
    path: "/",
    meta: { title: "首页" },
    component: LandingPage
  },
  {
    name: "Login",
    path: "/login",
    meta: { title: "登陆" },
    component: Login
  },
  {
    name: "Framework",
    path: "/",
    component: Framework,
    children: [
      {
        name: "VocabularyList",
        path: "/vocabulary/list",
        meta: {
          title: "词汇表",
          isMenu: true,
          help: "这里的词汇指的是业务中专用的词汇，通过建设词汇表，对业务中的概念进行抽象，并帮助构建业务数据模型；同时也可以将词汇表提供给AI以提供更多的帮助。"
        },
        component: () => import("@/views/VocabularyList.vue")
      },
      {
        name: "VocabularyEditor",
        path: "/vocabulary/detail/:id",
        meta: {
          title: "编辑词汇",
          isMenu: false,
          parent: "VocabularyList",
          help: "使用JsonSchema对业务中的专用词汇进行抽象，以便在后面的业务场景中将对应的概念数字化。即使你不懂得任何建模语言也不用担心，点击星号使用AI创建对业务专用词进行定义。"
        },
        component: () => import("@/views/VocabularyEditor.vue")
      },
      {
        name: "StructList",
        path: "/struct/list",
        meta: {
          title: "数据模型",
          isMenu: true,
          help: "使用数据模型对业务中的实体进行定义，通常一个实体需要使用多个属性定义，比如：学生会使用姓名、性别和年龄来描述。"
        },
        component: () => import("@/views/StructList.vue")
      },
      {
        name: "StructEditor",
        path: "/struct/detail/:id",
        meta: {
          title: "编辑数据模型",
          isMenu: false,
          parent: "StructList",
          help: "使用JsonSchema对业务中的实体进行定义，你可以使用多个属性进行描述。即使你不懂得任何建模语言也不用担心，点击星号使用AI创建数据模型。"
        },
        component: () => import("@/views/StructEditor.vue")
      },
      {
        name: "FormList",
        path: "/form/list",
        meta: {
          title: "表单设计",
          isMenu: true,
          help: "表单是用来收集数据的入口，表单和数据模型是绑定的，用户通过表单录入信息，并记录在对应数据模型的条目中。"
        },
        component: () => import("@/views/FormList.vue")
      },
      {
        name: "FormEditor",
        path: "/form/detail/:id",
        meta: {
          title: "编辑表单",
          isMenu: false,
          parent: "FormList",
          help: "使用JsonForms的UISchema格式定义表单的展现形式。即使你不会使用建模语言也不用担心，点击星号使用AI帮助设计，你只需要告诉它你希望怎么排版表单就可以了。"
        },
        component: () => import("@/views/FormEditor.vue")
      },
      {
        name: "FunctionList",
        path: "/function/list",
        meta: {
          title: "函数开发",
          isMenu: true,
          help: "函数是用来对数据进行处理的过程，提前定义好的函数可以在表单或者工作流中用到。向函数传入一个输入值，函数执行完成后会返回一个输出值。"
        },
        component: () => import("@/views/FunctionList.vue")
      },
      {
        name: "FunctionEditor",
        path: "/function/detail/:id",
        meta: {
          title: "编辑函数",
          isMenu: false,
          help: "函数使用javascript开发，如果你不了解如何编写javascript，可以使用AI帮助实现功能，在寻求帮助之前，请先确定函数的参数类型。"
        },
        component: () => import("@/views/FunctionEditor.vue")
      },
      {
        name: "ProcessList",
        path: "/process/list",
        meta: { title: "流程设计", isMenu: true },
        component: () => import("@/views/ProcessList.vue")
      },
      {
        name: "ProcessEditor",
        path: "/process/detail/:id",
        meta: { title: "编辑流程", isMenu: false },
        component: () => import("@/views/ProcessEditor.vue")
      },
      {
        name: "RecordList",
        path: "/record/list",
        meta: {
          title: "数据分析",
          isMenu: true,
          help: "这里是你使用表单收集的所有数据模型的实例，点击新增创建新的记录。"
        },
        component: () => import("@/views/RecordList.vue")
      },
      {
        name: "RecordEditor",
        path: "/record/detail/:id",
        meta: {
          title: "编辑数据",
          isMenu: false,
          parent: "RecordList",
          help: "在表单中录入要收集的信息，点击保存，就可以将数据提交的系统中了。"
        },
        component: () => import("@/views/RecordEditor.vue")
      },
      {
        name: "SpaceList",
        path: "/space/list",
        meta: {
          title: "空间管理",
          isMenu: true,
          help: "使用空间对功能进行隔离，每个空间承担独立的功能，可以是一家公司，或者一项专门的业务"
        },
        component: () => import("@/views/SpaceList.vue")
      },
      {
        name: "SpaceEditor",
        path: "/space/detail/:code",
        meta: {
          title: "编辑空间",
          isMenu: false,
          help: "你可以修改空间的名称"
        },
        component: () => import("@/views/SpaceDetail.vue")
      }
    ]
  }
]

export default routes
