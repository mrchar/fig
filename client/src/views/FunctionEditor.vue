<script setup lang="ts">
// @ts-ignore
import Interpreter from "js-interpreter"
import api from "@/api"
import { useJsonForms } from "@/composables/json-forms-renderers.ts"

const id = useRouteParams<string, number>("id", "", { transform: Number })

const func = ref({ id: 0, name: "", description: "", content: "" })

api.func.useGetFunction(id)
  .then(({ data }) => {
    if (data.value) {
      func.value = data.value
    }
  })

const route = useRoute()

const router = useRouter()

function goBack() {
  router.go(-1)
}

function saveFunction() {
  api.func.useUpdateFunctions(id, {
    name: func.value.name,
    description: func.value.description,
    content: func.value.content
  })
    .then(() => {
      router.go(-1)
    })
}

const argumentStruct = ref({ definition: null })
const args = ref({})
const result = ref("")

const { JsonForms, renderers } = useJsonForms()

function onJsonFormsChange(event: any) {
  args.value = event.data
}

function execute() {
  const interpreter = new Interpreter(`var args = ${JSON.stringify(args.value)}`)
  interpreter.appendCode(func.value.content)
  interpreter.appendCode("func(args)")
  interpreter.run()
  result.value = interpreter.value
}

function applyChange(value: string) {
  func.value.content = value
}

const completionDatasource = function(query: MaybeRef<string>) {
  const computedQuery = computed(() => {
    if (argumentStruct.value.definition) {
      return "这是方法参数定义：" + JSON.stringify(argumentStruct.value.definition) + "。\n" + unref(query)
    }
    return unref(query)
  })
  return api.completion.useCompleteFunction(computedQuery)
}
</script>

<template>
  <div class="w-ful h-full flex gap-2 divide-x divide-gray-500">
    <Form class="w-full h-full p-4 flex flex-col gap-2">
      <FormItem label="名称">
        <Input v-model="func.name" class="w-full" />
      </FormItem>
      <FormItem label="描述">
        <Input v-model="func.description" class="w-full" />
      </FormItem>
      <FormItem label="参数">
        <Select v-model="argumentStruct"
                class="w-full"
                :datasource="api.struct.useListStructs"
                :formatter="item => ({label:item.name, value:item.name})"
        />
      </FormItem>
      <FormItem label="代码">
        <template #label>
          <div class="flex w-full justify-between">
            <div>代码</div>
            <IntelligentButton :datasource="completionDatasource"
                               @apply="applyChange">
              <template #editor="{completionResult}">
                <MonacoEditor class="w-full"
                              :model-value="completionResult"
                              language="javascript"
                              :uri="`${route.path}/completion`"
                />
              </template>
            </IntelligentButton>
          </div>
        </template>
        <MonacoEditor v-model="func.content" language="javascript" :uri="route.path" />
      </FormItem>
      <div class="flex justify-end gap-2">
        <Button @click="goBack">取消</Button>
        <Button priority="primary" @click="saveFunction">保存</Button>
      </div>
    </Form>
    <div>
      <JsonForms
        class=" max-h-120 w-full max-w-lg  p-2 "
        :schema="argumentStruct.definition!"
        :renderers="renderers"
        @change="onJsonFormsChange"
      />
      <Button v-if="argumentStruct.definition!" @click="execute">执行</Button>
      <div v-if="argumentStruct.definition">
        {{ JSON.stringify(result) }}
      </div>
    </div>
  </div>

</template>
