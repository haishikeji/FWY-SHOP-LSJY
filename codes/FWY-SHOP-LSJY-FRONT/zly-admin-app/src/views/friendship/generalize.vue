<template>
  <div v-if="pageInfo.userRegiste" class="box">

    <el-row type="flex" justify="start">

      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>用户注册</span>
        </div>
        <div class="box-da">
          <span>今日</span>
          <span>昨日</span>
          <span>总注册</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userRegiste.today }}</span>
          <span>{{ pageInfo.userRegiste.yesToday }}</span>
          <span>{{ pageInfo.userRegiste.total }}</span>
        </div>
      </el-card>

      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>声卡发布</span>
        </div>
        <div class="box-da">
          <span>今日</span>
          <span>昨日</span>
          <span>总发布</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userWork.today }}</span>
          <span>{{ pageInfo.userWork.yesToday }}</span>
          <span>{{ pageInfo.userWork.total }}</span>
        </div>
      </el-card>

      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>动态发布</span>
        </div>
        <div class="box-da">
          <span>今日</span>
          <span>昨日</span>
          <span>总发布</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userDailyDynamic.today }}</span>
          <span>{{ pageInfo.userDailyDynamic.yesToday }}</span>
          <span>{{ pageInfo.userDailyDynamic.total }}</span>
        </div>
      </el-card>

    </el-row>

    <el-row type="flex" justify="start">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>vip开通</span>
        </div>
        <div class="box-da">
          <span>今日</span>
          <span>昨日</span>
          <span>总开通</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userVIP.today }}</span>
          <span>{{ pageInfo.userVIP.yesToday }}</span>
          <span>{{ pageInfo.userVIP.total }}</span>
        </div>
        <div class="box-da">
          <span>1个月</span>
          <span>3个月</span>
          <span>12个月</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userVIP.oneMonth }}</span>
          <span>{{ pageInfo.userVIP.threeMonth }}</span>
          <span>{{ pageInfo.userVIP.twelveMonth }}</span>
        </div>
        <div class="box-da">
          <span>今日充值</span>
          <span>昨日充值</span>
          <span>总充值</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userVIP.todayAmount }}</span>
          <span>{{ pageInfo.userVIP.yesTodayAmount }}</span>
          <span>{{ pageInfo.userVIP.totalAmount }}</span>
        </div>
      </el-card>

      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>爆灯点亮</span>
        </div>
        <div class="box-da">
          <span>今日</span>
          <span>昨日</span>
          <span>总开通</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userExplosionLight.today }}</span>
          <span>{{ pageInfo.userExplosionLight.yesToday }}</span>
          <span>{{ pageInfo.userExplosionLight.total }}</span>
        </div>
        <div class="box-da">
          <span>1个月</span>
          <span>3个月</span>
          <span>12个月</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userExplosionLight.oneMonth }}</span>
          <span>{{ pageInfo.userExplosionLight.threeMonth }}</span>
          <span>{{ pageInfo.userExplosionLight.twelveMonth }}</span>
        </div>
        <div class="box-da">
          <span>今日充值</span>
          <span>昨日充值</span>
          <span>总充值</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userExplosionLight.todayAmount }}</span>
          <span>{{ pageInfo.userExplosionLight.yesTodayAmount }}</span>
          <span>{{ pageInfo.userExplosionLight.totalAmount }}</span>
        </div>
      </el-card>

      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>私信聊天</span>
        </div>
        <div class="box-da">
          <span>今日</span>
          <span>昨日</span>
          <span>总开通</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userPrivateLetter.today }}</span>
          <span>{{ pageInfo.userPrivateLetter.yesToday }}</span>
          <span>{{ pageInfo.userPrivateLetter.total }}</span>
        </div>
        <div class="box-da">
          <span>1个月</span>
          <span>3个月</span>
          <span>12个月</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userPrivateLetter.oneMonth }}</span>
          <span>{{ pageInfo.userPrivateLetter.threeMonth }}</span>
          <span>{{ pageInfo.userPrivateLetter.twelveMonth }}</span>
        </div>
        <div class="box-da">
          <span>今日充值</span>
          <span>昨日充值</span>
          <span>总充值</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userPrivateLetter.todayAmount }}</span>
          <span>{{ pageInfo.userPrivateLetter.yesTodayAmount }}</span>
          <span>{{ pageInfo.userPrivateLetter.totalAmount }}</span>
        </div>
      </el-card>
    </el-row>

    <el-row type="flex" justify="start">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>形象卡发布</span>
        </div>
        <div class="box-da">
          <span>今日</span>
          <span>昨日</span>
          <span>总发布</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.userImageCardVO.today }}</span>
          <span>{{ pageInfo.userImageCardVO.yesToday }}</span>
          <span>{{ pageInfo.userImageCardVO.total }}</span>
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>U豆充值</span>
        </div>
        <div class="box-da">
          <span>今日充值</span>
          <span>昨日充值</span>
          <span>总充值</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.gift.today }}</span>
          <span>{{ pageInfo.gift.yesToday }}</span>
          <span>{{ pageInfo.gift.total }}</span>
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>陪玩订单</span>
        </div>
        <div class="box-da">
          <span>服务中</span>
          <span>已完成</span>
          <span>订单总额</span>
        </div>
        <div class="box-data">
          <span>{{ pageInfo.playerOrderStatisticsVO.serviceOrder }}</span>
          <span>{{ pageInfo.playerOrderStatisticsVO.completeOrder }}</span>
          <span>￥{{ pageInfo.playerOrderStatisticsVO.orderTotal }}</span>
        </div>
      </el-card>
    </el-row>

  </div>
</template>

<script>
import { generalize } from '@/api/admin_user'
export default {
  name: 'Generalize',
  data() {
    return {
      pageInfo: {}
    }
  },
  created() {
    this.loadDatas()
    if (window.Notification) {
      console.log('支持Notification')
    } else {
      console.log('不支持Notification')
    }
    if (window.Notification) {
      // 仅支持https
      Notification.requestPermission(function(status) {
        var n = new Notification('通知标题', { body: '这里是通知内容！' })
      })
    }
  },
  methods: {
    loadDatas() {
      generalize().then(res => {
        this.pageInfo = res.data.data
      })
    }
  }
}
</script>

<style scoped>
.box{
    padding: 10px;
}
.box-card {
    margin: 10px;
}
.box-da,
.box-data{
    height: 30px;
    line-height: 30px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}
.box-da>span{
    width: 120px;
    color:rgba(0, 0, 0, 0.5);
    text-align: center;
}
.box-data>span{
    width: 120px;
    color:rgba(0, 0, 0);
    font-weight:400;
    text-align: center;
}
.clearfix{
    font-weight: 700;
}
</style>
