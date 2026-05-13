<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input clearable @clear="load" style="width: 260px; margin-right: 5px" v-model="data.name" placeholder="请输入产品名称查询" :prefix-icon="Search"></el-input>
      <el-input clearable @clear="load" style="width: 260px; margin-right: 5px" v-model="data.spec" placeholder="请输入规格型号查询" :prefix-icon="Search"></el-input>
      <el-date-picker
          v-model="data.date"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择日期查询"
          style="width: 260px; margin-right: 5px"
          @change="load"
      />
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
        <el-table-column prop="price" label="价格">
          <template #default="scope">
            <span v-if="scope.row.price">{{ scope.row.price.toFixed(2) }}</span>
            <span v-else>0.00</span>
          </template>
        </el-table-column>
        <el-table-column prop="change" label="涨跌幅">
          <template #default="scope">
            <span :style="{ color: scope.row.change > 0 ? 'red' : (scope.row.change < 0 ? 'green' : '#333') }">
              {{ scope.row.change > 0 ? '+' : '' }}{{ scope.row.change }}%
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="日期" />
        <el-table-column prop="createTime" label="创建时间" />
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

    <el-dialog title="钢材产品价格信息" v-model="data.formVisible" width="30%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="80px" style="padding: 20px 30px 10px 0">
        <el-form-item prop="name" label="产品名称">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item prop="spec" label="规格型号">
          <el-input v-model="data.form.spec" autocomplete="off" placeholder="请输入规格型号"/>
        </el-form-item>
        <el-form-item prop="price" label="价格">
          <el-input-number v-model="data.form.price" :precision="2" :step="1" :min="0" placeholder="请输入价格" style="width: 100%"/>
        </el-form-item>
        <el-form-item prop="change" label="涨跌幅">
          <el-input-number v-model="data.form.change" :precision="2" :step="0.1" :min="-100" :max="100" placeholder="请输入涨跌幅百分比" style="width: 100%"/>
        </el-form-item>
        <el-form-item prop="date" label="日期">
          <el-date-picker
              v-model="data.form.date"
              type="date"
              placeholder="选择日期"
              value-format="YYYY-MM-DD"
              style="width: 100%"
          />
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
import request from "@/utils/request.js";
import {ElMessage,ElMessageBox} from "element-plus";


const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}"),
  name: null,
  spec: null,
  date: null,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  tableData: [],
  formVisible: false,
  form: {},
  rules: {
    name: [
      { required: true, message: '请填写产品名称', trigger: 'blur' }
    ],
    spec: [
      { required: true, message: '请填写规格型号', trigger: 'blur' }
    ],
    price: [
      { required: true, message: '请填写价格', trigger: 'blur' }
    ],
    change: [
      { required: true, message: '请填写涨跌幅', trigger: 'blur' }
    ],
    date: [
      { required: true, message: '请选择日期', trigger: 'change' }
    ],
  },
  rows: []
})

const formRef = ref()
const load = () => {
  request.get('/steelProductPrice/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      spec: data.spec,
      date: data.date
    }
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
  data.spec = null
  data.date = null
  load()
}

const handleAdd = () => {
  data.formVisible = true
  data.form = {}
}

const add = () => {
  // formRef 是表单的引用
  formRef.value.validate((valid) => {
    if (valid) {   // 验证通过的情况下
      request.post('/steelProductPrice/add', data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('新增成功')
          load()
        } else {
          ElMessage.error(res.message)
        }
      })
    }
  })
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))  // 深度拷贝数据
  data.formVisible = true
}

const update = () => {
  // formRef 是表单的引用
  formRef.value.validate((valid) => {
    if (valid) {   // 验证通过的情况下
      request.put('/steelProductPrice/update', data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('修改成功')
          load()
        } else {
          ElMessage.error(res.message)
        }
      })
    }
  })
}

const save = () => {
  data.form.id ? update() : add()
}

const del = (id) => {
  ElMessageBox.confirm('删除后无法恢复，您确认删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/steelProductPrice/delete/' + id).then(res => {
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
    request.delete('/steelProductPrice/deleteBatch', { data: data.rows }).then(res => {
      if (res.code === 20000) {
        ElMessage.success('批量删除成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  }).catch(err => {})
}

</script>
