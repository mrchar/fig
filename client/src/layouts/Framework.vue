<script setup lang="ts">
import routes from "../router/routes.ts"

const route = useRoute()
</script>

<template>
  <div class="w-screen h-screen flex flex-col">
    <div class="navbar bg-base-100 shadow-sm">
      <a class="btn btn-ghost text-xl">Fig</a>
    </div>
    <div class="h-full flex">
      <ul class="menu bg-base-200 rounded-box w-56 h-full">
        <li v-for="item in routes">
          <RouterLink :to="item.path" :class="{'menu-active':item.path===route.path}">
            {{ item?.meta?.title || item?.name || item.path }}
          </RouterLink>
        </li>
      </ul>
      <div class="w-full h-full p-4">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

