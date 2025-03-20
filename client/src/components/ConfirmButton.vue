<script setup lang="ts">
export type Props = {
  confirmText?: string
}

const props = withDefaults(defineProps<Props>(), { confirmText: "确认" })

const confirming = ref(false)

function onClickButton() {
  // 当用户点击按钮后会切换成确认中状态
  confirming.value = true
}

const confirmButtonRef = useTemplateRef("confirmButton")
onClickOutside(confirmButtonRef, () => {
  confirming.value = false
})

const emit = defineEmits(["confirm"])

function onClickConfirm() {
  confirming.value = false
  emit("confirm")
}
</script>

<template>
  <Button v-if="!confirming" v-bind="$attrs" @click="onClickButton">
    <slot></slot>
  </Button>
  <Button v-else ref="confirmButton" v-bind="$attrs" @click="onClickConfirm">
    {{ props.confirmText }}
  </Button>
</template>
