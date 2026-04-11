<script setup lang="ts">
import { ref } from 'vue'
import { MessageCircle, Phone, Mail, Clock, MapPin, Send, ChevronRight, FileText, Headphones, Users, Award } from 'lucide-vue-next'

const contactInfo = [
  { icon: Phone, label: '联系电话', value: '+86 (21) 6888 8888', sublabel: '周一至周五 9:00-18:00' },
  { icon: Mail, label: '电子邮箱', value: 'global@shengjun.com', sublabel: '24小时内回复' },
  { icon: MapPin, label: '公司地址', value: '中国 · 上海', sublabel: '浦东新区张江高科技园区' },
  { icon: Clock, label: '服务时间', value: '周一至周六', sublabel: '9:00 - 18:00' }
]

const consultationTypes = [
  {
    icon: FileText,
    title: '产品咨询',
    desc: '了解产品规格、型号、价格及库存情况',
    link: '立即咨询'
  },
  {
    icon: Headphones,
    title: '技术支持',
    desc: '获取技术文档、安装指导及售后支持',
    link: '获取支持'
  },
  {
    icon: Users,
    title: '商务合作',
    desc: '探讨OEM/ODM合作、代理商加盟等商务模式',
    link: '商务洽谈'
  },
  {
    icon: Award,
    title: '质量认证',
    desc: '索取ISO/IATF认证文件及产品质量报告',
    link: '下载资料'
  }
]

const formData = ref({
  name: '',
  company: '',
  email: '',
  phone: '',
  type: 'product',
  message: ''
})

const isSubmitting = ref(false)
const submitSuccess = ref(false)

const handleSubmit = async () => {
  isSubmitting.value = true
  await new Promise(resolve => setTimeout(resolve, 1500))
  isSubmitting.value = false
  submitSuccess.value = true
  Object.assign(formData, {
    name: '',
    company: '',
    email: '',
    phone: '',
    type: 'product',
    message: ''
  })
}
</script>

<template>
  <div>
    <section class="relative h-screen flex items-center overflow-hidden">
      <div class="absolute inset-0 z-0 parallax-bg">
        <img
          src="https://images.unsplash.com/photo-1557804506-669a67965ba0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=2000"
          alt="Consultation"
          class="w-full h-full object-cover"
        />
        <div class="absolute inset-0 bg-gradient-to-r from-black/70 via-black/50 to-transparent" />
      </div>

      <div class="relative z-10 max-w-7xl mx-auto px-6 lg:px-8 w-full parallax-content">
        <div class="max-w-2xl">
          <div class="text-sm tracking-widest text-gray-300 mb-4">
            CONSULTATION CENTER
          </div>
          <h1 class="text-6xl md:text-7xl lg:text-8xl font-bold text-white mb-6 leading-tight">
            咨询中心
          </h1>
          <p class="text-xl text-gray-200">
            专业团队为您提供全方位的技术支持与咨询服务
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
            <div class="text-4xl md:text-5xl font-bold mb-2">24h</div>
            <div class="text-gray-400 mb-1">快速响应</div>
            <div class="text-xs text-gray-600">全天候服务支持</div>
          </div>
          <div>
            <div class="text-4xl md:text-5xl font-bold mb-2">800+</div>
            <div class="text-gray-400 mb-1">专业工程师</div>
            <div class="text-xs text-gray-600">技术专家团队</div>
          </div>
          <div>
            <div class="text-4xl md:text-5xl font-bold mb-2">50+</div>
            <div class="text-gray-400 mb-1">服务国家</div>
            <div class="text-xs text-gray-600">全球化布局</div>
          </div>
          <div>
            <div class="text-4xl md:text-5xl font-bold mb-2">3000+</div>
            <div class="text-gray-400 mb-1">合作客户</div>
            <div class="text-xs text-gray-600">长期合作伙伴</div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-20 bg-white">
      <div class="max-w-7xl mx-auto px-6 lg:px-8">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <div
            v-for="(info, idx) in contactInfo"
            :key="idx"
            class="bg-gray-50 p-8 border border-gray-200 hover:border-black transition-colors"
          >
            <component :is="info.icon" :size="32" class="mb-4 text-black" />
            <h3 class="text-lg font-semibold mb-1">{{ info.label }}</h3>
            <p class="text-xl font-bold mb-1">{{ info.value }}</p>
            <p class="text-sm text-gray-500">{{ info.sublabel }}</p>
          </div>
        </div>
      </div>
    </section>

    <section class="py-20 bg-gray-50">
      <div class="max-w-7xl mx-auto px-6 lg:px-8">
        <div class="text-center mb-16">
          <div class="inline-block px-4 py-2 bg-black text-white text-sm tracking-widest mb-6">
            CONSULTATION SERVICES
          </div>
          <h2 class="text-4xl md:text-5xl font-bold mb-4">咨询服务</h2>
          <p class="text-xl text-gray-600 max-w-2xl mx-auto">
            选择适合您的咨询方式，我们的专业团队将在24小时内与您联系
          </p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <div
            v-for="(type, idx) in consultationTypes"
            :key="idx"
            class="bg-white p-8 border border-gray-200 hover:border-black hover:shadow-lg transition-all group cursor-pointer"
          >
            <div class="w-16 h-16 bg-black text-white flex items-center justify-center mb-6 group-hover:bg-gray-800 transition-colors">
              <component :is="type.icon" :size="28" />
            </div>
            <h3 class="text-2xl font-bold mb-2">{{ type.title }}</h3>
            <p class="text-gray-600 mb-4">{{ type.desc }}</p>
            <div class="flex items-center gap-2 text-black font-medium group-hover:gap-4 transition-all">
              {{ type.link }}
              <ChevronRight :size="20" />
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-20 bg-white">
      <div class="max-w-7xl mx-auto px-6 lg:px-8">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-16">
          <div>
            <div class="inline-block px-4 py-2 bg-black text-white text-sm tracking-widest mb-6">
              ONLINE CONSULTATION
            </div>
            <h2 class="text-4xl md:text-5xl font-bold mb-6">在线留言</h2>
            <p class="text-xl text-gray-600 mb-8">
              填写以下表单，我们的专业顾问将在24小时内与您联系
            </p>

            <div v-if="submitSuccess" class="bg-green-50 border border-green-200 p-6 mb-8">
              <div class="flex items-center gap-3 text-green-700">
                <div class="w-8 h-8 bg-green-100 rounded-full flex items-center justify-center">
                  <Send :size="16" />
                </div>
                <div>
                  <h4 class="font-semibold">提交成功！</h4>
                  <p class="text-sm">我们将尽快与您取得联系</p>
                </div>
              </div>
            </div>

            <form @submit.prevent="handleSubmit" class="space-y-6">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium mb-2">您的姓名 *</label>
                  <input
                    v-model="formData.name"
                    type="text"
                    required
                    class="w-full px-4 py-3 border border-gray-300 focus:border-black focus:outline-none transition-colors"
                    placeholder="请输入姓名"
                  />
                </div>
                <div>
                  <label class="block text-sm font-medium mb-2">公司名称</label>
                  <input
                    v-model="formData.company"
                    type="text"
                    class="w-full px-4 py-3 border border-gray-300 focus:border-black focus:outline-none transition-colors"
                    placeholder="请输入公司名称"
                  />
                </div>
              </div>

              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium mb-2">电子邮箱 *</label>
                  <input
                    v-model="formData.email"
                    type="email"
                    required
                    class="w-full px-4 py-3 border border-gray-300 focus:border-black focus:outline-none transition-colors"
                    placeholder="请输入邮箱"
                  />
                </div>
                <div>
                  <label class="block text-sm font-medium mb-2">联系电话</label>
                  <input
                    v-model="formData.phone"
                    type="tel"
                    class="w-full px-4 py-3 border border-gray-300 focus:border-black focus:outline-none transition-colors"
                    placeholder="请输入电话"
                  />
                </div>
              </div>

              <div>
                <label class="block text-sm font-medium mb-2">咨询类型 *</label>
                <select
                  v-model="formData.type"
                  required
                  class="w-full px-4 py-3 border border-gray-300 focus:border-black focus:outline-none transition-colors bg-white"
                >
                  <option value="product">产品咨询</option>
                  <option value="tech">技术支持</option>
                  <option value="business">商务合作</option>
                  <option value="quality">质量认证</option>
                  <option value="other">其他</option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-medium mb-2">留言内容 *</label>
                <textarea
                  v-model="formData.message"
                  required
                  rows="5"
                  class="w-full px-4 py-3 border border-gray-300 focus:border-black focus:outline-none transition-colors resize-none"
                  placeholder="请详细描述您的需求..."
                ></textarea>
              </div>

              <button
                type="submit"
                :disabled="isSubmitting"
                class="w-full px-8 py-4 bg-black text-white hover:bg-gray-800 transition-colors flex items-center justify-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <span v-if="isSubmitting">提交中...</span>
                <span v-else>提交咨询</span>
                <Send v-if="!isSubmitting" :size="20" />
              </button>
            </form>
          </div>

          <div class="relative">
            <img
              src="https://images.unsplash.com/photo-1504384308090-c894fdcc538d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=1200"
              alt="Team"
              class="w-full h-full object-cover"
            />
            <div class="absolute inset-0 bg-gradient-to-t from-black/60 via-transparent to-transparent flex items-end p-8">
              <div class="text-white">
                <h3 class="text-2xl font-bold mb-2">专业团队  全球响应</h3>
                <p class="text-gray-200">800+工程师24小时在线支持</p>
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
}
</style>
