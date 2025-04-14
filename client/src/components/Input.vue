<script setup lang="ts">
export type Props = {
  prefix?: string
  suffix?: string
  placeholder?: string
}

const model = defineModel()

const props = defineProps<Props>()
</script>
<template>
  <label class="input">
    <span v-if="props.prefix" class="label">
      {{ props.prefix }}
    </span>
    <input v-model="model" type="text" :placeholder="props.placeholder" />
    <span v-if="props.suffix" class="label">
      {{ props.suffix }}
    </span>
  </label>
</template>
