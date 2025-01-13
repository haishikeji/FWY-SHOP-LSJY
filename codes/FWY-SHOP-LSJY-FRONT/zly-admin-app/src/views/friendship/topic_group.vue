<!-- 话题群聊页面 -->
<template>
  <div id="topic_group" class="app-container">

    <div class="filter-container">
      <el-input v-model="listQuery.userId" placeholder="输入房主id搜索" style="width: 200px;" class="filter-item" type="text" maxlength="15" minlength="7" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入昵称搜索" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <span v-if="this.listQuery.status == 5">推荐状态：</span>
      <el-select v-if="this.listQuery.status == 5" v-model="listQuery.recommendToIndex" style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option v-for="item in recommendSelect" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="待审核" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户ID" width="100" />
          <el-table-column prop="nickName" label="房主昵称" width="110" />
          <el-table-column label="头像" width="102">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column prop="tag" label="话题标签" width="100" />
          <el-table-column prop="title" label="主题名称" />
          <el-table-column label="聊天室背景" width="122">
            <template slot-scope="scope">
              <img :src="scope.row.coverImg" style="width: 100px;">
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="100" />
          <el-table-column label="操作" width="200">
            <template slot-scope="scope" style="text-align:center;">
              <el-button type="success" @click="verify(scope.$index, scope.row.topicId, 4)">通过</el-button>
              <el-button @click="verify(scope.$index, scope.row.topicId, 3)">不通过</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="待推荐" name="second">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户ID" width="100" />
          <el-table-column prop="nickName" label="房主昵称" width="110" />
          <el-table-column label="头像" width="102">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column prop="tag" label="话题标签" width="100" />
          <el-table-column prop="title" label="主题名称" />
          <el-table-column label="聊天室背景" width="122">
            <template slot-scope="scope">
              <img :src="scope.row.coverImg" style="width: 100px;">
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="100" />
          <el-table-column label="操作" width="200">
            <template slot-scope="scope" style="text-align:center;">
              <el-button type="primary" @click="verify(scope.$index, scope.row.topicId, 5)">推荐</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已推荐" name="third">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户ID" width="100" />
          <el-table-column prop="nickName" label="房主昵称" width="110" />
          <el-table-column label="头像" width="102">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column prop="tag" label="话题标签" width="100" />
          <el-table-column prop="title" label="主题名称" />
          <el-table-column label="聊天室背景" width="122">
            <template slot-scope="scope">
              <img :src="scope.row.coverImg" style="width: 100px;">
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="100" />
          <el-table-column label="操作" width="164">
            <template slot-scope="scope" style="text-align:center;">
              <el-button type="danger" @click="verify(scope.$index, scope.row.topicId, 6)">下架</el-button>
              <el-button v-if="scope.row.recommandStatus == 1" type="primary" style="margin:10px 0;" @click="recommand(scope.row.topicId, 0)">推荐至首页</el-button>
              <el-button v-else style="margin:10px 0;" @click="recommand(scope.row.topicId, 1)">取消推荐至首页</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已下架" name="four">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户ID" width="100" />
          <el-table-column prop="nickName" label="房主昵称" width="110" />
          <el-table-column label="头像" width="102">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width:80px;">
            </template>
          </el-table-column>
          <el-table-column prop="tag" label="话题标签" width="100" />
          <el-table-column prop="title" label="主题名称" />
          <el-table-column label="聊天室背景" width="122">
            <template slot-scope="scope">
              <img :src="scope.row.coverImg" style="width: 100px;">
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="100" />
          <el-table-column label="操作" width="200">
            <template slot-scope="scope" style="text-align:center;">
              <el-button type="warning" @click="verify(scope.$index, scope.row.topicId, 2)">上架</el-button>
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
import { topic_groupList, topic_groupModify, topic_groupRecommand } from '@/api/topics'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        userId: null,
        nickName: null,
        recommendToIndex: null,
        pageNum: 1,
        pageSize: 20,
        status: 2
      },
      recommendSelect: [
        { id: null, name: '全部' },
        { id: 0, name: '已推荐首页' },
        { id: 1, name: '未推荐首页' }
      ],
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
        this.listQuery.status = 2
        this.loadDatas()
      } else if (tab.name === 'second') {
        this.listQuery.status = 4
        this.loadDatas()
      } else if (tab.name === 'third') {
        this.listQuery.status = 5
        this.loadDatas()
      } else if (tab.name === 'four') {
        this.listQuery.status = 6
        this.loadDatas()
      }
    },
    verify(index, id, status) {
      topic_groupModify({ topicId: id, status: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作成功' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    recommand(id, status) {
      topic_groupRecommand({ topicId: id, recommandStatus: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作成功' })
          this.loadDatas()
        }
      })
    },
    // 获取数据
    loadDatas() {
      topic_groupList(this.listQuery).then((res) => {
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
