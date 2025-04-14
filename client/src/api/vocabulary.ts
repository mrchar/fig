import qs from "qs"
import { useApi } from "./api.ts"
import type { PagedResponse, PaginationParams, VocabularyType } from "@/types"

export function useListVocabularies(pagination: MaybeRef<PaginationParams>) {
  return useApi(computed(() => `/vocabularies?${qs.stringify(unref(pagination))}`))
    .get()
    .json<PagedResponse<VocabularyType>>()
}

export function useGetVocabulary(id: MaybeRef<number>) {
  return useApi(computed(() => `/vocabularies/${unref(id)}`)).get().json<VocabularyType>()
}

export type VocabularyConcept = {
  name: string,
  definition: any
}

export function useAddVocabulary(params: MaybeRef<VocabularyConcept>) {
  return useApi("/vocabularies").post(unref(params)).json<VocabularyType>()
}

export function useUpdateVocabulary(id: MaybeRef<number>, params: MaybeRef<VocabularyConcept>) {
  return useApi(`/vocabularies/${unref(id)}`).put(unref(params)).json<VocabularyType>()
}

export function useDeleteVocabulary(id: MaybeRef<number>) {
  return useApi(`/vocabularies/${unref(id)}`).delete().json<VocabularyType>()
}

export default {
  useListVocabularies,
  useGetVocabulary,
  useAddVocabulary,
  useUpdateVocabulary,
  useDeleteVocabulary
}
