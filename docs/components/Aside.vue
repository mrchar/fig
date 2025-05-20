<script setup>
const props = defineProps(["menus"])
</script>
<template>
  <div class="border rounded p-1 flex-none">
    <div v-for="item in props.menus">
      {{item}}
    </div>
  </div>
</template>