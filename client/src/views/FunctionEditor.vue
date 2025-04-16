<script setup lang="ts">
const func = ref({code:"", name:"", description:"", content:""})

const route = useRoute()
</script>

<template>
  <Form>
    <FormItem label="编码">
      <Input :="func.code" class="w-full"/>
    </FormItem>
    <FormItem label="名称" >
      <Input v-model="func.name" class="w-full"/>
    </FormItem>
    <FormItem label="描述">
      <Input v-model="func.description" class="w-full"/>
    </FormItem>
    <FormItem label="代码">
      <MonacoEditor v-model="func.content" :uri="route.path"/>
    </FormItem>
  </Form>
</template>
