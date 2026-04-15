<template>
  <div class="timeline-container">
    <!-- 加载状态 -->
    <div v-if="loading" class="loading-wrapper">
      <el-skeleton :rows="5" animated />
    </div>

    <!-- 时间轴内容 -->
    <div v-else>
      <!-- 有数据时显示时间轴 -->
      <el-timeline v-if="groupedData && Object.keys(groupedData).length > 0">
        <el-timeline-item
          v-for="(items, year) in groupedData"
          :key="year"
          :timestamp="year + '年'"
          placement="top"
          :type="getYearType(year)"
          size="large"
          :hollow="false"
        >
          <!-- 年份标题 -->
          <div class="year-header">
            <span class="year-text">{{ year }}年</span>
            <el-tag size="small" type="info">{{ items.length }}个事件</el-tag>
          </div>

          <!-- 该年份下的所有里程碑节点 -->
          <div class="milestones-group">
            <MilestoneCard
              v-for="item in items"
              :key="item.milestoneId"
              :data="item"
              :is-active="item.milestoneId === activeId"
              @select="$emit('select', item)"
              @edit="$emit('edit', item)"
              @delete="$emit('delete', item)"
            />
          </div>
        </el-timeline-item>
      </el-timeline>

      <!-- 空状态 -->
      <el-empty
        v-else
        description="暂无里程碑数据，请点击「新增里程碑」按钮添加"
        :image-size="120"
      >
        <el-button type="primary" @click="$emit('add')">
          新增里程碑
        </el-button>
      </el-empty>
    </div>
  </div>
</template>

<script setup name="TimelineView">
import { computed } from 'vue'
import MilestoneCard from './MilestoneCard.vue'

const props = defineProps({
  data: { type: Array, default: () => [] },
  activeId: { type: [Number, String], default: null },
  loading: { type: Boolean, default: false }
})

const emit = defineEmits(['select', 'edit', 'delete', 'add'])

/**
 * 按年份分组并排序数据
 *
 * 返回格式：{ 2024: [...], 2023: [...] }
 * 按年份倒序（最近的在前）
 */
const groupedData = computed(() => {
  if (!props.data || props.data.length === 0) {
    return {}
  }

  const groups = {}

  // 按年份分组
  props.data.forEach(item => {
    const year = item.milestoneYear
    if (!groups[year]) {
      groups[year] = []
    }
    groups[year].push(item)
  })

  // 对每个年份内的节点按月日排序
  Object.keys(groups).forEach(year => {
    groups[year].sort((a, b) => {
      // 先按月份排序
      if (a.milestoneMonth !== b.milestoneMonth) {
        return (a.milestoneMonth || 0) - (b.milestoneMonth || 0)
      }
      // 再按日期排序
      if (a.milestoneDay !== b.milestoneDay) {
        return (a.milestoneDay || 0) - (b.milestoneDay || 0)
      }
      // 最后按sort_order排序
      return (a.sortOrder || 0) - (b.sortOrder || 0)
    })
  })

  // 按年份倒序返回（最近的在前）
  const sortedGroups = {}
  Object.keys(groups)
    .map(year => parseInt(year))
    .sort((a, b) => b - a)
    .forEach(year => {
      sortedGroups[year] = groups[year]
    })

  return sortedGroups
})

/**
 * 根据该年是否有重大里程碑决定时间轴节点样式
 *
 * @param year 年份
 * @returns Element Plus timeline类型
 */
function getYearType(year) {
  const items = groupedData.value[year] || []
  const hasMajor = items.some(item => item.milestoneLevel === '1')
  if (hasMajor) {
    return 'primary'
  }
  const hasImportant = items.some(item => item.milestoneLevel === '2')
  if (hasImportant) {
    return 'warning'
  }
  return 'info'
}
</script>

<style scoped>
.timeline-container {
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
  max-height: calc(100vh - 280px);
  overflow-y: auto;
}

.loading-wrapper {
  padding: 40px 20px;
}

/* 年份头部样式 */
.year-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 2px solid #e4e7ed;
}

.year-text {
  font-size: 18px;
  font-weight: 700;
  color: #303133;
}

/* 里程碑组容器 */
.milestones-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

/* 自定义时间轴样式覆盖 */
:deep(.el-timeline-item__wrapper) {
  padding-left: 20px;
}

:deep(.el-timeline-item__timestamp) {
  font-size: 16px;
  font-weight: 600;
}
</style>
