<script setup lang="ts">
const model = defineModel<any>()
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
      <Input v-model="model.description"></Input>
    </FormItem>
    <slot name="external"/>
  </div>
</template>
