<script lang="ts" setup>
export type Props = {
  label?: string
  suffix?: string
  placeholder?: string,
  formatter?: (value: any) => string,
  parser?: (value: string) => any
}

const model = defineModel()

const text = ref<any>("")

onMounted(()=>{
  watch(text, (newValue) => {
    if (props.parser) {
      try {
        model.value = props.parser(newValue)
      } catch (e) {

      }
      return
    }

    model.value = newValue
  }, { immediate: true })

  watch(model, (newValue) => {
    if (props.formatter) {
      try {
        text.value = props.formatter(newValue)
      } catch (e) {
      }

      return
    }

    text.value = newValue
  }, { immediate: true })
})


const props = defineProps<Props>()
</script>

<template>
  <fieldset class="fieldset">
    <legend v-if="props.label" class="fieldset-legend">
      {{ props.label }}
    </legend>
    <textarea v-model="text" class="textarea h-24" :placeholder="props.placeholder">
    </textarea>
    <div v-if="props.suffix" class="fieldset-label">
      {{ props.suffix }}
    </div>
  </fieldset>
</template>
