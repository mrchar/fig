import { useApi } from "@/api/api.ts"

function useGetSuggestions(keyword: MaybeRef<string>) {
  return useApi(computed(() => `/completions/suggestions?keyword=` + unref(keyword)))
    .get()
    .json()
}

function useCompleteVocabulary(query: MaybeRef<string>) {
  return useApi("/completions/vocabulary", { immediate: false }).post(query).text()
}

function useCompleteStruct(query: MaybeRef<string>) {
  return useApi("/completions/vocabulary", { immediate: false }).post(query).text()
}

function useCompleteForm(query: MaybeRef<string>) {
  return useApi("/completions/form", { immediate: false }).post(query).text()
}

function useCompleteFunction(query: MaybeRef<string>) {
  return useApi("/completions/function", { immediate: false }).post(query).text()
}

export default {
  useGetSuggestions,
  useCompleteVocabulary,
  useCompleteStruct,
  useCompleteForm,
  useCompleteFunction
}
