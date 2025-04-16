import qs from "qs"
import { useApi } from "@/api/api.ts"
import type { FunctionType, PagedResponse, PaginationParams } from "@/types"


function useListFunctions(params: MaybeRef<{ keyword: string }>, pagination: MaybeRef<PaginationParams>) {
  const url = computed(() => `/functions?${qs.stringify({ ...unref(params), ...pagination })}`)
  return useApi(url).get().json<PagedResponse<FunctionType>>()
}

function useGetFunction(id: MaybeRef<number>) {
  const url = computed(() => `/functions/${unref(id)}`)
  return useApi(url).get().json<FunctionType>()
}

export type AddOrUpdateFunctionParams = {
  name: string
  description: string
  content: string
}

function useAddFunctions(params: MaybeRef<AddOrUpdateFunctionParams>) {
  return useApi("/functions").post(params).json<FunctionType>()
}

function useUpdateFunctions(id: MaybeRef<number>, params: MaybeRef<AddOrUpdateFunctionParams>) {
  const url = computed(() => `/functions/${id}`)
  return useApi(url).put(params).json<FunctionType>()
}

function useDeleteFunctions(id: MaybeRef<number>) {
  const url = computed(() => `/functions/${id}`)
  return useApi(url).delete().json<FunctionType>()
}

export default {
  useListFunctions,
  useGetFunction,
  useAddFunctions,
  useUpdateFunctions,
  useDeleteFunctions
}
