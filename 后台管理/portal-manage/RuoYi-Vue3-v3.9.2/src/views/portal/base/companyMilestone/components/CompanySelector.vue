<template>
  <div class="company-selector">
    <label class="selector-label">选择企业：</label>
    <el-select
      v-model="selectedCompanyId"
      placeholder="请选择企业"
      :disabled="isDisabled"
      @change="handleChange"
      style="width: 100%"
    >
      <el-option
        v-for="company in companyList"
        :key="company.infoId"
        :label="company.companyName"
        :value="company.infoId"
      >
        <span>{{ company.companyName }}</span>
        <span style="float: right; color: #999; font-size: 12px">
          {{ company.infoId }}
        </span>
      </el-option>
    </el-select>
  </div>
</template>

<script setup name="CompanySelector">
import { ref, onMounted, computed } from 'vue'
import { listCompany } from "@/api/portal/company"

const props = defineProps({
  modelValue: { type: [Number, String], default: null }
})

const emit = defineEmits(['update:modelValue', 'change'])

/** 企业列表 */
const companyList = ref([])

/** 当前选中的企业ID */
const selectedCompanyId = ref(props.modelValue)

/**
 * 是否禁用选择
 * 超级管理员可以选择任意企业，企业管理员只能操作本企业
 */
const isDisabled = computed(() => {
  // TODO: 根据实际权限系统判断
  // return !userStore.isAdmin
  return false
})

/**
 * 加载企业列表
 */
async function loadCompanyList() {
  try {
    const res = await listCompany({ status: '0' })
    if (res.code === 200 && res.rows) {
      companyList.value = res.rows
    }
  } catch (error) {
    console.error('获取企业列表失败:', error)
  }
}

/**
 * 选择变化时触发
 *
 * @param value 选中的企业ID
 */
function handleChange(value) {
  emit('update:modelValue', value)
  emit('change', value)
}

onMounted(() => {
  loadCompanyList()
})
</script>

<style scoped>
.company-selector {
  display: flex;
  align-items: center;
  gap: 8px;
}

.selector-label {
  font-size: 14px;
  color: #606266;
  white-space: nowrap;
}
</style>
