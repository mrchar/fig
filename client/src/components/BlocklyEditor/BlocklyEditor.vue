<script setup lang="ts">
import type { WorkspaceSvg } from "blockly/core"
import * as Blockly from "blockly/core"
import "blockly/blocks"
import * as zhCn from "blockly/msg/zh-hans"
import { javascriptGenerator } from "blockly/javascript"
import toolbox from "./toolbox.json"


// @ts-ignore
Blockly.setLocale(zhCn)

const model = defineModel()

const workspace = shallowRef<WorkspaceSvg|null>(null)

function generateCode() {
  if (!workspace.value) {
    return
  }

  model.value = javascriptGenerator.workspaceToCode(workspace.value)
}

const containerRef = useTemplateRef("container")

onMounted(() => {
  workspace.value = Blockly.inject(containerRef.value as Element, {
    toolbox: toolbox,
    grid: {
      spacing: 20,
      length: 3,
      colour: "#ccc",
      snap: true
    },
    zoom: {
      controls: true,
      pinch: true
    },
    trashcan: true
  })
})
</script>

<template>
  <div ref="container" class="w-full min-h-96"></div>
  <Button @click="generateCode" class="btn-primary">生成代码</Button>
</template>
