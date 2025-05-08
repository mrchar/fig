<script setup lang="ts">
export type Props = {
  brand?: string
  path?: string
}

const props = defineProps<Props>()
</script>

<template>
  <div class="navbar">
    <div class="flex-1">
      <slot v-if="$slots.brand" name="brand" />
      <RouterLink v-else class="btn btn-ghost text-xl" :to="props.path || '/'">
        {{ props.brand || "Fig" }}
      </RouterLink>
    </div>
    <div class="flex-none">
      <slot name="default" />
    </div>
  </div>
</template>

<style scoped>
@reference "../style.css";

.navbar {
  @apply py-4 px-8;
  @apply dark:bg-gradient-to-r dark:from-slate-800/30 dark:via-gray-800/30 dark:to-zinc-800/30;
  @apply border-b border-gray-200 dark:border-gray-700;
  @apply backdrop-blur-md backdrop-saturate-75;
}
</style>
