<script setup lang="ts">
import { markRaw } from "vue"
import { JsonForms } from "@jsonforms/vue"
import { vanillaRenderers } from "@jsonforms/vue-vanilla"

import api from "@/api"
import type { Form, Struct } from "@/types"

const id = useRouteParams<string, number>("id", "", { transform: Number })
const form = ref<Form>({
  name: "",
  description: "",
  struct: { id: 0, name: "", definition: {} } as Struct,
  jsonSchema: {
    type: "object",
    properties: { name: { type: "string", title: "name" } }
  },
  uiSchema: {
    type: "VerticalLayout",
    elements: [{
      "type": "Control",
      "scope": "#/properties/name"
    }]
  }
} as Form)

api.form
  .useGetForm(id)
  .then(({ data }) => {
    form.value = data.value as Form
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

const renderers = markRaw([
  ...vanillaRenderers
  // here you can add custom renderers
])

const freezeRenderers = Object.freeze(renderers)

const data = ref({
  name: "John Doe"
})

function onJsonFormsChange(event: any) {
  data.value = event.data
}

const monacoEditorRef = useTemplateRef("monaco-editor")
onClickOutside(monacoEditorRef, () => {
  form.value.uiSchema = JSON.parse(uiSchemaString.value)
})
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
      <FormItem label="表单定义">
        <MonacoEditor ref="monaco-editor"
                      v-model="uiSchemaString"
                      class="w-full"
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
      :renderers="freezeRenderers"
      @change="onJsonFormsChange"
    />
  </div>
</template>

