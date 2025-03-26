<script setup lang="ts">
import api from "@/api"

const id = useRouteParams<string, number>("id", "", { transform: Number })

const name = ref("")

const definition = ref()

api.schema
  .useGetSchema(id)
  .then(({ data }) => {
    name.value = data.value!.name
    definition.value = data.value!.definition
  })

const router = useRouter()

function onClickCancel() {
  router.go(-1)
}

function onClickSave() {
  api.schema.useUpdateSchema(id, { name: name.value, definition: definition.value })
    .then(() => {
      router.go(-1)
    })
}

</script>

<template>
  <div class="w-fit h-full p-4 flex flex-col gap-2">
    <Input v-model="name" label="名称" placeholder="请输入格式名称" />
    <Textarea v-model="definition"
              :formatter="(value)=>(JSON.stringify(value))"
              :parser="(value)=>(JSON.parse(value))"
              placeholder="请输入格式的定义" />
    <div class="flex justify-end gap-2">
      <Button @click="onClickCancel">取消</Button>
      <Button priority="primary" @click="onClickSave">保存</Button>
    </div>
  </div>
</template>
