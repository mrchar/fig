<script setup lang="ts">
import api from "@/api"
import { storeToRefs } from "pinia"
import type { Column } from "@/components/Table.vue"
import type { PaginationParams } from "@/types"
import useAuditColumns from "@/composables/audit-columns.ts"
import { useSpaceStore } from "@/store"

const columns: Column[] = [
  {
    title: "序号", prop: "series", formatter({ index }) {
      return index + 1
    }
  },
  { title: "标识", prop: "key" },
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

// TODO: 实现参数变化时自动更新
const {space} = storeToRefs(useSpaceStore())
const searchParams = ref({ keyword: "" })
const searchDatasource = (pagination: MaybeRef<PaginationParams>) => {
  return api.vocabulary.useListVocabularies(
    computed(()=>({...searchParams.value, space:space.value.code })), pagination
  )
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

// 当key变化时，更新name
watch(()=>addParams.value.key,()=>{
  if(!addParams.value.key){
    return
  }

  if(!addParams.value.key.startsWith(addParams.value.name)){
    return
  }

  addParams.value.name = addParams.value.key
})

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

function createVocabulary() {
  api.vocabulary.useAddVocabulary(
    computed(()=>({...addParams.value, spaceCode: space.value.code}))
  )
    .then(({ data }) => {
      dialogRef.value!.close()
      addParams.value = initAddParams()
      router.push(`/vocabulary/detail/${data!.value!.id}`)
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
      <FormItem label="标识符">
        <Input v-model="addParams.key" class="w-full"/>
      </FormItem>
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
        <Button priority="primary" @click="createVocabulary">
          创建
        </Button>
      </div>
    </template>
  </Dialog>
</template>
