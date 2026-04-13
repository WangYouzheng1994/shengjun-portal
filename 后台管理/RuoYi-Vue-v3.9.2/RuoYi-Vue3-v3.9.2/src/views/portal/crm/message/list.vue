<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="联系人" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入联系人姓名"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入联系电话"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理状态" prop="handleStatus">
        <el-select v-model="queryParams.handleStatus" placeholder="请选择状态" clearable style="width: 150px">
          <el-option
            v-for="dict in portal_message_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否已读" prop="isRead">
        <el-select v-model="queryParams.isRead" placeholder="请选择" clearable style="width: 120px">
          <el-option label="未读" value="0" />
          <el-option label="已读" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="multiple"
          @click="handleBatchReply"
          v-hasPermi="['portal:crm:message:reply']"
        >批量回复</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['portal:crm:message:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['portal:crm:message:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="messageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="消息ID" align="center" prop="messageId" width="80" />
      <el-table-column label="联系人" align="center" prop="name" width="100" />
      <el-table-column label="联系电话" align="center" prop="phone" width="120" />
      <el-table-column label="电子邮箱" align="center" prop="email" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="消息主题" align="center" prop="subject" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="消息内容" align="center" prop="content" min-width="200" :show-overflow-tooltip="true" />
      <el-table-column label="关联客户" align="center" prop="customerName" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.customerName" type="success">{{ scope.row.customerName }}</el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="处理状态" align="center" prop="handleStatus" width="90">
        <template #default="scope">
          <dict-tag :options="portal_message_status" :value="scope.row.handleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="是否已读" align="center" prop="isRead" width="80">
        <template #default="scope">
          <el-tag v-if="scope.row.isRead === '0'" type="danger">未读</el-tag>
          <el-tag v-else type="success">已读</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="handlerName" width="90" />
      <el-table-column label="提交时间" align="center" prop="createTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="250" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)">查看</el-button>
          <el-button
            v-if="!scope.row.customerId"
            link type="warning"
            icon="User"
            @click="handleConvert(scope.row)"
            v-hasPermi="['portal:crm:message:convert']"
          >转为客户</el-button>
          <el-button
            v-if="scope.row.handleStatus !== '2' && scope.row.handleStatus !== '3'"
            link type="success"
            icon="ChatDotRound"
            @click="handleReply(scope.row)"
            v-hasPermi="['portal:crm:message:reply']"
          >回复</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['portal:crm:message:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 消息通知详情/回复对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body destroy-on-close>
      <el-descriptions :column="2" border v-if="!isEdit">
        <el-descriptions-item label="联系人">{{ form.name }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ form.phone }}</el-descriptions-item>
        <el-descriptions-item label="电子邮箱">{{ form.email }}</el-descriptions-item>
        <el-descriptions-item label="消息主题">{{ form.subject }}</el-descriptions-item>
        <el-descriptions-item label="消息内容" :span="2">{{ form.content }}</el-descriptions-item>
        <el-descriptions-item label="IP地址">{{ form.ipAddress }}</el-descriptions-item>
        <el-descriptions-item label="来源页面">{{ form.sourcePage }}</el-descriptions-item>
        <el-descriptions-item label="提交时间">{{ parseTime(form.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="当前状态">
          <dict-tag :options="portal_message_status" :value="form.handleStatus" />
        </el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">回复信息</el-divider>
      <el-form ref="messageRef" :model="form" :rules="rules" label-width="100px" v-if="isEdit">
        <el-form-item label="回复内容" prop="replyContent">
          <el-input v-model="form.replyContent" type="textarea" :rows="5" placeholder="请输入回复内容" />
        </el-form-item>
      </el-form>

      <div v-if="!isEdit && form.replyContent">
        <el-divider content-position="left">历史回复</el-divider>
        <div class="reply-content">{{ form.replyContent }}</div>
        <div class="reply-time">回复时间：{{ parseTime(form.replyTime) }} | 回复人：{{ form.handlerName }}</div>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">关 闭</el-button>
          <el-button v-if="isEdit" type="primary" @click="submitForm">回复并关闭</el-button>
          <el-button v-else-if="form.handleStatus !== '2' && form.handleStatus !== '3'" type="primary" @click="switchToReply">回复消息</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 转换为客户对话框 -->
    <el-dialog title="消息通知转客户" v-model="convertOpen" width="650px" append-to-body destroy-on-close>
      <el-alert
        type="info"
        :closable="false"
        style="margin-bottom: 20px"
      >
        <template #title>
          将为联系人 <strong>{{ convertForm.name }}</strong> 创建客户档案
          <br/>
          <span style="font-size: 12px; color: #909399;">系统将根据手机号/邮箱自动匹配已有客户，未匹配到则创建新客户</span>
        </template>
      </el-alert>

      <el-form ref="convertRef" :model="convertForm" :rules="convertRules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="客户姓名">
              <el-input v-model="convertForm.customerName" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码">
              <el-input v-model="convertForm.phone" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="电子邮箱">
              <el-input v-model="convertForm.email" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户等级" prop="level">
              <el-select v-model="convertForm.level" placeholder="请选择等级" style="width: 100%">
                <el-option
                  v-for="dict in portal_customer_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司名称" prop="companyName">
              <el-input v-model="convertForm.companyName" placeholder="请输入公司名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职位" prop="position">
              <el-input v-model="convertForm.position" placeholder="请输入职位" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="地址" prop="address">
          <el-input v-model="convertForm.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="convertForm.remark" type="textarea" :rows="3" placeholder="请输入备注信息" />
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="convertOpen = false">取 消</el-button>
          <el-button type="primary" @click="submitConvert">确认转换</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="MessageList">
import { listMessage, getMessage, delMessage, replyMessage, exportMessage, updateMessage, convertMessageToCustomer } from "@/api/portal/crm/message"

const { proxy } = getCurrentInstance()
const { portal_message_status, portal_customer_level } = proxy.useDict("portal_message_status", "portal_customer_level")

const messageList = ref([])
const open = ref(false)
const convertOpen = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const isEdit = ref(false)

const data = reactive({
  form: {},
  convertForm: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: null,
    phone: null,
    handleStatus: null,
    isRead: null
  },
  rules: {
    replyContent: [
      { required: true, message: "回复内容不能为空", trigger: "blur" }
    ]
  },
  convertRules: {
    level: [
      { required: true, message: "请选择客户等级", trigger: "change" }
    ]
  }
})

const { queryParams, form, rules, convertForm, convertRules } = toRefs(data)

/** 查询消息通知列表 */
function getList() {
  loading.value = true
  listMessage(queryParams.value).then(response => {
    messageList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 取消按钮 */
function cancel() {
  open.value = false
  isEdit.value = false
  reset()
}

/** 表单重置 */
function reset() {
  form.value = {
    messageId: null,
    name: null,
    phone: null,
    email: null,
    subject: null,
    content: null,
    handleStatus: null,
    replyContent: null
  }
  proxy.resetForm("messageRef")
}

/** 转换表单重置 */
function resetConvert() {
  convertForm.value = {
    messageId: null,
    customerName: null,
    phone: null,
    email: null,
    companyName: null,
    position: null,
    level: '0',
    address: null,
    remark: null
  }
  proxy.resetForm("convertRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.messageId)
  multiple.value = !selection.length
}

/** 查看详情 */
function handleDetail(row) {
  reset()
  const _messageId = row.messageId || ids.value[0]
  getMessage(_messageId).then(response => {
    form.value = response.data
    isEdit.value = false
    open.value = true
    title.value = "消息通知详情"

    if (form.value.isRead === '0') {
      updateMessage({ messageId: _messageId, isRead: '1' })
    }
  })
}

/** 回复消息通知 */
function handleReply(row) {
  reset()
  const _messageId = row.messageId || ids.value[0]
  getMessage(_messageId).then(response => {
    form.value = response.data
    isEdit.value = true
    open.value = true
    title.value = "回复消息通知"

    if (form.value.isRead === '0') {
      updateMessage({ messageId: _messageId, isRead: '1' })
    }
  })
}

/** 切换到回复模式 */
function switchToReply() {
  isEdit.value = true
  title.value = "回复消息通知"
}

/** 提交回复 */
function submitForm() {
  proxy.$refs["messageRef"].validate(valid => {
    if (valid) {
      form.value.handlerId = proxy.$store.state.user.id
      form.value.handlerName = proxy.$store.state.user.name
      replyMessage(form.value).then(response => {
        proxy.$modal.msgSuccess("回复成功")
        open.value = false
        getList()
      })
    }
  })
}

/** 打开转换对话框 */
function handleConvert(row) {
  resetConvert()
  convertForm.value.messageId = row.messageId
  convertForm.value.customerName = row.name
  convertForm.value.phone = row.phone
  convertForm.value.email = row.email
  convertForm.value.remark = '来源于官网消息通知：' + (row.subject || '')
  convertOpen.value = true
}

/** 提交转换 */
function submitConvert() {
  proxy.$refs["convertRef"].validate(valid => {
    if (valid) {
      const data = {
        companyName: convertForm.value.companyName,
        position: convertForm.value.position,
        level: convertForm.value.level,
        address: convertForm.value.address,
        remark: convertForm.value.remark
      }
      
      convertMessageToCustomer(convertForm.value.messageId, data).then(response => {
        if (response.code === 200) {
          const msg = response.data.isNewCustomer 
            ? `转换成功！已为新客户"${response.data.customerName}"创建档案` 
            : `转换成功！已关联到现有客户"${response.data.customerName}"`
          
          proxy.$modal.msgSuccess(msg)
          convertOpen.value = false
          getList()
        } else {
          proxy.$modal.msgError(response.msg || '转换失败')
        }
      }).catch(() => {
        proxy.$modal.msgError('转换失败，请稍后重试')
      })
    }
  })
}

/** 批量回复 */
function handleBatchReply() {
  if (ids.value.length === 0) {
    proxy.$modal.msgWarning("请选择要回复的消息")
    return
  }
  proxy.$prompt('请输入批量回复内容', '批量回复', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /\S+/,
    inputErrorMessage: '回复内容不能为空'
  }).then(({ value }) => {
    ids.value.forEach(messageId => {
      replyMessage({
        messageId: messageId,
        replyContent: value,
        handlerId: proxy.$store.state.user.id,
        handlerName: proxy.$store.state.user.name
      })
    })
    proxy.$modal.msgSuccess("批量回复成功")
    getList()
  }).catch(() => {})
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _messageIds = row.messageId || ids.value
  proxy.$modal.confirm('是否确认删除消息编号为"' + _messageIds + '"的数据项？').then(function() {
    return delMessage(_messageIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('portal/crm/message/export', {
    ...queryParams.value
  }, `message_${new Date().getTime()}.xlsx`)
}

getList()
</script>

<style scoped>
.reply-content {
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 4px;
  line-height: 1.6;
  color: #606266;
}

.reply-time {
  margin-top: 10px;
  text-align: right;
  color: #909399;
  font-size: 13px;
}
</style>
