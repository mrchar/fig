<script setup lang="ts">
import { Icon } from "@iconify/vue"
import { useJsonForms } from "@/composables/json-forms-renderers.ts"

import api from "@/api"
import type { FormType, StructType } from "@/types"

const id = useRouteParams<string, number>("id", "", { transform: Number })
const form = ref<FormType>({
  name: "",
  description: "",
  struct: { id: 0, name: "", definition: {} } as StructType,
  jsonSchema: {
    type: "object",
    properties: { name: { type: "string", title: "name" } }
  },
  uiSchema: {}
} as FormType)

api.form
  .useGetForm(id)
  .then(({ data }) => {
    form.value = data.value as FormType
  })

const uiSchemaString = ref("")
watch(() => form.value.uiSchema, (value: string) => {
  uiSchemaString.value = JSON.stringify(value, null, 2)
}, { immediate: true })

const route = useRoute()
const router = useRouter()

function onClickCancel() {
  router.go(-1)
}

function saveForm() {
  form.value.uiSchema = JSON.parse(uiSchemaString.value)
  api.form.useUpdateForm(id, {
    name: form.value.name,
    description: form.value.description,
    structId: form.value.struct.id,
    uiSchema: form.value.uiSchema
  })
    .then(() => {
      router.go(-1)
    })
}


const { JsonForms, renderers } = useJsonForms()

const data = ref({})

function onJsonFormsChange(event: any) {
  data.value = event.data
}

const monacoEditorRef = useTemplateRef<HTMLElement>("monaco-editor")
onClickOutside(monacoEditorRef, () => {
  form.value.uiSchema = JSON.parse(uiSchemaString.value)
})

const dialogRef = useTemplateRef("dialog")

function openDialog() {
  if (dialogRef.value) {
    dialogRef.value.open()
  }
}

const query = ref("")

const computedQuery = computed(() => {
  return "这是我提供的数据定义" + JSON.stringify(form.value.jsonSchema) + "。" + query.value
})

const {
  isFetching,
  data: completionResult,
  execute: executeCompletion
} = api.completion.useCompleteForm(computedQuery)

function onClickGenerate() {
  executeCompletion()
}

function onClickClose() {
  if (!dialogRef.value) {
    return
  }

  dialogRef.value.close()
  query.value = ""
  completionResult.value = ""
}

function onClickApply() {
  uiSchemaString.value = completionResult.value as string
  onClickClose()
}
</script>

<template>
  <div class="w-ful h-full flex gap-2 divide-x divide-gray-500">
    <Form class="w-full h-full p-4 flex flex-col gap-2">
      <FormItem label="名称">
        <Input v-model="form.name" class="w-full" placeholder="请输入表单名称" />
      </FormItem>
      <FormItem label="描述">
        <Input v-model="form.description" class="w-full" placeholder="请输入表单描述" />
      </FormItem>
      <FormItem label="数据定义">
        <Select v-model="form.struct" class="w-full"
                :datasource="api.struct.useListStructs"
                :formatter="item=>item.name"
        />
      </FormItem>
      <FormItem>
        <template #label>
          <div class="flex w-full justify-between">
            <div>表单定义</div>
            <Icon icon="mingcute:ai-line" class="w-6 h-6 cursor-pointer" @click="openDialog" />
          </div>
        </template>
        <MonacoEditor ref="monaco-editor"
                      v-model="uiSchemaString"
                      class="w-full"
                      language="json"
                      :uri="route.path"
        />
      </FormItem>
      <div class="flex justify-end gap-2">
        <Button @click="onClickCancel">取消</Button>
        <Button priority="primary" @click="saveForm">保存</Button>
      </div>
    </Form>
    <JsonForms
      :data="data"
      class=" max-h-120 w-full max-w-lg  p-2 "
      :schema="form.jsonSchema"
      :uischema="form.uiSchema"
      :renderers="renderers"
      @change="onJsonFormsChange"
    />
  </div>
  <Dialog ref="dialog">
    <Form class="flex flex-col gap-2">
      <FormItem class="flex gap-2">
        <Input v-model="query" class="w-full" />
        <Button @click="onClickGenerate">
          <template v-if="isFetching">
            <span class="loading loading-spinner loading-xs"></span>
          </template>
          <template v-else>
            生成
          </template>
        </Button>
      </FormItem>
      <MonacoEditor class="w-full" v-model="completionResult" language="json" :uri="`${route.path}/completion`" />
    </Form>
    <template #footer>
      <div class="flex justify-end gap-2">
        <Button @click="onClickClose">取消</Button>
        <Button priority="primary" @click="onClickApply">应用</Button>
      </div>
    </template>
  </Dialog>
</template>

