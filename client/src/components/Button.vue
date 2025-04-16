<script setup lang="ts">
export type Props = {
  loading?: boolean,
  size?: "xs" | "sm" | "md" | "lg" | "xl",
  type?: "button" | "submit" | "reset",
  mode?: "normal" | "outline" | "dash" | "ghost" | "link"
  priority?: "default" | "primary" | "secondary" | "warning" | "danger"
}

const props = withDefaults(defineProps<Props>(),
  { loading: false, size: "md", type: "button", mode: "normal", priority: "default" }
)

const computedClass = computed(() => {
  const result = []
  if (props.size !== "md") {
    result.push(`btn-${props.size}`)
  }

  if (props.mode !== "normal") {
    result.push(`btn-${props.mode}`)
  }

  if (props.mode === "normal") {
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

  if (props.mode === "outline" || props.mode === "dash") {
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

  if (props.mode === "ghost" || props.mode === "link") {
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
  <button :type="props.type" class="btn" :class="computedClass">
    <template v-if="props.loading">
      <span class="loading loading-spinner loading-xs"></span>
    </template>
    <template v-else>
      <slot></slot>
    </template>
  </button>
</template>
