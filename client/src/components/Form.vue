<script setup lang="ts">
const emit = defineEmits(["submit"])

function onSubmit() {
  emit("submit")
}
</script>
<template>
  <form class="px-2 py-4 flex flex-col gap-2" @submit.prevent="onSubmit">
    <slot></slot>
  </form>
</template>
