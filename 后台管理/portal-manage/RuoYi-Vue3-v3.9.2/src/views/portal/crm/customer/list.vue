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
      <el-form-item label="手机号码" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号码"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入公司名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 150px">
          <el-option
            v-for="dict in portal_customer_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户等级" prop="level">
        <el-select v-model="queryParams.level" placeholder="请选择等级" clearable style="width: 150px">
          <el-option
            v-for="dict in portal_customer_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['portal:crm:customer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['portal:crm:customer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['portal:crm:customer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['portal:crm:customer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="customerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="客户ID" align="center" prop="customerId" width="80" />
      <el-table-column label="客户姓名" align="center" prop="customerName" min-width="100" />
      <el-table-column label="手机号码" align="center" prop="phone" width="120" />
      <el-table-column label="电子邮箱" align="center" prop="email" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="公司名称" align="center" prop="companyName" min-width="150" :show-overflow-tooltip="true" />
      <el-table-column label="职位" align="center" prop="position" width="100" />
      <el-table-column label="来源" align="center" prop="source" width="90">
        <template #default="scope">
          <dict-tag :options="portal_customer_source" :value="scope.row.source" />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="90">
        <template #default="scope">
          <dict-tag :options="portal_customer_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="等级" align="center" prop="level" width="80">
        <template #default="scope">
          <dict-tag :options="portal_customer_level" :value="scope.row.level" />
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="ownerName" width="90" />
      <el-table-column label="最后联系时间" align="center" prop="lastContactTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.lastContactTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)">详情</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['portal:crm:customer:edit']">修改</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['portal:crm:customer:remove']">删除</el-button>
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

    <!-- 添加或修改客户对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body destroy-on-close>
      <el-form ref="customerRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户姓名" prop="customerName">
              <el-input v-model="form.customerName" placeholder="请输入客户姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="电子邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入电子邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
                <el-option label="未知" value="0" />
                <el-option label="男" value="1" />
                <el-option label="女" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司名称" prop="companyName">
              <el-input v-model="form.companyName" placeholder="请输入公司名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职位" prop="position">
              <el-input v-model="form.position" placeholder="请输入职位" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户来源" prop="source">
              <el-select v-model="form.source" placeholder="请选择来源" style="width: 100%">
                <el-option
                  v-for="dict in portal_customer_source"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                <el-option
                  v-for="dict in portal_customer_status"
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
            <el-form-item label="客户等级" prop="level">
              <el-select v-model="form.level" placeholder="请选择等级" style="width: 100%">
                <el-option
                  v-for="dict in portal_customer_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注信息" />
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

    <!-- 客户详情对话框（包含跟进记录） -->
    <el-dialog title="客户详情" v-model="detailOpen" width="900px" append-to-body destroy-on-close>
      <el-descriptions :column="3" border>
        <el-descriptions-item label="客户姓名">{{ detailForm.customerName }}</el-descriptions-item>
        <el-descriptions-item label="手机号码">{{ detailForm.phone }}</el-descriptions-item>
        <el-descriptions-item label="电子邮箱">{{ detailForm.email }}</el-descriptions-item>
        <el-descriptions-item label="公司名称">{{ detailForm.companyName }}</el-descriptions-item>
        <el-descriptions-item label="职位">{{ detailForm.position }}</el-descriptions-item>
        <el-descriptions-item label="性别">
          <dict-tag :options="{ '0': '未知', '1': '男', '2': '女' }" :value="detailForm.gender" />
        </el-descriptions-item>
        <el-descriptions-item label="来源">
          <dict-tag :options="portal_customer_source" :value="detailForm.source" />
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="portal_customer_status" :value="detailForm.status" />
        </el-descriptions-item>
        <el-descriptions-item label="等级">
          <dict-tag :options="portal_customer_level" :value="detailForm.level" />
        </el-descriptions-item>
        <el-descriptions-item label="负责人">{{ detailForm.ownerName }}</el-descriptions-item>
        <el-descriptions-item label="最后联系时间">{{ parseTime(detailForm.lastContactTime) }}</el-descriptions-item>
        <el-descriptions-item label="下次联系时间">{{ parseTime(detailForm.nextContactTime) }}</el-descriptions-item>
        <el-descriptions-item label="地址" :span="3">{{ detailForm.address }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="3">{{ detailForm.remark }}</el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">跟进记录</el-divider>
      <el-table :data="followUpList" border size="small">
        <el-table-column label="跟进方式" align="center" prop="followType" width="100">
          <template #default="scope">
            <dict-tag :options="portal_follow_type" :value="scope.row.followType" />
          </template>
        </el-table-column>
        <el-table-column label="跟进内容" align="center" prop="followContent" min-width="200" :show-overflow-tooltip="true" />
        <el-table-column label="跟进结果" align="center" prop="followResult" width="150" :show-overflow-tooltip="true" />
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
      </el-table>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="detailOpen = false">关 闭</el-button>
          <el-button type="primary" @click="handleAddFollowUp(detailForm)">添加跟进</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="CustomerList">
import { listCustomer, getCustomer, delCustomer, addCustomer, updateCustomer, exportCustomer } from "@/api/portal/crm/customer"
import { listFollowUpByCustomer, addFollowUp } from "@/api/portal/crm/followup"
import useUserStore from "@/store/modules/user"

const { proxy } = getCurrentInstance()
const userStore = useUserStore()
const { portal_customer_source, portal_customer_status, portal_customer_level, portal_follow_type, portal_contact_feeling } = proxy.useDict("portal_customer_source", "portal_customer_status", "portal_customer_level", "portal_follow_type", "portal_contact_feeling")

const customerList = ref([])
const followUpList = ref([])
const open = ref(false)
const detailOpen = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  detailForm: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    customerName: null,
    phone: null,
    companyName: null,
    status: null,
    level: null
  },
  rules: {
    customerName: [
      { required: true, message: "客户姓名不能为空", trigger: "blur" }
    ],
    phone: [
      { pattern: /^1[3-9]\d{9}$/, message: "请输入正确的手机号码", trigger: "blur" }
    ]
  }
})

const { queryParams, form, rules, detailForm } = toRefs(data)

/** 查询客户列表 */
function getList() {
  loading.value = true
  listCustomer(queryParams.value).then(response => {
    customerList.value = response.rows
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
    customerId: null,
    customerName: null,
    phone: null,
    email: null,
    companyName: null,
    position: null,
    gender: "0",
    source: "0",
    status: "0",
    level: "0",
    address: null,
    remark: null
  }
  proxy.resetForm("customerRef")
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
  ids.value = selection.map(item => item.customerId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加客户"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _customerId = row.customerId || ids.value
  getCustomer(_customerId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改客户"
  })
}

/** 查看详情 */
function handleDetail(row) {
  detailForm.value = row || {}
  detailOpen.value = true
  followUpList.value = []

  if (row && row.customerId) {
    const queryParams = { pageNum: 1, pageSize: 10 }
    listFollowUpByCustomer(row.customerId, queryParams).then(response => {
      followUpList.value = response.rows
    }).catch(error => {
      console.error("加载跟进记录失败:", error)
      proxy.$modal.msgError("加载跟进记录失败")
    })
  }
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["customerRef"].validate(valid => {
    if (valid) {
      if (form.value.customerId != null) {
        updateCustomer(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCustomer(form.value).then(response => {
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
  const _customerIds = row.customerId || ids.value
  proxy.$modal.confirm('是否确认删除客户编号为"' + _customerIds + '"的数据项？').then(function() {
    return delCustomer(_customerIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('portal/crm/customer/export', {
    ...queryParams.value
  }, `customer_${new Date().getTime()}.xlsx`)
}

/** 添加跟进记录 */
function handleAddFollowUp(customer) {
  proxy.$prompt('请输入跟进内容', '添加跟进', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /\S+/,
    inputErrorMessage: '跟进内容不能为空'
  }).then(({ value }) => {
    const followUpData = {
      customerId: customer.customerId,
      followType: "0",
      followContent: value,
      followUserId: userStore.id,
      followUserName: userStore.name
    }
    addFollowUp(followUpData).then(response => {
      proxy.$modal.msgSuccess("添加成功")
      listFollowUpByCustomer(customer.customerId, { pageNum: 1, pageSize: 10 }).then(res => {
        followUpList.value = res.rows
      })
    }).catch(error => {
      proxy.$modal.msgError("添加失败：" + (error.message || "未知错误"))
    })
  }).catch(() => {})
}

getList()
</script>
