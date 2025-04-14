import "./route-meta.ts"

export type PaginationParams = {
  size?: number,
  page?: number,
  sort?: any[]
}

export type PagedResponse<T = any> = {
  content: T[],
  page: {
    size: number,
    number: number,
    totalElements: number,
    totalPages: number
  }
}

export type VocabularyType = {
  id: number,
  name: string,
  definition: any,
  createdAt: string,
  updatedAt: string,
}

export type StructType = {
  id: number,
  name: string,
  definition: any,
  createdAt: string,
  updatedAt: string,
}

export type FormType = {
  id: number,
  name: string,
  description: string,
  struct: StructType,
  jsonSchema: any,
  uiSchema: any,
  createdAt: string,
  updatedAt: string,
}

export type RecordType = {
  id: number,
  form: FormType,
  content: any
}
