<template>
  <el-card class="form-card" v-loading="saving">
    <template #header>
      <div class="card-header">
        <span>{{ isEdit ? '编辑里程碑' : '新增里程碑' }}</span>
        <el-tag v-if="companyId" size="small" type="info">
          企业ID: {{ companyId }}
        </el-tag>
      </div>
    </template>

    <el-form
      ref="formRef"
      :model="formData"
      :rules="rules"
      label-width="100px"
      label-position="top"
    >
      <!-- 企业ID（隐藏字段） -->
      <el-form-item v-show="false" prop="companyId">
        <el-input v-model="formData.companyId" />
      </el-form-item>

      <!-- 基本信息 -->
      <el-divider content-position="left">基本信息</el-divider>

      <el-form-item label="事件标题" prop="milestoneTitle">
        <el-input
          v-model="formData.milestoneTitle"
          placeholder="如：公司正式成立、获得A轮融资等"
          maxlength="100"
          show-word-limit
          clearable
        />
      </el-form-item>

      <el-form-item label="发生时间" required>
        <el-row :gutter="12">
          <el-col :span="10">
            <el-date-picker
              v-model="dateValue"
              type="year"
              placeholder="选择年份"
              style="width: 100%"
              @change="handleYearChange"
            />
          </el-col>
          <el-col :span="7">
            <el-select
              v-model="formData.milestoneMonth"
              placeholder="月"
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="m in 12"
                :key="m"
                :label="m + '月'"
                :value="m"
              />
            </el-select>
          </el-col>
          <el-col :span="7">
            <el-select
              v-model="formData.milestoneDay"
              placeholder="日"
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="d in maxDay"
                :key="d"
                :label="d + '日'"
                :value="d"
              />
            </el-select>
          </el-col>
        </el-row>
      </el-form-item>

      <el-form-item label="节点级别" prop="milestoneLevel">
        <el-radio-group v-model="formData.milestoneLevel">
          <el-radio value="1">🔴 重大里程碑</el-radio>
          <el-radio value="2">🟡 重要事件</el-radio>
          <el-radio value="3">⚪ 一般事件</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- 描述信息 -->
      <el-divider content-position="left">详细描述</el-divider>

      <el-form-item label="事件描述">
        <el-input
          v-model="formData.description"
          type="textarea"
          placeholder="请输入事件的详细描述（支持HTML富文本）"
          :rows="4"
          maxlength="2000"
          show-word-limit
        />
        <div class="form-tip">支持HTML标签，前端门户可渲染为富文本效果</div>
      </el-form-item>

      <!-- 图片上传 -->
      <el-divider content-position="left">图片资源</el-divider>

      <el-form-item label="缩略图">
        <image-upload v-model="formData.thumbnailImage" :limit="1" />
        <div class="form-tip">建议尺寸：400x300px，用于时间轴卡片展示</div>
      </el-form-item>

      <el-form-item label="高清大图">
        <image-upload v-model="formData.hdImage" :limit="1" />
        <div class="form-tip">建议尺寸：1920x1080px以内，用于悬停或点击后的高清展示</div>
      </el-form-item>

      <!-- 链接设置 -->
      <el-divider content-position="left">链接设置</el-divider>

      <el-form-item label="链接类型">
        <el-radio-group v-model="formData.linkType" @change="handleLinkTypeChange">
          <el-radio value="0">无链接</el-radio>
          <el-radio value="1">详情页（弹窗）</el-radio>
          <el-radio value="2">内部路由</el-radio>
          <el-radio value="3">外部链接</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item v-if="formData.linkType === '1'" label="详情内容">
        <el-input
          v-model="formData.linkContent"
          type="textarea"
          placeholder="请输入详情页展示的内容（支持HTML富文本）"
          :rows="5"
          maxlength="5000"
          show-word-limit
        />
        <div class="form-tip">用户点击节点时，在弹窗中展示此内容</div>
      </el-form-item>

      <el-form-item v-if="formData.linkType === '2'" label="路由地址">
        <el-input
          v-model="formData.linkUrl"
          placeholder="/about/team 或 /news/detail/123"
          clearable
        />
        <div class="form-tip">填写前端门户网站的路由路径</div>
      </el-form-item>

      <el-form-item v-if="formData.linkType === '3'" label="外链地址">
        <el-input
          v-model="formData.linkUrl"
          placeholder="https://www.example.com"
          clearable
        />
        <div class="form-tip">需包含 http:// 或 https:// 协议头</div>
      </el-form-item>

      <!-- 其他设置 -->
      <el-divider content-position="left">其他设置</el-divider>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="排序权重">
            <el-input-number
              v-model="formData.sortOrder"
              :min="0"
              :max="9999"
              controls-position="right"
              style="width: 100%"
            />
            <div class="form-tip">数值越小越靠前</div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <el-radio-group v-model="formData.status">
              <el-radio value="0">正常</el-radio>
              <el-radio value="1">停用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="备注">
        <el-input
          v-model="formData.remark"
          type="textarea"
          :rows="2"
          maxlength="500"
          show-word-limit
          placeholder="可选填"
        />
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <div class="form-footer">
      <el-button @click="$emit('cancel')">取消</el-button>
      <el-button type="primary" :loading="saving" @click="handleSubmit">
        保存
      </el-button>
    </div>
  </el-card>
</template>

<script setup name="MilestoneForm">
import { ref, computed, watch } from 'vue'

const props = defineProps({
  milestone: { type: Object, default: () => ({}) },
  companyId: { type: [Number, String], required: true }
})

const emit = defineEmits(['save', 'cancel'])

const formRef = ref(null)
const saving = ref(false)

/** 表单数据 */
const formData = ref({
  milestoneId: undefined,
  companyId: props.companyId,
  milestoneTitle: '',
  milestoneYear: null,
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
})

/** 是否为编辑模式 */
const isEdit = computed(() => !!props.milestone?.milestoneId)

/**
 * 日期选择器的值（用于el-date-picker绑定）
 */
const dateValue = computed({
  get() {
    if (formData.value.milestoneYear) {
      return new Date(formData.value.milestoneYear, 0, 1)
    }
    return null
  },
  set(val) {
    if (val) {
      formData.value.milestoneYear = val.getFullYear()
    }
  }
})

/**
 * 根据月份计算最大天数
 */
const maxDay = computed(() => {
  if (!formData.value.milestoneMonth) {
    return 31
  }

  const month = formData.value.milestoneMonth
  if ([4, 6, 9, 11].includes(month)) {
    return 30
  }

  if (month === 2) {
    return 29
  }

  return 31
})

/** 表单验证规则 */
const rules = {
  milestoneTitle: [
    { required: true, message: '请输入事件标题', trigger: 'blur' },
    { min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur' }
  ],
  milestoneYear: [
    { required: true, message: '请选择年份', trigger: 'change' }
  ],
  milestoneLevel: [
    { required: true, message: '请选择节点级别', trigger: 'change' }
  ]
}

// 监听外部传入的milestone变化，同步到表单
watch(
  () => props.milestone,
  (newVal) => {
    if (newVal && Object.keys(newVal).length > 0) {
      formData.value = { ...newVal }
    } else {
      resetForm()
    }
  },
  { deep: true, immediate: true }
)

// 监听companyId变化
watch(
  () => props.companyId,
  (newVal) => {
    if (newVal) {
      formData.value.companyId = newVal
    }
  }
)

/**
 * 年份变化处理
 *
 * @param val 日期对象
 */
function handleYearChange(val) {
  if (val) {
    formData.value.milestoneYear = val.getFullYear()
  }
}

/**
 * 链接类型变化时清空相关字段
 *
 * @param val 链接类型值
 */
function handleLinkTypeChange(val) {
  formData.value.linkUrl = ''
  formData.value.linkContent = ''
}

/**
 * 重置表单为默认值
 */
function resetForm() {
  formData.value = {
    milestoneId: undefined,
    companyId: props.companyId,
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
 * 提交表单（保存）
 */
async function handleSubmit() {
  if (!formRef.value) {
    return
  }

  try {
    // 表单校验
    await formRef.value.validate()

    // 最终校验：确保企业ID存在
    if (!formData.value.companyId) {
      throw new Error('企业ID不能为空')
    }

    // 校验链接类型相关字段
    if (formData.value.linkType === '2' && !formData.value.linkUrl) {
      throw new Error('请填写路由地址')
    }

    if (formData.value.linkType === '3' && !formData.value.linkUrl) {
      throw new Error('请填写外链地址')
    }

    if (formData.value.linkType === '1' && !formData.value.linkContent) {
      throw new Error('请填写详情内容')
    }

    saving.value = true

    // 触发保存事件
    emit('save', { ...formData.value })
  } catch (error) {
    console.error('表单校验失败:', error)
    if (error.message) {
      // 可以在这里显示错误提示
    }
  } finally {
    saving.value = false
  }
}
</script>

<style scoped>
.form-card {
  height: calc(100vh - 280px);
  overflow-y: auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.form-footer {
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid #e4e7ed;
  text-align: right;
}

.form-tip {
  color: #909399;
  font-size: 12px;
  margin-top: 4px;
  line-height: 1.4;
}
</style>
