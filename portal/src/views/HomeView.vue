<script setup lang="ts">
import { ChevronRight, Globe, Award, Users, ArrowRight } from 'lucide-vue-next'

const stats = [
  { value: '99.8%', label: '产品合格率', sublabel: '超越行业标准' },
  { value: '50+', label: '服务国家', sublabel: '全球化布局' },
  { value: '3000+', label: '全球客户', sublabel: '长期合作伙伴' },
  { value: 'ISO/IATF', label: '双认证', sublabel: '国际质量体系' }
]

const brands = ['BMW', 'AUDI', 'MERCEDES', 'TOYOTA', 'VOLKSWAGEN']

const products = [
  {
    tag: 'OEM QUALITY',
    title: '发动机系统',
    desc: '符合欧VI排放标准，OEM级精密工艺',
    specs: '±0.005mm公差 | 100%德国设备加工',
    image: 'https://images.unsplash.com/photo-1758563920450-e0aaf7dec734?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=800'
  },
  {
    tag: 'TESTED & CERTIFIED',
    title: '底盘系统',
    desc: '通过50万次疲劳测试，超越OEM标准30%',
    specs: 'IATF认证 | 全球主流车型适配',
    image: 'https://images.unsplash.com/photo-1769218401073-71a5b1020c9b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=800'
  },
  {
    tag: 'PRECISION ENGINEERED',
    title: '传动系统',
    desc: '采用日本进口材料，99.9%精度保障',
    specs: '静音技术 | 延长30%使用寿命',
    image: 'https://images.unsplash.com/photo-1760317890359-4e6bb111e501?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=800'
  }
]

const features = [
  { icon: Award, title: '国际双认证', desc: 'ISO/TS 16949 + IATF 16949 双体系认证' },
  { icon: Globe, title: '全球战略布局', desc: '服务超过50个国家，3000+合作伙伴' },
  { icon: Users, title: '顶尖研发团队', desc: '800+工程师，年研发投入超营收8%' }
]

const globalStats = [
  { number: '25+', label: '年行业经验' },
  { number: '50+', label: '服务国家' },
  { number: '3000+', label: '全球客户' },
  { number: '5000+', label: 'SKU产品' }
]
</script>

<template>
  <div>
    <section class="relative h-screen flex items-center overflow-hidden">
      <div class="absolute inset-0 z-0 parallax-bg">
        <img
          src="https://images.unsplash.com/photo-1567789884554-0b844b597180?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=2000"
          alt="Manufacturing"
          class="w-full h-full object-cover"
        />
        <div class="absolute inset-0 bg-gradient-to-r from-black/70 via-black/50 to-transparent" />
      </div>

      <div class="relative z-10 max-w-7xl mx-auto px-6 lg:px-8 w-full parallax-content">
        <div class="max-w-2xl">
          <div class="text-sm tracking-widest text-gray-300 mb-4">
            MADE IN CHINA · TRUSTED GLOBALLY
          </div>
          <h1 class="text-6xl md:text-7xl lg:text-8xl font-bold text-white mb-6 leading-tight">
            中国制造
            <br />
            <span class="text-gray-300">全球品质</span>
          </h1>
          <p class="text-xl text-gray-200 mb-8 max-w-xl">
            顶级汽车零部件供应商，为全球 50+ 国家提供高精度解决方案
          </p>
          <div class="flex flex-col sm:flex-row gap-4">
            <button class="px-8 py-4 bg-white text-black hover:bg-gray-100 transition-colors flex items-center justify-center gap-2 group">
              获取产品目录
              <ArrowRight :size="20" class="group-hover:translate-x-1 transition-transform" />
            </button>
            <button class="px-8 py-4 border-2 border-white text-white hover:bg-white hover:text-black transition-all">
              成为合作伙伴
            </button>
          </div>
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
          <div v-for="(stat, idx) in stats" :key="idx">
            <div class="text-4xl md:text-5xl font-bold mb-2">{{ stat.value }}</div>
            <div class="text-gray-400 mb-1">{{ stat.label }}</div>
            <div class="text-xs text-gray-600">{{ stat.sublabel }}</div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-16 bg-gray-50 border-b border-gray-200">
      <div class="max-w-7xl mx-auto px-6 lg:px-8">
        <div class="text-center">
          <p class="text-xs tracking-widest text-gray-500 mb-2">全球顶级汽车品牌的共同选择</p>
          <p class="text-sm tracking-widest text-gray-400 mb-10">TRUSTED BY LEADING AUTOMOTIVE BRANDS WORLDWIDE</p>
          <div class="grid grid-cols-2 md:grid-cols-5 gap-8 items-center justify-items-center opacity-30">
            <div v-for="brand in brands" :key="brand" class="text-2xl font-bold tracking-wider">
              {{ brand }}
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="products" class="py-32 bg-white">
      <div class="max-w-7xl mx-auto px-6 lg:px-8">
        <div class="mb-16 max-w-4xl">
          <div class="inline-block px-4 py-1.5 bg-gray-100 text-sm tracking-widest text-gray-600 mb-6">
            PRODUCT EXCELLENCE
          </div>
          <h2 class="text-5xl md:text-6xl font-bold mb-6">核心产品系列</h2>
          <p class="text-xl text-gray-600">
            100%符合国际OEM标准，5000+SKU覆盖全球主流车型，为世界顶级汽车品牌提供同等品质
          </p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <div v-for="(product, idx) in products" :key="idx" class="group cursor-pointer">
            <div class="relative overflow-hidden mb-6 aspect-[4/3]">
              <img
                :src="product.image"
                :alt="product.title"
                class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
              />
              <div class="absolute inset-0 bg-black/0 group-hover:bg-black/20 transition-colors" />
              <div class="absolute top-4 left-4 px-3 py-1 bg-white text-black text-xs tracking-wider">
                {{ product.tag }}
              </div>
            </div>
            <h3 class="text-2xl font-semibold mb-2 group-hover:text-gray-600 transition-colors">
              {{ product.title }}
            </h3>
            <p class="text-gray-600 mb-3">{{ product.desc }}</p>
            <p class="text-sm text-gray-500 mb-4 border-l-2 border-black pl-3">{{ product.specs }}</p>
            <div class="flex items-center gap-2 text-black group-hover:gap-4 transition-all">
              了解更多
              <ChevronRight :size="20" />
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="manufacturing" class="py-32 bg-gray-50">
      <div class="max-w-7xl mx-auto px-6 lg:px-8">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">
          <div>
            <div class="inline-block px-4 py-2 bg-black text-white text-sm tracking-widest mb-6">
              WORLD-CLASS MANUFACTURING
            </div>
            <h2 class="text-5xl md:text-6xl font-bold mb-6">中国智造 · 全球标准</h2>
            <p class="text-xl text-gray-600 mb-12">
              德国工艺标准，中国制造优势，打造极致性价比的高端零部件
            </p>

            <div class="space-y-8">
              <div v-for="(item, idx) in features" :key="idx" class="flex gap-4">
                <div class="flex-shrink-0 w-14 h-14 bg-black text-white flex items-center justify-center">
                  <component :is="item.icon" :size="24" />
                </div>
                <div>
                  <h3 class="text-xl font-semibold mb-1">{{ item.title }}</h3>
                  <p class="text-gray-600">{{ item.desc }}</p>
                </div>
              </div>
            </div>
          </div>

          <div class="relative">
            <div class="relative mb-8">
              <img
                src="https://images.unsplash.com/photo-1764114235896-034c8772de01?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1200"
                alt="Manufacturing"
                class="w-full h-[600px] object-cover"
              />
              <div class="absolute -bottom-8 -left-8 bg-white p-8 shadow-lg max-w-xs">
                <div class="text-5xl font-bold mb-2">25+</div>
                <div class="text-gray-600">年行业经验</div>
              </div>
            </div>

            <div class="grid grid-cols-3 gap-4 pt-8">
              <div class="text-center p-4 border border-gray-200">
                <Award :size="32" class="mx-auto mb-2 text-gray-400" />
                <div class="text-xs text-gray-600">ISO 9001</div>
              </div>
              <div class="text-center p-4 border border-gray-200">
                <Award :size="32" class="mx-auto mb-2 text-gray-400" />
                <div class="text-xs text-gray-600">IATF 16949</div>
              </div>
              <div class="text-center p-4 border border-gray-200">
                <Award :size="32" class="mx-auto mb-2 text-gray-400" />
                <div class="text-xs text-gray-600">CE认证</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="global" class="py-32 bg-black text-white">
      <div class="max-w-7xl mx-auto px-6 lg:px-8">
        <div class="text-center mb-20">
          <div class="inline-block px-4 py-2 bg-white text-black text-sm tracking-widest mb-6">
            FROM CHINA TO THE WORLD
          </div>
          <h2 class="text-5xl md:text-6xl font-bold mb-6">立足中国 · 服务全球</h2>
          <p class="text-xl text-gray-400 max-w-3xl mx-auto">
            亚洲、欧洲、北美15个生产基地，24小时响应全球需求
          </p>
        </div>

        <div class="grid grid-cols-2 md:grid-cols-4 gap-8 mb-20">
          <div v-for="(stat, idx) in globalStats" :key="idx" class="text-center">
            <div class="text-5xl md:text-6xl font-bold mb-2">{{ stat.number }}</div>
            <div class="text-gray-400">{{ stat.label }}</div>
          </div>
        </div>

        <div class="relative h-96 overflow-hidden">
          <img
            src="https://images.unsplash.com/photo-1758873263428-f4b2edb45fe1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1600"
            alt="Global"
            class="w-full h-full object-cover opacity-30"
          />
          <div class="absolute inset-0 flex items-center justify-center">
            <div class="text-center max-w-3xl">
              <h3 class="text-3xl md:text-5xl font-bold mb-4">中国智造 · 世界品质</h3>
              <p class="text-xl text-gray-300 mb-8">与全球顶级汽车品牌共同选择的零部件合作伙伴</p>
              <div class="flex gap-4 justify-center">
                <button class="px-8 py-4 bg-white text-black hover:bg-gray-100 transition-colors">
                  获取报价
                </button>
                <button class="px-8 py-4 border-2 border-white text-white hover:bg-white hover:text-black transition-all">
                  下载产品目录
                </button>
              </div>
            </div>
          </div>
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
  
  /* 使用 CSS 实现视差效果 */
  .parallax-section {
    perspective: 1px;
    overflow: hidden;
  }
  
  .parallax-bg {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    transform-style: preserve-3d;
  }
}
</style>
