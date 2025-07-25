<script setup lang="ts">
import api from "@/api"
import type { VocabularyType } from "@/types"

const id = useRouteParams<string, number>("id", "", { transform: Number })
const saveParams = ref<VocabularyType>({ id: 0, name: "", description: "", definition: {} } as VocabularyType)

api.vocabulary
  .useGetVocabulary(id)
  .then(({ data }) => {
    if (data.value) {
      saveParams.value = data.value
    }
  })

const definitionString = computed({
  get(){
    return JSON.stringify(saveParams.value.definition, null, 2)
  },
  set(value){
    try{
      saveParams.value.definition = JSON.parse(value)
    }catch(e){
      console.debug("反序列化失败", e)
    }
  }
})

const route = useRoute()

const router = useRouter()

function goBack() {
  router.go(-1)
}

function saveVocabulary() {
  saveParams.value.definition = JSON.parse(definitionString.value)
  api.vocabulary.useUpdateVocabulary(id, {
    name: saveParams.value.name,
    description: saveParams.value.description,
    definition: saveParams.value.definition
  })
    .then(() => {
      router.go(-1)
    })
}

function applyChange(completionResult: string) {
  definitionString.value = completionResult
}
</script>

<template>
  <div class="flex gap-2">
    <Form class="flex-1 w-1/2 h-full p-4 flex flex-col gap-2">
      <FormItem label="名称">
        <Input class="w-full" v-model="saveParams.name" placeholder="请输入词汇名称" />
      </FormItem>
      <FormItem label="描述">
        <Input class="w-full" v-model="saveParams.description" placeholder="请输入词汇释义" />
      </FormItem>
      <FormItem class="w-full">
        <template #label>
          <div class="flex w-full justify-between">
            <div>定义</div>
            <IntelligentButton :datasource="api.completion.useCompleteVocabulary" @apply="applyChange">
              <template #editor="{completionResult}">
                <MonacoEditor class="w-full" :model-value="completionResult"
                              language="json"
                              :uri="`${route.path}/completion`" />
              </template>
            </IntelligentButton>
          </div>
        </template>
        <MonacoEditor ref="monaco-editor" class="w-full" v-model="definitionString" language="json" :uri="route.path" />
      </FormItem>
      <div class="flex justify-end gap-2">
        <Button @click="goBack">取消</Button>
        <Button priority="primary" @click="saveVocabulary">保存</Button>
      </div>
    </Form>
    <JsonSchemaEditor v-model="saveParams.definition" class="flex-1 w-1/2" />
  </div>
</template>
