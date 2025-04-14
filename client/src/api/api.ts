import { createFetch } from "@vueuse/core"

export const useApi = createFetch({
  baseUrl: import.meta.env.VITE_API_BASE_URL,
  options: {
    beforeFetch(ctx) {
      const url = new URL(ctx.url)
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
    }
  }
})
