<script setup lang="ts">
import type { ShallowRef } from "vue"
import type { PagedResponse } from "@/types"

export type Column = {
  title: string,
  prop: string,
  formatter?: (params: { index: number, row: any, column: Column, value: any }) => any
}

export type Datasource<T = any> = () => {
  isFetching: Readonly<ShallowRef<boolean>>,
  error: ShallowRef<any>,
  data: ShallowRef<PagedResponse<T> | null>,
  execute: (throwOnFailed?: boolean) => Promise<any>,
}

export type Props = {
  uniqueKey?: string
  columns?: Column[],
  datasource?: Datasource
}

const props = withDefaults(defineProps<Props>(), {
  uniqueKey: "id",
  columns: () => ([]),
  datasource: () => {
    return {
      isFetching: readonly(shallowRef(false)),
      error: shallowRef(null),
      data: shallowRef({ content: [], size: 10, number: 1, totalElements: 0 }),
      execute: () => Promise.resolve()
    } as ReturnType<Datasource>
  }
})

const { data } = props.datasource()
</script>

<template>
  <div class="flex justify-between p-2">
    <div v-if="$slots.operations">
      <slot name="operations"></slot>
    </div>
    <div v-if="$slots.settings">
      <slot name="settings"></slot>
    </div>
  </div>
  <div class="overflow-x-auto">
    <table class="table">
      <!-- head -->
      <thead>
      <tr>
        <th v-if="props.columns.length>0" v-for="column in props.columns" :key="column.prop">
          {{ column.title }}
        </th>
        <th v-else>&nbsp;</th>
      </tr>
      </thead>
      <!-- body -->
      <tbody>
      <tr v-if="data?.content && data.content.length>0"
          v-for="(row, index) in data?.content"
          :key="row[props.uniqueKey]"
      >
        <td v-for="column in props.columns" :key="column.prop" class="text-nowrap">
          <template v-if="$slots['columns.'+column.prop]">
            <slot :name="'columns.'+column.prop" v-bind="{index,row, column,value:row?.[column.prop]}" />
          </template>
          <template v-else-if="column.formatter">
            {{ column.formatter({ index, row, column, value: row?.[column.prop] }) }}
          </template>
          <template v-else>
            {{ row?.[column.prop] }}
          </template>
        </td>
      </tr>
      <tr v-else>
        <td :colspan="columns?.length || 1" class="text-center p-8">
          没有数据
        </td>
      </tr>
      </tbody>
    </table>
  </div>
  <div class="join flex justify-end px-8 py-1">
    <button class="join-item btn">1</button>
    <button class="join-item btn btn-active">2</button>
    <button class="join-item btn">3</button>
    <button class="join-item btn">4</button>
  </div>
</template>
