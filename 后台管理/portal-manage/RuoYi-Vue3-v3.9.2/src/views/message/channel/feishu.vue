<template>
  <div class="app-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>飞书消息配置</span>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="企业自建应用" name="internal">
          <el-form ref="internalFormRef" :model="internalForm" :rules="internalRules" label-width="140px" style="max-width: 600px;">
            <el-form-item label="AppId" prop="appId">
              <el-input v-model="internalForm.appId" placeholder="请输入应用AppId" />
            </el-form-item>
            <el-form-item label="AppSecret" prop="appSecret">
              <el-input v-model="internalForm.appSecret" placeholder="请输入应用AppSecret" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit('internal')" :loading="loading">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="WebHook机器人" name="webhook">
          <el-form ref="webhookFormRef" :model="webhookForm" :rules="webhookRules" label-width="140px" style="max-width: 600px;">
            <el-form-item label="Webhook地址" prop="webhookUrl">
              <el-input v-model="webhookForm.webhookUrl" placeholder="请输入Webhook地址" type="textarea" :rows="3" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit('webhook')" :loading="loading">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="第三方应用" name="thirdparty">
          <el-form ref="thirdpartyFormRef" :model="thirdpartyForm" :rules="thirdpartyRules" label-width="140px" style="max-width: 600px;">
            <el-form-item label="AppId" prop="appId">
              <el-input v-model="thirdpartyForm.appId" placeholder="请输入应用AppId" />
            </el-form-item>
            <el-form-item label="AppSecret" prop="appSecret">
              <el-input v-model="thirdpartyForm.appSecret" placeholder="请输入应用AppSecret" show-password />
            </el-form-item>
            <el-form-item label="租户标识" prop="tenantKey">
              <el-input v-model="thirdpartyForm.tenantKey" placeholder="请输入租户标识" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit('thirdparty')" :loading="loading">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup name="FeishuChannel">
import { getFeishuConfig, addFeishuConfig, updateFeishuConfig } from '@/api/message/feishu'

const { proxy } = getCurrentInstance()

const activeTab = ref('internal')
const loading = ref(false)
const configId = ref(null)

const internalFormRef = ref(null)
const webhookFormRef = ref(null)
const thirdpartyFormRef = ref(null)

const internalForm = reactive({
  appId: '',
  appSecret: '',
  feishuType: 'internal'
})

const webhookForm = reactive({
  webhookUrl: '',
  feishuType: 'webhook'
})

const thirdpartyForm = reactive({
  appId: '',
  appSecret: '',
  tenantKey: '',
  feishuType: 'thirdparty'
})

const internalRules = {
  appId: [{ required: true, message: '请输入AppId', trigger: 'blur' }],
  appSecret: [{ required: true, message: '请输入AppSecret', trigger: 'blur' }]
}

const webhookRules = {
  webhookUrl: [{ required: true, message: '请输入Webhook地址', trigger: 'blur' }]
}

const thirdpartyRules = {
  appId: [{ required: true, message: '请输入AppId', trigger: 'blur' }],
  appSecret: [{ required: true, message: '请输入AppSecret', trigger: 'blur' }],
  tenantKey: [{ required: true, message: '请输入租户标识', trigger: 'blur' }]
}

function handleTabChange(tabName) {
  activeTab.value = tabName
}

function getFormByType(type) {
  switch (type) {
    case 'internal':
      return internalForm
    case 'webhook':
      return webhookForm
    case 'thirdparty':
      return thirdpartyForm
    default:
      return null
  }
}

function getFormRefByType(type) {
  switch (type) {
    case 'internal':
      return internalFormRef
    case 'webhook':
      return webhookFormRef
    case 'thirdparty':
      return thirdpartyFormRef
    default:
      return null
  }
}

function handleSubmit(type) {
  const formRef = getFormRefByType(type)
  const form = getFormByType(type)

  formRef.value.validate(valid => {
    if (valid) {
      loading.value = true
      const data = { ...form, configId: configId.value }

      const submitFn = configId.value ? updateFeishuConfig : addFeishuConfig
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
  getFeishuConfig().then(response => {
    if (response.data) {
      configId.value = response.data.configId
      const data = response.data
      const type = data.feishuType || 'internal'
      activeTab.value = type

      if (type === 'internal') {
        Object.assign(internalForm, {
          configId: data.configId,
          appId: data.appId,
          appSecret: data.appSecret,
          feishuType: type
        })
      } else if (type === 'webhook') {
        Object.assign(webhookForm, {
          configId: data.configId,
          webhookUrl: data.webhookUrl,
          feishuType: type
        })
      } else if (type === 'thirdparty') {
        Object.assign(thirdpartyForm, {
          configId: data.configId,
          appId: data.appId,
          appSecret: data.appSecret,
          tenantKey: data.tenantKey,
          feishuType: type
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