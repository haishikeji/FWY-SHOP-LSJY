<!-- 提现页面 -->
<template>
  <div id="withdraw" class="app-container">

    <div class="filter-container">
      <el-input v-model="listQuery.userId" placeholder="输入需要搜索用户id" style="width: 200px;" class="filter-item" type="text" maxlength="15" minlength="7" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="待审核" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户id" />
          <el-table-column prop="id" label="提现id" />
          <el-table-column prop="nickName" label="昵称" />
          <el-table-column label="性别">
            <template slot-scope="scope">
              <label v-if="scope.row.gender === 0">男</label>
              <label v-else-if="scope.row.gender === 1">女</label>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="电话" />
          <el-table-column prop="type" label="提现方式" />
          <el-table-column prop="realName" label="姓名" />
          <el-table-column label="订单号">
            <template slot-scope="scope">
              <label v-if="scope.row.partnerTradeNo">{{scope.row.partnerTradeNo}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="openId">
            <template slot-scope="scope">
              <label v-if="scope.row.openId">{{scope.row.openId}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="微信昵称">
            <template slot-scope="scope">
              <label v-if="scope.row.accountNickName">{{scope.row.accountNickName}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column prop="cardNumber" label="银行卡号" />
          <el-table-column prop="bankName" label="所属银行" />
          <el-table-column prop="sugar" label="Q糖数量" />
          <el-table-column prop="withDrawAmount" label="提现金额" />
          <el-table-column label="状态">
            <template slot-scope="scope">
              <label v-if="scope.row.status === 1">待审核</label>
              <label v-else-if="scope.row.status === 2">待转账</label>
              <label v-else-if="scope.row.status === 3">已转账</label>
              <label v-else-if="scope.row.status === 4" style="color: #FF4D51">拒绝</label>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="时间" />
          <el-table-column prop="operatorUser" label="操作人" />
          <el-table-column fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="verify(scope.$index, scope.row.id, 2)">
                <label style="color: #61C091">通过</label>
              </el-button>
              <el-button size="mini" type="text" @click="verifyRefuse(scope.row.id)">
                <label style="color: #FF4D51">拒绝</label>
              </el-button>
              <el-button size="mini" type="text" @click="withdrawLog(scope.row.id)">
                <label>操作日志</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="待转账" name="second">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户id" />
          <el-table-column prop="id" label="提现id" />
          <el-table-column prop="nickName" label="昵称" />
          <el-table-column label="性别">
            <template slot-scope="scope">
              <label v-if="scope.row.gender === 0">男</label>
              <label v-else-if="scope.row.gender === 1">女</label>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="电话" />
          <el-table-column prop="type" label="提现方式" />
          <el-table-column prop="realName" label="姓名" />
          <el-table-column label="订单号">
            <template slot-scope="scope">
              <label v-if="scope.row.partnerTradeNo">{{scope.row.partnerTradeNo}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="openId">
            <template slot-scope="scope">
              <label v-if="scope.row.openId">{{scope.row.openId}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="微信昵称">
            <template slot-scope="scope">
              <label v-if="scope.row.accountNickName">{{scope.row.accountNickName}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column prop="cardNumber" label="银行卡号" />
          <el-table-column prop="bankName" label="所属银行" />
          <el-table-column prop="sugar" label="Q糖数量" />
          <el-table-column prop="withDrawAmount" label="提现金额" />
          <el-table-column label="状态">
            <template slot-scope="scope">
              <label v-if="scope.row.status === 1">待审核</label>
              <label v-else-if="scope.row.status === 2">待转账</label>
              <label v-else-if="scope.row.status === 3">已转账</label>
              <label v-else-if="scope.row.status === 4" style="color: #FF4D51">拒绝</label>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="申请时间" />
          <el-table-column prop="operationTime" label="最新时间" />
          <el-table-column prop="operatorUser" label="操作人" />
          <el-table-column fixed="right" label="操作" width="100">
            <template slot-scope="scope" style="text-align:left;">
              <el-button size="mini" type="text" @click="transfer(scope.$index, scope.row.id, scope.row.withdrawChannel, scope.row.openId)">
                <label v-if="scope.row.withdrawChannel === 0" style="color: #FF4D51">银行卡转账</label>
                <label v-else-if="scope.row.withdrawChannel === 1" style="color: #FF4D51">微信转账</label>
                <label v-else-if="scope.row.withdrawChannel === 2" style="color: #FF4D51">支付宝转账</label>
              </el-button>
              <el-button size="mini" type="text" @click="withdrawLog(scope.row.id)">
                <label>操作日志</label>
              </el-button>
              <el-button size="mini" type="text" @click="re_transfer(scope.$index, scope.row.id, 0)">
                <label style="color: #FF4D51">手动成功</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已转账" name="third">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户id" />
          <el-table-column prop="id" label="提现id" />
          <el-table-column prop="nickName" label="昵称" />
          <el-table-column label="性别">
            <template slot-scope="scope">
              <label v-if="scope.row.gender === 0">男</label>
              <label v-else-if="scope.row.gender === 1">女</label>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="电话" />
          <el-table-column prop="type" label="提现方式" />
          <el-table-column prop="realName" label="姓名" />
          <el-table-column label="订单号">
            <template slot-scope="scope">
              <label v-if="scope.row.partnerTradeNo">{{scope.row.partnerTradeNo}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="openId">
            <template slot-scope="scope">
              <label v-if="scope.row.openId">{{scope.row.openId}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="微信昵称">
            <template slot-scope="scope">
              <label v-if="scope.row.accountNickName">{{scope.row.accountNickName}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column prop="cardNumber" label="银行卡号" />
          <el-table-column prop="bankName" label="所属银行" />
          <el-table-column prop="sugar" label="Q糖数量" />
          <el-table-column prop="withDrawAmount" label="提现金额" />
          <el-table-column label="状态">
            <template slot-scope="scope">
              <label v-if="scope.row.status === 1">待审核</label>
              <label v-else-if="scope.row.status === 2">待转账</label>
              <label v-else-if="scope.row.status === 3">已转账</label>
              <label v-else-if="scope.row.status === 4" style="color: #FF4D51">拒绝</label>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="申请时间" />
          <el-table-column prop="operationTime" label="最新时间" />
          <el-table-column prop="operatorUser" label="操作人" />
          <el-table-column fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="remark(scope.row.id)">
                <label style="color: #FF4D51">备注</label>
              </el-button>
              <el-button size="mini" type="text" @click="detail(scope.row.id)">
                <label style="color: #FF4D51">详情</label>
              </el-button>
              <el-button size="mini" type="text" @click="withdrawLog(scope.row.id)">
                <label>操作日志</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已拒绝" name="four">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户id" />
          <el-table-column prop="id" label="提现id" />
          <el-table-column prop="nickName" label="昵称" />
          <el-table-column label="性别">
            <template slot-scope="scope">
              <label v-if="scope.row.gender === 0">男</label>
              <label v-else-if="scope.row.gender === 1">女</label>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="电话" />
          <el-table-column prop="type" label="提现方式" />
          <el-table-column prop="realName" label="姓名" />
          <el-table-column label="订单号">
            <template slot-scope="scope">
              <label v-if="scope.row.partnerTradeNo">{{scope.row.partnerTradeNo}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="openId">
            <template slot-scope="scope">
              <label v-if="scope.row.openId">{{scope.row.openId}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="微信昵称">
            <template slot-scope="scope">
              <label v-if="scope.row.accountNickName">{{scope.row.accountNickName}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column prop="cardNumber" label="银行卡号" />
          <el-table-column prop="bankName" label="所属银行" />
          <el-table-column prop="sugar" label="Q糖数量" />
          <el-table-column prop="withDrawAmount" label="提现金额" />
          <el-table-column label="状态">
            <template slot-scope="scope">
              <label v-if="scope.row.status === 1">待审核</label>
              <label v-else-if="scope.row.status === 2">待转账</label>
              <label v-else-if="scope.row.status === 3">已转账</label>
              <label v-else-if="scope.row.status === 4" style="color: #FF4D51">拒绝</label>
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="拒绝理由" />
          <el-table-column prop="createTime" label="申请时间" />
          <el-table-column prop="operationTime" label="最新时间" />
          <el-table-column prop="operatorUser" label="操作人" />
          <!-- <el-table-column fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="remark(scope.row.id)">
                <label style="color: #FF4D51">备注</label>
              </el-button>
              <el-button size="mini" type="text" @click="detail(scope.row.id)">
                <label style="color: #FF4D51">详情</label>
              </el-button>
            </template>
          </el-table-column> -->
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="失败/异常" name="fifth">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户id" />
          <el-table-column prop="id" label="提现id" />
          <el-table-column prop="nickName" label="昵称" />
          <el-table-column label="性别">
            <template slot-scope="scope">
              <label v-if="scope.row.gender === 0">男</label>
              <label v-else-if="scope.row.gender === 1">女</label>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="电话" />
          <el-table-column prop="type" label="提现方式" />
          <el-table-column prop="realName" label="姓名" />
          <el-table-column label="订单号">
            <template slot-scope="scope">
              <label v-if="scope.row.partnerTradeNo">{{scope.row.partnerTradeNo}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="openId">
            <template slot-scope="scope">
              <label v-if="scope.row.openId">{{scope.row.openId}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column label="微信昵称">
            <template slot-scope="scope">
              <label v-if="scope.row.accountNickName">{{scope.row.accountNickName}}</label>
              <label v-else>—</label>
            </template>
          </el-table-column>
          <el-table-column prop="cardNumber" label="银行卡号" />
          <el-table-column prop="bankName" label="所属银行" />
          <el-table-column prop="sugar" label="Q糖数量" />
          <el-table-column prop="withDrawAmount" label="提现金额" />
          <el-table-column label="状态">
            <template slot-scope="scope">
              <label v-if="scope.row.status === 1">待审核</label>
              <label v-else-if="scope.row.status === 2">待转账</label>
              <label v-else-if="scope.row.status === 3">已转账</label>
              <label v-else-if="scope.row.status === 4" style="color: #FF4D51">拒绝</label>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="申请时间" />
          <el-table-column prop="operationTime" label="最新时间" />
          <el-table-column prop="operatorUser" label="操作人" />
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope" style="text-align:left;">
              <el-button size="mini" type="text" @click="re_transfer(scope.$index, scope.row.id, 1)">
                <label style="color: #FF4D51">重新转账</label>
              </el-button>
              <el-button size="mini" type="text" @click="re_transfer(scope.$index, scope.row.id, 0)">
                <label style="color: #FF4D51">手动成功</label>
              </el-button>
              <el-button size="mini" type="text" @click="remark(scope.row.id)">
                <label style="color: #FF4D51">备注</label>
              </el-button>
              <el-button size="mini" type="text" @click="withdrawLog(scope.row.id)">
                <label>操作日志</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog :visible.sync="remarkView" title="备注信息">
      <el-form label-position="top">
        <el-form-item label="添加转账记录截图">
          <el-upload ref="uploadRemarkImg" action="" :auto-upload="false" :limit="1" list-type="picture" :before-upload="imgBefore">
            <el-button slot="trigger" size="small" type="primary">选择文件</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="备注说明">
          <el-input v-model="remarkFrom.remark" type="textarea" :rows="2" />
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="primary" @click="save()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="detailView" title="详情">
      <el-form label-position="top">
        <el-form-item label="添加转账记录截图">
          <img style="max-width:400px;max-height:280px;" :src="details.imgUrl" alt="">
        </el-form-item>
        <el-form-item label="备注说明">
          <span style="background:#f1f1f1;font-weight:800;">{{ details.content }}</span>
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="primary" @click="saveDetail()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="refuseView" title="理由说明">
      <el-form label-position="top">
        <el-form-item label="拒绝理由">
          <el-input v-model="refuseText.remark" type="textarea" :rows="2" />
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="primary" @click="sendRefuse()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="aboutViewlog" title="操作日志">
      <div v-if="makelog.length > 0">
        <el-steps :space="80" direction="vertical" :active="makelog.length">
          <el-step v-for="(item, index) in makelog" :key="index" :title="item.des" :description="item.operatingTime" icon="el-icon-info"></el-step>
        </el-steps>
      </div>
      <div v-else>
        无操作记录
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
import { withdrawList, withdrawVerify, withdrawRemark, withdrawTransfer, withdrawReTransfer, withdrawDetail, withdrawLog } from '@/api/give_away'
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
        status: 1
      },
      pageInfo: {},
      remarkView: false,
      detailView: false,
      refuseView: false,
      aboutViewlog: false,
      makelog: [],
      remarkFrom: {
        id: null,
        remark: ''
      },
      params: undefined, // 新增的数据对象
      details: {
        imgUrl: null,
        content: null
      },
      refuseText: {
        id: null,
        remark: '你的提现申请已被拒绝;原因如下：'
      }// 拒绝理由
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    withdrawLog (id) {
      withdrawLog({ id: id }).then(res => {
        this.aboutViewlog = true
        const ret = res.data
        if (ret.code === 200) {
          this.makelog = ret.data
        }
      })
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
      } else if (tab.name === 'four') {
        this.listQuery.status = 4
        this.loadDatas()
      } else if (tab.name === 'fifth') {
        this.listQuery.status = 5
        this.loadDatas()
      }

    },
    verify(index, id, status) {
      withdrawVerify({ id: id, status: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '已通过' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    sendRefuse() {
      withdrawVerify({ id: this.refuseText.id, status: 4, remark: this.refuseText.remark }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作成功' })
          this.refuseView = false
          this.loadDatas()
        }
      })
    },
    transfer(index, id, withdrawChannel, openId) {
      if(withdrawChannel==0){
        withdrawTransfer({ id: id, withdrawChannel: withdrawChannel }).then(res => {
          const ret = res.data
          if (ret.code === 200) {
            this.$notify.success({ title: '操作成功' })
            this.pageInfo.list.splice(index, 1)
          }
        })
      }else if(withdrawChannel==1 || withdrawChannel==2){
        withdrawTransfer({ id: id, withdrawChannel: withdrawChannel, openId:openId }).then(res => {
          const ret = res.data
          if (ret.code === 200) {
            this.$notify.success({ title: '操作成功' })
            this.pageInfo.list.splice(index, 1)
          }
        })
      }

    },
    re_transfer(index, id, status){
      withdrawReTransfer({ id: id, withDrawTransferType: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作成功' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    remark(id) {
      this.remarkFrom.id = id
      this.remarkFrom.remark = ''
      this.remarkView = true
    },
    detail(id) {
      withdrawDetail({ id: id }).then((res) => {
        this.detailView = true
        this.details.imgUrl = res.data.data.pictureUrl
        this.details.content = res.data.data.remark
      })
    },
    verifyRefuse(id) {
      this.refuseView = true
      this.refuseText.id = id
      // this.refuseText.remark = null
    },
    saveDetail() {
      this.detailView = false
      this.details.imgUrl = null
      this.details.content = null
    },
    imgBefore(file) {
      this.params.append('file', file)
      return false
    },
    save() {
      this.params = new FormData()
      this.$refs.uploadRemarkImg.submit()

      this.params.append('id', this.remarkFrom.id)
      this.params.append('remark', this.remarkFrom.remark)
      // console.log(this.params.get("file").type);
      if (this.params.get('file') === null) {
        this.$message.error('请选择转账凭据!')
      } else if (this.params.get('file').type !== 'image/jpeg') {
        this.$message.error('上传图片只能是 JPG 格式!')
      } else {
        withdrawRemark(this.params).then((res) => {
          this.$message.success('上传备注成功!')
          this.remarkView = false
        })
      }
    },
    // 获取数据
    loadDatas() {
      withdrawList(this.listQuery).then((res) => {
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
