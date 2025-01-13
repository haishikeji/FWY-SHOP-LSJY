<template>
  <div id="testView" class="app-container">

    <div class="filter-container">
      <el-input v-model="listQuery.id" placeholder="输入需要搜索id" style="width: 200px;" class="filter-item" type="text" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.phone" placeholder="输入需要搜索手机号" style="width: 200px;" class="filter-item" type="text" maxlength="15" minlength="7" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
      <el-table-column prop="" label="酒厂名称" width="120" />
      <el-table-column prop="" label="酒桶数量（3L）" width="140" />
      <el-table-column prop="" label="剩余酒桶数量（3L）" width="160" />
      <el-table-column prop="" label="酒桶数量（5L）" width="140" />
      <el-table-column prop="" label="剩余酒桶数量（5L）" width="160" />
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

  </div>
</template>

<script>
import { statistical } from '@/api/privatedomain'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        id: null,
        nickName: null,
        phone: null,
        pageNum: 1,
        pageSize: 20,
        status: 1
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
        this.listQuery.status = 1
        this.loadDatas()
      } else if (tab.name === 'second') {
        this.listQuery.status = 2
        this.loadDatas()
      }
    },
    verify(index, id, status) {
      // console.log(index, id, status)
      verifyDynamic({ dailyDynamicId: id, status: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '测试' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    // 获取数据
    loadDatas() {
      //   dynamicList(this.listQuery).then((res) => {
      //     console.log(res);
      //     this.pageInfo = res.data.data
      //   })
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
