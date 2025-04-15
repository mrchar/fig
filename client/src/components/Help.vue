<script setup lang="ts">
import { Icon } from "@iconify/vue"

const route = useRoute()
</script>
<template>
  <div v-if="route?.meta?.help" class="tooltip tooltip-left" :data-tip="route?.meta?.help">
    <Icon icon="material-symbols:help" class="w-5 h-5"/>
  </div>
</template>
