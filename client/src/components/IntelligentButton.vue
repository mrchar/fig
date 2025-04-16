<script setup lang="ts">
import { Icon } from "@iconify/vue"
import type {ShallowRef} from "vue"

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

const dialogRef = useTemplateRef("dialog")

function openDialog() {
  if (dialogRef.value) {
    dialogRef.value.open()
  }
}


const query = ref("")

const { isFetching, data: completionResult, execute: executeCompletion } = props.datasource(query)

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

const emit = defineEmits(["apply"])

function onClickApply() {
  emit("apply", completionResult.value)
  onClickClose()
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
        <Button @click="onClickGenerate">
          <template v-if="isFetching">
            <span class="loading loading-spinner loading-xs"></span>
          </template>
          <template v-else>
            生成
          </template>
        </Button>
      </FormItem>
      <slot name="editor" v-bind="{completionResult}"></slot>
    </Form>
    <template #footer>
      <div class="flex justify-end gap-2">
        <Button @click="onClickClose">取消</Button>
        <Button priority="primary" @click="onClickApply">应用</Button>
      </div>
    </template>
  </Dialog>
</template>
