<script setup lang="ts">
import type { MaybeRef, ShallowRef } from "vue"
import type { PagedResponse, PaginationParams } from "@/types"

export type Props = {
  prefix?: string,
  placeholder?: string,
  options?: any[],
  datasource?: SearchDatasource, // 获取选项的方法
  formatter?: (item: any) => { label: string, value: string } // 将选项转换为字符串
  selectFirst?: boolean
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

const emit = defineEmits(["change"])

const keyword = ref("")
const pagination: PaginationParams = {
  page: 1,
  size: 10
}
const searchParams = computed(() => ({ keyword: keyword.value }))
const { data, execute } = props.datasource
  ? props.datasource(searchParams, pagination)
  : { data: ref([]) }

watch(data, () => {
  if (!props.selectFirst) {
    return
  }

  const response: PagedResponse = data.value as PagedResponse
  if (response.content.length == 0) {
    return
  }
  if (model.value === null || !response.content.includes(model.value)) {
    emit("change", response.content[0])
    model.value = response.content[0]
  }
})

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

const selectValue = ref(null)

watch(model, () => {
  if (model.value === null) {
    return
  }

  selectValue.value = props.formatter
    ? props.formatter(model.value).value
    : model.value
}, { immediate: true })

watch(selectValue, (value) => {
  if (props.formatter) {
    const selected = _options.value.find(item => (props.formatter!(item).value === value))
    emit("change", selected)
    model.value = selected
  } else {
    emit("change", value)
    model.value = value
  }
})
</script>

<template>
  <label class="select">
    <span v-if="props.prefix" class="label">
      {{ props.prefix }}
    </span>
    <select ref="select" v-model="selectValue">
      <option v-if="props.placeholder" disabled selected>
        {{ props.placeholder }}
      </option>
      <option v-for="option in _options"
              :value="props.formatter ? props.formatter(option).value : option.value"
      >
        {{ props.formatter ? props.formatter(option).label : option.label }}
      </option>
    </select>
  </label>
</template>
