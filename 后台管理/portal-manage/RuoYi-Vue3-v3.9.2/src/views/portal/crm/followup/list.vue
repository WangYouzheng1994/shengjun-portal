<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="客户姓名" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户姓名"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="跟进方式" prop="followType">
        <el-select v-model="queryParams.followType" placeholder="请选择方式" clearable style="width: 150px">
          <el-option
            v-for="dict in portal_follow_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="接触感受" prop="contactFeeling">
        <el-select v-model="queryParams.contactFeeling" placeholder="请选择感受" clearable style="width: 150px">
          <el-option
            v-for="dict in portal_contact_feeling"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="跟进时间" prop="dateRange">
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
          v-hasPermi="['portal:crm:followup:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['portal:crm:followup:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="followUpList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="跟进ID" align="center" prop="followId" width="80" />
      <el-table-column label="客户姓名" align="center" prop="customerName" min-width="100" />
      <el-table-column label="跟进方式" align="center" prop="followType" width="100">
        <template #default="scope">
          <dict-tag :options="portal_follow_type" :value="scope.row.followType" />
        </template>
      </el-table-column>
      <el-table-column label="跟进内容" align="center" prop="followContent" min-width="250" :show-overflow-tooltip="true" />
      <el-table-column label="跟进结果" align="center" prop="followResult" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="下一步计划" align="center" prop="nextPlan" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="下次联系时间" align="center" prop="nextContactTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.nextContactTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="接触感受" align="center" prop="contactFeeling" width="100">
        <template #default="scope">
          <dict-tag :options="portal_contact_feeling" :value="scope.row.contactFeeling" />
        </template>
      </el-table-column>
      <el-table-column label="跟进人" align="center" prop="followUserName" width="90" />
      <el-table-column label="跟进时间" align="center" prop="createTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['portal:crm:followup:edit']">修改</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['portal:crm:followup:remove']">删除</el-button>
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

    <!-- 添加或修改跟进记录对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body destroy-on-close>
      <el-form ref="followUpRef" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="关联客户" prop="customerId">
              <el-select v-model="form.customerId" placeholder="请选择客户" filterable style="width: 100%" @change="handleCustomerChange">
                <el-option
                  v-for="customer in customerList"
                  :key="customer.customerId"
                  :label="customer.customerName + ' - ' + (customer.phone || '无电话')"
                  :value="customer.customerId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="跟进方式" prop="followType">
              <el-select v-model="form.followType" placeholder="请选择跟进方式" style="width: 100%">
                <el-option
                  v-for="dict in portal_follow_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="接触感受" prop="contactFeeling">
              <el-select v-model="form.contactFeeling" placeholder="请选择感受" style="width: 100%">
                <el-option
                  v-for="dict in portal_contact_feeling"
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
            <el-form-item label="跟进内容" prop="followContent">
              <el-input v-model="form.followContent" type="textarea" :rows="4" placeholder="请输入跟进内容" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="跟进结果" prop="followResult">
              <el-input v-model="form.followResult" type="textarea" :rows="2" placeholder="请输入跟进结果（可选）" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="下一步计划" prop="nextPlan">
              <el-input v-model="form.nextPlan" type="textarea" :rows="2" placeholder="请输入下一步计划（可选）" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="下次联系时间" prop="nextContactTime">
              <el-date-picker
                v-model="form.nextContactTime"
                type="datetime"
                placeholder="选择日期时间"
                value-format="YYYY-MM-DD HH:mm:ss"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注信息（可选）" />
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

<script setup name="FollowUpList">
import { listFollowUp, getFollowUp, delFollowUp, addFollowUp, updateFollowUp } from "@/api/portal/crm/followup"
import { listAllCustomer } from "@/api/portal/crm/customer"
import useUserStore from "@/store/modules/user"

const { proxy } = getCurrentInstance()
const userStore = useUserStore()
const { portal_follow_type, portal_contact_feeling } = proxy.useDict("portal_follow_type", "portal_contact_feeling")

const followUpList = ref([])
const customerList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const dateRange = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    customerName: null,
    followType: null,
    contactFeeling: null
  },
  rules: {
    customerId: [
      { required: true, message: "关联客户不能为空", trigger: "blur" }
    ],
    followContent: [
      { required: true, message: "跟进内容不能为空", trigger: "blur" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询跟进记录列表 */
function getList() {
  loading.value = true
  listFollowUp(proxy.addDateRange(queryParams.value, dateRange.value)).then(response => {
    followUpList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 加载客户列表 */
function loadCustomerList() {
  listAllCustomer().then(response => {
    customerList.value = response.rows || []
  })
}

/** 客户选择变化 */
function handleCustomerChange(customerId) {
  const customer = customerList.value.find(item => item.customerId === customerId)
  if (customer) {
    form.value.customerName = customer.customerName
  }
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

/** 表单重置 */
function reset() {
  form.value = {
    followId: null,
    customerId: null,
    customerName: null,
    messageId: null,
    followType: "0",
    followContent: null,
    followResult: null,
    nextPlan: null,
    nextContactTime: null,
    contactFeeling: "0",
    remark: null
  }
  proxy.resetForm("followUpRef")
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
  ids.value = selection.map(item => item.followId)
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加跟进记录"
  // 加载客户列表
  loadCustomerList()
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _followId = row.followId || ids.value[0]
  getFollowUp(_followId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改跟进记录"
    loadCustomerList()
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["followUpRef"].validate(valid => {
    if (valid) {
      if (form.value.followId != null) {
        updateFollowUp(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        }).catch(error => {
          console.error("修改跟进记录失败:", error)
          proxy.$modal.msgError("修改失败：" + (error.message || "请检查网络或刷新重试"))
        })
      } else {
        form.value.followUserId = userStore.id
        form.value.followUserName = userStore.name
        addFollowUp(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        }).catch(error => {
          console.error("新增跟进记录失败:", error)
          proxy.$modal.msgError("新增失败：" + (error.message || "请检查网络或刷新重试"))
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _followIds = row.followId || ids.value
  proxy.$modal.confirm('是否确认删除跟进记录编号为"' + _followIds + '"的数据项？').then(function() {
    return delFollowUp(_followIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

getList()
</script>
