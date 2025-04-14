<script setup lang="ts">
import type { Column } from "@/components/Table.vue"
import api from "@/api"
import type { PaginationParams } from "@/types"

const columns: Column[] = [
  {
    title: "序号", prop: "series", formatter({ index }) {
      return index + 1
    }
  },
  {
    title: "表单", prop: "form", formatter({ value }) {
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

const searchParams = ref({ form: {} })

const datasource = (pagination: MaybeRef<PaginationParams>) => {
  return api.record.useListRecords(computed(() => ({ formId: searchParams.value.form.id! })), pagination)
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

function initDialogFormData() {
  return { form: { id: 0 }, content: {} }
}

const dialogFormData = ref(initDialogFormData())

function onClickCancel() {
  if (dialogRef.value) {
    dialogRef.value.close()
  }

  dialogFormData.value = initDialogFormData()
}

const router = useRouter()

function createRecord() {
  api.record.useAddRecord({
    formId: dialogFormData.value.form.id,
    content: dialogFormData.value.content
  })
    .then(({ data }) => {
      dialogFormData.value = initDialogFormData()
      dialogRef.value!.close()
      router.push(`/record/detail/${data.value!.id}`)
    })
}

function onClickEdit(id: number) {
  router.push(`/record/detail/${id}`)
}

function onClickDelete(id: number) {
  api.record.useDeleteRecord(id)
    .then(() => {
      tableRef.value!.refresh()
    })
}
</script>

<template>
  <Form class="flex gap-2" @submit="onClickSearch">
    <Select prefix="表单" v-model="searchParams.form"
            :datasource="api.form.useListForms"
            :formatter="item => item.name" />
    <Button @click="onClickSearch">搜索</Button>
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
  <Dialog ref="dialog" title="新增记录">
    <Form @submit="createRecord">
      <FormItem label="表单">
        <Select v-model="dialogFormData.form" class="w-full"
                :datasource="api.form.useListForms"
                :formatter="item=>item.name"
        />
      </FormItem>
    </Form>
    <template #footer>
      <div class="w-full flex justify-end gap-2">
        <Button @click="onClickCancel">取消</Button>
        <Button priority="primary" @click="createRecord">
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
