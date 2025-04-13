<script setup lang="ts">
import api from "@/api"

const id = useRouteParams<string, number>("id", "", { transform: Number })
const name = ref("")
const definition = ref({})

api.vocabulary
  .useGetVocabulary(id)
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

function saveVocabulary() {
  definition.value = JSON.parse(definitionString.value)
  api.vocabulary.useUpdateVocabulary(id,
    { name: name.value, definition: definition.value })
    .then(() => {
      router.go(-1)
    })
}

</script>

<template>
  <Form class="w-full h-full p-4 flex flex-col gap-2">
    <FormItem label="名称">
      <Input class="w-full" v-model="name" placeholder="请输入词汇名称" />
    </FormItem>
    <FormItem label="定义">
      <MonacoEditor class="w-full" v-model="definitionString" :uri="route.path" />
    </FormItem>
    <div class="flex justify-end gap-2">
      <Button @click="onClickCancel">取消</Button>
      <Button priority="primary" @click="saveVocabulary">保存</Button>
    </div>
  </Form>
</template>
