<script setup lang="ts">
export type Props = {
  size?: "sm" | "md" | "lg" | "xl",
  type?: "normal" | "outline" | "dash" | "ghost" | "link"
  priority?: "default" | "primary" | "secondary" | "warning" | "danger"
}

const props = withDefaults(defineProps<Props>(),
  { size: "md", type: "normal", priority: "default" }
)

const computedClass = computed(() => {
  const result = []
  if (props.size !== "md") {
    result.push(`btn-${props.size}`)
  }

  if (props.type !== "normal") {
    result.push(`btn-${props.type}`)
  }

  if (props.type === "normal") {
    if (props.priority === "primary") {
      result.push("btn-primary")
    }
    if (props.priority === "secondary") {
      result.push("btn-primary", "btn-outline")
    }
    if (props.priority === "warning") {
      result.push("btn-warning", "btn-outline")
    }
    if (props.priority === "danger") {
      result.push("btn-error", "btn-outline")
    }
  }

  if (props.type === "outline" || props.type === "dash") {
    if (props.priority === "primary") {
      result.push("btn-primary")
    }
    if (props.priority === "secondary") {
      result.push("btn-primary")
    }
    if (props.priority === "warning") {
      result.push("btn-warning")
    }
    if (props.priority === "danger") {
      result.push("btn-error")
    }
  }

  if (props.type === "ghost" || props.type === "link") {
    if (props.priority === "primary") {
      result.push("text-primary")
    }
    if (props.priority === "warning") {
      result.push("text-warning")
    }
    if (props.priority === "danger") {
      result.push("text-error")
    }
  }

  return result
})

</script>

<template>
  <button class="btn" :class="computedClass">
    <slot></slot>
  </button>
</template>
