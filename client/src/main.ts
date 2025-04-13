import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

import router from "./router"
import App from "./App.vue"

import "./style.css"

const vuetify = createVuetify({
  components,
  directives
})

createApp(App).use(vuetify).use(router).mount("#app")
