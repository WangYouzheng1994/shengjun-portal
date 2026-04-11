<script setup lang="ts">
import { ref, computed } from 'vue'
import { ChevronRight, Filter, Grid3X3, List, Search, Phone, Mail, ArrowRight } from 'lucide-vue-next'

const categories = [
  { id: 'all', name: '全部产品' },
  { id: 'engine', name: '发动机系统' },
  { id: 'chassis', name: '底盘系统' },
  { id: 'transmission', name: '传动系统' },
  { id: 'electrical', name: '电气系统' }
]

const products = [
  {
    id: 1,
    category: 'engine',
    tag: 'OEM QUALITY',
    title: '发动机皮带张紧器',
    desc: '采用高强度合金钢，符合欧VI排放标准，OEM级精密工艺',
    specs: '±0.005mm公差 | 100%德国设备加工',
    image: 'https://images.unsplash.com/photo-1758563920450-e0aaf7dec734?w=800',
    price: '¥128.00'
  },
  {
    id: 2,
    category: 'engine',
    tag: 'PREMIUM',
    title: '发动机水泵总成',
    desc: '高效冷却系统设计，延长发动机使用寿命',
    specs: 'IATF认证 | 适用于大众/奥迪系列',
    image: 'https://images.unsplash.com/photo-1758563920450-e0aaf7dec734?w=800',
    price: '¥458.00'
  },
  {
    id: 3,
    category: 'chassis',
    tag: 'TESTED & CERTIFIED',
    title: '前减震器总成',
    desc: '通过50万次疲劳测试，超越OEM标准30%',
    specs: 'IATF认证 | 全球主流车型适配',
    image: 'https://images.unsplash.com/photo-1769218401073-71a5b1020c9b?w=800',
    price: '¥680.00'
  },
  {
    id: 4,
    category: 'chassis',
    tag: 'HIGH PERFORMANCE',
    title: '刹车盘片套装',
    desc: '采用陶瓷复合材料，制动性能卓越',
    specs: 'ECE认证 | 静音设计',
    image: 'https://images.unsplash.com/photo-1769218401073-71a5b1020c9b?w=800',
    price: '¥890.00'
  },
  {
    id: 5,
    category: 'transmission',
    tag: 'PRECISION ENGINEERED',
    title: '变速箱油泵',
    desc: '采用日本进口材料，99.9%精度保障',
    specs: '静音技术 | 延长30%使用寿命',
    image: 'https://images.unsplash.com/photo-1760317890359-4e6bb111e501?w=800',
    price: '¥1,280.00'
  },
  {
    id: 6,
    category: 'transmission',
    tag: 'OEM QUALITY',
    title: '传动轴万向节',
    desc: '高强度合金钢材质，等速万向节设计',
    specs: '德国工艺 | 50万km使用寿命',
    image: 'https://images.unsplash.com/photo-1760317890359-4e6bb111e501?w=800',
    price: '¥328.00'
  },
  {
    id: 7,
    category: 'electrical',
    tag: 'SMART TECH',
    title: '车身控制模块',
    desc: '智能集成控制，多种车型兼容',
    specs: '原厂品质 | 即插即用',
    image: 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800',
    price: '¥2,180.00'
  },
  {
    id: 8,
    category: 'electrical',
    tag: 'ENERGY EFFICIENT',
    title: 'LED大灯总成',
    desc: '原厂标准光型，夜间行车更安全',
    specs: '原厂品质 | 低功耗长寿命',
    image: 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800',
    price: '¥1,680.00'
  }
]

const activeCategory = ref('all')
const viewMode = ref<'grid' | 'list'>('grid')
const searchQuery = ref('')

const filteredProducts = computed(() => {
  let result = products

  if (activeCategory.value !== 'all') {
    result = result.filter(p => p.category === activeCategory.value)
  }

  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    result = result.filter(p =>
      p.title.toLowerCase().includes(query) ||
      p.desc.toLowerCase().includes(query)
    )
  }

  return result
})
</script>

<template>
  <div>
    <section class="relative h-screen flex items-center overflow-hidden">
      <div class="absolute inset-0 z-0 parallax-bg">
        <img
          src="https://images.unsplash.com/photo-1567789884554-0b844b597180?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=2000"
          alt="Products"
          class="w-full h-full object-cover"
        />
        <div class="absolute inset-0 bg-gradient-to-r from-black/70 via-black/50 to-transparent" />
      </div>

      <div class="relative z-10 max-w-7xl mx-auto px-6 lg:px-8 w-full parallax-content">
        <div class="max-w-2xl">
          <div class="text-sm tracking-widest text-gray-300 mb-4">
            PRODUCT CENTER
          </div>
          <h1 class="text-6xl md:text-7xl lg:text-8xl font-bold text-white mb-6 leading-tight">
            产品中心
          </h1>
          <p class="text-xl text-gray-200">
            5000+SKU 覆盖全球主流车型，100% 符合国际 OEM 标准
          </p>
        </div>
      </div>

      <div class="absolute bottom-8 left-1/2 -translate-x-1/2 z-10 scroll-indicator">
        <div class="w-6 h-10 border-2 border-white/50 rounded-full flex items-start justify-center p-2">
          <div class="w-1.5 h-1.5 bg-white rounded-full animate-bounce" />
        </div>
      </div>
    </section>

    <section class="py-20 bg-black text-white">
      <div class="max-w-7xl mx-auto px-6 lg:px-8">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
          <div>
            <div class="text-4xl md:text-5xl font-bold mb-2">5000+</div>
            <div class="text-gray-400 mb-1">SKU 产品</div>
            <div class="text-xs text-gray-600">丰富产品线</div>
          </div>
          <div>
            <div class="text-4xl md:text-5xl font-bold mb-2">50+</div>
            <div class="text-gray-400 mb-1">服务国家</div>
            <div class="text-xs text-gray-600">全球化布局</div>
          </div>
          <div>
            <div class="text-4xl md:text-5xl font-bold mb-2">99.8%</div>
            <div class="text-gray-400 mb-1">产品合格率</div>
            <div class="text-xs text-gray-600">超越行业标准</div>
          </div>
          <div>
            <div class="text-4xl md:text-5xl font-bold mb-2">OEM</div>
            <div class="text-gray-400 mb-1">同级品质</div>
            <div class="text-xs text-gray-600">国际质量标准</div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-20 bg-white">
      <div class="max-w-7xl mx-auto px-6 lg:px-8">
        <div class="flex flex-col lg:flex-row gap-12">
          <aside class="lg:w-64 flex-shrink-0">
            <div class="sticky top-24">
              <div class="mb-8 p-6 bg-gray-50 border border-gray-200">
                <h3 class="text-lg font-semibold mb-4">需要帮助？</h3>
                <p class="text-sm text-gray-600 mb-4">我们的专业团队随时为您提供产品咨询</p>
                <div class="space-y-3">
                  <div class="flex items-center gap-2 text-sm">
                    <Phone :size="16" />
                    <span>+86 (21) 6888 8888</span>
                  </div>
                  <div class="flex items-center gap-2 text-sm">
                    <Mail :size="16" />
                    <span>global@shengjun.com</span>
                  </div>
                </div>
              </div>
            </div>
          </aside>

          <main class="flex-1">
            <div class="flex flex-col md:flex-row gap-6 mb-8 p-6 bg-gray-50">
              <div class="flex-1">
                <div class="flex items-center gap-2 mb-3">
                  <Search :size="20" />
                  <span class="font-medium">产品搜索</span>
                </div>
                <input
                  v-model="searchQuery"
                  type="text"
                  placeholder="搜索产品..."
                  class="w-full px-4 py-3 border border-gray-300 focus:border-black focus:outline-none transition-colors bg-white"
                />
              </div>
              <div class="flex-1">
                <div class="flex items-center gap-2 mb-3">
                  <Filter :size="20" />
                  <span class="font-medium">产品分类</span>
                </div>
                <div class="flex flex-wrap gap-2">
                  <button
                    v-for="cat in categories"
                    :key="cat.id"
                    @click="activeCategory = cat.id"
                    :class="[
                      'px-4 py-2 transition-colors',
                      activeCategory === cat.id
                        ? 'bg-black text-white'
                        : 'bg-white border border-gray-300 hover:border-black'
                    ]"
                  >
                    {{ cat.name }}
                  </button>
                </div>
              </div>
            </div>

            <div class="flex items-center justify-between mb-8">
              <p class="text-gray-600">
                共找到 <span class="font-semibold">{{ filteredProducts.length }}</span> 个产品
              </p>
              <div class="flex items-center gap-4">
                <button
                  @click="viewMode = 'grid'"
                  :class="[
                    'p-2 transition-colors',
                    viewMode === 'grid' ? 'bg-black text-white' : 'hover:bg-gray-100'
                  ]"
                >
                  <Grid3X3 :size="20" />
                </button>
                <button
                  @click="viewMode = 'list'"
                  :class="[
                    'p-2 transition-colors',
                    viewMode === 'list' ? 'bg-black text-white' : 'hover:bg-gray-100'
                  ]"
                >
                  <List :size="20" />
                </button>
              </div>
            </div>

            <div
              v-if="viewMode === 'grid'"
              class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8"
            >
              <div
                v-for="product in filteredProducts"
                :key="product.id"
                class="group bg-white border border-gray-200 hover:border-black transition-all cursor-pointer"
              >
                <div class="relative overflow-hidden aspect-[4/3]">
                  <img
                    :src="product.image"
                    :alt="product.title"
                    class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
                  />
                  <div class="absolute top-4 left-4 px-3 py-1 bg-white text-black text-xs tracking-wider">
                    {{ product.tag }}
                  </div>
                </div>
                <div class="p-6">
                  <h3 class="text-xl font-semibold mb-2 group-hover:text-gray-600 transition-colors">
                    {{ product.title }}
                  </h3>
                  <p class="text-gray-600 text-sm mb-3">{{ product.desc }}</p>
                  <p class="text-sm text-gray-500 mb-4 border-l-2 border-black pl-3">{{ product.specs }}</p>
                  <div class="flex items-center justify-between">
                    <span class="text-xl font-bold">{{ product.price }}</span>
                    <button class="px-4 py-2 bg-black text-white text-sm hover:bg-gray-800 transition-colors">
                      咨询
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <div
              v-else
              class="space-y-6"
            >
              <div
                v-for="product in filteredProducts"
                :key="product.id"
                class="group bg-white border border-gray-200 hover:border-black transition-all cursor-pointer flex"
              >
                <div class="w-64 h-48 flex-shrink-0 overflow-hidden">
                  <img
                    :src="product.image"
                    :alt="product.title"
                    class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
                  />
                </div>
                <div class="flex-1 p-6 flex flex-col justify-between">
                  <div>
                    <div class="flex items-start justify-between mb-2">
                      <div>
                        <span class="inline-block px-2 py-1 bg-gray-100 text-black text-xs tracking-wider mb-2">
                          {{ product.tag }}
                        </span>
                        <h3 class="text-xl font-semibold group-hover:text-gray-600 transition-colors">
                          {{ product.title }}
                        </h3>
                      </div>
                      <span class="text-xl font-bold">{{ product.price }}</span>
                    </div>
                    <p class="text-gray-600 mb-2">{{ product.desc }}</p>
                    <p class="text-sm text-gray-500 border-l-2 border-black pl-3">{{ product.specs }}</p>
                  </div>
                  <div class="flex items-center gap-4">
                    <button class="px-6 py-2 bg-black text-white hover:bg-gray-800 transition-colors">
                      查看详情
                    </button>
                    <button class="px-6 py-2 border border-gray-300 hover:border-black transition-colors">
                      咨询
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <div v-if="filteredProducts.length === 0" class="text-center py-20">
              <div class="text-gray-400 mb-4">未找到匹配的产品</div>
              <button
                @click="searchQuery = ''; activeCategory = 'all'"
                class="px-6 py-2 bg-black text-white hover:bg-gray-800 transition-colors"
              >
                清除筛选
              </button>
            </div>
          </main>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(6px);
  }
}

.animate-bounce {
  animation: bounce 1.5s ease-in-out infinite;
}

/* 视差滚动效果 - 使用 CSS 实现更流畅的体验 */
.parallax-bg {
  transform: translateZ(0);
  will-change: transform;
}

.parallax-content {
  transform: translateZ(0);
  will-change: transform, opacity;
}

.scroll-indicator {
  transition: opacity 0.3s ease;
}

@media (prefers-reduced-motion: no-preference) {
  html {
    scroll-behavior: smooth;
  }
}
</style>
