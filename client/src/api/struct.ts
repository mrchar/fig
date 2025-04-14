import qs from "qs"
import { useApi } from "./api.ts"
import type { PagedResponse, PaginationParams, StructType } from "@/types"

export function useListStructs(params: MaybeRef<{ keyword: string }>, pagination: MaybeRef<PaginationParams>) {
  return useApi(computed(() => `/structs?${qs.stringify({ ...unref(params), ...unref(pagination) })}`))
    .get()
    .json<PagedResponse<StructType>>()
}

export function useGetStruct(id: MaybeRef<number>) {
  return useApi(computed(() => `/structs/${unref(id)}`)).get().json<StructType>()
}

export type SchemaConcept = {
  name: string,
  definition: any
}

export function useAddStruct(params: MaybeRef<SchemaConcept>) {
  return useApi("/structs").post(unref(params)).json<StructType>()
}

export function useUpdateStruct(id: MaybeRef<number>, params: MaybeRef<SchemaConcept>) {
  return useApi(`/structs/${unref(id)}`).put(unref(params)).json<StructType>()
}

export function useDeleteStruct(id: MaybeRef<number>) {
  return useApi(`/structs/${unref(id)}`).delete().json<StructType>()
}

export default {
  useListStructs,
  useGetStruct,
  useAddStruct,
  useUpdateStruct,
  useDeleteStruct
}
