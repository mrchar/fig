<script setup lang="ts">
export type Props = {
  label?: string
  suffix?: string
  placeholder?: string
}

const model = defineModel()

const props = defineProps<Props>()
</script>
<template>
  <label class="input">
    <span v-if="props.label" class="label">
      {{ props.label }}
    </span>
    <input v-model="model" type="text" :placeholder="props.placeholder" />
    <span v-if="props.suffix" class="label">
      {{ props.suffix }}
    </span>
  </label>
</template>
