<script setup lang="ts">
import type { ShallowRef } from "vue"
import type { PagedResponse, PaginationParams } from "@/types"
import Pagination from "@/components/Pagination.vue"

export type Column = {
  title: string,
  prop: string,
  ellipsis?: boolean,
  formatter?: (params: { index: number, row: any, column: Column, value: any }) => any
}

export type PageableDatasource<T = any> = (pagination: MaybeRef<PaginationParams>) => {
  isFetching: Readonly<ShallowRef<boolean>>,
  error: ShallowRef<any>,
  data: ShallowRef<PagedResponse<T> | null>,
  execute: (throwOnFailed?: boolean) => Promise<any>,
}

export type Props = {
  uniqueKey?: string
  caption?: string,
  columns?: Column[],
  datasource?: PageableDatasource
}

const props = withDefaults(defineProps<Props>(), {
  uniqueKey: "id",
  columns: () => ([]),
  datasource: (_: MaybeRef<PaginationParams>) => {
    return {
      isFetching: readonly(shallowRef(false)),
      error: shallowRef(null),
      data: shallowRef({ content: [], page: { size: 10, number: 1, totalElements: 0, totalPages: 0 } }),
      execute: () => Promise.resolve()
    } as ReturnType<PageableDatasource>
  }
})

const paginationParams = ref<PaginationParams>({
  size: 10,
  page: 1
})

const { data, execute } = props.datasource(paginationParams)

const pagination = computed(() => {
  const page = data.value?.page
  return {
    size: page?.size || 10,
    number: page?.number || 1,
    totalElements: page?.totalElements || 0
  }
})

function onPaginationChange(value: number) {
  paginationParams.value.page = value
  execute()
}

function refresh() {
  paginationParams.value.page = 0
  execute()
}

function renderCell(index: number, row: any, column: Column) {
  return column.formatter
    ? column.formatter({ index, row, column, value: row?.[column.prop] })
    : row?.[column.prop]
}

defineExpose({ refresh })
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
      <caption v-if="props.caption">
        {{ props.caption }}
      </caption>
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
        <td v-for="column in props.columns"
            :key="column.prop"
            class="text-nowrap max-w-3xs"
            :class="[column.ellipsis && 'overflow-hidden text-ellipsis']"
        >
          <template v-if="$slots['columns.'+column.prop]">
            <slot :name="'columns.'+column.prop" v-bind="{index,row, column,value:row?.[column.prop]}" />
          </template>
          <template v-else>
            {{ renderCell(index, row, column) }}
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
  <Pagination :size="pagination.size"
              :number="pagination.number"
              :total-elements="pagination.totalElements"
              @change="onPaginationChange" />
</template>
