<script setup lang="ts">
import api from "@/api"
import type { Column } from "@/components/Table.vue"
import type { PaginationParams } from "@/types"

const columns: Column[] = [
  {
    title: "序号", prop: "series", formatter({ index }) {
      return index + 1
    }
  },
  { title: "名称", prop: "name" },
  {
    title: "类型", prop: "definition", ellipsis: true, formatter({ value }) {
      return value?.type || "未知"
    }
  },
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

const searchParams = ref({ keyword: "" })

const datasource = (pagination: MaybeRef<PaginationParams>) => {
  return api.struct.useListStructs(searchParams, pagination)
}

const tableRef = useTemplateRef("table")

function onClickSearch() {
  tableRef.value!.refresh()
}

const dialogRef = useTemplateRef("dialog")

function onClickAdd() {
  if (dialogRef.value) {
    dialogRef.value.open()
  }
}

const addParams = ref({ name: "" })

function onClickCancel() {
  if (dialogRef.value) {
    dialogRef.value.close()
  }
  addParams.value = { name: "" }
}

const defaultDefinition = {
  "$schema": "http://json-schema.org/draft-07/schema"
}

const router = useRouter()

function createStruct() {
  api.struct.useAddStruct({ name: addParams.value.name, definition: defaultDefinition })
    .then(({ data }) => {
      addParams.value = { name: "" }
      dialogRef.value!.close()
      router.push(`/struct/detail/${data!.value!.id}`)
    })
}

function onClickEdit(id: number) {
  router.push(`/struct/detail/${id}`)
}

function onClickDelete(id: number) {
  api.struct.useDeleteStruct(id)
    .then(() => {
      tableRef.value!.refresh()
    })
}
</script>
<template>
  <Form class="flex gap-2" @submit="onClickSearch">
    <Input prefix="名称" v-model="searchParams.keyword" />
    <Button @click="onClickSearch">查询</Button>
  </Form>
  <Table ref="table" :columns="columns" :datasource="datasource">
    <template #operations>
      <Button priority="primary" @click="onClickAdd">新增</Button>
    </template>
    <template #columns.operations="{row}">
      <Button class="mr-1"
              type="ghost"
              priority="default"
              @click="onClickEdit(row.id)"
      >
        编辑
      </Button>
      <ConfirmButton type="ghost"
                     priority="danger"
                     title="删除确认"
                     content="您确定要删除这条数据吗？"
                     @confirm="onClickDelete(row.id)"
      >
        删除
      </ConfirmButton>
    </template>
  </Table>
  <Dialog ref="dialog" title="新增格式">
    <Form @submit="createStruct">
      <FormItem label="名称">
        <Input v-model="addParams.name" class="w-full" />
      </FormItem>
    </Form>
    <template #footer>
      <div class="w-full flex justify-end gap-2">
        <Button @click="onClickCancel">取消</Button>
        <Button priority="primary" @click="createStruct">
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
