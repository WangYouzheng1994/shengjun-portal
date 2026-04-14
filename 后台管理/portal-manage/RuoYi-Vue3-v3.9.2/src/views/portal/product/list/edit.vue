<template>
  <el-dialog
    :title="dialogTitle"
    v-model="visible"
    width="1000px"
    append-to-body
    destroy-on-close
    :close-on-click-modal="false"
  >
    <el-tabs v-model="activeTab" type="border-card">
      <!-- Tab1: 基本信息 -->
      <el-tab-pane label="基本信息" name="basic">
        <el-form ref="productRef" :model="form" :rules="rules" label-width="110px" class="product-form">
          <el-row>
            <el-col :span="12">
              <el-form-item label="产品编码" prop="productCode">
                <el-input v-model="form.productCode" placeholder="请输入产品编码" maxlength="50" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="产品名称" prop="productName">
                <el-input v-model="form.productName" placeholder="请输入产品名称" maxlength="200" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="所属分类" prop="categoryId">
                <el-tree-select
                  v-model="form.categoryId"
                  :data="categoryTreeOptions"
                  :props="{ value: 'categoryId', label: 'categoryName', children: 'children' }"
                  value-key="categoryId"
                  placeholder="请选择所属分类"
                  check-strictly
                  filterable
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="品牌">
                <el-select v-model="form.brandId" placeholder="请选择品牌" clearable filterable style="width: 100%">
                  <el-option
                    v-for="item in brandOptions"
                    :key="item.brandId"
                    :label="item.brandName"
                    :value="item.brandId"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label="主图" prop="mainImage">
                <image-upload v-model="form.mainImage" :limit="1" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label="缩略图">
                <image-upload v-model="form.thumbnailImage" :limit="1" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label="图片列表">
                <image-upload v-model="form.images" :limit="10" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="视频地址">
                <el-input v-model="form.videoUrl" placeholder="请输入视频地址" maxlength="500" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="副标题">
                <el-input v-model="form.subtitle" placeholder="请输入副标题" maxlength="200" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label="产品摘要">
                <el-input v-model="form.summary" type="textarea" placeholder="请输入产品摘要" :rows="3" maxlength="500" show-word-limit />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label="详细介绍" prop="description">
                <editor v-model="form.description" :min-height="350" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="价格" prop="price">
                <el-input-number v-model="form.price" :precision="2" :step="0.01" :min="0" controls-position="right" style="width: 100%" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="原价">
                <el-input-number v-model="form.originalPrice" :precision="2" :step="0.01" :min="0" controls-position="right" style="width: 100%" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="成本价">
                <el-input-number v-model="form.costPrice" :precision="2" :step="0.01" :min="0" controls-position="right" style="width: 100%" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="计量单位">
                <el-input v-model="form.unit" placeholder="如：个、件、套" maxlength="20" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="重量(kg)">
                <el-input-number v-model="form.weight" :precision="2" :step="0.01" :min="0" controls-position="right" style="width: 100%" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="体积(m³)">
                <el-input-number v-model="form.volume" :precision="3" :step="0.001" :min="0" controls-position="right" style="width: 100%" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label="推荐标签">
                <el-checkbox-group v-model="tagList">
                  <el-checkbox label="isRecommend">推荐</el-checkbox>
                  <el-checkbox label="isNew">新品</el-checkbox>
                  <el-checkbox label="isHot">热销</el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
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
            <el-col :span="8">
              <el-form-item label="排序">
                <el-input-number v-model="form.sortOrder" :min="0" :max="9999" controls-position="right" style="width: 100%" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="发布时间">
                <el-date-picker
                  v-model="form.publishTime"
                  type="datetime"
                  placeholder="选择发布时间"
                  value-format="YYYY-MM-DD HH:mm:ss"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label="备注">
                <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" :rows="2" maxlength="500" show-word-limit />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-tab-pane>

      <!-- Tab2: 扩展属性 -->
      <el-tab-pane label="扩展属性" name="attr">
        <div class="attr-tab-content">
          <el-alert
            v-if="attrDefs.length === 0"
            title="暂无扩展属性，请先在所属分类中配置属性模板"
            type="info"
            :closable="false"
            show-icon
            class="attr-empty-tip"
          />
          <el-form
            v-else
            ref="attrFormRef"
            :model="attrValuesForm"
            label-width="120px"
            class="attr-form"
          >
            <el-row v-for="(attr, index) in attrDefs" :key="index" :gutter="16">
              <el-col :span="12">
                <el-form-item :label="attr.attrName + (attr.isRequired === '1' ? '：' : '')" :prop="'attr_' + attr.attrCode" :rules="attr.isRequired === '1' ? [{ required: true, message: attr.attrName + '不能为空', trigger: 'blur' }] : []">
                  <!-- 文本输入 -->
                  <el-input
                    v-if="attr.inputType === 'input'"
                    v-model="attrValuesForm['attr_' + attr.attrCode]"
                    :placeholder="'请输入' + attr.attrName"
                    :maxlength="attr.maxLength || 200"
                  />
                  <!-- 数字输入 -->
                  <el-input-number
                    v-else-if="attr.inputType === 'number'"
                    v-model="attrValuesForm['attr_' + attr.attrCode]
"
                    :placeholder="'请输入' + attr.attrName"
                    :precision="attr.precision || 0"
                    :step="attr.step || 1"
                    controls-position="right"
                    style="width: 100%"
                  />
                  <!-- 单选 -->
                  <el-radio-group
                    v-else-if="attr.inputType === 'radio'"
                    v-model="attrValuesForm['attr_' + attr.attrCode]"
                  >
                    <el-radio
                      v-for="option in parseOptions(attr.options)"
                      :key="option"
                      :value="option"
                    >{{ option }}</el-radio>
                  </el-radio-group>
                  <!-- 多选 -->
                  <el-checkbox-group
                    v-else-if="attr.inputType === 'checkbox'"
                    v-model="attrValuesForm['attr_' + attr.attrCode]"
                  >
                    <el-checkbox
                      v-for="option in parseOptions(attr.options)"
                      :key="option"
                      :label="option"
                      :value="option"
                    >{{ option }}</el-checkbox>
                  </el-checkbox-group>
                  <!-- 下拉选择 -->
                  <el-select
                    v-else-if="attr.inputType === 'select'"
                    v-model="attrValuesForm['attr_' + attr.attrCode]
"
                    :placeholder="'请选择' + attr.attrName"
                    filterable
                    clearable
                    style="width: 100%"
                  >
                    <el-option
                      v-for="option in parseOptions(attr.options)"
                      :key="option"
                      :label="option"
                      :value="option"
                    />
                  </el-select>
                  <!-- 日期 -->
                  <el-date-picker
                    v-else-if="attr.inputType === 'date'"
                    v-model="attrValuesForm['attr_' + attr.attrCode]
"
                    type="date"
                    :placeholder="'请选择' + attr.attrName"
                    value-format="YYYY-MM-DD"
                    style="width: 100%"
                  />
                  <!-- 开关 -->
                  <el-switch
                    v-else-if="attr.inputType === 'switch'"
                    v-model="attrValuesForm['attr_' + attr.attrCode]
"
                    active-value="1"
                    inactive-value="0"
                  />
                  <!-- 富文本 -->
                  <el-input
                    v-else-if="attr.inputType === 'textarea'"
                    v-model="attrValuesForm['attr_' + attr.attrCode]
"
                    type="textarea"
                    :placeholder="'请输入' + attr.attrName"
                    :rows="3"
                    :maxlength="attr.maxLength || 500"
                    show-word-limit
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </el-tab-pane>

      <!-- Tab3: SKU管理 -->
      <el-tab-pane label="SKU管理" name="sku">
        <div class="sku-tab-content">
          <el-alert
            title="SKU规格管理：可手动添加或批量生成SKU，每个SKU代表一个具体规格的商品"
            type="info"
            :closable="false"
            show-icon
            class="sku-tip"
          />

          <!-- SKU操作按钮 -->
          <div class="sku-toolbar">
            <el-button type="primary" icon="Plus" @click="handleAddSku">添加SKU</el-button>
            <el-button type="warning" icon="Delete" @click="handleClearSku">清空所有SKU</el-button>
          </div>

          <!-- SKU表格 -->
          <el-table :data="skus" border stripe style="width: 100%">
            <el-table-column label="SKU编码" align="center" min-width="130">
              <template #default="scope">
                <el-input v-model="scope.row.skuCode" placeholder="SKU编码" size="small" />
              </template>
            </el-table-column>
            <el-table-column label="SKU名称" align="center" min-width="150">
              <template #default="scope">
                <el-input v-model="scope.row.skuName" placeholder="SKU名称" size="small" />
              </template>
            </el-table-column>
            <el-table-column label="规格值" align="center" min-width="180">
              <template #default="scope">
                <el-input v-model="scope.row.specValue" placeholder="如：红色/L码" size="small" />
              </template>
            </el-table-column>
            <el-table-column label="图片" align="center" width="90">
              <template #default="scope">
                <el-upload
                  class="avatar-uploader"
                  action="#"
                  :show-file-list="false"
                  :http-request="(file) => handleSkuUpload(file, scope.row)"
                  accept="image/*"
                >
                  <img v-if="scope.row.image" :src="baseUrl + scope.row.image" class="sku-avatar" />
                  <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
                </el-upload>
              </template>
            </el-table-column>
            <el-table-column label="价格" align="center" width="110">
              <template #default="scope">
                <el-input-number v-model="scope.row.price" :precision="2" :step="0.01" :min="0" size="small" controls-position="right" style="width: 95px" />
              </template>
            </el-table-column>
            <el-table-column label="原价" align="center" width="110">
              <template #default="scope">
                <el-input-number v-model="scope.row.originalPrice" :precision="2" :step="0.01" :min="0" size="small" controls-position="right" style="width: 95px" />
              </template>
            </el-table-column>
            <el-table-column label="成本价" align="center" width="110">
              <template #default="scope">
                <el-input-number v-model="scope.row.costPrice" :precision="2" :step="0.01" :min="0" size="small" controls-position="right" style="width: 95px" />
              </template>
            </el-table-column>
            <el-table-column label="库存" align="center" width="100">
              <template #default="scope">
                <el-input-number v-model="scope.row.stock" :min="0" size="small" controls-position="right" style="width: 85px" />
              </template>
            </el-table-column>
            <el-table-column label="条形码" align="center" width="130">
              <template #default="scope">
                <el-input v-model="scope.row.barcode" placeholder="条形码" size="small" />
              </template>
            </el-table-column>
            <el-table-column label="默认" align="center" width="70">
              <template #default="scope">
                <el-switch v-model="scope.row.isDefault" active-value="1" inactive-value="0" />
              </template>
            </el-table-column>
            <el-table-column label="状态" align="center" width="80">
              <template #default="scope">
                <el-switch v-model="scope.row.status" active-value="1" inactive-value="0" />
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="70" fixed="right">
              <template #default="scope">
                <el-button link type="danger" icon="Delete" @click="handleRemoveSku(scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { getProduct, addProduct, updateProduct } from "@/api/portal/product"
import { categoryTreeSelect } from "@/api/portal/productCategory"
import { listBrand } from "@/api/portal/productBrand"
import { getTemplate } from "@/api/portal/productAttrTemplate"
import axios from 'axios'
import { getToken } from "@/utils/auth"

const { proxy } = getCurrentInstance()
const { sys_normal_disable } = proxy.useDict("sys_normal_disable")
const emit = defineEmits(['refreshList'])

const baseUrl = import.meta.env.VITE_APP_BASE_API

/** 对话框状态 */
const visible = ref(false)
const dialogTitle = ref("新增产品")
const activeTab = ref("basic")
const submitLoading = ref(false)

/** 表单数据 */
const form = ref({})
const tagList = ref([])

/** 下拉选项 */
const categoryTreeOptions = ref([])
const brandOptions = ref([])

/** 扩展属性相关 */
const attrDefs = ref([])
const attrValuesForm = ref({})

/** SKU相关 */
const skus = ref([])

/** 验证规则 */
const rules = {
  productCode: [
    { required: true, message: "产品编码不能为空", trigger: "blur" }
  ],
  productName: [
    { required: true, message: "产品名称不能为空", trigger: "blur" }
  ],
  categoryId: [
    { required: true, message: "请选择所属分类", trigger: "change" }
  ],
  mainImage: [
    { required: true, message: "请上传主图", trigger: "change" }
  ],
  price: [
    { required: true, message: "价格不能为空", trigger: "blur" }
  ],
  status: [
    { required: true, message: "状态不能为空", trigger: "change" }
  ]
}

/** 获取分类树 */
function getCategoryTree() {
  categoryTreeSelect().then(response => {
    categoryTreeOptions.value = response.data
  })
}

/** 获取品牌列表 */
function getBrandList() {
  listBrand({ pageNum: 1, pageSize: 999 }).then(response => {
    brandOptions.value = response.rows
  })
}

/** 解析选项字符串为数组 */
function parseOptions(optionsStr) {
  if (!optionsStr) return []
  return optionsStr.split(',').map(item => item.trim()).filter(item => item)
}

/** 重置表单 */
function resetForm() {
  form.value = {
    productId: null,
    productCode: null,
    productName: null,
    categoryId: null,
    brandId: null,
    mainImage: null,
    thumbnailImage: null,
    images: null,
    videoUrl: null,
    subtitle: null,
    summary: null,
    description: null,
    price: null,
    originalPrice: null,
    costPrice: null,
    unit: null,
    weight: null,
    volume: null,
    isRecommend: '0',
    isNew: '0',
    isHot: '0',
    status: '0',
    sortOrder: 0,
    publishTime: null,
    remark: null
  }
  tagList.value = []
  attrDefs.value = []
  attrValuesForm.value = {}
  skus.value = [createEmptySku()]
  activeTab.value = 'basic'
}

/** 创建空的SKU对象 */
function createEmptySku() {
  return {
    skuId: null,
    skuCode: null,
    skuName: null,
    specValue: null,
    image: null,
    price: null,
    originalPrice: null,
    costPrice: null,
    stock: 0,
    weight: null,
    barcode: null,
    isDefault: '0',
    status: '1'
  }
}

/** 加载属性模板定义 */
function loadAttrTemplate(categoryId) {
  /** 先获取分类信息以获取模板ID，这里简化处理直接查询所有模板 */
  getTemplate(categoryId).then(response => {
    if (response.data && response.data.attrDefs && response.data.attrDefs.length > 0) {
      attrDefs.value = response.data.attrDefs.sort((a, b) => a.sortOrder - b.sortOrder)
      /** 初始化属性值表单 */
      const newAttrValues = {}
      attrDefs.value.forEach(attr => {
        newAttrValues['attr_' + attr.attrCode] = attr.defaultValue || ''
      })
      attrValuesForm.value = newAttrValues
    } else {
      attrDefs.value = []
      attrValuesForm.value = {}
    }
  }).catch(() => {
    attrDefs.value = []
    attrValuesForm.value = {}
  })
}

/** SKU图片上传 */
async function handleSkuUpload(fileObj, skuRow) {
  const formData = new FormData()
  formData.append('file', fileObj.file)
  try {
    const res = await axios.post(baseUrl + '/common/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: 'Bearer ' + getToken()
      }
    })
    if (res.data.code === 200) {
      skuRow.image = res.data.fileName
    } else {
      proxy.$modal.msgError('上传失败')
    }
  } catch (error) {
    proxy.$modal.msgError('上传失败')
  }
}

/** 添加SKU */
function handleAddSku() {
  skus.value.push(createEmptySku())
}

/** 删除SKU */
function handleRemoveSku(index) {
  skus.value.splice(index, 1)
}

/** 清空所有SKU */
function handleClearSku() {
  proxy.$modal.confirm('确定要清空所有SKU吗？').then(() => {
    skus.value = []
  }).catch(() => {})
}

/** 新增操作 */
function handleAdd() {
  resetForm()
  getCategoryTree()
  getBrandList()
  visible.value = true
  dialogTitle.value = "新增产品"
}

/** 修改操作 */
function handleUpdate(productId) {
  resetForm()
  getCategoryTree()
  getBrandList()
  getProduct(productId).then(response => {
    form.value = response.data

    /** 处理标签 */
    tagList.value = []
    if (response.data.isRecommend === '1') tagList.value.push('isRecommend')
    if (response.data.isNew === '1') tagList.value.push('isNew')
    if (response.data.isHot === '1') tagList.value.push('isHot')

    /** 处理扩展属性 */
    if (response.data.attrValues && response.data.attrValues.length > 0) {
      const newAttrValues = {}
      response.data.attrValues.forEach(item => {
        newAttrValues['attr_' + item.attrCode] = item.attrValue
      })
      attrValuesForm.value = newAttrValues
    }

    /** 处理SKU */
    skus.value = response.data.skus && response.data.skus.length > 0
      ? response.data.skus
      : [createEmptySku()]

    /** 加载属性模板 */
    if (response.data.categoryId) {
      loadAttrTemplate(response.data.categoryId)
    }

    visible.value = true
    dialogTitle.value = "修改产品"
  })
}

/** 查看详情（只读模式） */
function handleView(productId) {
  handleUpdate(productId)
  dialogTitle.value = "查看产品详情"
}

/** 取消操作 */
function handleCancel() {
  visible.value = false
}

/** 提交表单 */
function handleSubmit() {
  proxy.$refs["productRef"].validate(valid => {
    if (!valid) {
      activeTab.value = 'basic'
      return
    }

    /** 构建提交数据 */
    const submitData = {
      ...form.value,
      isRecommend: tagList.value.includes('isRecommend') ? '1' : '0',
      isNew: tagList.value.includes('isNew') ? '1' : '0',
      isHot: tagList.value.includes('isHot') ? '1' : '0'
    }

    /** 处理扩展属性值 */
    submitData.attrValues = []
    Object.keys(attrValuesForm.value).forEach(key => {
      if (key.startsWith('attr_')) {
        const attrCode = key.replace('attr_', '')
        let attrValue = attrValuesForm.value[key]
        /** 多选类型转为逗号分隔的字符串 */
        if (Array.isArray(attrValue)) {
          attrValue = attrValue.join(',')
        }
        submitData.attrValues.push({
          attrCode: attrCode,
          attrValue: String(attrValue || '')
        })
      }
    })

    /** 处理SKU数据 */
    submitData.skus = skus.value.filter(sku => sku.skuCode || sku.skuName)

    submitLoading.value = true
    const promise = submitData.productId != null
      ? updateProduct(submitData)
      : addProduct(submitData)

    promise.then(response => {
      proxy.$modal.msgSuccess(submitData.productId != null ? '修改成功' : '新增成功')
      visible.value = false
      emit('refreshList')
    }).finally(() => {
      submitLoading.value = false
    })
  })
}

defineExpose({
  handleAdd,
  handleUpdate,
  handleView
})
</script>

<style scoped>
.product-form {
  padding-right: 10px;
}

.attr-tab-content,
.sku-tab-content {
  padding: 10px;
}

.attr-empty-tip,
.sku-tip {
  margin-bottom: 16px;
}

.sku-toolbar {
  margin-bottom: 16px;
}

.avatar-uploader {
  display: inline-block;
}

.sku-avatar {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 4px;
}

:deep(.avatar-uploader .el-upload) {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

:deep(.avatar-uploader .el-upload:hover) {
  border-color: var(--el-color-primary);
}

.avatar-uploader-icon {
  font-size: 18px;
  color: #8c939d;
}
</style>
