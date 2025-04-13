<script setup lang="ts">
export type Props = {
  label?: string
  prefix?: string
  suffix?: string
  placeholder?: string
}

const model = defineModel()

const props = defineProps<Props>()
</script>
<template>
  <fieldset class="fieldset">
    <legend v-if="props.label" class="fieldset-legend">
      {{ props.label }}
    </legend>
    <label class="input w-full">
    <span v-if="props.prefix" class="label">
      {{ props.prefix }}
    </span>
      <input v-model="model" type="text" :placeholder="props.placeholder" />
      <span v-if="props.suffix" class="label">
      {{ props.suffix }}
    </span>
    </label>
  </fieldset>
</template>
