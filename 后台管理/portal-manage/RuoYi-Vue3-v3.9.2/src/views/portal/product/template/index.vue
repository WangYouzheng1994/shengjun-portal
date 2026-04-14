<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="模板名称" prop="templateName">
        <el-input
          v-model="queryParams.templateName"
          placeholder="请输入模板名称"
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
          v-hasPermi="['portal:product:attrTemplate:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['portal:product:attrTemplate:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['portal:product:attrTemplate:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="templateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="templateId" width="80" />
      <el-table-column label="模板名称" align="center" prop="templateName" :show-overflow-tooltip="true" />
      <el-table-column label="模板编码" align="center" prop="templateCode" width="120" />
      <el-table-column label="是否默认" align="center" prop="isDefault" width="90">
        <template #default="scope">
          <dict-tag :options="sys_yes_no" :value="scope.row.isDefault" />
        </template>
      </el-table-column>
      <el-table-column label="属性数量" align="center" width="90">
        <template #default="scope">
          <el-tag size="small">{{ scope.row.attrCount || 0 }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="description" :show-overflow-tooltip="true" />
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template #default="scope">
          <el-button link type="primary" icon="Setting" @click="handleAttrDef(scope.row)" v-hasPermi="['portal:product:attrTemplate:edit']">属性定义</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['portal:product:attrTemplate:edit']">修改</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['portal:product:attrTemplate:remove']">删除</el-button>
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

    <!-- 添加或修改属性模板对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body destroy-on-close>
      <el-form ref="templateRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="模板名称" prop="templateName">
              <el-input v-model="form.templateName" placeholder="请输入模板名称" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="模板编码" prop="templateCode">
              <el-input v-model="form.templateCode" placeholder="请输入模板编码（英文）" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="是否默认" prop="isDefault">
              <el-radio-group v-model="form.isDefault">
                <el-radio
                  v-for="dict in sys_yes_no"
                  :key="dict.value"
                  :value="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in sys_normal_disable"
                  :key="dict.value"
                  :value="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="描述">
              <el-input v-model="form.description" type="textarea" placeholder="请输入模板描述" :rows="3" maxlength="500" show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" maxlength="200" />
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

    <!-- 属性定义抽屉 -->
    <el-drawer
      v-model="attrDrawerVisible"
      :title="'属性定义 - ' + currentTemplate.templateName"
      direction="rtl"
      size="60%"
      destroy-on-close
    >
      <div class="attr-drawer-content">
        <!-- 属性列表 -->
        <div class="attr-list-header">
          <el-button type="primary" icon="Plus" @click="handleAddAttr">添加属性</el-button>
        </div>

        <el-table :data="attrDefs" border stripe style="width: 100%">
          <el-table-column label="排序" align="center" width="70">
            <template #default="scope">
              <el-input-number v-model="scope.row.sortOrder" :min="0" :max="999" size="small" controls-position="right" />
            </template>
          </el-table-column>
          <el-table-column label="属性名称" align="center" min-width="120">
            <template #default="scope">
              <el-input v-model="scope.row.attrName" placeholder="属性名称" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="属性编码" align="center" width="120">
            <template #default="scope">
              <el-input v-model="scope.row.attrCode" placeholder="英文编码" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="控件类型" align="center" width="120">
            <template #default="scope">
              <el-select v-model="scope.row.inputType" placeholder="选择类型" size="small">
                <el-option label="文本输入" value="input" />
                <el-option label="数字输入" value="number" />
                <el-option label="单选" value="radio" />
                <el-option label="多选" value="checkbox" />
                <el-option label="下拉选择" value="select" />
                <el-option label="日期" value="date" />
                <el-option label="开关" value="switch" />
                <el-option label="富文本" value="textarea" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否必填" align="center" width="85">
            <template #default="scope">
              <el-switch v-model="scope.row.isRequired" active-value="1" inactive-value="0" />
            </template>
          </el-table-column>
          <el-table-column label="选项值" align="center" min-width="180">
            <template #default="scope">
              <el-input
                v-if="['radio', 'checkbox', 'select'].includes(scope.row.inputType)"
                v-model="scope.row.options"
                placeholder="多个选项用逗号分隔"
                size="small"
              />
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="默认值" align="center" width="120">
            <template #default="scope">
              <el-input v-model="scope.row.defaultValue" placeholder="默认值" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="80" fixed="right">
            <template #default="scope">
              <el-button link type="danger" icon="Delete" @click="handleRemoveAttr(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <div class="drawer-footer">
          <el-button @click="attrDrawerVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleSubmitAttr">保存属性</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script setup name="ProductAttrTemplate">
import { listTemplate, getTemplate, delTemplate, addTemplate, updateTemplate } from "@/api/portal/productAttrTemplate"

const { proxy } = getCurrentInstance()
const { sys_normal_disable, sys_yes_no } = proxy.useDict("sys_normal_disable", "sys_yes_no")

const templateList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

/** 属性定义相关 */
const attrDrawerVisible = ref(false)
const currentTemplate = ref({})
const attrDefs = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    templateName: null,
    status: null
  },
  rules: {
    templateName: [
      { required: true, message: "模板名称不能为空", trigger: "blur" }
    ],
    templateCode: [
      { required: true, message: "模板编码不能为空", trigger: "blur" }
    ],
    isDefault: [
      { required: true, message: "请选择是否默认", trigger: "change" }
    ],
    status: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询属性模板列表 */
function getList() {
  loading.value = true
  listTemplate(queryParams.value).then(response => {
    templateList.value = response.rows
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
    templateId: null,
    templateName: null,
    templateCode: null,
    isDefault: "0",
    description: null,
    status: "0",
    remark: null
  }
  proxy.resetForm("templateRef")
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
  ids.value = selection.map(item => item.templateId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加属性模板"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _templateId = row.templateId || ids.value
  getTemplate(_templateId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改属性模板"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["templateRef"].validate(valid => {
    if (valid) {
      if (form.value.templateId != null) {
        updateTemplate(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addTemplate(form.value).then(response => {
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
  const _templateIds = row.templateId || ids.value
  proxy.$modal.confirm('是否确认删除属性模板编号为"' + _templateIds + '"的数据项？').then(function() {
    return delTemplate(_templateIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 打开属性定义抽屉 */
function handleAttrDef(row) {
  currentTemplate.value = { ...row }
  getTemplate(row.templateId).then(response => {
    attrDefs.value = response.data.attrDefs || []
    if (attrDefs.value.length === 0) {
      attrDefs.value = [createEmptyAttr()]
    }
    attrDrawerVisible.value = true
  })
}

/** 创建空的属性对象 */
function createEmptyAttr() {
  return {
    attrDefId: null,
    attrName: null,
    attrCode: null,
    inputType: 'input',
    isRequired: '0',
    options: null,
    defaultValue: null,
    sortOrder: 0
  }
}

/** 添加属性 */
function handleAddAttr() {
  attrDefs.value.push(createEmptyAttr())
}

/** 删除属性 */
function handleRemoveAttr(index) {
  attrDefs.value.splice(index, 1)
}

/** 保存属性定义 */
function handleSubmitAttr() {
  /** 验证属性数据 */
  for (let i = 0; i < attrDefs.value.length; i++) {
    const attr = attrDefs.value[i]
    if (!attr.attrName) {
      proxy.$modal.msgError(`第${i + 1}行：属性名称不能为空`)
      return
    }
    if (!attr.attrCode) {
      proxy.$modal.msgError(`第${i + 1}行：属性编码不能为空`)
      return
    }
    if (['radio', 'checkbox', 'select'].includes(attr.inputType) && !attr.options) {
      proxy.$modal.msgError(`第${i + 1}行：该控件类型需要填写选项值`)
      return
    }
  }

  const submitData = {
    ...currentTemplate.value,
    attrDefs: attrDefs.value
  }

  if (submitData.templateId != null) {
    updateTemplate(submitData).then(response => {
      proxy.$modal.msgSuccess("保存成功")
      attrDrawerVisible.value = false
      getList()
    })
  } else {
    addTemplate(submitData).then(response => {
      proxy.$modal.msgSuccess("保存成功")
      attrDrawerVisible.value = false
      getList()
    })
  }
}

getList()
</script>

<style scoped>
.attr-drawer-content {
  padding: 16px;
  height: calc(100vh - 120px);
  display: flex;
  flex-direction: column;
}

.attr-list-header {
  margin-bottom: 16px;
}

.drawer-footer {
  margin-top: 20px;
  text-align: right;
}
</style>
