<script setup lang="ts">
export type Column = {
  title: string,
  prop: string,
  formatter?: (params: { index: number, row: any, column: Column, value: any }) => any
}

export type Props = {
  uniqueKey?: string
  columns?: Column[],
  datasource?: () => { rows: any[] }
}

const props = withDefaults(defineProps<Props>(), {
  uniqueKey: "id",
  columns: () => ([]),
  datasource: () => {
    return { rows: [] }
  }
})

const { rows } = props.datasource()
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
        <th v-else> &nbsp;</th>
      </tr>
      </thead>
      <tbody>
      <!-- row 1 -->
      <tr v-for="(row, index) in rows" :key="row[props.uniqueKey]">
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
