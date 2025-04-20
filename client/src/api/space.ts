import { useApi } from "@/api/api.ts"
import type { PagedResponse, SpaceType } from "@/types"

function useListSpaces() {
  return useApi("/spaces").get().json<PagedResponse<SpaceType>>()
}

function useGetSpace(code: MaybeRef<string>) {
  const url = computed(() => `/spaces/${unref(code)}`)
  return useApi(url).get().json<SpaceType>()
}

export type AddOrUpdateSpaceParams = {
  name: string
}

function useAddSpace(params: MaybeRef<AddOrUpdateSpaceParams>) {
  return useApi("/spaces").post(unref(params)).json<SpaceType>()
}

function useUpdateSpace(code: MaybeRef<string>, params: MaybeRef<AddOrUpdateSpaceParams>) {
  const url = computed(() => `/spaces/${unref(code)}`)
  return useApi(url).put(unref(params)).json<SpaceType>()
}

function useDeleteSpace(code: MaybeRef<string>) {
  const url = computed(() => `/spaces/${unref(code)}`)
  return useApi(url).delete().json<SpaceType>()
}

export default {
  useListSpaces,
  useGetSpace,
  useAddSpace,
  useUpdateSpace,
  useDeleteSpace
}
