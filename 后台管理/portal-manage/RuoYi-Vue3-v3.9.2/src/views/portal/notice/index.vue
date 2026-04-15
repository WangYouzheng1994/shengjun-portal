<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入公告标题"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="noticeType">
        <el-select v-model="queryParams.noticeType" placeholder="请选择公告类型" clearable style="width: 200px">
          <el-option
            v-for="dict in portal_notice_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" prop="priorityLevel">
        <el-select v-model="queryParams.priorityLevel" placeholder="请选择优先级" clearable style="width: 200px">
          <el-option
            v-for="dict in portal_notice_priority"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">
          <el-option
            v-for="dict in portal_notice_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="展示方式" prop="displayMode">
        <el-select v-model="queryParams.displayMode" placeholder="请选择展示方式" clearable style="width: 200px">
          <el-option
            v-for="dict in portal_notice_display"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间" prop="dateRange">
        <el-date-picker
          v-model="dateRange"
          value-format="YYYY-MM-DD"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['portal:notice:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['portal:notice:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['portal:notice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['portal:notice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="noticeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="noticeId" width="80" />
      <el-table-column label="标题" align="center" prop="title" :show-overflow-tooltip="true" min-width="150" />
      <el-table-column label="类型" align="center" prop="noticeType" width="100">
        <template #default="scope">
          <dict-tag :options="portal_notice_type" :value="scope.row.noticeType" />
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="priorityLevel" width="80">
        <template #default="scope">
          <dict-tag :options="portal_notice_priority" :value="scope.row.priorityLevel" />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template #default="scope">
          <dict-tag :options="portal_notice_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="展示方式" align="center" prop="displayMode" width="100">
        <template #default="scope">
          <dict-tag :options="portal_notice_display" :value="scope.row.displayMode" />
        </template>
      </el-table-column>
      <el-table-column label="置顶" align="center" prop="isTop" width="60">
        <template #default="scope">
          <el-tag v-if="scope.row.isTop === '1'" type="danger">置顶</el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="常驻" align="center" prop="isSticky" width="60">
        <template #default="scope">
          <el-tag v-if="scope.row.isSticky === '1'" type="warning">常驻</el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="查看次数" align="center" prop="viewCount" width="80" />
      <el-table-column label="点击次数" align="center" prop="clickCount" width="80" />
      <el-table-column label="发布时间" align="center" prop="publishTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.publishTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['portal:notice:edit']">修改</el-button>
          <el-button
            v-if="scope.row.status === '0' || scope.row.status === '3'"
            link type="success"
            icon="Top"
            @click="handlePublish(scope.row)"
            v-hasPermi="['portal:notice:publish']"
          >发布</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            link type="warning"
            icon="Bottom"
            @click="handleOffline(scope.row)"
            v-hasPermi="['portal:notice:offline']"
          >停用</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['portal:notice:remove']">删除</el-button>
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

    <!-- 添加或修改公告对话框（大尺寸，包含富文本编辑器） -->
    <el-dialog :title="title" v-model="open" width="900px" append-to-body destroy-on-close>
      <el-form ref="noticeRef" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公告标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入公告标题" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公告类型" prop="noticeType">
              <el-select v-model="form.noticeType" placeholder="请选择公告类型" style="width: 100%">
                <el-option
                  v-for="dict in portal_notice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="优先级" prop="priorityLevel">
              <el-radio-group v-model="form.priorityLevel">
                <el-radio
                  v-for="dict in portal_notice_priority"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="展示方式" prop="displayMode">
              <el-select v-model="form.displayMode" placeholder="请选择展示方式" style="width: 100%">
                <el-option
                  v-for="dict in portal_notice_display"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="公告内容" prop="content">
              <editor v-model="form.content" :min-height="300" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="有效开始时间" prop="startTime">
              <el-date-picker
                v-model="form.startTime"
                type="datetime"
                value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="选择开始时间（可选）"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="有效结束时间" prop="endTime">
              <el-date-picker
                v-model="form.endTime"
                type="datetime"
                value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="选择结束时间（可选）"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="是否置顶" prop="isTop">
              <el-radio-group v-model="form.isTop">
                <el-radio label="0">否</el-radio>
                <el-radio label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否常驻" prop="isSticky">
              <el-radio-group v-model="form.isSticky">
                <el-radio label="0">否</el-radio>
                <el-radio label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="需要确认" prop="requireConfirm">
              <el-radio-group v-model="form.requireConfirm">
                <el-radio label="0">否</el-radio>
                <el-radio label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.requireConfirm === '1'">
          <el-col :span="12">
            <el-form-item label="确认按钮文字" prop="confirmText">
              <el-input v-model="form.confirmText" placeholder="如：我已知晓、确定" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="弹窗样式" prop="popupStyle">
              <el-select v-model="form.popupStyle" placeholder="请选择弹窗样式" style="width: 100%">
                <el-option label="默认" value="default" />
                <el-option label="警告" value="warning" />
                <el-option label="信息" value="info" />
                <el-option label="成功" value="success" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="目标受众" prop="targetAudience">
              <el-select v-model="form.targetAudience" placeholder="请选择目标受众" style="width: 100%">
                <el-option
                  v-for="dict in portal_notice_audience"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序权重" prop="sortOrder">
              <el-input-number v-model="form.sortOrder" :min="0" :max="9999" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in portal_notice_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Notice">
import { listNotice, getNotice, delNotice, addNotice, updateNotice, exportNotice, publishNotice, offlineNotice } from "@/api/portal/notice"

const { proxy } = getCurrentInstance()
const { portal_notice_type, portal_notice_priority, portal_notice_status, portal_notice_display, portal_notice_audience } = proxy.useDict("portal_notice_type", "portal_notice_priority", "portal_notice_status", "portal_notice_display", "portal_notice_audience")

const noticeList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const dateRange = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    title: null,
    noticeType: null,
    priorityLevel: null,
    status: null,
    displayMode: null
  },
  rules: {
    title: [
      { required: true, message: "公告标题不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "公告内容不能为空", trigger: "blur" }
    ],
    noticeType: [
      { required: true, message: "请选择公告类型", trigger: "change" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询公告列表 */
function getList() {
  loading.value = true
  listNotice(proxy.addDateRange(queryParams.value, dateRange.value)).then(response => {
    noticeList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

/** 表单重置 */
function reset() {
  form.value = {
    noticeId: null,
    title: null,
    content: null,
    noticeType: 'notice',
    priorityLevel: 'normal',
    displayMode: 'list',
    isTop: '0',
    isSticky: '0',
    status: '0',
    publishTime: null,
    startTime: null,
    endTime: null,
    sortOrder: 0,
    viewCount: 0,
    clickCount: 0,
    requireConfirm: '0',
    confirmText: '',
    popupStyle: 'default',
    targetAudience: 'all',
    remark: null
  }
  proxy.resetForm("noticeRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = []
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.noticeId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加公告"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _noticeId = row.noticeId || ids.value
  getNotice(_noticeId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改公告"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["noticeRef"].validate(valid => {
    if (valid) {
      if (form.value.noticeId != null) {
        updateNotice(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addNotice(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _noticeIds = row.noticeId || ids.value
  proxy.$modal.confirm('是否确认删除公告编号为"' + _noticeIds + '"的数据项？').then(function() {
    return delNotice(_noticeIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 发布按钮操作 */
function handlePublish(row) {
  const _noticeId = row.noticeId
  proxy.$modal.confirm('确认要发布该公告吗？').then(function() {
    return publishNotice({ noticeId: _noticeId })
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("发布成功")
  }).catch(() => {})
}

/** 停用按钮操作 */
function handleOffline(row) {
  const _noticeId = row.noticeId
  proxy.$modal.confirm('确认要停用该公告吗？').then(function() {
    return offlineNotice({ noticeId: _noticeId })
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("停用成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('portal/notice/notice/export', {
    ...queryParams.value
  }, `notice_${new Date().getTime()}.xlsx`)
}

getList()
</script>

<style scoped>
:deep(.el-radio-group) {
  flex-wrap: nowrap;
}
</style>
