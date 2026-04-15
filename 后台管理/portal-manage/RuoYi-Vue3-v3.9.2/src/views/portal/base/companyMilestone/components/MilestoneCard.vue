<template>
  <div
    class="milestone-card"
    :class="{ 'is-active': isActive, [`level-${data.milestoneLevel}`]: true }"
    @click="$emit('select', data)"
  >
    <!-- 级别标识 -->
    <div class="card-level" :class="`level-badge-${data.milestoneLevel}`">
      <span v-if="data.milestoneLevel === '1'">🔴</span>
      <span v-else-if="data.milestoneLevel === '2'">🟡</span>
      <span v-else>⚪</span>
    </div>

    <!-- 卡片内容 -->
    <div class="card-content">
      <!-- 标题 -->
      <div class="card-title">{{ data.milestoneTitle }}</div>

      <!-- 时间 -->
      <div class="card-time">
        {{ formatTime(data) }}
      </div>

      <!-- 缩略图 -->
      <div class="card-image" v-if="data.thumbnailImage">
        <el-image
          :src="data.thumbnailImage"
          :preview-src-list="[data.hdImage || data.thumbnailImage]"
          fit="cover"
          style="width: 100%; height: 120px; border-radius: 4px;"
        />
      </div>

      <!-- 描述摘要 -->
      <div class="card-desc" v-if="data.description && !isExpanded">
        {{ stripHtml(data.description).substring(0, 50) }}...
      </div>

      <!-- 链接类型标识 -->
      <div class="card-link-type" v-if="data.linkType && data.linkType !== '0'">
        <el-tag size="small" :type="getLinkTypeTagType(data.linkType)">
          {{ getLinkTypeName(data.linkType) }}
        </el-tag>
      </div>

      <!-- 操作按钮 -->
      <div class="card-actions">
        <el-button link type="primary" size="small" @click.stop="$emit('edit', data)">
          编辑
        </el-button>
        <el-button link type="danger" size="small" @click.stop="handleDelete">
          删除
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup name="MilestoneCard">
const props = defineProps({
  data: { type: Object, required: true },
  isActive: { type: Boolean, default: false }
})

const emit = defineEmits(['select', 'edit', 'delete'])

/** 是否展开描述 */
const isExpanded = ref(false)

/**
 * 格式化时间显示
 *
 * @param item 里程碑数据
 * @returns 格式化后的时间字符串
 */
function formatTime(item) {
  let timeStr = item.milestoneYear + '年'
  if (item.milestoneMonth) {
    timeStr += item.milestoneMonth + '月'
  }
  if (item.milestoneDay) {
    timeStr += item.milestoneDay + '日'
  }
  return timeStr
}

/**
 * 去除HTML标签，提取纯文本
 *
 * @param html HTML字符串
 * @returns 纯文本
 */
function stripHtml(html) {
  if (!html) return ''
  return html.replace(/<[^>]+>/g, '').replace(/&nbsp;/g, ' ')
}

/**
 * 获取链接类型的标签样式
 *
 * @param type 链接类型
 * @returns Element Plus tag类型
 */
function getLinkTypeTagType(type) {
  const map = {
    '1': 'primary',
    '2': 'success',
    '3': 'warning'
  }
  return map[type] || 'info'
}

/**
 * 获取链接类型名称
 *
 * @param type 链接类型
 * @returns 类型名称
 */
function getLinkTypeName(type) {
  const map = {
    '1': '详情页',
    '2': '内部路由',
    '3': '外部链接'
  }
  return map[type] || '未知'
}

/**
 * 处理删除操作
 */
function handleDelete() {
  emit('delete', props.data)
}
</script>

<style scoped>
.milestone-card {
  display: flex;
  gap: 12px;
  padding: 16px;
  margin-bottom: 12px;
  background: #fff;
  border: 2px solid #e4e7ed;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.milestone-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.milestone-card.is-active {
  border-color: #409eff;
  background: #ecf5ff;
}

/* 不同级别的卡片样式 */
.milestone-card.level-1 {
  border-left: 4px solid #f56c6c;
}

.milestone-card.level-2 {
  border-left: 4px solid #e6a23c;
}

.milestone-card.level-3 {
  border-left: 4px solid #909399;
}

/* 级别标识 */
.card-level {
  flex-shrink: 0;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}

/* 卡片内容 */
.card-content {
  flex: 1;
  min-width: 0;
}

.card-title {
  font-size: 15px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.card-time {
  font-size: 13px;
  color: #909399;
  margin-bottom: 10px;
}

.card-image {
  margin-bottom: 10px;
}

.card-desc {
  font-size: 13px;
  color: #606266;
  line-height: 1.6;
  margin-bottom: 10px;
}

.card-link-type {
  margin-bottom: 10px;
}

.card-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}
</style>
