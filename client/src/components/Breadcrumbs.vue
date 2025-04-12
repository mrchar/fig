<script setup lang="ts">
const route = useRoute()
const pieces = computed(() => {
  const ancestors = findAncestors(route)
  if (ancestors) {
    return [...ancestors, route]
  }

  return [route]
})

function findAncestors(route: RouteLocationNormalizedLoadedGeneric) {
  if (!route.meta?.parent) {
    return null
  }

  const parent = findRouteByName(route.meta.parent)
  if (!parent) {
    return null
  }

  const ancestors = findAncestors(parent)
  if (ancestors) {
    return [...ancestors, parent]
  }

  return [parent]
}

const router = useRouter()

function findRouteByName(name: string) {
  const routes = router.getRoutes()
  return walkThroughRoutes(routes, name)
}

function walkThroughRoutes(routes: RouteRecordRaw[], name: string) {
  for (let route of routes) {
    if (route.name === name) {
      return route
    }

    if (route.children && route.children.length > 0) {
      return walkThroughRoutes(route.children, name)
    }
  }

  return null
}


</script>

<template>
  <div class="breadcrumbs text-sm p-2">
    <ul>
      <li v-for="item in pieces" :key="item.path">
        <RouterLink :to="item.path">
          {{ item.meta?.title || item.name || item.path }}
        </RouterLink>
      </li>
    </ul>
  </div>
</template>
