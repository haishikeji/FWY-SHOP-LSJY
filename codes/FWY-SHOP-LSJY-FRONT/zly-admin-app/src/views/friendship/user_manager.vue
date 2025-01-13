<template>
  <div id="user" class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.id" placeholder="输入需要搜索id" style="width: 200px;" class="filter-item" type="text" maxlength="15" minlength="7" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.phone" placeholder="输入需要搜索手机号" style="width: 200px;" class="filter-item" type="text" maxlength="15" minlength="7" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.gender" style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option v-for="item in genderSelect" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="全部" name="first">
        <el-table :data="pageInfo.list" stripe border style="width: 100%;">
          <el-table-column prop="userId" label="id" />
          <el-table-column label="头像" width="110">
            <template slot-scope="scope">
              <img v-if="scope.row.headImg" :src="scope.row.headImg" style="width: 80px;">
              <span v-else>无</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="decodeUnicode" prop="nickName" label="昵称" />
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="绑定手机" width="110" />
          <el-table-column prop="workNums" label="形象卡数" width="80" />
          <el-table-column prop="dailyDynamicNums" label="动态数" width="70" />
          <el-table-column label="vip" width="70">
            <template slot-scope="scope">
              <label v-if="scope.row.vip===true" style="color: blue">已开通</label>
              <label v-else>未开通</label>
            </template>
          </el-table-column>
          <el-table-column label="爆灯点亮" width="80">
            <template slot-scope="scope">
              <label v-if="scope.row.explosionLight===true" style="color: blue">已开通</label>
              <label v-else>未开通</label>
            </template>
          </el-table-column>
          <el-table-column label="私信聊天" width="80">
            <template slot-scope="scope">
              <label v-if="scope.row.privateLetter===true" style="color: blue">已开通</label>
              <label v-else>未开通</label>
            </template>
          </el-table-column>
          <el-table-column prop="income" label="获得收入" width="80" />
          <el-table-column prop="createTime" label="注册时间" width="100" />
          <el-table-column label="操作" width="80" fixed="right">
            <template slot-scope="scope">
              <el-popover placement="left" width="350">
                <el-alert title="确认" description="当前操作会影响用户在手机APP上的使用." type="warning" show-icon />
                <div style="text-align: right; margin-top: 10px">
                  <el-button v-if="scope.row.status===0" type="warning" @click="switchStatus(scope.row.userId, 1)">确定冻结</el-button>
                  <el-button v-else type="success" @click="switchStatus(scope.row.userId, 0)">确定解冻</el-button>
                </div>
                <el-button slot="reference" size="mini" type="text">
                  <label v-if="scope.row.status===0" style="color: #E51C23">冻结</label>
                  <label v-else style="color: #1296DB">解冻</label>
                </el-button>
              </el-popover>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已冻结" name="second">
        <el-table :data="pageInfo.list" stripe border style="width: 100%;">
          <el-table-column prop="userId" label="id" />
          <el-table-column label="头像" width="110">
            <template slot-scope="scope">
              <img v-if="scope.row.gender" :src="scope.row.headImg" style="width: 80px;">
              <span v-else>无</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="decodeUnicode" prop="nickName" label="昵称" />
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="绑定手机" width="110" />
          <el-table-column prop="workNums" label="形象卡数" width="80" />
          <el-table-column prop="dailyDynamicNums" label="动态数" width="70" />
          <el-table-column label="vip" width="70">
            <template slot-scope="scope">
              <label v-if="scope.row.vip===true" style="color: blue">已开通</label>
              <label v-else>未开通</label>
            </template>
          </el-table-column>
          <el-table-column label="爆灯点亮" width="80">
            <template slot-scope="scope">
              <label v-if="scope.row.explosionLight===true" style="color: blue">已开通</label>
              <label v-else>未开通</label>
            </template>
          </el-table-column>
          <el-table-column label="私信聊天" width="80">
            <template slot-scope="scope">
              <label v-if="scope.row.privateLetter===true" style="color: blue">已开通</label>
              <label v-else>未开通</label>
            </template>
          </el-table-column>
          <el-table-column prop="income" label="获得收入" width="80" />
          <el-table-column prop="createTime" label="注册时间" width="100" />
          <el-table-column label="操作" width="80" fixed="right">
            <template slot-scope="scope">
              <el-popover placement="left" width="350">
                <el-alert title="确认" description="当前操作会影响用户在手机APP上的使用." type="warning" show-icon />
                <div style="text-align: right; margin-top: 10px">
                  <el-button v-if="scope.row.status===0" type="warning" @click="switchStatus(scope.row.userId, 1)">确定冻结</el-button>
                  <el-button v-else type="success" @click="switchStatus(scope.row.userId, 0)">确定解冻</el-button>
                </div>
                <el-button slot="reference" size="mini" type="text">
                  <label v-if="scope.row.status===0" style="color: #E51C23">冻结</label>
                  <label v-else style="color: #1296DB">解冻</label>
                </el-button>
              </el-popover>
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

  </div>
</template>

<script>
import { getUserList, optUser } from '@/api/user'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        id: null,
        nickName: null,
        phone: null,
        gender: null,
        pageNum: 1,
        pageSize: 20
      },
      genderSelect: [
        { id: null, name: '全部' },
        { id: 0, name: '男' },
        { id: 1, name: '女' }
      ],
      pageInfo: {}
    }
  },
  created() {
    this.getUserData()
  },
  methods: {
    handleClick(tab, event) {
      this.listQuery.pageNum = 1
      if (tab.name === 'first') {
        // this.listQuery.status = null
        delete this.listQuery.status
        this.getUserData()
      } else if (tab.name === 'second') {
        this.listQuery.status = 1
        this.getUserData()
      }
    },
    decodeUnicode(row) {
      const str = row.nickName.replace(/\\/g, '%')
      return unescape(str)
    },
    getUserData() {
      getUserList(this.listQuery).then((res) => {
        const ret = res.data
        if (ret.code === 200) {
          this.pageInfo = ret.data
        }
      })
    },
    switchStatus(id, status) {
      optUser({ userId: id, status: status }).then((res) => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作用户成功' })
          this.getUserData()
        }
      })
    },
    handleFilter() {
      this.getUserData()
    },
    pageSizeChange(num) {
      this.listQuery.pageSize = num
      this.pagingChange(1)
    },
    pagingChange(num) {
      this.listQuery.pageNum = num
      this.getUserData()
      scrollTo(0, 600)
    }
  }
}
</script>

<style>
</style>
