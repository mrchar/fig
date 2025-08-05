<script setup lang="ts">
import { h, watch } from "vue"
import { getMatchedRenderer, useInject } from "@/components/JsonSchemaEditor/renderers/index.ts"

const model = defineModel()

watch(model, () => {
  if (!model.value.items) {
    model.value.items = {
      type: "string",
      title: "Items",
      description: "Items Description"
    }
  }
}, { immediate: true })

const renderers = useInject()

const itemsRender = computed(() => {
  const items = model.value.items
  if (!items) {
    return
  }

  return getMatchedRenderer(items, renderers)
})
</script>

<template>
  <div class="grid grid-cols-2 gap-2">
    <FormItem label="类型">
      <TypeSelect v-model="model.type" />
    </FormItem>
    <FormItem label="标题">
      <Input v-model="model.title" />
    </FormItem>
    <FormItem label="描述" class="col-span-2">
      <Input v-model="model.description"></Input>
    </FormItem>
    <FormItem label="元素类型" class="col-span-2">
      <div class="pl-2">
        <component v-if="itemsRender"
                   v-model="model.items"
                   :is="h(itemsRender.component)"
        />
      </div>
    </FormItem>
    <FormItem label="最小元素数量" class="col-span-1">
      <Input v-model.number="model.minItems" />
    </FormItem>
    <FormItem label="最大元素数量" class="col-span-1">
      <Input v-model="model.maxItems"/>
    </FormItem>
    <FormItem label="元素唯一">
      <Toggle v-model="model.uniqueItems" />
    </FormItem>
    <slot name="external" />
  </div>
</template>
