<!-- 靓号管理 -->
<template>
  <div id="testView" class="app-container">

    <div class="filter-container">
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="addPretty">新增靓号</el-button>
      <el-input v-model="listQuery.userId" placeholder="输入需要搜索id" style="width: 200px;" class="filter-item" type="text" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="全部" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="id" label="靓号id" />
          <el-table-column prop="prettyAccount" label="新id" />
          <el-table-column prop="userId" label="原id" />
          <el-table-column prop="nickName" label="昵称" />
          <el-table-column prop="remark" label="备注" />
          <el-table-column prop="createTime" label="创建时间" />
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog :visible.sync="addView" width="600px" top="10px">
      <el-form ref="accountForm" :rules="accountRules" :model="accountData" label-width="180px" label-position="right" size="medium">
        <el-form-item label="请输入靓号：" prop="prettyAccount">
          <el-input v-model="accountData.prettyAccount" style="width:280px" />
        </el-form-item>
        <el-form-item label="请输入使用人ID：" prop="userId">
          <el-input v-model="accountData.userId" style="width:280px" />
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-input v-model="accountData.remark" style="width:280px" />
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="info" plain @click="accountReset('accountForm')">取消</el-button>
        <el-button type="primary" @click="accountAdd('accountForm')">确定</el-button>
      </div>
    </el-dialog>

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

  </div>
</template>

<script>
import { prettyAccountList, prettyAccountSubmit } from '@/api/give_away'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

const accountRules = {
  prettyAccount: [{ required: true, message: '不能为空', trigger: 'blur' }],
  userId: [{ required: true, message: '不能为空', trigger: 'blur' }],
  remark: [{ required: true, message: '不能为空', trigger: 'blur' }],
}

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        userId: null,
        nickName: null,
        pageNum: 1,
        pageSize: 20,
      },
      pageInfo: {},
      addView: false,
			accountData: { 
        prettyAccount: null,
				userId: null,
				remark: null,
			},
			accountRules: Object.assign({}, accountRules),
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    accountAdd() {
      this.$refs['accountForm'].validate((valid) => {
        if (valid) {
          prettyAccountSubmit(this.accountData).then((res) => {
            this.$notify.success({ title: '提交完成。' })
						this.loadDatas()
						this.accountReset()
          })
        }
      })
    },
		accountReset() {
      this.addView = false
      this.$refs['accountForm'].resetFields()
		},
    addPretty() {
      this.addView = true
    },
    handleFilter() {
      this.loadDatas()
    },
    handleClick(tab, event) {
      this.listQuery.pageNum = 1
      if (tab.name === 'first') {
        this.loadDatas()
      }
    },
    // 获取数据
    loadDatas() {
        prettyAccountList(this.listQuery).then((res) => {
          this.pageInfo = res.data.data
        })
    },
    pageSizeChange(num) {
      this.listQuery.pageSize = num
      this.pagingChange(1)
    },
    pagingChange(num) {
      this.listQuery.pageNum = num
      this.loadDatas()
      scrollTo(0, 600)
    }
  }
}
</script>

<style>
</style>
