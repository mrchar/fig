<script setup lang="ts">
import { arrow, offset, useFloating } from "@floating-ui/vue"

export type Props = {
  title?: string
  content?: string
  confirm?: string
  cancel?: string
}

const props = withDefaults(defineProps<Props>(), {
  title: "执行确认",
  content: "您确定要继续执行吗？",
  confirm: "确定",
  cancel: "取消"
})

const confirming = ref(false)

function onClickButton() {
  // 当用户点击按钮后会切换成确认中状态
  confirming.value = true
}

function onClickCancel() {
  confirming.value = false
}

const emit = defineEmits(["confirm"])

function onClickConfirm() {
  confirming.value = false
  emit("confirm")
}

const reference = useTemplateRef<HTMLElement>("reference")
const floating = useTemplateRef<HTMLElement>("floating")
const floatingArrow = useTemplateRef<HTMLElement>("floatingArrow")
const { floatingStyles, middlewareData } = useFloating(reference, floating, {
  placement: "bottom",
  middleware: [offset(10), arrow({ element: floatingArrow })]
})
</script>

<template>
  <Button ref="reference" v-bind="$attrs" @click="onClickButton">
    <slot></slot>
  </Button>
  <div ref="floating" v-if="confirming" :style="floatingStyles">
    <div
      ref="floatingArrow"
      class="card w-8 h-8 bg-base-100 rotate-45"
      :style="{
        position: 'absolute',
        left:
          middlewareData.arrow?.x != null
            ? `${middlewareData.arrow.x}px`
            : '',
        top:
          middlewareData.arrow?.y != null
            ? `${middlewareData.arrow.y}px`
            : '',
      }"
    ></div>
    <div class="card w-64 bg-base-100 card-xs shadow-sm">
      <div class="card-body">
        <h2 class="card-title">{{ props.title }}</h2>
        <p>{{ props.content }}</p>
        <div class="justify-end card-actions">
          <Button size="xs" @click="onClickCancel">
            {{ props.cancel }}
          </Button>
          <Button size="xs" priority="primary" @click="onClickConfirm">
            {{ props.confirm }}
          </Button>
        </div>
      </div>
    </div>
  </div>

</template>
