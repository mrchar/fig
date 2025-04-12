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

export type Vocabulary = {
  id: number,
  name: string,
  definition: any,
  createdAt: string,
  updatedAt: string,
}

export type Struct = {
  id: number,
  name: string,
  definition: any,
  createdAt: string,
  updatedAt: string,
}
