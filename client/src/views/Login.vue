<script setup lang="ts">
import { Icon } from "@iconify/vue"
import { useApi } from "@/api/api.ts"

useTitle("登录")

const showPassword = ref(false)

const params = ref({ username: "", password: "" })

const router = useRouter()

function login() {
  const formData = new FormData()
  formData.append("username", params.value.username)
  formData.append("password", params.value.password)
  useApi("/login").post(formData)
    .then(() => {
      router.push("/")
    })
}
</script>
<template>
  <div class="min-h-screen flex items-center justify-center">
    <div class="card w-96 bg-base-100 shadow-xl">
      <div class="card-body">
        <h2 class="card-title">登录</h2>
        <Form>
          <FormItem label="用户名">
            <Input v-model="params.username" placeholder="请输入用户名" />
          </FormItem>
          <FormItem label="密码">
            <Input v-model="params.password" :type="!showPassword && 'password'" placeholder="请输入密码">
              <template #suffix>
                <Icon class="w-6 h-6 cursor-pointer"
                      :icon="showPassword?'material-symbols:password-rounded':'mdi:eye'"
                      @mousedown="showPassword=true"
                      @mouseup="showPassword=false"
                />
              </template>
            </Input>
          </FormItem>
          <div class="mt-6">
            <Button priority="primary" @click="login">登录</Button>
          </div>
        </Form>
        <div class="divider">OR</div>
        <Button mode="outline" disabled>注册</Button>
      </div>
    </div>
  </div>
</template>
