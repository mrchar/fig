<script setup lang="ts">
import * as monaco from "monaco-editor"
import editorWorker from "monaco-editor/esm/vs/editor/editor.worker?worker"
import jsonWorker from "monaco-editor/esm/vs/language/json/json.worker?worker"
import cssWorker from "monaco-editor/esm/vs/language/css/css.worker?worker"
import htmlWorker from "monaco-editor/esm/vs/language/html/html.worker?worker"
import tsWorker from "monaco-editor/esm/vs/language/typescript/ts.worker?worker"
import api from "@/api"

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

// 注册自定义完成项提供者
monaco.languages.registerCompletionItemProvider("json", {
  async provideCompletionItems(model, position) {
    // 获取用户当前输入的文本
    const currentLine = model.getLineContent(position.lineNumber)
    const currentInput = currentLine.slice(0, position.column - 1)

    if (!currentInput || currentInput.length == 0) {
      return
    }

    const { data } = await api.completion.useGetSuggestions(encodeURIComponent(currentInput.trim()))

    return {
      suggestions: data.value.content
    }
  }
})

export type Props = {
  uri: string
  language: string
}

const content = defineModel<string | null>({ default: "" })
const props = defineProps<Props>()
const uri = monaco.Uri.parse(props.uri)
const model = monaco.editor.createModel(content.value as string, props.language, uri)

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
      model.setValue(content.value as string)
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
  <div ref="container" class="min-h-96"></div>
</template>
