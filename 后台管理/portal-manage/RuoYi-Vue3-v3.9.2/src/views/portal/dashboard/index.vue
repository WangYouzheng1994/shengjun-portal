<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="12" :md="6">
        <el-card shadow="hover" class="statistic-card">
          <template #header>
            <div class="card-header">
              <el-icon><View /></el-icon>
              <span>今日点击量</span>
            </div>
          </template>
          <div class="statistic-content">
            <span class="statistic-number">{{ dashboardData.todayVisits }}</span>
            <div class="statistic-footer" v-if="dashboardData.yesterdayVisits > 0">
              <span
                :class="getTrendClass(dashboardData.todayVisits, dashboardData.yesterdayVisits)"
              >
                {{ getTrendText(dashboardData.todayVisits, dashboardData.yesterdayVisits) }}
              </span>
              <span class="statistic-compare">较昨日</span>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="12" :md="6">
        <el-card shadow="hover" class="statistic-card">
          <template #header>
            <div class="card-header">
              <el-icon><DataAnalysis /></el-icon>
              <span>昨日点击量</span>
            </div>
          </template>
          <div class="statistic-content">
            <span class="statistic-number">{{ dashboardData.yesterdayVisits }}</span>
            <div class="statistic-footer">
              <span class="text-muted">昨日数据</span>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="12" :md="6">
        <el-card shadow="hover" class="statistic-card">
          <template #header>
            <div class="card-header">
              <el-icon><Document /></el-icon>
              <span>本月文章点击量</span>
            </div>
          </template>
          <div class="statistic-content">
            <span class="statistic-number">{{ formatNumber(dashboardData.monthArticleViews) }}</span>
            <div class="statistic-footer">
              <span class="text-muted">累计浏览</span>
              <el-tag size="small" type="info" class="ml-2">共{{ dashboardData.totalArticles }}篇</el-tag>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="12" :md="6">
        <el-card shadow="hover" class="statistic-card">
          <template #header>
            <div class="card-header">
              <el-icon><Goods /></el-icon>
              <span>本月产品查阅量</span>
            </div>
          </template>
          <div class="statistic-content">
            <span class="statistic-number">{{ formatNumber(dashboardData.monthProductViews) }}</span>
            <div class="statistic-footer">
              <span class="text-muted">累计查阅</span>
              <el-tag size="small" type="success" class="ml-2">共{{ dashboardData.totalProducts }}个</el-tag>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :xs="24" :lg="16">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon><TrendCharts /></el-icon>
              <span>近七天访问趋势</span>
            </div>
          </template>
          <div ref="trendChartRef" class="trend-chart-container"></div>
        </el-card>
      </el-col>

      <el-col :xs="24" :lg="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon><Bell /></el-icon>
              <span>快捷入口</span>
            </div>
          </template>
          <div class="quick-entry-container">
            <div
              class="quick-entry-item"
              @click="goToMessageList"
            >
              <div class="entry-icon message-icon">
                <el-icon :size="32"><ChatDotRound /></el-icon>
              </div>
              <div class="entry-info">
                <span class="entry-title">待处理留言</span>
                <span class="entry-count">{{ dashboardData.pendingMessageCount }}</span>
              </div>
              <el-icon class="entry-arrow"><ArrowRight /></el-icon>
            </div>

            <div
              class="quick-entry-item"
              @click="goToArticleList"
            >
              <div class="entry-icon article-icon">
                <el-icon :size="32"><Document /></el-icon>
              </div>
              <div class="entry-info">
                <span class="entry-title">文章管理</span>
                <span class="entry-count">{{ dashboardData.totalArticles }}篇</span>
              </div>
              <el-icon class="entry-arrow"><ArrowRight /></el-icon>
            </div>

            <div
              class="quick-entry-item"
              @click="goToProductList"
            >
              <div class="entry-icon product-icon">
                <el-icon :size="32"><Goods /></el-icon>
              </div>
              <div class="entry-info">
                <span class="entry-title">产品管理</span>
                <span class="entry-count">{{ dashboardData.totalProducts }}个</span>
              </div>
              <el-icon class="entry-arrow"><ArrowRight /></el-icon>
            </div>

            <div
              class="quick-entry-item"
              @click="goToCustomerList"
            >
              <div class="entry-icon customer-icon">
                <el-icon :size="32"><User /></el-icon>
              </div>
              <div class="entry-info">
                <span class="entry-title">客户管理</span>
                <span class="entry-count">{{ dashboardData.totalCustomers }}人</span>
              </div>
              <el-icon class="entry-arrow"><ArrowRight /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="PortalDashboard">
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import * as echarts from 'echarts'
import { getDashboardOverview } from '@/api/portal/dashboard'

const router = useRouter()

const trendChartRef = ref(null)
let chartInstance = null

const dashboardData = ref({
  todayVisits: 0,
  yesterdayVisits: 0,
  monthArticleViews: 0,
  monthProductViews: 0,
  pendingMessageCount: 0,
  totalArticles: 0,
  totalProducts: 0,
  totalCustomers: 0,
  trendData: []
})

function loadDashboardData() {
  getDashboardOverview().then(res => {
    if (res.code === 200 && res.data) {
      dashboardData.value = res.data
      nextTick(() => {
        initTrendChart()
      })
    }
  })
}

function initTrendChart() {
  if (!trendChartRef.value) {
    return
  }

  if (chartInstance) {
    chartInstance.dispose()
  }

  chartInstance = echarts.init(trendChartRef.value)

  const dates = dashboardData.value.trendData.map(item => item.dateLabel)
  const totalVisits = dashboardData.value.trendData.map(item => item.totalVisits)
  const articleViews = dashboardData.value.trendData.map(item => item.articleViews)

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross',
        crossStyle: {
          color: '#999'
        }
      }
    },
    legend: {
      data: ['总访问量', '文章浏览量'],
      bottom: 0
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '15%',
      top: '10%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: dates,
        axisPointer: {
          type: 'shadow'
        }
      }
    ],
    yAxis: [
      {
        type: 'value',
        name: '访问量',
        minInterval: 1,
        axisLabel: {
          formatter: '{value}'
        }
      }
    ],
    series: [
      {
        name: '总访问量',
        type: 'bar',
        data: totalVisits,
        itemColor: {
          color: '#409EFF'
        }
      },
      {
        name: '文章浏览量',
        type: 'line',
        yAxisIndex: 0,
        data: articleViews,
        smooth: true,
        lineStyle: {
          width: 3,
          color: '#67C23A'
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(103, 194, 58, 0.3)' },
            { offset: 1, color: 'rgba(103, 194, 58, 0.05)' }
          ])
        },
        itemStyle: {
          color: '#67C23A'
        }
      }
    ]
  }

  chartInstance.setOption(option)
}

function handleResize() {
  if (chartInstance) {
    chartInstance.resize()
  }
}

function formatNumber(num) {
  if (!num) {
    return '0'
  }
  return num.toLocaleString()
}

function getTrendClass(today, yesterday) {
  if (today > yesterday) {
    return 'text-success'
  } else if (today < yesterday) {
    return 'text-danger'
  } else {
    return 'text-info'
  }
}

function getTrendText(today, yesterday) {
  if (yesterday === 0) {
    return '-'
  }
  const rate = ((today - yesterday) / yesterday * 100).toFixed(1)
  const sign = today > yesterday ? '+' : ''
  return `${sign}${rate}%`
}

function goToMessageList() {
  router.push('/crm/message/list')
}

function goToArticleList() {
  router.push('/portal/article/article')
}

function goToProductList() {
  router.push('/portal/product/list')
}

function goToCustomerList() {
  router.push('/crm/customer/list')
}

onMounted(() => {
  loadDashboardData()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  if (chartInstance) {
    chartInstance.dispose()
    chartInstance = null
  }
})
</script>

<style scoped lang="scss">
.dashboard-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 84px);
}

.statistic-card {
  margin-bottom: 10px;

  .card-header {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 14px;
    font-weight: 500;
    color: #606266;
  }

  .statistic-content {
    text-align: center;
    padding: 10px 0;
  }

  .statistic-number {
    display: block;
    font-size: 28px;
    font-weight: bold;
    color: #303133;
    margin-bottom: 8px;
  }

  .statistic-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
    font-size: 13px;
  }
}

.text-success {
  color: #67c23a;
}

.text-danger {
  color: #f56c6c;
}

.text-info {
  color: #909399;
}

.text-muted {
  color: #909399;
}

.ml-2 {
  margin-left: 8px;
}

.trend-chart-container {
  width: 100%;
  height: 350px;
}

.quick-entry-container {
  padding: 10px 0;
}

.quick-entry-item {
  display: flex;
  align-items: center;
  padding: 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 12px;
  background-color: #f8f9fa;

  &:last-child {
    margin-bottom: 0;
  }

  &:hover {
    background-color: #ecf5ff;
    transform: translateX(4px);
  }

  .entry-icon {
    width: 48px;
    height: 48px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 14px;
    flex-shrink: 0;
  }

  .message-icon {
    background-color: #fef0f0;
    color: #f56c6c;
  }

  .article-icon {
    background-color: #fdf6ec;
    color: #e6a23c;
  }

  .product-icon {
    background-color: #f0f9eb;
    color: #67c23a;
  }

  .customer-icon {
    background-color: #ecf5ff;
    color: #409eff;
  }

  .entry-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 4px;
  }

  .entry-title {
    font-size: 15px;
    font-weight: 500;
    color: #303133;
  }

  .entry-count {
    font-size: 13px;
    color: #909399;
  }

  .entry-arrow {
    color: #c0c4cc;
    transition: color 0.3s ease;
  }

  &:hover .entry-arrow {
    color: #409eff;
  }
}
</style>
