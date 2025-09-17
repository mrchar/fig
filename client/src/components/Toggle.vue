<script setup lang="ts">
const model = defineModel<boolean>()

function onChange(e: Event) {
  model.value = (e.target as HTMLInputElement).checked
}
</script>
<template>
  <input type="checkbox" :checked="model" class="toggle" @change="onChange" />
</template>
