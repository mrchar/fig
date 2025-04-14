import qs from "qs"
import { useApi } from "./api.ts"
import type { PagedResponse, PaginationParams, StructType } from "@/types"

export function useListStructs(params: MaybeRef<{ keyword: string }>, pagination: MaybeRef<PaginationParams>) {
  const url = computed(() => `/structs?${qs.stringify({ ...unref(params), ...unref(pagination) })}`)
  return useApi(url)
    .get()
    .json<PagedResponse<StructType>>()
}

export function useGetStruct(id: MaybeRef<number>) {
  const url = computed(() => `/structs/${unref(id)}`)
  return useApi(url).get().json<StructType>()
}

export type SchemaConcept = {
  name: string,
  definition: any
}

export function useAddStruct(params: MaybeRef<SchemaConcept>) {
  return useApi("/structs").post(unref(params)).json<StructType>()
}

export function useUpdateStruct(id: MaybeRef<number>, params: MaybeRef<SchemaConcept>) {
  const url = computed(() => `/structs/${unref(id)}`)
  return useApi(url).put(unref(params)).json<StructType>()
}

export function useDeleteStruct(id: MaybeRef<number>) {
  const url = computed(() => `/structs/${unref(id)}`)
  return useApi(url).delete().json<StructType>()
}

export default {
  useListStructs,
  useGetStruct,
  useAddStruct,
  useUpdateStruct,
  useDeleteStruct
}
