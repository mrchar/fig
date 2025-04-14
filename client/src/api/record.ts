import qs from "qs"
import { useApi } from "./api.ts"
import type { PagedResponse, PaginationParams, RecordType } from "@/types"

function useListRecords(pagination: MaybeRef<PaginationParams>) {
  return useApi("/records?" + qs.stringify({ ...unref(pagination) })).get().json<PagedResponse<RecordType>>()
}

function useGetRecord(id: MaybeRef<Number>) {
  return useApi(`/records/${unref(id)}`).get().json<RecordType>()
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
  return useApi(computed(() => `/records/${unref(id)}`)).put(unref(params).content).json<RecordType>()
}

function useDeleteRecord(id: MaybeRef<number>) {
  return useApi(computed(() => `/records/${unref(id)}`)).delete()
}

export default {
  useListRecords,
  useGetRecord,
  useAddRecord,
  useUpdateRecord,
  useDeleteRecord
}
