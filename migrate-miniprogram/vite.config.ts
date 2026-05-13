import { defineConfig } from "vite";
import uni from "@dcloudio/vite-plugin-uni";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [uni()],
  // 环境变量配置
  envPrefix: 'VITE_', // 指定以 VITE_ 开头的环境变量才会暴露给客户端
});
