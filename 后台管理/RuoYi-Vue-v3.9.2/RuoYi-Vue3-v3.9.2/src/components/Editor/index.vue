<template>
  <div>
    <el-upload
      :action="uploadUrl"
      :before-upload="handleBeforeUpload"
      :on-success="handleUploadSuccess"
      :on-error="handleUploadError"
      name="file"
      :show-file-list="false"
      :headers="headers"
      class="editor-img-uploader"
      v-if="type == 'url'"
    >
      <i ref="uploadRef" class="editor-img-uploader"></i>
    </el-upload>
  </div>
  <div class="editor">
    <quill-editor
      ref="quillEditorRef"
      v-model:content="content"
      contentType="html"
      @textChange="(e) => $emit('update:modelValue', content)"
      :options="options"
      :style="styles"
    />
  </div>
</template>

<script setup>
import axios from 'axios'
import { QuillEditor, Quill } from "@vueup/vue-quill"
import "@vueup/vue-quill/dist/vue-quill.snow.css"
import { getToken } from "@/utils/auth"

const { proxy } = getCurrentInstance()

/**
 * 注册Quill自定义字体
 * 包含常用中文字体，满足文章发布需求
 */
const Font = Quill.import("formats/font")
Font.whitelist = [
  "false",
  "songti",
  "heiti",
  "kaiti",
  "fangsong",
  "yahei",
  "serif",
  "monospace"
]
Quill.register(Font, true)

const quillEditorRef = ref()
const uploadUrl = ref(import.meta.env.VITE_APP_BASE_API + "/common/upload") // 上传的图片服务器地址
const headers = ref({
  Authorization: "Bearer " + getToken()
})

const props = defineProps({
  /* 编辑器的内容 */
  modelValue: {
    type: String,
  },
  /* 高度 */
  height: {
    type: Number,
    default: null,
  },
  /* 最小高度 */
  minHeight: {
    type: Number,
    default: null,
  },
  /* 只读 */
  readOnly: {
    type: Boolean,
    default: false,
  },
  /* 上传文件大小限制(MB) */
  fileSize: {
    type: Number,
    default: 5,
  },
  /* 类型（base64格式、url格式） */
  type: {
    type: String,
    default: "url",
  }
})

const options = ref({
  theme: "snow",
  bounds: document.body,
  debug: "warn",
  modules: {
    // 工具栏配置
    toolbar: [
      ["bold", "italic", "underline", "strike"],      // 加粗 斜体 下划线 删除线
      ["blockquote", "code-block"],                   // 引用  代码块
      [{ list: "ordered" }, { list: "bullet" }],      // 有序、无序列表
      [{ indent: "-1" }, { indent: "+1" }],           // 缩进
      [{ size: ["small", false, "large", "huge"] }],  // 字体大小
      [{ header: [1, 2, 3, 4, 5, 6, false] }],        // 标题
      [{ font: [false, "songti", "heiti", "kaiti", "fangsong", "yahei", "serif", "monospace"] }],  // 字体（含中文字体）
      [{ color: [] }, { background: [] }],            // 字体颜色、字体背景颜色
      [{ align: [] }],                                // 对齐方式
      ["clean"],                                      // 清除文本格式
      ["link", "image", "video"]                      // 链接、图片、视频
    ],
  },
  placeholder: "请输入内容",
  readOnly: props.readOnly
})

const styles = computed(() => {
  let style = {}
  if (props.minHeight) {
    style.minHeight = `${props.minHeight}px`
  }
  if (props.height) {
    style.height = `${props.height}px`
  }
  return style
})

const content = ref("")
watch(() => props.modelValue, (v) => {
  if (v !== content.value) {
    content.value = v == undefined ? "<p></p>" : v
  }
}, { immediate: true })

// 如果设置了上传地址则自定义图片上传事件
onMounted(() => {
  let quill = quillEditorRef.value.getQuill()

  /**
   * 为工具栏按钮添加中文悬停提示
   * 提升用户体验，让每个按钮功能一目了然
   */
  addToolbarTooltips(quill)

  if (props.type == 'url') {
    let toolbar = quill.getModule("toolbar")
    toolbar.addHandler("image", (value) => {
      if (value) {
        proxy.$refs.uploadRef.click()
      } else {
        quill.format("image", false)
      }
    })
    quill.root.addEventListener('paste', handlePasteCapture, true)
  }
})

/**
 * 工具栏按钮提示文案映射表
 * 按钮格式 -> 中文说明
 */
const toolbarTooltipMap = {
  "ql-bold": "加粗",
  "ql-italic": "斜体",
  "ql-underline": "下划线",
  "ql-strike": "删除线",
  "ql-blockquote": "引用",
  "ql-code-block": "代码块",
  "ql-list[value=ordered]": "有序列表",
  "ql-list[value=bullet]": "无序列表",
  "ql-indent[value=-1]": "减少缩进",
  "ql-indent[value=+1]": "增加缩进",
  "ql-header[value=1]": "标题1",
  "ql-header[value=2]": "标题2",
  "ql-header[value=3]": "标题3",
  "ql-header[value=4]": "标题4",
  "ql-header[value=5]": "标题5",
  "ql-header[value=6]": "标题6",
  "ql-size[value=small]": "小号字体",
  "ql-size[value=large]": "大号字体",
  "ql-size[value=huge]": "超大号字体",
  "ql-font[value=songti]": "宋体",
  "ql-font[value=heiti]": "黑体",
  "ql-font[value=kaiti]": "楷体",
  "ql-font[value=fangsong]": "仿宋",
  "ql-font[value=yahei]": "微软雅黑",
  "ql-font[value=serif]": "衬线体",
  "ql-font[value=monospace]": "等宽字体",
  "ql-color": "字体颜色",
  "ql-background": "背景颜色",
  "ql-align": "对齐方式",
  "ql-clean": "清除格式",
  "ql-link": "插入链接",
  "ql-image": "插入图片",
  "ql-video": "插入视频"
}

/**
 * 为Quill编辑器工具栏添加悬停提示
 *
 * @param quill Quill实例对象
 */
function addToolbarTooltips(quill) {
  nextTick(() => {
    const toolbarElement = quill.getModule("toolbar").container
    if (!toolbarElement) {
      return
    }

    const buttons = toolbarElement.querySelectorAll("button, .ql-picker")
    buttons.forEach((btn) => {
      const className = btn.className || ""
      let tooltipText = ""

      for (const [key, value] of Object.entries(toolbarTooltipMap)) {
        if (className.includes(key.replace(/[[\]]/g, ""))) {
          tooltipText = value
          break
        }
      }

      if (tooltipText && !btn.getAttribute("title")) {
        btn.setAttribute("title", tooltipText)
      }
    })
  })
}

// 上传前校检格式和大小
function handleBeforeUpload(file) {
  const type = ["image/jpeg", "image/jpg", "image/png", "image/svg"]
  const isJPG = type.includes(file.type)
  //检验文件格式
  if (!isJPG) {
    proxy.$modal.msgError(`图片格式错误!`)
    return false
  }
  // 校检文件大小
  if (props.fileSize) {
    const isLt = file.size / 1024 / 1024 < props.fileSize
    if (!isLt) {
      proxy.$modal.msgError(`上传文件大小不能超过 ${props.fileSize} MB!`)
      return false
    }
  }
  return true
}

// 上传成功处理
function handleUploadSuccess(res, file) {
  // 如果上传成功
  if (res.code == 200) {
    // 获取富文本实例
    let quill = toRaw(quillEditorRef.value).getQuill()
    // 获取光标位置
    let length = quill.selection.savedRange.index
    // 插入图片，res.url为服务器返回的图片链接地址
    quill.insertEmbed(length, "image", import.meta.env.VITE_APP_BASE_API + res.fileName)
    // 调整光标到最后
    quill.setSelection(length + 1)
  } else {
    proxy.$modal.msgError("图片插入失败")
  }
}

// 上传失败处理
function handleUploadError() {
  proxy.$modal.msgError("图片插入失败")
}

// 复制粘贴图片处理
function handlePasteCapture(e) {
  const clipboard = e.clipboardData || window.clipboardData
  if (clipboard && clipboard.items) {
    for (let i = 0; i < clipboard.items.length; i++) {
      const item = clipboard.items[i]
      if (item.type.indexOf('image') !== -1) {
        e.preventDefault()
        const file = item.getAsFile()
        insertImage(file)
      }
    }
  }
}

function insertImage(file) {
  const formData = new FormData()
  formData.append("file", file)
  axios.post(uploadUrl.value, formData, { headers: { "Content-Type": "multipart/form-data", Authorization: headers.value.Authorization } }).then(res => {
    handleUploadSuccess(res.data)
  })
}
</script>

<style>
.editor-img-uploader {
  display: none;
}
.editor, .ql-toolbar {
  white-space: pre-wrap !important;
  line-height: normal !important;
}
.quill-img {
  display: none;
}
.ql-snow .ql-tooltip[data-mode="link"]::before {
  content: "请输入链接地址:";
}
.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
  border-right: 0px;
  content: "保存";
  padding-right: 0px;
}
.ql-snow .ql-tooltip[data-mode="video"]::before {
  content: "请输入视频地址:";
}
.ql-snow .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: "14px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
  content: "10px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
  content: "18px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
  content: "32px";
}
.ql-snow .ql-picker.ql-header .ql-picker-label::before,
.ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: "文本";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
  content: "标题1";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
  content: "标题2";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
  content: "标题3";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
  content: "标题4";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
  content: "标题5";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
  content: "标题6";
}
.ql-snow .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: "字体";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="songti"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="songti"]::before {
  content: "宋体";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="heiti"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="heiti"]::before {
  content: "黑体";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="kaiti"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="kaiti"]::before {
  content: "楷体";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="fangsong"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="fangsong"]::before {
  content: "仿宋";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="yahei"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="yahei"]::before {
  content: "微软雅黑";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
  content: "衬线体";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
  content: "等宽";
}

/**
 * 字体样式定义
 * 使用系统默认字体，确保跨平台兼容性
 */
.ql-editor .ql-font-songti {
  font-family: "SimSun", "STSong", "Songti SC", serif;
}
.ql-editor .ql-font-heiti {
  font-family: "SimHei", "STHeiti", "Heiti SC", sans-serif;
}
.ql-editor .ql-font-kaiti {
  font-family: "KaiTi", "STKaiti", "KaiTi_SC", serif;
}
.ql-editor .ql-font-fangsong {
  font-family: "FangSong", "STFangsong", serif;
}
.ql-editor .ql-font-yahei {
  font-family: "Microsoft YaHei", "PingFang SC", "Hiragino Sans GB", sans-serif;
}
</style>
