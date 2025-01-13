<!-- 陪玩订单 -->
<template>
  <div id="play_order" class="app-container">

    <div class="filter-container">
      <el-input v-model="listQuery.orderNo" placeholder="输入需要搜索id" style="width: 200px;" class="filter-item" type="text" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="待付款" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="orderId" label="订单号" width="100" />
          <el-table-column label="用户头像" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column label="用户信息">
            <template slot-scope="scope">
              <div style="display:flex;flex-direction:column;">
                <span>{{ scope.row.nickName }}</span>
                <span>id：{{ scope.row.userId }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="gameName" label="陪玩游戏" width="100" />
          <el-table-column prop="numberOfBoard" label="陪玩局数" width="120" />
          <el-table-column label="订单金额" width="100">
            <template slot-scope="scope">
              <span>￥{{ scope.row.money }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="totalBeans" label="消耗U豆" width="100" />
          <el-table-column prop="serviceStartTime" label="陪玩时间" width="100" />
          <el-table-column prop="orderCreateTime" label="下单时间" width="100" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="待接单" name="second">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="orderId" label="订单号" width="100" />
          <el-table-column label="用户头像" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column label="用户信息">
            <template slot-scope="scope">
              <div style="display:flex;flex-direction:column;">
                <span>{{ scope.row.nickName }}</span>
                <span>id：{{ scope.row.userId }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="gameName" label="陪玩游戏" width="100" />
          <el-table-column prop="numberOfBoard" label="陪玩局数" width="120" />
          <el-table-column label="订单金额" width="100">
            <template slot-scope="scope">
              <span>￥{{ scope.row.money }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="totalBeans" label="消耗U豆" width="100" />
          <el-table-column prop="serviceStartTime" label="陪玩时间" width="100" />
          <el-table-column prop="orderCreateTime" label="下单时间" width="100" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="待服务" name="third">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="orderId" label="订单号" width="100" />
          <el-table-column label="用户头像" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column label="用户信息">
            <template slot-scope="scope">
              <div style="display:flex;flex-direction:column;">
                <span>{{ scope.row.nickName }}</span>
                <span>id：{{ scope.row.userId }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="gameName" label="陪玩游戏" width="100" />
          <el-table-column prop="numberOfBoard" label="陪玩局数" width="120" />
          <el-table-column label="订单金额" width="100">
            <template slot-scope="scope">
              <span>￥{{ scope.row.money }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="totalBeans" label="消耗U豆" width="100" />
          <el-table-column prop="serviceStartTime" label="陪玩时间" width="100" />
          <el-table-column prop="orderCreateTime" label="下单时间" width="100" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="服务中" name="four">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="orderId" label="订单号" width="100" />
          <el-table-column label="用户头像" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column label="用户信息">
            <template slot-scope="scope">
              <div style="display:flex;flex-direction:column;">
                <span>{{ scope.row.nickName }}</span>
                <span>id：{{ scope.row.userId }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="gameName" label="陪玩游戏" width="100" />
          <el-table-column prop="numberOfBoard" label="陪玩局数" width="120" />
          <el-table-column label="订单金额" width="100">
            <template slot-scope="scope">
              <span>￥{{ scope.row.money }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="totalBeans" label="消耗U豆" width="100" />
          <el-table-column prop="serviceStartTime" label="陪玩时间" width="100" />
          <el-table-column prop="orderCreateTime" label="下单时间" width="100" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已完成" name="fifth">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="orderId" label="订单号" width="100" />
          <el-table-column label="用户头像" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column label="用户信息">
            <template slot-scope="scope">
              <div style="display:flex;flex-direction:column;">
                <span>{{ scope.row.nickName }}</span>
                <span>id：{{ scope.row.userId }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="gameName" label="陪玩游戏" width="100" />
          <el-table-column prop="numberOfBoard" label="陪玩局数" width="120" />
          <el-table-column label="订单金额" width="100">
            <template slot-scope="scope">
              <span>￥{{ scope.row.money }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="totalBeans" label="消耗U豆" width="100" />
          <el-table-column prop="serviceStartTime" label="陪玩时间" width="100" />
          <el-table-column prop="orderCreateTime" label="下单时间" width="100" />
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
import { play_orderList } from '@/api/order'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        orderNo: null,
        nickName: null,
        pageNum: 1,
        pageSize: 20,
        status: 0
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
        this.listQuery.status = 0
        this.loadDatas()
      } else if (tab.name === 'second') {
        this.listQuery.status = 1
        this.loadDatas()
      } else if (tab.name === 'third') {
        this.listQuery.status = 3
        this.loadDatas()
      } else if (tab.name === 'four') {
        this.listQuery.status = 5
        this.loadDatas()
      } else if (tab.name === 'fifth') {
        this.listQuery.status = 7
        this.loadDatas()
      }
    },
    // 获取数据
    loadDatas() {
      play_orderList(this.listQuery).then((res) => {
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
.el-table__body-wrapper {
    overflow-y: auto;
}
</style>
