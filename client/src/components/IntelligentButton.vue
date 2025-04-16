<script setup lang="ts">
import { Icon } from "@iconify/vue"
import type { ShallowRef } from "vue"

export type CompletionDatasource = (query: MaybeRef<string>) => {
  isFetching: Readonly<ShallowRef<boolean>>,
  error: ShallowRef<any>,
  data: ShallowRef<string | null>,
  execute: (throwOnFailed?: boolean) => Promise<any>,
}

export type Props = {
  datasource: CompletionDatasource
}

const props = defineProps<Props>()

// 用户输入的提示词内容
const query = ref("")

const { isFetching, data: completionResult, execute: executeGeneration } = props.datasource(query)

const dialogRef = useTemplateRef("dialog")

function openDialog() {
  if (!dialogRef.value) {
    return
  }

  dialogRef.value.open()
}


function closeDialog() {
  if (!dialogRef.value) {
    return
  }

  dialogRef.value.close()
  query.value = ""
  completionResult.value = ""
}

const emit = defineEmits(["apply"])

function applyChange() {
  emit("apply", completionResult.value)
  closeDialog()
}
</script>

<template>
  <Button mode="ghost">
    <Icon icon="mingcute:ai-line" class="w-6 h-6 cursor-pointer" @click="openDialog" />
  </Button>
  <Dialog ref="dialog">
    <Form class="flex flex-col gap-2">
      <FormItem class="flex gap-2">
        <Input v-model="query" class="w-full" />
        <Button @click="executeGeneration" :loading="isFetching">
          生成
        </Button>
      </FormItem>
      <slot name="editor" v-bind="{completionResult}"></slot>
    </Form>
    <template #footer>
      <div class="flex justify-end gap-2">
        <Button @click="closeDialog">取消</Button>
        <Button priority="primary" @click="applyChange" :disabled="!completionResult">
          应用
        </Button>
      </div>
    </template>
  </Dialog>
</template>
