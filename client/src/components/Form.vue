<script setup lang="ts">
const emit = defineEmits(["submit"])

function onSubmit() {
  emit("submit")
}
</script>
<template>
  <form class="max-w-lg px-2 py-4 flex flex-col gap-2" @submit.prevent="onSubmit">
    <slot></slot>
  </form>
</template>
