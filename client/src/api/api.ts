import { createFetch } from "@vueuse/core"

export const useApi = createFetch({
  baseUrl: "http://localhost:8080/api"
})
