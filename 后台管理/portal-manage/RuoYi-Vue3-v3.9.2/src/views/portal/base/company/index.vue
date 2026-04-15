<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="公司名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入公司名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">
          <el-option
            v-for="dict in sys_normal_disable"
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
          v-hasPermi="['portal:base:company:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['portal:base:company:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['portal:base:company:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="companyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="infoId" width="80" />
      <el-table-column label="公司名称" align="center" prop="companyName" :show-overflow-tooltip="true" />
      <el-table-column label="公司Logo" align="center" width="100">
        <template #default="scope">
          <image-preview v-if="scope.row.companyLogo" :src="scope.row.companyLogo" :width="80" :height="40" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="联系电话" align="center" prop="phone" width="130" />
      <el-table-column label="电子邮箱" align="center" prop="email" :show-overflow-tooltip="true" width="180" />
      <el-table-column label="地址" align="center" prop="address" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['portal:base:company:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['portal:base:company:remove']">删除</el-button>
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

    <!-- 添加或修改企业信息对话框 -->
    <el-dialog :title="title" v-model="open" width="1000px" append-to-body destroy-on-close>
      <el-tabs v-model="activeTab">
        <!-- 基本信息 Tab -->
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="companyRef" :model="form" :rules="rules" label-width="120px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="公司名称" prop="companyName">
                  <el-input v-model="form.companyName" placeholder="请输入公司名称" maxlength="100" show-word-limit />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公司Logo">
                  <image-upload v-model="form.companyLogo" :limit="1" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="12">
                <el-form-item label="联系电话">
                  <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="20" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="电子邮箱">
                  <el-input v-model="form.email" placeholder="请输入电子邮箱" maxlength="50" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="12">
                <el-form-item label="传真号码">
                  <el-input v-model="form.fax" placeholder="请输入传真号码" maxlength="20" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="QQ号">
                  <el-input v-model="form.qq" placeholder="请输入QQ号" maxlength="20" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="24">
                <el-form-item label="公司地址">
                  <el-input v-model="form.address" placeholder="请输入公司详细地址" maxlength="200" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="24">
                <el-form-item label="ICP备案号">
                  <el-input v-model="form.icpNumber" placeholder="请输入ICP备案号" maxlength="50" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="12">
                <el-form-item label="状态">
                  <el-radio-group v-model="form.status">
                    <el-radio
                      v-for="dict in sys_normal_disable"
                      :key="dict.value"
                      :value="dict.value"
                    >{{ dict.label }}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="备注">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 社交媒体 Tab -->
        <el-tab-pane label="社交媒体" name="social">
          <el-form ref="socialRef" :model="form" label-width="120px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="微信公众号">
                  <el-input v-model="form.wechat" placeholder="请输入微信公众号或二维码链接" maxlength="200" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="新浪微博">
                  <el-input v-model="form.weibo" placeholder="请输入新浪微博地址或账号" maxlength="200" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="12">
                <el-form-item label="抖音账号">
                  <el-input v-model="form.douyin" placeholder="请输入抖音账号或分享链接" maxlength="200" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="">
                  <div class="tip-text">
                    <p>提示：所有社交媒体字段均为可选，如果不配置，前端门户网站将不会显示对应的入口。</p>
                    <p>支持填写账号名、链接地址或二维码图片地址。</p>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 公司简介 Tab（富文本） -->
        <el-tab-pane label="公司简介" name="introduction">
          <el-form ref="introRef" :model="form" label-width="120px">
            <el-row>
              <el-col :span="24">
                <el-form-item label="公司简介" prop="introduction">
                  <editor v-model="form.introduction" :min-height="400" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="24">
                <el-form-item label="营业执照">
                  <file-upload v-model="form.businessLicense" />
                  <div class="form-tip">支持上传营业执照扫描件或照片</div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

        <!-- 办公点管理 Tab -->
        <el-tab-pane label="办公点管理" name="office">
          <div class="office-header">
            <el-button
              type="primary"
              plain
              icon="Plus"
              @click="handleAddOffice"
              v-hasPermi="['portal:base:company:add']"
            >新增办公点</el-button>
            <span class="office-tip">管理企业的各个办公地点（如总部、分公司、仓库等）</span>
          </div>

          <el-table v-loading="officeLoading" :data="officeList" border style="width: 100%; margin-top: 10px">
            <el-table-column label="办公点名称" align="center" prop="locationName" min-width="120" />
            <el-table-column label="类型" align="center" prop="locationType" width="90">
              <template #default="scope">
                <dict-tag :options="portal_office_type" :value="scope.row.locationType" />
              </template>
            </el-table-column>
            <el-table-column label="地址" align="center" prop="address" min-width="180" :show-overflow-tooltip="true" />
            <el-table-column label="联系人" align="center" prop="contactPerson" width="100" />
            <el-table-column label="联系电话" align="center" prop="contactPhone" width="120" />
            <el-table-column label="状态" align="center" prop="status" width="80">
              <template #default="scope">
                <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
              <template #default="scope">
                <el-button link type="primary" icon="Edit" @click="handleUpdateOffice(scope.row)" v-hasPermi="['portal:base:company:edit']">修改</el-button>
                <el-button link type="danger" icon="Delete" @click="handleDeleteOffice(scope.row)" v-hasPermi="['portal:base:company:remove']">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-empty v-if="!officeLoading && officeList.length === 0" description="暂无办公点数据" :image-size="100" />
        </el-tab-pane>
      </el-tabs>

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 办公点编辑对话框 -->
    <el-dialog :title="officeDialogTitle" v-model="officeDialogOpen" width="900px" append-to-body destroy-on-close>
      <el-form ref="officeRef" :model="officeForm" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="办公点名称" prop="locationName">
              <el-input v-model="officeForm.locationName" placeholder="如：北京总部、上海分公司" maxlength="100" show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="办公点类型" prop="locationType">
              <el-select v-model="officeForm.locationType" placeholder="请选择类型">
                <el-option
                  v-for="dict in portal_office_type"
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
            <el-form-item label="详细地址" prop="address">
              <el-input v-model="officeForm.address" placeholder="请输入详细地址" maxlength="500" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="地图定位">
              <MapPicker
                v-model:longitude="officeForm.longitude"
                v-model:latitude="officeForm.latitude"
                :address="officeForm.address"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">联系信息</el-divider>

        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人">
              <el-input v-model="officeForm.contactPerson" placeholder="请输入联系人姓名" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话">
              <el-input v-model="officeForm.contactPhone" placeholder="请输入联系电话" maxlength="20" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱">
              <el-input v-model="officeForm.contactEmail" placeholder="请输入电子邮箱" maxlength="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="微信号">
              <el-input v-model="officeForm.wechat" placeholder="请输入微信号" maxlength="100" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="飞书号">
              <el-input v-model="officeForm.feishu" placeholder="请输入飞书号" maxlength="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序">
              <el-input-number v-model="officeForm.sortOrder" :min="0" :max="9999" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="officeForm.status">
                <el-radio
                  v-for="dict in sys_normal_disable"
                  :key="dict.value"
                  :value="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input v-model="officeForm.remark" type="textarea" placeholder="请输入备注信息" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitOfficeForm">确 定</el-button>
          <el-button @click="officeDialogOpen = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="CompanyInfo">
import { listCompany, getCompany, delCompany, addCompany, updateCompany } from "@/api/portal/company"
import { listOfficeLocation, getOfficeLocation, addOfficeLocation, updateOfficeLocation, delOfficeLocation } from "@/api/portal/officeLocation"

const { proxy } = getCurrentInstance()
const { sys_normal_disable, portal_office_type } = proxy.useDict("sys_normal_disable", "portal_office_type")

const companyList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const activeTab = ref("basic")

// 办公点相关状态
const officeList = ref([])
const officeDialogOpen = ref(false)
const officeDialogTitle = ref("")
const officeForm = ref({})
const officeLoading = ref(false)

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    companyName: undefined,
    status: undefined
  },
  rules: {
    companyName: [{ required: true, message: "公司名称不能为空", trigger: "blur" }]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询企业基础信息列表 */
function getList() {
  loading.value = true
  listCompany(queryParams.value).then(response => {
    companyList.value = response.rows
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
    infoId: undefined,
    companyName: undefined,
    companyLogo: undefined,
    address: undefined,
    phone: undefined,
    email: undefined,
    fax: undefined,
    wechat: undefined,
    weibo: undefined,
    douyin: undefined,
    qq: undefined,
    introduction: undefined,
    businessLicense: undefined,
    icpNumber: undefined,
    status: '0',
    remark: undefined
  }
  activeTab.value = 'basic'
  officeList.value = []
  proxy.resetForm("companyRef")
  proxy.resetForm("socialRef")
  proxy.resetForm("introRef")
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
  ids.value = selection.map(item => item.infoId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加企业信息"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const infoId = row.infoId || ids.value
  getCompany(infoId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改企业信息"
    // 加载办公点数据
    loadOfficeLocations(infoId)
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["companyRef"].validate(valid => {
    if (valid) {
      if (form.value.infoId != undefined) {
        updateCompany(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCompany(form.value).then(response => {
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
  const infoIds = row.infoId || ids.value
  proxy.$modal.confirm('是否确认删除企业信息编号为"' + infoIds + '"的数据项？').then(function() {
    return delCompany(infoIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

// ==================== 办公点管理方法 ====================

/**
 * 加载办公点列表
 * @param infoId 企业信息ID
 */
function loadOfficeLocations(infoId) {
  if (!infoId) {
    return
  }
  officeLoading.value = true
  listOfficeLocation(infoId).then(response => {
    officeList.value = response.data || []
    officeLoading.value = false
  }).catch(() => {
    officeLoading.value = false
  })
}

/** 新增办公点按钮操作 */
function handleAddOffice() {
  resetOfficeForm()
  officeForm.value.infoId = form.value.infoId
  officeDialogOpen.value = true
  officeDialogTitle.value = "新增办公点"
}

/** 修改办公点按钮操作 */
function handleUpdateOffice(row) {
  resetOfficeForm()
  getOfficeLocation(row.locationId).then(response => {
    officeForm.value = response.data
    officeDialogOpen.value = true
    officeDialogTitle.value = "修改办公点"
  })
}

/** 重置办公点表单 */
function resetOfficeForm() {
  officeForm.value = {
    locationId: undefined,
    infoId: undefined,
    locationName: undefined,
    locationType: '0',
    province: undefined,
    city: undefined,
    district: undefined,
    address: undefined,
    longitude: undefined,
    latitude: undefined,
    contactPerson: undefined,
    contactPhone: undefined,
    contactEmail: undefined,
    wechat: undefined,
    feishu: undefined,
    sortOrder: 0,
    status: '0',
    remark: undefined
  }
}

/** 提交办公点表单 */
function submitOfficeForm() {
  if (!officeForm.value.locationName) {
    proxy.$modal.msgWarning("请输入办公点名称")
    return
  }

  if (officeForm.value.locationId != undefined) {
    updateOfficeLocation(officeForm.value).then(response => {
      proxy.$modal.msgSuccess("修改成功")
      officeDialogOpen.value = false
      loadOfficeLocations(form.value.infoId)
    })
  } else {
    addOfficeLocation(officeForm.value).then(response => {
      proxy.$modal.msgSuccess("新增成功")
      officeDialogOpen.value = false
      loadOfficeLocations(form.value.infoId)
    })
  }
}

/** 删除办公点按钮操作 */
function handleDeleteOffice(row) {
  const locationIds = row.locationId
  proxy.$modal.confirm('是否确认删除办公点"' + row.locationName + '"？').then(function() {
    return delOfficeLocation(locationIds)
  }).then(() => {
    loadOfficeLocations(form.value.infoId)
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/**
 * 监听Tab切换，懒加载办公点数据
 */
watch(activeTab, async (newVal) => {
  if (newVal === 'office' && form.value.infoId && officeList.value.length === 0) {
    loadOfficeLocations(form.value.infoId)
  }
})

getList()
</script>

<style scoped>
.tip-text {
  color: #909399;
  font-size: 13px;
  line-height: 1.8;
  padding: 8px 12px;
  background-color: #f4f4f5;
  border-radius: 4px;
}

.form-tip {
  color: #909399;
  font-size: 12px;
  margin-top: 4px;
}

.office-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 10px;
}

.office-tip {
  color: #909399;
  font-size: 13px;
}
</style>
