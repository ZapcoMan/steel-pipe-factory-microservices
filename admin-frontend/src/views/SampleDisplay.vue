<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input clearable @clear="load" style="width: 260px; margin-right: 5px" v-model="data.name" placeholder="请输入产品名称查询" :prefix-icon="Search"></el-input>
      <el-input clearable @clear="load" style="width: 260px; margin-right: 5px" v-model="data.category" placeholder="请输入分类查询" :prefix-icon="Search"></el-input>
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
        <el-table-column prop="name" label="产品名称" />
        <el-table-column prop="spec" label="规格型号" />
        <el-table-column prop="material" label="材质" />
        <el-table-column label="特性" >
          <template #default="scope">
            <el-tag v-for="(feature, index) in parseFeatures(scope.row.features)" :key="index" size="small" style="margin-right: 5px; margin-bottom: 5px">
              {{ feature }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="application" label="应用领域" />
        <el-table-column label="是否新品">
          <template #default="scope">
            <el-tag :type="scope.row.isNew === 1 ? 'success' : 'info'">
              {{ scope.row.isNew === 1 ? '是' : '否' }}
            </el-tag>
          </template>
        </el-table-column>
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

    <el-dialog title="样品展示信息" v-model="data.formVisible" width="50%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="80px" style="padding: 20px 30px 10px 0">
        <el-form-item prop="name" label="产品名称">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item prop="spec" label="规格型号">
          <el-input v-model="data.form.spec" autocomplete="off" placeholder="请输入规格型号"/>
        </el-form-item>
        <el-form-item prop="material" label="材质">
          <el-input v-model="data.form.material" autocomplete="off" placeholder="请输入材质"/>
        </el-form-item>
        <el-form-item prop="features" label="特性">
          <el-select v-model="data.form.features" multiple placeholder="请选择特性（可多选）" style="width: 100%">
            <el-option label="高强度" value="高强度" />
            <el-option label="耐压" value="耐压" />
            <el-option label="耐腐蚀" value="耐腐蚀" />
            <el-option label="成本低" value="成本低" />
            <el-option label="易加工" value="易加工" />
            <el-option label="规格全" value="规格全" />
            <el-option label="截面利用率高" value="截面利用率高" />
            <el-option label="便于连接" value="便于连接" />
            <el-option label="承载能力强" value="承载能力强" />
            <el-option label="截面惯性矩大" value="截面惯性矩大" />
            <el-option label="抗弯性能好" value="抗弯性能好" />
            <el-option label="美观实用" value="美观实用" />
            <el-option label="抗弯能力强" value="抗弯能力强" />
            <el-option label="截面合理" value="截面合理" />
            <el-option label="节省材料" value="节省材料" />
            <el-option label="截面形状合理" value="截面形状合理" />
            <el-option label="便于固定" value="便于固定" />
            <el-option label="截面模数大" value="截面模数大" />
            <el-option label="省材料" value="省材料" />
          </el-select>
        </el-form-item>
        <el-form-item prop="application" label="应用领域">
          <el-input v-model="data.form.application" autocomplete="off" placeholder="请输入应用领域"/>
        </el-form-item>
        <el-form-item prop="image" label="图片">
          <el-upload
              :action="config.uploadURL"
              :headers="{ token: data.user.token }"
              :on-success="handleFileSuccess"
              list-type="picture-card"
              :file-list="data.fileList"
              :on-change="handleFileChange"
              :on-remove="handleFileRemove"
          >
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="category" label="分类">
          <el-select v-model="data.form.category" placeholder="请选择分类" style="width: 100%">
            <el-option label="无缝钢管" value="steel-tube" />
            <el-option label="方管矩形管" value="rectangular" />
            <el-option label="工字钢" value="i-beam" />
            <el-option label="角钢" value="angle" />
            <el-option label="槽钢" value="channel" />
            <el-option label="H 型钢" value="h-beam" />
          </el-select>
        </el-form-item>
        <el-form-item prop="isNew" label="是否新品">
          <el-radio-group v-model="data.form.isNew">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
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
import { reactive, ref } from "vue";
import {Search} from "@element-plus/icons-vue";
import {ElMessage,ElMessageBox} from "element-plus";
import { 
  fetchSampleDisplay, 
  addSampleDisplay, 
  updateSampleDisplay, 
  deleteSampleDisplay, 
  deleteSampleDisplayBatch 
} from "@/api/sampleDisplay.js";
import { config } from "@/config";

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}"),
  name: null,
  category: null,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  tableData: [],
  formVisible: false,
  form: {},
  fileList: [],
  rules: {
    name: [
      { required: true, message: '请填写产品名称', trigger: 'blur' }
    ],
    spec: [
      { required: true, message: '请填写规格型号', trigger: 'blur' }
    ],
    material: [
      { required: true, message: '请填写材质', trigger: 'blur' }
    ],
    features: [
      { required: true, message: '请选择特性', trigger: 'change' }
    ],
    application: [
      { required: true, message: '请填写应用领域', trigger: 'blur' }
    ],
    category: [
      { required: true, message: '请选择分类', trigger: 'change' }
    ],
  },
  rows: []
})

const formRef = ref()
const load = () => {
  fetchSampleDisplay({
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    name: data.name,
    category: data.category
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
  data.category = null
  load()
}

const handleAdd = () => {
  data.formVisible = true
  data.form = { isNew: 0 }
  data.fileList = []
}

const add = () => {
  // formRef 是表单的引用
  formRef.value.validate((valid) => {
    if (valid) {   // 验证通过的情况下
      addSampleDisplay(data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('新增成功')
          load()
        } else {
          ElMessage.error(res.message || '新增失败')
        }
      }).catch(err => {
        console.error('新增样品失败:', err)
        ElMessage.error('系统异常，请检查内容格式或联系管理员')
      })
    }
  })
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))  // 深度拷贝数据
  // 解析 features 字符串为数组
  if (typeof data.form.features === 'string') {
    try {
      data.form.features = JSON.parse(data.form.features)
    } catch (e) {
      data.form.features = []
    }
  }
  // 处理图片列表
  if (data.form.image) {
    data.fileList = [{
      name: '样品图片',
      url: data.form.image
    }]
  } else {
    data.fileList = []
  }
  data.formVisible = true
}

const update = () => {
  // formRef 是表单的引用
  formRef.value.validate((valid) => {
    if (valid) {   // 验证通过的情况下
      updateSampleDisplay(data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('修改成功')
          load()
        } else {
          ElMessage.error(res.message || '修改失败')
        }
      }).catch(err => {
        console.error('修改样品失败:', err)
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
    deleteSampleDisplay(id).then(res => {
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
    deleteSampleDisplayBatch(data.rows.map(item => item.id)).then(res => {
      if (res.code === 20000) {
        ElMessage.success('批量删除成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  }).catch(err => {})
}

// 解析 features 字段
const parseFeatures = (features) => {
  if (!features) return []
  if (Array.isArray(features)) return features
  try {
    return JSON.parse(features)
  } catch (e) {
    return []
  }
}

// 处理图片上传成功
const handleFileSuccess = (res) => {
  console.log('图片上传响应:', res)
  if (res.code === 20000 && res.data) {
    data.form.image = res.data
    ElMessage.success('图片上传成功')
  } else {
    ElMessage.error(res.message || '图片上传失败')
  }
}

// 处理文件改变
const handleFileChange = (file, fileList) => {
  data.fileList = fileList.slice(-1) // 只保留最后一张图片
}

// 处理文件删除
const handleFileRemove = (file, fileList) => {
  data.fileList = []
  data.form.image = ''
}

</script>

<style scoped>

</style>
