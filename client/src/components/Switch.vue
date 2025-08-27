<script lang="ts" setup>
export type Props = {
  options:{key:string, label:string}[]
}

const model = defineModel()

const props = defineProps<Props>()

function onClick(key: string){
  model.value = key
}

</script>

<template>
  <div role="tablist" class="tabs tabs-box w-max">
    <a v-for="item in props.options" role="tab" :key="item.key"
       class="tab" :class="{'tab-active':item.key===model}"
       @click="onClick(item.key)"
    >
      {{item.label}}
    </a>
  </div>
</template>
