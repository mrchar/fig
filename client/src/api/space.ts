import { useApi } from "@/api/api.ts"

function useListSpaces() {
  return useApi("/spaces").get().json()
}

export default {
  useListSpaces
}
