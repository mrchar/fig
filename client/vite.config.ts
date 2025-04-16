import { resolve } from "path"
import { defineConfig } from "vite"
import vue from "@vitejs/plugin-vue"
import tailwindcss from "@tailwindcss/vite"
import AutoImport from "unplugin-auto-import/vite"
import Components from "unplugin-vue-components/vite"
import vueDevTools from "vite-plugin-vue-devtools"

// https://vite.dev/config/
export default defineConfig({
  resolve: {
    alias: { "@": resolve(__dirname, "src") }
  },
  plugins: [
    vue(),
    tailwindcss(),
    AutoImport({
      imports: [
        "vue",
        "vue-router",
        "@vueuse/core",
        { "@vueuse/router": ["useRouteParams"] }
      ]
    }),
    Components({ dirs: ["src/components"] }),
    vueDevTools()
  ],
  server: {
    proxy: {
      "/api": "http://localhost:8080"
    }
  }
})
