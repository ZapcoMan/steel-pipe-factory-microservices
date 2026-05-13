<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input clearable @clear="load" style="width: 260px; margin-right: 5px" v-model="data.name" placeholder="请输入企业名称查询" :prefix-icon="Search"></el-input>
      <el-input clearable @clear="load" style="width: 260px; margin-right: 5px" v-model="data.type" placeholder="请输入企业类型查询" :prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查 询</el-button>
      <el-button @click="reset">重 置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="handleAdd">新 增</el-button>
      <el-button type="danger" @click="deleteBatch">批量删除</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" style="width: 100%" @selection-change="handleSelectionChange"
                :header-cell-style="{ color: '#333', backgroundColor: '#eaf4ff' }">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="name" label="企业名称" />
        <el-table-column prop="type" label="企业类型" />
        <el-table-column label="经营范围" >
          <template #default="scope">
            <div class="richtext-content" v-html="truncateAndStripHtml(scope.row.scope)" :title="removeHtmlTags(scope.row.scope)"></div>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="联系电话" />
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button type="primary" icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" circle @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination
          v-model:current-page="data.pageNum"
          v-model:page-size="data.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[5, 10, 15, 25]"
          :total="data.total"
          @current-change="load"
          @size-change="load"
      />
    </div>

    <el-dialog title="特色企业信息" v-model="data.formVisible" width="50%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="80px" style="padding: 20px 30px 10px 0">
        <el-form-item prop="name" label="企业名称">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item prop="type" label="企业类型">
          <el-input v-model="data.form.type" autocomplete="off" placeholder="请输入企业类型"/>
        </el-form-item>
        <el-form-item prop="scope" label="经营范围">
          <div style="border: 1px solid #ccc; min-height: 350px;">
            <Toolbar
              style="border-bottom: 1px solid #ccc"
              :editor="editorRef"
              :defaultConfig="toolbarConfig"
              :mode="mode"
            />
            <Editor
              style="height: 300px; overflow-y: hidden;"
              v-model="valueHtml"
              :defaultConfig="editorConfig"
              :mode="mode"
              @onCreated="handleCreated"
            />
          </div>
        </el-form-item>
        <el-form-item prop="phone" label="联系电话">
          <el-input v-model="data.form.phone" autocomplete="off" placeholder="请输入联系电话"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref, onBeforeUnmount, watch } from "vue";
import {Search} from "@element-plus/icons-vue";
import {ElMessage,ElMessageBox} from "element-plus";
import { 
  fetchFeaturedCompanies, 
  addFeaturedCompany, 
  updateFeaturedCompany, 
  deleteFeaturedCompany, 
  deleteFeaturedCompanies 
} from "@/api/featuredCompany.js";
// 引入富文本编辑器组件和 CSS
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css'
import { config } from "@/config";

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}"),
  name: null,
  type: null,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  tableData: [],
  formVisible: false,
  form: {},
  rules: {
    name: [
      { required: true, message: '请填写企业名称', trigger: 'blur' }
    ],
    type: [
      { required: true, message: '请填写企业类型', trigger: 'blur' }
    ],
    scope: [
      { required: true, message: '请填写经营范围', trigger: 'blur' }
    ],
    phone: [
      { required: true, message: '请填写联系电话', trigger: 'blur' }
    ],
  },
  rows: []
})

// 富文本编辑器相关配置
const editorRef = ref()
const valueHtml = ref('')
const mode = 'default'
const toolbarConfig = { }
const editorConfig = { 
  placeholder: '请输入经营范围...',
  MENU_CONF: {
    uploadImage: {
      fieldName: 'file',
      maxFileSize: 2 * 1024 * 1024, // 2M
      server: config.baseURL + '/files/wang/upload', // 配置服务器上传地址
    }
  }
}

// 监听表单内容变化，同步到富文本编辑器
watch(
  () => data.form.scope,
  (newVal) => {
    if (newVal !== valueHtml.value) {
      valueHtml.value = newVal || ''
    }
  },
  { immediate: true }
)

// 监听富文本编辑器内容变化，同步到表单
watch(valueHtml, (newVal) => {
  // 清理富文本内容，移除可能导致后端异常的字符
  const cleanedValue = cleanRichTextContent(newVal)
  data.form.scope = cleanedValue
})

// 清理富文本内容，移除可能导致后端异常的字符和格式
const cleanRichTextContent = (content) => {
  if (!content) return ''
  // 移除多余的换行符和空格
  let cleaned = content.replace(/\n\s*\n/g, '\n')
  // 限制总长度，防止超出数据库字段限制
  if (cleaned.length > 5000) {
    cleaned = cleaned.substring(0, 5000)
  }
  // 防止XSS攻击，移除可能的危险标签和属性
  cleaned = cleaned.replace(/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, '')
  // 移除可能导致问题的特殊字符
  cleaned = cleaned.replace(/\0/g, '') // 移除null字符
  return cleaned
}

// 富文本编辑器回调函数
const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}

const formRef = ref()
const load = () => {
  fetchFeaturedCompanies({
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    name: data.name,
    type: data.type
  }).then(res => {
    console.log('分页返回数据:', res.dataMap)
    if (res.code === 20000) {
      data.tableData = res.dataMap.pageInfo.list
      data.total = res.dataMap.pageInfo.total
    } else {
      ElMessage.error(res.message)
    }
  })
}
load()
const reset = () => {
  data.name = null
  data.type = null
  load()
}

const handleAdd = () => {
  data.formVisible = true
  data.form = {}
  valueHtml.value = '' // 清空编辑器内容
}

const add = () => {
  // formRef 是表单的引用
  formRef.value.validate((valid) => {
    if (valid) {   // 验证通过的情况下
      addFeaturedCompany(data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('新增成功')
          load()
        } else {
          ElMessage.error(res.message || '新增失败')
        }
      }).catch(err => {
        console.error('新增特色企业失败:', err)
        ElMessage.error('系统异常，请检查内容格式或联系管理员')
      })
    }
  })
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))  // 深度拷贝数据
  data.formVisible = true
  valueHtml.value = data.form.scope || '' // 设置编辑器内容
}

const update = () => {
  // formRef 是表单的引用
  formRef.value.validate((valid) => {
    if (valid) {   // 验证通过的情况下
      updateFeaturedCompany(data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('修改成功')
          load()
        } else {
          ElMessage.error(res.message || '修改失败')
        }
      }).catch(err => {
        console.error('修改特色企业失败:', err)
        ElMessage.error('系统异常，请检查内容格式或联系管理员')
      })
    }
  })
}

const save = () => {
  data.form.id ? update() : add()
}

const del = (id) => {
  ElMessageBox.confirm('删除后无法恢复，您确认删除吗？', '删除确认', { type: 'warning' }).then(res => {
    deleteFeaturedCompany(id).then(res => {
      if (res.code === 20000) {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  }).catch(err => {})
}

const handleSelectionChange = (rows) => {  // rows 就是实际选择的数组
  data.rows = rows
}

const deleteBatch = () => {
  if (data.rows.length === 0) {
    ElMessage.warning('请选择数据')
    return
  }
  ElMessageBox.confirm('删除后无法恢复，您确认删除吗？', '删除确认', { type: 'warning' }).then(res => {
    deleteFeaturedCompanies(data.rows).then(res => {
      if (res.code === 20000) {
        ElMessage.success('批量删除成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  }).catch(err => {})
}

// 组件销毁时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

// 移除HTML标签，用于显示工具提示
const removeHtmlTags = (html) => {
  if (!html) return ''
  return html.replace(/<[^>]*>/g, '')
}

// 截断并清理HTML内容用于表格显示
const truncateAndStripHtml = (html) => {
  if (!html) return ''
  // 先移除HTML标签
  let text = removeHtmlTags(html)
  // 替换多个连续的空白字符（包括换行符）为单个空格
  text = text.replace(/\s+/g, ' ')
  // 如果文本太长则截断
  if (text.length > 100) {
    text = text.substring(0, 100) + '...'
  }
  return text
}

</script>

<style>
.richtext-content {
  max-height: 60px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  cursor: pointer;
}
</style>
