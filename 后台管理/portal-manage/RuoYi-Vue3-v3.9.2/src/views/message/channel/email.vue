<template>
  <div class="app-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>邮件消息配置</span>
        </div>
      </template>

      <el-form ref="formRef" :model="form" :rules="rules" label-width="140px" style="max-width: 600px;">
        <el-form-item label="SMTP服务器" prop="smtpHost">
          <el-input v-model="form.smtpHost" placeholder="请输入SMTP服务器地址，如：smtp.qq.com" />
        </el-form-item>
        <el-form-item label="SMTP端口" prop="smtpPort">
          <el-input-number v-model="form.smtpPort" :min="1" :max="65535" placeholder="请输入SMTP端口" style="width: 100%;" />
          <div style="color: #909399; font-size: 12px; margin-top: 5px;">
            常用端口：25（默认）、465（SSL）、587（TLS）
          </div>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名/邮箱" />
        </el-form-item>
        <el-form-item label="密码/授权码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码或授权码" show-password />
        </el-form-item>
        <el-form-item label="发件人地址" prop="fromAddress">
          <el-input v-model="form.fromAddress" placeholder="请输入发件人地址" />
        </el-form-item>
        <el-form-item label="发件人名称" prop="fromName">
          <el-input v-model="form.fromName" placeholder="请输入发件人名称" />
        </el-form-item>
        <el-form-item label="使用SSL" prop="useSsl">
          <el-switch v-model="form.useSsl" active-value="1" inactive-value="0" />
          <span style="margin-left: 10px; color: #909399;">465端口通常需要启用SSL</span>
        </el-form-item>
        <el-form-item label="使用TLS" prop="useTls">
          <el-switch v-model="form.useTls" active-value="1" inactive-value="0" />
          <span style="margin-left: 10px; color: #909399;">587端口通常需要启用TLS</span>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存配置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup name="EmailChannel">
import { getEmailConfig, addEmailConfig, updateEmailConfig } from '@/api/message/email'

const { proxy } = getCurrentInstance()

const loading = ref(false)
const configId = ref(null)

const formRef = ref(null)

const form = reactive({
  smtpHost: '',
  smtpPort: 465,
  username: '',
  password: '',
  fromAddress: '',
  fromName: '',
  useSsl: '1',
  useTls: '0'
})

const rules = {
  smtpHost: [{ required: true, message: '请输入SMTP服务器地址', trigger: 'blur' }],
  smtpPort: [{ required: true, message: '请输入SMTP端口', trigger: 'blur' }],
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码或授权码', trigger: 'blur' }],
  fromAddress: [
    { required: true, message: '请输入发件人地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ]
}

function handleSubmit() {
  formRef.value.validate(valid => {
    if (valid) {
      loading.value = true
      const data = { ...form, configId: configId.value }

      const submitFn = configId.value ? updateEmailConfig : addEmailConfig
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
  getEmailConfig().then(response => {
    if (response.data) {
      configId.value = response.data.configId
      Object.assign(form, response.data)
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