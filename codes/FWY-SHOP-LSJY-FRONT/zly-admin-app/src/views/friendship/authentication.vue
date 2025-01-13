<template>
  <div id="works" class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.userId" placeholder="输入需要搜索id" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.phone" placeholder="输入需要搜索手机" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="待审核" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="userId" width="110" />
          <el-table-column label="头像" width="110">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" width="110" />
          <el-table-column prop="phone" label="绑定手机" width="120" />
          <el-table-column prop="realName" label="姓名" width="100" />
          <el-table-column prop="idNumber" label="身份证号" width="170" />
          <el-table-column label="手持证件" width="160" >
            <template slot-scope="scope">
              <img :src="scope.row.userHoldPic" @click="showPic(scope.row.userHoldPic)" style="width: 138px;">
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="adopt(scope.$index, scope.row)">
                <label style="color: #61C091">通过</label>
              </el-button>
              <el-button size="mini" type="text" @click="refuse(scope.$index, scope.row)">
                <label style="color: #FF4D51">拒绝</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已认证" name="second">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="userId" width="110" />
          <el-table-column label="头像" width="110">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" width="110" />
          <el-table-column prop="phone" label="绑定手机" width="120" />
          <el-table-column prop="realName" label="姓名" width="100" />
          <el-table-column prop="idNumber" label="身份证号" width="170" />
          <el-table-column label="手持证件" width="160" >
            <template slot-scope="scope">
              <img :src="scope.row.userHoldPic" @click="showPic(scope.row.userHoldPic)" style="width: 138px;">
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已拒绝" name="third">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="userId" width="110" />
          <el-table-column label="头像" width="110">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" width="110" />
          <el-table-column prop="phone" label="绑定手机" width="120" />
          <el-table-column prop="realName" label="姓名" width="100" />
          <el-table-column prop="idNumber" label="身份证号" width="170" />
          <el-table-column label="手持证件" width="160" >
            <template slot-scope="scope">
              <img :src="scope.row.userHoldPic" @click="showPic(scope.row.userHoldPic)" style="width: 138px;">
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog :visible.sync="refuseView" title="理由说明" width="700px">
      <el-form label-position="top">
        <div style="margin-bottom: 20px">
          <el-checkbox-group v-model="refuseList" @change="clickCheckbox">
            <el-checkbox label="实名认证资料不符，请重新更新资料" />
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

    <el-drawer title="查看图片" :visible.sync="showPic_drawer" size="600px" direction="rtl">
      <div style="padding: 0 10px;">
        <img :src="picURL" style="height:auto;max-height:560px;width:auto;max-width:560px;"/>
      </div>
    </el-drawer>

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
import { authenticationList, authenticationVerify } from '@/api/user'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        userId: null,
        nickName: null,
        phone: null,
        pageNum: 1,
        pageSize: 20,
        status: 1
      },
      pageInfo: {},
      showPic_drawer: false,//展示图窗口
      picURL: null, // 图地址
      refuseView: false,//拒绝窗口
      verifyQuery: {// 审核参数
        id: null,
        userId: null,
        status: null,
        remark: null
      },
      refuseList: []//拒绝说明
    }
  },
  mounted() { // created 周期建议处理简单的业务。
    this.loadDatas()
  },
  methods: {
    // 查看技能展示图
    showPic(picURL) {
      this.picURL = picURL
      this.showPic_drawer = true
    },
    adopt (index, row) {
      this.verifyQuery = {
        id: row.id,
        userId: row.userId,
        status: 2
      }
      //console.log(this.verifyQuery)
      authenticationVerify(this.verifyQuery).then(res => {
        if (res.data.code === 200) {
          this.$notify.success({ title: '审核通过' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    // 拒绝
    refuse (index, row) {
      this.refuseList = []
      this.verifyQuery = {
        id: row.id,
        userId: row.userId,
        status: 3,
        remark: null,
      }
      this.refuseView = true
    },
    //选择拒绝理由
    clickCheckbox (e) {
      if (e.length == 1) {
        this.verifyQuery.remark = this.refuseList[0]
      } else if (e.length == 2) {
        this.verifyQuery.remark = this.refuseList[0] + this.refuseList[1]
      } else {
        this.verifyQuery.remark = null
      }
    },
    //确定拒绝
    sendRefuse() {
      if (this.verifyQuery.remark == null) {
        this.$notify.error({ title: '请说明理由' })
      } else {
        //console.log(this.verifyQuery)
        authenticationVerify(this.verifyQuery).then(res => {
          const ret = res.data
          if (ret.code === 200) {
            this.loadDatas()
            this.$notify.success({ title: '成功设置审核' })
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
      } else if (tab.name === 'third') {
        this.listQuery.status = 3
        this.loadDatas()
      }
    },
    // 获取数据
    loadDatas() {
      authenticationList(this.listQuery).then((res) => {
        // console.log(res);
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
