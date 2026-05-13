<template>
  <div>
    <!-- 头部区域开始 -->
    <div style="height: 60px; display: flex;">
      <div :style="{width: data.isCollapse ? '64px' : '260px', display: 'flex', alignItems: 'center', paddingLeft: data.isCollapse ? '0' : '20px', justifyContent: data.isCollapse ? 'center' : 'flex-start', backgroundColor: '#3a456b'}">
        <img style="width: 40px; height: 40px; border-radius: 50%" src="@/assets/imgs/logo.png" alt="">
        <span v-show="!data.isCollapse" style="font-size: 20px; font-weight: bold; color: #f1f1f1; margin-left: 5px; white-space: nowrap;">钢材小程序后台管理</span>
      </div>
      <div style="flex: 1; display: flex; align-items: center; padding-left: 20px; border-bottom: 1px solid #ddd">
          <p style="font-size: larger"></p>
      </div>
      <div style="width: fit-content; padding-right: 20px; display: flex; align-items: center; border-bottom: 1px solid #ddd">
        <el-dropdown>
          <div style="display: flex; align-items: center">
            <img v-if="data.user?.avatar" 
                 style="width: 40px; height: 40px; border-radius: 50%" 
                 :src="data.user?.avatar" 
                 @error="$event.target.src='https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
            <img v-else style="width: 40px; height: 40px; border-radius: 50%" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" alt="" />
            <span style="margin-left: 10px">{{ data.user?.name }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/manager/person')">个人信息</el-dropdown-item>
              <el-dropdown-item @click="router.push('/manager/updatePassword')">修改密码</el-dropdown-item>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <!--  头部区域结束 -->
    <!--  下方区域开始 -->
    <div style="display: flex">
      <!--  菜单区域开始 -->
      <div :style="{width: data.isCollapse ? '64px' : '260px'}">
        <el-menu
          router
          :collapse="data.isCollapse"
          :default-openeds="['1']"
          :default-active="router.currentRoute.value.path"
          style="min-height: calc(100vh - 60px)"
          :collapse-transition="false"
        >
          <el-menu-item index="/manager/home">
            <el-icon><House /></el-icon>
            <template #title>首页</template>
          </el-menu-item>
          <el-sub-menu index="1" v-if="data.user.role === 'ADMIN'">
            <template #title>
              <el-icon><User /></el-icon>
              <span v-show="!data.isCollapse">用户管理</span>
            </template>
            <el-menu-item index="/manager/admin">管理员信息</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><location /></el-icon>
              <span v-show="!data.isCollapse">功能模块</span>
            </template>
            <el-menu-item index="/manager/IndustryNews" v-if="data.user.role === 'ADMIN'">行业资讯管理</el-menu-item>
            <el-menu-item index="/manager/Enterprisedisplay" v-if="data.user.role === 'ADMIN'">企业展示管理</el-menu-item>
            <el-menu-item index="/manager/SteelProductPrice" v-if="data.user.role === 'ADMIN'">钢材产品价格管理</el-menu-item>
            <el-menu-item index="/manager/SampleDisplay" v-if="data.user.role === 'ADMIN'">样品展示管理</el-menu-item>
            <el-menu-item index="/manager/AboutUsManage" v-if="data.user.role === 'ADMIN'">关于我们管理</el-menu-item>
          </el-sub-menu>

          <!-- 菜单收缩按钮 -->
          <el-menu-item @click="toggleCollapse" style="position: absolute; bottom: 0; width: 100%;">
            <el-icon><Expand v-if="data.isCollapse" /><Fold v-else /></el-icon>
            <template #title>
              <span v-show="!data.isCollapse">收起菜单</span>
            </template>
          </el-menu-item>
        </el-menu>
      </div>
      <!--  菜单区域结束 -->

      <!--  数据渲染区域开始 -->
      <div style="flex: 1; width: 0; padding: 10px; background-color: #f2f4ff">
        <RouterView @updateUser="updateUser"/>
      </div>
      <!--  数据渲染区域结束 -->

    </div>
    <!--  下方区域结束 -->

  </div>
</template>

<script setup>
import { House, User, Location, Expand, Fold } from '@element-plus/icons-vue'

import router from "@/router/index.js";

import { reactive } from "vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}"),
  isCollapse: false // 控制菜单收缩状态
})

// 切换菜单收缩状态
const toggleCollapse = () => {
  data.isCollapse = !data.isCollapse
}

const logout = () => {
  localStorage.removeItem('code_user')
  location.href = '/login'
}

/*if (!data.user?.id) {
  location.href = '/login'
}*/

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem("code_user") || '{}')
}

</script>

<style>
.el-menu {
  background-color: #3a456b;
  border: none;
}
.el-sub-menu__title {
  background-color: #3a456b;
  color: #ddd;
}
.el-menu-item {
  height: 50px;
  color: #ddd;
}
.el-menu .is-active {
  background-color: #537bee;
  color: #fff;
}
.el-sub-menu__title:hover {
  background-color: #3a456b;
}
.el-menu-item:not(.is-active):hover {
  background-color: #7a9fff;
  color: #333;
}
.el-dropdown {
  cursor: pointer;
}
.el-tooltip__trigger {
  outline: none;
}
.el-menu--inline .el-menu-item {
  padding-left: 48px !important;
}

/* 收缩时的特殊样式 */
.el-menu--collapse .el-sub-menu__title span,
.el-menu--collapse .el-menu-item span {
  display: none;
}

.el-menu--collapse .el-sub-menu__title .el-icon,
.el-menu--collapse .el-menu-item .el-icon {
  margin-right: 0;
}
</style>
