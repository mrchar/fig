import type { Column } from "@/components/Table.vue"

export function useAuditColumns(): Column[] {
  return [
    {
      title: "创建时间", prop: "createdAt",
      formatter: ({ value }) => {
        return value && new Date(value).toLocaleString()
      }
    },
    {
      title: "更新时间", prop: "updatedAt",
      formatter: ({ value }) => {
        return value && new Date(value).toLocaleString()
      }
    }
  ]
}

export default useAuditColumns
