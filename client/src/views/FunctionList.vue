<script setup lang="ts">
import api from "@/api"
import type { PaginationParams } from "@/types"
import type { Column } from "@/components/Table.vue"
import useAuditColumns from "@/composables/audit-columns.ts"

const columns: Column[] = [
  {
    title: "序号", prop: "series", formatter({ index }) {
      return index + 1
    }
  },
  { title: "名称", prop: "name" },
  { title: "描述", prop: "description" },
  ...useAuditColumns(),
  {
    title: "操作", prop: "operations"
  }
]

const searchParams = ref({ keyword: "" })

const searchDatasource = (pagination: MaybeRef<PaginationParams>) => {
  return api.func.useListFunctions(searchParams, pagination)
}

const tableRef = useTemplateRef("table")

function refreshResult() {
  if (!tableRef.value) {
    return
  }

  tableRef.value.refresh()
}

function initAddParams() {
  return { name: "", description: "", content: "function func(args){\n  return args;\n}" }
}

const addParams = ref(initAddParams())

const dialogRef = useTemplateRef("dialog")

function openDialog() {
  dialogRef.value?.open()
}

function closeDialog() {
  if (!dialogRef.value) {
    return
  }

  dialogRef.value?.close()
  addParams.value = initAddParams()
}

function createFunction() {
  api.func.useAddFunctions(addParams)
    .then(({ data }) => {
      router.push("/function/detail/" + data.value!.id)
      addParams.value = initAddParams()
    })
}

const router = useRouter()

function gotoEditor(id: number) {
  router.push("/function/detail/" + id)
}

function deleteFunction(id: number) {
  api.func.useDeleteFunctions(id)
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
  <Table ref="table" :columns="columns" :datasource="searchDatasource">
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
                     @confirm="deleteFunction(row.id)"
      >
        删除
      </ConfirmButton>
    </template>
  </Table>
  <Dialog ref="dialog" title="新增函数">
    <Form @submit="createFunction">
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
        <Button priority="primary" @click="createFunction">
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
