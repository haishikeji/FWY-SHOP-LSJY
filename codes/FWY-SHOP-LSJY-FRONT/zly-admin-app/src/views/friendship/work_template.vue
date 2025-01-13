<!-- 文案 -->
<template>
  <div id="bgsoundList" class="app-container">
    <div class="filter-container">
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="toAdd">新增文案</el-button>
      <!-- <el-input v-model="listQuery.title" placeholder="名称" style="width: 200px; margin-left: 50px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.author" placeholder="作者" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.content" placeholder="内容" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.dicItemId" style="width: 140px" class="filter-item" placeholder="不区分类型" clearable @change="handleFilter">
        <el-option v-for="item in dictionaryList" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-button class="filter-item" type="info" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button> -->
    </div>

    <el-table :data="pageInfo.datas" border stripe highlight-current-row max-height="auto" style="width: 100%;">
      <el-table-column label="#" width="50">
        <template slot-scope="scope">
          {{ scope.$index + pageInfo.startRow + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="ID" width="180">
        <template slot-scope="scope">
          <el-button size="medium" type="text" @click="toEdit(scope.$index, scope.row)">{{ scope.row.id }}</el-button>
        </template>
      </el-table-column>
      <!-- <el-table-column label="名称" width="160">
        <template slot-scope="scope">
          <el-button size="medium" type="text" @click="toEdit(scope.$index, scope.row)">{{ scope.row.title }}</el-button>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="类型" prop="dicItemId" :formatter="dicColFmt" width="90" /> -->
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="createTime" label="添加时间" :formatter="createTimeFmt" width="100" />
      <el-table-column label="操作" fixed="right" width="80">
        <template slot-scope="scope">
          <el-popover placement="left" width="350">
            <el-alert title="确认" description="上下架会影响当前数据在手机APP上的使用." type="warning" show-icon />
            <div style="text-align: right; margin-top: 10px">
              <el-button v-if="scope.row.status===0" type="warning" @click="switchStatus(scope.row)">确定下架</el-button>
              <el-button v-else type="success" @click="switchStatus(scope.row)">确定上架</el-button>
            </div>
            <el-button slot="reference" size="mini" type="text">
              <label v-if="scope.row.status===0" style="color: #61C091">下架</label>
              <label v-else style="color: #FF4D51">上架</label>
            </el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination
        :current-page.sync="listQuery.pageNum"
        :page-size="listQuery.pageSize"
        :total="pageInfo.total"
        :page-sizes="[10, 20, 50, 80, 100, 200, 300]"
        layout="total, prev, pager, next, sizes, jumper"
        @size-change="pageSizeChange"
        @current-change="pagingChange"
      />
    </div>

    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='new'?'新增':'修改方案'">
      <el-form ref="bgForm" :rules="Rules" :model="rowData" label-width="80px" label-position="right" size="medium">
        <!-- <el-form-item label="名称" prop="title">
          <el-input v-model="rowData.title" placeholder="请填写名称" maxlength="32" show-word-limit />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="rowData.author" placeholder="请填写作者" maxlength="32" show-word-limit />
        </el-form-item>
        <el-form-item label="类型" prop="dicItemId">
          <el-select v-model="rowData.dicItemId" style="width:100%">
            <el-option v-for="item in dictionaryList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item> -->
        <el-form-item label="内容" prop="content">
          <el-input v-model="rowData.content" type="textarea" :autosize="{ minRows: 5, maxRows: 10}" maxlength="255" show-word-limit placeholder="请填写声卡文案" />
        </el-form-item>
        <el-form-item label="上下架" prop="status">
          <el-radio-group v-model="rowData.status">
            <el-radio :label="0">上架</el-radio>
            <el-radio :label="1">下架</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="resetForm('bgForm')"> 取 消 </el-button>
        <el-button type="primary" @click="save('bgForm')">提交保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { deepClone, parseTime } from '@/utils'
import { templateList, addTemplate, editTemplate, setTemplateStatus } from '@/api/work'
import { scrollTo } from '@/utils/scrollTo'
// const defaultRowData = {
//   id: 0,
//   title: '',
//   author: '',
//   dicItemId: '',
//   content: '',
//   status: 0
// }
const Rules = {
  title: [
    { required: true, message: '请填写名称', trigger: 'blur' },
    { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' }
  ],
  author: [
    { required: true, message: '需要填写作者', trigger: 'blur' }
  ],
  dicItemId: [
    { required: true, message: '请选择类别', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '需要内容', trigger: 'blur' },
    { min: 3, max: 255, message: '长度在 3 到 255 个字符', trigger: 'blur' }
  ]
}
export default {
  data() {
    return {
      dictionaryList: [],
      listQuery: {
        // title: '',
        // author: '',
        // dicItemId: '',
        // content:'',
        pageNum: 1,
        pageSize: 20
      },
      pageInfo: {},
      dialogVisible: false,
      dialogType: 'new',
      rowData: {},
      Rules: Object.assign({}, Rules)
    }
  },
  created() {
    this.loadWorkTemplates()
  },
  methods: {
    loadWorkTemplates() {
      templateList(this.listQuery).then((res) => {
        const ret = res.data.data
        this.dictionaryList = ret.dictionaryList
        this.pageInfo = ret.pageInfo
      })
    },
    handleFilter() {
      this.loadWorkTemplates()
    },
    pageSizeChange(num) {
      this.listQuery.pageSize = num
      this.pagingChange(1)
    },
    pagingChange(num) {
      this.listQuery.pageNum = num
      this.loadWorkTemplates()
      scrollTo(0, 600)
    },
    // 类型
    // dicColFmt(row, column) {
    //   let typeName = '---'
    //   for (const item of this.dictionaryList) {
    //     if (item.id === row.dicItemId) {
    //       typeName = item.name
    //       break
    //     }
    //   }
    //   return typeName
    // },
    createTimeFmt(row, column) {
      return parseTime(row.createTime, '{y}-{m}-{d}')
    },
    switchStatus(row) {
      const status = row.status === 0 ? 1 : 0
      setTemplateStatus({ id: row.id, status: status }).then((res) => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '上/下架操作成功' })
          this.loadWorkTemplates()
        }
      })
    },
    toAdd() {
      this.dialogVisible = true
      this.dialogType = 'new'
      this.rowData.id = 0
      this.rowData.status = 0
    },
    toEdit(index, row) {
      this.rowData = deepClone(row)
      this.dialogVisible = true
      this.dialogType = 'edit'
    },
    resetForm(formName) {
      this.rowData = {}
      this.rowData.id = 0
      this.rowData.status = 0
      this.dialogVisible = false
      this.$refs[formName].resetFields()
      this.$refs[formName].clearValidate()
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (!valid) {
          return
        }
        if (this.rowData.id === 0 && this.dialogType === 'new') {
          addTemplate(this.rowData).then((res) => {
            if (res.data.code === 200) {
              this.$notify.success({ title: '成功.' })
              this.resetForm(formName)
              this.loadWorkTemplates()
            }
          })
        } else {
          editTemplate(this.rowData).then((res) => {
            if (res.data.code === 200) {
              this.$notify.success({ title: '更新成功' })
              this.resetForm(formName)
              this.loadWorkTemplates()
            }
          })
        }
      })
    }
  }
}
</script>

<style>
</style>
