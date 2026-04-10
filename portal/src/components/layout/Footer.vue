<script setup lang="ts">
const footerLinks = {
  products: ['发动机系统', '底盘系统', '传动系统', '电气系统'],
  about: ['公司简介', '质量认证', '全球布局', '加入我们']
}
</script>

<template>
  <footer class="bg-gray-900 text-gray-400 py-16">
    <div class="max-w-7xl mx-auto px-6 lg:px-8">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-12 mb-12">
        <div>
          <div class="text-white text-2xl font-semibold mb-2">盛骏</div>
          <div class="text-sm text-gray-500 mb-4">SHENGJUN</div>
          <p class="text-sm">中国顶级汽车零部件供应商</p>
          <p class="text-xs text-gray-500 mt-2">ISO/IATF双认证企业</p>
        </div>
        <div>
          <h4 class="text-white font-semibold mb-4">产品系列</h4>
          <ul class="space-y-2 text-sm">
            <li v-for="item in footerLinks.products" :key="item">
              <a href="#" class="hover:text-white transition-colors">{{ item }}</a>
            </li>
          </ul>
        </div>
        <div>
          <h4 class="text-white font-semibold mb-4">关于盛骏</h4>
          <ul class="space-y-2 text-sm">
            <li v-for="item in footerLinks.about" :key="item">
              <a href="#" class="hover:text-white transition-colors">{{ item }}</a>
            </li>
          </ul>
        </div>
        <div>
          <h4 class="text-white font-semibold mb-4">联系我们</h4>
          <ul class="space-y-2 text-sm">
            <li>总部: +86 (21) 6888 8888</li>
            <li>邮箱: global@shengjun.com</li>
            <li>中国 · 上海</li>
            <li class="pt-2">
              <a href="#" class="text-white hover:underline">获取产品目录 →</a>
            </li>
          </ul>
        </div>
      </div>
      <div class="border-t border-gray-800 pt-8 flex flex-col md:flex-row justify-between items-center text-sm gap-4">
        <p>&copy; 2026 盛骏汽车零部件 SHENGJUN. 保留所有权利.</p>
        <div class="flex gap-6">
          <a href="#" class="hover:text-white transition-colors">隐私政策</a>
          <a href="#" class="hover:text-white transition-colors">使用条款</a>
          <a href="#" class="hover:text-white transition-colors">质量保证</a>
        </div>
      </div>
    </div>
  </footer>
</template>
