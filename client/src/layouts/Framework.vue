<script setup lang="ts">
import routes from "../router/routes.ts"

const menus = computed(() => {
  return routes.filter(item => item?.meta && item.meta?.isMenu)
})

const route = useRoute()
</script>

<template>
  <div class="w-screen h-screen flex flex-col">
    <div class="navbar bg-base-100 shadow-sm">
      <a class="btn btn-ghost text-xl">Fig</a>
    </div>
    <div class="h-full flex">
      <ul class="menu bg-base-200 rounded-box w-56 h-full">
        <li v-for="menu in menus">
          <RouterLink :to="menu.path" :class="{'menu-active':menu.path===route.path}">
            {{ menu?.meta?.title || menu?.name || menu.path }}
          </RouterLink>
        </li>
      </ul>
      <div class="w-full h-full p-4">
        <Breadcrumbs/>
        <slot></slot>
      </div>
    </div>
  </div>
</template>

