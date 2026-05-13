import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect:'/login'},
    { path: '/manager', component: () =>import('../views/Manager.vue'),
      children:[
        {path: 'home', meta: {name:'主页'}, component: () =>import('../views/Home.vue'),},
        {path: 'admin', meta: {name:'管理员信息'}, component: () =>import('../views/Admin.vue')},
        {path: 'person', meta: {name:'个人信息'}, component: () =>import('../views/Person.vue'),},
        {path: 'IndustryNews', meta: {name:'行业新闻'}, component: () =>import('../views/IndustryNews.vue'),},
        {path: 'Enterprisedisplay', meta: {name:'企业展示'}, component: () =>import('../views/Enterprisedisplay.vue'),},
        {path: 'SteelProductPrice', meta: {name:'钢材产品价格'}, component: () =>import('../views/SteelProductPrice.vue'),},
        {path: 'SampleDisplay', meta: {name:'样品展示'}, component: () =>import('../views/SampleDisplay.vue'),},
        {path: 'AboutUsManage', meta: {name:'关于我们管理'}, component: () =>import('../views/AboutUsManage.vue'),},
        {path: 'updatePassword', meta: {name:'修改密码'}, component: () =>import('../views/UpdatePassword.vue'),},
      ]
    },
    { path: '/login', component: () =>import('../views/Login.vue')},
    { path: '/notfound', component: () =>import('../views/404.vue'),},
    { path: '/:pathMatch(.*)', redirect: '/notFound' }
  ],
})

export default router
