<script setup lang="ts">
import Modeler from "bpmn-js/lib/Modeler"
import "bpmn-js/dist/assets/diagram-js.css"
import "bpmn-js/dist/assets/bpmn-font/css/bpmn.css"

const canvas = useTemplateRef("canvas")

const modeler = new Modeler()

function initModeler() {
  if (canvas.value) {
    modeler.attachTo(canvas.value)
  }

  modeler.on("commandStack.changed", () => {
    // user modeled something or
    // performed an undo/redo operation
    console.log("commandStack changed")
  })

  modeler.on("element.changed", (event) => {
    const element = event.element

    // the element was changed by the user
    console.log("element changed", element)
  })
}

onMounted(() => {
  initModeler()
})


</script>

<template>
  <div ref="canvas" class="w-full h-full bg-white"></div>
</template>
