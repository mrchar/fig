import { defineStore } from "pinia"
import { StorageSerializers } from "@vueuse/core"
import type { SpaceType } from "@/types"

export const useSpaceStore = defineStore("space", () => {
  const space = useStorage<SpaceType>("spaces", null, undefined, { serializer: StorageSerializers.object })

  function setSpace(value) {
    space.value = value
  }

  return {
    space,
    setSpace
  }
})
