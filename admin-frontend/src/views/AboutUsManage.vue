<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="handleAdd">新 增</el-button>
      <el-button type="danger" @click="deleteBatch">批量删除</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" style="width: 100%" @selection-change="handleSelectionChange"
                :header-cell-style="{ color: '#333', backgroundColor: '#eaf4ff' }">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="aboutUs" label="关于我们描述" min-width="200" show-overflow-tooltip />
        <el-table-column label="功能特性" min-width="150">
          <template #default="scope">
            <el-tag v-for="(feature, index) in parseFeatures(scope.row.features)" :key="index" size="small" style="margin-right: 5px; margin-bottom: 5px">
              {{ feature }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="contactPhone" label="联系电话" width="120" />
        <el-table-column prop="contactEmail" label="联系邮箱" width="150" />
        <el-table-column prop="contactAddress" label="联系地址" min-width="150" show-overflow-tooltip />
        <el-table-column label="操作" width="150">
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

    <el-dialog title="关于我们信息" v-model="data.formVisible" width="60%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="100px" style="padding: 20px 30px 10px 0">
        <el-form-item prop="aboutUs" label="关于我们描述">
          <el-input 
            v-model="data.form.aboutUs" 
            autocomplete="off" 
            placeholder="请输入关于我们描述内容" 
            type="textarea"
            :rows="4"
          />
        </el-form-item>
        <el-form-item prop="features" label="功能特性">
          <el-select v-model="data.form.features" multiple placeholder="请选择功能特性（可多选）" style="width: 100%">
            <el-option label="生产数据统计与分析" value="生产数据统计与分析" />
            <el-option label="材料成本计算器" value="材料成本计算器" />
            <el-option label="设备维护提醒" value="设备维护提醒" />
            <el-option label="质量检测记录" value="质量检测记录" />
            <el-option label="员工绩效管理" value="员工绩效管理" />
            <el-option label="库存管理" value="库存管理" />
            <el-option label="订单跟踪" value="订单跟踪" />
            <el-option label="报表导出" value="报表导出" />
          </el-select>
        </el-form-item>
        <el-form-item prop="contactPhone" label="联系电话">
          <el-input v-model="data.form.contactPhone" autocomplete="off" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item prop="contactEmail" label="联系邮箱">
          <el-input v-model="data.form.contactEmail" autocomplete="off" placeholder="请输入联系邮箱" />
        </el-form-item>
        <el-form-item prop="contactAddress" label="联系地址">
          <el-input v-model="data.form.contactAddress" autocomplete="off" placeholder="请输入联系地址" />
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
import {ElMessage, ElMessageBox} from "element-plus";
import { 
  fetchAboutUsPage, 
  addAboutUs, 
  updateAboutUs, 
  deleteAboutUs 
} from "@/api/aboutUs.js";

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}"),
  pageNum: 1,
  pageSize: 10,
  total: 0,
  tableData: [],
  formVisible: false,
  form: {},
  rules: {
    aboutUs: [
      { required: true, message: '请填写关于我们描述', trigger: 'blur' }
    ],
    features: [
      { required: true, message: '请选择功能特性', trigger: 'change' }
    ],
    contactPhone: [
      { required: true, message: '请填写联系电话', trigger: 'blur' }
    ],
    contactEmail: [
      { required: true, message: '请填写联系邮箱', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
    ],
    contactAddress: [
      { required: true, message: '请填写联系地址', trigger: 'blur' }
    ],
  },
  rows: []
})

const formRef = ref()
const load = () => {
  fetchAboutUsPage({
    pageNum: data.pageNum,
    pageSize: data.pageSize
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

const handleAdd = () => {
  data.formVisible = true
  data.form = {}
}

const add = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      addAboutUs(data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('新增成功')
          load()
        } else {
          ElMessage.error(res.message || '新增失败')
        }
      }).catch(err => {
        console.error('新增关于我们失败:', err)
        ElMessage.error('系统异常，请检查内容格式或联系管理员')
      })
    }
  })
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  // 解析 features 字符串为数组
  if (typeof data.form.features === 'string') {
    try {
      data.form.features = JSON.parse(data.form.features)
    } catch (e) {
      data.form.features = []
    }
  }
  data.formVisible = true
}

const update = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      updateAboutUs(data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('修改成功')
          load()
        } else {
          ElMessage.error(res.message || '修改失败')
        }
      }).catch(err => {
        console.error('修改关于我们失败:', err)
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
    deleteAboutUs(id).then(res => {
      if (res.code === 20000) {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  }).catch(err => {})
}

const handleSelectionChange = (rows) => {
  data.rows = rows
}

const deleteBatch = () => {
  if (data.rows.length === 0) {
    ElMessage.warning('请选择数据')
    return
  }
  ElMessageBox.confirm('删除后无法恢复，您确认删除吗？', '删除确认', { type: 'warning' }).then(res => {
    const promises = data.rows.map(item => deleteAboutUs(item.id))
    Promise.all(promises).then(results => {
      ElMessage.success('批量删除成功')
      load()
    }).catch(err => {
      ElMessage.error('批量删除失败')
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

</script>

<style scoped>

</style>
