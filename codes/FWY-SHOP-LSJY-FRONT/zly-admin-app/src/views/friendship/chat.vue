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

    <el-table :data="pageInfo.list" stripe border style="width: 100%;">
      <el-table-column prop="userId" label="id" />
      <el-table-column label="头像" width="110">
        <template slot-scope="scope">
          <img :src="scope.row.headImg" style="width: 80px;">
        </template>
      </el-table-column>
      <el-table-column prop="nickName" label="昵称" />
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
      <el-table-column prop="income" label="获得收入" width="80" />
      <el-table-column prop="createTime" label="注册时间" width="100" />
      <el-table-column label="操作" fixed="right" width="160">
        <template slot-scope="scope">
          <div v-if="scope.row.gender===1">
            <el-badge :value="100" :max="99" class="item">
              <el-button type="primary" @click="goChat(scope.$index, scope.row)">去聊天</el-button>
            </el-badge>
          </div>
          <div v-else>
            <el-button type="primary" @click="goChat(scope.$index, scope.row)">去聊天</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- <el-dialog :visible.sync="chatWindowView" title="聊天" top="2vh" width="800px">
      <div style="height:800px;height:500px;">
        <iframe :src="chatUrl" width="100%" height="500" align="left">
        </iframe>
      </div>
    </el-dialog> -->

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
import { getSystemaccountList } from '@/api/user'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
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
      pageInfo: {
        list: []
      },
      chatWindowView: false,

      
      chatUrl: 'http://front.test.uqchat.cn/chat/im.html?rongCloudToken=Tr0K7ihSc4rN8n9TwruEsVE8/E89OkN9siQWC/gKIE8=@rdkq.cn.rongnav.com;rdkq.cn.rongcfg.com',   // TODO 链接配置
    }
  },
  created() {
    this.getUserData()
  },
  mounted: function() {
    // this.$notify.success({ title: '测试' });
    // this.$notify.error({ title: '' });
    // setInterval(function(){
    //   console.log('⚠️警告🍎')
    // },3000)
  },
  methods: {
    goChat(index, e) {
      // this.chatWindowView = true
      // this.pageInfo.list.splice(index, 1)
      // console.log(e)

      // TODO 链接配置
      this.linkNewUrl('http://front.test.uqchat.cn/chat/im.html?rongCloudToken=Tr0K7ihSc4rN8n9TwruEsVE8/E89OkN9siQWC/gKIE8=@rdkq.cn.rongnav.com;rdkq.cn.rongcfg.com')
    },
    linkNewUrl(url) {
      window.open(url, '_blank') // 新窗口打开外链接
    },
    getUserData() {
      getSystemaccountList(this.listQuery).then((res) => {
        const that = this
        if (res.data.code === 200) {
          this.pageInfo = res.data.data
          // setInterval(function(){
          //   that.pageInfo.list.unshift(res.data.data.list[13])
          // },3000)
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
.item {
  margin-top: 10px;
  margin-right: 40px;
}
</style>
