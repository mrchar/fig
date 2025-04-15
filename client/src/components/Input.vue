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
    <span v-if="props.prefix || $slots.prefix" class="label">
      <template v-if="$slots.prefix">
        <slot name="prefix"></slot>
      </template>
      <template v-else>
        {{ props.prefix }}
      </template>
    </span>
    <input v-model="model" :placeholder="props.placeholder" v-bind="$attrs" />
    <span v-if="props.suffix||$slots.suffix" class="label">
      <template v-if="$slots.suffix">
        <slot name="suffix"></slot>
      </template>
      <template v-else>
      {{ props.suffix }}
      </template>
    </span>
  </label>
</template>
