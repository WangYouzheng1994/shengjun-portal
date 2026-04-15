<template>
  <div class="milestone-container">
    <!-- 顶部工具栏 -->
    <el-row :gutter="20" class="toolbar">
      <el-col :span="8">
        <!-- 企业选择器：超级管理员可切换企业，企业管理员固定显示本企业 -->
        <CompanySelector
          v-model="currentCompanyId"
          @change="handleCompanyChange"
        />
      </el-col>
      <el-col :span="5">
        <el-date-picker
          v-model="filterYear"
          type="year"
          placeholder="筛选年份"
          clearable
          style="width: 100%"
        />
      </el-col>
      <el-col :span="5">
        <el-select
          v-model="filterLevel"
          placeholder="节点级别"
          clearable
          style="width: 100%"
        >
          <el-option label="全部" value="" />
          <el-option label="重大里程碑" value="1" />
          <el-option label="重要事件" value="2" />
          <el-option label="一般事件" value="3" />
        </el-select>
      </el-col>
      <el-col :span="6" style="text-align: right">
        <el-button
          type="primary"
          icon="Plus"
          @click="handleAdd"
        >
          新增里程碑
        </el-button>
      </el-col>
    </el-row>

    <!-- 当前企业信息提示 -->
    <el-alert
      v-if="currentCompanyName && !loading"
      :title="'当前编辑：' + currentCompanyName + ' 的发展历程'"
      type="info"
      :closable="false"
      show-icon
      style="margin-bottom: 16px"
    />

    <!-- 主体区域：左右分栏 -->
    <el-row :gutter="20" class="main-content">
      <!-- 左侧：时间轴视图 -->
      <el-col :span="14">
        <TimelineView
          :data="timelineData"
          :active-id="currentMilestoneId"
          :loading="loading"
          @select="handleSelectMilestone"
          @edit="handleEditMilestone"
          @delete="handleDelete"
          @add="handleAdd"
        />
      </el-col>

      <!-- 右侧：编辑面板 -->
      <el-col :span="10">
        <MilestoneForm
          ref="formRef"
          :milestone="currentMilestone"
          :company-id="currentCompanyId"
          @save="handleSave"
          @cancel="handleCancel"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="CompanyMilestone">
import { ref, onMounted, watch } from 'vue'
import { listMilestone, delMilestone, addMilestone, updateMilestone } from "@/api/portal/companyMilestone"
import { listCompany } from "@/api/portal/company"
import CompanySelector from './components/CompanySelector.vue'
import TimelineView from './components/TimelineView.vue'
import MilestoneForm from './components/MilestoneForm.vue'

const { proxy } = getCurrentInstance()

// ==================== 状态管理 ====================

/** 当前选中的企业ID */
const currentCompanyId = ref(null)

/** 当前企业的名称（用于展示） */
const currentCompanyName = ref('')

/** 时间轴数据列表 */
const timelineData = ref([])

/** 当前选中的里程碑ID */
const currentMilestoneId = ref(null)

/** 当前编辑的里程碑对象 */
const currentMilestone = ref({})

/** 加载状态 */
const loading = ref(false)

/** 筛选条件 - 年份 */
const filterYear = ref(null)

/** 筛选条件 - 节点级别 */
const filterLevel = ref('')

// ==================== 企业相关逻辑 ====================

/**
 * 初始化企业列表
 * 超级管理员加载所有企业并允许切换，
 * 企业管理员只加载本企业并禁用切换
 */
async function initCompanyList() {
  try {
    const res = await listCompany({ status: '0' })
    if (res.code === 200 && res.rows && res.rows.length > 0) {
      // 默认选中第一个企业（或根据用户角色智能选择）
      if (!currentCompanyId.value) {
        currentCompanyId.value = res.rows[0].infoId
        currentCompanyName.value = res.rows[0].companyName
      }
    }
  } catch (error) {
    console.error('获取企业列表失败:', error)
    proxy.$modal.msgError('获取企业列表失败')
  }
}

/**
 * 切换企业时的处理逻辑
 *
 * @param companyId 新的企业ID
 */
function handleCompanyChange(companyId) {
  if (companyId !== currentCompanyId.value) {
    // 更新企业名称
    const company = getCompanyById(companyId)
    if (company) {
      currentCompanyName.value = company.companyName
    }

    // 清空当前选中的里程碑
    currentMilestoneId.value = null
    currentMilestone.value = {}

    // 重新加载该企业的历程数据
    loadMilestoneList()
  }
}

/**
 * 根据ID获取企业信息（从本地缓存）
 *
 * @param companyId 企业ID
 * @returns 企业对象或undefined
 */
async function getCompanyById(companyId) {
  try {
    const res = await listCompany({ status: '0' })
    if (res.code === 200 && res.rows) {
      return res.rows.find(item => item.infoId === companyId)
    }
  } catch (error) {
    console.error('获取企业信息失败:', error)
  }

  return undefined
}

// ==================== 里程碑CRUD操作 ====================

/**
 * 加载里程碑列表
 * 根据当前选中的企业ID和筛选条件查询数据
 */
async function loadMilestoneList() {
  if (!currentCompanyId.value) {
    proxy.$modal.msgWarning('请先选择企业')
    return
  }

  loading.value = true

  try {
    const queryParams = {
      companyId: currentCompanyId.value,
      pageNum: 1,
      pageSize: 100,
      milestoneYear: filterYear.value ? filterYear.value.getFullYear() : null,
      milestoneLevel: filterLevel.value || null,
      status: '0'
    }

    const res = await listMilestone(queryParams)

    if (res.code === 200) {
      timelineData.value = res.rows || []
    } else {
      proxy.$modal.msgError(res.msg || '加载数据失败')
      timelineData.value = []
    }
  } catch (error) {
    console.error('加载里程碑列表失败:', error)
    proxy.$modal.msgError('加载数据失败')
    timelineData.value = []
  } finally {
    loading.value = false
  }
}

/**
 * 新增里程碑操作
 * 预填充当前企业ID和默认值
 */
function handleAdd() {
  currentMilestoneId.value = null
  currentMilestone.value = {
    companyId: currentCompanyId.value,
    milestoneTitle: '',
    milestoneYear: new Date().getFullYear(),
    milestoneMonth: null,
    milestoneDay: null,
    milestoneLevel: '3',
    description: '',
    thumbnailImage: '',
    hdImage: '',
    linkType: '0',
    linkUrl: '',
    linkContent: '',
    sortOrder: 0,
    status: '0',
    remark: ''
  }
}

/**
 * 选择某个里程碑进行编辑
 *
 * @param item 里程碑对象
 */
function handleSelectMilestone(item) {
  currentMilestoneId.value = item.milestoneId
  // 深拷贝避免直接修改原数据
  currentMilestone.value = JSON.parse(JSON.stringify(item))
}

/**
 * 编辑某个里程碑（与选择相同）
 *
 * @param item 里程碑对象
 */
function handleEditMilestone(item) {
  handleSelectMilestone(item)
}

/**
 * 保存里程碑（新增或修改）
 *
 * @param formData 表单数据
 */
async function handleSave(formData) {
  try {
    let res

    if (formData.milestoneId) {
      // 修改模式
      res = await updateMilestone(formData)
    } else {
      // 新增模式
      formData.companyId = currentCompanyId.value
      res = await addMilestone(formData)
    }

    if (res.code === 200) {
      proxy.$modal.msgSuccess(formData.milestoneId ? '修改成功' : '新增成功')

      // 重新加载列表
      await loadMilestoneList()

      // 清空表单
      handleCancel()
    } else {
      proxy.$modal.msgError(res.msg || '保存失败')
    }
  } catch (error) {
    console.error('保存失败:', error)
    proxy.$modal.msgError('保存失败')
  }
}

/**
 * 取消编辑操作
 */
function handleCancel() {
  currentMilestoneId.value = null
  currentMilestone.value = {}
}

/**
 * 删除里程碑操作
 *
 * @param row 里程碑对象
 */
function handleDelete(row) {
  const milestoneIds = row.milestoneId

  proxy.$modal
    .confirm('是否确认删除里程碑"' + row.milestoneTitle + '"？')
    .then(async () => {
      const res = await delMilestone(milestoneIds)

      if (res.code === 200) {
        proxy.$modal.msgSuccess('删除成功')

        // 如果删除的是当前正在编辑的项，清空表单
        if (currentMilestoneId.value === milestoneIds) {
          handleCancel()
        }

        // 重新加载列表
        await loadMilestoneList()
      } else {
        proxy.$modal.msgError(res.msg || '删除失败')
      }
    })
    .catch(() => {})
}

// ==================== 生命周期钩子 ====================

onMounted(() => {
  // 先初始化企业列表
  initCompanyList().then(() => {
    // 再加载里程碑数据
    loadMilestoneList()
  })
})

// 监听筛选条件变化，自动刷新数据
watch([filterYear, filterLevel], () => {
  loadMilestoneList()
})
</script>

<style scoped>
.milestone-container {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
}

.toolbar {
  margin-bottom: 16px;
  padding: 16px;
  background: #f5f7fa;
  border-radius: 8px;
  display: flex;
  align-items: center;
}

.main-content {
  min-height: calc(100vh - 300px);
}
</style>
