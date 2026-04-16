<template>
  <div class="app-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>钉钉消息配置</span>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="企业内部应用" name="internal">
          <el-form ref="internalFormRef" :model="internalForm" :rules="internalRules" label-width="140px" style="max-width: 600px;">
            <el-form-item label="企业ID" prop="corpId">
              <el-input v-model="internalForm.corpId" placeholder="请输入企业ID" />
            </el-form-item>
            <el-form-item label="AppKey" prop="appKey">
              <el-input v-model="internalForm.appKey" placeholder="请输入应用AppKey" />
            </el-form-item>
            <el-form-item label="AppSecret" prop="appSecret">
              <el-input v-model="internalForm.appSecret" placeholder="请输入应用AppSecret" show-password />
            </el-form-item>
            <el-form-item label="AgentId" prop="agentId">
              <el-input v-model="internalForm.agentId" placeholder="请输入应用AgentId" />
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
            <el-form-item label="加签密钥" prop="secretKey">
              <el-input v-model="robotForm.secretKey" placeholder="请输入加签密钥" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit('robot')" :loading="loading">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="ISV第三方" name="isv">
          <el-form ref="isvFormRef" :model="isvForm" :rules="isvRules" label-width="140px" style="max-width: 600px;">
            <el-form-item label="企业ID" prop="corpId">
              <el-input v-model="isvForm.corpId" placeholder="请输入企业ID" />
            </el-form-item>
            <el-form-item label="套件ID" prop="suiteId">
              <el-input v-model="isvForm.suiteId" placeholder="请输入套件ID" />
            </el-form-item>
            <el-form-item label="套件Secret" prop="suiteSecret">
              <el-input v-model="isvForm.suiteSecret" placeholder="请输入套件Secret" show-password />
            </el-form-item>
            <el-form-item label="调用凭证" prop="token">
              <el-input v-model="isvForm.token" placeholder="请输入调用凭证" />
            </el-form-item>
            <el-form-item label="加解密密钥" prop="encodingAesKey">
              <el-input v-model="isvForm.encodingAesKey" placeholder="请输入加解密密钥" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSubmit('isv')" :loading="loading">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup name="DingtalkChannel">
import { getDingtalkConfig, addDingtalkConfig, updateDingtalkConfig } from '@/api/message/dingtalk'

const { proxy } = getCurrentInstance()

const activeTab = ref('internal')
const loading = ref(false)
const configId = ref(null)

const internalFormRef = ref(null)
const robotFormRef = ref(null)
const isvFormRef = ref(null)

const internalForm = reactive({
  corpId: '',
  appKey: '',
  appSecret: '',
  agentId: '',
  dingtalkType: 'internal'
})

const robotForm = reactive({
  webhookUrl: '',
  secretKey: '',
  dingtalkType: 'robot'
})

const isvForm = reactive({
  corpId: '',
  suiteId: '',
  suiteSecret: '',
  token: '',
  encodingAesKey: '',
  dingtalkType: 'isv'
})

const internalRules = {
  corpId: [{ required: true, message: '请输入企业ID', trigger: 'blur' }],
  appKey: [{ required: true, message: '请输入AppKey', trigger: 'blur' }],
  appSecret: [{ required: true, message: '请输入AppSecret', trigger: 'blur' }],
  agentId: [{ required: true, message: '请输入AgentId', trigger: 'blur' }]
}

const robotRules = {
  webhookUrl: [{ required: true, message: '请输入Webhook地址', trigger: 'blur' }]
}

const isvRules = {
  corpId: [{ required: true, message: '请输入企业ID', trigger: 'blur' }],
  suiteId: [{ required: true, message: '请输入套件ID', trigger: 'blur' }],
  suiteSecret: [{ required: true, message: '请输入套件Secret', trigger: 'blur' }]
}

function handleTabChange(tabName) {
  activeTab.value = tabName
}

function getFormByType(type) {
  switch (type) {
    case 'internal':
      return internalForm
    case 'robot':
      return robotForm
    case 'isv':
      return isvForm
    default:
      return null
  }
}

function getFormRefByType(type) {
  switch (type) {
    case 'internal':
      return internalFormRef
    case 'robot':
      return robotFormRef
    case 'isv':
      return isvFormRef
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

      const submitFn = configId.value ? updateDingtalkConfig : addDingtalkConfig
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
  getDingtalkConfig().then(response => {
    if (response.data) {
      configId.value = response.data.configId
      const data = response.data
      const type = data.dingtalkType || 'internal'
      activeTab.value = type

      if (type === 'internal') {
        Object.assign(internalForm, {
          configId: data.configId,
          corpId: data.corpId,
          appKey: data.appKey,
          appSecret: data.appSecret,
          agentId: data.agentId,
          dingtalkType: type
        })
      } else if (type === 'robot') {
        Object.assign(robotForm, {
          configId: data.configId,
          webhookUrl: data.webhookUrl,
          secretKey: data.secretKey,
          dingtalkType: type
        })
      } else if (type === 'isv') {
        Object.assign(isvForm, {
          configId: data.configId,
          corpId: data.corpId,
          suiteId: data.suiteId,
          suiteSecret: data.suiteSecret,
          token: data.token,
          encodingAesKey: data.encodingAesKey,
          dingtalkType: type
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