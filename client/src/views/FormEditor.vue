<script setup lang="ts">
import { useJsonForms } from "@/composables/json-forms-renderers.ts"

import api from "@/api"
import type { FormType, StructType } from "@/types"

const id = useRouteParams<string, number>("id", "", { transform: Number })
const saveParams = ref({
  id: 0,
  name: "",
  description: "",
  struct: { id: 0, name: "", definition: {} } as StructType,
  jsonSchema: {
    type: "object",
    properties: { name: { type: "string", title: "name" } }
  },
  uiSchema: {} as any
})

api.form
  .useGetForm(id)
  .then(({ data }) => {
    saveParams.value = data.value as FormType
  })

const uiSchemaString = ref("")
watch(() => saveParams.value.uiSchema, (value: string) => {
  uiSchemaString.value = JSON.stringify(value, null, 2)
}, { immediate: true })

const route = useRoute()
const router = useRouter()

function onClickCancel() {
  router.go(-1)
}

function saveForm() {
  saveParams.value.uiSchema = JSON.parse(uiSchemaString.value)
  api.form.useUpdateForm(id, {
    name: saveParams.value.name,
    description: saveParams.value.description,
    structId: saveParams.value.struct.id,
    uiSchema: saveParams.value.uiSchema
  })
    .then(() => {
      router.go(-1)
    })
}


const { JsonForms, renderers } = useJsonForms()

const formData = ref({})

function onJsonFormsChange(event: any) {
  formData.value = event.data
}

const monacoEditorRef = useTemplateRef<HTMLElement>("monaco-editor")
onClickOutside(monacoEditorRef, () => {
  saveParams.value.uiSchema = JSON.parse(uiSchemaString.value)
})

const completionDatasource = (query: MaybeRef<string>) => {
  const computedQuery = computed(() => {
    return "这是我提供的数据定义" + JSON.stringify(saveParams.value.jsonSchema) + "。" + unref(query)
  })
  return api.completion.useCompleteForm(computedQuery)
}

function applyChange(completionResult: string) {
  uiSchemaString.value = completionResult
}
</script>

<template>
  <div class="w-ful h-full flex gap-2 divide-x divide-gray-500">
    <Form class="w-full h-full p-4 flex flex-col gap-2">
      <FormItem label="名称">
        <Input v-model="saveParams.name" class="w-full" placeholder="请输入表单名称" />
      </FormItem>
      <FormItem label="描述">
        <Input v-model="saveParams.description" class="w-full" placeholder="请输入表单描述" />
      </FormItem>
      <FormItem label="数据定义">
        <Select v-model="saveParams.struct" class="w-full"
                :datasource="api.struct.useListStructs"
                :formatter="item=>({label:item.name, value: item.name})"
        />
      </FormItem>
      <FormItem>
        <template #label>
          <div class="flex w-full justify-between">
            <div>表单定义</div>
            <IntelligentButton :datasource="completionDatasource"
                               @apply="applyChange">
              <template #editor="{completionResult}">
                <MonacoEditor class="w-full"
                              :model-value="completionResult"
                              language="json"
                              :uri="`${route.path}/completion`"
                />
              </template>
            </IntelligentButton>
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
      :data="formData"
      class=" max-h-120 w-full max-w-lg  p-2 "
      :schema="saveParams.jsonSchema"
      :uischema="saveParams.uiSchema"
      :renderers="renderers"
      @change="onJsonFormsChange"
    />
  </div>
</template>

