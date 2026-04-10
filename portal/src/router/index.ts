import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/HomeView.vue'),
    meta: {
      title: '首页'
    }
  },
  {
    path: '/consultation',
    name: 'Consultation',
    component: () => import('../views/ConsultationView.vue'),
    meta: {
      title: '咨询中心'
    }
  },
  {
    path: '/products',
    name: 'Products',
    component: () => import('../views/ProductCenterView.vue'),
    meta: {
      title: '产品中心'
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
    return { top: 0 }
  }
})

router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title || '盛骏'} - 盛骏汽车零部件`
  next()
})

export default router
