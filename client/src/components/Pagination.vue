<script setup lang="ts">
export type Props = {
  size: number,
  number: number,
  totalElements: number
}

const props = withDefaults(defineProps<Props>(),
  { number: 1, size: 10, totalElements: 100 }
)

const totalPages = computed(() => Math.ceil(props.totalElements / props.size))

const pageNumbers = computed(() => {
  const result = []
  // 页码不超过10个是的时候直接罗列
  if (totalPages.value <= 10) {
    for (let i = 1; i <= totalPages.value; i++) {
      result.push(i)
    }
    return result
  }

  // 左侧直接罗列，右侧省略
  if (props.number <= 5) {
    for (let i = 1; i <= 8; i++) {
      result.push(i)
    }
    result.push("...", totalPages.value)
    return result
  }

  // 右侧直接罗列，中间省略
  if (totalPages.value - props.number <= 4) {
    result.push(1, "...")
    for (let i = totalPages.value - 7; i <= totalPages.value; i++) {
      result.push(i)
    }
    return result
  }

  // 否则罗列左侧2个，右侧3个，两边省略
  result.push(1, "...")
  for (let i = props.number - 2; i <= props.number + 3; i++) {
    result.push(i)
  }

  result.push("...", totalPages.value)
  return result
})

const emit = defineEmits(["change"])

function onClickButton(value: number) {
  if (value === props.number) {
    return
  }

  emit("change", value)
}

</script>
<template>
  <div class="join flex justify-end px-8 py-1">
    <button class="join-item btn"
            :class="{'btn-active':item===props.number, 'btn-disabled':item==='...'}"
            v-for="item in pageNumbers"
            :key="item"
            @click="()=>onClickButton(item as number)"
    >
      {{ item }}
    </button>
  </div>
</template>
