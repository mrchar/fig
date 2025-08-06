<script setup lang="ts">
import * as Blockly from "blockly/core"
import "blockly/blocks"
import * as zhCn from "blockly/msg/zh-hans"
import { javascriptGenerator } from "blockly/javascript"

Blockly.setLocale(zhCn)

const toolbox = {
  "kind": "flyoutToolbox",
  "contents": [
    {
      "kind": "block",
      "type": "controls_if"
    },
    {
      "kind": "block",
      "type": "controls_whileUntil"
    }
  ]
}

const model = defineModel()

const workspace = shallowRef(null)

function generateCode() {
  if (!workspace.value) {
    return
  }

  model.value = javascriptGenerator.workspaceToCode(workspace.value)
}

const containerRef = useTemplateRef("container")

onMounted(() => {
  workspace.value = Blockly.inject(containerRef.value, {
    toolbox: toolbox,
    grid: {
      spacing: 20,
      length: 3,
      colour: "#ccc",
      snap: true
    },
    zoom: {
      controls: true,
      wheel: true,
      startScale: 1,
      maxScale: 3,
      minScale: 0.5,
      scaleSpeed: 0.5,
      pinch: true
    },
    trashcan: true
  })
})
</script>

<template>
  <div ref="container" class="w-full h-72"></div>
  <Button @click="generateCode" class="btn-primary">生成代码</Button>
</template>
