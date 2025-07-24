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

function isRequired(key: string): boolean {
  if (!model.value.required || model.value.required.length === 0) {
    return false
  }

  return model.value.required.includes(key)
}

function setRequired(key: string, required: boolean) {
  if (!model.value.required) {
    model.value.required = []
  }

  if (required) {
    if (model.value.required.includes(key)) {
      return
    }

    model.value.required.push(key)
  } else {
    const index = model.value.required.indexOf(key)
    if (index === -1) {
      return
    }

    model.value.required.splice(index, 1)
  }
}
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
    <FormItem label="必填项">
      <div v-for="(property, key) in model.properties" class="flex gap-2 items-center">
        <span>{{ property?.title || key }}</span>
        <Checkbox :model-value="isRequired(key)" @change="(value)=>{setRequired(key, value)}" />
      </div>
    </FormItem>
  </div>
</template>
