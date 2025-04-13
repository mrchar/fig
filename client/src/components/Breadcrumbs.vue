<script setup lang="ts">
import type { RouteMeta } from "vue-router"

type Route = {
  name: string
  path: string
  meta: RouteMeta,
  children: Route[]
}

const route = useRoute()
const pieces = computed((): Route[] => {
  const ancestors = findAncestors(route as unknown as Route)
  if (ancestors) {
    return [...ancestors, route as unknown as Route]
  }

  return [route as unknown as Route]
})

function findAncestors(route: Route): Route[] | null {
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

function findRouteByName(name: string): Route | null {
  const routes = router.getRoutes()
  return walkThroughRoutes(routes as Route[], name)
}

function walkThroughRoutes(routes: Route[], name: string): Route | null {
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
