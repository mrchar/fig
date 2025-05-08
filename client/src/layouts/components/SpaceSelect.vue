<script setup lang="ts">

import { useSpaceStore } from "@/store"
import api from "@/api"

const { space, setSpace } = useSpaceStore()
</script>

<template>
  <Select prefix="空间"
          class="w-56"
          :model-value="space"
          :datasource="api.space.useListSpaces"
          :formatter="item=>({label:item.name, value: item.code})"
          :select-first="true"
          placeholder="请选择空间"
          @change="setSpace"
  />
</template>
