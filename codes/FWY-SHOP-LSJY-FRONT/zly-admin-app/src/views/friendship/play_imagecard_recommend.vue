<!-- 服务推荐 -->
<template>
  <div id="play_imagecard_recommend" class="app-container">

    <div class="filter-container">
      <el-input v-model="listQuery.id" placeholder="输入需要搜索id" style="width: 200px;" class="filter-item" type="text" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="待推荐" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="id" width="90" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="90" />
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <!-- <el-table-column label="实名照" width="102">
            <template slot-scope="scope">
              <img :src="scope.row.userRealPicture" @click="showPic(scope.row.userRealPicture)" style="width: 80px;">
            </template>
          </el-table-column> -->
          <el-table-column label="认证技能" width="100">
            <template slot-scope="scope">
              <div v-for="item in scope.row.skillNamelist" :key="item">
                <span>{{ item }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="skillNamelist.length" label="技能数" width="60" />
          <el-table-column label="技能展示图" width="140">
            <template slot-scope="scope">
              <img :src="scope.row.skillAuthenPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.skillAuthenPicture)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.voideoCoverImage" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="userImage(scope.row.userId)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="技能信息" width="160">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showSkill(scope.row.skillVO)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="服务状态">
            <template slot-scope="scope">
              <label v-if="scope.row.logicDeleteStatus===0" style="color: #61C091">开启</label>
              <label v-else style="color: #61C091">关闭</label>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="110">
            <template slot-scope="scope">
              <el-button type="success" style="margin:0 0 10px" @click="recommend(scope.$index, scope.row.userId)">推荐</el-button>
              <el-button type="danger" style="margin:0" @click="noRecommend(scope.$index, scope.row.userId, 2, 4)">不推荐</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已推荐" name="second">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="id" width="90" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="90" />
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <!-- <el-table-column label="实名照" width="102">
            <template slot-scope="scope">
              <img :src="scope.row.userRealPicture" @click="showPic(scope.row.userRealPicture)" style="width: 80px;">
            </template>
          </el-table-column> -->
          <el-table-column label="认证技能" width="100">
            <template slot-scope="scope">
              <div v-for="item in scope.row.skillNamelist" :key="item">
                <span>{{ item }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="技能展示图" width="140">
            <template slot-scope="scope">
              <img :src="scope.row.skillAuthenPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.skillAuthenPicture)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.voideoCoverImage" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="userImage(scope.row.userId)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="技能信息" width="160">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showSkill(scope.row.skillVO)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="服务状态" >
            <template slot-scope="scope">
              <label v-if="scope.row.logicDeleteStatus===0" style="color: #61C091">开启</label>
              <label v-else style="color: #61C091">关闭</label>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="120">
            <template slot-scope="scope">
              <el-button type="danger" style="margin:0 0 10px" @click="obtained(scope.$index, scope.row.userId)">下架</el-button>
              <el-button v-if="scope.row.indexRecommendStatus == 0" style="margin:0" @click="updateStatus(scope.$index, scope.row.userId, 2, 6)">推荐首页</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已下架" name="third">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="id" width="90" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="90" />
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <!-- <el-table-column label="实名照" width="102">
            <template slot-scope="scope">
              <img :src="scope.row.userRealPicture" @click="showPic(scope.row.userRealPicture)" style="width: 80px;">
            </template>
          </el-table-column> -->
          <el-table-column label="认证技能" width="100">
            <template slot-scope="scope">
              <div v-for="item in scope.row.skillNamelist" :key="item">
                <span>{{ item }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="技能展示图" width="140">
            <template slot-scope="scope">
              <img :src="scope.row.skillAuthenPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.skillAuthenPicture)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.voideoCoverImage" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="userImage(scope.row.userId)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="技能信息" width="160">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showSkill(scope.row.skillVO)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="服务状态">
            <template slot-scope="scope">
              <label v-if="scope.row.logicDeleteStatus===0" style="color: #61C091">开启</label>
              <label v-else style="color: #61C091">关闭</label>
            </template>
          </el-table-column>
          <!-- <el-table-column label="操作" fixed="right" width="100">
            <template slot-scope="scope">
              <el-button type="success" @click="updateStatus(scope.$index, scope.row.userId, 1, 0)">上架</el-button>
            </template>
          </el-table-column> -->
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="不推荐" name="four">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="id" width="90" />
          <el-table-column prop="nickName" label="昵称" width="90" />
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <!-- <el-table-column label="实名照" width="102">
            <template slot-scope="scope">
              <img :src="scope.row.userRealPicture" @click="showPic(scope.row.userRealPicture)" style="width: 80px;">
            </template>
          </el-table-column> -->
          <el-table-column label="认证技能" width="100">
            <template slot-scope="scope">
              <div v-for="item in scope.row.skillNamelist" :key="item">
                <span>{{ item }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="技能展示图" width="140">
            <template slot-scope="scope">
              <img :src="scope.row.skillAuthenPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.skillAuthenPicture)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.voideoCoverImage" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="userImage(scope.row.userId)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="技能信息" width="160">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showSkill(scope.row.skillVO)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="服务状态" >
            <template slot-scope="scope">
              <label v-if="scope.row.logicDeleteStatus===0" style="color: #61C091">开启</label>
              <label v-else style="color: #61C091">关闭</label>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="首页推荐" name="fifth">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="id" width="90" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="90" />
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <!-- <el-table-column label="实名照" width="102">
            <template slot-scope="scope">
              <img :src="scope.row.userRealPicture" @click="showPic(scope.row.userRealPicture)" style="width: 80px;">
            </template>
          </el-table-column> -->
          <el-table-column label="认证技能" width="100">
            <template slot-scope="scope">
              <div v-for="item in scope.row.skillNamelist" :key="item">
                <span>{{ item }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="技能展示图" width="140">
            <template slot-scope="scope">
              <img :src="scope.row.skillAuthenPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.skillAuthenPicture)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.voideoCoverImage" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="userImage(scope.row.userId)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="技能信息" width="160">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showSkill(scope.row.skillVO)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="服务状态">
            <template slot-scope="scope">
              <label v-if="scope.row.logicDeleteStatus===0" style="color: #61C091">开启</label>
              <label v-else style="color: #61C091">关闭</label>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="110">
            <template slot-scope="scope">
              <el-button type="danger" @click="updateStatus(scope.$index, scope.row.userId, 0, 1)">首页下架</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog title="本人展示" :visible.sync="showUserImage_drawer" top="2vh" width="400px" height="660">
      <div class="pic_video_list">
        <div v-for="item in UserShowPicList" :key="item.pictureId" class="checkbox">
          <img :src="item.pictureUrl" alt="" style="width:200px;">
        </div>
        <div v-for="item in UserShowVideoList" :key="item.imageCardId" class="checkbox">
          <video :poster="item.videoCoverUrl" preload="“metadata”" controls="controls" width="200px" height="120px">
            <source :src="item.videoUrl" type="video/mp4; codecs=hevc">
            <source :src="item.videoUrl" type="video/ogg">
            <source :src="item.videoUrl" type="video/WebM">
          </video>
        </div>
      </div>
    </el-dialog>

    <el-drawer title="查看图片" :visible.sync="showPic_drawer" size="600px">
      <div style="padding: 0 10px;">
        <img :src="picURL" style="height:auto;max-height:560px;width:auto;max-width:560px;"/>
      </div>
    </el-drawer>

    <el-dialog :visible.sync="showSkill_drawer" title="技能信息" width="400px">
      <el-form :model="skill_obj" label-width="180px" label-position="right" size="medium">
        <el-form-item label="游戏技能：" prop="title">{{ skill_obj.gameName }}</el-form-item>
        <el-form-item label="服务状态：" prop="title">{{ skill_obj.serviceStatus==0?'接单中':'停止接单' }}</el-form-item>
        <el-form-item label="服务标题：" prop="title">{{ skill_obj.serviceTitle }}</el-form-item>
        <el-form-item label="服务价格：" prop="title">{{ skill_obj.price }}</el-form-item>
        <el-form-item label="认证段位：" prop="title">{{ skill_obj.userGameLevel }}</el-form-item>
      </el-form>
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
import { recommendList, recommendUpdate, recommendUserImage, recommendUpdateNot, recommendUserGame, obtainedUserGame } from '@/api/gamesAccompany'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        id: null,
        nickName: null,
        pageNum: 1,
        pageSize: 20,
        status: 2
      },
      pageInfo: {},
      showUserImage_drawer: false,
      UserShowPicList: [], // 本人照片展示
      UserShowVideoList: [], // 本人视频展示
      picURL: null,
      showPic_drawer: false,
      showSkill_drawer: false,
      skill_obj: {}
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    decodeUnicodeN(row) {
      if (row.nickName != null) {
        const str = row.nickName.replace(/\\/g, '%')
        return unescape(str)
      }
    },
    handleFilter() {
      this.loadDatas()
    },
    handleClick(tab, event) {
      this.listQuery.pageNum = 1
      if (tab.name === 'first') {
        this.listQuery.status = 2
        this.loadDatas()
      } else if (tab.name === 'second') {
        this.listQuery.status = 0
        this.loadDatas()
      } else if (tab.name === 'third') {
        this.listQuery.status = 1
        this.loadDatas()
      } else if (tab.name === 'four') {
        this.listQuery.status = 4
        this.loadDatas()
      } else if (tab.name === 'fifth') {
        this.listQuery.status = 3
        this.loadDatas()
      }
    },
    recommend (index, userId) {
      recommendUserGame({ userId: userId }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '推荐成功' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    obtained (index, userId) {
      obtainedUserGame({ userId: userId }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '下架成功' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    updateStatus(index, userId, originalState, status) {
      recommendUpdate({ userId: userId, originalState: originalState, status: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作成功' })
          this.loadDatas()
          //this.pageInfo.list.splice(index, 1)
        }
      })
    },
    noRecommend(index, userId, originalState, status) {
      recommendUpdateNot({ userId: userId, originalState: originalState, status: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作成功' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    showSkill(obj) {
      this.skill_obj = obj
      this.showSkill_drawer = true
    },
    userImage(id) {
      this.showUserImage_drawer = true
      recommendUserImage({ userId: id }).then(res => {
        this.UserShowPicList = res.data.data.filter((item) => item.type == 0)
        this.UserShowVideoList = res.data.data.filter((item) => item.type == 1)
      })
    },
    // 获取数据
    loadDatas() {
      recommendList(this.listQuery).then((res) => {
        //console.log(res)
        this.pageInfo = res.data.data
      })
    },
    // 查看技能展示图
    showPic(picURL) {
      this.picURL = picURL
      this.showPic_drawer = true
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
