<script setup lang="ts">
const model = defineModel<boolean>()

function onChange(e: Event) {
  console.log("Changed", e)
  model.value = (e.target as HTMLInputElement).checked
}
</script>

<template>
  <input type="radio" :checked="model" class="radio" @change="onChange" />
</template>
