import { useApi } from "./api.ts"
import type { PagedResponse, Vocabulary } from "@/types"

export function useListVocabularies() {
  return useApi("/vocabularies").get().json<PagedResponse<Vocabulary>>()
}

export function useGetVocabulary(id: MaybeRefOrGetter<number>) {
  return useApi(computed(() => `/vocabularies/${unref(id)}`)).get().json<Vocabulary>()
}

export type VocabularyConcept = {
  name: string,
  definition: any
}

export function useAddVocabulary(params: MaybeRefOrGetter<VocabularyConcept>) {
  return useApi("/vocabularies").post(unref(params)).json<Vocabulary>()
}

export function useUpdateVocabulary(id: MaybeRefOrGetter<number>, params: MaybeRefOrGetter<VocabularyConcept>) {
  return useApi(`/vocabularies/${unref(id)}`).put(unref(params)).json<Vocabulary>()
}

export function useDeleteVocabulary(id: MaybeRefOrGetter<number>) {
  return useApi(`/vocabularies/${unref(id)}`).delete().json<Vocabulary>()
}

export default {
  useListVocabularies,
  useGetVocabulary,
  useAddVocabulary,
  useUpdateVocabulary,
  useDeleteVocabulary
}
