// 定义请求配置接口
interface RequestOptions {
  url: string;
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE';
  data?: any;
  header?: Record<string, string>;
  timeout?: number;
}

// 定义响应数据接口
interface ResponseData<T = any> {
  code: number;
  data: T;
  message: string;
}

import config from '@/config';

class HttpRequest {
  private baseURL: string = config.baseURL;
  private timeout: number = config.timeout;
  private headers: Record<string, string> = {};

  constructor(baseURL?: string, timeout?: number) {
    // 如果传入了自定义配置则使用传入的配置，否则使用默认配置
    this.baseURL = baseURL !== undefined ? baseURL : config.baseURL;
    this.timeout = timeout !== undefined ? timeout : config.timeout;
  }

  /**
   * 设置全局请求头
   * @param headers 请求头对象
   */
  setHeaders(headers: Record<string, string>) {
    this.headers = { ...this.headers, ...headers };
  }

  /**
   * 请求拦截器
   * @param config 请求配置
   */
  private requestInterceptor(config: RequestOptions): RequestOptions {
    console.log('请求拦截器:', config);
    
    // 添加通用请求头
    config.header = {
      'Content-Type': 'application/json',
      ...this.headers,
      ...config.header
    };

    // 可以在这里添加身份验证逻辑
    // const token = uni.getStorageSync('token');
    // if (token) {
    //   config.header.Authorization = `Bearer ${token}`;
    // }

    // 添加时间戳等其他处理
    console.log('发送请求:', `${this.baseURL}${config.url}`, config);
    return config;
  }

  /**
   * 响应拦截器
   * @param response 响应数据
   */
  private responseInterceptor<T>(response: UniApp.RequestSuccessCallbackResult): ResponseData<T> {
    console.log('响应拦截器:', response);
    
    // 可以在这里进行统一的状态码处理
    switch (response.statusCode) {
      case 200:
      case 201:
        // 成功状态直接返回数据
        return response.data as ResponseData<T>;
      case 401:
        // 未授权处理
        uni.showToast({
          title: '登录已过期，请重新登录',
          icon: 'none'
        });
        // 可以跳转到登录页
        // uni.redirectTo({ url: '/pages/login/login' });
        throw new Error('未授权访问');
      case 403:
        uni.showToast({
          title: '拒绝访问',
          icon: 'none'
        });
        throw new Error('拒绝访问');
      case 404:
        uni.showToast({
          title: '请求资源不存在',
          icon: 'none'
        });
        throw new Error('请求资源不存在');
      case 500:
        uni.showToast({
          title: '服务器内部错误',
          icon: 'none'
        });
        throw new Error('服务器内部错误');
      default:
        uni.showToast({
          title: '请求失败',
          icon: 'none'
        });
        throw new Error(`请求失败，状态码：${response.statusCode}`);
    }
  }

  /**
   * 发起请求
   * @param options 请求配置
   */
  request<T>(options: RequestOptions): Promise<ResponseData<T>> {
    // 请求拦截
    const config = this.requestInterceptor(options);

    return new Promise((resolve, reject) => {
      uni.request({
        url: `${this.baseURL}${config.url}`,
        method: config.method || 'GET',
        data: config.data,
        header: config.header,
        timeout: config.timeout || this.timeout,
        success: (res) => {
          try {
            // 响应拦截
            const result = this.responseInterceptor<T>(res);
            resolve(result);
          } catch (error) {
            reject(error);
          }
        },
        fail: (err) => {
          console.error('网络请求失败:', err);
          uni.showToast({
            title: '网络请求失败',
            icon: 'none'
          });
          reject(new Error('网络请求失败'));
        }
      });
    });
  }

  /**
   * GET 请求
   * @param url 请求地址
   * @param params 请求参数
   */
  get<T>(url: string, params?: any): Promise<ResponseData<T>> {
    return this.request<T>({
      url,
      method: 'GET',
      data: params
    });
  }

  /**
   * POST 请求
   * @param url 请求地址
   * @param data 请求数据
   */
  post<T>(url: string, data?: any): Promise<ResponseData<T>> {
    return this.request<T>({
      url,
      method: 'POST',
      data
    });
  }

  /**
   * PUT 请求
   * @param url 请求地址
   * @param data 请求数据
   */
  put<T>(url: string, data?: any): Promise<ResponseData<T>> {
    return this.request<T>({
      url,
      method: 'PUT',
      data
    });
  }

  /**
   * DELETE 请求
   * @param url 请求地址
   */
  delete<T>(url: string): Promise<ResponseData<T>> {
    return this.request<T>({
      url,
      method: 'DELETE'
    });
  }


}

export default HttpRequest;
export type { RequestOptions, ResponseData };