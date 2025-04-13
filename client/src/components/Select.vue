<script setup lang="ts">
import type { MaybeRef } from "vue"
import type { PagedResponse, PaginationParams } from "@/types"

export type Props = {
  label?: string
  placeholder?: string
  options?: any[]
  datasource?: SearchDatasource, // 获取选项的方法
  formatter?: (any) => string // 将选项转换为字符串
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

const model = defineModel()
const props = withDefaults(defineProps<Props>(),
  { options: [], placeholder: "please select a option" }
)

const keyword = ref("")
const pagination: PaginationParams = {
  page: 0, // TODO: 使用useFetch的拦截器处理
  size: 10
}
const searchParams = computed(() => ({ keyword: keyword.value }))
const { data, execute } = props?.datasource(searchParams, pagination)
|| { data: ref([]) }

let timeoutIndex = null
if (!props.datasource) {
  watch(keyword, () => {
    if (timeoutIndex) {
      clearTimeout(timeoutIndex)
    }

    timeoutIndex = setTimeout(() => {
      execute()
      timeoutIndex = null
    }, 500)
  })
}

const _options = computed((): any[] => {
  if (!props.datasource) {
    return props.options
  } else {
    return data?.value?.content || []
  }
})

const selectRef = useTemplateRef("select")
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
  const value = e.target.value
  if (!props.formatter) {
    emit("change", value)
    model.value = value
    return
  }

  const selected = _options.value.find(item => props.formatter(item) === value)
  emit("change", selected)
  model.value = selected
}
</script>

<template>
  <fieldset class="fieldset">
    <legend v-if="props.label" class="fieldset-legend">
      {{ props.label }}
    </legend>
    <!--<input v-model="keyword" type="text" class="input w-full" />-->
    <select ref="select" class="select w-full" @change="onSelectChange">
      <option v-if="props.placeholder" disabled selected>
        {{ props.placeholder }}
      </option>
      <option v-for="option in _options">
        {{ props.formatter ? props.formatter(option) : option }}
      </option>
    </select>
  </fieldset>
</template>
