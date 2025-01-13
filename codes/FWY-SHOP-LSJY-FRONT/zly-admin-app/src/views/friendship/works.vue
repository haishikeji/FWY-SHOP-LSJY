<!-- 作品 -->
<template>
  <div id="works" class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.authorId" placeholder="作者id" style="width: 200px;" class="filter-item" type="text" maxlength="15" minlength="7" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="昵称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <!-- 待审核 -->
      <el-tab-pane label="待审核" name="first">
        <worksTable :tb-datas="pageInfo.datas" :start-row="pageInfo.startRow">

          <el-table-column slot="pic" label="图片" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.pictureUrl" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column slot="opt" label="操作" width="100" fixed="right">
            <template slot-scope="scope">
              <el-popover placement="left" width="350">
                <el-alert title="确认" description="审核成功后会在 待推荐栏 显示." type="warning" show-icon />
                <el-input v-model="statusRemark" placeholder="备注" maxlength="200" style="width: 280px; margin-left: 10px;margin-top: 10px" show-word-limit />
                <div style="text-align: right; margin-top: 10px">
                  <el-button type="danger" @click="verify(scope.$index, scope.row.id, 4)">下架</el-button>
                  <el-button type="success" @click="verify(scope.$index, scope.row.id, 5)">审核通过</el-button>
                </div>
                <el-button slot="reference" size="mini" type="primary">审核</el-button>
              </el-popover>
            </template>
          </el-table-column>

        </worksTable>
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
      </el-tab-pane>
      <!-- 待推荐 -->
      <el-tab-pane label="待推荐" name="verify">
        <worksTable :tb-datas="pageInfo.datas" :start-row="pageInfo.startRow">

          <el-table-column slot="pic" label="图片" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.pictureUrl" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column slot="opt" label="操作" fixed="right" width="100">
            <template slot-scope="scope">
              <el-popover placement="left" width="350">
                <el-alert title="确认" description="推荐后会在 App首页 显示." type="warning" show-icon />
                <el-input v-model="statusRemark" placeholder="备注" maxlength="200" style="width: 280px; margin-left: 10px;margin-top: 10px" show-word-limit />
                <div style="text-align: right; margin-top: 10px">
                  <el-button type="danger" @click="verify(scope.$index, scope.row.id, 1)">取消审核</el-button>
                  <el-button type="success" @click="recommend(scope.$index, scope.row.id, 6)">确定推荐</el-button>
                </div>
                <el-button slot="reference" size="mini" type="success">推荐</el-button>
              </el-popover>
            </template>
          </el-table-column>

        </worksTable>
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

      </el-tab-pane>
      <!-- 已推荐 -->
      <el-tab-pane label="已推荐" name="recommend">

        <worksTable :tb-datas="pageInfo.datas" :start-row="pageInfo.startRow">

          <el-table-column slot="pic" label="图片" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.pictureUrl" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column slot="likeCount" prop="likeCount" label="喜欢人数" width="80" />
          <el-table-column slot="missCount" prop="missCount" label="错过人数" width="80" />
          <el-table-column slot="opt" label="操作" fixed="right" width="100">
            <template slot-scope="scope">
              <el-popover placement="left" width="350">
                <el-alert title="确认" description="取消推荐后 不会在 App首页 显示." type="warning" show-icon />
                <el-input v-model="statusRemark" placeholder="备注" maxlength="200" style="width: 280px; margin-left: 10px;margin-top: 10px" show-word-limit />
                <div style="text-align: right; margin-top: 10px">
                  <el-button type="warning" @click="recommend(scope.$index, scope.row.id, 4)">确定下架</el-button>
                </div>
                <el-button slot="reference" size="mini" type="warning">下架</el-button>
              </el-popover>
            </template>
          </el-table-column>

        </worksTable>
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
      </el-tab-pane>
      <!-- 已下架 -->
      <el-tab-pane label="已下架" name="offed">

        <worksTable :tb-datas="pageInfo.datas" :start-row="pageInfo.startRow">

          <el-table-column slot="pic" label="图片" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.pictureUrl" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column slot="likeCount" prop="likeCount" label="喜欢人数" width="80" />
          <el-table-column slot="missCount" prop="missCount" label="错过人数" width="80" />
          <el-table-column slot="opt" label="操作" fixed="right" width="100">
            <template slot-scope="scope">
              <el-popover placement="left" width="350">
                <el-alert title="确认" description="上架后,将显示在 待审核 栏." type="warning" show-icon />
                <el-input v-model="statusRemark" placeholder="备注" maxlength="200" style="width: 280px; margin-left: 10px;margin-top: 10px" show-word-limit />
                <div style="text-align: right; margin-top: 10px">
                  <el-button type="primary" @click="verify(scope.$index, scope.row.id, 1)">确定上架</el-button>
                </div>
                <el-button slot="reference" size="mini" type="warning">上架</el-button>
              </el-popover>
            </template>
          </el-table-column>

        </worksTable>
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
      </el-tab-pane>

    </el-tabs>

    <el-drawer title="查看作品图片" :visible.sync="drawer" size="60%" direction="rtl">
      <div style="overflow: auto; position: fixed; max-height: 600px; padding-left: 10px;">
        <el-image v-for="url in pics" :key="url" :src="url" />
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { worksList, verifyWorks } from '@/api/work'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'
import worksTable from './works_table_cpn'

export default {
  components: { worksTable },
  data() {
    return {
      activeName: 'first',
      listQuery: {
        authorId: null,
        nickName: null,
        dataType: 1,
        pageNum: 1,
        pageSize: 20
      },
      pageInfo: {
        datas: []
      },
      drawer: false,
      pics: [],
      statusRemark: ''
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    handleClick(tab, event) {
      if (tab.name === 'first') {
        this.listQuery.dataType = 1
        this.loadDatas()
      } else if (tab.name === 'verify') {
        this.listQuery.dataType = 5
        this.loadDatas()
      } else if (tab.name === 'recommend') {
        this.listQuery.dataType = 6
        this.loadDatas()
      } else if (tab.name === 'offed') {
        this.listQuery.dataType = 4
        this.loadDatas()
      }
    },
    handleFilter() {
      this.loadDatas()
    },
    loadDatas() {
      worksList(this.listQuery).then(res => {
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
    },
    verify(index, id, status) { // 2是审核通过 3是拒绝
      console.log('id=', id, 'status=', status)
      let msg = '成功设置审核'
      if (status === 1) {
        msg = '重新上架'
      } else if (status === 4) {
        msg = '已下架'
      }
      verifyWorks({ id: id, status: status, remark: this.statusRemark }).then(res => {
        this.statusRemark = ''
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: msg })
          this.pageInfo.datas.splice(index, 1)
        }
      })
    },
    recommend(index, id, status) {
      verifyWorks({ id: id, status: status, remark: this.statusRemark }).then(res => {
        this.statusRemark = ''
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: status === 0 ? '已推荐' : '已下架' })
          this.pageInfo.datas.splice(index, 1)
        }
      })
    }
  }
}
</script>

<style>
.el-table__body-wrapper {
    overflow-y: auto;
}
</style>
