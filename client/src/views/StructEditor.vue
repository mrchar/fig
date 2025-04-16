<script setup lang="ts">
import api from "@/api"

const id = useRouteParams<string, number>("id", "", { transform: Number })
const saveParams = ref({ id: 0, name: "", description: "", definition: {} })

api.struct
  .useGetStruct(id)
  .then(({ data }) => {
    if (data.value) {
      saveParams.value = data.value
    }
  })

const definitionString = ref("")
watch(() => saveParams.value.definition, (value: any) => {
  definitionString.value = JSON.stringify(value, null, 2)
}, { immediate: true })

const route = useRoute()
const router = useRouter()

function goBack() {
  router.go(-1)
}

function saveStruct() {
  saveParams.value.definition = JSON.parse(definitionString.value)
  api.struct.useUpdateStruct(id, {
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
  <Form class="w-full h-full p-4 flex flex-col gap-2">
    <FormItem label="名称">
      <Input class="w-full" v-model="saveParams.name" placeholder="请输入数据模型的名称" />
    </FormItem>
    <FormItem label="描述">
      <Input class="w-full" v-model="saveParams.description" placeholder="请输入数据模型的描述" />
    </FormItem>
    <FormItem>
      <template #label>
        <div class="flex w-full justify-between">
          <div>定义</div>
          <IntelligentButton :datasource="api.completion.useCompleteStruct"
                             @apply="applyChange">
            <template #editor="{completionResult}">
              <MonacoEditor class="w-full"
                            :model-value="completionResult"
                            language="json"
                            :uri="`${route.path}/completion`" />
            </template>

          </IntelligentButton>
        </div>
      </template>
      <MonacoEditor class="w-full" v-model="definitionString" language="json" :uri="route.path" />
    </FormItem>
    <div class="flex justify-end gap-2">
      <Button @click="goBack">取消</Button>
      <Button priority="primary" @click="saveStruct">保存</Button>
    </div>
  </Form>
</template>
