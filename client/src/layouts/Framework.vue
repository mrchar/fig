<script setup lang="ts">
import routes from "@/router/routes.ts"
import ThemeToggle from "./components/ThemeToggle.vue"
import SpaceSelect from "./components/SpaceSelect.vue"

const menus = computed(() => {
  return routes
      ?.find(item => item.name === "Framework")?.children
      ?.filter(item => item?.meta && item.meta?.isMenu)
    || []
})

const route = useRoute()

</script>

<template>
  <div class="w-screen h-screen flex flex-col">
    <Navbar>
      <div class="flex gap-2">
        <ThemeToggle />
        <SpaceSelect />
      </div>
    </Navbar>
    <div class="h-full flex">
      <aside>
        <Menu :menus="menus" :active="route" />
      </aside>
      <main class="w-full h-full p-4">
        <div class="flex justify-between">
          <Breadcrumbs />
          <Help></Help>
        </div>
        <RouterView />
      </main>
    </div>
    <footer class="fixed bottom-0 text-right w-full p-4 text-gray-400 pointer-events-none">
      {{ space?.code }}
    </footer>
  </div>
</template>

