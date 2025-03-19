<script setup lang="ts">
import routes from "../router/routes.ts"
</script>

<template>
  <div class="w-screen h-screen flex flex-col">
    <div class="navbar bg-base-100 shadow-sm">
      <a class="btn btn-ghost text-xl">Fig</a>
    </div>
    <div class="h-full flex">
      <ul class="menu bg-base-200 rounded-box w-56 h-full">
        <li v-for="route in routes">
          <RouterLink :to="route.path">
            {{ route?.meta.title || route?.name || route.path }}
          </RouterLink>
        </li>
      </ul>
      <div class="w-full h-full">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

