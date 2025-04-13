<script setup lang="ts">
import api from "@/api"
import type { Column } from "@/components/Table.vue"
import type { Form, PaginationParams } from "@/types"

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

const params = ref({ keyword: "" })

const datasource = (pagination: MaybeRef<PaginationParams>) => {
  return api.form.useListForms(params, pagination)
}

const router = useRouter()

const dialogRef = useTemplateRef("dialog")

function onClickAdd() {
  if (dialogRef.value) {
    dialogRef.value.open()
  }
}

function initFormToAdd(): Form {
  return { name: "", description: "", struct: {}, uiSchema: {} } as Form
}

const formToAdd = ref<Form>(initFormToAdd())

function onClickCancel() {
  if (dialogRef.value) {
    dialogRef.value.close()
  }

  formToAdd.value = initFormToAdd()
}

function createForm() {
  api.form.useAddForm({
    name: formToAdd.value.name,
    description: formToAdd.value.description,
    structId: formToAdd.value.struct.id,
    uiSchema: formToAdd.value.uiSchema
  })
    .then(({ data }) => {
      formToAdd.value = initFormToAdd()
      dialogRef.value!.close()
      router.push(`/form/detail/${data!.value!.id}`)
    })
}

function onClickEdit(id: number) {
  router.push(`/form/detail/${id}`)
}

const tableRef = useTemplateRef("table")

function onClickDelete(id: number) {
  api.form.useDeleteForm(id)
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
  <Dialog ref="dialog" title="新增表单">
    <Form @submit="createForm">
      <Input v-model="formToAdd.name" label="名称" class="w-full" />
      <Input v-model="formToAdd.description" label="描述" class="w-full" />
      <Select v-model="formToAdd.struct" label="数据定义" class="w-full"
              :datasource="api.struct.useListStructs"
              :formatter="item=>item.name"
      />
    </Form>
    <template #footer>
      <div class="w-full flex justify-end gap-2">
        <Button @click="onClickCancel">取消</Button>
        <Button priority="primary" @click="createForm">
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
