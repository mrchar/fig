<script setup lang="ts">
const types = [
  { label: "文本", value: "string" },
  { label: "数字", value: "number" },
  { label: "整数", value: "integer" },
  { label: "逻辑", value: "boolean" },
  { label: "[空]", value: "null" },
  { label: "列表", value: "array" },
  { label: "对象", value: "object" }
]

const model = defineModel()
</script>

<template>
  <Select v-model="model" :options="types"></Select>
</template>
