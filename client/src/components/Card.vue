<script setup lang="ts">
export type Props = {
  title?: string
  content?: string
}

const props = defineProps<Props>()
</script>

<template>
  <div class="card bg-base-100 w-96 shadow-sm">
    <div class="card-body">
      <slot v-if="$slots.title" name="title" class="card-title"/>
      <h2 v-else-if="props.title" class="card-title">
        {{ props.title }}
      </h2>
      <slot v-if="$slots.default" name="default" />
      <p v-else-if="props.content">
        {{ props.content }}
      </p>
      <div v-if="$slots.actions" class="card-actions justify-end">
        <slot name="actions" />
      </div>
    </div>
  </div>
</template>
