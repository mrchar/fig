<script setup lang="ts">
export type Props = {
  label?: string
  description?: string
}

const props = defineProps<Props>()
</script>

<template>
  <fieldset class="fieldset">
    <legend v-if="props.label || $slots.label" class="fieldset-legend w-full">
      <template v-if="$slots.label">
        <slot name="label"></slot>
      </template>
      <template v-else>
        {{ props.label }}
      </template>
    </legend>
    <slot></slot>
    <p v-if="props.description" class="fieldset-label">
      {{ props.description }}
    </p>
  </fieldset>
</template>
