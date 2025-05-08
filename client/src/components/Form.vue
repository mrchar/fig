<script setup lang="ts">
const emit = defineEmits(["submit"])

function onSubmit() {
  emit("submit")
}
</script>

<template>
  <form class="p-2" @submit.prevent="onSubmit">
    <slot></slot>
  </form>
</template>
