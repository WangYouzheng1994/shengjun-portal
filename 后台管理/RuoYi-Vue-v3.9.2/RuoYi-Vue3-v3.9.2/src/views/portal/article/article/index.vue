<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 左侧分类树 -->
      <el-col :span="5" :xs="24">
        <div class="category-tree-container">
          <div class="tree-header">
            <span>文章分类</span>
            <el-button icon="Refresh" circle size="small" @click="handleRefreshCategory" title="刷新分类"/>
          </div>
          <el-input
            v-model="categorySearchText"
            placeholder="搜索分类名称"
            clearable
            style="margin-bottom: 10px;"
            prefix-icon="Search"
          />
          <el-tree
            ref="categoryTreeRef"
            :data="filteredCategoryTreeData"
            :props="defaultProps"
            :expand-on-click-node="false"
            :highlight-current="true"
            node-key="id"
            default-expand-all
            @node-click="handleNodeClick"
          >
            <template #default="{ node, data }">
              <span class="custom-tree-node">
                <span>{{ data.label }}</span>
              </span>
            </template>
          </el-tree>
        </div>
      </el-col>

      <!-- 右侧文章列表 -->
      <el-col :span="19" :xs="24">
        <!-- 搜索表单 -->
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
          <el-form-item label="标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入文章标题"
              clearable
              style="width: 200px"
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">
              <el-option
                v-for="dict in portal_article_status"
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
              v-hasPermi="['portal:article:article:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="Edit"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['portal:article:article:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="Delete"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['portal:article:article:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="Download"
              @click="handleExport"
              v-hasPermi="['portal:article:article:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <!-- 数据表格 -->
        <el-table v-loading="loading" :data="articleList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="ID" align="center" prop="articleId" width="80" />
          <el-table-column label="封面图" align="center" prop="coverImage" width="100">
            <template #default="scope">
              <image-preview v-if="scope.row.coverImage" :src="scope.row.coverImage" :width="80" :height="50" />
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="标题" align="center" prop="title" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="分类" align="center" prop="categoryName" width="100" />
          <el-table-column label="作者" align="center" prop="author" width="80" />
          <el-table-column label="状态" align="center" prop="status" width="80">
            <template #default="scope">
              <dict-tag :options="portal_article_status" :value="scope.row.status" />
            </template>
          </el-table-column>
          <el-table-column label="置顶" align="center" prop="isTop" width="60">
            <template #default="scope">
              <el-tag v-if="scope.row.isTop === '1'" type="danger">置顶</el-tag>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="推荐" align="center" prop="isRecommend" width="60">
            <template #default="scope">
              <el-tag v-if="scope.row.isRecommend === '1'" type="warning">推荐</el-tag>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="浏览量" align="center" prop="viewCount" width="70" />
          <el-table-column label="发布时间" align="center" prop="publishTime" width="160">
            <template #default="scope">
              <span>{{ parseTime(scope.row.publishTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
            <template #default="scope">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['portal:article:article:edit']">修改</el-button>
              <el-button
                v-if="scope.row.status === '0' || scope.row.status === '2'"
                link type="success"
                icon="Top"
                @click="handlePublish(scope.row)"
                v-hasPermi="['portal:article:article:publish']"
              >发布</el-button>
              <el-button
                v-if="scope.row.status === '1'"
                link type="warning"
                icon="Bottom"
                @click="handleOffline(scope.row)"
                v-hasPermi="['portal:article:article:edit']"
              >下架</el-button>
              <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['portal:article:article:remove']">删除</el-button>
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

        <!-- 添加或修改文章对话框（大尺寸，包含富文本编辑器） -->
        <el-dialog :title="title" v-model="open" width="900px" append-to-body destroy-on-close>
          <el-form ref="articleRef" :model="form" :rules="rules" label-width="100px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="文章标题" prop="title">
                  <el-input v-model="form.title" placeholder="请输入文章标题" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="副标题" prop="subtitle">
                  <el-input v-model="form.subtitle" placeholder="请输入副标题（可选）" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="所属分类" prop="categoryId">
                  <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%">
                    <el-option
                      v-for="item in categoryOptions"
                      :key="item.categoryId"
                      :label="item.categoryName"
                      :value="item.categoryId"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="作者" prop="author">
                  <el-input v-model="form.author" placeholder="请输入作者" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="文章摘要" prop="summary">
                  <el-input v-model="form.summary" type="textarea" :rows="3" placeholder="请输入文章摘要" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="封面图片" prop="coverImage">
                  <image-upload v-model="form.coverImage" :limit="1" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="正文内容" prop="content">
                  <editor v-model="form.content" :min-height="300" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="来源" prop="source">
                  <el-input v-model="form.source" placeholder="请输入来源" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="排序" prop="sortOrder">
                  <el-input-number v-model="form.sortOrder" :min="0" :max="9999" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="状态" prop="status">
                  <el-radio-group v-model="form.status">
                    <el-radio
                      v-for="dict in portal_article_status"
                      :key="dict.value"
                      :label="dict.value"
                    >{{ dict.label }}</el-radio>
                  </el-radio-group>
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
                <el-form-item label="是否推荐" prop="isRecommend">
                  <el-radio-group v-model="form.isRecommend">
                    <el-radio label="0">否</el-radio>
                    <el-radio label="1">是</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8">
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
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Article">
import { listArticle, getArticle, delArticle, addArticle, updateArticle, exportArticle, publishArticle, offlineArticle } from "@/api/portal/article"
import { categoryTreeSelect, listCategory } from "@/api/portal/articleCategory"

const { proxy } = getCurrentInstance()
const { portal_article_status } = proxy.useDict("portal_article_status")

const articleList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const dateRange = ref([])

/**
 * 分类选项列表（用于表单下拉）
 */
const categoryOptions = ref([])

/**
 * 分类树数据
 */
const categoryTreeData = ref([])

/**
 * 分类搜索文本
 */
const categorySearchText = ref("")

/**
 * 树形组件默认配置
 */
const defaultProps = {
  children: "children",
  label: "label"
}

/**
 * 当前选中的分类ID
 */
const currentCategoryId = ref(null)

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    title: null,
    categoryId: null,
    status: null,
    author: null
  },
  rules: {
    title: [
      { required: true, message: "文章标题不能为空", trigger: "blur" }
    ],
    categoryId: [
      { required: true, message: "请选择分类", trigger: "change" }
    ],
    content: [
      { required: true, message: "正文内容不能为空", trigger: "blur" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 获取分类树数据 */
function getCategoryTree() {
  categoryTreeSelect({ status: "0" }).then(response => {
    const treeData = response.data
    categoryTreeData.value = [{
      id: 0,
      label: "全部文章",
      children: treeData
    }]
  })
}

/**
 * 过滤分类树数据（前端模糊搜索）
 */
const filteredCategoryTreeData = computed(() => {
  if (!categorySearchText.value) {
    return categoryTreeData.value
  }
  return filterTreeNodes(categoryTreeData.value, categorySearchText.value.toLowerCase())
})

/**
 * 递归过滤树节点
 */
function filterTreeNodes(nodes, keyword) {
  const result = []
  for (const node of nodes) {
    if (node.label && node.label.toLowerCase().includes(keyword)) {
      result.push({ ...node })
    } else if (node.children && node.children.length > 0) {
      const filteredChildren = filterTreeNodes(node.children, keyword)
      if (filteredChildren.length > 0) {
        result.push({ ...node, children: filteredChildren })
      }
    }
  }
  return result
}

/**
 * 刷新分类树数据
 */
function handleRefreshCategory() {
  categorySearchText.value = ""
  getCategoryTree()
}

/** 获取分类列表（用于表单下拉） */
function getCategoryOptions() {
  listCategory({ status: "0" }).then(response => {
    categoryOptions.value = response.rows
  })
}

/** 查询文章列表 */
function getList() {
  loading.value = true
  listArticle(proxy.addDateRange(queryParams.value, dateRange.value)).then(response => {
    articleList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 点击树节点 */
function handleNodeClick(data) {
  if (data.id === 0) {
    queryParams.value.categoryId = null
  } else {
    queryParams.value.categoryId = data.id
  }
  currentCategoryId.value = data.id
  handleQuery()
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

/** 表单重置 */
function reset() {
  form.value = {
    articleId: null,
    categoryId: currentCategoryId.value || null,
    title: null,
    subtitle: null,
    summary: null,
    content: null,
    coverImage: null,
    author: null,
    source: null,
    viewCount: 0,
    isTop: "0",
    isRecommend: "0",
    status: "0",
    publishTime: null,
    sortOrder: 0,
    remark: null
  }
  proxy.resetForm("articleRef")
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
  if (currentCategoryId.value && currentCategoryId.value !== 0) {
    queryParams.value.categoryId = currentCategoryId.value
  } else {
    queryParams.value.categoryId = null
  }
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.articleId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加文章"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _articleId = row.articleId || ids.value
  getArticle(_articleId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改文章"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["articleRef"].validate(valid => {
    if (valid) {
      if (form.value.articleId != null) {
        updateArticle(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addArticle(form.value).then(response => {
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
  const _articleIds = row.articleId || ids.value
  proxy.$modal.confirm('是否确认删除文章编号为"' + _articleIds + '"的数据项？').then(function() {
    return delArticle(_articleIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 发布按钮操作 */
function handlePublish(row) {
  const _articleId = row.articleId
  proxy.$modal.confirm('确认要发布该文章吗？').then(function() {
    return publishArticle({ articleId: _articleId })
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("发布成功")
  }).catch(() => {})
}

/** 下架按钮操作 */
function handleOffline(row) {
  const _articleId = row.articleId
  proxy.$modal.confirm('确认要下架该文章吗？').then(function() {
    return offlineArticle({ articleId: _articleId })
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("下架成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('portal/article/article/export', {
    ...queryParams.value
  }, `article_${new Date().getTime()}.xlsx`)
}

getCategoryTree()
getCategoryOptions()
getList()
</script>

<style scoped>
.category-tree-container {
  background-color: #fff;
  border-radius: 4px;
  padding: 15px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  height: calc(100vh - 120px);
  overflow-y: auto;
}

.tree-header {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

:deep(.el-tree-node__content) {
  height: 34px;
}

:deep(.el-tree-node.is-current > .el-tree-node__content) {
  background-color: #ecf5ff;
  color: #409eff;
}
</style>
