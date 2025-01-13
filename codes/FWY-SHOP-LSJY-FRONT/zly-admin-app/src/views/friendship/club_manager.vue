<!-- 公会管理 -->
<template>
  <div id="testView" class="app-container">

    <div class="filter-container">
      <el-input v-model="listQuery.code" placeholder="输入需要搜索公会邀请码" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.clubName" placeholder="输入需要搜索公会名称" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="待审核" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="code" label="公会邀请码" width="100" />
          <el-table-column prop="clubName" label="公会名称" width="100" />
          <el-table-column label="公会logo" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.logo" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column prop="clubDes" label="公会介绍" width="160" />
          <el-table-column prop="wechatName" label="联系微信" width="140" />
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="verify(scope.$index, scope.row.clubId, 2)">
                <label style="color: #61C091">通过</label>
              </el-button>
              <el-button size="mini" type="text" @click="refuse(scope.$index, scope.row.clubId, 3)">
                <label style="color: #FF4D51">拒绝</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已通过" name="second">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="code" label="公会邀请码" width="100" />
          <el-table-column prop="clubName" label="公会名称" width="100" />
          <el-table-column label="公会logo" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.logo" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column prop="clubDes" label="公会介绍" />
          <el-table-column prop="wechatName" label="联系微信" width="120" />
          <el-table-column prop="rate" label="收益比例" width="80" />
          <el-table-column prop="clubUserNum" label="会员" width="80" />
          <el-table-column prop="incomeTotal" label="总收益" width="80" />
          <el-table-column label="管理员" width="120">
            <template slot-scope="scope">
              <p>id：{{scope.row.adminId}}</p>
              <p>昵称：{{scope.row.adminName}}</p>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="80">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="edit(scope.row.clubId, scope.row.clubName, scope.row.rate)">
                <label style="color: #61C091">设置</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog :visible.sync="refuseView" title="理由说明" width="700px">
      <el-form label-position="top">
        <div style="margin-bottom: 20px">
          <el-checkbox-group v-model="checkList" @change="clickCheckbox">
            <el-checkbox label="公会名称或公会介绍存在敏感信息，请修改后再提交" />
          </el-checkbox-group>
        </div>
        <el-form-item label="拒绝理由">
          <el-input v-model="verifyQuery.remark" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" maxlength="120" show-word-limit />
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="primary" @click="sendRefuse()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="clubrateView" title="收益比例" width="400px">
      <el-form :label-position="labelPosition" label-width="100px">
        <el-form-item label="公会id：">
          <el-input v-model="clubrateQuery.clubId" type="text" :disabled="true"/>
        </el-form-item>
        <el-form-item label="公会名称：">
          <el-input v-model="clubrateQuery.clubName" type="text" :disabled="true"/>
        </el-form-item>
        <el-form-item label="收益比例：">
          <el-input v-model="clubrateQuery.rate" type="text" />
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="primary" @click="editOK()">确定</el-button>
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
import { club_managerList, club_managerVerify, club_managerChange } from '@/api/club_manager'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        clubId: null,
        clubName: null,
        pageNum: 1,
        pageSize: 20,
        status: 1
      },
      refuseView: false,//拒绝窗口
      verifyQuery: {// 拒绝参数
        clubId: null,
        status: null,
        remark: null
      },
      pageInfo: {},
      checkList: [],//拒绝说明
      clubrateView: false,
      clubrateQuery: {
        clubId: null,
        clubName: null,
        rate: null
      }
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    editOK () {
      club_managerChange(this.clubrateQuery).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.loadDatas()
          this.$notify.success({ title: '设置成功' })
          this.clubrateView = false
        }
      })
    },
    edit (clubId, clubName, rate) {
      this.clubrateView = true
      this.clubrateQuery.clubId = clubId
      this.clubrateQuery.clubName = clubName
      this.clubrateQuery.rate = rate
    },
    // 拒绝
    refuse(index, id, status) {
      this.checkList = []
      this.verifyQuery = {
        clubId: id,
        status: status,
        remark: null
      }
      this.refuseView = true
    },    
    //选择拒绝理由
    clickCheckbox(e) {
      if (e.length == 1) {
        this.verifyQuery.remark = this.checkList[0]
      } else if (e.length == 2) {
        this.verifyQuery.remark = this.checkList[0] + this.checkList[1]
      } else {
        this.verifyQuery.remark = null
      }
    },      
    //确定拒绝
    sendRefuse() {
      // console.log(this.verifyQuery)
      if (this.verifyQuery.remark == null) {
        this.$notify.error({ title: '请说明理由' })
      } else {
        club_managerVerify(this.verifyQuery).then(res => {
          const ret = res.data
          if (ret.code === 200) {
            this.loadDatas()
            this.$notify.success({ title: '拒绝成功' })
            this.refuseView = false
          }
        })
      }
    },    
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
      club_managerVerify({ clubId: id, status: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '成功通过' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    // 获取数据
    loadDatas() {
      club_managerList(this.listQuery).then((res) => {
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
