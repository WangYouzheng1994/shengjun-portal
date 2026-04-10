<script setup lang="ts">
import { ref, watch } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { Menu, X } from 'lucide-vue-next'

const route = useRoute()
const menuOpen = ref(false)

const navItems = [
  { path: '/', label: '首页' },
  { path: '/consultation', label: '咨询中心' },
  { path: '/products', label: '产品中心' }
]

const isActive = (path: string) => {
  if (path === '/') {
    return route.path === '/'
  }
  return route.path.startsWith(path)
}

watch(() => route.path, () => {
  menuOpen.value = false
})
</script>

<template>
  <nav class="fixed top-0 left-0 right-0 z-50 bg-white/95 backdrop-blur-sm border-b border-gray-200">
    <div class="max-w-7xl mx-auto px-6 lg:px-8">
      <div class="flex items-center justify-between h-20">
        <RouterLink to="/" class="flex items-baseline gap-3">
          <span class="text-2xl font-bold tracking-tight">盛骏</span>
          <span class="text-xs text-gray-400 tracking-widest">SHENGJUN</span>
        </RouterLink>

        <div class="hidden md:flex items-center gap-8">
          <RouterLink
            v-for="item in navItems"
            :key="item.path"
            :to="item.path"
            :class="[
              'transition-colors',
              isActive(item.path)
                ? 'text-black font-medium'
                : 'text-gray-600 hover:text-black'
            ]"
          >
            {{ item.label }}
          </RouterLink>
          <button class="px-6 py-2.5 bg-black text-white hover:bg-gray-800 transition-colors">
            合作咨询
          </button>
        </div>

        <button
          @click="menuOpen = !menuOpen"
          class="md:hidden p-2"
        >
          <X v-if="menuOpen" :size="24" />
          <Menu v-else :size="24" />
        </button>
      </div>
    </div>

    <div
      v-if="menuOpen"
      class="md:hidden bg-white border-t border-gray-200"
    >
      <div class="px-6 py-4 space-y-4">
        <RouterLink
          v-for="item in navItems"
          :key="item.path"
          :to="item.path"
          :class="[
            'block transition-colors',
            isActive(item.path)
              ? 'text-black font-medium'
              : 'text-gray-600'
          ]"
        >
          {{ item.label }}
        </RouterLink>
        <button class="w-full px-6 py-2.5 bg-black text-white">
          合作咨询
        </button>
      </div>
    </div>
  </nav>
</template>
