import qs from "qs"
import { useApi } from "./api.ts"
import type { PagedResponse, PaginationParams, Struct } from "@/types"

export function useListSchemas(pagination: MaybeRef<PaginationParams>) {
  return useApi(computed(() => `/structs?${qs.stringify(unref(pagination))}`))
    .get()
    .json<PagedResponse<Struct>>()
}

export function useGetSchema(id: MaybeRef<number>) {
  return useApi(computed(() => `/structs/${unref(id)}`)).get().json<Struct>()
}

export type SchemaConcept = {
  name: string,
  definition: any
}

export function useAddSchema(params: MaybeRef<SchemaConcept>) {
  return useApi("/structs").post(unref(params)).json<Struct>()
}

export function useUpdateSchema(id: MaybeRef<number>, params: MaybeRef<SchemaConcept>) {
  return useApi(`/structs/${unref(id)}`).put(unref(params)).json<Struct>()
}

export function useDeleteSchema(id: MaybeRef<number>) {
  return useApi(`/structs/${unref(id)}`).delete().json<Struct>()
}

export default {
  useListSchemas,
  useGetSchema,
  useAddSchema,
  useUpdateSchema,
  useDeleteSchema
}
