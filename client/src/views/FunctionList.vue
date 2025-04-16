<script setup lang="ts">
import api from "@/api"
import type { PaginationParams } from "@/types"
import type { Column } from "@/components/Table.vue"

const columns :Column[]= [
  {
    title: "序号", prop: "series", formatter({ index }) {
      return index + 1
    }
  },
  { title: "名称", prop: "name" },
  { title: "描述", prop: "description" },
  {
    title: "创建时间", prop: "createdAt",
    formatter({ value }) {
      return value && new Date(value).toLocaleString()
    }
  },
  {
    title: "更新时间",
    prop: "updatedAt",
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
  return api.func.useListFunctions(searchParams, pagination)
}

const tableRef = useTemplateRef("table")

function initAddParams() {
  return { name: "", description: "", content: "function func(args){\n  return args;\n}" }
}

const addParams = ref(initAddParams())

const dialogRef = useTemplateRef("dialog")

function onClickCancel() {
  dialogRef.value?.close()
}

function createFunction() {
  api.func.useAddFunctions(addParams)
    .then(({ data }) => {
      router.push("/function/detail/" + data.value!.id)
      addParams.value = initAddParams()
    })
}

const router = useRouter()

function onClickAdd() {
  dialogRef.value?.open()
}

function onClickEdit(id: number) {
  router.push("/function/detail" + id)
}

function onClickDelete(id: number) {
  api.func.useDeleteFunctions(id)
    .then(() => {
      if (tableRef.value) {
        tableRef.value.refresh()
      }
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
              mode="ghost"
              priority="default"
              @click="onClickEdit(row.id)"
      >
        编辑
      </Button>
      <ConfirmButton mode="ghost"
                     priority="danger"
                     title="删除确认"
                     content="您确定要删除这条数据吗？"
                     @confirm="onClickDelete(row.id)"
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
        <Button @click="onClickCancel">取消</Button>
        <Button priority="primary" @click="createFunction">
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
