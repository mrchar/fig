export type PaginationParams = {
  size?: number,
  page?: number,
  sort?: any[]
}

export type PagedResponse<T = any> = {
  content: T[],
  size: number,
  number: number,
  totalElements: number,
}

export type Vocabulary = {
  id: number,
  name: string,
  definition: any,
  createdAt: string,
  updatedAt: string,
}
