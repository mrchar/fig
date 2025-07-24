<script setup lang="ts">
const model = defineModel()

const emit = defineEmits(["change"])

function onChange(event) {
  emit("change", event.target.checked)
  model.value = event.target.checked
}

</script>

<template>
  <input type="checkbox" :checked="model" class="checkbox" @change="onChange" />
</template>
