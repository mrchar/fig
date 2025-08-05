import NumberEditor from "./NumberEditor.vue"
import StringEditor from "./StringEditor.vue"
import ObjectEditor from "./ObjectEditor.vue"
import NullEditor from "@/components/JsonSchemaEditor/renderers/NullEditor.vue"
import ArrayEditor from "@/components/JsonSchemaEditor/renderers/ArrayEditor.vue"
import BooleanEditor from "@/components/JsonSchemaEditor/renderers/BooleanEditor.vue"

export type Renderer = {
  supports(model: any): boolean,
  component: any
}

export const arrayRenderer: Renderer = {
  supports(model: any): boolean {
    return model.type === "array"
  },
  component: ArrayEditor
}

export const booleanRenderer: Renderer = {
  supports(model: any): boolean {
    return model.type === "boolean"
  },
  component: BooleanEditor
}

export const nullRenderer: Renderer = {
  supports(model: any): boolean {
    return model.type === "null"
  },
  component: NullEditor
}

export const integerRenderer: Renderer = {
  supports(model: any) {
    return model.type === "integer"
  },
  component: NumberEditor
}

export const numberRenderer: Renderer = {
  supports(model: any) {
    return model.type == "number"
  },
  component: NumberEditor
}

export const stringRenderer: Renderer = {
  supports(model: any) {
    return model.type == "string"
  },
  component: StringEditor
}

export const objectRenderer: Renderer = {
  supports(model: any) {
    return model.type == "object"
  },
  component: ObjectEditor
}

export const defaultRenderers = [arrayRenderer, booleanRenderer, nullRenderer, integerRenderer, numberRenderer, stringRenderer, objectRenderer]

export function getMatchedRenderer(model: any, renderers: Renderer[]) {
  for (let renderer of renderers) {
    if (renderer.supports(model)) {
      return renderer
    }
  }
  return null
}

export const renderersKey = Symbol("renderers")

export function useProvide(renderers: Renderer[]) {
  provide(renderersKey, renderers)
}

export function useInject() {
  return inject<Renderer[]>(renderersKey)
}
