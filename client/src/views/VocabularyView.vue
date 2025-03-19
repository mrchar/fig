<script setup lang="ts">
import type { Vocabulary } from "@/types"
import type { Column } from "@/components/Table.vue"

const columns: Column[] = [
  {
    title: "序号", prop: "series", formatter({ index }) {
      return index + 1
    }
  },
  { title: "名称", prop: "name" },
  { title: "定义", prop: "definition" },
  {
    title: "创建时间", prop: "createdAt",
    formatter({ value }) {
      return value && new Date(value).toLocaleString()
    }
  },
  {
    title: "更新时间", prop: "updatedAt",
    formatter({ value }) {
      return value && new Date(value).toLocaleString()
    }
  },
  {
    title: "操作", prop: "operations"
  }
]

const datasource = () => {
  return {
    rows: [
      {
        id: 1,
        name: "词汇1",
        definition: {},
        createdAt: "2024-03-19T22:38:23+08:00",
        updatedAt: "2024-03-19T22:38:23+08:00"
      },
      {
        id: 2,
        name: "词汇2",
        definition: {},
        createdAt: "2024-03-19T22:38:23+08:00",
        updatedAt: "2024-03-19T22:38:23+08:00"
      }
    ] as Vocabulary[]
  }
}

function onClickEdit(id: number) {
  console.log("Do edit record" + id)
}

function onClickDelete(id: number) {
  console.log("Do delete record" + id)
}
</script>

<template>
  <Table :columns="columns" :datasource="datasource">
    <template #operations>
      <Button priority="primary">添加</Button>
    </template>
    <template #columns.operations="{row}">
      <Button type="ghost" priority="default"
              class="mr-1" @click="onClickEdit(row.id)">编辑
      </Button>
      <Button type="ghost" priority="danger" @click="onClickDelete(row.id)">删除</Button>
    </template>
  </Table>
</template>
