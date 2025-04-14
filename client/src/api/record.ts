import qs from "qs"
import { useApi } from "./api.ts"
import type { PagedResponse, PaginationParams, RecordType } from "@/types"

function useListRecords(pagination: MaybeRef<PaginationParams>) {
  const url = computed(() => `/records?${qs.stringify({ ...unref(pagination) })}`)
  return useApi(url).get().json<PagedResponse<RecordType>>()
}

function useGetRecord(id: MaybeRef<Number>) {
  const url = computed(() => `/records/${unref(id)}`)
  return useApi(url).get().json<RecordType>()
}

export type AddRecordParams = {
  formId: number
  content: any
}

function useAddRecord(params: MaybeRef<AddRecordParams>) {
  return useApi("/records").post(params).json<RecordType>()
}

export type UpdateRecordParams = {
  content: any
}

function useUpdateRecord(id: MaybeRef<number>, params: MaybeRef<UpdateRecordParams>) {
  const url = computed(() => `/records/${unref(id)}`)
  return useApi(url).put(unref(params).content).json<RecordType>()
}

function useDeleteRecord(id: MaybeRef<number>) {
  const url = computed(() => `/records/${unref(id)}`)
  return useApi(url).delete().json<RecordType>()
}

export default {
  useListRecords,
  useGetRecord,
  useAddRecord,
  useUpdateRecord,
  useDeleteRecord
}
