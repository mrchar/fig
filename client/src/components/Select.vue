<script setup lang="ts">
import type { MaybeRef, ShallowRef } from "vue"
import type { PagedResponse, PaginationParams } from "@/types"

export type Props = {
  placeholder?: string
  options?: any[]
  datasource?: SearchDatasource, // 获取选项的方法
  formatter?: (item: any) => string // 将选项转换为字符串
}

export type SearchDatasource<T = any> = (
  params: MaybeRef<{ keyword: string }>,
  pagination: MaybeRef<PaginationParams>
) => {
  isFetching: Readonly<ShallowRef<boolean>>,
  error: ShallowRef<any>,
  data: ShallowRef<PagedResponse<T> | null>,
  execute: (throwOnFailed?: boolean) => Promise<any>,
}

const model = defineModel<any>()
const props = withDefaults(defineProps<Props>(),
  { options: () => [], placeholder: "please select a option" }
)

const keyword = ref("")
const pagination: PaginationParams = {
  page: 0, // TODO: 使用useFetch的拦截器处理
  size: 10
}
const searchParams = computed(() => ({ keyword: keyword.value }))
const { data, execute } = props.datasource
  ? props.datasource(searchParams, pagination)
  : { data: ref([]) }

let timeoutIndex: number | null = null
if (!props.datasource) {
  watch(keyword, () => {
    if (timeoutIndex) {
      clearTimeout(timeoutIndex)
    }

    timeoutIndex = setTimeout(() => {
      execute && execute()

      timeoutIndex = null
    }, 500)
  })
}

const _options = computed((): any[] => {
  if (!props.datasource) {
    return props.options
  }

  if (data.value) {
    const response: PagedResponse = data.value as PagedResponse
    return response.content
  }

  return []
})

const selectRef = useTemplateRef<HTMLSelectElement>("select")
watch(model, () => {
  if (!selectRef.value) {
    return
  }

  selectRef.value.value = props.formatter
    ? props.formatter(model.value)
    : model.value
}, { immediate: true })

const emit = defineEmits(["change"])

function onSelectChange(e: Event) {
  const value = (e.target as HTMLSelectElement).value
  if (props.formatter) {
    const selected = _options.value.find(item => (props.formatter!(item) === value))
    emit("change", selected)
    model.value = selected
  } else {
    emit("change", value)
    model.value = value
  }
}
</script>

<template>
  <select ref="select" class="select w-full" @change="onSelectChange">
    <option v-if="props.placeholder" disabled selected>
      {{ props.placeholder }}
    </option>
    <option v-for="option in _options">
      {{ props.formatter ? props.formatter(option) : option }}
    </option>
  </select>
</template>
