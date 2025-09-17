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

// 自动设置默认值
watch(_options, (value) => {
  // 如果没有启动自动选择第一个则直接返回
  if (!props.selectFirst) {
    return
  }

  // 如果选项是空的，则直接返回
  if (value?.length == 0) {
    return
  }

  const selected = props.formatter
    ? props.formatter(value[0]).value
    : value[0].value

  // 如果没有选中任何项目，则自动选择第一个选项
  if(model.value === null || model.value === undefined) {
    console.log("model.value is null or undefined")
    selectValue.value = selected
  }

  // 判断当前选项是否存在
  const exists = props.formatter
    ? value.some(item =>
      (props.formatter!(item).value === props.formatter!(model.value).value))
    : value.some(item => (item === model.value))

  // 如果当前选中项在列表中不存在，则直接选中第一项
  if (!exists) {
    selectValue.value = selected
  }
})

// 当model发生改变时，更新选中项
watch(model, () => {
  if (model.value === null) {
    return
  }

  selectValue.value = props.formatter
    ? props.formatter(model.value).value
    : model.value
}, { immediate: true })

// 当选中项更新时，发布时间并更新model
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
