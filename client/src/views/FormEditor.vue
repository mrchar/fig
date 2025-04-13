<script setup lang="ts">
import api from "@/api"

const id = useRouteParams<string, number>("id", "", { transform: Number })
const form = ref({ name: "", description: "", struct: {}, uiSchema: {} })

api.form
  .useGetForm(id)
  .then(({ data }) => {
    form.value.name = data.value!.name
    form.value.description = data.value!.description!
    form.value.struct = data.value!.struct
    form.value.uiSchema = data.value!.uiSchema
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

</script>

<template>
  <Form class="max-w-xl w-full h-full p-4 flex flex-col gap-2"
        @submit="saveForm"
  >
    <Input class="w-full" v-model="form.name" label="名称" placeholder="请输入表单名称" />
    <Input class="w-full" v-model="form.description" label="描述" placeholder="请输入表单描述" />
    <Select v-model="form.struct" label="数据定义" class="w-full"
            :datasource="api.struct.useListStructs"
            :formatter="item=>item.name"
    />
    <MonacoEditor class="w-full" v-model="uiSchemaString" :uri="route.path" />
    <div class="flex justify-end gap-2">
      <Button @click="onClickCancel">取消</Button>
      <Button priority="primary" @click="saveForm">保存</Button>
    </div>
  </Form>
</template>
