/**
 * 通用响应数据结构
 */
export interface ResponseData<T = any> {
  code: number;
  data: T;
  message: string;
}

/**
 * 分页数据结构
 */
export interface PageData<T> {
  list: T[];
  total: number;
  page: number;
  size: number;
}

/**
 * 用户信息
 */
export interface UserInfo {
  id: number;
  username: string;
  nickname: string;
  avatar: string;
  email: string;
  phone: string;
}

/**
 * 登录参数
 */
export interface LoginParams {
  username: string;
  password: string;
}