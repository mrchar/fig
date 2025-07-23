<script setup lang="ts">
import loader from "@monaco-editor/loader"
import api from "@/api"

export type Props = {
  uri: string
  language: string
}

const content = defineModel<string | null>({ default: "" })
const props = defineProps<Props>()


let model = null

function createModel(monaco) {
  const uri = monaco.Uri.parse(props.uri)
  model = monaco.editor.createModel(content.value as string, props.language, uri)
}

function registerProvider(monaco) {
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
}

const skeletonRef = useTemplateRef("skeleton")

const containerRef = useTemplateRef("container")

function createMonacoEditor(monaco) {
  if (!containerRef.value) {
    console.error("找不到容器，MonacoEditor初始化失败")
    return
  }

  const editor = monaco.editor.create(containerRef.value, {
    model,
    automaticLayout: true,
    theme: "vs-dark"
  })

  skeletonRef.value.classList.add("opacity-0")
  setTimeout(()=>{
    skeletonRef.value.classList.add("hidden")
    containerRef.value.classList.remove("opacity-0")
  },500)

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

function loadMonacoEditor() {
  loader.init().then(moncao => {
    createModel(moncao)
    registerProvider(monaco)
    createMonacoEditor(monaco)
  })
}

function disposeModel() {
  model?.dispose()
}

onMounted(() => {
  loadMonacoEditor()
})

onUnmounted(() => {
  disposeModel()
})

</script>

<template>
  <div>
    <div ref="skeleton" class="flex flex-col gap-4 p-4 transition-opacity duration-500">
      <div class="skeleton h-4 w-1/4 rounded"></div>
      <div class="skeleton h-4 w-1/2 rounded"></div>
      <div class="skeleton h-4 w-3/4 rounded"></div>
      <div class="skeleton h-4 w-full rounded"></div>
      <div class="skeleton h-4 w-5/6 rounded"></div>
      <div class="skeleton h-4 w-1/3 rounded"></div>
      <div class="skeleton h-4 w-full rounded"></div>
      <div class="skeleton h-4 w-2/3 rounded"></div>
      <div class="skeleton h-4 w-4/5 rounded"></div>
      <div class="skeleton h-4 w-1/2 rounded"></div>
    </div>
    <div ref="container" class="min-h-96 opacity-0 transition-opacity duration-500">
    </div>
  </div>
</template>
