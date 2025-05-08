<script setup lang="ts">
import { h } from "vue"
import { getMatchedRenderer, useInject } from "@/components/JsonSchemaEditor/renderers/index.ts"

const model = defineModel()

const renderers = useInject()

const propertiesRenderers = computed(() => {
  const properties = model.value.properties
  if (!properties) {
    return
  }

  const result = {}
  for (let key of Object.keys(properties)) {
    result[key] = getMatchedRenderer(properties[key], renderers)
  }

  return result
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
    <slot name="external" />
    <FormItem label="允许额外属性">
      <Radio v-model="model.additionalProperties" />
    </FormItem>
    <FormItem label="属性" class="col-span-2">
      <Collapse v-for="(item, key) in model.properties" :title="item.title || key" class="col-span-2">
        <component v-if="propertiesRenderers"
                   v-model="model.properties[key]"
                   :is="h(propertiesRenderers[key].component)"
        />
      </Collapse>
    </FormItem>
  </div>
</template>
