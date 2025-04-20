<script setup lang="ts">
import api from "@/api"

const router = useRouter()

const code = useRouteParams<string>("code", null)

const updateParams = ref({ name: "" })

api.space.useGetSpace(code)
  .then(({ data }) => {
    updateParams.value = data.value
  })

function goBack() {
  router.go(-1)
}

function updateSpace() {
  api.space.useUpdateSpace(code, updateParams)
    .then(() => {
      router.go(-1)
    })
}

</script>

<template>
  <Form>
    <FormItem label="名称">
      <Input v-model="updateParams.name" class="w-full"></Input>
    </FormItem>
    <div class="text-right">
      <Button class="mr-1" @click="goBack">取消</Button>
      <Button priority="primary" @click="updateSpace">保存</Button>
    </div>
  </Form>
</template>
