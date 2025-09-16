<script setup lang="ts">

import { useSpaceStore } from "@/store"
import api from "@/api"

const store = useSpaceStore()
</script>

<template>
  <Select prefix="空间"
          class="w-56"
          :model-value="store.space"
          :datasource="api.space.useListSpaces"
          :formatter="item=>({label:item?.name, value: item?.code})"
          :select-first="true"
          placeholder="请选择空间"
          @change="store.setSpace"
  />
</template>
