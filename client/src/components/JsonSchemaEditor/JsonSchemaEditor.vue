<script setup lang="ts">
import { h } from "vue"
import type { Renderer } from "./renderers"
import { defaultRenderers, getMatchedRenderer, useProvide } from "./renderers"

type Props = {
  renderers?: Renderer[]
}
const model = defineModel()

const props = withDefaults(defineProps<Props>(), { renderers: ()=>defaultRenderers })

useProvide(props.renderers)

const renderer = computed(() => {
  if (!model.value || !props.renderers) {
    return
  }

  return getMatchedRenderer(model.value, props.renderers)
})

</script>

<template>
  <Form>
    <component
      v-if="renderer"
      v-model="model"
      :is="h(renderer.component)"
    />
  </Form>
</template>
