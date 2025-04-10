import { useApi } from "@/api/api.ts"

function getSuggestions(keyword: MaybeRef<string>) {
  return useApi(computed(() => `/suggestions?keyword=` + unref(keyword)))
    .get()
    .json()
}

export default {
  getSuggestions
}
