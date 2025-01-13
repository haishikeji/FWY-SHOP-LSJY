<!-- 测试页面 -->
<template>
  <div id="testView" class="app-container">

    <div class="filter-container">
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="toAdd">设置比例</el-button>
      <el-input v-model="listQuery.userId" placeholder="输入需要搜索id" style="width: 200px;" class="filter-item" type="text" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="全部" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="id" width="100" />
          <el-table-column prop="nickName" label="昵称" width="100" />
          <el-table-column label="头像" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column prop="rate" label="固定比例" />
          <el-table-column label="操作" >
            <template slot-scope="scope">
              <el-button type="text" @click="verify(scope.$index, scope.row.userId, scope.row.rate)">
                <label style="color: #FF4D51">编辑</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog :visible.sync="dialogVisible" title="设置比例">
      <div v-if="userInfo.headImg" class="userInfo_view">
        <img :src="userInfo.headImg" alt="">
        <span>{{userInfo.nickName}}</span>
      </div>
      <el-form label-width="100px" label-position="right" size="medium">
        <el-form-item label="输入id：">
          <el-input v-model="rowSound.userId" type="text" style="width:300px" />
          <el-button type="primary" @click="checkUserId(rowSound.userId)">
            查询
          </el-button>
        </el-form-item>
        <el-form-item label="分成比例：">
          <el-input v-model="rowSound.rate" type="text" style="width:300px" />
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="info" plain @click="resetForm()">取消</el-button>
        <el-button type="primary" @click="save()">保存</el-button>
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
import { userRateManagerList, userRateManagerQuery, userRateManagerSubmit } from '@/api/client'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

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
      dialogVisible: false,
      rowSound: {
        userId: null,
        rate: null
      },
      userInfo: {}
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    checkUserId(userId) {
      userRateManagerQuery({ userId: userId }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.userInfo = ret.data
        }
      })
    },
    save() {
      if (this.rowSound.userId == null) {
        this.$notify.error({ title: 'id不能为空!' })
      } else if (this.rowSound.rate == null) {
        this.$notify.error({ title: '比例不能为空!' })
      } else {
        userRateManagerSubmit(this.rowSound).then(res => {
          const ret = res.data
          if (ret.code === 200) {
            this.$notify.success({ title: '修改成功' })
            this.loadDatas()
            this.dialogVisible = false
          }
        })
      }
    },
    toAdd() {
      this.dialogVisible = true
      this.rowSound =  {
        userId: null,
        rate: null
      }
      this.userInfo = {}
    },
    resetForm() {
      this.dialogVisible = false
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
    verify(index, userId, rate) {
      this.rowSound =  {
        userId: userId,
        rate: rate
      }
      this.checkUserId(userId)
      this.dialogVisible = true
    },
    // 获取数据
    loadDatas() {
        userRateManagerList(this.listQuery).then((res) => {
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
.userInfo_view{
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 500px;
  margin-bottom: 20px;
}
.userInfo_view>img{
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin: auto;
  margin-right: 0;
}
.userInfo_view>span{
  margin: auto;
  margin-left: 10px;
}
</style>
