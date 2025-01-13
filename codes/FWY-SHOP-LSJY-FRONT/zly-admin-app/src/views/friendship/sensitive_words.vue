<!-- 敏感词管理 -->
<template>
  <div id="SensitiveWords" class="app-container">

    <div class="filter-container">
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="toAdd">添加敏感词</el-button>
      <el-input v-model="listQuery.keyWord" placeholder="关键词搜索" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="全部" name="first">
        <el-table :data="pageInfo.datas" border stripe height="660" style="width: 100%;">
          <el-table-column prop="id" label="序号" width="100" />
          <el-table-column prop="sensitiveWords" label="敏感词" width="400" />
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="primary" @click="keyWordDelete(scope.$index, scope.row.id )">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

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

    <el-dialog :visible.sync="dialogVisible" title="新增敏感词">
      <el-form ref="form" label-width="80px" label-position="right" size="medium">
        <el-form-item label="词组：" prop="word">
          <el-input v-model="word" placeholder="请填写" maxlength="16" show-word-limit />
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="resetForm('form')"> 取 消 </el-button>
        <el-button type="primary" @click="save('form')">提交保存</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { sensitiveWordsList, sensitiveWordsAdd, sensitiveWordsDelete } from '@/api/client'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        keyWord: null,
        pageNum: 1,
        pageSize: 20
      },
      word: null,
      dialogVisible: false,
      pageInfo: {}
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    toAdd() {
      this.dialogVisible = true
    },
    resetForm(formName) {
      this.dialogVisible = false
    },
    save(formName) {
      sensitiveWordsAdd({sensitiveWord: this.word}).then((res) => {
        if (res.data.code === 200) {
          this.$notify.success({ title: '新增敏感词成功' })
          this.resetForm()
          this.loadDatas()
          this.dialogVisible = false
        }
      })
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
    keyWordDelete(index, id) {
      sensitiveWordsDelete({ sensitiveWordId: id }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '删除成功' })
          this.pageInfo.datas.splice(index, 1)
        }
      })
    },
    // 获取数据
    loadDatas() {
      sensitiveWordsList(this.listQuery).then((res) => {
        //console.log(res);
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
