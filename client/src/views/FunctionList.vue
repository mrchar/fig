<script setup lang="ts">
const columns = [
  {title:"序号", prop:"index", formatter({index}){return index+1}}
]
</script>

<template>
  <Table :columns="columns" :datasource="api.function.useGetFunctions">
    
  </Table>
</template>
