import qs from "qs"
import { useApi } from "./api.ts"
import type { PagedResponse, PaginationParams, VocabularyType } from "@/types"

export function useListVocabularies(params: MaybeRef<{ keyword: string }>, pagination: MaybeRef<PaginationParams>) {
  const url = computed(() =>
    `/vocabularies?${qs.stringify({ ...unref(params), ...unref(pagination) })}`)
  return useApi(url)
    .get()
    .json<PagedResponse<VocabularyType>>()
}

export function useGetVocabulary(id: MaybeRef<number>) {
  const url = computed(() => `/vocabularies/${unref(id)}`)
  return useApi(url).get().json<VocabularyType>()
}

export type VocabularyConcept = {
  name: string,
  definition: any
}

export function useAddVocabulary(params: MaybeRef<VocabularyConcept>) {
  return useApi("/vocabularies").post(unref(params)).json<VocabularyType>()
}

export function useUpdateVocabulary(id: MaybeRef<number>, params: MaybeRef<VocabularyConcept>) {
  const url = computed(() => `/vocabularies/${unref(id)}`)
  return useApi(url).put(unref(params)).json<VocabularyType>()
}

export function useDeleteVocabulary(id: MaybeRef<number>) {
  const url = computed(() => `/vocabularies/${unref(id)}`)
  return useApi(url).delete().json<VocabularyType>()
}

export default {
  useListVocabularies,
  useGetVocabulary,
  useAddVocabulary,
  useUpdateVocabulary,
  useDeleteVocabulary
}
