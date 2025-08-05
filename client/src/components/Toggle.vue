<script setup lang="ts">
const model = defineModel()

function onChange(e) {
  model.value = e.target.checked
}
</script>
<template>
  <input type="checkbox" :checked="model" class="toggle" @change="onChange" />
</template>
