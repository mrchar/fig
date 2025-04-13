<script setup lang="ts">
import api from "@/api"

const id = useRouteParams<string, number>("id", "", { transform: Number })
const name = ref("")
const definition = ref()

api.struct
  .useGetStruct(id)
  .then(({ data }) => {
    name.value = data.value!.name
    definition.value = data.value!.definition
  })

const definitionString = ref("")
watch(definition, (value: string) => {
  definitionString.value = JSON.stringify(value, null, 2)
}, { immediate: true })

const route = useRoute()
const router = useRouter()

function onClickCancel() {
  router.go(-1)
}

function saveStruct() {
  definition.value = JSON.parse(definitionString.value)
  api.struct.useUpdateStruct(id, { name: name.value, definition: definition.value })
    .then(() => {
      router.go(-1)
    })
}

</script>

<template>
  <Form class="max-w-xl w-full h-full p-4 flex flex-col gap-2">
    <Input class="w-full" v-model="name" label="名称" placeholder="请输入格式名称" />
    <MonacoEditor class="w-full" v-model="definitionString" :uri="route.path" />
    <div class="flex justify-end gap-2">
      <Button @click="onClickCancel">取消</Button>
      <Button priority="primary" @click="saveStruct">保存</Button>
    </div>
  </Form>
</template>
