<script setup lang="ts">
import { Icon } from "@iconify/vue"

const isDark = useDark({
  attribute: "data-theme",
  valueLight: "light",
  valueDark: "dark"
})
const toggleDark = useToggle(isDark)
</script>

<template>
  <Swap :model-value="!isDark" @update:model-value="toggleDark()">
    <template #on>
      <Icon icon="cuida:sun-outline" width="24" height="24" />
    </template>
    <template #off>
      <Icon icon="material-symbols-light:dark-mode-rounded"
            width="24" height="24"
      />
    </template>
  </Swap>
</template>
