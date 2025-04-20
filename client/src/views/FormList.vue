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
    title: "数据定义", prop: "struct", formatter({ value }) {
      return value.name
    }
  },
  ...useAuditColumns(),
  {
    title: "操作", prop: "operations"
  }
]

const searchParams = ref({ keyword: "" })

const datasource = (pagination: MaybeRef<PaginationParams>) => {
  return api.form.useListForms(searchParams, pagination)
}

const tableRef = useTemplateRef("table")

function refreshResult() {
  tableRef.value!.refresh()
}

const dialogRef = useTemplateRef("dialog")

function openDialog() {
  if (!dialogRef.value) {
    return
  }

  dialogRef.value.open()
}

function initAddParams() {
  return { id: 0, name: "", description: "", struct: { id: 0, definition: {} }, uiSchema: {} }
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
  return `我要创建一个表单${addParams.value.name}，
  表单的内容数据类型为：${JSON.stringify(addParams.value.struct.definition)} ，
  这是对这个表单的描述${addParams.value.description}。
  请帮我创建表单定义。`
})

const {
  isFetching: isGenerating,
  data: completionResult,
  execute: executeGeneration
} = api.completion.useCompleteForm(query)

function createForm() {
  executeGeneration()
    .then(() => {
      if (completionResult.value) {
        addParams.value.uiSchema = JSON.parse(completionResult.value)
      }

      api.form.useAddForm({
        name: addParams.value.name,
        description: addParams.value.description,
        structId: addParams.value.struct.id,
        uiSchema: addParams.value.uiSchema
      })
        .then(({ data }) => {
          addParams.value = initAddParams()
          dialogRef.value!.close()
          router.push(`/form/detail/${data!.value!.id}`)
        })
    })

}

function gotoEditor(id: number) {
  router.push(`/form/detail/${id}`)
}

function deleteForm(id: number) {
  api.form.useDeleteForm(id)
    .then(() => {
      refreshResult()
    })
}
</script>

<template>
  <Form class="flex gap-2" @submit="refreshResult">
    <Input prefix="名称" v-model="searchParams.keyword" />
    <Button @click="refreshResult">查询</Button>
  </Form>
  <Table ref="table" :columns="columns" :datasource="datasource">
    <template #operations>
      <Button priority="primary" @click="openDialog">新增</Button>
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
                     @confirm="deleteForm(row.id)"
      >
        删除
      </ConfirmButton>
    </template>
  </Table>
  <Dialog ref="dialog" title="新增表单">
    <Form @submit="createForm">
      <FormItem label="名称">
        <Input v-model="addParams.name" class="w-full" />
      </FormItem>
      <FormItem label="描述">
        <Input v-model="addParams.description" class="w-full" />
      </FormItem>
      <FormItem label="数据定义">
        <Select v-model="addParams.struct" class="w-full"
                :datasource="api.struct.useListStructs"
                :formatter="item=>item.name"
        />
      </FormItem>
    </Form>
    <template #footer>
      <div class="w-full flex justify-end gap-2">
        <Button @click="closeDialog">取消</Button>
        <Button priority="primary" :loading="isGenerating" @click="createForm">
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
