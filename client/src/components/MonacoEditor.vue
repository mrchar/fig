<script setup lang="ts">
import * as monaco from "monaco-editor"
import editorWorker from "monaco-editor/esm/vs/editor/editor.worker?worker"
import jsonWorker from "monaco-editor/esm/vs/language/json/json.worker?worker"
import cssWorker from "monaco-editor/esm/vs/language/css/css.worker?worker"
import htmlWorker from "monaco-editor/esm/vs/language/html/html.worker?worker"
import tsWorker from "monaco-editor/esm/vs/language/typescript/ts.worker?worker"

self.MonacoEnvironment = {
  getWorker(_, label) {
    if (label === "json") {
      return new jsonWorker()
    }
    if (label === "css" || label === "scss" || label === "less") {
      return new cssWorker()
    }
    if (label === "html" || label === "handlebars" || label === "razor") {
      return new htmlWorker()
    }
    if (label === "typescript" || label === "javascript") {
      return new tsWorker()
    }
    return new editorWorker()
  }
}

export type Props = {
  uri: string
}

const content = defineModel({ default: "" })
const props = defineProps<Props>()
const uri = monaco.Uri.parse(props.uri)
const model = monaco.editor.createModel(content.value, "json", uri)

const containerRef = useTemplateRef("container")

function initMonacoEditor() {
  if (!containerRef.value) {
    console.error("找不到容器，MonacoEditor初始化失败")
    return
  }

  const editor = monaco.editor.create(containerRef.value, {
    model,
    automaticLayout: true,
    theme: "vs-dark"
  })

  model.onDidChangeContent(() => {
    content.value = model.getValue()
  })

  watch(content, () => {
    if (!editor.hasTextFocus()) {
      // 避免编辑代码时变更引起重绘
      model.setValue(content.value)
    }
  })
}

onMounted(() => {
  initMonacoEditor()
})

onUnmounted(() => {
  model && model.dispose()
})

</script>

<template>
  <div ref="container" class="min-h-36"></div>
</template>
