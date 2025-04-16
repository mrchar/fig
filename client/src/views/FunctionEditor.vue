<script setup lang="ts">
import Interpreter from "js-interpreter"
import api from "@/api"
import { useJsonForms } from "@/composables/json-forms-renderers.ts"

const id = useRouteParams<string, number>("id", "", { transform: Number })

const name = ref("")
const description = ref("")
const content = ref("")

api.func.useGetFunction(id)
  .then(({ data }) => {
    name.value = data?.value!.name
    description.value = data?.value!.description
    content.value = data?.value!.content
  })

const route = useRoute()

const router = useRouter()

function onClickCancel() {
  router.go(-1)
}

function saveFunction() {
  api.func.useUpdateFunctions(id, {
    name: name.value,
    description: description.value,
    content: content.value
  }).then(() => {
    router.go(-1)
  })
}

const struct = ref({})
const args = ref({})
const result = ref("")

const { JsonForms, renderers } = useJsonForms()

function onJsonFormsChange(event: any) {
  args.value = event.data
}

function execute() {
  const interpreter = new Interpreter(`var args = ${JSON.stringify(args.value)}`)
  interpreter.appendCode(content.value)
  interpreter.appendCode("func(args)")
  interpreter.run()
  result.value = interpreter.value
}
</script>

<template>
  <div class="w-ful h-full flex gap-2 divide-x divide-gray-500">
    <Form class="w-full h-full p-4 flex flex-col gap-2">
      <FormItem label="名称">
        <Input v-model="name" class="w-full" />
      </FormItem>
      <FormItem label="描述">
        <Input v-model="description" class="w-full" />
      </FormItem>
      <FormItem label="参数">
        <Select v-model="struct"
                class="w-full"
                :datasource="api.struct.useListStructs"
                :formatter="item => item.name"
        />
      </FormItem>
      <FormItem label="代码">
        <MonacoEditor v-model="content" language="javascript" :uri="route.path" />
      </FormItem>
      <div class="flex justify-end gap-2">
        <Button @click="onClickCancel">取消</Button>
        <Button priority="primary" @click="saveFunction">保存</Button>
      </div>
    </Form>
    <div>
      <JsonForms
        class=" max-h-120 w-full max-w-lg  p-2 "
        :schema="struct.definition"
        :renderers="renderers"
        @change="onJsonFormsChange"
      />
      <Button v-if="struct.definition" @click="execute">执行</Button>
      <div v-if="struct.definition">
        {{ JSON.stringify(result) }}
      </div>
    </div>
  </div>

</template>
