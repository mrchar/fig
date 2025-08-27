<script setup lang="ts">
const model = defineModel()
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
    <FormItem label="最小长度" class="col-span-1">
      <Input type="number" v-model="model.minLength" min="0"/>
    </FormItem>
    <FormItem label="最大长度" class="col-span-1">
      <Input type="number" v-model="model.maxLength" min="0"/>
    </FormItem>
    <FormItem label="格式" class="col-span-2">
      <Select v-model="model.format" :options="[
        {label:'日期时间', value:'data-time'},
        {label:'日期', value:'date'},
        {label:'时间', value:'time'},
        {label:'电子邮箱', value:'email'},
      ]"/>
    </FormItem>
    <slot name="external"/>
  </div>
</template>
