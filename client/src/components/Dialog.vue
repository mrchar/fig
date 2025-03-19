<script setup lang="ts">
export type Props = {
  title?: string
}

const props = defineProps<Props>()

const dialogRef = useTemplateRef("dialog")

function open() {
  dialogRef.value.showModal()
}

function close() {
  dialogRef.value.close()
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
        <form method="dialog">
          <!-- if there is a button in form, it will close the modal -->
          <button class="btn">Close</button>
        </form>
      </div>
    </div>
  </dialog>
</template>
