import qs from "qs"
import { useApi } from "./api.ts"
import type { PagedResponse, PaginationParams, Vocabulary } from "@/types"

export function useListVocabularies(pagination: MaybeRef<PaginationParams>) {
  return useApi(computed(() => `/vocabularies?${qs.stringify(unref(pagination))}`))
    .get()
    .json<PagedResponse<Vocabulary>>()
}

export function useGetVocabulary(id: MaybeRef<number>) {
  return useApi(computed(() => `/vocabularies/${unref(id)}`)).get().json<Vocabulary>()
}

export type VocabularyConcept = {
  name: string,
  definition: any
}

export function useAddVocabulary(params: MaybeRef<VocabularyConcept>) {
  return useApi("/vocabularies").post(unref(params)).json<Vocabulary>()
}

export function useUpdateVocabulary(id: MaybeRef<number>, params: MaybeRef<VocabularyConcept>) {
  return useApi(`/vocabularies/${unref(id)}`).put(unref(params)).json<Vocabulary>()
}

export function useDeleteVocabulary(id: MaybeRef<number>) {
  return useApi(`/vocabularies/${unref(id)}`).delete().json<Vocabulary>()
}

export default {
  useListVocabularies,
  useGetVocabulary,
  useAddVocabulary,
  useUpdateVocabulary,
  useDeleteVocabulary
}
