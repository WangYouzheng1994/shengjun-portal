<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="渠道名称" prop="channelName">
        <el-input
          v-model="queryParams.channelName"
          placeholder="请输入渠道名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="渠道类型" prop="channelType">
        <el-select v-model="queryParams.channelType" placeholder="请选择渠道类型" clearable style="width: 150px">
          <el-option label="钉钉" value="dingtalk" />
          <el-option label="企业微信" value="wecom" />
          <el-option label="邮件" value="email" />
          <el-option label="飞书" value="feishu" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="isEnabled">
        <el-select v-model="queryParams.isEnabled" placeholder="请选择状态" clearable style="width: 120px">
          <el-option label="启用" value="1" />
          <el-option label="禁用" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['message:channel:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['message:channel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['message:channel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="channelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="渠道ID" align="center" prop="channelId" width="80" />
      <el-table-column label="渠道类型" align="center" prop="channelType" width="100">
        <template #default="scope">
          <span v-if="scope.row.channelType === 'dingtalk'">钉钉</span>
          <span v-else-if="scope.row.channelType === 'wecom'">企业微信</span>
          <span v-else-if="scope.row.channelType === 'email'">邮件</span>
          <span v-else-if="scope.row.channelType === 'feishu'">飞书</span>
          <span v-else>{{ scope.row.channelType }}</span>
        </template>
      </el-table-column>
      <el-table-column label="渠道名称" align="center" prop="channelName" min-width="120" :show-overflow-tooltip="true" />
      <el-table-column label="是否启用" align="center" prop="isEnabled" width="100">
        <template #default="scope">
          <el-switch
            v-model="scope.row.isEnabled"
            active-value="1"
            inactive-value="0"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['message:channel:edit']"
          />
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sortOrder" width="80" />
      <el-table-column label="备注" align="center" prop="remark" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)">查看</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['message:channel:edit']">修改</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['message:channel:remove']">删除</el-button>
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

    <el-dialog :title="title" v-model="open" width="600px" append-to-body destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="渠道类型" prop="channelType">
          <el-select v-model="form.channelType" placeholder="请选择渠道类型" :disabled="form.channelId !== undefined" style="width: 100%">
            <el-option label="钉钉" value="dingtalk" />
            <el-option label="企业微信" value="wecom" />
            <el-option label="邮件" value="email" />
            <el-option label="飞书" value="feishu" />
          </el-select>
        </el-form-item>
        <el-form-item label="渠道名称" prop="channelName">
          <el-input v-model="form.channelName" placeholder="请输入渠道名称" />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="form.sortOrder" :min="0" :max="9999" />
        </el-form-item>
        <el-form-item label="是否启用" prop="isEnabled">
          <el-radio-group v-model="form.isEnabled">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="配置内容" prop="configContent">
          <el-input v-model="form.configContent" type="textarea" :rows="5" placeholder="请输入JSON格式配置内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="渠道详情" v-model="detailOpen" width="600px" append-to-body destroy-on-close>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="渠道ID">{{ detailForm.channelId }}</el-descriptions-item>
        <el-descriptions-item label="渠道类型">
          <span v-if="detailForm.channelType === 'dingtalk'">钉钉</span>
          <span v-else-if="detailForm.channelType === 'wecom'">企业微信</span>
          <span v-else-if="detailForm.channelType === 'email'">邮件</span>
          <span v-else-if="detailForm.channelType === 'feishu'">飞书</span>
          <span v-else>{{ detailForm.channelType }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="渠道名称">{{ detailForm.channelName }}</el-descriptions-item>
        <el-descriptions-item label="排序">{{ detailForm.sortOrder }}</el-descriptions-item>
        <el-descriptions-item label="是否启用">{{ detailForm.isEnabled === '1' ? '启用' : '禁用' }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ detailForm.remark }}</el-descriptions-item>
        <el-descriptions-item label="配置内容" :span="2">
          <pre style="margin: 0; white-space: pre-wrap;">{{ detailForm.configContent }}</pre>
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

<script setup name="MessageChannel">
import { listChannel, getChannel, addChannel, updateChannel, delChannel } from '@/api/message/channel'

const { proxy } = getCurrentInstance()

const channelList = ref([])
const open = ref(false)
const detailOpen = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const multiple = ref(true)
const total = ref(0)
const title = ref('')

const data = reactive({
  form: {},
  detailForm: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    channelType: null,
    channelName: null,
    isEnabled: null
  },
  rules: {
    channelType: [{ required: true, message: '渠道类型不能为空', trigger: 'change' }],
    channelName: [{ required: true, message: '渠道名称不能为空', trigger: 'blur' }]
  }
})

const { queryParams, form, detailForm, rules } = toRefs(data)

function getList() {
  loading.value = true
  listChannel(queryParams.value).then(response => {
    channelList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function cancel() {
  open.value = false
  reset()
}

function reset() {
  form.value = {
    channelId: undefined,
    channelType: null,
    channelName: null,
    sortOrder: 0,
    isEnabled: '0',
    configContent: null,
    remark: null
  }
  proxy.resetForm('formRef')
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
  ids.value = selection.map(item => item.channelId)
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = '添加渠道配置'
}

function handleDetail(row) {
  detailForm.value = row
  detailOpen.value = true
}

function handleUpdate(row) {
  reset()
  const channelId = row.channelId || ids.value[0]
  getChannel(channelId).then(response => {
    form.value = response.data
    open.value = true
    title.value = '修改渠道配置'
  })
}

function submitForm() {
  proxy.$refs['formRef'].validate(valid => {
    if (valid) {
      if (form.value.channelId !== undefined) {
        updateChannel(form.value).then(response => {
          proxy.$modal.msgSuccess('修改成功')
          open.value = false
          getList()
        })
      } else {
        addChannel(form.value).then(response => {
          proxy.$modal.msgSuccess('新增成功')
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row) {
  const channelIds = row.channelId || ids.value
  proxy.$modal.confirm('是否确认删除渠道编号为"' + channelIds + '"的数据项？').then(function() {
    return delChannel(channelIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess('删除成功')
  }).catch(() => {})
}

function handleStatusChange(row) {
  updateChannel(row).then(response => {
    proxy.$modal.msgSuccess('修改成功')
  })
}

function handleExport() {
  proxy.download('message/channel/export', {
    ...queryParams.value
  }, `channel_${new Date().getTime()}.xlsx`)
}

getList()
</script>
