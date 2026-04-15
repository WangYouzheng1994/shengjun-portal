<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="荣誉标题" prop="honorTitle">
        <el-input
          v-model="queryParams.honorTitle"
          placeholder="请输入荣誉标题"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="荣誉类型" prop="honorType">
        <el-select v-model="queryParams.honorType" placeholder="请选择荣誉类型" clearable style="width: 200px">
          <el-option
            v-for="dict in portal_honor_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['portal:base:honor:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['portal:base:honor:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="honorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="honorId" width="80" />
      <el-table-column label="荣誉图片" align="center" width="120">
        <template #default="scope">
          <image-preview v-if="scope.row.honorImage" :src="scope.row.honorImage" :width="100" :height="70" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="荣誉标题" align="center" prop="honorTitle" :show-overflow-tooltip="true" />
      <el-table-column label="授予机构" align="center" prop="awardOrg" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="获得时间" align="center" prop="awardDate" width="120">
        <template #default="scope">
          <span>{{ parseTime(scope.row.awardDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="荣誉类型" align="center" prop="honorType" width="100">
        <template #default="scope">
          <dict-tag :options="portal_honor_type" :value="scope.row.honorType" />
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sortOrder" width="70" />
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['portal:base:honor:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['portal:base:honor:remove']">删除</el-button>
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

    <!-- 添加或修改企业荣誉墙对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body destroy-on-close>
      <el-form ref="honorRef" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="荣誉标题" prop="honorTitle">
              <el-input v-model="form.honorTitle" placeholder="请输入荣誉标题（如：ISO9001质量管理体系认证）" maxlength="100" show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="荣誉图片" prop="honorImage">
              <image-upload v-model="form.honorImage" :limit="1" />
              <div class="form-tip">建议上传清晰的荣誉证书扫描件或照片，支持JPG、PNG格式，大小不超过5MB</div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="荣誉说明" prop="honorDescription">
              <el-input
                v-model="form.honorDescription"
                type="textarea"
                placeholder="请输入荣誉的详细说明，如认证范围、有效期等信息"
                :rows="3"
                maxlength="500"
                show-word-limit
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="授予机构" prop="awardOrg">
              <el-input v-model="form.awardOrg" placeholder="如：中国国家认证认可监督管理委员会" maxlength="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="获得时间" prop="awardDate">
              <el-date-picker
                v-model="form.awardDate"
                type="date"
                placeholder="选择获得时间"
                value-format="YYYY-MM-DD"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="荣誉类型" prop="honorType">
              <el-select v-model="form.honorType" placeholder="请选择荣誉类型" style="width: 100%">
                <el-option
                  v-for="dict in portal_honor_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sortOrder">
              <el-input-number v-model="form.sortOrder" :min="0" :max="9999" controls-position="right" style="width: 100%" />
              <div class="form-tip-inline">数值越小越靠前</div>
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

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="CompanyHonor">
import { listCompanyHonor, getCompanyHonor, delCompanyHonor, addCompanyHonor, updateCompanyHonor } from "@/api/portal/companyHonor"

const { proxy } = getCurrentInstance()
const { portal_honor_type, sys_normal_disable } = proxy.useDict("portal_honor_type", "sys_normal_disable")

const honorList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    honorTitle: undefined,
    honorType: undefined,
    status: undefined
  },
  rules: {
    honorTitle: [{ required: true, message: "荣誉标题不能为空", trigger: "blur" }],
    honorImage: [{ required: true, message: "荣誉图片不能为空", trigger: "change" }],
    honorType: [{ required: true, message: "请选择荣誉类型", trigger: "change" }]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询企业荣誉墙列表 */
function getList() {
  loading.value = true
  listCompanyHonor(queryParams.value).then(response => {
    honorList.value = response.rows
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
    honorId: undefined,
    honorTitle: undefined,
    honorImage: undefined,
    honorDescription: undefined,
    awardOrg: undefined,
    awardDate: undefined,
    honorType: '0',
    sortOrder: 0,
    status: '0',
    remark: undefined
  }
  proxy.resetForm("honorRef")
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
  ids.value = selection.map(item => item.honorId)
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加企业荣誉"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const honorId = row.honorId || ids.value
  getCompanyHonor(honorId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改企业荣誉"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["honorRef"].validate(valid => {
    if (valid) {
      if (form.value.honorId != undefined) {
        updateCompanyHonor(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCompanyHonor(form.value).then(response => {
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
  const honorIds = row.honorId || ids.value
  proxy.$modal.confirm('是否确认删除企业荣誉编号为"' + honorIds + '"的数据项？').then(function() {
    return delCompanyHonor(honorIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

getList()
</script>

<style scoped>
.form-tip {
  color: #909399;
  font-size: 12px;
  margin-top: 4px;
  line-height: 1.6;
}

.form-tip-inline {
  color: #909399;
  font-size: 12px;
  margin-top: 4px;
}
</style>
