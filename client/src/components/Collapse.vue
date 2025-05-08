<script setup lang="ts">
export type Props = {
  title?: string
  content?: string
  border?: boolean
}

const model = defineModel()
const props = withDefaults(defineProps<Props>(),
  { title: "", content: "", border: true }
)
</script>

<template>
  <div class="collapse collapse-arrow"
       :class="props.border && ['border-base-300', 'border']"
  >
    <input v-model="model" type="checkbox" />
    <slot v-if="$slots.title" name="title" class="collapse-title font-semibold" />
    <div v-else class="collapse-title font-semibold">
      {{ props.title }}
    </div>
    <div class="collapse-content text-sm">
      <slot v-if="$slots.default" name="default" />
      <template v-else>
        {{ props.content }}
      </template>
    </div>
  </div>
</template>
