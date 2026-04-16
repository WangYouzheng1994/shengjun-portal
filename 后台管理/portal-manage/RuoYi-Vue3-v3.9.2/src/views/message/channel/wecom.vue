<template>
  <div class="app-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>企业微信消息配置</span>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="企业自建应用" name="internal">
          <el-form ref="internalFormRef" :model="internalForm" :rules="internalRules" label-width="140px" style="max-width: 600px;">
            <el-form-item label="企业ID" prop="corpId">
              <el-input v-model="internalForm.corpId" placeholder="请输入企业ID" />
            </el-form-item>
            <el-form-item label="AgentId" prop="agentId">
              <el-input v-model="internalForm.agentId" placeholder="请输入应用AgentId" />
            </el-form-item>
            <el-form-item label="AgentSecret" prop="agentSecret">
              <el-input v-model="internalForm.agentSecret" placeholder="请输入应用AgentSecret" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit('internal')" :loading="loading">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="群机器人" name="robot">
          <el-form ref="robotFormRef" :model="robotForm" :rules="robotRules" label-width="140px" style="max-width: 600px;">
            <el-form-item label="Webhook地址" prop="webhookUrl">
              <el-input v-model="robotForm.webhookUrl" placeholder="请输入Webhook地址" type="textarea" :rows="3" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit('robot')" :loading="loading">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup name="WeComChannel">
import { getWeComConfig, addWeComConfig, updateWeComConfig } from '@/api/message/wecom'

const { proxy } = getCurrentInstance()

const activeTab = ref('internal')
const loading = ref(false)
const configId = ref(null)

const internalFormRef = ref(null)
const robotFormRef = ref(null)

const internalForm = reactive({
  corpId: '',
  agentId: '',
  agentSecret: '',
  wecomType: 'internal'
})

const robotForm = reactive({
  webhookUrl: '',
  wecomType: 'robot'
})

const internalRules = {
  corpId: [{ required: true, message: '请输入企业ID', trigger: 'blur' }],
  agentId: [{ required: true, message: '请输入AgentId', trigger: 'blur' }],
  agentSecret: [{ required: true, message: '请输入AgentSecret', trigger: 'blur' }]
}

const robotRules = {
  webhookUrl: [{ required: true, message: '请输入Webhook地址', trigger: 'blur' }]
}

function handleTabChange(tabName) {
  activeTab.value = tabName
}

function getFormByType(type) {
  return type === 'internal' ? internalForm : robotForm
}

function getFormRefByType(type) {
  return type === 'internal' ? internalFormRef : robotFormRef
}

function handleSubmit(type) {
  const formRef = getFormRefByType(type)
  const form = getFormByType(type)

  formRef.value.validate(valid => {
    if (valid) {
      loading.value = true
      const data = { ...form, configId: configId.value }

      const submitFn = configId.value ? updateWeComConfig : addWeComConfig
      submitFn(data).then(response => {
        proxy.$modal.msgSuccess('保存成功')
        configId.value = response.data?.configId
        loadConfig()
      }).finally(() => {
        loading.value = false
      })
    }
  })
}

function loadConfig() {
  getWeComConfig().then(response => {
    if (response.data) {
      configId.value = response.data.configId
      const data = response.data
      const type = data.wecomType || 'internal'
      activeTab.value = type

      if (type === 'internal') {
        Object.assign(internalForm, {
          configId: data.configId,
          corpId: data.corpId,
          agentId: data.agentId,
          agentSecret: data.agentSecret,
          wecomType: type
        })
      } else if (type === 'robot') {
        Object.assign(robotForm, {
          configId: data.configId,
          webhookUrl: data.webhookUrl,
          wecomType: type
        })
      }
    }
  })
}

onMounted(() => {
  loadConfig()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>