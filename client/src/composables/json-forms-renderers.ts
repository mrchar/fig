import { JsonForms } from "@jsonforms/vue"
import { vanillaRenderers } from "@jsonforms/vue-vanilla"

export function useJsonForms() {
  const renderers = markRaw([
    ...vanillaRenderers
    // here you can add custom renderers
  ])

  return {JsonForms, renderers: Object.freeze(renderers) }
}

export default useJsonForms
