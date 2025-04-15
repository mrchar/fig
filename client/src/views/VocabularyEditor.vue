<script setup lang="ts">
import api from "@/api"
import { Icon } from "@iconify/vue"

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
watch(definition, (value: any) => {
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

const dialogRef = useTemplateRef("dialog")

function openDialog() {
  if (dialogRef.value) {
    dialogRef.value.open()
  }
}

const query = ref("")

const { isFetching, data: completionResult, execute: executeCompletion } = api.completion.useCompleteVocabulary(query)

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

function onClickApply(){
  definitionString.value = completionResult.value
  onClickClose()
}
</script>

<template>
  <Form class="w-full h-full p-4 flex flex-col gap-2">
    <FormItem label="名称">
      <Input class="w-full" v-model="name" placeholder="请输入词汇名称" />
    </FormItem>
    <FormItem>
      <template #label>
        <div class="flex w-full justify-between">
          <div>定义</div>
          <Icon icon="mingcute:ai-line" class="w-6 h-6 cursor-pointer" @click="openDialog" />
        </div>
      </template>
      <MonacoEditor class="w-full" v-model="definitionString" :uri="route.path" />
    </FormItem>
    <div class="flex justify-end gap-2">
      <Button @click="onClickCancel">取消</Button>
      <Button priority="primary" @click="saveVocabulary">保存</Button>
    </div>
  </Form>
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
      <MonacoEditor class="w-full" v-model="completionResult" :uri="`${route.path}/completion`" />
    </Form>
    <template #footer>
      <div class="flex justify-end gap-2">
        <Button @click="onClickClose">取消</Button>
        <Button priority="primary" @click="onClickApply">应用</Button>
      </div>
    </template>
  </Dialog>
</template>
