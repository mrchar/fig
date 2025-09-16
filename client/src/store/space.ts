import { defineStore } from "pinia"
import { StorageSerializers } from "@vueuse/core"
import type { SpaceType } from "@/types"

export const useSpaceStore = defineStore("space", () => {
  const space = useStorage<SpaceType>("space", null, localStorage, { serializer: StorageSerializers.object })

  function setSpace(value: any) {
    space.value = value
  }

  return {
    space,
    setSpace
  }
})
