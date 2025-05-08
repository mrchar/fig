<script setup lang="ts">
export type Props = {
  onText?: string
  offText?: string
}
const model = defineModel()

const props = withDefaults(defineProps<Props>(),
  { onText: "ON", offText: "OFF" }
)
</script>

<template>
  <label class="swap swap-rotate">
    <input v-model="model" type="checkbox" />

    <span class="swap-on">
      <slot v-if="$slots.on" name="on" class="swap-on" />
      <template v-else>
        {{ props.onText }}
      </template>
    </span>


    <span class="swap-off">
      <slot v-if="$slots.off" name="off" />
      <template v-else>
      {{ props.offText }}
      </template>
    </span>
  </label>
</template>
