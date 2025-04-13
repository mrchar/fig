import qs from "qs"
import { useApi } from "./api.ts"
import type { Form, PagedResponse, PaginationParams } from "@/types"

export function useListForms(params: MaybeRef<{ keyword: string }>, pagination: MaybeRef<PaginationParams>) {
  return useApi(computed(() => `/forms?${qs.stringify({ ...unref(params), ...unref(pagination) })}`))
    .get()
    .json<PagedResponse<Form>>()
}

export function useGetForm(id: MaybeRef<number>) {
  return useApi(computed(() => `/forms/${unref(id)}`)).get().json<Form>()
}

export type AddOrUpdateFormParams = {
  name: string,
  description: string,
  structId: number,
  uiSchema: any
}

export function useAddForm(params: MaybeRef<AddOrUpdateFormParams>) {
  return useApi("/forms").post(unref(params)).json<Form>()
}

export function useUpdateForm(id: MaybeRef<number>, params: MaybeRef<AddOrUpdateFormParams>) {
  return useApi(`/forms/${unref(id)}`).put(unref(params)).json<Form>()
}

export function useDeleteForm(id: MaybeRef<number>) {
  return useApi(`/forms/${unref(id)}`).delete().json<Form>()
}

export default {
  useListForms,
  useGetForm,
  useAddForm,
  useUpdateForm,
  useDeleteForm
}
