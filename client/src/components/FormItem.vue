<script setup lang="ts">
export type Props = {
  label?: string
  description?: string
}

const props = defineProps<Props>()
</script>

<template>
  <fieldset class="fieldset">
    <legend v-if="props.label" class="fieldset-legend">
      {{ props.label }}
    </legend>
    <slot></slot>
    <p v-if="props.description" class="fieldset-label">
      {{ props.description }}
    </p>
  </fieldset>
</template>
