import { createFetch } from "@vueuse/core"
import { useSpaceStore } from "@/store"

let apiBaseUrl = import.meta.env.VITE_API_BASE_URL
if (window.location.hostname !== "localhost") {
  apiBaseUrl = window.location.origin + "/api"
}

export const useApi = createFetch({
  baseUrl: apiBaseUrl,
  options: {
    beforeFetch(ctx) {
      const url = new URL(ctx.url)

      const {space}=useSpaceStore()
      if(space){
        url.searchParams.set("space", space.code)
      }

      if (url.searchParams.has("page")
        && url.searchParams.has("size")) {
        const page = parseInt(url.searchParams.get("page") as string)
        url.searchParams.set("page", (page - 1).toString())
      }
      return { url: url.toString() }
    },
    afterFetch({ data }) {
      if (data && data.page && data.content) {
        data.page.number = data.page.number + 1
      }

      return { data }
    },
    onFetchError(ctx) {
      if (ctx.response && ctx.response.status === 401) {
        window.location.href = "/login"
      }

      return ctx
    }
  }
})
