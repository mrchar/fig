<script setup lang="ts">
export type Props = {
  title?: string
}

const props = defineProps<Props>()

const dialogRef = useTemplateRef("dialog")

function open() {
  if (dialogRef.value) {
    dialogRef.value.showModal()
  }
}

function close() {
  if (dialogRef.value) {
    dialogRef.value.close()
  }
}

defineExpose({
  open,
  close
})
</script>

<template>
  <dialog ref="dialog" class="modal">
    <div class="modal-box">
      <h3 class="text-lg font-bold">
        {{ props?.title || "对话框" }}
      </h3>
      <slot></slot>
      <div class="modal-action">
        <slot v-if="$slots.footer" name="footer"></slot>
        <button v-else class="btn">Close</button>
      </div>
    </div>
  </dialog>
</template>
