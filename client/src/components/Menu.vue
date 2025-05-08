<script setup lang="ts">
import type { RouteRecordRaw } from "vue-router"

export type Props = {
  menus: RouteRecordRaw[]
  active?: { path: string }
}

const props = withDefaults(defineProps<Props>(), { active: () => ({ path: "" }) })


</script>
<template>
  <ul
    class="menu bg-gradient-to-r from-slate-800/30 via-gray-800/30 to-zinc-800/30 backdrop-blur-md backdrop-saturate-75 w-28 h-full border-gray-700">
    <li v-for="menu in props.menus">
      <RouterLink :to="menu.path" :class="{'menu-active':menu.path===props.active.path}">
        {{ menu?.meta?.title || menu?.name || menu.path }}
      </RouterLink>
    </li>
  </ul>
</template>
