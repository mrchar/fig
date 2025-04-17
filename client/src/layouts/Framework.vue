<script setup lang="ts">
import routes from "../router/routes.ts"
import api from "@/api"
import { useSpaceStore } from "@/store/space.ts"

const menus = computed(() => {
  return routes
      ?.find(item => item.name === "Framework")?.children
      ?.filter(item => item?.meta && item.meta?.isMenu)
    || []
})

const route = useRoute()

const { space, setSpace } = useSpaceStore()
</script>

<template>
  <div class="w-screen h-screen flex flex-col">
    <nav
      class="navbar bg-gradient-to-r from-slate-800/30 via-gray-800/30 to-zinc-800/30 backdrop-blur-md backdrop-saturate-75 py-4 px-8 border-b border-gray-700 flex justify-between">
      <RouterLink class="btn btn-ghost text-xl" to="/">Fig</RouterLink>
      <Select prefix="空间"
              class="w-56"
              :model-value="space"
              :datasource="api.space.useListSpaces"
              :formatter="item=>item.name"
              :select-first="true"
              placeholder="请选择空间"
              @change="setSpace"
      />
    </nav>
    <div class="h-full flex">
      <ul
        class="menu bg-gradient-to-r from-slate-800/30 via-gray-800/30 to-zinc-800/30 backdrop-blur-md backdrop-saturate-75 w-56 h-full border-gray-700">
        <li v-for="menu in menus">
          <RouterLink :to="menu.path" :class="{'menu-active':menu.path===route.path}">
            {{ menu?.meta?.title || menu?.name || menu.path }}
          </RouterLink>
        </li>
      </ul>
      <div class="w-full h-full p-4">
        <div class="flex justify-between">
          <Breadcrumbs />
          <Help></Help>
        </div>
        <RouterView />
      </div>
    </div>
  </div>
</template>

