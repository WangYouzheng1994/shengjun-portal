<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="渠道类型" prop="channelType">
        <el-select v-model="queryParams.channelType" placeholder="请选择渠道类型" clearable style="width: 150px">
          <el-option label="钉钉" value="dingtalk" />
          <el-option label="企业微信" value="wecom" />
          <el-option label="邮件" value="email" />
          <el-option label="飞书" value="feishu" />
        </el-select>
      </el-form-item>
      <el-form-item label="发送状态" prop="sendStatus">
        <el-select v-model="queryParams.sendStatus" placeholder="请选择状态" clearable style="width: 120px">
          <el-option label="待发送" value="0" />
          <el-option label="成功" value="1" />
          <el-option label="失败" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="接收者" prop="receiver">
        <el-input
          v-model="queryParams.receiver"
          placeholder="请输入接收者"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消息标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入消息标题"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['message:sendLog:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['message:sendLog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sendLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日志ID" align="center" prop="logId" width="80" />
      <el-table-column label="渠道类型" align="center" prop="channelType" width="100">
        <template #default="scope">
          <span v-if="scope.row.channelType === 'dingtalk'">钉钉</span>
          <span v-else-if="scope.row.channelType === 'wecom'">企业微信</span>
          <span v-else-if="scope.row.channelType === 'email'">邮件</span>
          <span v-else-if="scope.row.channelType === 'feishu'">飞书</span>
          <span v-else>{{ scope.row.channelType }}</span>
        </template>
      </el-table-column>
      <el-table-column label="接收者" align="center" prop="receiver" min-width="120" :show-overflow-tooltip="true" />
      <el-table-column label="消息标题" align="center" prop="title" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="发送状态" align="center" prop="sendStatus" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.sendStatus === '0'" type="warning">待发送</el-tag>
          <el-tag v-else-if="scope.row.sendStatus === '1'" type="success">成功</el-tag>
          <el-tag v-else-if="scope.row.sendStatus === '2'" type="danger">失败</el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="发送时间" align="center" prop="sendTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.sendTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="错误信息" align="center" prop="errorMessage" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)">查看</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['message:sendLog:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog title="发送日志详情" v-model="detailOpen" width="700px" append-to-body destroy-on-close>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="日志ID">{{ detailForm.logId }}</el-descriptions-item>
        <el-descriptions-item label="渠道类型">
          <span v-if="detailForm.channelType === 'dingtalk'">钉钉</span>
          <span v-else-if="detailForm.channelType === 'wecom'">企业微信</span>
          <span v-else-if="detailForm.channelType === 'email'">邮件</span>
          <span v-else-if="detailForm.channelType === 'feishu'">飞书</span>
          <span v-else>{{ detailForm.channelType }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="接收者">{{ detailForm.receiver }}</el-descriptions-item>
        <el-descriptions-item label="发送状态">
          <el-tag v-if="detailForm.sendStatus === '0'" type="warning">待发送</el-tag>
          <el-tag v-else-if="detailForm.sendStatus === '1'" type="success">成功</el-tag>
          <el-tag v-else-if="detailForm.sendStatus === '2'" type="danger">失败</el-tag>
          <span v-else>-</span>
        </el-descriptions-item>
        <el-descriptions-item label="发送时间">{{ parseTime(detailForm.sendTime) }}</el-descriptions-item>
        <el-descriptions-item label="消息标题" :span="2">{{ detailForm.title }}</el-descriptions-item>
        <el-descriptions-item label="消息内容" :span="2">
          <pre style="margin: 0; white-space: pre-wrap;">{{ detailForm.content }}</pre>
        </el-descriptions-item>
        <el-descriptions-item label="错误码" v-if="detailForm.errorCode">{{ detailForm.errorCode }}</el-descriptions-item>
        <el-descriptions-item label="错误信息" v-if="detailForm.errorMessage" :span="2">
          <span style="color: #f56c6c;">{{ detailForm.errorMessage }}</span>
        </el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="detailOpen = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="MessageSendLog">
import { listSendLog, getSendLog, delSendLog } from '@/api/message/sendLog'

const { proxy } = getCurrentInstance()

const sendLogList = ref([])
const detailOpen = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const multiple = ref(true)
const total = ref(0)

const data = reactive({
  detailForm: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    channelType: null,
    sendType: null,
    sendStatus: null,
    receiver: null,
    title: null
  }
})

const { detailForm, queryParams } = toRefs(data)

function getList() {
  loading.value = true
  listSendLog(queryParams.value).then(response => {
    sendLogList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm('queryRef')
  handleQuery()
}

function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.logId)
  multiple.value = !selection.length
}

function handleDetail(row) {
  detailForm.value = row
  detailOpen.value = true
}

function handleDelete(row) {
  const logIds = row.logId || ids.value
  proxy.$modal.confirm('是否确认删除日志编号为"' + logIds + '"的数据项？').then(function() {
    return delSendLog(logIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess('删除成功')
  }).catch(() => {})
}

function handleExport() {
  proxy.download('message/sendLog/export', {
    ...queryParams.value
  }, `sendLog_${new Date().getTime()}.xlsx`)
}

getList()
</script>
