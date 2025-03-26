<script setup lang="ts">
import * as monaco from "monaco-editor"

const model = defineModel()

const containerRef = useTemplateRef("container")

onMounted(() => {
  if (!containerRef.value) {
    return
  }

  const editor = monaco.editor.create(containerRef.value, {
    value: "",
    language: "javascript",
    automaticLayout: true,
    theme: "vs-dark"
  })

  editor.onDidChangeModelContent(() => {
    model.value = editor.getValue()
  })

  watch(model, () => {
    editor.setValue(model.value)
  }, { immediate: true })
})

</script>

<template>
  <div ref="container" class="min-h-36"></div>
</template>
