<script setup lang="ts">
const model = defineModel<boolean>()

const emit = defineEmits(["change"])

function onChange(event: Event) {
  emit("change", (event.target as HTMLInputElement).checked)
  model.value = (event.target as HTMLInputElement).checked
}

</script>

<template>
  <input type="checkbox" :checked="model" class="checkbox" @change="onChange" />
</template>
