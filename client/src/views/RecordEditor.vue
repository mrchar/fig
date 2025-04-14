<script setup lang="ts">
import { markRaw } from "vue"
import { JsonForms } from "@jsonforms/vue"
import { vanillaRenderers } from "@jsonforms/vue-vanilla"

import api from "@/api"

const id = useRouteParams<string, number>("id", "", { transform: Number })

const { data } = api.record.useGetRecord(id)

const recordContent = ref({})
watch(data, () => {
  recordContent.value = data.value?.content || {}
}, { immediate: true })

function onJsonFormsChange(event: any) {
  recordContent.value = event.data
}

const router = useRouter()

function onClickCancel() {
  router.go(-1)
}

function saveRecord() {
  api.record.useUpdateRecord(id, { content: recordContent.value })
    .then(() => {
      router.go(-1)
    })
}

const renderers = markRaw([
  ...vanillaRenderers
  // here you can add custom renderers
])

const freezeRenderers = Object.freeze(renderers)
</script>

<template>
  <Form class="w-full h-full p-4 flex flex-col gap-2">
    <JsonForms
      :data="recordContent"
      class=" max-h-120 w-full max-w-lg  p-2 "
      :schema="data?.form.jsonSchema || {}"
      :uischema="data?.form.uiSchema ||{}"
      :renderers="freezeRenderers"
      @change="onJsonFormsChange"
    />
    <div class="flex justify-end gap-2">
      <Button @click="onClickCancel">取消</Button>
      <Button priority="primary" @click="saveRecord">保存</Button>
    </div>
  </Form>

</template>
