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
  return api.vocabulary.useListVocabularies(searchParams, pagination)
}

const tableRef = useTemplateRef("table")

function refreshResult() {
  tableRef.value!.refresh()
}

const dialogRef = useTemplateRef("dialog")

function onClickOpenDialog() {
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
  return `我要定义一个词汇${addParams.value.name}, 这是这个词汇的解释：${addParams.value.description}。请帮我创建数据定义。`
})

const {
  isFetching: isGenerating,
  data: completionResult,
  execute: executeCompletion
} = api.completion.useCompleteVocabulary(query)

function createVocabulary() {
  executeCompletion().then(() => {
    if (completionResult.value) {
      addParams.value.definition = JSON.parse(completionResult.value)
    }
    api.vocabulary.useAddVocabulary(addParams)
      .then(({ data }) => {
        dialogRef.value!.close()
        addParams.value = initAddParams()
        router.push(`/vocabulary/detail/${data!.value!.id}`)
      })
  })

}

function gotoEditor(id: number) {
  router.push(`/vocabulary/detail/${id}`)
}

function deleteVocabulary(id: number) {
  api.vocabulary.useDeleteVocabulary(id)
    .then(() => {
      tableRef.value!.refresh()
    })
}

</script>

<template>
  <Form class="flex gap-2" @submit="refreshResult">
    <Input v-model="searchParams.keyword" prefix="名称" />
    <Button @click="refreshResult">查询</Button>
  </Form>
  <Table ref="table" :columns="columns" :datasource="searchDatasource">
    <template #operations>
      <Button priority="primary" @click="onClickOpenDialog">新增</Button>
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
                     @confirm="deleteVocabulary(row.id)"
      >
        删除
      </ConfirmButton>
    </template>
  </Table>
  <Dialog ref="dialog" title="新增词汇">
    <Form @submit="createVocabulary">
      <FormItem label="名称">
        <Input v-model="addParams.name" class="w-full" />
      </FormItem>
      <FormItem label="描述">
        <Input v-model="addParams.description" class="w-full" />
      </FormItem>
    </Form>
    <template #footer>
      <div class="w-full flex justify-end gap-2">
        <Button @click="closeDialog">
          取消
        </Button>
        <Button priority="primary" @click="createVocabulary" :loading="isGenerating">
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
