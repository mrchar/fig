<script setup lang="ts">
const model = defineModel<any>({default: {}})
</script>

<template>
  <div class="grid grid-cols-2 gap-2">
    <FormItem label="类型">
      <TypeSelect v-model="model.type" />
    </FormItem>
    <FormItem label="标题">
      <Input v-model="model.title"/>
    </FormItem>
    <FormItem label="描述" class="col-span-2">
      <Input v-model="model.description"/>
    </FormItem>
    <FormItem label="最小值" class="col-span-1">
      <Input v-model="model.minimum" placeholder="输入最小值"/>
    </FormItem>
    <FormItem label="最大值" class="col-span-1">
      <Input v-model="model.maximum" placeholder="输入最大值"/>
    </FormItem>
    <slot name="external"/>
  </div>
</template>
