import qs from "qs"
import { useApi } from "./api.ts"
import type { PagedResponse, PaginationParams, Schema } from "@/types"

export function useListSchemas(pagination: MaybeRef<PaginationParams>) {
  return useApi(computed(() => `/schemas?${qs.stringify(unref(pagination))}`))
    .get()
    .json<PagedResponse<Schema>>()
}

export function useGetSchema(id: MaybeRef<number>) {
  return useApi(computed(() => `/schemas/${unref(id)}`)).get().json<Schema>()
}

export type SchemaConcept = {
  name: string,
  definition: any
}

export function useAddSchema(params: MaybeRef<SchemaConcept>) {
  return useApi("/schemas").post(unref(params)).json<Schema>()
}

export function useUpdateSchema(id: MaybeRef<number>, params: MaybeRef<SchemaConcept>) {
  return useApi(`/schemas/${unref(id)}`).put(unref(params)).json<Schema>()
}

export function useDeleteSchema(id: MaybeRef<number>) {
  return useApi(`/schemas/${unref(id)}`).delete().json<Schema>()
}

export default {
  useListSchemas,
  useGetSchema,
  useAddSchema,
  useUpdateSchema,
  useDeleteSchema
}
