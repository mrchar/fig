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

const datasource = (pagination: MaybeRef<PaginationParams>) => {
  return api.schema.useListSchemas(pagination)
}

const router = useRouter()

const dialogRef = useTemplateRef("dialog")

function onClickAdd() {
  if (dialogRef.value) {
    dialogRef.value.open()
  }
}

const nameToAdd = ref("")

function onClickCancel() {
  if (dialogRef.value) {
    dialogRef.value.close()
  }
  nameToAdd.value = ""
}

function onClickCreate() {
  api.schema.useAddSchema({ name: nameToAdd.value, definition: {} })
    .then(({ data }) => {
      nameToAdd.value = ""
      dialogRef.value!.close()
      router.push(`/schema/detail/${data!.value!.id}`)
    })
}

function onClickEdit(id: number) {
  router.push(`/schema/detail/${id}`)
}

const tableRef = useTemplateRef("table")


function onClickDelete(id: number) {
  api.schema.useDeleteSchema(id)
    .then(() => {
      tableRef.value!.refresh()
    })
}
</script>
<template>
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
    <Form>
      <Input v-model="nameToAdd" label="名称" class="w-full" />
    </Form>
    <template #footer>
      <div class="w-full flex justify-end gap-2">
        <Button @click="onClickCancel">取消</Button>
        <Button priority="primary" @click="onClickCreate">
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
