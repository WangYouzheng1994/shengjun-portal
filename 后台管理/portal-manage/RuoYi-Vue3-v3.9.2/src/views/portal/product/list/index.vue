<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="产品名称/编码" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          placeholder="请输入产品名称或编码"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属分类" prop="categoryId">
        <el-tree-select
          v-model="queryParams.categoryId"
          :data="categoryTreeOptions"
          :props="{ value: 'id', label: 'label', children: 'children' }"
          value-key="id"
          placeholder="选择分类"
          check-strictly
          filterable
          clearable
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="品牌" prop="brandId">
        <el-select v-model="queryParams.brandId" placeholder="请选择品牌" clearable filterable style="width: 200px">
          <el-option
            v-for="item in brandOptions"
            :key="item.brandId"
            :label="item.brandName"
            :value="item.brandId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 120px">
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
          v-hasPermi="['portal:product:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['portal:product:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['portal:product:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['portal:product:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品编码" align="center" prop="productCode" width="120" />
      <el-table-column label="产品名称" align="center" prop="productName" :show-overflow-tooltip="true" min-width="150" />
      <el-table-column label="分类名称" align="center" prop="categoryName" width="120" :show-overflow-tooltip="true" />
      <el-table-column label="品牌名称" align="center" prop="brandName" width="100" :show-overflow-tooltip="true" />
      <el-table-column label="主图" align="center" prop="mainImage" width="80">
        <template #default="scope">
          <image-preview v-if="scope.row.mainImage" :src="scope.row.mainImage" :width="50" :height="50" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" prop="price" width="90">
        <template #default="scope">
          <span v-if="scope.row.price != null">{{ scope.row.price.toFixed(2) }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="原价" align="center" prop="originalPrice" width="90">
        <template #default="scope">
          <span v-if="scope.row.originalPrice != null">{{ scope.row.originalPrice.toFixed(2) }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="销量" align="center" prop="salesCount" width="70" />
      <el-table-column label="浏览次数" align="center" prop="viewCount" width="80" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" width="160">
        <template #default="scope">
          <el-tag v-if="scope.row.isRecommend === '1'" size="small" type="danger" class="tag-margin">推荐</el-tag>
          <el-tag v-if="scope.row.isNew === '1'" size="small" type="success" class="tag-margin">新品</el-tag>
          <el-tag v-if="scope.row.isHot === '1'" size="small" type="warning" class="tag-margin">热销</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="250" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleView(scope.row)">查看</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['portal:product:edit']">修改</el-button>
          <el-button
            link
            :type="scope.row.status === '0' ? 'success' : 'warning'"
            :icon="scope.row.status === '0' ? 'Top' : 'Bottom'"
            @click="handleChangeStatus(scope.row)"
            v-hasPermi="['portal:product:edit']"
          >{{ scope.row.status === '0' ? '上架' : '下架' }}</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['portal:product:remove']">删除</el-button>
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

    <!-- 产品编辑对话框（使用组件） -->
    <ProductEdit
      ref="productEditRef"
      @refreshList="getList"
    />
  </div>
</template>

<script setup name="ProductList">
import { listProduct, delProduct, updateProductStatus, exportProduct } from "@/api/portal/product"
import { categoryTreeSelect } from "@/api/portal/productCategory"
import { listBrand } from "@/api/portal/productBrand"
import ProductEdit from './edit.vue'

const { proxy } = getCurrentInstance()
const { sys_normal_disable } = proxy.useDict("sys_normal_disable")

const productList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)

/** 下拉选项数据 */
const categoryTreeOptions = ref([])
const brandOptions = ref([])

/** 产品编辑组件引用 */
const productEditRef = ref(null)

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    keyword: null,
    categoryId: null,
    brandId: null,
    status: null
  }
})

const { queryParams } = toRefs(data)

/** 查询产品列表 */
function getList() {
  loading.value = true
  listProduct(queryParams.value).then(response => {
    productList.value = response.rows
    total.value = response.total
    loading.value = false
  })
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
  ids.value = selection.map(item => item.productId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  productEditRef.value.handleAdd()
}

/** 修改按钮操作 */
function handleUpdate(row) {
  const _productId = row.productId || ids.value[0]
  productEditRef.value.handleUpdate(_productId)
}

/** 查看详情 */
function handleView(row) {
  productEditRef.value.handleView(row.productId)
}

/** 上架/下架操作 */
function handleChangeStatus(row) {
  const newStatus = row.status === '0' ? '1' : '0'
  const actionText = newStatus === '1' ? '上架' : '下架'
  proxy.$modal.confirm(`是否确认${actionText}产品"${row.productName}"？`).then(function() {
    return updateProductStatus(row.productId, newStatus)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess(`${actionText}成功`)
  }).catch(() => {})
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _productIds = row.productId || ids.value
  proxy.$modal.confirm('是否确认删除产品编号为"' + _productIds + '"的数据项？').then(function() {
    return delProduct(_productIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('portal/product/export', {
    ...queryParams.value
  }, `product_${new Date().getTime()}.xlsx`)
}

getCategoryTree()
getBrandList()
getList()
</script>

<style scoped>
.tag-margin {
  margin-right: 4px;
}
</style>
