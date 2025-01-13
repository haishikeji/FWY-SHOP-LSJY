<!-- 测试页面 -->
<template>
  <div id="testView" class="app-container">

    <div class="filter-container">
      <el-date-picker v-model="listQuery.queryDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
      <el-select v-model="listQuery.financialDataType" placeholder="请输入U豆Q糖项目">
        <el-option label="平台充值总u豆数量" value="0"></el-option>
        <el-option label="系统赠送总u豆" value="1"></el-option>
        <el-option label="提现总q糖" value="2"></el-option>
        <el-option label="平台用户u豆总余额" value="3"></el-option>
        <el-option label="平台用户q糖总余额" value="4"></el-option>
        <el-option label="背包礼物总价值(u豆)" value="5"></el-option>
        <el-option label="所有类型" value="6"></el-option>
      </el-select>

      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="每日明细" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="rechargeBeans" label="平台充值u豆数量" />
          <el-table-column prop="systemSendBeans" label="系统赠送u豆" />
          <el-table-column prop="withDrawalSugars" label="提现总q糖" />
          <el-table-column prop="recordTime" label="日期" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="每日结余" name="second">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="totalBeanNum" label="平台用户u豆总余额" />
          <el-table-column prop="totalPageGiftPrice" label="背包礼物总价值(u豆)" />
          <el-table-column prop="totalSugarNum" label="平台用户q糖总余额" />
          <el-table-column prop="recordTime" label="日期" />
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
import { statistical_accountList } from '@/api/admin_user'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        queryDate: null,
        financialDataType: '6',
        pageNum: 1,
        pageSize: 20,
        queryType: 0
      },
      pageInfo: {}
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    handleFilter() {
      this.loadDatas()
    },
    handleClick(tab, event) {
      this.listQuery.pageNum = 1
      if (tab.name === 'first') {
        this.listQuery.queryType = 0
        this.loadDatas()
      } else if (tab.name === 'second') {
        this.listQuery.queryType = 1
        this.loadDatas()
      }
    },
    // 获取数据
    loadDatas() {
      statistical_accountList(this.listQuery).then((res) => {
        //console.log(res)
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
