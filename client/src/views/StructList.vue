<script setup lang="ts">
import api from "@/api"
import type { Column } from "@/components/Table.vue"
import type { PaginationParams } from "@/types"
import useAuditColumns from "@/composables/audit-columns.ts"

const columns: Column[] = [
  {
    title: "序号", prop: "series", formatter({ index }) {
      return index + 1
    }
  },
  { title: "名称", prop: "name" },
  { title: "描述", prop: "description" },
  {
    title: "类型", prop: "definition", ellipsis: true, formatter({ value }) {
      return value?.type || "未知"
    }
  },
  ...useAuditColumns(),
  {
    title: "操作", prop: "operations"
  }
]

const searchParams = ref({ keyword: "" })

const searchDatasource = (pagination: MaybeRef<PaginationParams>) => {
  return api.struct.useListStructs(searchParams, pagination)
}

const tableRef = useTemplateRef("table")

function refreshResult() {
  tableRef.value!.refresh()
}

const dialogRef = useTemplateRef("dialog")

function onClickAdd() {
  if (!dialogRef.value) {
    return
  }

  dialogRef.value.open()
}

function initAddParams() {
  return {
    name: "",
    description: "",
    definition: {
      "$schema": "http://json-schema.org/draft-07/schema"
    }
  }
}

const addParams = ref(initAddParams())

function closeDialog() {
  if (dialogRef.value) {
    dialogRef.value.close()
  }

  addParams.value = initAddParams()
}

const router = useRouter()

const query = computed(() => {
  return `我要定义一个数据结构${addParams.value.name}, 这是对这个数据的解释：${addParams.value.description}。请帮我创建数据定义。`
})

const {
  isFetching: isGenerating,
  data: completionResult,
  execute: executeCompletion
} = api.completion.useCompleteStruct(query)

function createStruct() {
  executeCompletion().then(() => {
    if (completionResult.value) {
      addParams.value.definition = JSON.parse(completionResult.value)
    }

    api.struct.useAddStruct(addParams)
      .then(({ data }) => {
        addParams.value = initAddParams()
        dialogRef.value!.close()
        router.push(`/struct/detail/${data!.value!.id}`)
      })
  })

}

function gotoEditor(id: number) {
  router.push(`/struct/detail/${id}`)
}

function deleteStruct(id: number) {
  api.struct.useDeleteStruct(id)
    .then(() => {
      tableRef.value!.refresh()
    })
}
</script>
<template>
  <Form class="flex gap-2" @submit="refreshResult">
    <Input prefix="名称" v-model="searchParams.keyword" />
    <Button @click="refreshResult">查询</Button>
  </Form>
  <Table ref="table" :columns="columns" :datasource="searchDatasource">
    <template #operations>
      <Button priority="primary" @click="onClickAdd">新增</Button>
    </template>
    <template #columns.operations="{row}">
      <Button class="mr-1"
              mode="ghost"
              priority="default"
              @click="gotoEditor(row.id)"
      >
        编辑
      </Button>
      <ConfirmButton mode="ghost"
                     priority="danger"
                     title="删除确认"
                     content="您确定要删除这条数据吗？"
                     @confirm="deleteStruct(row.id)"
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
      <FormItem label="描述">
        <Input v-model="addParams.description" class="w-full" />
      </FormItem>
    </Form>
    <template #footer>
      <div class="w-full flex justify-end gap-2">
        <Button @click="closeDialog">取消</Button>
        <Button priority="primary"
                :loading="isGenerating"
                @click="createStruct"
        >
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
