<script setup lang="ts">
import type { Column } from "@/components/Table.vue"
import api from "@/api"
import useAuditColumns from "@/composables/audit-columns.ts"

const router = useRouter()

const columns: Column[] = [
  {
    title: "序号", prop: "serial", formatter({ index }) {
      return index + 1
    }
  },
  {
    title: "名称", prop: "name"
  },
  ...useAuditColumns(),
  { title: "操作", prop: "operations" }
]

const tableRef = useTemplateRef("table")

function gotoEditor(code: string) {
  router.push(`/space/detail/${code}`)
}

function deleteSpace(code: string) {
  api.space.useDeleteSpace(code)
    .then(() => {
      if (tableRef.value) {
        tableRef.value.refresh()
      }
    })
}

const dialogRef = useTemplateRef("dialog")

function openDialog() {
  if (!dialogRef.value) {
    return
  }

  dialogRef.value.open()
}

const addParams = ref({ name: "" })

function addSpace() {
  api.space.useAddSpace(addParams).then(({ data }) => {
    if (dialogRef.value) {
      dialogRef.value.close()
    }
    addParams.value.name = ""
    if (data.value) {
      router.push(`/space/detail/${data.value.code!}`)
    }
  })
}

function closeDialog() {
  if (!dialogRef.value) {
    return
  }

  addParams.value.name = ""
  dialogRef.value.close()
}
</script>

<template>
  <Table ref="table" :columns :datasource="api.space.useListSpaces">
    <template #operations>
      <Button priority="primary" @click="openDialog">新增</Button>
    </template>
    <template #columns.operations="{row}">
      <Button mode="ghost" class="mr-1" @click="gotoEditor(row.code)">编辑</Button>
      <ConfirmButton mode="ghost" priority="danger" @confirm="deleteSpace(row.code)">删除</ConfirmButton>
    </template>
  </Table>
  <Dialog ref="dialog">
    <Form>
      <FormItem label="名称">
        <Input v-model="addParams.name" class="w-full" />
      </FormItem>
    </Form>
    <template #footer>
      <Button @click="closeDialog">取消</Button>
      <Button priority="primary" @click="addSpace">创建</Button>
    </template>
  </Dialog>
</template>
